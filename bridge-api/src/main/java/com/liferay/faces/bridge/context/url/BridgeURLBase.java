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
package com.liferay.faces.bridge.context.url;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.ResponseStateManager;
import javax.portlet.BaseURL;
import javax.portlet.MimeResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeUtil;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.PortletModeValidator;
import com.liferay.faces.bridge.PortletModeValidatorFactory;
import com.liferay.faces.bridge.WindowStateValidator;
import com.liferay.faces.bridge.WindowStateValidatorFactory;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeURLBase implements BridgeURL {

	// Private Data Members
	private BridgeURI bridgeURI;
	private String contextPath;
	private String facesViewTarget;
	private String namespace;
	private Map<String, String[]> parameterMap;
	private boolean secure;
	private boolean selfReferencing;
	private String viewId;
	private String viewIdRenderParameterName;
	private String viewIdResourceParameterName;

	public BridgeURLBase(BridgeURI bridgeURI, String contextPath, String namespace, String viewId,
		String viewIdRenderParameterName, String viewIdResourceParameterName) {
		this.bridgeURI = bridgeURI;
		this.contextPath = contextPath;
		this.namespace = namespace;
		this.viewId = viewId;
		this.viewIdRenderParameterName = viewIdRenderParameterName;
		this.viewIdResourceParameterName = viewIdResourceParameterName;
	}

	@Override
	public String toString() {

		String stringValue = null;

		try {

			// Ask the Portlet Container for a BaseURL that contains the modified parameters.
			BaseURL baseURL = toBaseURL();

			// If the URL string has escaped characters (like %20 for space, etc) then ask the
			// portlet container to create an escaped representation of the URL string.
			if (getBridgeURI().isEscaped()) {

				StringWriter urlWriter = new StringWriter();

				try {
					baseURL.write(urlWriter, true);
				}
				catch (IOException e) {
					logError(e);
					stringValue = baseURL.toString();
				}

				stringValue = urlWriter.toString();
			}

			// Otherwise, ask the portlet container to create a normal (non-escaped) string
			// representation of the URL string.
			else {
				stringValue = baseURL.toString();
			}
		}
		catch (MalformedURLException e) {
			logError(e);
		}

		return stringValue;
	}

	protected abstract void log(Level level, String message, Object... arguments);

	protected abstract void logError(Throwable t);

	protected String _toString(boolean modeChanged) {
		return _toString(modeChanged, null);
	}

	protected String _toString(boolean modeChanged, Set<String> excludedParameterNames) {

		StringBuilder buf = new StringBuilder();

		BridgeURI bridgeURI = getBridgeURI();
		String uri = bridgeURI.toString();

		int endPos = uri.indexOf("?");

		if (endPos < 0) {
			endPos = uri.length();
		}

		if (bridgeURI.isPortletScheme()) {
			Bridge.PortletPhase urlPortletPhase = bridgeURI.getPortletPhase();

			if (urlPortletPhase == Bridge.PortletPhase.ACTION_PHASE) {
				buf.append(uri.substring("portlet:action".length(), endPos));
			}
			else if (urlPortletPhase == Bridge.PortletPhase.RENDER_PHASE) {
				buf.append(uri.substring("portlet:render".length(), endPos));
			}
			else {
				buf.append(uri.substring("portlet:resource".length(), endPos));
			}
		}
		else {
			buf.append(uri.subSequence(0, endPos));
		}

		boolean firstParam = true;

		buf.append("?");

		Set<String> parameterNames = getParameterMap().keySet();
		boolean foundFacesViewIdParam = false;
		boolean foundFacesViewPathParam = false;

		for (String parameterName : parameterNames) {

			boolean addParameter = false;
			String parameterValue = getParameter(parameterName);

			if (Bridge.PORTLET_MODE_PARAMETER.equals(parameterName)) {

				// Only add the "javax.portlet.faces.PortletMode" parameter if it has a valid value.
				if (parameterValue != null) {

					PortletModeValidatorFactory portletModeValidatorFactory = (PortletModeValidatorFactory)
						BridgeFactoryFinder.getFactory(PortletModeValidatorFactory.class);
					PortletModeValidator portletModeValidator = portletModeValidatorFactory.getPortletModeValidator();
					addParameter = portletModeValidator.isValid(parameterValue);
				}
			}
			else if (Bridge.PORTLET_SECURE_PARAMETER.equals(parameterName)) {
				addParameter = ((parameterValue != null) &&
						("true".equalsIgnoreCase(parameterValue) || "false".equalsIgnoreCase(parameterValue)));
			}
			else if (Bridge.PORTLET_WINDOWSTATE_PARAMETER.equals(parameterName)) {

				WindowStateValidatorFactory windowStateValidatorFactory = (WindowStateValidatorFactory)
					BridgeFactoryFinder.getFactory(WindowStateValidatorFactory.class);
				WindowStateValidator windowStateValidator = windowStateValidatorFactory.getWindowStateValidator();
				addParameter = windowStateValidator.isValid(parameterValue);
			}
			else {

				if (!foundFacesViewIdParam) {
					foundFacesViewIdParam = Bridge.FACES_VIEW_ID_PARAMETER.equals(parameterName);
				}

				if (!foundFacesViewPathParam) {
					foundFacesViewPathParam = Bridge.FACES_VIEW_PATH_PARAMETER.equals(parameterName);
				}

				addParameter = true;
			}

			if ((addParameter) &&
					((excludedParameterNames == null) || !excludedParameterNames.contains(parameterName))) {

				if (firstParam) {
					firstParam = false;
				}
				else {
					buf.append("&");
				}

				buf.append(parameterName);
				buf.append("=");
				buf.append(parameterValue);
			}
		}

		// If the "_jsfBridgeViewId" and "_jsfBridgeViewPath" parameters are not present in the URL, then add a
		// parameter that indicates the target Faces viewId.
		if (!foundFacesViewIdParam && !foundFacesViewPathParam && (getFacesViewTarget() != null)) {

			if (!bridgeURI.isPortletScheme()) {

				// Note that if the "javax.portlet.faces.PortletMode" parameter is specified, then a mode change is
				// being requested and the target Faces viewId parameter must NOT be added.
				if (!modeChanged) {

					if (!firstParam) {
						buf.append("&");
					}

					buf.append(getViewIdParameterName());
					buf.append("=");

					String contextRelativePath = bridgeURI.getContextRelativePath(getContextPath());
					buf.append(contextRelativePath);
				}
			}
		}

		return buf.toString();
	}

	/**
	 * Copies any query paramters present in the specified "from" URL to the specified "to" URL.
	 */
	protected void copyParameters(String fromURL, BaseURL toURL) throws MalformedURLException {

		List<RequestParameter> requestParameters = parseRequestParameters(fromURL);

		if (requestParameters != null) {

			for (RequestParameter requestParameter : requestParameters) {
				String name = requestParameter.getName();
				String value = requestParameter.getValue();
				toURL.setParameter(name, value);
				log(Level.FINE, "Copied parameter to portletURL name=[{0}] value=[{1}]", name, value);
			}
		}
	}

	protected PortletURL createActionURL(FacesContext facesContext, String fromURL) throws MalformedURLException {

		try {
			log(Level.FINE, "createActionURL fromURL=[{0}]", fromURL);

			ExternalContext externalContext = facesContext.getExternalContext();
			MimeResponse mimeResponse = (MimeResponse) externalContext.getResponse();
			PortletURL actionURL = mimeResponse.createActionURL();
			copyParameters(fromURL, actionURL);

			return actionURL;
		}
		catch (ClassCastException e) {
			throw new MalformedURLException(e.getMessage());
		}
	}

	protected ResourceURL createPartialActionURL(FacesContext facesContext, String fromURL)
		throws MalformedURLException {

		log(Level.FINE, "createPartialActionURL fromURL=[{0}]", fromURL);

		return createResourceURL(facesContext, fromURL);
	}

	protected PortletURL createRenderURL(FacesContext facesContext, String fromURL) throws MalformedURLException {

		// TODO: FACES-2648 Bridge.PortletPhase portletRequestPhase = BridgeUtil.getPortletRequestPhase(facesContext);
		Bridge.PortletPhase portletRequestPhase = BridgeUtil.getPortletRequestPhase();

		if ((portletRequestPhase == Bridge.PortletPhase.RENDER_PHASE) ||
				(portletRequestPhase == Bridge.PortletPhase.RESOURCE_PHASE)) {

			try {
				log(Level.FINE, "createRenderURL fromURL=[{0}]", fromURL);

				ExternalContext externalContext = facesContext.getExternalContext();
				MimeResponse mimeResponse = (MimeResponse) externalContext.getResponse();
				PortletURL renderURL = mimeResponse.createRenderURL();
				copyParameters(fromURL, renderURL);

				return renderURL;
			}
			catch (ClassCastException e) {
				throw new MalformedURLException(e.getMessage());
			}
		}
		else {
			throw new MalformedURLException("Unable to create a RenderURL during " + portletRequestPhase.toString());
		}

	}

	protected ResourceURL createResourceURL(FacesContext facesContext, String fromURL) throws MalformedURLException {

		try {
			log(Level.FINE, "createResourceURL fromURL=[{0}]", fromURL);

			// Ask the portlet container to create a portlet resource URL.
			ExternalContext externalContext = facesContext.getExternalContext();
			MimeResponse mimeResponse = (MimeResponse) externalContext.getResponse();
			ResourceURL resourceURL = mimeResponse.createResourceURL();

			// If the "javax.faces.resource" token is found in the URL, then
			int tokenPos = fromURL.indexOf("javax.faces.resource");

			if (tokenPos >= 0) {

				// Parse-out the resourceId
				String resourceId = fromURL.substring(tokenPos);

				// Parse-out the resourceName and convert it to a URL parameter on the portlet resource URL.
				int queryStringPos = resourceId.indexOf('?');

				String resourceName = resourceId;

				if (queryStringPos > 0) {
					resourceName = resourceName.substring(0, queryStringPos);
				}

				int slashPos = resourceName.indexOf('/');

				if (slashPos > 0) {
					resourceName = resourceName.substring(slashPos + 1);
				}
				else {
					log(Level.FINE, "There is no slash after the [{0}] token in resourceURL=[{1}]",
						"javax.faces.resource", fromURL);
				}

				resourceURL.setParameter("javax.faces.resource", resourceName);
				log(Level.FINE, "Added parameter to portletURL name=[{0}] value=[{1}]", "javax.faces.resource",
					resourceName);
			}

			// Copy the request parameters to the portlet resource URL.
			copyParameters(fromURL, resourceURL);

			return resourceURL;
		}
		catch (ClassCastException e) {
			throw new MalformedURLException(e.getMessage());
		}
	}

	/**
	 * Determines whether or not the specified files have the same path (prefix) and extension (suffix).
	 *
	 * @param   file1  The first file to compare.
	 * @param   file2  The second file to compare.
	 *
	 * @return  <code>true</code> if the specified files have the same path (prefix) and extension (suffix), otherwise
	 *          <code>false</code>.
	 */
	protected boolean matchPathAndExtension(String file1, String file2) {

		boolean match = false;

		String path1 = null;
		int lastSlashPos = file1.lastIndexOf("/");

		if (lastSlashPos > 0) {
			path1 = file1.substring(0, lastSlashPos);
		}

		String path2 = null;
		lastSlashPos = file2.lastIndexOf("/");

		if (lastSlashPos > 0) {
			path2 = file2.substring(0, lastSlashPos);
		}

		if (((path1 == null) && (path2 == null)) || ((path1 != null) && (path2 != null) && path1.equals(path2))) {

			String ext1 = null;
			int lastDotPos = file1.indexOf(".");

			if (lastDotPos > 0) {
				ext1 = file1.substring(lastDotPos);
			}

			String ext2 = null;
			lastDotPos = file2.indexOf(".");

			if (lastDotPos > 0) {
				ext2 = file2.substring(lastDotPos);
			}

			if (((ext1 == null) && (ext2 == null)) || ((ext1 != null) && (ext2 != null) && ext1.equals(ext2))) {
				match = true;
			}
		}

		return match;
	}

	/**
	 * Parses the specified URL and returns a list of query parameters that are found.
	 *
	 * @param   url  The URL to parse.
	 *
	 * @return  The list of query parameters found.
	 *
	 * @throws  MalformedURLException
	 */
	protected List<RequestParameter> parseRequestParameters(String url) throws MalformedURLException {

		List<RequestParameter> requestParameters = null;

		if (url != null) {
			int pos = url.indexOf("?");

			if (pos >= 0) {
				String queryString = url.substring(pos + 1);

				if (queryString.length() > 0) {
					requestParameters = new ArrayList<RequestParameter>();

					String[] queryParameters = queryString.split("[&]");

					for (String queryParameter : queryParameters) {

						String[] nameValueArray = queryParameter.split("[=]");

						if (nameValueArray.length == 1) {

							String name = nameValueArray[0].trim();

							if (name.length() == 0) {
								throw new MalformedURLException("Invalid name/value pair=[" + queryParameter +
									"]: name cannot be empty.");
							}
							else {
								requestParameters.add(new RequestParameter(name, ""));
							}
						}
						else if (nameValueArray.length == 2) {

							String name = nameValueArray[0].trim();

							if (name.length() == 0) {
								throw new MalformedURLException("Invalid name/value pair=[" + queryParameter +
									"]: name cannot be empty.");
							}
							else {

								String value = nameValueArray[1];
								requestParameters.add(new RequestParameter(name, value));
							}
						}
						else {
							throw new MalformedURLException("Invalid name/value pair: " + queryParameter);
						}
					}
				}
			}
		}

		return requestParameters;
	}

	protected String removeParameter(String name) {

		String[] values = getParameterMap().remove(name);
		String value = null;

		if ((values != null) && (values.length > 0)) {
			value = values[0];
		}

		return value;
	}

	protected BridgeURI getBridgeURI() {
		return bridgeURI;
	}

	protected String getContextPath() {
		return contextPath;
	}

	@Override
	public boolean isSecure() {
		return secure;
	}

	@Override
	public String getFacesViewTarget() {

		if (facesViewTarget == null) {

			String contextRelativePath = getBridgeURI().getContextRelativePath(getContextPath());
			String viewId = getViewId();

			if ((viewId != null) && (viewId.equals(contextRelativePath))) {
				facesViewTarget = viewId;
			}
			else {

				// If the context relative path maps to an actual Faces View due to an implicit servlet mapping or an
				// explicit servlet-mapping entry in the WEB-INF/web.xml descriptor, then the context relative path
				// is a faces view target.
				if (isMappedToFacesServlet(contextRelativePath)) {
					facesViewTarget = contextRelativePath;
				}

				// Otherwise,
				else {
					String potentialFacesViewId;

					// If the context relative path is not available, then
					if (contextRelativePath == null) {

						// TCK TestPage005 (modeViewIDTest)
						// * viewId="/tests/modeViewIdTest.xhtml"
						//
						// TCK TestPage042 (requestRenderIgnoresScopeViaCreateViewTest)
						// TCK TestPage043 (requestRenderRedisplayTest)
						// TCK TestPage044 (requestRedisplayOutOfScopeTest)
						// TCK TestPage049 (renderRedirectTest)
						// TCK TestPage050 (ignoreCurrentViewIdModeChangeTest)
						// TCK TestPage051 (exceptionThrownWhenNoDefaultViewIdTest)
						// * viewId="/tests/redisplayRenderNewModeRequestTest.xhtml"
						//
						// TCK TestPage073 (scopeAfterRedisplayResourcePPRTest)
						// * viewId="/tests/redisplayResourceAjaxResult.xhtml"
						//
						// TCK TestPage088 (encodeActionURLPortletRenderTest)
						// TCK TestPage089 (encodeActionURLPortletActionTest)
						// * viewId="/tests/singleRequestTest.xhtml"
						//
						// TCK TestPage179 (redirectRenderPRP1Test)
						// * viewId=null
						potentialFacesViewId = viewId;
					}

					// Otherwise, if the context relative path is indeed available, then
					else {

						// TCK TestPage059 (renderPhaseListenerTest)
						// TCK TestPage095 (encodeActionURLWithWindowStateActionTest)
						// TCK TestPage097 (encodeActionURLNonJSFViewRenderTest)
						// TCK TestPage098 (encodeActionURLNonJSFViewWithParamRenderTest)
						// TCK TestPage099 (encodeActionURLNonJSFViewWithModeRenderTest)
						// TCK TestPage100 (encodeActionURLNonJSFViewWithInvalidModeRenderTest)
						// TCK TestPage101 (encodeActionURLNonJSFViewWithWindowStateRenderTest)
						// TCK TestPage102 (encodeActionURLNonJSFViewWithInvalidWindowStateRenderTest)
						// TCK TestPage103 (encodeActionURLNonJSFViewResourceTest)
						// TCK TestPage104 (encodeActionURLNonJSFViewWithParamResourceTest)
						// TCK TestPage105 (encodeActionURLNonJSFViewWithModeResourceTest)
						// TCK TestPage106 (encodeActionURLNonJSFViewWithInvalidModeResourceTest)
						// TCK TestPage107 (encodeActionURLNonJSFViewWithWindowStateResourceTest)
						// TCK TestPage108 (encodeActionURLNonJSFViewWithInvalidWindowStateResourceTest)
						// * contextRelativeViewPath="/nonFacesViewTestPortlet.ptlt"
						// * viewId="/tests/nonJSFViewTest.xhtml"
						//
						// TCK TestPage071 (nonFacesResourceTest)
						// * contextRelativeViewPath="/tck/nonFacesResource"
						// * viewId="/tests/nonFacesResourceTest.xhtml"
						//
						// TCK TestPage134 (encodeResourceURLBackLinkTest)
						// * contextRelativePath="/resources/myImage.jpg"
						// * viewId="/tests/singleRequestTest.xhtml"
						//
						// TCK TestPage181 (illegalRedirectRenderTest)
						// * contextRelativeViewPath="/tests/NonJSFView.portlet"
						// * viewId=null
						potentialFacesViewId = contextRelativePath;
					}

					// If the extension/suffix of the context relative path matches that of the viewId at the time of
					// construction, then it is a it is a faces view target.
					if ((viewId != null) && (matchPathAndExtension(viewId, potentialFacesViewId))) {

						// TCK TestPage005 (modeViewIDTest)
						// * contextRelativeViewPath=null
						// * potentialFacesViewId="/tests/modeViewIdTest.xhtml"
						// * viewId="/tests/modeViewIdTest.xhtml"
						facesViewTarget = potentialFacesViewId;

						log(Level.FINE,
							"Regarding path=[{0}] as a Faces view since it has the same path and extension as the current viewId=[{1}]",
							potentialFacesViewId, viewId);
					}
				}
			}
		}

		return facesViewTarget;
	}

	@Override
	public boolean isSelfReferencing() {
		return selfReferencing;
	}

	protected String getNamespace() {
		return namespace;
	}

	@Override
	public String getParameter(String name) {

		String value = null;
		Map<String, String[]> parameterMap = getParameterMap();
		String[] values = parameterMap.get(name);

		if (values == null) {
			values = parameterMap.get(getNamespace() + name);
		}

		if ((values != null) && (values.length > 0)) {
			value = values[0];
		}

		return value;
	}

	@Override
	public void setParameter(String name, String[] value) {
		getParameterMap().put(name, value);
	}

	@Override
	public void setParameter(String name, String value) {
		getParameterMap().put(name, new String[] { value });
	}

	@Override
	public Map<String, String[]> getParameterMap() {

		if (parameterMap == null) {
			parameterMap = new LinkedHashMap<String, String[]>(getBridgeURI().getParameterMap());
		}

		return parameterMap;
	}

	protected void setPortletModeParameter(FacesContext facesContext, PortletURL portletURL, String portletMode) {

		if (portletMode != null) {

			try {
				PortletMode candidatePortletMode = new PortletMode(portletMode);
				ExternalContext externalContext = facesContext.getExternalContext();
				PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();

				if (portletRequest.isPortletModeAllowed(candidatePortletMode)) {
					portletURL.setPortletMode(candidatePortletMode);
				}
				else {
					// TestPage118: encodeActionURLWithInvalidModeRenderTest
				}
			}
			catch (PortletModeException e) {
				logError(e);
			}
		}
	}

	protected void setRenderParameters(FacesContext facesContext, BaseURL baseURL) {

		// Get the modified parameter map.
		Map<String, String[]> urlParameterMap = getParameterMap();

		// Copy the public render parameters of the current view to the BaseURL.
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		Map<String, String[]> publicParameterMap = portletRequest.getPublicParameterMap();

		if (publicParameterMap != null) {
			Set<Map.Entry<String, String[]>> publicParamterMapEntrySet = publicParameterMap.entrySet();

			for (Map.Entry<String, String[]> mapEntry : publicParamterMapEntrySet) {
				String publicParameterName = mapEntry.getKey();

				// Note that preserved action parameters, parameters that already exist in the URL string,
				// and "javax.faces.ViewState" must not be copied.
				if (!ResponseStateManager.VIEW_STATE_PARAM.equals(publicParameterName) &&
						!urlParameterMap.containsKey(publicParameterName)) {
					baseURL.setParameter(publicParameterName, mapEntry.getValue());
				}
			}
		}

		// Copy the private render parameters of the current view to the BaseURL.
		Map<String, String[]> privateParameterMap = portletRequest.getPrivateParameterMap();

		if (privateParameterMap != null) {
			Set<Map.Entry<String, String[]>> privateParameterMapEntrySet = privateParameterMap.entrySet();

			for (Map.Entry<String, String[]> mapEntry : privateParameterMapEntrySet) {
				String privateParameterName = mapEntry.getKey();

				// Note that preserved action parameters, parameters that already exist in the URL string,
				// and "javax.faces.ViewState" must not be copied.
				if (!ResponseStateManager.VIEW_STATE_PARAM.equals(privateParameterName) &&
						!urlParameterMap.containsKey(privateParameterName)) {
					baseURL.setParameter(privateParameterName, mapEntry.getValue());
				}
			}
		}
	}

	@Override
	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	protected void setSecureParameter(String secure, BaseURL baseURL) {

		if (secure != null) {

			try {
				baseURL.setSecure("true".equalsIgnoreCase(secure));
			}
			catch (PortletSecurityException e) {
				logError(e);
			}
		}
	}

	@Override
	public void setSelfReferencing(boolean selfReferencing) {
		this.selfReferencing = selfReferencing;
	}

	protected abstract boolean isMappedToFacesServlet(String viewPath);

	protected String getViewId() {
		return viewId;
	}

	protected String getViewIdParameterName() {

		BridgeURI bridgeURI = getBridgeURI();

		if (bridgeURI.isPortletScheme() && (bridgeURI.getPortletPhase() == Bridge.PortletPhase.RESOURCE_PHASE)) {
			return getViewIdResourceParameterName();
		}
		else {
			return getViewIdRenderParameterName();
		}
	}

	public String getViewIdRenderParameterName() {
		return viewIdRenderParameterName;
	}

	public String getViewIdResourceParameterName() {
		return viewIdResourceParameterName;
	}

	protected void setWindowStateParameter(FacesContext facesContext, PortletURL portletURL, String windowState) {

		if (windowState != null) {

			try {
				WindowState candidateWindowState = new WindowState(windowState);
				ExternalContext externalContext = facesContext.getExternalContext();
				PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();

				if (portletRequest.isWindowStateAllowed(candidateWindowState)) {
					portletURL.setWindowState(candidateWindowState);
				}
				else {
					// TestPage120: encodeActionURLWithInvalidWindowStateRenderTest
				}
			}
			catch (WindowStateException e) {
				logError(e);
			}
		}

	}

	protected static class RequestParameter {

		private String name;
		private String value;

		public RequestParameter(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}
	}
}
