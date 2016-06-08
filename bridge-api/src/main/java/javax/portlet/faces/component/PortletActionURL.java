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


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = PortletActionURL.COMPONENT_TYPE)
public class PortletActionURL extends PortletBaseURL {

	// Public Constants
	public static final String COMPONENT_TYPE = "javax.portlet.faces.ActionURL";

	// Protected Enumerations
	protected enum ActionURLPropertyKeys {
		copyCurrentRenderParameters, name, portletMode, windowState
	}

	public PortletActionURL() {
		super();
		setRendererType("javax.portlet.faces.ActionURL");
	}

	/**
	 * <code>name</code> attribute description:<br />
	 * <br />
	 * The name of the action method to be executed.
	 */
	public String getName() {
		return (String) getStateHelper().eval(ActionURLPropertyKeys.name, null);
	}

	/**
	 * <code>portletMode</code> attribute description:<br />
	 * <br />
	 * The name of the mode of the portlet which will be accessed via the URL.
	 */
	public String getPortletMode() {
		return (String) getStateHelper().eval(ActionURLPropertyKeys.portletMode, null);
	}

	/**
	 * <code>windowState</code> attribute description:<br />
	 * <br />
	 * The name of the window state of the portlet which will be accessed via the URL.
	 */
	public String getWindowState() {
		return (String) getStateHelper().eval(ActionURLPropertyKeys.windowState, null);
	}

	/**
	 * <code>copyCurrentRenderParameters</code> attribute description:<br />
	 * <br />
	 * When true, copy the current request's render parameters to the URL. Defaults to false.
	 */
	public boolean isCopyCurrentRenderParameters() {
		return (Boolean) getStateHelper().eval(ActionURLPropertyKeys.copyCurrentRenderParameters, false);
	}

	/**
	 * <code>copyCurrentRenderParameters</code> attribute description:<br />
	 * <br />
	 * When true, copy the current request's render parameters to the URL. Defaults to false.
	 */
	public void setCopyCurrentRenderParameters(boolean copyCurrentRenderParameters) {
		getStateHelper().put(ActionURLPropertyKeys.copyCurrentRenderParameters, copyCurrentRenderParameters);
	}

	/**
	 * <code>name</code> attribute description:<br />
	 * <br />
	 * The name of the action method to be executed.
	 */
	public void setName(String name) {
		getStateHelper().put(ActionURLPropertyKeys.name, name);
	}

	/**
	 * <code>portletMode</code> attribute description:<br />
	 * <br />
	 * The name of the mode of the portlet which will be accessed via the URL.
	 */
	public void setPortletMode(String portletMode) {
		getStateHelper().put(ActionURLPropertyKeys.portletMode, portletMode);
	}

	/**
	 * <code>windowState</code> attribute description:<br />
	 * <br />
	 * The name of the window state of the portlet which will be accessed via the URL.
	 */
	public void setWindowState(String windowState) {
		getStateHelper().put(ActionURLPropertyKeys.windowState, windowState);
	}
}
