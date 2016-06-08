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

import javax.faces.component.UIComponentBase;


/**
 * @author  Neil Griffin
 */
public abstract class PortletBaseURL extends UIComponentBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "javax.portlet.faces.URL";

	// Protected Enumerations
	protected enum BaseURLPropertyKeys {
		escapeXml, secure, var
	}

	public PortletBaseURL() {
		super();
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	/**
	 * <code>secure</code> attribute description:<br />
	 * <br />
	 * When true, the URL will be secure. Defaults to the security setting of the current request.
	 */
	public Boolean getSecure() {
		return (Boolean) getStateHelper().eval(BaseURLPropertyKeys.secure, null);
	}

	/**
	 * <code>var</code> attribute description:<br />
	 * <br />
	 * Introduces an EL variable that contains the URL.
	 */
	public String getVar() {
		return (String) getStateHelper().eval(BaseURLPropertyKeys.var, null);
	}

	/**
	 * <code>escapeXml</code> attribute description:<br />
	 * <br />
	 * When true, xml special characters will be escaped. Defaults to true.
	 */
	public boolean isEscapeXml() {
		return (Boolean) getStateHelper().eval(BaseURLPropertyKeys.escapeXml, true);
	}

	/**
	 * <code>escapeXml</code> attribute description:<br />
	 * <br />
	 * When true, xml special characters will be escaped. Defaults to true.
	 */
	public void setEscapeXml(boolean escapeXml) {
		getStateHelper().put(BaseURLPropertyKeys.escapeXml, escapeXml);
	}

	/**
	 * <code>secure</code> attribute description:<br />
	 * <br />
	 * When true, the URL will be secure. Defaults to the security setting of the current request.
	 */
	public void setSecure(Boolean secure) {
		getStateHelper().put(BaseURLPropertyKeys.secure, secure);
	}

	/**
	 * <code>var</code> attribute description:<br />
	 * <br />
	 * Introduces an EL variable that contains the URL.
	 */
	public void setVar(String var) {
		getStateHelper().put(BaseURLPropertyKeys.var, var);
	}
}
