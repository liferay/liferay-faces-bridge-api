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
package javax.portlet.faces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = PortletActionURL.COMPONENT_TYPE)
public class PortletActionURL extends UIComponentBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "javax.portlet.faces.URL";
	public static final String COMPONENT_TYPE = "javax.portlet.faces.ActionURL";

	// Protected Enumerations
	protected enum PropertyKeys {
		copyCurrentRenderParameters, escapeXml, name, portletMode, secure, var, windowState
	}

	public PortletActionURL() {
		super();
		setRendererType("javax.portlet.faces.ActionURL");
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	/**
	 * <code>name</code> attribute description:<br />
	 * <br />
	 * The name of the action method to be executed.
	 */
	public String getName() {
		return (String) getStateHelper().eval(PropertyKeys.name, null);
	}

	/**
	 * <code>portletMode</code> attribute description:<br />
	 * <br />
	 * The name of the mode of the portlet which will be accessed via the URL.
	 */
	public String getPortletMode() {
		return (String) getStateHelper().eval(PropertyKeys.portletMode, null);
	}

	/**
	 * <code>secure</code> attribute description:<br />
	 * <br />
	 * When true, the URL will be secure. Defaults to the security setting of the current request.
	 */
	public Boolean getSecure() {
		return (Boolean) getStateHelper().eval(PropertyKeys.secure, null);
	}

	/**
	 * <code>var</code> attribute description:<br />
	 * <br />
	 * Introduces an EL variable that contains the URL.
	 */
	public String getVar() {
		return (String) getStateHelper().eval(PropertyKeys.var, null);
	}

	/**
	 * <code>windowState</code> attribute description:<br />
	 * <br />
	 * The name of the window state of the portlet which will be accessed via the URL.
	 */
	public String getWindowState() {
		return (String) getStateHelper().eval(PropertyKeys.windowState, null);
	}

	/**
	 * <code>copyCurrentRenderParameters</code> attribute description:<br />
	 * <br />
	 * When true, copy the current request's render parameters to the URL. Defaults to false.
	 */
	public boolean isCopyCurrentRenderParameters() {
		return (Boolean) getStateHelper().eval(PropertyKeys.copyCurrentRenderParameters, false);
	}

	/**
	 * <code>escapeXml</code> attribute description:<br />
	 * <br />
	 * When true, xml special characters will be escaped. Defaults to true.
	 */
	public boolean isEscapeXml() {
		return (Boolean) getStateHelper().eval(PropertyKeys.escapeXml, true);
	}

	/**
	 * <code>copyCurrentRenderParameters</code> attribute description:<br />
	 * <br />
	 * When true, copy the current request's render parameters to the URL. Defaults to false.
	 */
	public void setCopyCurrentRenderParameters(boolean copyCurrentRenderParameters) {
		getStateHelper().put(PropertyKeys.copyCurrentRenderParameters, copyCurrentRenderParameters);
	}

	/**
	 * <code>escapeXml</code> attribute description:<br />
	 * <br />
	 * When true, xml special characters will be escaped. Defaults to true.
	 */
	public void setEscapeXml(boolean escapeXml) {
		getStateHelper().put(PropertyKeys.escapeXml, escapeXml);
	}

	/**
	 * <code>name</code> attribute description:<br />
	 * <br />
	 * The name of the action method to be executed.
	 */
	public void setName(String name) {
		getStateHelper().put(PropertyKeys.name, name);
	}

	/**
	 * <code>portletMode</code> attribute description:<br />
	 * <br />
	 * The name of the mode of the portlet which will be accessed via the URL.
	 */
	public void setPortletMode(String portletMode) {
		getStateHelper().put(PropertyKeys.portletMode, portletMode);
	}

	/**
	 * <code>secure</code> attribute description:<br />
	 * <br />
	 * When true, the URL will be secure. Defaults to the security setting of the current request.
	 */
	public void setSecure(Boolean secure) {
		getStateHelper().put(PropertyKeys.secure, secure);
	}

	/**
	 * <code>var</code> attribute description:<br />
	 * <br />
	 * Introduces an EL variable that contains the URL.
	 */
	public void setVar(String var) {
		getStateHelper().put(PropertyKeys.var, var);
	}

	/**
	 * <code>windowState</code> attribute description:<br />
	 * <br />
	 * The name of the window state of the portlet which will be accessed via the URL.
	 */
	public void setWindowState(String windowState) {
		getStateHelper().put(PropertyKeys.windowState, windowState);
	}
}
