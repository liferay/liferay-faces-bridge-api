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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;


/**
 * The <code>GenericFacesPortlet</code> is provided to simplify development of a portlet that in whole or part relies on
 * the Faces bridge to process requests. If all requests are to be handled by the bridge, <code>
 * GenericFacesPortlet</code> is a turnkey implementation. Developers do not need to subclass it. However, if there are
 * some situations where the portlet doesn't require bridge services then <code>GenericFacesPortlet</code> can be
 * subclassed and overriden.
 *
 * <p>Since <code>GenericFacesPortlet</code> subclasses <code>GenericPortlet</code> care is taken to all subclasses to
 * override naturally. For example, though <code>doDispatch()</code> is overriden, requests are only dispatched to the
 * bridge from here if the <code>PortletMode</code> isn't <code>VIEW</code>, <code>EDIT</code>, or <code>HELP</code>.
 *
 * <p>The <code>GenericFacesPortlet</code> recognizes the following portlet initialization parameters:
 *
 * <ul>
 *   <li><code>javax.portlet.faces.defaultViewId.[<i>mode</i>]</code>: specifies on a per mode basis the default viewId
 *     the Bridge executes when not already encoded in the incoming request. A value must be defined for each <code>
 *     PortletMode</code> the <code>Bridge</code> is expected to process.</li>
 *   <li><code>javax.portlet.faces.excludedRequestAttributes</code>: specifies on a per portlet basis the set of request
 *     attributes the bridge is to exclude from its request scope. The value of this parameter is a comma delimited list
 *     of either fully qualified attribute names or a partial attribute name of the form <i>packageName.*</i>. In this
 *     later case all attributes exactly prefixed by <i>packageName</i> are excluded, non recursive.</li>
 *   <li><code>javax.portlet.faces.preserveActionParams</code>: specifies on a per portlet basis whether the bridge
 *     should preserve parameters received in an action request and restore them for use during subsequent renders.</li>
 * </ul>
 *
 * The <code>GenericFacesPortlet</code> recognizes the following application (<code>PortletContext</code>)
 * initialization parameters:
 *
 * <ul>
 *   <li><code>javax.portlet.faces.BridgeImplClass</code>: specifies the <code>Bridge</code>implementation class used by
 *     this portlet. Typically this initialization parameter isn't set as the <code>GenericFacesPortlet</code> defaults
 *     to finding the class name from the bridge configuration. However if more then one bridge is configured in the
 *     environment such per application configuration is necessary to force a specific bridge to be used.</li>
 * </ul>
 *
 * @author  Michael Freedman
 * @author  Neil Griffin
 */
public class GenericFacesPortlet extends GenericPortlet {

	/**
	 * Portlet init parameter containing the setting for whether the <code>GenericFacesPortlet</code> overrides event
	 * processing by dispatching all events to the bridge or delegates all event processing to the <code>
	 * GenericPortlet</code>. Default is <code>true</code>.
	 */
	public static final String BRIDGE_AUTO_DISPATCH_EVENTS = "javax.portlet.faces.autoDispatchEvents";

	/**
	 * Application (PortletContext) init parameter that names the bridge class used by this application. Typically not
	 * used unless more then 1 bridge is configured in an environment as its more usual to rely on the self detection.
	 */
	public static final String BRIDGE_CLASS = "javax.portlet.faces.BridgeImplClass";

	/**
	 * @deprecated  Location of the services descriptor file in a brige installation that defines the class name of the
	 *              bridge implementation. It is deprecated because {@link BridgeFactory} provides a more flexibile way
	 *              to decorate the bridge.
	 */
	@Deprecated
	public static final String BRIDGE_SERVICE_CLASSPATH = "META-INF/services/javax.portlet.faces.Bridge";

	/**
	 * @deprecated  Portlet init parameter that defines the render response ContentType the bridge sets prior to
	 *              rendering. If not set the bridge uses the request's preferred content type. It is deprecated because
	 *              bridge implementations (since JSR 329) no longer utilize it.
	 */
	@Deprecated
	public static final String DEFAULT_CONTENT_TYPE = "javax.portlet.faces.defaultContentType";

	/**
	 * @deprecated  Portlet init parameter that defines the render response CharacterSetEncoding the bridge sets prior
	 *              to rendering. Typcially only set when the jsp outputs an encoding other then the portlet container's
	 *              and the portlet container supports response encoding transformation. It is deprecated because bridge
	 *              implementations (since JSR 329) no longer utilize it.
	 */
	@Deprecated
	public static final String DEFAULT_CHARACTERSET_ENCODING = "javax.portlet.faces.defaultCharacterSetEncoding";

	/**
	 * Portlet init parameter that defines the default ViewId that should be used when the request doesn't otherwise
	 * convery the target. There must be one initialization parameter for each supported mode. Each parameter is named
	 * DEFAULT_VIEWID.<i>mode</i>, where <i>mode</i> is the name of the corresponding <code>PortletMode</code>
	 */
	public static final String DEFAULT_VIEWID = "javax.portlet.faces.defaultViewId";

	// Private Data Members
	private boolean autoDispatchEvents;
	private Bridge bridge;
	private String bridgeClassName;
	private BridgeEventHandler bridgeEventHandler;
	private BridgePublicRenderParameterHandler bridgePublicRenderParameterHandler;
	private Map<String, String> defaultViewIdMap;
	private List<String> excludedRequestAttributes;
	private Boolean preserveActionParameters;

	/**
	 * Release resources, specifically it destroys the bridge.
	 */
	@Override
	public void destroy() {
		bridge.destroy();
	}

	/**
	 * Returns the className of the bridge implementation this portlet uses. Subclasses override to alter the default
	 * behavior. Default implementation first checks for a portlet context init parameter:
	 * javax.portlet.faces.BridgeImplClass. If it doesn't exist then it looks for the resource file
	 * "META-INF/services/javax.portlet.faces.Bridge" using the current threads classloader and extracts the classname
	 * from the first line in that file.
	 *
	 * @return  the class name of the Bridge class the GenericFacesPortlet uses. null if it can't be determined.
	 */
	public String getBridgeClassName() {
		return bridgeClassName;
	}

	/**
	 * Returns a String defining the default render kit id the bridge should ensure for this portlet. If non-null, this
	 * value is used to override any default render kit id set on an app wide basis in the faces-config.xml. This
	 * default implementation reads the values from the portlet init_param javax.portlet.faces.defaultRenderKitId. If
	 * not present, null is returned.
	 *
	 * @return  a boolean indicating whether or not the bridge should preserve all the action parameters in the
	 *          subsequent renders that occur in the same scope.
	 */
	public String getDefaultRenderKitId() {

		// TCK TestPage016: initMethodTest
		return getPortletConfig().getInitParameter(Bridge.BRIDGE_PACKAGE_PREFIX + Bridge.DEFAULT_RENDERKIT_ID);
	}

	/**
	 * Returns the defaultViewIdMap the bridge should use when its unable to resolve to a specific target in the
	 * incoming request. There is one entry per support <code>PortletMode</code>. The entry key is the name of the mode.
	 * The entry value is the default viewId for that mode.
	 *
	 * @return  the defaultViewIdMap
	 */
	public Map<String, String> getDefaultViewIdMap() {

		if (defaultViewIdMap == null) {

			// TCK TestPage015: portletInitializationParametersTest
			defaultViewIdMap = new HashMap<String, String>();

			Enumeration<String> initParameterNames = getPortletConfig().getInitParameterNames();

			if (initParameterNames != null) {
				int defaultViewIdLength = DEFAULT_VIEWID.length();
				int portletModeIndex = defaultViewIdLength + 1;

				while (initParameterNames.hasMoreElements()) {
					String initParameterName = initParameterNames.nextElement();

					if ((initParameterName != null) && initParameterName.startsWith(DEFAULT_VIEWID) &&
							(initParameterName.length() > defaultViewIdLength)) {
						String initParameterValue = getPortletConfig().getInitParameter(initParameterName);
						String portletMode = initParameterName.substring(portletModeIndex);
						defaultViewIdMap.put(portletMode, initParameterValue);
					}
				}
			}
		}

		return defaultViewIdMap;
	}

	/**
	 * Returns the set of RequestAttribute names that the portlet wants the bridge to exclude from its managed request
	 * scope. This default implementation picks up this list from the comma delimited init_param
	 * javax.portlet.faces.excludedRequestAttributes.
	 *
	 * @return  a List containing the names of the attributes to be excluded. null if it can't be determined.
	 */
	public List<String> getExcludedRequestAttributes() {

		if (excludedRequestAttributes == null) {

			// Note: Not able to perform lazy-init operation with an empty ArrayList due to
			// TCK TestPage022: getExcludedRequestAttributesMethodNotSetTest
			String initParamName = Bridge.BRIDGE_PACKAGE_PREFIX + Bridge.EXCLUDED_REQUEST_ATTRIBUTES;
			String initParamValue = getPortletConfig().getInitParameter(initParamName);

			// TCK TestPage016: initMethodTest
			if (initParamValue != null) {

				excludedRequestAttributes = new ArrayList<String>();

				String[] values = initParamValue.split(",");

				for (String value : values) {
					excludedRequestAttributes.add(value.trim());
				}
			}
		}

		return excludedRequestAttributes;
	}

	/**
	 * Returns an initialized bridge instance adequately prepared so the caller can call doFacesRequest directly without
	 * further initialization.
	 *
	 * @param   portletRequest
	 * @param   portletResponse
	 *
	 * @return  An instance of the bridge.
	 *
	 * @throws  PortletException  When an error occurs acquiring or initializing the bridge.
	 */
	public Bridge getFacesBridge(PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortletException {

		String viewId = portletRequest.getParameter(Bridge.FACES_VIEW_ID_PARAMETER);

		if (viewId != null) {
			portletRequest.setAttribute(Bridge.VIEW_ID, viewId);
		}
		else {
			String viewPath = portletRequest.getParameter(Bridge.FACES_VIEW_PATH_PARAMETER);

			if (viewPath != null) {
				portletRequest.setAttribute(Bridge.VIEW_PATH, viewPath);
			}
		}

		return bridge;
	}

	/**
	 * @deprecated  This method is no longer used or called by the <code>GenericFacesPortlet</code> but retained in case
	 *              a subclass has called it.
	 *
	 * @return      null
	 */
	@Deprecated
	public String getResponseCharacterSetEncoding(PortletRequest portletRequest) {
		return null;
	}

	/**
	 * @deprecated  This method is no longer used or called by the <code>GenericFacesPortlet</code> but retained in case
	 *              a subclass has called it.
	 *
	 * @return      request.getResponseContentType()
	 */
	@Deprecated
	public String getResponseContentType(PortletRequest portletRequest) {
		return portletRequest.getResponseContentType();
	}

	/**
	 * Initialize generic faces portlet from portlet.xml
	 *
	 * @param   portletConfig
	 *
	 * @throws  PortletException
	 */
	@Override
	public void init(PortletConfig portletConfig) throws PortletException {

		String portletName = portletConfig.getPortletName();

		// Initialize the bridge according to the requirements set forth in Section 3.2 of the JSR 329 Spec. Begin
		// this process by delegating preliminary initialization to the parent class.
		super.init(portletConfig);

		bridge = BridgeFactory.getBridgeInstance();
		bridgeClassName = bridge.getClass().getName();

		// Initialize the bridge implementation instance.
		String customBridgeClassName = getBridgeClassName();

		if (customBridgeClassName == null) {

			// TCK TestPage016: initMethodTest
			customBridgeClassName = portletConfig.getInitParameter(GenericFacesPortlet.BRIDGE_CLASS);
		}

		if (customBridgeClassName != null) {
			bridge = BridgeFactory.getBridgeInstance(customBridgeClassName);
			bridgeClassName = customBridgeClassName;
		}

		bridge.init(portletConfig);

		// Save the default JSF views specified as WEB-INF/portlet.xml init-param value(s) as a portlet context
		// attribute with name "javax.portlet.faces.<portlet-name>.defaultViewIdMap"
		PortletContext portletContext = portletConfig.getPortletContext();
		String attributeName = Bridge.BRIDGE_PACKAGE_PREFIX + portletName + "." + Bridge.DEFAULT_VIEWID_MAP;
		portletContext.setAttribute(attributeName, getDefaultViewIdMap());

		// Save the "javax.portlet.faces.excludedRequestAttributes" init-param value(s) as a portlet context attribute
		// with name "javax.portlet.faces.<portlet-name>.excludedRequestAttributes"
		attributeName = Bridge.BRIDGE_PACKAGE_PREFIX + portletName + "." + Bridge.EXCLUDED_REQUEST_ATTRIBUTES;
		portletContext.setAttribute(attributeName, getExcludedRequestAttributes());

		// Save the "javax.portlet.faces.preserveActionParams" init-param value as a portlet context attribute with name
		// "javax.portlet.faces.<portlet-name>.preserveActionParams"
		attributeName = Bridge.BRIDGE_PACKAGE_PREFIX + portletName + "." + Bridge.PRESERVE_ACTION_PARAMS;
		portletContext.setAttribute(attributeName, isPreserveActionParameters());

		// If a javax.portlet.faces.bridgeEventHandler is registered as an init-param in portlet.xml, then obtain an
		// instance of the handler and save it as a portlet context attribute as required by Section 3.2 of the JSR 329
		// Spec.
		BridgeEventHandler bridgeEventHandlerInstance = getBridgeEventHandler();

		if (bridgeEventHandlerInstance != null) {

			// Attribute name format: javax.portlet.faces.{portlet-name}.bridgeEventHandler
			attributeName = Bridge.BRIDGE_PACKAGE_PREFIX + portletConfig.getPortletName() + "." +
				Bridge.BRIDGE_EVENT_HANDLER;
			portletContext.setAttribute(attributeName, bridgeEventHandlerInstance);
		}

		// If a javax.portlet.faces.bridgePublicRenderParameterHandler is registered as an init-param in portlet.xml,
		// then obtain an instance of the handler and save it as a portlet context attribute as required by Section 3.2
		// of the JSR 329 Spec.
		BridgePublicRenderParameterHandler bridgePublicRenderParameterHandlerInstance =
			getBridgePublicRenderParameterHandler();

		if (bridgePublicRenderParameterHandlerInstance != null) {

			// Attribute name format: javax.portlet.faces.{portlet-name}.bridgePublicRenderParameterHandler
			String bridgeEventHandlerAttributeName = Bridge.BRIDGE_PACKAGE_PREFIX + portletConfig.getPortletName() +
				"." + Bridge.BRIDGE_PUBLIC_RENDER_PARAMETER_HANDLER;
			portletContext.setAttribute(bridgeEventHandlerAttributeName, bridgePublicRenderParameterHandlerInstance);
		}

		// If a javax.portlet.faces.defaultRenderKitId is specified as an init-param in WEB-INF/portlet.xml then
		// save it as a portlet context attribute as required by Section 4.2.16 of the JSR 329 Spec.
		String defaultRenderKitId = getDefaultRenderKitId();

		if (defaultRenderKitId != null) {
			portletContext.setAttribute(Bridge.BRIDGE_PACKAGE_PREFIX + portletConfig.getPortletName() + "." +
				Bridge.DEFAULT_RENDERKIT_ID, defaultRenderKitId);
		}

		// Determine whether or not all events should be auto-dispatched.
		String initParamValue = portletConfig.getInitParameter(BRIDGE_AUTO_DISPATCH_EVENTS);

		// TCK TestPage034: isAutoDispatchEventsSetTest
		autoDispatchEvents = ((initParamValue == null) || Boolean.parseBoolean(initParamValue));
	}

	/**
	 * Returns the value of the portlet initialization parameter <code>javax.portlet.faces.autoDispatchEvents</code> if
	 * non-null or <code>true</code>, otherwise.
	 *
	 * @return  boolean indicating whether to auto-dispatch all events to the bridge or not.
	 */
	public boolean isAutoDispatchEvents() {
		return autoDispatchEvents;
	}

	/**
	 * Returns a boolean indicating whether or not the bridge should preserve all the action parameters in the
	 * subsequent renders that occur in the same scope. This default implementation reads the values from the portlet
	 * init_param javax.portlet.faces.preserveActionParams. If not present, false is returned.
	 *
	 * @return  a boolean indicating whether or not the bridge should preserve all the action parameters in the
	 *          subsequent renders that occur in the same scope.
	 */
	public boolean isPreserveActionParameters() {

		if (preserveActionParameters == null) {

			// TCK TestPage016: initMethodTest
			String initParamName = Bridge.BRIDGE_PACKAGE_PREFIX + Bridge.PRESERVE_ACTION_PARAMS;
			String initParamValue = getPortletConfig().getInitParameter(initParamName);

			if (initParamValue != null) {
				preserveActionParameters = Boolean.parseBoolean(initParamValue);
			}
			else {
				preserveActionParameters = Boolean.FALSE;
			}
		}

		return preserveActionParameters;
	}

	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException,
		IOException {

		Bridge bridge = getFacesBridge(actionRequest, actionResponse);
		bridge.doFacesRequest(actionRequest, actionResponse);
	}

	@Override
	public void processEvent(EventRequest eventRequest, EventResponse eventResponse) throws PortletException,
		IOException {

		// If events are to be auto-dispatched to the bridge, then invoke the bridge implementation's handling of the
		// EVENT_PHASE.
		if (isAutoDispatchEvents()) {

			Bridge bridge = getFacesBridge(eventRequest, eventResponse);
			bridge.doFacesRequest(eventRequest, eventResponse);
		}

		// Otherwise, call the superclass method in GenericPortlet in order to let subclasses of GenericFacesPortlet
		// take advantage of the @ProcessEvent Portlet API annotation if desired.
		else {
			super.processEvent(eventRequest, eventResponse);
		}
	}

	@Override
	public void renderHeaders(HeaderRequest headerRequest, HeaderResponse headerResponse) throws PortletException,
		IOException {

		// TODO: FACES-2696
		super.renderHeaders(headerRequest, headerResponse);
	}

	/**
	 * Handles resource requests and dispatches to the Bridge.
	 *
	 * @param   resourceRequest
	 * @param   resourceResponse
	 *
	 * @throws  PortletException
	 * @throws  IOException
	 */
	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException, IOException {

		Bridge bridge = getFacesBridge(resourceRequest, resourceResponse);
		bridge.doFacesRequest(resourceRequest, resourceResponse);
	}

	/**
	 * If mode is VIEW, EDIT, or HELP -- defer to the doView, doEdit, doHelp so subclasses can override. Otherwise
	 * handle mode here if there is a defaultViewId mapping for it.
	 *
	 * @param   renderRequest
	 * @param   renderResponse
	 *
	 * @throws  PortletException
	 * @throws  IOException
	 */
	@Override
	protected void doDispatch(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		String autoNonFacesViewDispatch = getInitParameter("javax.portlet.faces.automaticNonFacesViewDispatching");
		String nonFacesTargetPath = renderRequest.getParameter(Bridge.NONFACES_TARGET_PATH_PARAMETER);

		if ((autoNonFacesViewDispatch != null) && autoNonFacesViewDispatch.equalsIgnoreCase("true") &&
				(nonFacesTargetPath != null)) {

			PortletContext portletContext = getPortletContext();
			String responseContentType = renderRequest.getResponseContentType();

			// TCK TestPage017: requestProcessingNonFacesTest
			renderResponse.setContentType(responseContentType);

			PortletRequestDispatcher portletRequestDispatcher = portletContext.getRequestDispatcher(nonFacesTargetPath);

			try {
				portletRequestDispatcher.forward(renderRequest, renderResponse);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected void doEdit(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		Bridge bridge = getFacesBridge(renderRequest, renderResponse);
		bridge.doFacesRequest(renderRequest, renderResponse);
	}

	@Override
	protected void doHeaders(RenderRequest renderRequest, RenderResponse renderResponse) {

		try {

			// Streaming portals like WebSphere (as opposed to buffered portals like Liferay) will set the
			// javax.portlet.render_part request attribute to a value of "RENDER_HEADERS" which will cause
			// javax.portlet.GenericPortlet (the superclass of this class) to call this doHeaders(RenderRequest,
			// RenderResponse) method, but will not in turn call GenericPortlet.doDispatch(RenderRequest,
			// RenderResponse). That also means that that the doView(RenderRequest, RenderResponse) will not be called
			// in this class. So if the attribute is set, we call the Bridge.doFacesRequest(RenderRequest,
			// RenderResponse) method here, so that the Faces lifecycle can be run, and resources added to
			// h:head can be retrieved. Note that it is the responsibility of the bridge to check for this
			// attribute as well, because at this point the bridge should not render any JSF views to the response.
			Object renderPartAttribute = renderRequest.getAttribute(RenderRequest.RENDER_PART);

			if ((renderPartAttribute != null) && renderPartAttribute.equals(RenderRequest.RENDER_HEADERS)) {
				Bridge bridge = getFacesBridge(renderRequest, renderResponse);
				bridge.doFacesRequest(renderRequest, renderResponse);
			}
		}
		catch (PortletException e) {

			// Unfortunately the signature for GenericPortlet.doHeaders(RenderRequest, RenderResponse) does not throw
			// an exception, so we have no choice but to simply report any exceptions by printing the stacktrace.
			e.printStackTrace();
		}
	}

	@Override
	protected void doHelp(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		Bridge bridge = getFacesBridge(renderRequest, renderResponse);
		bridge.doFacesRequest(renderRequest, renderResponse);
	}

	@Override
	protected void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		Bridge bridge = getFacesBridge(renderRequest, renderResponse);
		bridge.doFacesRequest(renderRequest, renderResponse);
	}

	/**
	 * Returns an instance of a BridgeEventHandler used to process portlet events in a JSF environment. This default
	 * implementation looks for a portlet initParameter that names the class used to instantiate the handler.
	 *
	 * @return  an instance of BridgeEventHandler or null if there is none.
	 *
	 * @throws  PortletException
	 */
	protected BridgeEventHandler getBridgeEventHandler() throws PortletException {

		if (bridgeEventHandler == null) {

			// TCK TestPage016: initMethodTest
			String initParamName = Bridge.BRIDGE_PACKAGE_PREFIX + Bridge.BRIDGE_EVENT_HANDLER;
			String className = getPortletConfig().getInitParameter(initParamName);

			if (className != null) {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

				try {
					Class<?> clazz = classLoader.loadClass(className);
					bridgeEventHandler = (BridgeEventHandler) clazz.newInstance();
				}
				catch (Exception e) {
					throw new PortletException(e);
				}
			}
		}

		return bridgeEventHandler;
	}

	/**
	 * Returns an instance of a BridgePublicRenderParameterHandler used to post process public render parameter changes
	 * that the bridge has pushed into mapped models. This default implementation looks for a portlet initParameter that
	 * names the class used to instantiate the handler.
	 *
	 * @return  an instance of BridgeRenderParameterHandler or null if there is none.
	 *
	 * @throws  PortletException
	 */
	protected BridgePublicRenderParameterHandler getBridgePublicRenderParameterHandler() throws PortletException {

		if (bridgePublicRenderParameterHandler == null) {

			// TCK TestPage016: initMethodTest
			String initParamName = Bridge.BRIDGE_PACKAGE_PREFIX + Bridge.BRIDGE_PUBLIC_RENDER_PARAMETER_HANDLER;
			String className = getPortletConfig().getInitParameter(initParamName);

			if (className != null) {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

				try {
					Class<?> clazz = classLoader.loadClass(className);
					bridgePublicRenderParameterHandler = (BridgePublicRenderParameterHandler) clazz.newInstance();
				}
				catch (Exception e) {
					throw new PortletException(e);
				}
			}
		}

		return bridgePublicRenderParameterHandler;
	}
}
