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
package javax.portlet.faces;

import java.util.Iterator;
import java.util.ServiceLoader;

import javax.faces.FacesException;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeFactoryFinder {

	// Private Static Data Members
	private static BridgeFactoryFinder instance;

	public static Object getFactory(Class<?> clazz) {
		return getInstance().getFactoryInstance(clazz);
	}

	public static BridgeFactoryFinder getInstance() throws FacesException {

		if (instance == null) {

			ServiceLoader<BridgeFactoryFinder> serviceLoader = ServiceLoader.load(BridgeFactoryFinder.class);

			if (serviceLoader != null) {

				Iterator<BridgeFactoryFinder> iterator = serviceLoader.iterator();

				while ((instance == null) && iterator.hasNext()) {
					instance = iterator.next();
				}

				if (instance == null) {
					throw new FacesException("Unable locate service for " + BridgeFactoryFinder.class.getName());
				}
			}
			else {
				throw new FacesException("Unable to acquire ServiceLoader for " + BridgeFactoryFinder.class.getName());
			}
		}

		return instance;
	}

	public abstract Object getFactoryInstance(Class<?> clazz);
}
