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

/**
 * @author  Neil Griffin
 */
public abstract class BridgeURLWrapper implements BridgeURL {

	public boolean isSecure() {
		return getWrapped().isSecure();
	}

	public boolean isSelfReferencing() {
		return getWrapped().isSelfReferencing();
	}

	public String getParameter(String name) {
		return getWrapped().getParameter(name);
	}

	public void setParameter(String name, String value) {
		getWrapped().setParameter(name, value);
	}

	public void setParameter(String name, String[] value) {
		getWrapped().setParameter(name, value);
	}

	public void setSecure(boolean secure) {
		getWrapped().setSecure(secure);
	}

	public void setSelfReferencing(boolean selfReferencing) {
		getWrapped().setSelfReferencing(selfReferencing);
	}

	public boolean isFacesViewTarget() {
		return getWrapped().isFacesViewTarget();
	}

	public abstract BridgeURL getWrapped();
}
