/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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

import javax.faces.FacesWrapper;

import org.osgi.annotation.versioning.ConsumerType;


/**
 * @author  Neil Griffin
 */
@ConsumerType
public abstract class RequestAttributeInspectorWrapper implements RequestAttributeInspector,
	FacesWrapper<RequestAttributeInspector> {

	@Override
	public abstract RequestAttributeInspector getWrapped();

	@Override
	public boolean containsExcludedNamespace(String name) {
		return getWrapped().containsExcludedNamespace(name);
	}

	@Override
	public boolean isExcludedByAnnotation(String name, Object value) {
		return getWrapped().isExcludedByAnnotation(name, value);
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
	public boolean isExcludedByType(String name, Object value) {
		return getWrapped().isExcludedByType(name, value);
	}
}
