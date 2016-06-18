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

import javax.faces.FacesWrapper;
import javax.portlet.PortletException;
import javax.portlet.faces.Bridge;


/**
 * This class provides the contract for a factory that can create instances of {@link Bridge}. It implements {@link
 * FacesWrapper} in order to follow the standard factory delegation pattern found in the Faces API. If a concrete
 * implementation of this class has a one-arg constructor and the type of the argument is {@link BridgeFactory} then the
 * constructor will be called with the next factory instance in the delegation chain.
 *
 * @author  Neil Griffin
 */
public abstract class BridgeFactory implements FacesWrapper<BridgeFactory> {

	/**
	 * Gets an instance of {@link Bridge} from the {@link BridgeFactory} found by the {@link BridgeFactoryFinder}.
	 */
	public static Bridge getBridgeInstance() throws PortletException {

		BridgeFactory bridgeFactory = (BridgeFactory) BridgeFactoryFinder.getFactory(BridgeFactory.class);

		return bridgeFactory.getBridge();
	}

	/**
	 * Gets an instance of {@link Bridge} from the {@link BridgeFactory} found by the {@link BridgeFactoryFinder}.
	 *
	 * @param  bridgeClassName  The fully-qualified class name of a class that implements {@link Bridge} interface.
	 */
	public static Bridge getBridgeInstance(String bridgeClassName) throws PortletException {

		BridgeFactory bridgeFactory = (BridgeFactory) BridgeFactoryFinder.getFactory(BridgeFactory.class);

		return bridgeFactory.getBridge(bridgeClassName);
	}

	/**
	 * Gets the {@link Bridge} instance which is the first member of the delegation chain.
	 */
	public abstract Bridge getBridge() throws PortletException;

	/**
	 * Returns an {@link Bridge} instance of the specified bridge class name.
	 *
	 * @param  bridgeClassName  The fully-qualified class name of a class that implements {@link Bridge} interface.
	 */
	public abstract Bridge getBridge(String bridgeClassName) throws PortletException;

	/**
	 * If this factory has been decorated then this method provides access to the wrapped factory instance.
	 */
	@Override
	public abstract BridgeFactory getWrapped();
}
