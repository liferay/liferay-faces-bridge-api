/**
 * Copyright (c) 2000-2025 Liferay, Inc. All rights reserved.
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
package jakarta.portlet.faces;

import jakarta.faces.context.FacesContext;

import org.osgi.annotation.versioning.ProviderType;


/**
 * This interface defines the contract for a Portlet 2.0 Public Render Parameter "handler" that enables JSF portlet
 * developers to perform any processing that might be necessary after the bridge pushes public render parameter values
 * into the model. Handlers are registered in the portlet.xml descriptor using an init-param with name
 * "bridgePublicRenderParameterHandler" as defined in {@link Bridge#BRIDGE_PUBLIC_RENDER_PARAMETER_HANDLER}.
 *
 * @author  Michael Freedman
 * @author  Neil Griffin
 */
@ProviderType
public interface BridgePublicRenderParameterHandler {

	/**
	 * This method is called after the bridge has pushed public render parameter values into the model, which occurs
	 * after the RESTORE_VIEW phase of the JSF lifecycle.
	 *
	 * @param  facesContext  The current Faces context.
	 */
	public void processUpdates(FacesContext facesContext);
}
