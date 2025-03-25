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
package com.liferay.faces.bridge.context;

import jakarta.faces.FacesWrapper;
import jakarta.faces.context.ResponseWriter;
import jakarta.portlet.PortletContext;
import jakarta.portlet.PortletResponse;
import jakarta.portlet.faces.BridgeFactoryFinder;

import org.osgi.annotation.versioning.ProviderType;


/**
 * @author  Neil Griffin
 */
@ProviderType
public abstract class HeadResponseWriterFactory implements FacesWrapper<HeadResponseWriterFactory> {

	/**
	 * Returns a new instance of {@link ResponseWriter} from the {@link HeadResponseWriterFactory} found by the {@link
	 * BridgeFactoryFinder}. The returned instance is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static ResponseWriter getHeadResponseWriterInstance(ResponseWriter responseWriter,
		PortletContext portletContext, PortletResponse portletResponse) {

		HeadResponseWriterFactory headResponseWriterFactory = (HeadResponseWriterFactory) BridgeFactoryFinder
			.getFactory(portletContext, HeadResponseWriterFactory.class);

		return headResponseWriterFactory.getHeadResponseWriter(responseWriter, portletResponse);
	}

	/**
	 * Returns a new instance of {@link ResponseWriter}. The returned instance is not guaranteed to be {@link
	 * java.io.Serializable}.
	 */
	public abstract ResponseWriter getHeadResponseWriter(ResponseWriter responseWriter,
		PortletResponse portletResponse);

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 */
	@Override
	public abstract HeadResponseWriterFactory getWrapped();
}
