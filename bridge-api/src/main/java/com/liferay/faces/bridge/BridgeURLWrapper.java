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

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;


/**
 * @author  Neil Griffin
 */
@ProviderType
public abstract class BridgeURLWrapper implements BridgeURL {

	public abstract BridgeURL getWrapped();

	public String getParameter(String name) {
		return getWrapped().getParameter(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return getWrapped().getParameterMap();
	}

	@Override
	public String getViewId() {
		return getWrapped().getViewId();
	}

	@Override
	public String removeParameter(String name) {
		return getWrapped().removeParameter(name);
	}

	@Override
	public void setParameter(String name, String value) {
		getWrapped().setParameter(name, value);
	}

	@Override
	public void setParameter(String name, String[] value) {
		getWrapped().setParameter(name, value);
	}

	@Override
	public String toString() {
		return getWrapped().toString();
	}
}
