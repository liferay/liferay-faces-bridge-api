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

import com.liferay.faces.util.helper.Wrapper;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;


/**
 * @author  Neil Griffin
 */
public abstract class RequestAttributeInspectorFactory implements Wrapper<RequestAttributeInspectorFactory> {

	/**
	 * Returns a new instance of {@link RequestAttributeInspector} from the {@link RequestAttributeInspectorFactory}
	 * found by the {@link FactoryExtensionFinder}. The returned instance is designed to be used during execution of a
	 * request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static RequestAttributeInspector getRequestAttributeInspectorInstance(PortletRequest portletRequest,
		PortletConfig portletConfig, BridgeConfig bridgeConfig) {

		RequestAttributeInspectorFactory requestAttributeInspectorFactory = (RequestAttributeInspectorFactory)
			BridgeFactoryFinder.getFactory(RequestAttributeInspectorFactory.class);

		return requestAttributeInspectorFactory.getRequestAttributeInspector(portletRequest, portletConfig,
				bridgeConfig);
	}

	/**
	 * Returns a new instance of {@link RequestAttributeInspector}. The returned instance is designed to be used during
	 * execution of a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public abstract RequestAttributeInspector getRequestAttributeInspector(PortletRequest portletRequest,
		PortletConfig portletConfig, BridgeConfig bridgeConfig);

	/**
	 * Returns the wrapped factory instance if this factory has been decorated. Otherwise, this method returns null.
	 */
	@Override
	public abstract RequestAttributeInspectorFactory getWrapped();
}
