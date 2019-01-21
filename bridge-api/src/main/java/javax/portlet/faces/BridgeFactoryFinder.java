/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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
package javax.portlet.faces;

import java.util.Iterator;
import java.util.ServiceLoader;

import javax.faces.FacesException;
import javax.portlet.PortletContext;

import org.osgi.annotation.versioning.ProviderType;


/**
 * This class provides a factory lookup mechanism similar to the {@link javax.faces.FactoryFinder} in the JSF API.
 * Factory instances are stored as attributes in the {@link PortletContext}.
 *
 * @author  Neil Griffin
 */
@ProviderType
public abstract class BridgeFactoryFinder {

	/**
	 * Returns the factory instance associated with the specified factory class from the specified portlet context.
	 *
	 * @param  portletContext  The portlet context associated with the current portlet request.
	 * @param  factoryClass    The factory {@link java.lang.Class}.
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
		return OnDemandBridgeFactoryFinder.INSTANCE;
	}

	/**
	 * Returns the factory instance associated with the specified factory class from the specified portlet context.
	 *
	 * @param  portletContext  The portlet context associated with the current portlet request.
	 * @param  factoryClass    The factory {@link java.lang.Class}.
	 */
	public abstract Object getFactoryInstance(PortletContext portletContext, Class<?> factoryClass);

	/**
	 * Releases all of the factories that are associated with the specified portlet context. It is designed to be called
	 * when a portlet application is taken out of service.
	 *
	 * @param  portletContext  The portlet context associated with the portlet application that is being taken out of
	 *                         service.
	 */
	public abstract void releaseFactories(PortletContext portletContext);

	private static final class OnDemandBridgeFactoryFinder {

		// Since this class is not referenced until BridgeFactoryFinder.getInstance() is called, the
		// BridgeFactoryFinder instance will be lazily initialized when BridgeFactoryFinder.getInstance() is called.
		// Class initialization is thread-safe. For more details on this pattern, see
		// http://stackoverflow.com/questions/7420504/threading-lazy-initialization-vs-static-lazy-initialization.
		private static final BridgeFactoryFinder INSTANCE;

		static {

			ServiceLoader<BridgeFactoryFinder> serviceLoader = ServiceLoader.load(BridgeFactoryFinder.class);
			Iterator<BridgeFactoryFinder> iterator = serviceLoader.iterator();

			BridgeFactoryFinder bridgeFactoryFinder = null;

			while ((bridgeFactoryFinder == null) && iterator.hasNext()) {
				bridgeFactoryFinder = iterator.next();
			}

			if (bridgeFactoryFinder == null) {
				throw new FacesException("Unable locate service for " + BridgeFactoryFinder.class.getName());
			}

			INSTANCE = bridgeFactoryFinder;
		}

		private OnDemandBridgeFactoryFinder() {
			throw new AssertionError();
		}
	}
}
