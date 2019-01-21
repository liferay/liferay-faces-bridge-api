/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portlet.component.portleturl;
//J-

import javax.annotation.Generated;
import com.liferay.faces.portlet.component.baseurl.BaseURL;
import org.osgi.annotation.versioning.ProviderType;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
@ProviderType
public abstract class PortletURLBase extends BaseURL {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portlet.component.portleturl.PortletURL";
	public static final String RENDERER_TYPE = "com.liferay.faces.portlet.component.portleturl.PortletURLRenderer";

	// Protected Enumerations
	protected enum PortletURLPropertyKeys {
		copyCurrentRenderParameters,
		portletMode,
		windowState
	}

	public PortletURLBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	/**
	 * <p><code>copyCurrentRenderParameters</code> attribute description:</p>
	 *
	 * <p>When true, copy the current request's render parameters to the URL. Defaults to false.</p>
	 */
	public boolean isCopyCurrentRenderParameters() {
		return (Boolean) getStateHelper().eval(PortletURLPropertyKeys.copyCurrentRenderParameters, false);
	}

	/**
	 * <p><code>copyCurrentRenderParameters</code> attribute description:</p>
	 *
	 * <p>When true, copy the current request's render parameters to the URL. Defaults to false.</p>
	 */
	public void setCopyCurrentRenderParameters(boolean copyCurrentRenderParameters) {
		getStateHelper().put(PortletURLPropertyKeys.copyCurrentRenderParameters, copyCurrentRenderParameters);
	}

	/**
	 * <p><code>portletMode</code> attribute description:</p>
	 *
	 * <p>The name of the mode of the portlet which will be accessed via the URL.</p>
	 */
	public String getPortletMode() {
		return (String) getStateHelper().eval(PortletURLPropertyKeys.portletMode, null);
	}

	/**
	 * <p><code>portletMode</code> attribute description:</p>
	 *
	 * <p>The name of the mode of the portlet which will be accessed via the URL.</p>
	 */
	public void setPortletMode(String portletMode) {
		getStateHelper().put(PortletURLPropertyKeys.portletMode, portletMode);
	}

	/**
	 * <p><code>windowState</code> attribute description:</p>
	 *
	 * <p>The name of the window state of the portlet which will be accessed via the URL.</p>
	 */
	public String getWindowState() {
		return (String) getStateHelper().eval(PortletURLPropertyKeys.windowState, null);
	}

	/**
	 * <p><code>windowState</code> attribute description:</p>
	 *
	 * <p>The name of the window state of the portlet which will be accessed via the URL.</p>
	 */
	public void setWindowState(String windowState) {
		getStateHelper().put(PortletURLPropertyKeys.windowState, windowState);
	}
}
//J+
