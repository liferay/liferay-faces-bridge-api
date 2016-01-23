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
package com.liferay.faces.portlet.component.renderurl;
//J-

import javax.annotation.Generated;
import com.liferay.faces.portlet.component.baseurl.BaseURL;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class RenderURLBase extends BaseURL {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portlet.component.renderurl.RenderURL";
	public static final String RENDERER_TYPE = "com.liferay.faces.portlet.component.renderurl.RenderURLRenderer";

	// Protected Enumerations
	protected enum RenderURLPropertyKeys {
		copyCurrentRenderParameters,
		portletMode,
		windowState
	}

	public RenderURLBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	/**
	 * <code>copyCurrentRenderParameters</code> attribute description:
	 * <br /><br />
	 * When true, copy the current request's render parameters to the URL. Defaults to false.
	 */
	public boolean isCopyCurrentRenderParameters() {
		return (Boolean) getStateHelper().eval(RenderURLPropertyKeys.copyCurrentRenderParameters, false);
	}

	/**
	 * <code>copyCurrentRenderParameters</code> attribute description:
	 * <br /><br />
	 * When true, copy the current request's render parameters to the URL. Defaults to false.
	 */
	public void setCopyCurrentRenderParameters(boolean copyCurrentRenderParameters) {
		getStateHelper().put(RenderURLPropertyKeys.copyCurrentRenderParameters, copyCurrentRenderParameters);
	}

	/**
	 * <code>portletMode</code> attribute description:
	 * <br /><br />
	 * The name of the mode of the portlet which will be accessed via the URL.
	 */
	public String getPortletMode() {
		return (String) getStateHelper().eval(RenderURLPropertyKeys.portletMode, null);
	}

	/**
	 * <code>portletMode</code> attribute description:
	 * <br /><br />
	 * The name of the mode of the portlet which will be accessed via the URL.
	 */
	public void setPortletMode(String portletMode) {
		getStateHelper().put(RenderURLPropertyKeys.portletMode, portletMode);
	}

	/**
	 * <code>windowState</code> attribute description:
	 * <br /><br />
	 * The name of the window state of the portlet which will be accessed via the URL.
	 */
	public String getWindowState() {
		return (String) getStateHelper().eval(RenderURLPropertyKeys.windowState, null);
	}

	/**
	 * <code>windowState</code> attribute description:
	 * <br /><br />
	 * The name of the window state of the portlet which will be accessed via the URL.
	 */
	public void setWindowState(String windowState) {
		getStateHelper().put(RenderURLPropertyKeys.windowState, windowState);
	}
}
//J+
