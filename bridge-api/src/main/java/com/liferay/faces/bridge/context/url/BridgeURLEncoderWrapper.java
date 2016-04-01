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

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeURLEncoderWrapper implements BridgeURLEncoder, FacesWrapper<BridgeURLEncoder> {

	@Override
	public BridgeURL encodeActionURL(FacesContext facesContext, String url) throws URISyntaxException {
		return getWrapped().encodeActionURL(facesContext, url);
	}

	@Override
	public BridgeURL encodeBookmarkableURL(FacesContext facesContext, String url, Map<String, List<String>> parameters)
		throws URISyntaxException {
		return getWrapped().encodeBookmarkableURL(facesContext, url, parameters);
	}

	@Override
	public BridgeURL encodePartialActionURL(FacesContext facesContext, String url) throws URISyntaxException {
		return getWrapped().encodePartialActionURL(facesContext, url);
	}

	@Override
	public BridgeURL encodeRedirectURL(FacesContext facesContext, String url, Map<String, List<String>> parameters)
		throws URISyntaxException {
		return getWrapped().encodeRedirectURL(facesContext, url, parameters);
	}

	@Override
	public BridgeURL encodeResourceURL(FacesContext facesContext, String url) throws URISyntaxException {
		return getWrapped().encodeResourceURL(facesContext, url);
	}

	@Override
	public abstract BridgeURLEncoder getWrapped();
}
