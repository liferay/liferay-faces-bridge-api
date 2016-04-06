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

import java.util.Map;


/**
 * This interface represents a bridge URL, meaning a URL that has convenience methods for representing URLs according to
 * Section 6.1.3.1 of the Bridge Spec.
 *
 * @author  Neil Griffin
 */
public interface BridgeURL {

	/**
	 * Removes the entry of the underlying {@link BridgeURL#getParameterMap()} according to the specified <code>
	 * name</code>.
	 *
	 * @return  the first value of the underlying {@link BridgeURL#getParameterMap()} with the specified <code>
	 *          name</code>.
	 */
	public String removeParameter(String name);

	/**
	 * Returns a string-based representation of the URL.
	 */
	@Override
	public String toString();

	/**
	 * @return  the first value of the underlying {@link BridgeURL#getParameterMap()} with the specified <code>
	 *          name</code>.
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
	 * Returns a mutable {@link Map} representing the URL parameters.
	 */
	public Map<String, String[]> getParameterMap();
}
