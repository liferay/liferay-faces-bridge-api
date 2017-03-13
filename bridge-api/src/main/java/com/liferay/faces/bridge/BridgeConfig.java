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
package com.liferay.faces.bridge;

import java.util.Map;
import java.util.Set;


/**
 * @author  Neil Griffin
 */
public interface BridgeConfig {

	/**
	 * Returns an immutable {@link Map} of bridge configuration attributes.
	 */
	public Map<String, Object> getAttributes();

	/**
	 * Returns an immutable {@link Set} of attribute names that are to be excluded from the bridge request scope as
	 * defined in the bridge:excluded-attributes element within the faces-config.xml descriptor.
	 */
	public Set<String> getExcludedRequestAttributes();

	/**
	 * Returns an immutable {@link Map} of Public Render Parameter mappings as defined in the
	 * bridge:public-parameter-mappings element of the faces-config.xml descriptor.
	 */
	public Map<String, String[]> getPublicParameterMappings();

	/**
	 * Returns the parameter name used for storing the value of the target viewId in a {@link
	 * javax.portlet.RenderRequest}.
	 */
	public String getViewIdRenderParameterName();

	/**
	 * Returns the parameter name used for storing the value of the target viewId in a {@link
	 * javax.portlet.ResourceRequest}.
	 */
	public String getViewIdResourceParameterName();

}
