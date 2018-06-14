/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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
package javax.portlet.faces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

import org.osgi.annotation.versioning.ProviderType;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = PortletRenderURL.COMPONENT_TYPE)
@ProviderType
public class PortletRenderURL extends UIComponentBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "javax.portlet.faces.URL";
	public static final String COMPONENT_TYPE = "javax.portlet.faces.RenderURL";

	// Protected Enumerations
	protected enum PropertyKeys {
		copyCurrentRenderParameters, escapeXml, portletMode, secure, var, windowState
	}

	public PortletRenderURL() {
		super();
		setRendererType("javax.portlet.faces.RenderURL");
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	/**
	 * <p><code>portletMode</code> attribute description:</p>
	 *
	 * <p>The name of the mode of the portlet which will be accessed via the URL.</p>
	 */
	public String getPortletMode() {
		return (String) getStateHelper().eval(PropertyKeys.portletMode, null);
	}

	/**
	 * <p><code>secure</code> attribute description:</p>
	 *
	 * <p>When true, the URL will be secure. Defaults to the security setting of the current request.</p>
	 */
	public Boolean getSecure() {
		return (Boolean) getStateHelper().eval(PropertyKeys.secure, null);
	}

	/**
	 * <p><code>var</code> attribute description:</p>
	 *
	 * <p>Introduces an EL variable that contains the URL.</p>
	 */
	public String getVar() {
		return (String) getStateHelper().eval(PropertyKeys.var, null);
	}

	/**
	 * <p><code>windowState</code> attribute description:</p>
	 *
	 * <p>The name of the window state of the portlet which will be accessed via the URL.</p>
	 */
	public String getWindowState() {
		return (String) getStateHelper().eval(PropertyKeys.windowState, null);
	}

	/**
	 * <p><code>copyCurrentRenderParameters</code> attribute description:</p>
	 *
	 * <p>When true, copy the current request's render parameters to the URL. Defaults to false.</p>
	 */
	public boolean isCopyCurrentRenderParameters() {
		return (Boolean) getStateHelper().eval(PropertyKeys.copyCurrentRenderParameters, false);
	}

	/**
	 * <p><code>escapeXml</code> attribute description:</p>
	 *
	 * <p>When true, xml special characters will be escaped. Defaults to true.</p>
	 */
	public boolean isEscapeXml() {
		return (Boolean) getStateHelper().eval(PropertyKeys.escapeXml, true);
	}

	/**
	 * <p><code>copyCurrentRenderParameters</code> attribute description:</p>
	 *
	 * <p>When true, copy the current request's render parameters to the URL. Defaults to false.</p>
	 */
	public void setCopyCurrentRenderParameters(boolean copyCurrentRenderParameters) {
		getStateHelper().put(PropertyKeys.copyCurrentRenderParameters, copyCurrentRenderParameters);
	}

	/**
	 * <p><code>escapeXml</code> attribute description:</p>
	 *
	 * <p>When true, xml special characters will be escaped. Defaults to true.</p>
	 */
	public void setEscapeXml(boolean escapeXml) {
		getStateHelper().put(PropertyKeys.escapeXml, escapeXml);
	}

	/**
	 * <p><code>portletMode</code> attribute description:</p>
	 *
	 * <p>The name of the mode of the portlet which will be accessed via the URL.</p>
	 */
	public void setPortletMode(String portletMode) {
		getStateHelper().put(PropertyKeys.portletMode, portletMode);
	}

	/**
	 * <p><code>secure</code> attribute description:</p>
	 *
	 * <p>When true, the URL will be secure. Defaults to the security setting of the current request.</p>
	 */
	public void setSecure(Boolean secure) {
		getStateHelper().put(PropertyKeys.secure, secure);
	}

	/**
	 * <p><code>var</code> attribute description:</p>
	 *
	 * <p>Introduces an EL variable that contains the URL.</p>
	 */
	public void setVar(String var) {
		getStateHelper().put(PropertyKeys.var, var);
	}

	/**
	 * <p><code>windowState</code> attribute description:</p>
	 *
	 * <p>The name of the window state of the portlet which will be accessed via the URL.</p>
	 */
	public void setWindowState(String windowState) {
		getStateHelper().put(PropertyKeys.windowState, windowState);
	}
}
