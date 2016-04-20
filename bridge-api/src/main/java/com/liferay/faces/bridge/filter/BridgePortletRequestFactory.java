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

import javax.faces.FacesWrapper;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.config.BridgeConfig;


/**
 * @author  Neil Griffin
 */
public abstract class BridgePortletRequestFactory implements FacesWrapper<BridgePortletRequestFactory> {

	/**
	 * Returns an instance of {@link ActionRequest} from the {@link BridgePortletRequestFactory} found by the {@link
	 * BridgeFactoryFinder}.
	 */
	public static ActionRequest getActionRequestInstance(ActionRequest actionRequest, ActionResponse actionResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig) {

		BridgePortletRequestFactory bridgePortletRequestFactory = (BridgePortletRequestFactory) BridgeFactoryFinder
			.getFactory(BridgePortletRequestFactory.class);

		return bridgePortletRequestFactory.getActionRequest(actionRequest, actionResponse, portletConfig, bridgeConfig);
	}

	/**
	 * Returns an instance of {@link EventRequest} from the {@link BridgePortletRequestFactory} found by the {@link
	 * BridgeFactoryFinder}.
	 */
	public static EventRequest getEventRequestInstance(EventRequest eventRequest, EventResponse eventResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig) {

		BridgePortletRequestFactory bridgePortletRequestFactory = (BridgePortletRequestFactory) BridgeFactoryFinder
			.getFactory(BridgePortletRequestFactory.class);

		return bridgePortletRequestFactory.getEventRequest(eventRequest, eventResponse, portletConfig, bridgeConfig);
	}

	/**
	 * Returns an instance of {@link RenderRequest} from the {@link BridgePortletRequestFactory} found by the {@link
	 * BridgeFactoryFinder}.
	 */
	public static RenderRequest getRenderRequestInstance(RenderRequest renderRequest, RenderResponse renderResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig) {

		BridgePortletRequestFactory bridgePortletRequestFactory = (BridgePortletRequestFactory) BridgeFactoryFinder
			.getFactory(BridgePortletRequestFactory.class);

		return bridgePortletRequestFactory.getRenderRequest(renderRequest, renderResponse, portletConfig, bridgeConfig);
	}

	/**
	 * Returns an instance of {@link ResourceRequest} from the {@link BridgePortletRequestFactory} found by the {@link
	 * BridgeFactoryFinder}.
	 */
	public static ResourceRequest getResourceRequestInstance(ResourceRequest resourceRequest,
		ResourceResponse resourceResponse, PortletConfig portletConfig, BridgeConfig bridgeConfig) {

		BridgePortletRequestFactory bridgePortletRequestFactory = (BridgePortletRequestFactory) BridgeFactoryFinder
			.getFactory(BridgePortletRequestFactory.class);

		return bridgePortletRequestFactory.getResourceRequest(resourceRequest, resourceResponse, portletConfig,
				bridgeConfig);
	}

	public abstract ActionRequest getActionRequest(ActionRequest actionRequest, ActionResponse actionResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig);

	public abstract EventRequest getEventRequest(EventRequest eventRequest, EventResponse eventResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig);

	public abstract RenderRequest getRenderRequest(RenderRequest renderRequest, RenderResponse renderResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig);

	public abstract ResourceRequest getResourceRequest(ResourceRequest resourceRequest,
		ResourceResponse resourceResponse, PortletConfig portletConfig, BridgeConfig bridgeConfig);
}
