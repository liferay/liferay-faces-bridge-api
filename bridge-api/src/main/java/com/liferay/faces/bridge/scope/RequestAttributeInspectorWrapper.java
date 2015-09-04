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

import javax.faces.FacesWrapper;


/**
 * @author  Neil Griffin
 */
public abstract class RequestAttributeInspectorWrapper implements RequestAttributeInspector,
	FacesWrapper<RequestAttributeInspector> {

	@Override
	public boolean containsExcludedNamespace(String name) {
		return getWrapped().containsExcludedNamespace(name);
	}

	@Override
	public boolean isExcludedByType(String name, Object value) {
		return getWrapped().isExcludedByType(name, value);
	}

	@Override
	public boolean isExcludedByConfig(String name, Object value) {
		return getWrapped().isExcludedByConfig(name, value);
	}

	@Override
	public boolean isExcludedByPreExisting(String name, Object value) {
		return getWrapped().isExcludedByPreExisting(name, value);
	}

	@Override
	public boolean isExcludedByAnnotation(String name, Object value) {
		return getWrapped().isExcludedByAnnotation(name, value);
	}

	@Override
	public abstract RequestAttributeInspector getWrapped();
}
