/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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
import org.osgi.annotation.versioning.ProviderType;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
@ProviderType
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
	 * <p><code>name</code> attribute description:</p>
	 *
	 * <p>The name of the render parameter.</p>
	 */
	public String getName() {
		return (String) getStateHelper().eval(ParamPropertyKeys.name, null);
	}

	/**
	 * <p><code>name</code> attribute description:</p>
	 *
	 * <p>The name of the render parameter.</p>
	 */
	public void setName(String name) {
		getStateHelper().put(ParamPropertyKeys.name, name);
	}

	/**
	 * <p><code>value</code> attribute description:</p>
	 *
	 * <p>The value of the render parameter.</p>
	 */
	public String getValue() {
		return (String) getStateHelper().eval(ParamPropertyKeys.value, null);
	}

	/**
	 * <p><code>value</code> attribute description:</p>
	 *
	 * <p>The value of the render parameter.</p>
	 */
	public void setValue(String value) {
		getStateHelper().put(ParamPropertyKeys.value, value);
	}
}
//J+
