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
package com.liferay.faces.bridge.filter;

import javax.portlet.PortletContext;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class BridgePortletContextFactory implements Wrapper<BridgePortletContextFactory> {

	/**
	 * Returns an instance of {@link PortletContext} from the {@link BridgePortletContextFactory} found by the {@link
	 * BridgeFactoryFinder}.
	 */
	public static PortletContext getPortletContextInstance(PortletContext portletContext) {

		BridgePortletContextFactory bridgePortletContextFactory = (BridgePortletContextFactory) BridgeFactoryFinder
			.getFactory(BridgePortletContextFactory.class);

		return bridgePortletContextFactory.getPortletContext(portletContext);
	}

	public abstract PortletContext getPortletContext(PortletContext portletContext);
}
