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
import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class BridgePortletResponseFactory implements Wrapper<BridgePortletResponseFactory> {

	/**
	 * Returns a new instance of {@link ActionResponse} from the {@link BridgePortletResponseFactory} found by the
	 * {@link FactoryExtensionFinder}. The returned instance is designed to be used during execution of a request
	 * thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static ActionResponse getActionResponseInstance(ActionRequest actionRequest, ActionResponse actionResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig) {

		BridgePortletResponseFactory bridgePortletResponseFactory = (BridgePortletResponseFactory) BridgeFactoryFinder
			.getFactory(BridgePortletResponseFactory.class);

		return bridgePortletResponseFactory.getActionResponse(actionRequest, actionResponse, portletConfig,
				bridgeConfig);
	}

	/**
	 * Returns a new instance of {@link EventResponse} from the {@link BridgePortletResponseFactory} found by the {@link
	 * FactoryExtensionFinder}. The returned instance is designed to be used during execution of a request thread, so it
	 * is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static EventResponse getEventResponseInstance(EventRequest eventRequest, EventResponse eventResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig) {

		BridgePortletResponseFactory bridgePortletResponseFactory = (BridgePortletResponseFactory) BridgeFactoryFinder
			.getFactory(BridgePortletResponseFactory.class);

		return bridgePortletResponseFactory.getEventResponse(eventRequest, eventResponse, portletConfig, bridgeConfig);
	}

	/**
	 * Returns a new instance of {@link RenderResponse} from the {@link BridgePortletResponseFactory} found by the
	 * {@link FactoryExtensionFinder}. The returned instance is designed to be used during execution of a request
	 * thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static RenderResponse getRenderResponseInstance(RenderRequest renderRequest, RenderResponse renderResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig) {

		BridgePortletResponseFactory bridgePortletResponseFactory = (BridgePortletResponseFactory) BridgeFactoryFinder
			.getFactory(BridgePortletResponseFactory.class);

		return bridgePortletResponseFactory.getRenderResponse(renderRequest, renderResponse, portletConfig,
				bridgeConfig);
	}

	/**
	 * Returns a new instance of {@link ResourceResponse} from the {@link BridgePortletResponseFactory} found by the
	 * {@link FactoryExtensionFinder}. The returned instance is designed to be used during execution of a request
	 * thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static ResourceResponse getResourceResponseInstance(ResourceRequest resourceRequest,
		ResourceResponse resourceResponse, PortletConfig portletConfig, BridgeConfig bridgeConfig) {

		BridgePortletResponseFactory bridgePortletResponseFactory = (BridgePortletResponseFactory) BridgeFactoryFinder
			.getFactory(BridgePortletResponseFactory.class);

		return bridgePortletResponseFactory.getResourceResponse(resourceRequest, resourceResponse, portletConfig,
				bridgeConfig);
	}

	/**
	 * Returns a new instance of {@link ActionResponse}. The returned instance is designed to be used during execution
	 * of a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public abstract ActionResponse getActionResponse(ActionRequest actionRequest, ActionResponse actionResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig);

	/**
	 * Returns a new instance of {@link EventResponse}. The returned instance is designed to be used during execution of
	 * a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public abstract EventResponse getEventResponse(EventRequest eventRequest, EventResponse eventResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig);

	/**
	 * Returns a new instance of {@link RenderResponse}. The returned instance is designed to be used during execution
	 * of a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public abstract RenderResponse getRenderResponse(RenderRequest renderRequest, RenderResponse renderResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig);

	/**
	 * Returns a new instance of {@link ResourceResponse}. The returned instance is designed to be used during execution
	 * of a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public abstract ResourceResponse getResourceResponse(ResourceRequest resourceRequest,
		ResourceResponse resourceResponse, PortletConfig portletConfig, BridgeConfig bridgeConfig);

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 */
	@Override
	public abstract BridgePortletResponseFactory getWrapped();
}
