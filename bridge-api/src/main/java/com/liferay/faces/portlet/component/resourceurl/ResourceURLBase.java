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
package com.liferay.faces.portlet.component.resourceurl;
//J-

import javax.annotation.Generated;
import com.liferay.faces.portlet.component.baseurl.BaseURL;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ResourceURLBase extends BaseURL {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portlet.component.resourceurl.ResourceURL";
	public static final String RENDERER_TYPE = "com.liferay.faces.portlet.component.resourceurl.ResourceURLRenderer";

	// Protected Enumerations
	protected enum ResourceURLPropertyKeys {
		cacheability,
		id
	}

	public ResourceURLBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	/**
	 * <code>cacheability</code> attribute description:
	 * <br /><br />
	 * The cacheability of the resource returned by the resourceURL. Valid values include <code>ResourceURL.FULL</code>, <code>ResourceURL.PAGE</code>, and <code>ResourceURL.PORTLET</code>. Defaults to <code>ResourceURL.PAGE</code>.
	 */
	public String getCacheability() {
		return (String) getStateHelper().eval(ResourceURLPropertyKeys.cacheability, javax.portlet.ResourceURL.PAGE);
	}

	/**
	 * <code>cacheability</code> attribute description:
	 * <br /><br />
	 * The cacheability of the resource returned by the resourceURL. Valid values include <code>ResourceURL.FULL</code>, <code>ResourceURL.PAGE</code>, and <code>ResourceURL.PORTLET</code>. Defaults to <code>ResourceURL.PAGE</code>.
	 */
	public void setCacheability(String cacheability) {
		getStateHelper().put(ResourceURLPropertyKeys.cacheability, cacheability);
	}

	/**
	 * <code>id</code> attribute description:
	 * <br /><br />
	 * The id for the resource.
	 */
	@Override
	public String getId() {
		return (String) getStateHelper().eval(ResourceURLPropertyKeys.id, null);
	}

	/**
	 * <code>id</code> attribute description:
	 * <br /><br />
	 * The id for the resource.
	 */
	@Override
	public void setId(String id) {
		getStateHelper().put(ResourceURLPropertyKeys.id, id);
	}
}
//J+
