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
package javax.portlet.faces;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
public class BridgeUtil {

	public static Bridge.PortletPhase getPortletRequestPhase() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();

		return (Bridge.PortletPhase) requestMap.get(Bridge.PORTLET_LIFECYCLE_PHASE);
	}

	public static boolean isPortletRequest() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		Bridge.PortletPhase portletPhase = (Bridge.PortletPhase) requestMap.get(Bridge.PORTLET_LIFECYCLE_PHASE);

		return (portletPhase != null);
	}
}
