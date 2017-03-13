/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portlet.component.param;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIComponentBase;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ParamBase extends UIComponentBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "com.liferay.faces.portlet.component.param";
	public static final String COMPONENT_TYPE = "com.liferay.faces.portlet.component.param.Param";

	// Protected Enumerations
	protected enum ParamPropertyKeys {
		name,
		value
	}

	public ParamBase() {
		super();
		setRendererType("");
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	/**
	 * <code>name</code> attribute description:
	 * <br /><br />
	 * The name of the render parameter.
	 */
	public String getName() {
		return (String) getStateHelper().eval(ParamPropertyKeys.name, null);
	}

	/**
	 * <code>name</code> attribute description:
	 * <br /><br />
	 * The name of the render parameter.
	 */
	public void setName(String name) {
		getStateHelper().put(ParamPropertyKeys.name, name);
	}

	/**
	 * <code>value</code> attribute description:
	 * <br /><br />
	 * The value of the render parameter.
	 */
	public String getValue() {
		return (String) getStateHelper().eval(ParamPropertyKeys.value, null);
	}

	/**
	 * <code>value</code> attribute description:
	 * <br /><br />
	 * The value of the render parameter.
	 */
	public void setValue(String value) {
		getStateHelper().put(ParamPropertyKeys.value, value);
	}
}
//J+
