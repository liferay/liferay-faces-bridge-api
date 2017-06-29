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

import javax.portlet.faces.Bridge;


/**
 * This interface represents a bridge URL, meaning a URL that has convenience methods for representing URLs according to
 * Section 6.1.3.1 of the Bridge Spec.
 *
 * @see     Bridge#DIRECT_LINK
 * @see     Bridge#FACES_AJAX_PARAMETER
 * @see     Bridge#FACES_PARTIAL_PARAMETER
 * @see     Bridge#FACES_VIEW_ID_PARAMETER
 * @see     Bridge#FACES_VIEW_PATH_PARAMETER
 * @see     Bridge#NONFACES_TARGET_PATH_PARAMETER
 * @see     Bridge#PORTLET_MODE_PARAMETER
 * @see     Bridge#PORTLET_WINDOWSTATE_PARAMETER
 * @see     Bridge#PORTLET_SECURE_PARAMETER
 * @author  Neil Griffin
 */
public interface BridgeURL {

	/**
	 * Returns the first value of the underlying {@link BridgeURL#getParameterMap()} with the specified <code>
	 * name</code>.
	 */
	public String getParameter(String name);

	/**
	 * Returns a mutable {@link Map} representing the URL parameters.
	 */
	public Map<String, String[]> getParameterMap();

	/**
	 * If the URL targets a Faces view, then returns the viewId. Otherwise, returns null.
	 *
	 * @return  if the URL targets a Faces View, returns the viewId. Otherwise <code>null</code>.
	 */
	public String getViewId();

	/**
	 * Removes the entry of the underlying {@link BridgeURL#getParameterMap()} according to the specified <code>
	 * name</code>.
	 *
	 * @return  the first value of the underlying {@link BridgeURL#getParameterMap()} with the specified <code>
	 *          name</code>.
	 */
	public String removeParameter(String name);

	/**
	 * Sets the <code>value</code> of the underlying {@link BridgeURL#getParameterMap()} according to the specified
	 * <code>name</code>.
	 */
	public void setParameter(String name, String value);

	/**
	 * Sets the <code>value</code> of the underlying {@link BridgeURL#getParameterMap()} according to the specified
	 * <code>name</code>.
	 */
	public void setParameter(String name, String[] value);

	/**
	 * Returns a string-based representation of the URL.
	 */
	@Override
	public String toString();
}
