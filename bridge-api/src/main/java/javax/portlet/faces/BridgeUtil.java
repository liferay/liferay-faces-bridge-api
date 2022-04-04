/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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
package javax.portlet.faces;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.osgi.annotation.versioning.ProviderType;


/**
 * Utility class designed to make it easy for Faces subsystems including the bridge itself to determine whether this
 * request is running in a portlet container and/or which portlet request phase it is executing in.
 *
 * @author  Michael Freedman
 * @author  Neil Griffin
 */
@ProviderType
public final class BridgeUtil {

	// Prevent instantiation since this is a static utility class.
	private BridgeUtil() {
		throw new AssertionError();
	}

	/**
	 * Indicates the portlet lifecycle phase currently being executed within the execution of the Faces lifecycle.
	 *
	 * @return  The portlet lifecycle phase if the current request is executing within a portlet environment. Otherwise
	 *          <code>null</code>.
	 */
	public static Bridge.PortletPhase getPortletRequestPhase() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		return getPortletRequestPhase(facesContext);
	}

	/**
	 * Indicates the portlet lifecycle phase currently being executed within the execution of the Faces lifecycle.
	 *
	 * @param   facesContext  The current Faces context.
	 *
	 * @return  The portlet lifecycle phase if the current request is executing within a portlet environment. Otherwise
	 *          <code>null</code>.
	 */
	public static Bridge.PortletPhase getPortletRequestPhase(FacesContext facesContext) {
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();

		return (Bridge.PortletPhase) requestMap.get(Bridge.PORTLET_LIFECYCLE_PHASE);
	}

	/**
	 * Indicates whether the current request is executing in the portlet container.
	 *
	 * @return  <code>true</code> if the request is a portlet request, otherwise <code>false</code>.
	 */
	public static boolean isPortletRequest() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		return isPortletRequest(facesContext);
	}

	/**
	 * Indicates whether the current request is executing in the portlet container.
	 *
	 * @param   facesContext  The current Faces context.
	 *
	 * @return  <code>true</code> if the request is a portlet request, otherwise <code>false</code>.
	 */
	public static boolean isPortletRequest(FacesContext facesContext) {
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		Bridge.PortletPhase portletPhase = (Bridge.PortletPhase) requestMap.get(Bridge.PORTLET_LIFECYCLE_PHASE);

		return (portletPhase != null);
	}
}
