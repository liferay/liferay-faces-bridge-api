/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge;

import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;
import javax.portlet.Event;
import javax.portlet.faces.BridgeEventHandler;
import javax.portlet.faces.event.EventNavigationResult;

import org.osgi.annotation.versioning.ConsumerType;


/**
 * @author  Neil Griffin
 */
@ConsumerType
public abstract class BridgeEventHandlerWrapper implements BridgeEventHandler, FacesWrapper<BridgeEventHandler> {

	@Override
	public abstract BridgeEventHandler getWrapped();

	@Override
	public EventNavigationResult handleEvent(FacesContext facesContext, Event event) {
		return getWrapped().handleEvent(facesContext, event);
	}
}
