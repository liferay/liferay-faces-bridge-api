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
package com.liferay.faces.bridge;

import java.util.List;
import java.util.Map;

import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;
import javax.portlet.faces.BridgeException;


/**
 * This factory is responsible for creating instances of {@link BridgeURL}.
 *
 * @author  Neil Griffin
 */
public abstract class BridgeURLFactory implements FacesWrapper<BridgeURLFactory> {

	/**
	 * Returns a new action URL instance of {@link BridgeURL} from the {@link BridgeURLFactory} found by the {@link
	 * BridgeFactoryFinder}. The returned instance is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static BridgeURL getBridgeActionURLInstance(FacesContext facesContext, String uri) {

		BridgeURLFactory bridgeURLFactory = (BridgeURLFactory) BridgeFactoryFinder.getFactory(BridgeURLFactory.class);

		return bridgeURLFactory.getBridgeActionURL(facesContext, uri);
	}

	/**
	 * Returns a new bookmarkable URL instance of {@link BridgeURL} from the {@link BridgeURLFactory} found by the
	 * {@link BridgeFactoryFinder}. The returned instance is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static BridgeURL getBridgeBookmarkableURLInstance(FacesContext facesContext, String uri,
		Map<String, List<String>> parameters) {

		BridgeURLFactory bridgeURLFactory = (BridgeURLFactory) BridgeFactoryFinder.getFactory(BridgeURLFactory.class);

		return bridgeURLFactory.getBridgeBookmarkableURL(facesContext, uri, parameters);
	}

	/**
	 * Returns a new partial action URL instance of {@link BridgeURL} from the {@link BridgeURLFactory} found by the
	 * {@link BridgeFactoryFinder}. The returned instance is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static BridgeURL getBridgePartialActionURLInstance(FacesContext facesContext, String uri) {

		BridgeURLFactory bridgeURLFactory = (BridgeURLFactory) BridgeFactoryFinder.getFactory(BridgeURLFactory.class);

		return bridgeURLFactory.getBridgePartialActionURL(facesContext, uri);
	}

	/**
	 * Returns a new redirect URL instance of {@link BridgeURL} from the {@link BridgeURLFactory} found by the {@link
	 * BridgeFactoryFinder}. The returned instance is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static BridgeURL getBridgeRedirectURLInstance(FacesContext facesContext, String uri,
		Map<String, List<String>> parameters) {

		BridgeURLFactory bridgeURLFactory = (BridgeURLFactory) BridgeFactoryFinder.getFactory(BridgeURLFactory.class);

		return bridgeURLFactory.getBridgeRedirectURL(facesContext, uri, parameters);
	}

	/**
	 * Returns a new resource URL instance of {@link BridgeURL} from the {@link BridgeURLFactory} found by the {@link
	 * BridgeFactoryFinder}. The returned instance is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static BridgeURL getBridgeResourceURLInstance(FacesContext facesContext, String uri) {

		BridgeURLFactory bridgeURLFactory = (BridgeURLFactory) BridgeFactoryFinder.getFactory(BridgeURLFactory.class);

		return bridgeURLFactory.getBridgeResourceURL(facesContext, uri);
	}

	/**
	 * Returns a new action URL instance of {@link BridgeURL}. The returned instance is not guaranteed to be {@link
	 * java.io.Serializable}. The return value of {@link BridgeURL#toString()} conforms to the deviation requirements of
	 * {@link javax.faces.context.ExternalContext#encodeActionURL(String)} listed in Section 6.1.3.1 of the Bridge Spec.
	 *
	 * @param   facesContext  The FacesContext instance associated with the current request.
	 * @param   uri           The URI that is to be encoded.
	 *
	 * @return  The bridge action URL that corresponds to the specified URI.
	 *
	 * @throws  BridgeException  if the specified URI is an invalid reference.
	 */
	public abstract BridgeURL getBridgeActionURL(FacesContext facesContext, String uri) throws BridgeException;

	/**
	 * Returns a new bookmarkable URL instance of {@link BridgeURL}. The returned instance is not guaranteed to be
	 * {@link java.io.Serializable}. The return value of {@link BridgeURL#toString()} conforms to the requirements of
	 * {@link javax.faces.context.ExternalContext#encodeBookmarkableURL(String, Map)}.
	 *
	 * @param   facesContext  The FacesContext instance associated with the current request.
	 * @param   uri           The URI that is to be encoded.
	 *
	 * @return  The bridge bookmarkable URL that corresponds to the specified URI.
	 *
	 * @throws  BridgeException  if the specified URI is an invalid reference.
	 */
	public abstract BridgeURL getBridgeBookmarkableURL(FacesContext facesContext, String uri,
		Map<String, List<String>> parameters) throws BridgeException;

	/**
	 * Returns a new partial action URL instance of {@link BridgeURL}. The returned instance is not guaranteed to be
	 * {@link java.io.Serializable}. The return value of {@link BridgeURL#toString()} conforms to the requirements of
	 * {@link javax.faces.context.ExternalContext#encodePartialActionURL(String)}.
	 *
	 * @param   facesContext  The FacesContext instance associated with the current request.
	 * @param   uri           The URI that is to be encoded.
	 *
	 * @return  The bridge partial action URL that corresponds to the specified URI.
	 *
	 * @throws  BridgeException  if the specified URI is an invalid reference.
	 */
	public abstract BridgeURL getBridgePartialActionURL(FacesContext facesContext, String uri) throws BridgeException;

	/**
	 * Returns a new redirect URL instance of {@link BridgeURL}. The returned instance is not guaranteed to be {@link
	 * java.io.Serializable}. The return value of {@link BridgeURL#toString()}, conforms to the requirements of {@link
	 * javax.faces.context.ExternalContext#encodeRedirectURL(String, Map)} and the deviation requirements of {@link
	 * javax.faces.context.ExternalContext#redirect(String)} listed in Section 6.1.3.1 of the Bridge Spec.
	 *
	 * @param   facesContext  The FacesContext instance associated with the current request.
	 * @param   uri           The URI that is to be encoded.
	 *
	 * @return  The bridge redirect URL that corresponds to the specified URI.
	 *
	 * @throws  BridgeException  if the specified URI is an invalid reference.
	 */
	public abstract BridgeURL getBridgeRedirectURL(FacesContext facesContext, String uri,
		Map<String, List<String>> parameters) throws BridgeException;

	/**
	 * Returns a new resource URL instance of {@link BridgeURL}. The returned instance is not guaranteed to be {@link
	 * java.io.Serializable}. The return value of {@link BridgeURL#toString()} conforms to the deviation requirements of
	 * {@link javax.faces.context.ExternalContext#encodeResourceURL(String)} listed in Section 6.1.3.1 of the Bridge
	 * Spec.
	 *
	 * @param   facesContext  The FacesContext instance associated with the current request.
	 * @param   uri           The URI that is to be encoded.
	 *
	 * @return  The bridge resource URL that corresponds to the specified URI.
	 *
	 * @throws  BridgeException  if the specified URI is an invalid reference.
	 */
	public abstract BridgeURL getBridgeResourceURL(FacesContext facesContext, String uri) throws BridgeException;

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 */
	// Java 1.6+ @Override
	public abstract BridgeURLFactory getWrapped();
}
