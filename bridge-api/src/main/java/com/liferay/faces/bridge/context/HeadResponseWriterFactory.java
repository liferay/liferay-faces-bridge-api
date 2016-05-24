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
package com.liferay.faces.bridge.context;

import javax.faces.FacesWrapper;
import javax.faces.context.ResponseWriter;
import javax.portlet.PortletResponse;

import com.liferay.faces.bridge.BridgeFactoryFinder;


/**
 * @author  Neil Griffin
 */
public abstract class HeadResponseWriterFactory implements FacesWrapper<HeadResponseWriterFactory> {

	/**
	 * Returns an instance of {@link HeadResponseWriter} from the {@link HeadResponseWriterFactory} found by the {@link
	 * BridgeFactoryFinder}.
	 */
	public static HeadResponseWriter getHeadResponseWriterInstance(ResponseWriter responseWriter,
		PortletResponse portletResponse) {

		HeadResponseWriterFactory headResponseWriterFactory = (HeadResponseWriterFactory) BridgeFactoryFinder
			.getFactory(HeadResponseWriterFactory.class);

		return headResponseWriterFactory.getHeadResponseWriter(responseWriter, portletResponse);
	}

	public abstract HeadResponseWriter getHeadResponseWriter(ResponseWriter responseWriter,
		PortletResponse portletResponse);
}
