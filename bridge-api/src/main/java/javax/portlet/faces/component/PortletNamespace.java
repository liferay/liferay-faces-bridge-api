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
package jakarta.portlet.faces.component;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponentBase;

import org.osgi.annotation.versioning.ConsumerType;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = PortletNamespace.COMPONENT_TYPE)
@ConsumerType
public class PortletNamespace extends UIComponentBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "jakarta.portlet.faces.Namespace";
	public static final String COMPONENT_TYPE = "jakarta.portlet.faces.Namespace";

	// Protected Enumerations
	protected enum NamespacePropertyKeys {
		var
	}

	public PortletNamespace() {
		super();
		setRendererType("jakarta.portlet.faces.Namespace");
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	/**
	 * <p><code>var</code> attribute description:</p>
	 *
	 * <p>Introduces an EL variable that contains the portlet namespace.</p>
	 */
	public String getVar() {
		return (String) getStateHelper().eval(NamespacePropertyKeys.var, null);
	}

	/**
	 * <p><code>var</code> attribute description:</p>
	 *
	 * <p>Introduces an EL variable that contains the portlet namespace.</p>
	 */
	public void setVar(String var) {
		getStateHelper().put(NamespacePropertyKeys.var, var);
	}
}
