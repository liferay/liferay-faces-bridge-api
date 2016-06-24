/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package javax.portlet.faces;

import javax.faces.FacesWrapper;
import javax.portlet.PortletConfig;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeEventHandlerFactory implements FacesWrapper<BridgeEventHandlerFactory> {

	public static BridgeEventHandler getBridgeEventHandlerInstance(PortletConfig portletConfig) {

		BridgeEventHandlerFactory bridgeEventHandlerFactory = (BridgeEventHandlerFactory) BridgeFactoryFinder
			.getFactory(BridgeEventHandlerFactory.class);

		return bridgeEventHandlerFactory.getBridgeEventHandler(portletConfig);
	}

	public abstract BridgeEventHandler getBridgeEventHandler(PortletConfig portletConfig);
}
