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

import com.liferay.faces.bridge.BridgeConfig;
import com.liferay.faces.bridge.BridgeFactoryFinder;


/**
 * @author  Neil Griffin
 */
public abstract class BridgePortletRequestFactory implements FacesWrapper<BridgePortletRequestFactory> {

	/**
	 * Returns a new instance of {@link ActionRequest} from the {@link BridgePortletRequestFactory} found by the {@link
	 * FactoryExtensionFinder}. The returned instance is designed to be used during execution of a request thread, so it
	 * is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static ActionRequest getActionRequestInstance(ActionRequest actionRequest, ActionResponse actionResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig) {

		BridgePortletRequestFactory bridgePortletRequestFactory = (BridgePortletRequestFactory) BridgeFactoryFinder
			.getFactory(BridgePortletRequestFactory.class);

		return bridgePortletRequestFactory.getActionRequest(actionRequest, actionResponse, portletConfig, bridgeConfig);
	}

	/**
	 * Returns a new instance of {@link EventRequest} from the {@link BridgePortletRequestFactory} found by the {@link
	 * FactoryExtensionFinder}. The returned instance is designed to be used during execution of a request thread, so it
	 * is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static EventRequest getEventRequestInstance(EventRequest eventRequest, EventResponse eventResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig) {

		BridgePortletRequestFactory bridgePortletRequestFactory = (BridgePortletRequestFactory) BridgeFactoryFinder
			.getFactory(BridgePortletRequestFactory.class);

		return bridgePortletRequestFactory.getEventRequest(eventRequest, eventResponse, portletConfig, bridgeConfig);
	}

	/**
	 * Returns a new instance of {@link RenderRequest} from the {@link BridgePortletRequestFactory} found by the {@link
	 * FactoryExtensionFinder}. The returned instance is designed to be used during execution of a request thread, so it
	 * is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static RenderRequest getRenderRequestInstance(RenderRequest renderRequest, RenderResponse renderResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig) {

		BridgePortletRequestFactory bridgePortletRequestFactory = (BridgePortletRequestFactory) BridgeFactoryFinder
			.getFactory(BridgePortletRequestFactory.class);

		return bridgePortletRequestFactory.getRenderRequest(renderRequest, renderResponse, portletConfig, bridgeConfig);
	}

	/**
	 * Returns a new instance of {@link ResourceRequest} from the {@link BridgePortletRequestFactory} found by the
	 * {@link FactoryExtensionFinder}. The returned instance is designed to be used during execution of a request
	 * thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static ResourceRequest getResourceRequestInstance(ResourceRequest resourceRequest,
		ResourceResponse resourceResponse, PortletConfig portletConfig, BridgeConfig bridgeConfig) {

		BridgePortletRequestFactory bridgePortletRequestFactory = (BridgePortletRequestFactory) BridgeFactoryFinder
			.getFactory(BridgePortletRequestFactory.class);

		return bridgePortletRequestFactory.getResourceRequest(resourceRequest, resourceResponse, portletConfig,
				bridgeConfig);
	}

	/**
	 * Returns a new instance of {@link ActionRequest}. The returned instance is designed to be used during execution of
	 * a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public abstract ActionRequest getActionRequest(ActionRequest actionRequest, ActionResponse actionResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig);

	/**
	 * Returns a new instance of {@link EventRequest}. The returned instance is designed to be used during execution of
	 * a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public abstract EventRequest getEventRequest(EventRequest eventRequest, EventResponse eventResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig);

	/**
	 * Returns a new instance of {@link RenderRequest}. The returned instance is designed to be used during execution of
	 * a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public abstract RenderRequest getRenderRequest(RenderRequest renderRequest, RenderResponse renderResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig);

	/**
	 * Returns a new instance of {@link ResourceRequest}. The returned instance is designed to be used during execution
	 * of a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public abstract ResourceRequest getResourceRequest(ResourceRequest resourceRequest,
		ResourceResponse resourceResponse, PortletConfig portletConfig, BridgeConfig bridgeConfig);

	/**
	 * Returns the wrapped factory instance if this factory has been decorated. Otherwise, this method returns null.
	 */
	@Override
	public abstract BridgePortletRequestFactory getWrapped();
}
