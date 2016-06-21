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

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;


/**
 * <p>The <code>Bridge</code> interface is used by a portlet to execute a JSF application. Its lifecycle follows the
 * pattern used by other web components such as portlets or servlets, namely:
 *
 * <ul>
 *   <li><code>init</code>: one time (per portlet) initialization. Usually invoked during portlet <code>init</code> but
 *     may also occur lazily. Context is passed to the Bridge at initialization via <code>PortletContext</code>
 *     attributes. See method description for details.</li>
 *   <li><code>doFacesRequest</code>: called for each portlet request that is to be handled by Faces. Must only be
 *     called after the bridge has been initialized.</li>
 *   <li><code>destroy</code>: called to destroy this bridge instance. Usually invoked during portlet <code>
 *     destroy</code> but may also occur earlier if the portlet decides to reclaim resources.</li>
 * </ul>
 * </p>
 *
 * <p>Portlet developers are encouraged to allow deployers an ability to configure the particular Bridge implementation
 * it uses within a given deployment. This ensures a best fit solution for a given application server, portlet
 * container, and/or Faces environment. The specifics for this configuration are undefined. Each portlet can define a
 * preferred mechanism. Subclasses of {@link GenericFacesPortlet} automatically inherit this behavior as it recognizes a
 * defined portlet initialization parameter.</p>
 *
 * <p>Implementations of this <code>Bridge</code> interface are required to have a zero-arg constructor.</p>
 *
 * @author  Michael Freedman
 * @author  Neil Griffin
 */
public interface Bridge {

	/**
	 * Portlet request attribute set by an include filter in recognition of the {@link #RENDER_CONTENT_AFTER_VIEW}
	 * attribute. Its value is either char[] or byte[] holding the {@link #AFTER_VIEW_CONTENT} generated while rendering
	 * a JSP. In conjunction with the bridge this enables preserving rendering order of native JSP rendering and Faces
	 * rendering in a JSP.
	 */
	public static final String AFTER_VIEW_CONTENT = "javax.portlet.faces.AfterViewContent";

	/**
	 * Special token parameter in the URL passed to the bridge's implementation of {@link
	 * javax.faces.context.ExternalContext#encodeResourceURL(String)} that it recognizes as an indication that an URL
	 * referring back to the page which contains this portlet should be encoded in the resource URL. This reference is
	 * encoded as the value of a query string parameter whose name is the value of this back link token parameter.
	 */
	public static final String BACK_LINK = "javax.portlet.faces.BackLink";

	/**
	 * Portlet context attribute that a portlet can set prior to calling {@link Bridge#init(PortletConfig)} to configure
	 * the bridge to use/call the associated {@link BridgeEventHandler} when processing an event. Value is an instance
	 * of <code>BridgeEventHandler</code>. As this attribute is scoped to a specific portlet in an application-wide
	 * context the attribute name must be include the portlet name as follows:
	 *
	 * <pre>"javax.portlet.faces." + portletContext.getPortletName() + ".bridgeEventHandler"</pre>
	 */
	public static final String BRIDGE_EVENT_HANDLER = "bridgeEventHandler";

	/** Base attribute/context parameter prefix. */
	public static final String BRIDGE_PACKAGE_PREFIX = "javax.portlet.faces.";

	/**
	 * Portlet context attribute that a portlet can set prior to calling the {@link #init(PortletConfig)} method to
	 * configure the bridge to use/call the associated {@link BridgePublicRenderParameterHandler}. This handler is used
	 * to process updates that result from public render parameter changes passed in a request. The bridge first pushs
	 * all the public render parameter values into the models and then calls this handler's processUpdates method. The
	 * handler can then compute further model changes based on the changes. Value is an instance of <code>
	 * BridgePublicRenderParameterHandler</code>. As this attribute is scoped to a specific portlet in an
	 * application-wide context the attribute name must be include the portlet name as follows:
	 *
	 * <pre>"javax.portlet.faces." + portletContext.getPortletName() + ".bridgeEventHandler"</pre>
	 */
	public static final String BRIDGE_PUBLIC_RENDER_PARAMETER_HANDLER = "bridgePublicRenderParameterHandler";

	/**
	 * Portlet context attribute that a portlet can set prior to calling the {@link #init(PortletConfig)} method to
	 * configure the bridge to default the renderKitId used for rendering this portlet to the named Id. In Faces, the
	 * default renderKitId is set in the faces-config.xml and is application wide. In 1.2 this can be overidden by a
	 * specially named request parameter. To allow differing portlets in the same app to use different default render
	 * kits, without having to add this parameter, the portlet can set this attribute prior to the bridge init(). The
	 * bridge will recognize this configuration value and on each request add the special faces request parameter to the
	 * request (if its not already present).
	 */
	public static final String DEFAULT_RENDERKIT_ID = "defaultRenderKitId";

	/**
	 * Portlet context attribute that a portlet must set prior to calling {@link Bridge#init(PortletConfig)} to convey
	 * to the bridge the set of default viewIds that correspond to this portlet's supported portlet modes. Its value is
	 * a {@link java.util.Map} with one entry per mode. The mode name is the key. The entry's value is the corresponding
	 * default viewId the bridge should use for this mode. As this attribute is scoped to a specific portlet in an
	 * application-wide context the attribute name must be include the portlet name as follows:
	 *
	 * <pre>"javax.portlet.faces." + portletContext.getPortletName() + ".defaultViewIdMap"</pre>
	 */
	public static final String DEFAULT_VIEWID_MAP = "defaultViewIdMap";

	/**
	 * Special token parameter in the URL passed to the bridge's implementation of {@link
	 * javax.faces.context.ExternalContext#encodeActionURL(String)} that it recognizes as an indication that this action
	 * should be treated as a direct link and hence shouldn't be encoded as a Portlet action. Rather, the call to <code>
	 * encodeActionURL</code> merely returns this URL unchanged.
	 */
	public static final String DIRECT_LINK = "javax.portlet.faces.DirectLink";

	/**
	 * Portlet context attribute that a portlet can set prior to calling {@link Bridge#init(PortletConfig)} to configure
	 * the bridge to exclude specific attributes from its bridge request scope. Value is a comma delimited list
	 * containing either a fully qualified attribute name or package name terminated with a ".*" wildcard indicator. In
	 * this later case, all attributes in the package name which precedes the ".*" are excluded, non recursive. As this
	 * attribute is scoped to a specific portlet in an application-wide context the attribute name must be include the
	 * portlet name as follows:
	 *
	 * <pre>"javax.portlet.faces." + portletContext.getPortletName() + ".excludedRequestAttributes"</pre>
	 */
	public static final String EXCLUDED_REQUEST_ATTRIBUTES = "excludedRequestAttributes";

	/**
	 * Special value recognized when encoding an action URL: It is a URL that contains either the {@link
	 * #FACES_VIEW_ID_PARAMETER} or {@link #FACES_VIEW_PATH_PARAMETER}. The bridge's implementation of {@link
	 * javax.faces.context.ExternalContext#encodeActionURL(String)} recognizes this value as indicating it needs to
	 * generate and encode an URL to the current JSF including its current state. For example: It not only encodes the
	 * link reference but also the existing render parameters so they can be carried forward to reestablish the state.
	 */
	public static final String FACES_USE_CURRENT_VIEW_PARAMETER = "_jsfBridgeCurrentView";

	/**
	 * Name of a request parameter (generally) encoded in a link from a non-Faces view response. It acts as a marker to
	 * the portlet that the non-Faces view intends to navigate to the Faces view expressed in the value of this
	 * parameter. It differs from the {@link #FACES_VIEW_PATH_PARAMETER} in that its value is the actual Faces viewId of
	 * the target while the former is a <code>ContextPath</code> relative path containing the viewId. Portlets receiving
	 * such a parameter should set the the corresponding request attribute {@link #VIEW_ID} before calling the bridge to
	 * handle the request.
	 */
	public static final String FACES_VIEW_ID_PARAMETER = "_jsfBridgeViewId";

	/**
	 * Name of a request parameter (generally) encoded in a link from a non-Faces view response. It acts as a marker to
	 * the portlet that the non-Faces view intends to navigate to the Faces view expressed in the value of this
	 * parameter. It differs from the {@link #FACES_VIEW_ID_PARAMETER} in that its value is a <code>ContextPath</code>
	 * relative path containing the viewId while the former is the viewId itself. Portlets receiving such a parameter
	 * should set the the corresponding request attribute <code>javax.portlet.faces.viewPath</code> before calling the
	 * bridge to handle the request.
	 */
	public static final String FACES_VIEW_PATH_PARAMETER = "_jsfBridgeViewPath";

	/**
	 * Special token parameter in the URL passed to the bridge's implementation of {@link
	 * javax.faces.context.ExternalContext#encodeResourceURL(String)} that it recognizes as an indication that this
	 * resource should be handled in-protocol.
	 */
	public static final String IN_PROTOCOL_RESOURCE_LINK = "javax.portlet.faces.InProtocolResourceLink";

	/**
	 * Portlet request attribute set by the bridge when processing a Faces request that signals this request is a Faces
	 * postback. Its provided as an alternative signal to the common reliance on the view state parameter as an
	 * indicator that this is a postback request. Implementations needing this information and not using the view state
	 * parameter indicator can check this attribute when running in a portlet environment.
	 */
	public static final String IS_POSTBACK_ATTRIBUTE = "javax.portlet.faces.isPostback";

	/**
	 * Context initialization parameter that specifies the maximum number of bridge request scopes to be preserved
	 * across all uses within this application.
	 */
	public static final String MAX_MANAGED_REQUEST_SCOPES = "javax.portlet.faces.MAX_MANAGED_REQUEST_SCOPES";

	/**
	 * Name of the render parameter set by the bridge when it encodes a navigation link to a non-Faces target. Though
	 * the bridge recognizes non-Faces targets when it encodes a navigational link, it does not handle the subsequent
	 * request. It only handles requests for Faces targets. It is the portlet's responsibility to detect and handle
	 * these requests. When the non-Faces target is a path based resource (such as a JSP or servlet), the <code>
	 * ContextPath</code> relative path of the resource is written as the value of this render parameter. For
	 * convenience, the <code>GenericFacesPortlet</code> recognizes this render parameter in received requests and uses
	 * the {@link javax.portlet.PortletRequestDispatcher} to dispatch to the encoded path instead of calling the bridge
	 * to execute the request.
	 */
	public static final String NONFACES_TARGET_PATH_PARAMETER = "_jsfBridgeNonFacesView";

	/**
	 * Portlet request attribute set by the bridge prior to creating/acquiring a {@link
	 * javax.faces.context.FacesContext}. Its value indicates which portlet phase this Faces is executing in. It can be
	 * used by Faces subsystems not only to determine the portlet execution phase but if present (not null) as an
	 * indication the request is being processed in a portlet container.
	 */
	public static final String PORTLET_LIFECYCLE_PHASE = "javax.portlet.faces.phase";

	/**
	 * Special token parameter in the URL passed to the bridge's implementation of {@link
	 * javax.faces.context.ExternalContext#encodeActionURL(String)} that it recognizes as an indication that this action
	 * should encode a portlet mode change to the one indicated by the parameter's value.
	 */
	public static final String PORTLET_MODE_PARAMETER = "javax.portlet.faces.PortletMode";

	/**
	 * Name of the portlet response property set by the bridge when it recognizes that the view has been rendered using
	 * a {@link javax.faces.component.NamingContainer} that ensures all generated ids are namespaced using the consumer
	 * provided unique portlet id.
	 */
	public static final String PORTLET_NAMESPACED_RESPONSE_PROPERTY = "X-JAVAX-PORTLET-FACES-NAMESPACED-RESPONSE";

	/**
	 * Special token parameter in the URL passed to bridge's implementation of {@link
	 * javax.faces.context.ExternalContext#encodeActionURL(String)} that it recognizes as an indication that this action
	 * should encode a security level change to the one indicated by the parameter's value.
	 */
	public static final String PORTLET_SECURE_PARAMETER = "javax.portlet.faces.Secure";

	/**
	 * Special token parameter in the URL passed to the bridge's implementation of {@link
	 * javax.faces.context.ExternalContext#encodeActionURL(String)} that it recognizes as an indication that this action
	 * should encode a window state change to the one indicated by the parameter's value.
	 */
	public static final String PORTLET_WINDOWSTATE_PARAMETER = "javax.portlet.faces.WindowState";

	/**
	 * Portlet context attribute that a portlet can set prior to calling {@link Bridge#init(PortletConfig)} to configure
	 * the bridge to preserve action parameters received by this portlet along with bridge's request scope so that they
	 * may be restored and acessed in subsequent renders. If <code>true</code>, the action parameters are preserved. If
	 * <code>false</code>, they are not preserved. The bridge default is <code>false</code>.<br>
	 * As this attribute is scoped to a specific portlet in an application-wide context the attribute name must be
	 * include the portlet name as follows:
	 *
	 * <pre>"javax.portlet.faces." + portletContext.getPortletName() + ".preserveActionParams"</pre>
	 */
	public static final String PRESERVE_ACTION_PARAMS = "preserveActionParams";

	/**
	 * Portlet request attribute set by the bridge in its {@link
	 * javax.faces.application.ViewHandler#renderView(javax.faces.context.FacesContext,
	 * javax.faces.component.UIViewRoot)} method prior to dispatching the request to the view (JSP) indicating a filter
	 * should put the {@link #AFTER_VIEW_CONTENT} in a buffer on the request for it to process after rendering the view
	 * components. In conjunction with the filter this enables preserving rendering order of native JSP rendering and
	 * Faces rendering in a JSP.
	 */
	public static final String RENDER_CONTENT_AFTER_VIEW = "javax.portlet.faces.RenderContentAfterView";

	/**
	 * Context initialization parameter that defines the policy the bridge uses for rendering. Parameter value is the
	 * string representation of one of the {@link BridgeRenderPolicy} enum values.
	 */
	public static final String RENDER_POLICY = "javax.portlet.faces.RENDER_POLICY";

	/**
	 * Context initialization parameter that defines the <code>SAVESTATE_FIELD_MARKER</code> in use in the given
	 * deployment. If not set, consult your bridge implementation documentation to determine which Faces implementations
	 * it automatically detects and supports. For example the bridge RI will detect and run properly in either the Faces
	 * RI or MyFaces environments without this being set.
	 */
	public static final String SAVESTATE_FIELD_MARKER = "javax.portlet.faces.SAVESTATE_FIELD_MARKER";

	/**
	 * Portlet request attribute that a portlet may set prior to calling the bridge's {@link
	 * #doFacesRequest(RenderRequest, RenderResponse)} method. The value of this attribute is a <code>String</code>
	 * representing the Faces viewId the bridge is to target for this request. Used by a portlet to specifically control
	 * a request's view target in situations such as navigating from a non-Faces view to a specific Faces view (other
	 * than the default). Generally, the use of this attribute is mutually exclusive with the use of {@link #VIEW_PATH}.
	 * If both have been set in a given request, the bridge gives precedence to {@link #VIEW_ID}.
	 */
	public static final String VIEW_ID = "javax.portlet.faces.viewId";

	/**
	 * Special token parameter in the URL passed to the bridge's implementation of {@link
	 * javax.faces.context.ExternalContext#encodeResourceURL(String)} that it recognizes as an indication that this URL
	 * refers to Faces view (navigation) and hence should be encoded as a portlet ActionURL rather then a portlet
	 * ResourceURL. This token is intended for use in URLs signifying a view navigation using components such as <code>
	 * h:outputLink</code>.
	 */
	public static final String VIEW_LINK = "javax.portlet.faces.ViewLink";

	/**
	 * Portlet request attribute that a portlet may set prior to calling the bridge's {@link
	 * #doFacesRequest(RenderRequest, RenderResponse)} method. The value of this attribute is a <code>String</code>
	 * containing a <code>ContextPath</code> relative path in which the Faces viewId is encoded. Like {@link #VIEW_ID},
	 * this attribute provides a means for a portlet to explicitly control the Faces target for a specific request. It
	 * is used in situations such as navigating from a non-Faces view to a specific Faces view (other than the default).
	 * Generally, the use of this attribute is mutually exclusive with the use of {@link #VIEW_PATH}. If both have been
	 * set in a given request, the bridge gives precedence to {@link #VIEW_ID}.
	 */
	public static final String VIEW_PATH = "javax.portlet.faces.viewPath";

	/**
	 * Portlet session attribute set by the bridge to hold the last viewId accessed in a given mode. The attribute (key)
	 * is composed of this name + the mode name. For example: javax.portlet.faces.viewIdHistory.view. There is one
	 * attribute per supported portlet mode. The attributes are always set even if the user session has never entered
	 * the mode. Its initial setting/value is determined by the default viewId configured for the mode. Attribute is
	 * used by developers to reference/return to the last view in a given mode from another mode.
	 */
	public static final String VIEWID_HISTORY = "javax.portlet.faces.viewIdHistory";

	/**
	 * Enumeration whose values describe the current portlet phase the bridge is executing Faces within.
	 */
	public static enum PortletPhase {
		ACTION_PHASE, EVENT_PHASE, RENDER_PHASE, RESOURCE_PHASE
	}

	/**
	 * Enumeration whose values describe the render policy used by the bridge to render portlets in this application. A
	 * policy of {@link BridgeRenderPolicy#DEFAULT} indicates the bridge will first delegate rendering and if this
	 * results in an exception being thrown will render the itself. A policy of {@link
	 * BridgeRenderPolicy#ALWAYS_DELEGATE} indicates the bridge will always delegate rendering, never rendering itself.
	 * A policy of {@link BridgeRenderPolicy#NEVER_DELEGATE} indicates the bridge will always render itself without
	 * delegating.
	 */
	public static enum BridgeRenderPolicy {
		DEFAULT, ALWAYS_DELEGATE, NEVER_DELEGATE,
	}

	/**
	 * Called by the portlet to take the bridge out of service. Once out of service, the bridge must be reinitialized
	 * before processing any further requests.
	 */
	public void destroy();

	/**
	 * Called by the portlet when it wants the bridge to invoke action request/response processing.
	 *
	 * @param   actionRequest   The current action request.
	 * @param   actionResponse  The current action response.
	 *
	 * @throws  BridgeDefaultViewNotSpecifiedException  thrown if the request indicates to the Bridge that is should use
	 *                                                  the default viewId and the portlet hasn't supplied one.
	 * @throws  BridgeUninitializedException            thrown if the bridge is not initialized.
	 * @throws  BridgeException                         - if an error occurs during action request/response processing.
	 */
	public void doFacesRequest(ActionRequest actionRequest, ActionResponse actionResponse)
		throws BridgeDefaultViewNotSpecifiedException, BridgeUninitializedException, BridgeException;

	/**
	 * Called by the portlet when it wants the bridge to invoke event request/response processing.
	 *
	 * @param   eventRequest   The current event request.
	 * @param   eventResponse  The current event response.
	 *
	 * @throws  BridgeUninitializedException  - if the bridge is not initialized.
	 * @throws  BridgeException               - if an error occurs during event request/response processing.
	 */
	public void doFacesRequest(EventRequest eventRequest, EventResponse eventResponse)
		throws BridgeUninitializedException, BridgeException;

	/**
	 * Called by the portlet when it wants the bridge to invoke render request/response processing.
	 *
	 * @param   renderRequest   The current render request.
	 * @param   renderResponse  The current render response.
	 *
	 * @throws  BridgeDefaultViewNotSpecifiedException  - if the request indicates to the Bridge that is should use the
	 *                                                  default viewId and the portlet hasn't supplied one.
	 * @throws  BridgeUninitializedException            - if the bridge is not initialized.
	 * @throws  BridgeException                         - if an error occurs during render request/response processing.
	 */
	public void doFacesRequest(RenderRequest renderRequest, RenderResponse renderResponse)
		throws BridgeDefaultViewNotSpecifiedException, BridgeUninitializedException, BridgeException;

	/**
	 * Called by the portlet when it wants the bridge to invoke resource request/response processing.
	 *
	 * @param   resourceRequest   The current resource request.
	 * @param   resourceResponse  The current resource response.
	 *
	 * @throws  BridgeUninitializedException  - if the bridge is not initialized.
	 * @throws  BridgeException               - if an error occurs during resource request/response processing.
	 */
	public void doFacesRequest(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws BridgeUninitializedException, BridgeException;

	/**
	 * <p>Called by the portlet. It indicates that the bridge is being placed into service.</p>
	 *
	 * <p>The portlet calls the <code>init</code> method exactly once before invoking other lifecycle methods. Usually,
	 * done immediately after instantiating the bridge. The <code>init</code> method must complete successfully before
	 * the bridge can receive any requests.</p>
	 *
	 * <p>The portlet cannot place the bridge into service if the <code>init</code> method throws a <code>
	 * BridgeException</code>.</p>
	 *
	 * <p>Initialization context is passed to bridge via <code>PortletContext</code> attributes. The following
	 * attributes are defined:
	 *
	 * <ul>
	 *   <li><code>javax.portlet.faces.encodeRedirectURL</code>: instructs the bridge to call <code>
	 *     ExternalContext.encodeActionURL()</code> before processing the redirect request. This exists because some
	 *     (newer) versions of JSF 1.2 call <code>encodeActionURL</code> before calling <code>redirect</code> while
	 *     others do not. This flag adjusts the behavior of the bridge in accordance with the JSF 1.2 implementation it
	 *     runs with.</li>
	 *   <li><code>javax.portlet.faces.MAX_MANAGED_REQUEST_SCOPES</code>: defines the maximum number of bridge request
	 *     scopes this bridge preserves at any given time. Value is an integer. Bridge request scopes are managed on a
	 *     per Bridge class portlet context wide basis. As a typical portlet application uses the same bridge
	 *     implementation for all its Faces based portlets, this means that all bridge request scopes are managed in a
	 *     single bucket.<br>
	 *     For convenience this interface defines the {@link #MAX_MANAGED_REQUEST_SCOPES} constant.</li>
	 *   <li><code>javax.faces.lifecycleID</code>: defines the Faces <code>Lifecycle</code> id that bridge uses when
	 *     acquiring the <code>Faces.Lifecycle</code> via which it executes the request. As a context wide attribute,
	 *     all bridge instances in this portlet application will use this lifecycle.</li>
	 *   <li><code>javax.portlet.faces.[portlet name].preserveActionParams</code>: instructs the bridge to preserve
	 *     action parameters in the action scope and represent them in subsequent renders. Should be used only when
	 *     binding to a Faces implementation that relies on accessing such parameters during its render phase. As this
	 *     is a portlet/bridge instance specific attribute, the <code>PortletContext</code>attribute name is qualified
	 *     by the portlet instance name. This allows different portlets within the same portlet application to have
	 *     different settings.<br>
	 *     For convenience this interfaces defines a number of constants that simplifies constructing and/or recognizing
	 *     this name.</li>
	 * </ul>
	 * </p>
	 *
	 * @param   portletConfig  <code>PortletConfig</code> object containing the portlet's configuration and
	 *                         initialization parameters
	 *
	 * @throws  BridgeException  - if an exception has occurred that interferes with the bridge's normal operation. For
	 *                           example, if the bridge is already initialized.
	 */
	public void init(PortletConfig portletConfig) throws BridgeException;
}
