/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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

import javax.faces.context.FacesContext;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeResourceURLWrapper extends BridgeURLWrapper implements BridgeResourceURL,
	Wrapper<BridgeResourceURL> {

	public void replaceBackLinkParameter(FacesContext facesContext) {
		getWrapped().replaceBackLinkParameter(facesContext);
	}

	public void setInProtocol(boolean inProtocol) {
		getWrapped().setInProtocol(inProtocol);
	}

	public boolean isEncodedFaces2ResourceURL() {
		return getWrapped().isEncodedFaces2ResourceURL();
	}

	public boolean isFaces2ResourceURL() {
		return getWrapped().isFaces2ResourceURL();
	}

	public void setViewLink(boolean viewLink) {
		getWrapped().setViewLink(viewLink);
	}

	@Override
	public abstract BridgeResourceURL getWrapped();

}
