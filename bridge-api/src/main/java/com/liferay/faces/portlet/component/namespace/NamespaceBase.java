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
package com.liferay.faces.portlet.component.namespace;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIComponentBase;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class NamespaceBase extends UIComponentBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portlet.component.namespace.Namespace";
	public static final String RENDERER_TYPE = "com.liferay.faces.portlet.component.namespace.NamespaceRenderer";

	// Protected Enumerations
	protected enum NamespacePropertyKeys {
		var
	}

	public NamespaceBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getVar() {
		return (String) getStateHelper().eval(NamespacePropertyKeys.var, null);
	}

	public void setVar(String var) {
		getStateHelper().put(NamespacePropertyKeys.var, var);
	}
}
//J+
