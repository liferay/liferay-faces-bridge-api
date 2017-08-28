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
package com.liferay.faces.bridge.filter;

import javax.portlet.PortletContext;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.util.helper.Wrapper;


/**
 * @deprecated  Instead, register a custom {@link BridgePortletConfigFactory} that provides a custom instance of {@link
 *              javax.portlet.PortletConfig}, that in turn provides a method {@link Override} for {@link
 *              javax.portlet.PortletConfig#getPortletContext()}.
 * @author      Neil Griffin
 */
@Deprecated
public abstract class BridgePortletContextFactory implements Wrapper<BridgePortletContextFactory> {

	/**
	 * Returns a thread-safe instance of {@link PortletContext} from the {@link BridgePortletContextFactory} found by
	 * the {@link BridgeFactoryFinder}. The returned instance is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static PortletContext getPortletContextInstance(PortletContext portletContext) {

		BridgePortletContextFactory bridgePortletContextFactory = (BridgePortletContextFactory) BridgeFactoryFinder
			.getFactory(portletContext, BridgePortletContextFactory.class);

		return bridgePortletContextFactory.getPortletContext(portletContext);
	}

	/**
	 * Returns a thread-safe instance of {@link PortletContext}. The returned instance is not guaranteed to be {@link
	 * java.io.Serializable}.
	 */
	public abstract PortletContext getPortletContext(PortletContext portletContext);

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 */
	@Override
	public abstract BridgePortletContextFactory getWrapped();
}
