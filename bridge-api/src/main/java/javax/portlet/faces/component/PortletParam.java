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
@FacesComponent(value = PortletParam.COMPONENT_TYPE)
@ConsumerType
public class PortletParam extends UIComponentBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "javax.portlet.faces.URL";
	public static final String COMPONENT_TYPE = "javax.portlet.faces.Param";

	// Protected Enumerations
	protected enum ParamPropertyKeys {
		name, value, type
	}

	public PortletParam() {
		super();
		setRendererType("");
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	/**
	 * <p><code>name</code> attribute description:</p>
	 *
	 * <p>The name of the parameter.</p>
	 */
	public String getName() {
		return (String) getStateHelper().eval(ParamPropertyKeys.name, null);
	}

	/**
	 * <p><code>type</code> attribute description:</p>
	 *
	 * <p>The type of the parameter.</p>
	 */
	public String getType() {
		return (String) getStateHelper().eval(ParamPropertyKeys.type, null);
	}

	/**
	 * <p><code>value</code> attribute description:</p>
	 *
	 * <p>The value of the parameter.</p>
	 */
	public String getValue() {
		return (String) getStateHelper().eval(ParamPropertyKeys.value, null);
	}

	/**
	 * <p><code>name</code> attribute description:</p>
	 *
	 * <p>The name of the parameter.</p>
	 */
	public void setName(String name) {
		getStateHelper().put(ParamPropertyKeys.name, name);
	}

	/**
	 * <p><code>type</code> attribute description:</p>
	 *
	 * <p>The type of the parameter. When used with a surrounding <code>portlet:actionURL</code> the valid values are
	 * "action" (the default) and "render". When used with a surrounding <code>portlet:renderURL</code> or <code>
	 * portlet:resourceURL</code> this attribute is ignored.</p>
	 */
	public void setType(String type) {
		getStateHelper().put(ParamPropertyKeys.type, type);
	}

	/**
	 * <p><code>value</code> attribute description:</p>
	 *
	 * <p>The value of the parameter.</p>
	 */
	public void setValue(String value) {
		getStateHelper().put(ParamPropertyKeys.value, value);
	}
}
