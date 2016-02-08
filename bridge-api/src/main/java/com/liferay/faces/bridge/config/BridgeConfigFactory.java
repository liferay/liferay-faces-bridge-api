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
package com.liferay.faces.bridge.config;

import javax.portlet.PortletConfig;

import com.liferay.faces.bridge.BridgeFactoryFinder;

import com.liferay.faces.util.helper.Wrapper;


/**
 * This abstract class provides a contract for defining a factory that knows how to create instances of type {@link
 * BridgeConfig} and {@link PortletConfig}.
 *
 * @author  Neil Griffin
 */
public abstract class BridgeConfigFactory implements Wrapper<BridgeConfigFactory> {

	/**
	 * Returns an instance of {@link BridgeConfig} from the {@link BridgeConfigFactory} found by the {@link
	 * BridgeFactoryFinder}.
	 *
	 * @param  portletConfig  The configuration associated with the current portlet.
	 */
	public static BridgeConfig getBridgeConfigInstance(PortletConfig portletConfig) {

		BridgeConfigFactory bridgeConfigFactory = (BridgeConfigFactory) BridgeFactoryFinder.getFactory(
				BridgeConfigFactory.class);

		return bridgeConfigFactory.getBridgeConfig(portletConfig);
	}

	public abstract BridgeConfig getBridgeConfig(PortletConfig portletConfig);

	public abstract PortletConfig getPortletConfig(PortletConfig portletConfig);
}
