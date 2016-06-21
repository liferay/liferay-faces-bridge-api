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
package javax.portlet.faces;

import javax.faces.context.FacesContext;
import javax.portlet.Event;
import javax.portlet.faces.event.EventNavigationResult;


/**
 * <p>The <code>BridgeEventHandler</code> interface defines the class the bridge relies on to process portlet events.
 * Because portlet events have arbitrary payloads the bridge provides no automated mappings to managed beans. Instead,
 * the bridge calls the {@link #handleEvent(FacesContext, Event)} method on the <code>BridgeEventHandler</code> instance
 * passed to it (via a portlet context attribute at initialization time. This method is expected to update any models
 * based on the event's payload and then to perform any needed application recomputation to ensure a consistent state.
 * The method is called after the {@link FacesContext} has been established and the {@link
 * javax.faces.lifecycle.Lifecycle} has restored the view.</p>
 *
 * <p>A view navigation can be affected by returning a non-null {@link EventNavigationResult}. Such an object will
 * contain two <code>String</code> values: a fromAction and an outcome. These correspond to the from action and outcomes
 * in Faces navigation rules. Using this information the bridge affects the navigation by calling the Faces {@link
 * javax.faces.application.NavigationHandler}.</p>
 *
 * @author  Michael Freedman
 * @author  Neil Griffin
 */
public interface BridgeEventHandler {

	/**
	 * <p>Called by the bridge when it needs to process a portlet event.</p>
	 *
	 * <p>Because portlet events have arbitrary payloads the bridge provides no automated mappings to managed beans.
	 * Instead, the bridge calls this method on the <code>BridgeEventHandler</code> instance passed to it (via a portlet
	 * context attribute at initialization time. This method is expected to update any models based on the event's
	 * payload and then to perform any needed application re-computation to ensure a consistent state. The method is
	 * called after the {@link FacesContext} has been established and the {@link javax.faces.lifecycle.Lifecycle} has
	 * restored the view.</p>
	 *
	 * <p>A view navigation can be affected by returning a non-null {@link EventNavigationResult}. Such an object will
	 * contain two <code>String</code> values: a fromAction and an outcome. These correspond to the from action and
	 * outcomes in Faces navigation rules. Using this information the bridge affects the navigation by calling the Faces
	 * {@link javax.faces.application.NavigationHandler}.</p>
	 *
	 * @param   facesContext  The current Faces context. A Lifecycle has been acquired and the current view restored.
	 * @param   event         The portlet event. Other portlet information (request/response) is accessed via the {@link
	 *                        javax.faces.context.ExternalContext}.
	 *
	 * @return  an object containing the fromAction and outcome of any navigation that resulted from this event. If the
	 *          event doesn't cause a navigation, return <code>null</code>.
	 */
	public EventNavigationResult handleEvent(FacesContext facesContext, Event event);
}
