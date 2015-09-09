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
import javax.portlet.faces.Bridge;


/**
 * This interface represents a bridge "resource" URL, meaning a URL that has convenience methods for representing URLs
 * according to the deviation requirements of {@link javax.faces.context.ExternalContext#encodeResourceURL(String)}
 * listed in Section 6.1.3.1 of the Bridge Spec.
 *
 * @author  Neil Griffin
 */
public interface BridgeResourceURL extends BridgeURL {

	/**
	 * Replaces the value of the "javax.portlet.BackLink" parameter with an encoded action URL that represents a link to
	 * the current Faces viewId.
	 *
	 * @param  facesContext  The current {@link FacesContext} instance.
	 */
	void replaceBackLinkParameter(FacesContext facesContext);

	/**
	 * Sets a flag indicating whether or not the URL must satisfy "in-protocol" resource serving.
	 *
	 * @param  inProtocol  <code>true</code> if the URL must satisfy "in-protocol" resource serving, otherwise <code>
	 *                     false</code>.
	 */
	void setInProtocol(boolean inProtocol);

	/**
	 * Sets a flag indicating whether or not the URL is a view-link to a Faces view, which is a type of navigation. For
	 * more information, refer to the documentation at {@link Bridge#VIEW_LINK}.
	 */
	void setViewLink(boolean viewLink);
}
