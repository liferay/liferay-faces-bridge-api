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
package com.liferay.faces.bridge.event;

import java.io.Serializable;

import javax.faces.FacesWrapper;
import javax.portlet.StateAwareResponse;


/**
 * <p>The purpose of this class is to provide support a vendor-specific feature of Liferay Portal that provides the
 * ability to broadcast Portlet 2.0 Events to portlets that exist on a different portal page. For more information,
 * refer to the "portlet.event.distribution" key inside of the Liferay Portal portal.properties file.</p>
 *
 * <p>This class provides the ability to wrap a {@link Serializable} event payload that is intended to be sent via
 * {@link StateAwareResponse#setEvent(String, Serializable)} or {@link
 * StateAwareResponse#setEvent(javax.xml.namespace.QName, Serializable)}. It also provides the ability for the recipient
 * of the event to determine whether or not a redirect is taking place.</p>
 *
 * @author  Neil Griffin
 */
public class EventPayloadWrapper implements FacesWrapper<Serializable>, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 9167031956551424140L;

	// Private Data Members
	private boolean redirect;
	private Serializable wrappedPayload;

	/**
	 * Constructs a new {@link EventPayloadWrapper} instance.
	 *
	 * @param  payload   The {@link Serializable} payload that is to be wrapped.
	 * @param  redirect  The flag indicating whether or not a redirect is taking place.
	 */
	public EventPayloadWrapper(Serializable payload, boolean redirect) {
		this.wrappedPayload = payload;
		this.redirect = redirect;
	}

	public boolean isRedirect() {
		return redirect;
	}

	public Serializable getWrapped() {
		return wrappedPayload;
	}
}
