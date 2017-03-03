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
package com.liferay.faces.bridge.context;

/**
 * @author  Neil Griffin
 */
public final class BridgePortalContext {

	/**
	 * Property indicating if the portal application supports adding elements to the &lt;head&gt;...&lt;/head&gt;
	 * section of the rendered portal page. A non-null value indicates that the portal application provides support.
	 */
	public static final String ADD_ELEMENT_TO_HEAD_SUPPORT =
		"com.liferay.faces.bridge.add.element.to.head.support";

	/**
	 * Property indicating if the portal application supports adding a &lt;script src=".." /&gt; resource to the
	 * &lt;head&gt;...&lt;/head&gt; section of the rendered portal page. A non-null value indicates that the portal
	 * application provides support.
	 */
	public static final String ADD_SCRIPT_RESOURCE_TO_HEAD_SUPPORT =
		"com.liferay.faces.bridge.add.script.resource.to.head.support";

	/**
	 * Property indicating if the portal application supports adding &lt;script&gt; text to the
	 * &lt;head&gt;...&lt;/head&gt; section of the rendered portal page. A non-null value indicates that the portal
	 * application provides support.
	 */
	public static final String ADD_SCRIPT_TEXT_TO_HEAD_SUPPORT =
		"com.liferay.faces.bridge.add.script.text.to.head.support";

	/**
	 * Property indicating if the portal application supports adding stylesheet &lt;link&gt; tags to the
	 * &lt;head&gt;...&lt;/head&gt; section of the rendered portal page. A non-null value indicates that the portal
	 * application provides support.
	 */
	public static final String ADD_STYLE_SHEET_RESOURCE_TO_HEAD_SUPPORT =
		"com.liferay.faces.bridge.add.style.sheet.resource.to.head.support";

	/**
	 * Property indicating if the portal application supports adding &lt;style&gt; text to the
	 * &lt;head&gt;...&lt;/head&gt; section of the rendered portal page. A non-null value indicates that the portal
	 * application provides support.
	 */
	public static final String ADD_STYLE_SHEET_TEXT_TO_HEAD_SUPPORT =
		"com.liferay.faces.bridge.add.style.sheet.text.to.head.support";

	/**
	 * Property indicating if the portal application supports/implements the POST-REDIRECT-GET design pattern, meaning
	 * that the {@link javax.portlet.PortletRequest#ACTION_PHASE} originates from an HTTP POST request, and the {@link
	 * javax.portlet.PortletRequest#RENDER_PHASE} is caused by a subsequent HTTP GET request. A non-null value indicates
	 * that the portal application provides support.
	 */
	public static final String POST_REDIRECT_GET_SUPPORT = "com.liferay.faces.bridge.post.redirect.get.support";

	/**
	 * Property indicating if the portal application supports the standard mechanism of setting the {@link
	 * javax.portlet.ResourceResponse#HTTP_STATUS_CODE} property on the {@link javax.portlet.ResourceResponse}. A
	 * non-null value indicates that the portal application provides support. A non-null value indicates that the portal
	 * application provides support.
	 */
	public static final String SET_HTTP_STATUS_CODE_SUPPORT = "com.liferay.faces.bridge.set.http.status.code.support";

	/**
	 * Property indicating if the portal application supports setting the buffer size on its {@link
	 * javax.portlet.ResourceResponse} implementation. A non-null value indicates that the portal application provides
	 * support.
	 */
	public static final String SET_RESOURCE_RESPONSE_BUFFER_SIZE_SUPPORT =
		"com.liferay.faces.bridge.set.resource.response.buffer.size.support";

	/**
	 * Property indicating if the portal application supports creation of a render URL during the {@link
	 * javax.portlet.PortletRequest#ACTION_PHASE} of the portlet lifecycle.
	 */
	public static final String CREATE_RENDER_URL_DURING_ACTION_PHASE_SUPPORT =
		"com.liferay.faces.bridge.create.render.url.during.action.phase.support";

	/**
	 * Property indicating if the portal application requires parameters to be namespaced. A non-null value indicates
	 * that the portal application provides support.
	 */
	public static final String STRICT_NAMESPACED_PARAMETERS_SUPPORT =
		"com.liferay.faces.bridge.strict.namespaced.paramters.support";

	private BridgePortalContext() {
		throw new AssertionError();
	}
}
