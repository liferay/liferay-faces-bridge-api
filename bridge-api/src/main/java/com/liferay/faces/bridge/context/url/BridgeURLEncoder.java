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

import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
public interface BridgeURLEncoder {

	/**
	 * Returns an encoded bridge "action" URL, meaning a URL that conforms to the deviation requirements of {@link
	 * javax.faces.context.ExternalContext#encodeActionURL(String)} listed in Section 6.1.3.1 of the Bridge Spec.
	 */
	public BridgeURL encodeActionURL(FacesContext facesContext, String url) throws URISyntaxException;

	/**
	 * Returns an encoded bridge "bookmarkable" URL, meaning a URL that is compatible with the requirements of {@link
	 * javax.faces.context.ExternalContext#encodeBookmarkableURL(String, Map)}.
	 */
	public BridgeURL encodeBookmarkableURL(FacesContext facesContext, String url, Map<String, List<String>> parameters)
		throws URISyntaxException;

	/**
	 * Returns an encoded bridge "partial action" URL, meaning a URL that is compatible with the requirements of {@link
	 * javax.faces.context.ExternalContext#encodePartialActionURL(String)}.
	 */
	public BridgeURL encodePartialActionURL(FacesContext facesContext, String url) throws URISyntaxException;

	/**
	 * Returns an encoded bridge "redirect" URL, meaning a URL that is compatible with the requirements of {@link
	 * javax.faces.context.ExternalContext#encodeRedirectURL(String, Map)} and the deviation requirements of {@link
	 * javax.faces.context.ExternalContext#redirect(String)} listed in Section 6.1.3.1 of the Bridge Spec.
	 */
	public BridgeURL encodeRedirectURL(FacesContext facesContext, String url, Map<String, List<String>> parameters)
		throws URISyntaxException;

	/**
	 * Returns an encoded bridge "resource" URL, meaning a URL that conforms to the deviation requirements of {@link
	 * javax.faces.context.ExternalContext#encodeResourceURL(String)} listed in Section 6.1.3.1 of the Bridge Spec.
	 */
	public BridgeURL encodeResourceURL(FacesContext facesContext, String url) throws URISyntaxException;
}
