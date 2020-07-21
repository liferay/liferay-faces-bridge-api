/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
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
import org.osgi.annotation.versioning.ProviderType;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
@ProviderType
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

	/**
	 * <p><code>escapeXml</code> attribute description:</p>
	 *
	 * <p>When true, xml special characters will be escaped. Defaults to true.</p>
	 */
	public boolean isEscapeXml() {
		return (Boolean) getStateHelper().eval(BaseURLPropertyKeys.escapeXml, true);
	}

	/**
	 * <p><code>escapeXml</code> attribute description:</p>
	 *
	 * <p>When true, xml special characters will be escaped. Defaults to true.</p>
	 */
	public void setEscapeXml(boolean escapeXml) {
		getStateHelper().put(BaseURLPropertyKeys.escapeXml, escapeXml);
	}

	/**
	 * <p><code>secure</code> attribute description:</p>
	 *
	 * <p>When true, the URL will be secure. Defaults to the security setting of the current request.</p>
	 */
	public Boolean getSecure() {
		return (Boolean) getStateHelper().eval(BaseURLPropertyKeys.secure, null);
	}

	/**
	 * <p><code>secure</code> attribute description:</p>
	 *
	 * <p>When true, the URL will be secure. Defaults to the security setting of the current request.</p>
	 */
	public void setSecure(Boolean secure) {
		getStateHelper().put(BaseURLPropertyKeys.secure, secure);
	}

	/**
	 * <p><code>var</code> attribute description:</p>
	 *
	 * <p>Introduces an EL variable that contains the URL.</p>
	 */
	public String getVar() {
		return (String) getStateHelper().eval(BaseURLPropertyKeys.var, null);
	}

	/**
	 * <p><code>var</code> attribute description:</p>
	 *
	 * <p>Introduces an EL variable that contains the URL.</p>
	 */
	public void setVar(String var) {
		getStateHelper().put(BaseURLPropertyKeys.var, var);
	}
}
//J+
