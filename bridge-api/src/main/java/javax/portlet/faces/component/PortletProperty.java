/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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

import org.osgi.annotation.versioning.ProviderType;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = PortletProperty.COMPONENT_TYPE)
@ProviderType
public class PortletProperty extends UIComponentBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "javax.portlet.faces.URL";
	public static final String COMPONENT_TYPE = "javax.portlet.faces.Property";

	// Protected Enumerations
	protected enum PropertyPropertyKeys {
		name, value
	}

	public PortletProperty() {
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
	 * <p>The name of the portlet property.</p>
	 */
	public String getName() {
		return (String) getStateHelper().eval(PropertyPropertyKeys.name, null);
	}

	/**
	 * <p><code>value</code> attribute description:</p>
	 *
	 * <p>The value of the portlet property.</p>
	 */
	public String getValue() {
		return (String) getStateHelper().eval(PropertyPropertyKeys.value, null);
	}

	/**
	 * <p><code>name</code> attribute description:</p>
	 *
	 * <p>The name of the portlet property.</p>
	 */
	public void setName(String name) {
		getStateHelper().put(PropertyPropertyKeys.name, name);
	}

	/**
	 * <p><code>value</code> attribute description:</p>
	 *
	 * <p>The value of the portlet property.</p>
	 */
	public void setValue(String value) {
		getStateHelper().put(PropertyPropertyKeys.value, value);
	}
}
