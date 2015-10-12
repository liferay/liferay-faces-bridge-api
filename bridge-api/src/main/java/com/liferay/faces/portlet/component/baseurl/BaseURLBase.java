/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portlet.component.baseurl;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIComponentBase;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class BaseURLBase extends UIComponentBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "com.liferay.faces.portlet.component.baseurl";
	public static final String COMPONENT_TYPE = "com.liferay.faces.portlet.component.baseurl.BaseURL";
	public static final String RENDERER_TYPE = "com.liferay.faces.portlet.component.baseurl.BaseURLRenderer";

	// Protected Enumerations
	protected enum BaseURLPropertyKeys {
		escapeXml,
		secure,
		var
	}

	public BaseURLBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public boolean isEscapeXml() {
		return (Boolean) getStateHelper().eval(BaseURLPropertyKeys.escapeXml, true);
	}

	public void setEscapeXml(boolean escapeXml) {
		getStateHelper().put(BaseURLPropertyKeys.escapeXml, escapeXml);
	}

	public Boolean getSecure() {
		return (Boolean) getStateHelper().eval(BaseURLPropertyKeys.secure, null);
	}

	public void setSecure(Boolean secure) {
		getStateHelper().put(BaseURLPropertyKeys.secure, secure);
	}

	public String getVar() {
		return (String) getStateHelper().eval(BaseURLPropertyKeys.var, null);
	}

	public void setVar(String var) {
		getStateHelper().put(BaseURLPropertyKeys.var, var);
	}

	public abstract com.liferay.faces.util.component.StateHelper getStateHelper();
}
//J+
