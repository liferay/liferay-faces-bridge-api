/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
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

import java.util.Iterator;
import java.util.ServiceLoader;

import javax.faces.FacesException;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeFactoryFinder {

	public static Object getFactory(Class<?> clazz) {
		return getInstance().getFactoryInstance(clazz);
	}

	public static BridgeFactoryFinder getInstance() throws FacesException {
		return OnDemandBridgeFactoryFinder.instance;
	}

	public abstract Object getFactoryInstance(Class<?> clazz);

	private static class OnDemandBridgeFactoryFinder {

		// Since this class is not referenced until BridgeFactoryFinder.getInstance() is called, the
		// BridgeFactoryFinder instance will be lazily initialized when BridgeFactoryFinder.getInstance() is called.
		// Class initialization is thread-safe. For more details on this pattern, see
		// http://stackoverflow.com/questions/7420504/threading-lazy-initialization-vs-static-lazy-initialization.
		private static final BridgeFactoryFinder instance;

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

			instance = bridgeFactoryFinder;
		}

		private OnDemandBridgeFactoryFinder() {
			throw new AssertionError();
		}
	}
}
