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
package com.liferay.faces.bridge.context.url;

import java.net.MalformedURLException;
import java.util.Map;

import javax.portlet.BaseURL;
import javax.portlet.PortletURL;


/**
 * This interface represents a bridge URL, meaning a URL that has convenience methods for representing URLs according to
 * Section 6.1.3.1 of the Bridge Spec.
 *
 * @author  Neil Griffin
 */
public interface BridgeURL {

	/**
	 * Converts the bridge URL to an instance of the {@link BaseURL} type.
	 *
	 * @return  The converted bridge URL.
	 */
	public BaseURL toBaseURL() throws MalformedURLException;

	/**
	 * Returns a string-based representation of the URL.
	 */
	@Override
	public String toString();

	/**
	 * Flag indicating whether or not the URL is secure. For more information, see {@link
	 * PortletURL#setSecure(boolean)}.
	 *
	 * @return  <code>true</code> if the URL is secure, otherwise <code>false</code>.
	 */
	public boolean isSecure();

	/**
	 * If the URL targets a Faces view, then returns the viewId. Otherwise, returns null.
	 *
	 * @return  if the URL targets a Faces View, returns the viewId. Otherwise <code>null</code>.
	 */
	public String getFacesViewTarget();

	/**
	 * Determines whether or not the URL is self-referencing, meaning, it targets the current Faces view.
	 *
	 * @return  <code>true</code> if self-referencing, otherwise <code>false</code>.
	 */
	public boolean isSelfReferencing();

	/**
	 * Returns the first value of the underlying {@link BridgeURL#getParameterMap()} with the specified <code>
	 * name</code>.
	 */
	public String getParameter(String name);

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
	 * Returns an mutable {@link Map} representing the URL parameters.
	 */
	public Map<String, String[]> getParameterMap();

	/**
	 * Sets the flag indicating whether or not the URL is secure.
	 *
	 * @param  secure  <code>true</code> if secure, otherwise <code>false</code>.
	 */
	public void setSecure(boolean secure);

	/**
	 * Sets the flag indicating whether or not the URL is self-referencing, meaning, whether or not it targets the
	 * current Faces view.
	 *
	 * @param  selfReferencing  <code>true</code> if self-referencing, otherwise <code>false</code>.
	 */
	public void setSelfReferencing(boolean selfReferencing);
}
