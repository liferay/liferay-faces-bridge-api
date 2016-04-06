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
 * @author  Neil Griffin
 */
public abstract class BridgeURLWrapper implements BridgeURL {

	@Override
	public String removeParameter(String name) {
		return getWrapped().removeParameter(name);
	}

	@Override
	public String toString() {
		return getWrapped().toString();
	}

	@Override
	public String getParameter(String name) {
		return getWrapped().getParameter(name);
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
	public Map<String, String[]> getParameterMap() {
		return getWrapped().getParameterMap();
	}

	public abstract BridgeURL getWrapped();
}
