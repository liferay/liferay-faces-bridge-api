/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.faces.bridge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletContext;


/**
 * This class provides a factory lookup mechanism similar to the {@link javax.faces.FactoryFinder} in the JSF API.
 * Factory instances are stored as attributes in the {@link PortletContext}.
 *
 * @author  Neil Griffin
 */
public abstract class BridgeFactoryFinder {

	/**
	 * @deprecated  Call {@link #getFactory(PortletContext, Class)} instead.
	 *
	 *              <p>Returns the factory instance associated with the specified factory class from the portlet context
	 *              associated with the current portlet request.</p>
	 *
	 * @param       factoryClass  The factory {@link java.lang.Class}.
	 */
	@Deprecated
	public static Object getFactory(Class<?> factoryClass) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletContext portletContext = (PortletContext) externalContext.getContext();

		return getFactory(portletContext, factoryClass);
	}

	/**
	 * Returns the factory instance associated with the specified factory class from the specified portlet context.
	 *
	 * @param  portletContext  The portlet context associated with the current portlet request.
	 * @param  factoryClass    The factory {@link java.lang.Class}.
	 *
	 * @since  4.1
	 * @since  3.1
	 * @since  2.1
	 */
	public static Object getFactory(PortletContext portletContext, Class<?> factoryClass) {
		return getInstance().getFactoryInstance(portletContext, factoryClass);
	}

	/**
	 * Returns the thread-safe Singleton instance of the bridge factory finder.
	 *
	 * @throws  FacesException  When the factory extension finder cannot be discovered.
	 */
	public static BridgeFactoryFinder getInstance() throws FacesException {
		return OnDemandBridgeFactoryFinder.instance;
	}

	/**
	 * @deprecated  Call {@link #getFactory(PortletContext, Class)} instead.
	 *
	 *              <p>Returns the factory instance associated with the specified factory class from the portlet context
	 *              associated with the current portlet request.</p>
	 *
	 * @param       factoryClass  The factory {@link java.lang.Class}.
	 */
	@Deprecated
	public abstract Object getFactoryInstance(Class<?> factoryClass);

	/**
	 * Returns the factory instance associated with the specified factory class from the specified portlet context.
	 *
	 * @param  portletContext  The portlet context associated with the current portlet request.
	 * @param  factoryClass    The factory {@link java.lang.Class}.
	 *
	 * @since  4.1
	 * @since  3.1
	 * @since  2.1
	 */
	public abstract Object getFactoryInstance(PortletContext portletContext, Class<?> factoryClass);

	private static class OnDemandBridgeFactoryFinder {

		// Since this class is not referenced until BridgeFactoryFinder.getInstance() is called, the
		// BridgeFactoryFinder instance will be lazily initialized when BridgeFactoryFinder.getInstance() is called.
		// Class initialization is thread-safe. For more details on this pattern, see
		// http://stackoverflow.com/questions/7420504/threading-lazy-initialization-vs-static-lazy-initialization.
		private static final BridgeFactoryFinder instance;

		static {

			ServiceFinder<BridgeFactoryFinder> serviceLoader = ServiceFinder.load(BridgeFactoryFinder.class);
			Iterator<BridgeFactoryFinder> iterator = serviceLoader.iterator();

			BridgeFactoryFinder bridgeFactoryFinder = null;

			while ((bridgeFactoryFinder == null) && iterator.hasNext()) {
				bridgeFactoryFinder = iterator.next();
			}

			if (bridgeFactoryFinder == null) {
				throw new FacesException("Unable locate service for " + BridgeFactoryFinder.class.getName());
			}

			instance = bridgeFactoryFinder;
		}

		private OnDemandBridgeFactoryFinder() {
			throw new AssertionError();
		}
	}

	private static final class ServiceFinder<S> implements Iterable<S> {

		private Class<S> serviceClass;

		private ServiceFinder(Class<S> serviceClass) {
			this.serviceClass = serviceClass;
		}

		private static <S> ServiceFinder<S> load(Class<S> serviceClass) {
			return new ServiceFinder(serviceClass);
		}

		public Iterator<S> iterator() {

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			List<S> instances = new ArrayList<S>();
			Enumeration<URL> resources = null;

			try {
				resources = classLoader.getResources("META-INF/services/" + serviceClass.getName());
			}
			catch (IOException e) {

				System.err.println("Unable to obtain resources via path=[META-INF/services/" + serviceClass.getName() +
					"]:");
				System.err.println(e);
			}

			while ((resources != null) && resources.hasMoreElements()) {

				URL resource = resources.nextElement();
				InputStream inputStream = null;
				InputStreamReader inputStreamReader = null;
				BufferedReader bufferedReader = null;
				String className = null;

				try {

					inputStream = resource.openStream();
					inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
					bufferedReader = new BufferedReader(inputStreamReader);
					className = bufferedReader.readLine();

				}
				catch (IOException e) {
					System.err.println("Unable to read contents of resource=[" + resource.getPath() + "]");
				}
				finally {

					try {

						if (bufferedReader != null) {
							bufferedReader.close();
						}

						if (inputStreamReader != null) {
							inputStreamReader.close();
						}

						if (inputStream != null) {
							inputStream.close();
						}
					}
					catch (IOException e) {
						// ignore
					}
				}

				if (className != null) {

					try {

						Class<?> clazz = Class.forName(className);
						S instance = (S) clazz.newInstance();
						instances.add(instance);
					}
					catch (Exception e) {

						System.err.println("Unable to instantiate class=[" + className + "]:");
						System.err.println(e);
					}
				}
			}

			return instances.iterator();
		}
	}
}
