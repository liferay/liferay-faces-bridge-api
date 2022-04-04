/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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
import javax.portlet.PortletConfig;

import org.osgi.annotation.versioning.ProviderType;


/**
 * This abstract class provides a contract for defining a factory that knows how to create instances of type {@link
 * BridgeConfig} and {@link PortletConfig}.
 *
 * @author  Neil Griffin
 */
@ProviderType
public abstract class BridgeConfigFactory implements FacesWrapper<BridgeConfigFactory> {

	/**
	 * Returns a thread-safe instance of {@link BridgeConfig} from the {@link BridgeConfigFactory} found by the {@link
	 * BridgeFactoryFinder}. The returned instance is not guaranteed to be {@link java.io.Serializable}.
	 *
	 * @param  portletConfig  The configuration associated with the current portlet.
	 */
	public static BridgeConfig getBridgeConfigInstance(PortletConfig portletConfig) {

		BridgeConfigFactory bridgeConfigFactory = (BridgeConfigFactory) BridgeFactoryFinder.getFactory(
				portletConfig.getPortletContext(), BridgeConfigFactory.class);

		return bridgeConfigFactory.getBridgeConfig(portletConfig);
	}

	/**
	 * Returns a thread-safe instance of {@link BridgeConfig}. The returned instance is not guaranteed to be {@link
	 * java.io.Serializable}.
	 *
	 * @param  portletConfig  The configuration associated with the current portlet.
	 */
	public abstract BridgeConfig getBridgeConfig(PortletConfig portletConfig);

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 */
	@Override
	public abstract BridgeConfigFactory getWrapped();
}
