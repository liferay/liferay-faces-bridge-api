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
package com.liferay.faces.bridge;

import org.osgi.annotation.versioning.ProviderType;


/**
 * @author  Neil Griffin
 */
@ProviderType
public interface RequestAttributeInspector {

	/**
	 * Determines whether or not a request attribute is to be excluded from the bridge request scope because the
	 * specified name name starts with a particular sequence of characters (a namespace).
	 *
	 * @param   name  The request attribute name.
	 *
	 * @return  If the request attribute is to be excluded then the return value is <code>true</code>. Otherwise, the
	 *          return value is <code>false</code>.
	 */
	public boolean containsExcludedNamespace(String name);

	/**
	 * Determines whether or not a request attribute is to be excluded from the bridge request scope because the
	 * specified value is an instance of a class annotated with {@link
	 * javax.portlet.faces.annotation.ExcludeFromManagedRequestScope}.
	 *
	 * @param   name   The request attribute name.
	 * @param   value  The request attribute value.
	 *
	 * @return  If the request attribute is to be excluded then the return value is <code>true</code>. Otherwise, the
	 *          return value is <code>false</code>.
	 */
	public boolean isExcludedByAnnotation(String name, Object value);

	/**
	 * Determines whether or not a request attribute is to be excluded from the bridge request scope because the
	 * specified name appears in the <code>bridge:excluded-attributes</code> element within the faces-config.xml
	 * descriptor.
	 *
	 * @param   name   The request attribute name.
	 * @param   value  The request attribute value.
	 *
	 * @return  If the request attribute is to be excluded then the return value is <code>true</code>. Otherwise, the
	 *          return value is <code>false</code>.
	 */
	public boolean isExcludedByConfig(String name, Object value);

	/**
	 * Determines whether or not a request attribute is to be excluded from the bridge request scope because the
	 * specified name matches the name of a request attribute that existed before the bridge acquired the {@link
	 * javax.faces.context.FacesContext}.
	 *
	 * @param   name   The request attribute name.
	 * @param   value  The request attribute value.
	 *
	 * @return  If the request attribute is to be excluded then the return value is <code>true</code>. Otherwise, the
	 *          return value is <code>false</code>.
	 */
	public boolean isExcludedByPreExisting(String name, Object value);

	/**
	 * Determines whether or not a request attribute is to be excluded from the bridge request scope because the
	 * specified value is an instance of a particular class such as those listed in Section 5.1.2 of the Bridge
	 * Specification.
	 *
	 * @param   name   The request attribute name.
	 * @param   value  The request attribute value.
	 *
	 * @return  If the request attribute is to be excluded then the return value is <code>true</code>. Otherwise, the
	 *          return value is <code>false</code>.
	 */
	public boolean isExcludedByType(String name, Object value);
}
