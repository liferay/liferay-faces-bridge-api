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
package com.liferay.faces.portlet.component.property;

// JSF 2: import javax.faces.component.FacesComponent;

import javax.faces.context.FacesContext;

import com.liferay.faces.util.component.ComponentStateHelper;
import com.liferay.faces.util.component.StateHelper;


/**
 * @author  Neil Griffin
 */
// JSF 2: @FacesComponent(value = Property.COMPONENT_TYPE)
public class Property extends PropertyBase {

	// Private Data Members
	private StateHelper stateHelper;

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	@Override
	public StateHelper getStateHelper() {

		if (stateHelper == null) {
			stateHelper = new ComponentStateHelper(this);
		}

		return stateHelper;
	}

	@Override
	public void restoreState(FacesContext facesContext, Object state) {

		Object[] values = (Object[]) state;
		super.restoreState(facesContext, values[0]);
		getStateHelper().restoreState(facesContext, values[1]);
	}

	@Override
	public Object saveState(FacesContext facesContext) {

		Object[] values = new Object[2];
		values[0] = super.saveState(facesContext);
		values[1] = getStateHelper().saveState(facesContext);

		return values;
	}
}
