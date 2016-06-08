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
@FacesComponent(value = PortletParam.COMPONENT_TYPE)
public class PortletParam extends UIComponentBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "javax.portlet.faces.URL";
	public static final String COMPONENT_TYPE = "javax.portlet.faces.Param";

	// Protected Enumerations
	protected enum ParamPropertyKeys {
		name, value
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
	 * <code>name</code> attribute description:<br />
	 * <br />
	 * The name of the render parameter.
	 */
	public String getName() {
		return (String) getStateHelper().eval(ParamPropertyKeys.name, null);
	}

	/**
	 * <code>value</code> attribute description:<br />
	 * <br />
	 * The value of the render parameter.
	 */
	public String getValue() {
		return (String) getStateHelper().eval(ParamPropertyKeys.value, null);
	}

	/**
	 * <code>name</code> attribute description:<br />
	 * <br />
	 * The name of the render parameter.
	 */
	public void setName(String name) {
		getStateHelper().put(ParamPropertyKeys.name, name);
	}

	/**
	 * <code>value</code> attribute description:<br />
	 * <br />
	 * The value of the render parameter.
	 */
	public void setValue(String value) {
		getStateHelper().put(ParamPropertyKeys.value, value);
	}
}
