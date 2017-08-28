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

import javax.portlet.PortletConfig;
import javax.portlet.faces.BridgePublicRenderParameterHandler;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class BridgePublicRenderParameterHandlerFactory
	implements Wrapper<BridgePublicRenderParameterHandlerFactory> {

	/**
	 * Returns a thread-safe instance of {@link BridgePublicRenderParameterHandler} from the {@link
	 * BridgePublicRenderParameterHandlerFactory} found by the {@link BridgeFactoryFinder}. The returned instance is not
	 * guaranteed to be {@link java.io.Serializable}.
	 */
	public static BridgePublicRenderParameterHandler getBridgePublicRenderParameterHandlerInstance(
		PortletConfig portletConfig) {

		BridgePublicRenderParameterHandlerFactory bridgePublicRenderParameterHandlerFactory =
			(BridgePublicRenderParameterHandlerFactory) BridgeFactoryFinder.getFactory(
				portletConfig.getPortletContext(), BridgePublicRenderParameterHandlerFactory.class);

		return bridgePublicRenderParameterHandlerFactory.getBridgePublicRenderParameterHandler(portletConfig);
	}

	/**
	 * Returns a thread-safe instance of {@link BridgePublicRenderParameterHandler}. The returned instance is not
	 * guaranteed to be {@link java.io.Serializable}.
	 */
	public abstract BridgePublicRenderParameterHandler getBridgePublicRenderParameterHandler(
		PortletConfig portletConfig);

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 */
	@Override
	public abstract BridgePublicRenderParameterHandlerFactory getWrapped();
}
