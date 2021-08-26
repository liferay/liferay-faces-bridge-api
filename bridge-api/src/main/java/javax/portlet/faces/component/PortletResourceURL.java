/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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

import org.osgi.annotation.versioning.ConsumerType;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = PortletResourceURL.COMPONENT_TYPE)
@ConsumerType
public class PortletResourceURL extends UIComponentBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "javax.portlet.faces.URL";
	public static final String COMPONENT_TYPE = "javax.portlet.faces.ResourceURL";

	// Protected Enumerations
	protected enum PropertyKeys {
		cacheability, escapeXml, id, secure, var
	}

	public PortletResourceURL() {
		super();
		setRendererType("javax.portlet.faces.ResourceURL");
	}

	/**
	 * <p><code>cacheability</code> attribute description:</p>
	 *
	 * <p>The cacheability of the resource returned by the resourceURL. Valid values include <code>
	 * ResourceURL.FULL</code>, <code>ResourceURL.PAGE</code>, and <code>ResourceURL.PORTLET</code>. Defaults to <code>
	 * ResourceURL.PAGE</code>.</p>
	 */
	public String getCacheability() {
		return (String) getStateHelper().eval(PropertyKeys.cacheability, javax.portlet.ResourceURL.PAGE);
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	/**
	 * <p><code>id</code> attribute description:</p>
	 *
	 * <p>The id for the resource.</p>
	 */
	@Override
	public String getId() {
		return (String) getStateHelper().eval(PropertyKeys.id, null);
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
	 * <p><code>escapeXml</code> attribute description:</p>
	 *
	 * <p>When true, xml special characters will be escaped. Defaults to true.</p>
	 */
	public boolean isEscapeXml() {
		return (Boolean) getStateHelper().eval(PropertyKeys.escapeXml, true);
	}

	/**
	 * <p><code>cacheability</code> attribute description:</p>
	 *
	 * <p>The cacheability of the resource returned by the resourceURL. Valid values include <code>
	 * ResourceURL.FULL</code>, <code>ResourceURL.PAGE</code>, and <code>ResourceURL.PORTLET</code>. Defaults to <code>
	 * ResourceURL.PAGE</code>.</p>
	 */
	public void setCacheability(String cacheability) {
		getStateHelper().put(PropertyKeys.cacheability, cacheability);
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
	 * <p><code>id</code> attribute description:</p>
	 *
	 * <p>The id for the resource.</p>
	 */
	@Override
	public void setId(String id) {
		getStateHelper().put(PropertyKeys.id, id);
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
}
