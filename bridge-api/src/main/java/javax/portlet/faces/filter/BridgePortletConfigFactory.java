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
package javax.portlet.faces.filter;

import javax.faces.FacesWrapper;
import javax.portlet.PortletConfig;
import javax.portlet.faces.BridgeFactoryFinder;


/**
 * @author  Neil Griffin
 */
public abstract class BridgePortletConfigFactory implements FacesWrapper<BridgePortletConfigFactory> {

	/**
	 * Returns a new instance of {@link PortletConfig} from the {@link BridgePortletConfigFactory} found by the {@link
	 * BridgeFactoryFinder}. The returned instance is designed to be used during execution of a request thread, so it
	 * is not guaranteed to be {@link java.io.Serializable}.
	 *
	 * @param  portletConfig  The configuration associated with the current portlet.
	 */
	public static PortletConfig getPortletConfigInstance(PortletConfig portletConfig) {

		BridgePortletConfigFactory bridgePortletConfigFactory = (BridgePortletConfigFactory) BridgeFactoryFinder
			.getFactory(BridgePortletConfigFactory.class);

		return bridgePortletConfigFactory.getPortletConfig(portletConfig);
	}

	/**
	 * Returns a new instance of {@link PortletConfig}. The returned instance is designed to be used during execution of
	 * a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 *
	 * @param  portletConfig  The configuration associated with the current portlet.
	 */
	public abstract PortletConfig getPortletConfig(PortletConfig portletConfig);

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 */
	@Override
	public abstract BridgePortletConfigFactory getWrapped();
}
