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
package com.liferay.faces.portlet.component.actionurl;
//J-

import javax.annotation.Generated;
import com.liferay.faces.portlet.component.renderurl.RenderURLBase;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ActionURLBase extends RenderURLBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portlet.component.actionurl.ActionURL";
	public static final String RENDERER_TYPE = "com.liferay.faces.portlet.component.actionurl.ActionURLRenderer";

	// Protected Enumerations
	protected enum ActionURLPropertyKeys {
		name
	}

	public ActionURLBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getName() {
		return (String) getStateHelper().eval(ActionURLPropertyKeys.name, null);
	}

	public void setName(String name) {
		getStateHelper().put(ActionURLPropertyKeys.name, name);
	}
}
//J+
