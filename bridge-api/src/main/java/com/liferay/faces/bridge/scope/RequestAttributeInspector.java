/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.bridge.scope;

/**
 * @author  Neil Griffin
 */
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
	 * specified value is an instance of a particular class such as those listed in Section 5.1.2 of the Bridge
	 * Specification.
	 *
	 * @param   name  The request attribute name.
	 * @param   value  The request attribute value.
	 *
	 * @return  If the request attribute is to be excluded then the return value is <code>true</code>. Otherwise, the
	 *          return value is <code>false</code>.
	 */
	public boolean isExcludedByType(String name, Object value);

	/**
	 * Determines whether or not a request attribute is to be excluded from the bridge request scope because the
	 * specified name appears in the <code>bridge:excluded-attributes</code> element within the faces-config.xml
	 * descriptor.
	 *
	 * @param   name  The request attribute name.
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
	 * @param   name  The request attribute name.
	 * @param   value  The request attribute value.
	 *
	 * @return  If the request attribute is to be excluded then the return value is <code>true</code>. Otherwise, the
	 *          return value is <code>false</code>.
	 */
	public boolean isExcludedByPreExisting(String name, Object value);

	/**
	 * Determines whether or not a request attribute is to be excluded from the bridge request scope because the
	 * specified value is an instance of a class annotated with {@link
	 * javax.portlet.faces.annotation.ExcludeFromManagedRequestScope}.
	 *
	 * @param   name  The request attribute name.
	 * @param   value  The request attribute value.
	 *
	 * @return  If the request attribute is to be excluded then the return value is <code>true</code>. Otherwise, the
	 *          return value is <code>false</code>.
	 */
	public boolean isExcludedByAnnotation(String name, Object value);
}
