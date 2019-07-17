_Version: 5.0.0-edr3-SNAPSHOT_

# 6. Managing Faces

* * *

The majority of the Bridge's control function is implemented by overriding various Faces extension points to translate
between the corresponding Faces function and the Portlet model.

## <a name="6.1"></a>6.1 ExternalContext

The Faces `ExternalContext` provides the container independent abstraction of the request and response to the Faces
runtime environment. By implementing an `ExternalContext`, the portlet bridge provides access to the portlet request and
response.

The `ExternalContext` is acquired from the `FacesContext`. To provide an `ExternalContext` implementation, the bridge
must provide a `FacesContext` implementation. A `FacesContext` is manufactured via a FacesContextFactory [Section 6.5 of
JSF 1.2 Specification]. The bridge must use the Faces `FactoryFinder` to acquire the `FacesContextFactory` which it then
uses to instantiate and acquire a `FacesContext`.

### <a name="6.1.1"></a>6.1.1 FacesContextFactory

The bridge provides an implementation of `FacesContextFactory` in order to expose its `ExternalContext`. This
`FacesContextFactory` must be configured as a service provider in the `META-INF/services` directory of the Java jar file
containing the bridge implementation:

The file `META-INF/services/javax.faces.context.FacesContextFactory` contains the name of the bridge's concrete
`FacesContextFactory` implementation class<sup>[[6.1](tck-tests.md#6.1)]</sup>.

The bridge's implementation of the `FacesContextFactory` must follow the decorator design pattern such that there is a
constructor with a single argument of type `FacesContextFactory` so that the custom implementation receives a reference
to the implementation that was previously fulfilling the role. In this way, the custom implementation is able to
override just a subset of the functionality (or provide only some additional functionality) and delegate the rest to the
existing implementation.

In implementing the `getFacesContext` method, the bridge must provide its own instance of the `FacesContext` without
delegating if and only if the request is being serviced by a portlet in the portlet container. Otherwise the bridge must
delegate to the previous factory and perform no other action.

The bridge must not assume that the `FacesContext` returned by calling `getFacesContext` is an *instanceof* its
`FacesContext` implementation class<sup>[[6.2](tck-tests.md#6.2)]</sup>. Other Faces subsystems running in the portlet
bridge environment are free to introduce their own factory and wrapping implementation as long as they follow the
decorator pattern described above.

### <a name="6.1.2"></a>6.1.2 FacesContext

`FacesContext` contains all of the per-request state information related to the processing of a single Faces request and
the rendering of the corresponding response. It is passed to, and potentially modified by, each phase of the request
processing lifecycle.

A `FacesContext` instance is associated with a particular request at the beginning of request processing, by a call to
the `getFacesContext()` method of the `FacesContextFactory` instance associated with the current web application. The
instance remains active until its `release()` method is called, after which no further references to this instance are
allowed. While a `FacesContext` instance is active, it must not be referenced from any thread other than the one upon
which the servlet container executing this web application utilizes for the processing of this
request<sup>[[nt](tck-tests.md#nt)]</sup>.

Because there are subtle differences between the servlet and portlet models, its recommended that the bridge's
`FacesContext` implementation not delegate any method implementations. For each method implemented by the bridge's
`FacesContext`, the implementation must meet all the requirements for that method as defined in the JSF 1.2
specification.

In addition, the bridge's `FacesContext` is required to do the following:

- `setViewRoot()`:

If the class of the `UIViewRoot` passed to `setViewRoot` is annotated with
`javax.portlet.faces.annotation.PortletNamingContainer` then it additionally must set the
`"X-JAVAX-PORTLET-FACES-NAMESPACED-RESPONSE"` portlet response property with a String value of `"true"`. For further
explanation see section [6.6](chapter-6-managing-faces.md#6.6).

### <a name="6.1.3"></a>6.1.3 ExternalContext Methods

The Faces 1.2 `ExternalContext` javadoc describes specific behaviors for each method executed in the portlet request
context. By and large these descriptions are complete and accurate. Section
[6.1.3.2](chapter-6-managing-faces.md#6.1.3.2) provides these descriptions. However, a few methods aren't adequately
described or are incorrect. For these the bridge must ignore the existing javadoc and implement the behavior as
described in the next section [6.1.3.1](chapter-6-managing-faces.md#6.1.3.1).

#### Relationship of Faces 1.2 ViewHandler URL Methods to ExternalContext URL Methods

The Faces 1.2 API defined **two types of URLs** that could be rendered to the response: _action_ and _resource_. It also
established a 1::1 correspondence between "getting" a URL from the view handler and "encoding" a URL via external
context.

The [ViewHandler.getActionURL(FacesContext facesContext,String
viewId)](http://docs.oracle.com/javaee/5/api/javax/faces/application/ViewHandler.html#getActionURL%28javax.faces.context.FacesContext%2C%20java.lang.String%29)
method was designed to return a URL "suitable for rendering" that "selects the specified view identifier" but does not
encode the URL in any specific way. Instead, it is the responsibility of the caller to subsequently call
[ExternalContext.encodeActionURL(String)](http://docs.oracle.com/javaee/5/api/javax/faces/context/ExternalContext.html#encodeActionURL%28java.lang.String%29)
in order to obtain an encoded URL that can be written to the response. For example, Mojarra's
[com.sun.faces.renderkit.html_basic.FormRenderer](https://github.com/javaserverfaces/mojarra/blob/JSF_1_2_15_B02/jsf-ri/src/com/sun/faces/renderkit/html_basic/FormRenderer.java#L198)
class follows this convention when writing the `"action"` attribute of a `<form>` element.

Similarly, the [ViewHandler.getResourceURL(FacesContext facesContext, String
path)](http://docs.oracle.com/javaee/5/api/javax/faces/application/ViewHandler.html#getResourceURL%28javax.faces.context.FacesContext%2C%20java.lang.String%29)
method was designed to return a URL "suitable for rendering" that "selects the specified web application resource" but
does not encode the URL in any specific way. Instead, it is the responsibility of the caller to subsequently call
[ExternalContext.encodeResourceURL(String)](http://docs.oracle.com/javaee/5/api/javax/faces/context/ExternalContext.html#encodeResourceURL%28java.lang.String%29)
in order to obtain an encoded URL that can be written to the response. For example, Mojarra's
[com.sun.faces.renderkit.html_basic.ImageRenderer](https://github.com/javaserverfaces/mojarra/blob/JSF_1_2_15_B02/jsf-ri/src/com/sun/faces/renderkit/html_basic/ImageRenderer.java#L134)
class follows this convention when writing the `"src"` attribute of an `<img>` element.

Upon closer examination of the Faces 1.2 JavaDoc for `ExternalContext.encodeActionURL(String url)` and
`ExternalContext.encodeResourceURL(String url)`, the following requirements are common to both:
> Servlet: This must be the value returned by [javax.servlet.http.HttpServletResponse.encodeURL(String url)](http://docs.oracle.com/javaee/5/api/javax/servlet/http/HttpServletResponse.html#encodeURL%28java.lang.String%29).
>
> Portlet: This must be the value returned by [javax.portlet.PortletResponse.encodeURL(String url)](http://portals.apache.org/pluto/portlet-2.0-apidocs/javax/portlet/PortletResponse.html#encodeURL%28java.lang.String%29).

The requirement for the "Servlet" use-case is correct, but as section [6.1.3.1](chapter-6-managing-faces.md#6.1.3.1)
explains, the requirement for the "Portlet" use-case is not adequately described in that it does not take into account
all the features of the FacesBridge. Regardless, the Faces 1.2 JavaDoc reveals that **the original intent** for these
methods was to be a simple layer of abstraction to encode/rewrite URLs (such as calling
`HttpServletResponse.encodeURL(String)` in order to append the `jessionid` URL parameter when the user-agent does not
support cookies). Further developed in the next section, this **original intent** helps to explain why Faces 2.0
requires "bookmarkable" and "redirect" URLs to be encoded by calling `ExternalContext.encodeActionURL(String url)`
before being written to the response.

#### Relationship of Faces 2.0 ViewHandler URL Methods to ExternalContext URL Methods

The Faces 2.0 API added two additional methods for getting URLs from the view handler:
[ViewHandler.getBookmarkableURL(FacesContext facesContext, String viewId, Map&lt;String,List&lt;String&gt;&gt; parameters, boolean
includeViewParams)](http://docs.oracle.com/javaee/6/api/javax/faces/application/ViewHandler.html#getBookmarkableURL%28javax.faces.context.FacesContext%2C%20java.lang.String%2C%20java.util.Map%2C%20boolean%29)
and [ViewHandler.getRedirectURL(FacesContext facesContext, String viewId, Map&lt;String,List&lt;String&gt;&gt; parameters, boolean
includeViewParams)](http://docs.oracle.com/javaee/6/api/javax/faces/application/ViewHandler.html#getRedirectURL%28javax.faces.context.FacesContext%2C%20java.lang.String%2C%20java.util.Map%2C%20boolean%29).
However, the Faces 2.0 API **did not add two new types of URLs**. Rather, both "bookmarkable" and "redirect" URLs are
simply considered to be Faces _action_ URLs (i.e. they target a Faces view). Because of this, both "bookmarkable" and
"redirect" URLs must ultimately be encoded by calling `ExternalContext.encodeActionURL(String)` before they are
written to the response.

The Faces 2.0 API maintained the 1::1 correspondence between view handler URL methods and external context URL methods
by introducing [ExternalContext.encodeBookmarkableURL(String
url)](http://docs.oracle.com/javaee/6/api/javax/faces/context/ExternalContext.html#encodeBookmarkableURL%28java.lang.String%2C%20java.util.Map%29)
and [ExternalContext.encodeRedirectURL(String
url)](http://docs.oracle.com/javaee/6/api/javax/faces/context/ExternalContext.html#encodeRedirectURL%28java.lang.String%2C%20java.util.Map%29).
However, the implementation requirements **departed from the pattern** of having the caller of the view handler methods
to be responsible for calling the corresponding external context methods. Instead, `ViewHandler.getBookmarkableURL(...)`
would itself be required to first call `ExternalContext.encodeBookmarkableURL(String url)` and pass the result to
`ExternalContext.encodeActionURL(String url)` before returning the value. Similarly, `ViewHandler.getRedirectURL(...)`
would itself be required to first call `ExternalContext.encodeRedirectURL(String url)` and pass the result to
`ExternalContext.encodeActionURL(String url)` before returning the value. As a consequence of this,
`ViewHandler.getBookmarkableURL(...)` and `ViewHandler.getRedirectURL(...)` both return fully encoded URLs that can be
written to the response. For example, Mojarra's
[com.sun.faces.renderkit.html_basic.OutcomeTargetRenderer](https://github.com/javaserverfaces/mojarra/blob/2.2.13/jsf-ri/src/main/java/com/sun/faces/renderkit/html_basic/OutcomeTargetRenderer.java#L194)
assumes that the return value of `ViewHandler.getBookmarkableURL(...)` can be written directly to the response.

In order to facilitate Ajax, the Faces 2.0 API introduced a **new type of URL** called the _partial action_ URL.
However, the Faces 2.0 view handler API does not contain a "getPartialActionURL" method. Instead, the value returned by
the `ViewHandler.getActionURL(FacesContext facesContext, String viewId)` method is to be passed to the
[ExternalContext.encodePartialActionURL(String
url)](http://docs.oracle.com/javaee/6/api/javax/faces/context/ExternalContext.html#encodePartialActionURL%28java.lang.String%29)
method before it can be written to the response. This **departs from the pattern** of 1::1 correspondence between
URL methods in view handler and external context.

#### <a name="6.1.3.1"></a>6.1.3.1 Methods that deviate from Faces 1.2 Javadoc

The following methods require an implementation that aren't adequately described in the Faces 1.2 `ExternalContext`
javadoc:

- `dispatch()`:

	Dispatch a request to the specified resource to create output for this response. This must be accomplished by
	calling the `javax.portlet.PortletContext` method `getRequestDispatcher()`, and calling the
	`forward()`<sup>[[6.3](tck-tests.md#6.3)]</sup> method on the resulting object unless `dispatch()` has already been
	called (at least once) within this portlet request. If `dispatch()` has already been called within this portlet
	request, then `include()` may be called rather than `forward()`. This situation might occur in a bridge that doesn't
	wrap the response prior to dispatch with a wrapper that prevents committing the response which happens implicitly at
	the completion of the forward processing.

	**Note:** Because the Portlet 1.0 bridge was restricted to using `include()`, rendering may appear differently when
	*the application runs in a 1.0 vs. a 2.0 bridge. Specifically, in a Portlet 2.0 bridge any markup written prior to
	*calling `dispatch` will not be returned to the client, while it will in a Portlet 1.0 bridge environment. For
	*(potential) backwards compatibility a bridge may provide an implementation specific mechanism to be configured to
	*support portlet 1.0 bridge dispatch behavior (only use `include`) as long as this is not the default behavior.

- `encodeActionURL()`:

	Process the `inputURL` and construct and encode a corresponding valid portlet (generally action) URL. If called
	during the `HEADER_PHASE` or `RESOURCE_PHASE`, return this URL so it can be written into the markup response. If
	called during the `ACTION_PHASE`, directly encode this URL in the action response. The value and form of the URL
	returned when running in the `ACTION_PHASE` is undefined although it must not be `null`. If called during the
	`EVENT_PHASE`, directly encode this URL in the event response. The value and form of the URL returned when running
	in the `EVENT_PHASE` isundefined although it must not be `null`.
	
	To process such an `inputURL` correctly, this method must:

    - If the `inputURL` starts with the # character<sup>[[6.4](tck-tests.md#6.4)]</sup> or the `inputURL` is an
    absolute path external to this portlet application<sup>[[6.5](tck-tests.md#6.5)]</sup> return the `inputURL`
    unchanged.
    - If the `inputURL` contains the parameter `javax.portlet.faces.DirectLink` (with a value of "true") return an
    absolute path derived from the `inputURL`. Don't remove the `DirectLink` parameter if it
    exists<sup>[[6.6](tck-tests.md#6.6)]</sup>. If the `inputURL` contains the parameter
    `javax.portlet.faces.DirectLink` and its value is false then remove the `javax.portlet.faces.DirectLink` parameter
    and its value from the query string and continue processing (using the next step concerning determining the target
    of the `URL`)<sup>[[6.7](tck-tests.md#6.7)]</sup>.
    - Determine the target of the `URL`:
        - If the `inputURL` starts with the URI scheme `"portlet:"` the target is the portlet itself. Interpret this
        `inputURL` as follows:
            - The scheme "`portlet:`" indicates that the target of this action is the portlet itself. Though generally
            used to generate links to non-Faces views in this portlet it can also be used to generate action or render
            links to a Faces view (including the current view).
            - The scheme is followed by either the keyword `action`, `render` or `resource`. `render` indicates a
            portlet `renderURL` should be encoded<sup>[[6.8](tck-tests.md#6.8)]</sup>. `action` indicates a portlet
            `actionURL` should be encoded<sup>[[6.9](tck-tests.md#6.9)]</sup>. `resource` indicates a portlet
            `resourceURL` should be encoded<sup>[[6.102](tck-tests.md#6.102)]</sup>.
            - Following this URL type indicator is an optional query string. Parameter value pairs in the query string
            are the parameters that are to be encoded into the `portletURL`.
            <br />
            <br />
            To generate a link to a Faces view, encode the view as the value of either the `_jsfBridgeViewId` or
            `_jsfBridgeViewPath` parameter (depending on whether you are encoding the viewId or the viewPath). Targets
            of such references are run in new empty scopes. An exception is made when the target is the current view and
            either of the above parameters is included in the query string with a value of `_jsfBridgeCurrentView`. In
            this case the URL is encoded with the current render parameters and hence retains access to its state/scope.
            In all cases the bridge removes the above parameter(s) from the query string before generating the encoded
            URL.
            <br />
            <br />
            For a resource URL, a Faces view is only encoded if one of the `_jsfBridgeViewId` or `_jsfBridgeViewPath`
            parameters is included in the query string, otherwise a non-Faces resource URL is generated. The
            `_jsfBridgeCurrentView` value is used as a shortcut to indicate the resource targets the current view.
            <br />
            <br />
            A simplified BNF is:<br />
            `portletURL = Scheme URLType [QueryString]`<br />
            `Scheme = "portlet:"`<br />
            `URLType = ("action" | "render" | "resource")`<br />
            `QueryString = "?" ParameterPair {"&" ParameterPair}`<br />
            `ParameterPair =  Text "=" Text`<br />
            <br />
         - If the `inputURL` contains a full or relative path then analyze the path information to determine whether it
         encodes a request to a Faces view or a non-Faces view. If it is a reference to a Faces view the target is the
         encoded Faces viewId<sup>[[6.10](tck-tests.md#6.10), [6.18](tck-tests.md#6.18)]</sup>. If it is a reference
         to a non-Faces view, the target is the path that follows the context path (or if relative, constructed relative
         to the current view path).
             - To determine if the `URL` represents a Faces request, determine if the servlet container would map this
             URL to the Faces servlet. This can be done by reading the servlet definitions in the web application's
             `web.xml` to determine how the Faces servlet is mapped and then checking the `URL` to see if it would be
             mapped. If a Faces `URL`, derive the view identifier encoded in this `URL`, as follows: (**Note**: all
             resulting `viewIds` start with a "/")
                 - If prefix mapping (such as "/faces/*") is used for the `FacesServlet`, the `viewId` is set from the
                 extra path information of the request `URI`. This corresponds to the path that follows the prefix
                 mapping. e.g. a prefix mapped URL such as /faces/mypage.jsp would have a `viewId` of "/mypage.jsp".
                 - If suffix mapping (such as "*.faces") is used for `FacesServlet`, the `viewId` is set from the
                 servlet path information of the request URI, after replacing the suffix with the value of the context
                 initialization parameter named by the symbolic constant `ViewHandler.DEFAULT_SUFFIX_PARAM_NAME` (if no
                 such context initialization parameter is present, use the value of the symbolic constant
                 `ViewHandler.DEFAULT_SUFFIX` as the replacement suffix). This corresponds to the path that follows the
                 context path. e.g. a suffix mapped URL such as /pages/secondpage.jsf would have a `viewId` of
                 "/pages/secondpage.jsp" (assuming .jsp is the replacement suffix).
    - Process PortletURL related state modifications.
        - The portlet model allows one to encode changes to the portlet's mode, window state and or security (access)
        level in self-referencing URLs or as a response to an action request. Because the form of this encoding is not
        specified by the portlet standard these values aren't manipulated in the portlet URL's query string. Rather
        changes are expressed by operating directly on a `PortletURL` object (header phase) or the `actionResponse`
        (action phase). As `encodeActionURL()` isolates Faces clients from these APIs/objects, an alternative technique
        is provided so `encodeActionURL()` can recognize and incorporate the necessary changes. This technique
        recognizes special query string parameters in the `inputURL` which are extracted and processed using the
        corresponding APIs. encodeActionURL() processing must:
            - recognize the query string parameter `javax.portlet.faces.PortletMode` and use the value of this parameter
            to identify the mode that should be encoded in the generated reference<sup>[[6.11](tck-tests.md#6.11),
            [6.19](tck-tests.md#6.19)]</sup>. If the value doesn't identify a valid mode then no encoding action is
            taken<sup>[[6.12](tck-tests.md#6.12), [6.19](tck-tests.md#6.19)]</sup>. Regardless of validity, it must
            also prevent this query string parameter from being carried forward directly in the generated reference.
            - recognize the query string parameter `javax.portlet.faces.WindowState` and use the value of this parameter
            to identify the window state that should be encoded in the generated
            reference<sup>[[6.13](tck-tests.md#6.13), [6.21](tck-tests.md#6.21)]</sup>. If the value doesn't
            identify a valid window state then no encoding action is taken<sup>[[6.14](tck-tests.md#6.14),
            [6.22](tck-tests.md#6.22)]</sup>. Regardless of validity, it must also prevent this query string parameter
            from being carried forward directly in the generated reference.
            - recognize the query string parameter `javax.portlet.faces.Secure` and use the value of this parameter to
            identify the security level that should be encoded in the generated
            reference<sup>[[6.15](tck-tests.md#6.15), [6.23](tck-tests.md#6.23)]</sup>. A value of true or false is
            translated into the boolean true/false respectively regardless of case. Any other value is
            ignored<sup>[[6.16](tck-tests.md#6.16), [6.24](tck-tests.md#6.24)]</sup>. Regardless of validity, it
            must also prevent this query string parameter from being carried forward directly in the generated
            reference.
            - All other query string parameters are added to the PortletURL as
            parameters<sup>[[6.17](tck-tests.md#6.17), [6.25](tck-tests.md#6.25)]</sup>.
    - Perform the encoding: 
        - If executed during the `ACTION_PHASE` encode into the `ActionResponse` and return a non-null `URL` as
        follows<sup>[[6.10-6.17](tck-tests.md#6.10)]</sup>:
            - the target, if the target is not the portlet itself (as identified by use of the `portlet: URI`). When the
            target is a Faces `viewId`, it is encoded in an implementation dependent manner. When the target is a
            non-Faces view, the target is encoded as the value of the parameter named `_jsfBridgeNonFacesView`.
            - the identified state modifications
            - any additional query string parameters that were in the `inputURL`.
         - If executed during the `EVENT_PHASE` encode into the `EventResponse` and return a non-null `URL` as
         follows<sup>[[6.103-6.110](tck-tests.md#6.103)]</sup>:
             - the target (see action description)
             - the identified state modifications
             - any additional query string parameters that were in the `inputURL`.
         - If executed during the `HEADER_PHASE` or `RESOURCE_PHASE` and the target was determined by URL path (not
         portlet: syntax) and that target is a Faces viewId, construct and return an `actionURL` by calling
         `getResponse().createActionURL().toString()` as follows<sup>[[6.18-6.25](tck-tests.md#6.18),
         [6.111-6.118](tck-tests.md#6.111)]</sup>:
             - encode the target determined above (in an implementation dependent manner).
             - the identified state modifications,
             - any additional query string parameters that were in the `inputURL`.
         - If executed during the `HEADER_PHASE` or `RESOURCE_PHASE` and the target was determined by its URL path (not
         portlet: syntax) and that target is a non-Faces `viewId`, construct and return a `renderURL` by calling
         `getResponse().createRenderURL().toString()` as follows<sup>[[6.136-6.152](tck-tests.md#6.136)]</sup>:
             - encode the target determined above as the value of the parameter named `_jsfBridgeNonFacesView`.
             - the identified state modifications.
             - any additional query string parameters that were in the `inputURL`.
         - If executed during the `HEADER_PHASE` or `RESOURCE_PHASE` and the target was determined from the portlet:
         syntax (not by path), construct the appropriate `URL` type determined from the path portion of the based on the
         `urlTypeInputURL` and return its value in `String` form (using `toString()`) as follows:
             - encode the identified state modifications.
             - any additional query string parameters that were in the `inputURL`.
             - if the target of this URL is the current JSF view as determined by the use of the `_jsfBridgeCurrentView`
             value, additionally encode all current render parameters into the portletURL. Return
             `portletURL.toString()`.
         <br />
         <br />
         Note on encoding/xml escaping: because renderkits have their own pre/post processing to deal with situations
         calling for xml escaping in URLs, the bridge must return an URL that contains the identical xml escaping (or
         not) used in the URL passed to encodeActionURL. I.e. if the incoming URL is xml escaped the returned URL
         must also be xml escaped, likewise if the incoming URL isn't escaped the returned URL must not be escaped. In
         the case xml escaping can't be determined from the incoming URL, the bridge must assume the URL is not xml
         escaped and return an unescaped URL accordingly.<sup>[[6.99](tck-tests.md#6.99)]</sup>

- `encodeResourceURL()`:

	In Portlet 1.0 resources were not served by the portlet but rather accessed directly by the consumer using an http
	(resource) request. Portlet 2.0 introduces a second type of resourceURL, a (in-protocol) portlet served resource. A
	resourceURL of this type signifies that the portlet itself should be called by the consumer to generate the
	resource. The Portlet 2.0 APIs provide distinct calls for creating a reference to portlet served resource to one
	that is accessed directly. As Faces only has a single concept of a resourceURL, the bridge uses a heuristic to
	determine which of these two access methods it uses to reference the resource. In summary, resourceURLs that target
	a Faces view are constructed to be served by the portlet while those that don't target a Faces view are constructed
	to be accessed directly.

	This method returns the inputURL after performing any rewriting needed to ensure that it will correctly identify an
	addressable resource. To process such an inputURL correctly, this method must:

    - If the `inputURL` is *opaque*, in that it is an absolute URI with a scheme-specific part that doesn't begin with a
    slash character (e.g. `mailto:java-net@java.sun.com`) and the scheme isn't portlet:, return the `inputURL`
    unchanged<sup>[[6.26](tck-tests.md#6.26)]</sup>.
    - If the inputURL 's scheme specific part is portlet: or its not opaque and its query string contains the parameter
    javax.portlet.faces.ViewLink with a value equal to true[6.34], then return an URL representing a view navigation.
    This is done by:
        - if its exists, remove the `javax.portlet.faces.ViewLink` query string parameter. **Note**: This supports use
        cases such as using h:outputLink to navigate to a new view.
        - if it exists, replace the `javax.portlet.faces.BackLink` query string parameter with a parameter whose name is
        the value of this parameter and whose value is the `String` (`URL` path) returned after calling
        `ViewHandler.getActionURL()` passing the current `viewId`<sup>[[6.27](tck-tests.md#6.27)]</sup>.
        <br />
        <br />
        This encodes a "back link" into the resource URL allowing the targeted resource a way back to the calling
        portlet page for use in the situation where the `resourceURL` actually is used as a page link. E.g. use of
        `h:outputLink`.
        - returning the result of calling `encodeActionURL` passing this transformed
        `inputURL`<sup>[[6.33](tck-tests.md#6.33), [6.34](tck-tests.md#6.34)]</sup>.
    - If the `inputURL` is *hierarchical*, in that it is either an an absolute URI whose scheme-specific part begins
    with a slash character, or a relative URI, that is, a URI that does not specify a scheme, and it targets a resource
    that is external to this application:
        - check to see if the `inputURL` contains a query string parameter named `javax.portlet.faces.BackLink`. If it
        does replace it with a parameter whose name is the value of this parameter and whose value is the `String`
        (`URL` path) returned after calling `ViewHandler.getActionURL()` passing the current `viewId` followed by
        `ExternalContext.encodeActionURL()`<sup>[[6.27](tck-tests.md#6.27)]</sup>.
        <br />
        <br />
        This encodes a "back link" into the resource `URL` allowing the targeted resource a way back to the calling
        portlet page for use in the situation where the `resourceURL` actually is used as a page link. E.g. use of
        `h:outputLink`.
        <br />
        <br />
        - return `getResponse().encodeURL(inputURL)`<sup>[[6.28](tck-tests.md#6.28)]</sup>.
    - If the `inputURL` is *hierarchical* and targets a resource that is within this application:
        - if the `inputURL` is a relative `URL` (i.e. it is neither absolute nor starts with a '/') then the `inputURL`
        must be turned into a context path relative URL by constructing a new URL based on going relative to the current
        path<sup>[[6.29](tck-tests.md#6.29)]</sup>. The current path is defined as the path that would be used to
        `dispatch()` to the current view.
        - ensure that the `inputURL` (potentially modified by the previous step) is a fully qualified path `URI` (i.e.
        contains the context path)<sup>[[6.31](tck-tests.md#6.31)]</sup>.
        - if the resulting `inputURL` contains a query string parameter named `javax.portlet.faces.BackLink` then
        replace it with a parameter whose name is the value of this parameter and whose value is the `String` (`URL`
        path) returned after calling `ViewHandler.getActionURL()` passing the current
        `viewId`<sup>[[6.30](tck-tests.md#6.30)]</sup>.
        <br/><br />
        This encodes a "back link" into the resource URL allowing the targeted resource a way back to the calling
        portlet page. E.g. use of `h:outputLink`.
        - determine whether the targeted resources will be satisfied using Portlet 2.0 in protocol resource serving
        support or Portlet 1.0 out of band http support. The bridge must encode the resource to be satisfied by the in
        protocol resource serving support if:
            - the query string contains the parameter `javax.portlet.faces.InProtocolResourceLink`
            - the target is a Faces resource, one that in a servlet environment would be processed running through the
            `FacesServlet`.
        <br/><br />
        **Note**: The bridge can choose the strategy for acquiring (representing) all other resources though it is noted
        that out of band (http) resource access is generally more efficient.
        - if returning an in protocol resource, return the `String` representation of a `resourceURL` created using
        `response.createResourceURL()` after processing `PortletURL` related state modifications:
            - as you can't change a portlet's mode in a resource request, remove and ignore the query string parameter
            `javax.portlet.faces.PortletMode` if it exists.
            - as you can't change a portlet's window state in a resource request, remove and ignore the query string
            parameter `javax.portlet.faces.WindowState` if it exists.
            - recognize the query string parameter `javax.portlet.faces.Secure` and use the value of this parameter to
            identify the security level that should be encoded in the generated reference. A value of `true` or `false`
            is translated into the `boolean true/false` respectively regardless of case. Any other value is ignored.
            Regardless of validity, it must also prevent this query string parameter from being carried forward directly
            in the generated reference.
            - All other query string parameters are added to the `ResourceURL` as parameters.
            - if returning an out of band (http) resource return
            `getResponse().encodeURL(inputURL)`<sup>[[6.28](tck-tests.md#6.28)]</sup>.
        <br /><br />
        **Note on encoding/xml escaping**: because renderkits have their own pre/post processing to deal with situations
        calling for xml escaping in URLs, the bridge must return an URL that contains the identical xml escaping (or
        not) used in the URL passed to `encodeActionURL`. I.e. if the incoming URL is xml escaped the returned URL
        must also be xml escaped, likewise if the incoming URL isn't escaped the returned URL must not be escaped. In
        the case xml escaping can't be determined from the incoming URL, the bridge must assume the URL is not xml
        escaped and return an unescaped URL accordingly<sup>[[6.99](tck-tests.md#6.99)]</sup>. Also, because there are
        situations where Faces components will further encode returned URL strings by replacing &lt;spaces&gt; in the
        `URL` with the '+' which not all portlet containers may be able to subsequently process, the bridge can (should)
        `URL`-encode the space character (%20) prior to returning the `URL` regardless of any stipulation regarding base
        encoding.
        <br /><br />

- `getRequest()`:

    Return the environment-specific object instance for the current request. This must be the last request object set as
    a consequence of calling `setRequest()`<sup>[[6.35](tck-tests.md#6.35)]</sup> or if none set, the request object
    passed to this instance's constructor.

- `setRequest()`:

    Set the environment-specific request to be returned by subsequent calls to
    `getRequest()`<sup>[[6.35](tck-tests.md#6.35)]</sup>. This may be used to install a wrapper for the request.

- `setRequestCharacterEncoding()`:

    Overrides the name of the character encoding used in the body of this
    request<sup>[[6.37](tck-tests.md#6.37)]</sup>.
    <br />
    <br />
    Calling this method after reading request parameters or reading input has no effect and throws no exceptions.
    Calling this method during the RENDER_PHASE is has no effect and throws no
    exceptions<sup>[[6.36](tck-tests.md#6.36)]</sup>.

- `getRequestHeaderMap()`:  

    Return an immutable `Map` whose keys are the set of request header names included in the current request, and whose
    values (of type `String`) are the first (or only) value for each header name returned by the underlying
    request<sup>[[6.38](tck-tests.md#6.35), [6.39](tck-tests.md#6.39), [6.119](tck-tests.md#6.119),
    [6.120](tck-tests.md#6.120)]</sup>. The returned `Map` must implement the entire contract for an unmodifiable
    `Map` as described in the JavaDocs for `java.util.Map`<sup>[[6.38](tck-tests.md#6.38),
    [6.39](tck-tests.md#6.39), [6.119](tck-tests.md#6.119), [6.120](tck-tests.md#6.120)]</sup>. In addition, key
    comparisons must be performed in a case insensitive manner.

    This Map must include the set of properties available via the `javax.portlet.PortletRequest` methods `getProperty()`
    and `getPropertyNames()` except when executing a `HEADER_REQUEST` or an `EVENT_REQUEST`. Within a `HEADER_REQUEST`
    or `EVENT_REQUEST`, the map must exclude the `Content-Type` and `Content-Length` properties (if they are present in
    the underlying request)<sup>[[6.38](tck-tests.md#6.38), [6.119](tck-tests.md#6.119)]</sup>.

    In addition, to provide compatibility with servlets, the bridge must ensure that the following entries exist in the
    `Map` when the bridge is executing during an `ACTION_PHASE` or `RESOURCE_PHASE`: `Accept`, `Accept-Language`,
    `Content-Type`, and `Content-Length`<sup>[[6.39](tck-tests.md#6.39), [6.120](tck-tests.md#6.120)]</sup>. When
    executing during a `HEADER_PHASE` or an `EVENT_PHASE` the bridge must only ensure that `Accept` and
    `Accept-Language` exist (and as noted above that `Content-Type` and `Content-Length` don't
    exist)<sup>[[6.38](tck-tests.md#6.38), [6.119](tck-tests.md#6.119)]</sup>. The values for these headers are derived
    as follows:
 
    - `Accept`: The value returned for this header must be the result of properly encoding the values returned by
    `getResponseContentTypes()` as a string conforming to the [HTTP 1.1 Accept header
    format](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.md) as defined by RFC2616 Section 14.1.

    - `Accept-Language`: The value returned for this header must be the result of properly encoding the values returned
    by `getLocales()` as a string conforming to the [HTTP 1.1 Accept-Language header
    format](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.md) as defined by RFC2616 Section 14.4.

    - `Content-Type`: The value returned for this header must be the result of properly encoding the values returned by
    `getContentType()` and `getCharacterEncoding()` as a string conforming to the [HTTP 1.1 Content-Type header
    format](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.md) as defined by RFC2616 Section 14.17. This header
    value must only be represented if `getContentType()` returns a non-null value. The character set portion of this
    header must only be represented if `getCharacterEncoding()` returns a non-null value.

    - `Content-Length`: The value returned for this header must be the result of properly encoding the values returned
    by `getContentLength()` as a string conforming to the [HTTP 1.1 Content-Length header
    format](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.md) as defined by RFC2616 Section 14.13. This header
    value must only be represented if `getContentLength()` returns a value other then -1.

- `getRequestHeaderValuesMap()`: 

    Return an immutable `Map` whose keys are the set of request header names included in the current request, and whose
    values (of type `String[]`) are all of the value for each header name returned by the underlying
    request<sup>[[6.40](tck-tests.md#6.40), [6.41](tck-tests.md#6.41), [6.121](tck-tests.md#6.121),
    [6.122](tck-tests.md#6.122)]</sup>. The returned `Map` must implement the entire contract for an unmodifiable
    `Map` as described in the JavaDocs for `java.util.Map`<sup>[[6.40](tck-tests.md#6.40),
    [6.41](tck-tests.md#6.41), [6.121](tck-tests.md#6.121), [6.122](tck-tests.md#6.122)]</sup>. In addition, key
    comparisons must be performed in a case insensitive manner.

    This `Map` must include the set of property names and values available via the `javax.portlet.PortletRequest`
    methods `getProperty()` and `getPropertyNames()` except when executing a `HEADER_REQUEST` or an `EVENT_REQUEST`.
    Within a HEADER_REQUEST`, `RENDER_REQUEST`, or `EVENT_REQUEST`, the map must exclude the `CONTENT-TYPE` property (if
    it is present in the underlying request)<sup>[[6.40](tck-tests.md#6.40), [6.121](tck-tests.md#6.121)]</sup>.
    
    In addition, to provide compatibility with servlets, the bridge must ensure that the following entries exist in the
    Map when the bridge is executing during an `ACTION_PHASE` or `RESOURCE_PHASE`: `Accept`, `Accept-Language`,
    `Content-Type`, and `Content-Length`<sup>[[6.41](tck-tests.md#6.41), [6.122](tck-tests.md#6.122)]</sup>. When
    executing during a `HEADER_PHASE`, `RENDER_PHASE`, or an `EVENT_PHASE` the bridge must only ensure that `Accept` and
    `Accept-Language` exist (and as noted above that `Content-Type` doesn't exist)<sup>[[6.40](tck-tests.md#6.40),
    [6.121](tck-tests.md#6.121)]</sup>. The values for these headers are derived as follows:

    - `Accept`: The value returned for this header must be the result of properly encoding the values returned by
    `getResponseContentTypes()` as a string conforming to the [HTTP 1.1 Accept header
    format](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.md) as defined by RFC2616 Section 14.1.
    
    - `Accept-Language`: The value returned for this header must be the result of properly encoding the values returned
    by `getLocales()` as a string conforming to the [HTTP 1.1 Accept-Language header
    format](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.md) as defined by RFC2616 Section 14.4.
    
    - `Content-Type`: The value returned for this header must be the result of properly encoding the values returned by
    `getContentType()` and `getCharacterEncoding()` as a string conforming to the [HTTP 1.1 Content-Type header
    format](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.md) as defined by RFC2616 Section 14.17. This header
    value must only be represented if `getContentType()` returns a non-null value. The character set portion of this
    header must only be represented if `getCharacterEncoding()` returns a non-null value.
    
    - `Content-Length`: The value returned for this header must be the result of properly encoding the values returned
    by `getContentLength()` as a string conforming to the [HTTP 1.1 Content-Length header
    format](http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.md) as defined by RFC2616 Section 14.13. This header
    value must only be represented if `getContentLength()` returns a value other then -1.
    
- `getRequestMap()`:

    Return a mutable `Map` representing the request scope attributes for the current
    application<sup>[[6.42](tck-tests.md#6.42)]</sup>. The returned `Map` must implement the entire contract for a
    modifiable map as described in the JavaDocs for `java.util.Map`. Modifications made in the `Map` must cause the
    corresponding changes in the set of request scope attributes. Particularly the `clear()`, `remove()`, `put()`,
    `putAll()`, and `get()` operations must take the appropriate action on the underlying data
    structure<sup>[[6.42](tck-tests.md#6.42)]</sup>.

    For any of the Map methods that cause an element to be removed from the underlying data structure, the following
    occurs [6.8](Chapter-6-Bridge-Requirements-for-Managing-Faces#6.8):

    - If the attribute is excluded from the bridge request scope and the attribute's value is a managed-bean, and it has
    one or more public no-argument void return methods annotated with `javax.annotation.PreDestroy`, then each such
    method must be called before the element is removed from the underlying data structure. Elements that are not
    managed-beans, but do happen to have methods with that annotation must not have those methods called on removal.
    
    - If the attribute is included in the bridge request scope then regardless of whether the attribute is a
    managed-bean or not, if the attribute's value has one or more public no-argument void return methods annotated with
    `javax.portlet.faces.annotation.PreDestroy`, then each such method must be called before the element is removed from
    the underlying data structure<sup>[[6.42](tck-tests.md#6.42)/[nt](tck-tests.md#nt)]</sup>.
    
    Any exception thrown by either of these `PreDestroy` annotated methods must by caught and not rethrown. The
    exception may be logged.

    The `Map` must contain the set of attributes available via the `javax.portlet.PortletRequest` methods
    `getAttribute()`, `getAttributeNames()`, `removeAttribute()`, and `setAttribute()`. Furthermore these attributes
    must be managed across portlet requests according to the rules defined in section
    [5.1.2](chapter-5-request-lifecycle.md#5.1.2)<sup>[[6.43](tck-tests.md#6.43)]</sup>.
    
- `getRequestParameterMap()`: 

    Return an immutable `Map` whose keys are the set of request parameters names included in the current request, and
    whose values (of type `String`) are the first (or only) value for each parameter name returned by the underlying
    request<sup>[[6.45](tck-tests.md#6.45)]</sup>. The returned `Map` must implement the entire contract for an
    unmodifiable map as described in the JavaDocs for `java.util.Map`.
    
    This `Map` must be composed from the set of parameters available via the `javax.portlet.PortletRequest` methods
    `getParameter()` and `getParameterNames()` plus any additional parameter names encoded in the (query string) of the
    `viewId`<sup>[[6.47](tck-tests.md#6.47)]</sup>. This later situation primarily occurs when using a default
    `viewId` provided by the portlet.
    
    In addition, during a portlet's `HEADER_PHASE`, if not otherwise already in the `Map`, the bridge must include those
    parameters managed in the corresponding bridge request scope. This always includes the
    `ResponseStateManger.VIEW_STATE_PARAM parameter`<sup>[[6.45](tck-tests.md#6.45)]</sup>. The preservation/inclusion
    of the rest of the action parameters depends on the `javax.portlet.faces.[portlet name].preserveActionParams`
    portlet context attribute. If this context attribute exists and has a value of `Boolean.TRUE`, the additional action
    parameters are preserved/included<sup>[[6.46](tck-tests.md#6.46)]</sup>, otherwise they aren't.

- `getRequestParameterNames()`:

    Return an `Iterator` over the names of all request parameters included in the current request. This must be an
    `Iterator` over the values returned by the `javax.portlet.PortletRequest` method
    `getParameterNames()`<sup>[[6.48](tck-tests.md#6.48)]</sup> plus any additional parameter names encoded in the
    (query string) of the `viewId`<sup>[[6.50](tck-tests.md#6.50)]</sup>. This later situation primarily occurs when
    using a default `viewId` provided by the portlet.
    
    In addition, during a portlet's `HEADER_PHASE`, if not otherwise already in the `Iterator`, the bridge must include
    those parameter names managed in the corresponding bridge request scope. This always includes the
    `ResponseStateManger.VIEW_STATE_PARAM` parameter<sup>[[6.48](tck-tests.md#6.48)]</sup>. The preservation/inclusion
    of the rest of the action parameters depends on the `javax.portlet.faces.[portlet name].preserveActionParams`
    portlet context attribute. If this context attribute exists and has a value of `Boolean.TRUE`, the additional action
    parameters are preserved/included<sup>[[6.49](tck-tests.md#6.49)]</sup>, otherwise they aren't.
    
- `getRequestParameterValuesMap()`:

    Return an immutable `Map` whose keys are the set of request parameters names included in the current request, and
    whose values (of type `String[]`) are all of the values for each parameter name returned by the underlying
    request<sup>[[6.51](tck-tests.md#6.51)]</sup>. The returned `Map` must implement the entire contract for an
    unmodifiable map as described in the JavaDocs for `java.util.Map`.
    
    This must be the set of parameter values available via the `javax.portlet.PortletRequest` methods
    `getParameterValues()` and `getParameterNames()` plus any additional parameter names encoded in the (query string)
    of the `viewId`<sup>[[6.53](tck-tests.md#6.53)]</sup>. This later situation primarily occurs when using a default
    `viewId` provided by the portlet.
    
    In addition, during a portlet's `HEADER_PHASE`, if not otherwise already in the `Map`, the bridge must include those
    parameter names managed in the corresponding bridge request scope. This always includes the
    `ResponseStateManger.VIEW_STATE_PARAM` parameter<sup>[[6.51](tck-tests.md#6.51)]</sup>. The preservation/inclusion
    of the rest of the action parameters depends on the `javax.portlet.faces.[portlet name].preserveActionParams`
    portlet context attribute<sup>[[6.52](tck-tests.md#6.52)]</sup>. If this context attribute exists and has a value
    of `Boolean.TRUE`, the additional action parameters are preserved/included, otherwise they aren't.

- `getRequestPathInfo()`:

    Return the extra path information (if any) included in the request `URI`; otherwise, return `null`. This value must
    represent the path portion of the current target `viewId`.

    Because the portlet model doesn't support a (servlet) equivalent notion of `pathInfo` and `servletPath`, the bridge
    must manufacture these values based on the target `viewId`. The bridge determines the target view from request
    parameter(s) it has previously encoded. If this information doesn't exist, the target view is the default `viewId`
    defined by the portlet. The associated pathInfo and servletPath are constructed by determining the servlet mapping
    of the Faces servlet and constructing the appropriate paths such that they conform to the paths the servlet
    container generates when processing an http request which targets this view as defined in SRV .3.4 in the Servlet
    2.5 specification titled "Request Path Elements"<sup>[[6.54](tck-tests.md#6.54)]</sup>.

    Examples:

    | Faces servlet mapping     | viewId      | servletPath | pathInfo    |
    |---------------------------|-------------|-------------|-------------|
    | /faces/* (prefix mapping) | myView.jspx | /faces      | myView.jspx |
    | \*.jsf (suffix mapping)   | myView.jspx | myView.jsf  | `null`      |

- `getRequestServletPath()`:

    Returns the part of this request's URL that calls the servlet. This path starts with a "/" character and includes
    either the servlet name or a path to the servlet, but does not include any extra path information or a query string.

    This method will return an empty string ("") if the servlet used to process this request was matched using the "/*"
    pattern.

    Because the portlet model doesn't support a (servlet) equivalent notion of `pathInfo` and `servletPath`, the bridge
    must manufacture these values based on the target `viewId`. The bridge determines the target view from request
    parameter(s) it has previously encoded. If this information doesn't exist, the target view is the default `viewId`
    defined by the portlet. The associated pathInfo and servletPath are constructed by determining the servlet mapping
    of the Faces servlet and constructing the appropriate paths such that they conform to the paths the servlet
    container generates when processing an http request which targets this view as defined in SRV .3.4 in the Servlet
    2.5 specification titled "Request Path Elements"<sup>[[6.55](tck-tests.md#6.55)]</sup>.

    Examples:

    | Faces servlet mapping     | viewId      | servletPath | pathInfo    |
    |---------------------------|-------------|-------------|-------------|
    | /faces/* (prefix mapping) | myView.jspx | /faces      | myView.jspx |
    | \*.jsf (suffix mapping)   | myView.jspx | myView.jsf  | `null`      |

- `getRequestCharacterEncoding()`: 

    Return the character encoding currently being used to interpret this request. If called during the `ACTION_PHASE` or
    `RESOURCE_PHASE`, returns the value from the corresponding action
    `request.getCharacterEncoding()`<sup>[[6.57](tck-tests.md#6.57), [6.123](tck-tests.md#6.123)]</sup>. If called
    during the HEADER_PHASE` or `EVENT_PHASE` it returns `null`<sup>[[6.56](tck-tests.md#6.56),
    [6.124](tck-tests.md#6.124)]</sup>.

- `getRequestContentType()`: 

    Return the MIME Content-Type for this request. If called during the `ACTION_PHASE` or `RESOURCE_PHASE`, returns the
    value from the corresponding action `request.getContentType()`<sup>[[6.59](tck-tests.md#6.59),
    [6.125](tck-tests.md#6.125)]</sup>. If called during the HEADER_PHASE` or `EVENT_PHASE` it returns
    `null`<sup>[[6.58](tck-tests.md#6.58), [6.126](tck-tests.md#6.126)]</sup>.

- `getResponseCharacterEncoding()`: 

    Returns the name of the character encoding (MIME charset) used for the body sent in this response. If called during
    the HEADER_PHASE` or `RESOURCE_PHASE`, returns the value from the corresponding render
    `response.getCharacterEncoding()`<sup>[[6.60](tck-tests.md#6.60), [6.127](tck-tests.md#6.127)]</sup>. If called
    during the `ACTION_PHASE` or `EVENT_PHASE` it throws an `IllegalStateException`<sup>[[6.61](tck-tests.md#6.61),
    [6.128](tck-tests.md#6.128)]</sup>.

- `getResponseContentType()`: 

    Return the MIME Content-Type for this response. If called during the HEADER_REQUEST`, or `RESOURCE_PHASE`, returns
    the value from the corresponding `response.getContentType()`<sup>[[6.62](tck-tests.md#6.62),
    [6.129](tck-tests.md#6.129)]</sup>. However, if the return value of the corresponding `response.getContentType()` is
    null, then it returns the value from the corresponding
    `request.getContentType()`<sup>[[5.29](tck-tests.md#5.29)]</sup>. If called during the `ACTION_PHASE` or
    `EVENT_PHASE` it throws an `IllegalStateException`<sup>[[6.63](tck-tests.md#6.63),
    [6.130](tck-tests.md#6.130)]</sup>.

- `getResponse()`:

    Return the environment-specific object instance for the current response. This must be the last response object set
    as a consequence of calling `setResponse()` or if none set, the `response` object passed to this instance's
    constructor.

- `setResponse()`:

    Set the environment-specific response to be returned by subsequent calls to `getResponse()`. This may be used to
    install a wrapper for the response.

- `redirect()`:

    Redirect a request to the specified `URL`.

    Because the portlet redirect semantics differ from servlets the bridge implements the following support:

    - During an Action Request<sup>[[6.64](tck-tests.md#6.64)]</sup>:
        <br />
        <br />
        - if the redirect target URL starts with '#', references a resource outside of this web application, or contains
        the query string parameter `javax.portlet.faces.DirectLink` with a value of "`true`", call
        `ActionResponse.sendRedirect()` passing the `url`.
        <br />
        <br />
        This causes the client to redirect from the consumer page to this target.
        <br />
        <br />
        - otherwise, ensure that the action response is set to cause the subsequent render to target this redirect view.
        Typically this merely involves ensuring that `encodeActionURL()` has been called on the target.
        <br />
        <br />
        This is equivalent to a regular faces navigation, the client doesn't redirect, rather the consumer page renders
        the portlet's new target.
        <br />
        <br />
        **Note**: in either case, `FacesContext.responseComplete()` must be called before returning.

    - During an Event Request<sup>[[6.131](tck-tests.md#6.131)]</sup>:
        <br />
        <br />
        - if the redirect target URL starts with '#', references a resource outside of this web application, or contains
        the query string parameter `javax.portlet.faces.DirectLink` with a value of "`true`". The redirect is ignored.
        <br />
        <br />
        - otherwise, ensure that the event response is set to cause the subsequent render to target this redirect view.
        Typically this merely involves ensuring that `encodeActionURL()` has been called on the target.
        <br />
        <br />
        This is equivalent to a regular faces navigation, the client doesn't redirect, rather the consumer page renders
        the portlet's new target.
        <br />
        <br />
        **Note**: in either case, `FacesContext.responseComplete()` must be called before returning.

    - During a Header Request:
        <br />
        <br />
        - if the redirect target URL starts with '#', references a resource outside of this web application, or contains
        the query string parameter `javax.portlet.faces.DirectLink` with a value of "`true`". The redirect is ignored.
        <br />
        <br />
        - if the redirect target URL references a non-Faces view then throw an
        `IllegalStateException`<sup>[[6.66](tck-tests.md#6.66)]</sup>.
        <br />
        <br />
        - otherwise, call `FacesContext.responseComplete()` to ensure the bridge will cease rendering its current view
        prior to returning any response content. Instead, capture the markup of the redirect target for rendering during
        the render phase<sup>[[6.65](tck-tests.md#6.65)]</sup>. In addition, support this behavior so that subsequent
        rerenders will ignore the (original) request target and instead render with the (cached) redirect target URL and
        its preserved saved (render) view state. Special care is needed in managing public render parameters during a
        render redirect. The bridge must ensure that the public render parameters passed to this request are carried
        forward and processed during the rendering of the redirect that occurs within the same render request. However,
        if subsequent rerender (requests), the bridge must not use any of the public render parameters used in the
        initial render (redirect) but rather use the public render parameters that are reflected in the (new) request.

    - During a Render Request: Write the markup that was captured during the header phase to the response.

    - During a Resource Request<sup>[[nt](tck-tests.md#nt)]</sup>: The redirect is ignored. Take no action.

#### <a name="6.1.3.2"></a>6.1.3.2 Methods that conform with Faces 1.2 Javadoc

The following methods require an implementation that are adequately described in the Faces 1.2 ExternalContext javadoc.
This includes the implicit requirement that each access to the portlet `request` or `response` object be done by using
the last `request` or `response` object set using the corresponding `setRequest()` or `setResponse()` methods or the
original objects passed to the constructor if none have been directly set.

- `encodeNamespace()`:

    Return the specified name, after prefixing it with a namespace that ensures that it will be unique within the
    context of a particular page. The returned value must be the input value prefixed by the value returned by the
    `javax.portlet.RenderResponse` method `getNamespace()`<sup>[[6.67](tck-tests.md#6.67)]</sup>.

- `getApplicationMap()`:

    Return a mutable `Map` representing the application scope attributes for the current
    application<sup>[[6.68](tck-tests.md#6.68)]</sup>. This must be the set of attributes available via the
    `javax.portlet.PortletContext` methods `getAttribute()`, `getAttributeNames()`, `removeAttribute()`, and
    `setAttribute()`. The returned `Map` must implement the entire contract for a modifiable map as described in the
    JavaDocs for `java.util.Map`. Modifications made in the `Map` must cause the corresponding changes in the set of
    application scope attributes. Particularly the `clear()`, `remove()`, `put()`, `putAll()`, and `get()` operations
    must take the appropriate action on the underlying data structure.

    For any of the `Map` methods that cause an element to be removed from the underlying data structure, the following
    action regarding managed-beans must be taken. If the element to be removed is a managed-bean, and it has one or more
    public no-argument void return methods annotated with `javax.annotation.PreDestroy`, each such method must be called
    before the element is removed from the underlying data structure. Elements that are not managed-beans, but do happen
    to have methods with that annotation must not have those methods called on removal. Any exception thrown by the
    `PreDestroy` annotated methods must by caught and not rethrown. The exception may be logged.

- `getAuthType()`:

    Return the name of the authentication scheme used to authenticate the current user, if any; otherwise, return
    `null`<sup>[[6.69](tck-tests.md#6.69)]</sup>. For standard authentication schemes, the returned value will match
    one of the following constants: `BASIC_AUTH`, `CLIENT_CERT_AUTH`, `DIGEST_AUTH`, or `FORM_AUTH`. This must be the
    value returned by the `javax.portlet.http.PortletRequest` method `getAuthType()`.

- `getContext()`:

    Return the application environment object instance for the current application. This must be the current
    application's `javax.portlet.PortletContext` instance<sup>[[6.70](tck-tests.md#6.70)]</sup>.

- `getInitParameter()`:

    Return the value of the specified application initialization parameter (if any). If the result of calling
    `PortletConfig.getInitParameter(name)` is non-null, then return the non-null value. Otherwise, return the result of
    calling `PortletContext.getInitParameter(String)`<sup>[[6.71](tck-tests.md#6.71)]</sup>.

- `getInitParameterMap()`:

    Return an immutable `Map` whose keys are the set of application initialization parameter names configured for this
    application, and whose values are the corresponding parameter values<sup>[[6.72](tck-tests.md#6.72)]</sup>. The
    returned `Map` must implement the entire contract for an unmodifiable map as described in the JavaDocs for
    `java.util.Map`. The map keys must be the union of the key names from `PortletConfig.getInitParameterNames()` and
    `PortletContext.getInitParameterNames()`. Each map value must be equal to the non-null result of calling
    `PortletConfig.getInitParameter(name)`. Otherwise, if `PortletConfig.getInitParameter(name)` returns a null value
    then the map value must be equal to the result of calling `PortletContext.getInitParameter(name)`.

- `getRemoteUser()`:

    Return the login name of the user making the current request if any; otherwise, return `null`. This must be the
    value returned by the `javax.portlet.http.PortletRequest` method
    `getRemoteUser()`<sup>[[6.73](tck-tests.md#6.73)]</sup>.

- `getRequestContextPath()`:

    Return the portion of the request URI that identifies the web application context for this request. This must be the
    value returned by the `javax.portlet.PortletRequest` method
    `getContextPath()`<sup>[[6.74](tck-tests.md#6.74)]</sup>.

- `getRequestCookieMap()`:

    Return an immutable `Map` whose keys are the set of cookie names included in the current request, and whose values
    (of type `javax.servlet.http.Cookie`) are the first (or only) cookie for each cookie name returned by the underlying
    request. The returned `Map` must implement the entire contract for an unmodifiable map as described in the JavaDocs
    for `java.util.Map`.

    This must be an empty Map<sup>[[6.75](tck-tests.md#6.75)]</sup>.

- `getRequestLocale()`:

    Return the preferred `Locale` in which the client will accept content. This must be the value returned by the
    `javax.portlet.PortletRequest` method `getLocale()`<sup>[[6.76](tck-tests.md#6.76)]</sup>.

- `getRequestLocales()`:

    Return an `Iterator` over the preferred `Locale`s specified in the request, in decreasing order of preference. This
    must be an `Iterator` over the values returned by the `javax.portlet.PortletRequest` method
    `getLocales()`<sup>[[6.77](tck-tests.md#6.77)]</sup>.

- `getResource()`:

    Return a `URL` for the application resource mapped to the specified path, if it exists; otherwise, return `null`.
    This must be the value returned by the `javax.portlet.PortletContext` method
    `getResource(path)`<sup>[[6.78](tck-tests.md#6.78)]</sup>.

- `getResourceAsStream()`:

    Return an `InputStream` for an application resource mapped to the specified path, if it exists; otherwise, return
    `null`. This must be the value returned by the `javax.portlet.PortletContext` method
    `getResourceAsStream(path)`<sup>[[6.79](tck-tests.md#6.79)]</sup>.

- `getResourcePaths()`:

    Return the `Set` of resource paths for all application resources whose resource path starts with the specified
    argument. This must be the value returned by the `javax.portlet.PortletContext` method
    `getResourcePaths(path)`<sup>[[6.80](tck-tests.md#6.80)]</sup>.

- `setResponseCharacterEncoding()`:

    Sets the character encoding (MIME charset) of the response being sent to the client, for example, to UTF-8. This
    method must take no action<sup>[[6.81](tck-tests.md#6.81)]</sup>.

- `getSession()`:

    If the `create` parameter is `true`, create (if necessary) and return a session instance associated with the current
    request. If the `create` parameter is `false` return any existing session instance associated with the current
    request, or return `null` if there is no such session. This method must return the result of calling
    `getPortletSession(create)` on the underlying `javax.portlet.PortletRequest`
    instance<sup>[[6.82](tck-tests.md#6.82)]</sup>.

- `getSessionMap()`:  

    Return a mutable `Map` representing the session (`PORTLET_SCOPE`) scope attributes for the current
    portlet<sup>[[6.83](tck-tests.md#6.83)]</sup>. The returned `Map` must implement the entire contract for a
    modifiable map as described in the JavaDocs for `java.util.Map`. Modifications made in the Map must cause the
    corresponding changes in the set of session scope attributes. Particularly the `clear()`, `remove()`, `put()`, and
    `get()` operations must take the appropriate action on the underlying data structure. Accessing attributes via this
    `Map` must cause the creation of a session associated with the current request, if such a session does not already
    exist.

    For any of the `Map` methods that cause an element to be removed from the underlying data structure, the following
    action regarding managed-beans must be taken. If the element to be removed is a managed-bean, and it has one or more
    public no-argument void return methods annotated with `javax.annotation.PreDestroy`, each such method must be called
    before the element is removed from the underlying data structure<sup>[[6.84](tck-tests.md#6.84)]</sup>. Elements
    that are not managed-beans, but do happen to have methods with that annotation must not have those methods called on
    removal. Any exception thrown by the `PreDestroy` annotated methods must by caught and not rethrown. The exception
    may be logged.

    This `Map` must be composed from the set of attributes that exist in the `PORTLET_SCOPE` available via the
    `javax.portlet.PortletSession` methods `getAttribute()`, `getAttributeNames()`, `removeAttribute()`, and
    `setAttribute()`.

- `getUserPrincipal()`:

    Return the `Principal` object containing the name of the current authenticated user, if any; otherwise, return
    `null`. This must be the value returned by the `javax.portlet.http.PortletRequest` method
    `getUserPrincipal()`<sup>[[6.85](tck-tests.md#6.85)]</sup>.

- `isUserInRole()`:

    Return `true` if the currently authenticated user is included in the specified role. Otherwise, return `false`. This
    must be the value returned by the `javax.portlet.http.PortletRequest` method `isUserInRole(role)`.

- `log()`:

    Log the specified message to the application object. This must be performed by calling the equivalent form of the
    `javax.portlet.PortletContext` method `log()`.

## <a name="6.2"></a>6.2 ViewHandler

The Faces `ViewHandler` is the pluggability mechanism that allows implementations to extend the JavaServer Faces
specification to provide their own handling of the activities in the Render Response and Restore View phases of the
request processing lifecycle. This allows for implementations to support different response generation technologies, as
well as different state saving/restoring approaches.

The bridge is required to provide an implementation of the `ViewHandler`<sup>[[nt](tck-tests.md#nt)]</sup>. This
implementation must be configured in the `faces-config.xml` file packaged into the bridge's `jar`
file<sup>[[nt](tck-tests.md#nt)]</sup>. The implementation must implement the decorator design pattern described in
section 10.4.5 in the JSF 1.2 specification<sup>[[nt](tck-tests.md#nt)]</sup>.

Because configuration is limited to using the `faces-config.xml` file packaged into the bridge's `jar` file,
`ViewHandler` order can't be defined if other Faces extensions relying on the same `ViewHandler` configuration technique
exist in the environment. The bridge implementation must safely cohabit with these other `ViewHandler`s regardless of
precedence order in the particular runtime environment they execute in<sup>[[nt](tck-tests.md#nt)]</sup>. To satisfy
this the bridge must delegate to its parent `ViewHandler` for all methods unless otherwise indicated to do so by a
configuration parameter. For most methods this will be a strict delegation with no pre/post processing of the call. If
pre/post processing is necessary it must be done in a manner that doesn't interfere with the normally processing of the
other `ViewHandler`s in the chain. To facilitate this, implementations are encouraged to subclass
`javax.faces.application.ViewHandlerWrapper`.

Furthermore, where ever the bridge provides bridge specific `ViewHandler` behavior, it must ensure this behavior is only
executed when executing a request via the bridge<sup>[[nt](tck-tests.md#nt)]</sup>. Because the `ViewHandler` is a
general Faces extension the bridge's `ViewHandler` methods will be called if the bridge is in this application's
classpath regardless of whether the current request has been dispatched through the bridge.

### <a name="6.2.1"></a>6.2.1 Method Requirements

The following `ViewHandler` methods must meet specific bridge requirements (i.e.
have expected pre/post delegation processing):

- `createView()`: is responsible for returning the `UIViewRoot` for the newly created view tree. The bridge, during a
portlet request, should return a `UIViewRoot` from its `createView()` that supports (is annotated by)
`javax.portlet.faces.annotation.PortletNamingContainer`. Such a `NamingContainer` ensures all ids in the tree are unique
on a per portlet (instance) basis [6.6](chapter-6-managing-faces.md#6.6). This type of `UIViewRoot` must be returned
unless the bridge delegates `UIViewRoot` creation and the result of that delegation is a `UIViewRoot` whose
implementation class (not `instanceof`) is **not**
`javax.faces.component.UIViewRoot`<sup>[[6.86](tck-tests.md#6.86)]</sup>.

### <a name="6.2.2"></a>6.2.2 Other Methods

The following `ViewHandler` methods have no specific bridge requirements and hence its expected they will always be
delegated. If a bridge implementation does more then merely delegate if must satisfy the above cohabitation
requirements. This is particularly true for `writeState` as overriding this commonly interferes with the application
developers desired state management. If `getActionURL` is modified the bridge must ensure the resulting `String` it
returns is a valid `URL`. In particular it can't be an `URL` derived from a `portletResponse.createActionURL()`.

- `calculateCharacterEncoding()`
- `calculateLocale()`
- `calculateRenderKitId()`
- `getActionURL()`
- `getResourceURL()`
- `initView()`
- `restoreView()`
- `writeState()`

## <a name="6.3"></a>6.3 StateManager

To properly maintain references to (updated) view state the bridge will likely have to provide its own `StateManager` in
order to override the state writing process allowing it to inspect and preserve (update) the value of the
`VIEW_STATE_PARAM` parameter it manages in its extended bridge scope
[[5.1.2.2](chapter-5-request-lifecycle.md#5.1.2.2)]. For example the bridge can override
`StateManager.writeState(FacesContext context, Object state)`:

- replace the `ResponseWriter` with one it manufactures that writes to a `StringWriter`
- delegate the call so the state is output.
- copy the output back into the original `ResponseWriter`
- parse the output in the `StringWriter` locating the `VIEW_STATE_PARAM` parameter and value
- copy the value and place it on a request attribute for the bridge to process later

## <a name="6.4"></a>6.4 Phase Listeners

The bridge must prevent the Faces action phases (`ApplyRequestValues`, `ProcessValidations`, `UpdateModel`, and
`InvokeApplication`) from executing if processing an event or rendering in a restored bridge request
scope<sup>[[6.90](tck-tests.md#6.90)]</sup> [[5.1.2](chapter-5-request-lifecycle.md#5.1.2)]. I.e. during either a
portlet's `EVENT_PHASE` or `HEADER_PHASE`, when the Faces `Lifecycle` is executed to restore the view, the bridge must
ensure the lifecycle falls directly through to render after the view is restored. This is most conveniently supported by
implementing a `PhaseListener` and calling `FacesContext.renderResponse()` when invoked in the `RestoreView` phase.

## <a name="6.5"></a>6.5 Expression Language Resolution

Faces relies on the Unified Expression Language architecture to ensure consistent EL evaluation in a JSP page where JSP
expressions and Faces expressions can coexist. During a JSP EL resolution Faces extends the JSP resolvers to
process/expose Faces unique variables. During a Faces EL resolution Faces provides the base resolvers that not only
expose the Faces unique variables but also those that the JSP resolvers otherwise have access to during the scope of the
page. In a servlet environment, this ensures evaluation consistency for the same expression whether within a JSP
expression or a Faces expression. However because the EL types are resolved by different resolvers in different contexts
when running in a portlet environment evaluation isn't always consistent. Its important to be aware of the following
subtle differences in implicit object evaluation.

### <a name="6.5.1"></a>6.5.1 Implicit Objects

When running in a JSP context, JSP provides an ELResolver that recognizes and resolves the following implicit
objects<sup>[[6.100](tck-tests.md#6.100)]</sup>:

- `applicationScope` -> a `Map` that maps application-scoped attribute names to their values
- `cookie` -> a `Map` that maps cookie names to a single `Cookie` object. Cookies are retrieved according to the
semantics of `HttpServletRequest.getCookies()`.
- `header` -> a `Map` that maps header names to a single `String` header value (obtained by calling
`HttpServletRequest.getHeader(String name)`)
- `headerValues` -> a `Map` that maps header names to a `String[]` of all values for that header (obtained by calling
`HttpServletRequest.getHeaders(String)`)
- `initParam` -> a `Map` that maps context initialization parameter names to their `String` parameter value (obtained by
calling `ServletContext.getInitParameter(String name)`)
- `pageContext` -> the `PageContext`
- `pageScope` -> a `Map` that maps page-scoped attribute names to their values
- `param` -> a `Map` that maps parameter names to a single `String` parameter value (obtained by calling
`ServletRequest.getParameter(String name)`)
- `paramValues` -> `Map` that maps parameter names to a `String[]` of all values for that parameter (obtained by calling
`ServletRequest.getParameterValues(String name)`)
- `requestScope` -> a `Map` that maps request-scoped attribute names to their values
- `sessionScope` -> a `Map` that maps session-scoped attribute names to their values

When the `<portlet:defineObjects>` tag is used within this JSP page, the following variables are exposed and will be
implicitly resolved by this JSP EL resolver<sup>[[6.100](tck-tests.md#6.100)]</sup>:

- `portletConfig`: object of type `javax.portlet.PortletConfig`
- `actionRequest`: object of type `javax.portlet.ActionRequest` (only accessible in an `ActionRequest`)
- `actionResponse`: object of type `javax.portlet.ActionResponse` (only accessible in an `ActionRequest`)
- `eventRequest`: object of type `javax.portlet.EventRequest` (only accessible in an `EventRequest`)
- `eventResponse`: object of type `javax.portlet.EventResponse` (only accessible in an `EventRequest`)
- `headerRequest`: object of type `javax.portlet.HeaderRequest` (only accessible in a `HeaderRequest`)
- `headerResponse`: object of type `javax.portlet.HeaderResponse` (only accessible in a `HeaderRequest`)
- `renderRequest`: object of type `javax.portlet.HeaderRequest` (only accessible in a `HeaderRequest`)
- `renderResponse`: object of type `javax.portlet.HeaderResponse` (only accessible in a `HeaderRequest`)
- `resourceRequest`: object of type `javax.portlet.ResourceRequest` (only accessible in a `ResourceRequest`)
- `resourceResponse`: object of type `javax.portlet.ResourceResponse` (only accessible in a `ResourceRequest`)
- `portletSession`: current `PortletSession` object.
- `portletSessionScope`: immutable `Map` containing `PortletSession` attribute/values at `PORTLET_SCOPE`.
- `portletPreferences`: current `PortletPreferences` object.
- `portletPreferencesValues`: immutable `Map` containing entries equivalent to `PortletPreferences.getMap()`.

Faces extends this behavior by providing its own ELResolver to recognize and resolve the following implicit
objects<sup>[[6.100](tck-tests.md#6.100)]</sup>:

- `facesContext` -> the `FacesContext` for this request
- `view` -> `facesContext.getViewRoot()`

When running in a Faces context (outside of JSP execution) Faces provides the base implicit object resolver that
recognizes and resolves the following implicit objects<sup>[[6.101](tck-tests.md#6.101)]</sup>:

- `application` -> `externalContext.getContext()`
- `applicationScope` -> `externalContext.getApplicationMap()`
- `cookie` -> `externalContext.getRequestCookieMap()`
- `facesContext` -> the `FacesContext` for this request
- `header` -> `externalContext.getRequestHeaderMap()`
- `headerValues` -> `externalContext.getRequestHeaderValuesMap()`
- `initParam` -> `externalContext.getInitParameterMap()`
- `param` -> `externalContext.getRequestParameterMap()`
- `paramValues` -> `externalContext.getRequestParameterValuesMap()`
- `request` -> `externalContext.getRequest()`
- `requestScope` -> `externalContext.getRequestScope()`
- `session` -> `externalContext.getSession()`
- `sessionScope` -> `externalContext.getSessionMap()`
- `view` -> `facesContext.getViewRoot()`

The primary difference between the `ELResolvers` used within the `JSP` context vs outside this context is that the base
`JSP ELResolver` is used to resolve the bulk of the implicit objects and that this resolution is based on the `JSP`s
servlet objects in the page while outside this context these objects are resolved via the `ExternalContext`. In a
portlet environment, because the `JSP ELResolver` evaluates based on the servlet objects generated when dispatched from
the portlet environment while the `JSF ELResolver` evaluates based on the `ExternalContext`, the following difference
needs to be considered:

- `sessionScope`: This `Map` contains the `APPLICATION_SCOPE` session attributes if used in JSP EL but `PORTLET_SCOPE`
session attributes if used in Faces EL<sup>[[6.100](tck-tests.md#6.100),[6.101](tck-tests.md#6.101)]</sup>.

In addition one will note that the Faces implicit object ELResolver recognizes three objects that the JSP Resolver
doesn't<sup>[[6.101](tck-tests.md#6.101)]</sup>:

- `application`
- `session`
- `request`

In a JSP `ELContext` one references these objects indirectly via the
PageContext<sup>[[6.100](tck-tests.md#6.100)]</sup>. I.e.

- `${pageContext.servletContext}`
- `${pageContext.session}`
- `${pageContext.request}`

These differ from the objects returned by the Faces EL Resolver in that<sup>[[6.101](tck-tests.md#6.101)]</sup>:

- `${pageContext.servletContext}`: This is an object of type `ServletContext` while application is an object of type
`PortletContext`.
- `${pageContext.session}`: This is an object of type servlet `Session` while `session` is an object of type
`PortletSession`. The key difference is that `PortletSession` by default references `PORTLET_SCOPE` attributes while the
servlet `Session` is a window onto the portlet's `APPLICATION_SCOPE`.
- `${pageContext.request}`: This is an object of type `ServletRequest` (through wrapped by the portlet request) while
`request` is an object of type `PortletRequest`.

### <a name="6.5.2"></a>6.5.2 Bridge ELResolver

As noted above, when the `<portlet:defineObjects>` tag is used, the following variables are exposed in the JSP page and
will be implicitly resolved by the JSP EL resolver<sup>[[6.100](tck-tests.md#6.100)]</sup>:

- `portletConfig`: object of type `javax.portlet.PortletConfig`
- `actionRequest`: object of type `javax.portlet.ActionRequest` (only accessible in an `ActionRequest`)
- `actionResponse`: object of type `javax.portlet.ActionResponse` (only accessible in an `ActionRequest`)
- `eventRequest`: object of type `javax.portlet.EventRequest` (only accessible in an `EventRequest`)
- `eventResponse`: object of type `javax.portlet.EventResponse` (only accessible in an `EventRequest`)
- `headerRequest`: object of type `javax.portlet.HeaderRequest` (only accessible in an `HeaderRequest`)
- `headerResponse`: object of type `javax.portlet.HeaderResponse` (only accessible in an `HeaderRequest`)
- `renderRequest`: object of type `javax.portlet.RenderRequest` (only accessible in an `RenderRequest`)
- `renderResponse`: object of type `javax.portlet.RenderResponse` (only accessible in an `RenderRequest`)
- `resourceRequest`: object of type `javax.portlet.ResourceRequest` (only accessible in an `ResourceRequest`)
- `resourceResponse`: object of type `javax.portlet.ResourceResponse` (only accessible in an `ResourceRequest`)
- `portletSession`: current `PortletSession` object.
- `portletSessionScope`:  immutable `Map` containing `PortletSession` attribute/values at `PORTLET_SCOPE`.
- `portletPreferences`:  current `PortletPreferences` object.
- `portletPreferencesValues`: immutable `Map` containing entries equivalent to `PortletPreferences.getMap()`.

The bridge must provide a corresponding Faces EL resolver that recognizes these variable names and resolves them to the
same object as the JSP resolver<sup>[[6.101](tck-tests.md#6.101)]</sup>.

Because JSP EL evaluation and Faces EL evaluation are handled by different resolvers, the following differences are
noted:

- `portletSessionScope`: This is an immutable `Map` when referenced in a JSP expression but a mutable `Map` when
referenced in a Faces expression.

#### <a name="6.5.2.1"></a>6.5.2.1 Additional Implicit Object Support for Portlets

The bridge's ELResolver must additionally recognize and resolve identically the following EL object references
regardless of whether its evaluating in a JSP or Faces
expression<sup>[[6.100](tck-tests.md#6.100),[6.101](tck-tests.md#6.101)]</sup>:

- `httpSessionScope`: mutable `Map` containing `PortletSession` attribute/values at `APPLICATION_SCOPE`.
- `mutablePortletPreferencesValues`: mutable `Map` of type `Map<String, javax.portlet.faces.preference.Preference>`.
This EL variable provides read/write access to each portlet preference.

#### <a name="6.5.2.2"></a>6.5.2.2 ELResolver Requirements

The bridge's ELResolver must be added through configuration within its faces-config.xml. e.g.

    <el-resolver>
        javax.portlet.faces.el.PortletELResolver
    </el-resolver>

The ELResolver must be implemented as follows<sup>[[6.100](tck-tests.md#6.100),[6.101](tck-tests.md#6.101)]</sup>:

<!--HTML Table-->

<table style="border: 1px solid black;">
<thead>
<tr>
<th>ELResolver method</th>
<th>implementation requirements</th>
</tr>
</thead>
<tbody>
<tr>
<td><tt>getValue</tt></td>
<td>If running in a non-portlet request or base is non-null return <tt>null</tt>.
<br />
If evaluating a JSP expression (non-Faces expression) and property is <tt>portletConfig</tt>, <tt>headerRequest</tt>, or
<tt>headerResponse</tt>, <tt>renderRequest</tt>, or <tt>renderResponse</tt>, return <tt>null</tt>.
<br />
If base is <tt>null</tt> and property is <tt>null</tt>, throw <tt>PropertyNotFoundException</tt>.
<br />
If none of the above and base is <tt>null</tt> and property is a String equal to one of the above names, call
<tt>setPropertyResolved(true)</tt> on the argument <tt>ELContext</tt> and return result, where property and result are
as follows:
<br />
<table style="border: 1px solid black;">
<thead>
<tr>
<th>EL Object Name</th>
<th>Result</th>
<th>Decoration Requirements</th>
</tr>
</thead>
<tbody>
<tr>
<td><tt>portletConfig</tt></td>
<td><tt>portletConfig</tt> object (recommended implementation is to place the <tt>portletConfig</tt> object on the
<tt>ELContext</tt> so can pull it here).</td>
<td>Decorated by the BridgePortletConfigFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>actionRequest</tt></td>
<td>If within an <tt>ActionRequest</tt> then <tt>ExternalContext.getRequest()</tt> otherwise throw an
<tt>ELException</tt></td>
<td>Decorated by the BridgePortletRequestFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>actionResponse</tt></td>
<td>If within an <tt>ActionRequest</tt> then&nbsp;<tt>ExternalContext.getResponse() </tt>otherwise throw an
<tt>ELException</tt></td>
<td>Decorated by the BridgePortletResponseFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>eventRequest</tt></td>
<td>If within an <tt>EventRequest</tt> then <tt>ExternalContext.getRequest()</tt> otherwise throw an
<tt>ELException</tt></td>
<td>Decorated by the BridgePortletRequestFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>eventResponse</tt></td>
<td>If within an <tt>EventRequest</tt> then&nbsp;<tt>ExternalContext.getResponse()</tt> otherwise throw an
<tt>ELException</tt></td>
<td>Decorated by the BridgePortletResponseFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>headerRequest</tt></td>
<td>If within a <tt>HeaderRequest</tt> then <tt>ExternalContext.getRequest()</tt> otherwise throw an
<tt>ELException</tt></td>
<td>Decorated by the BridgePortletRequestFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>headerResponse</tt></td>
<td>If within a <tt>HeaderRequest</tt> then&nbsp;<tt>ExternalContext.getResponse()</tt> otherwise throw an
<tt>ELException</tt></td>
<td>Decorated by the BridgePortletResponseFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>renderRequest</tt></td>
<td>If within a <tt>HeaderRequest</tt> or <tt>RenderRequest</tt> then <tt>ExternalContext.getRequest()</tt> otherwise
throw an <tt>ELException</tt></td>
<td>Decorated by the BridgePortletRequestFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>renderResponse</tt></td>
<td>If within a <tt>HeaderRequest</tt> or <tt>RenderRequest</tt> then&nbsp;<tt>ExternalContext.getResponse()</tt>
otherwise throw an <tt>ELException</tt></td>
<td>Decorated by the BridgePortletResponseFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>resourceRequest</tt></td>
<td>If within an <tt>ResourceRequest</tt> then <tt>ExternalContext.getRequest()</tt> otherwise throw an
<tt>ELException</tt></td>
<td>Decorated by the BridgePortletRequestFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>resourceResponse</tt></td>
<td>If within an <tt>ResourceRequest</tt> then <tt>ExternalContext.getResponse()</tt> otherwise throw an
<tt>ELException</tt></td>
<td>Decorated by the BridgePortletResponseFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>portletSession</tt></td>
<td><tt>ExternalContext.getSession()</tt></td>
<td>Since the PortletSession object is obtained by calling PortletRequest.getPortletSession(), it is decorated by BridgePortletRequestFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>portletSessionScope</tt></td>
<td><tt>ExternalContext.getSessionMap()</tt></td>
<td>Since the PortletSession object is obtained by calling PortletRequest.getPortletSession(), it is decorated by BridgePortletRequestFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>httpSessionScope</tt></td>
<td>an internally constructed <tt>Map</tt> containing those portlet session attributes at <tt>APPLICATION_SCOPE.</tt></td>
<td>Since the PortletSession object is obtained by calling PortletRequest.getPortletSession(), it is decorated by BridgePortletRequestFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>portletPreferences</tt></td>
<td><tt>ExternalContext.getRequest()).getPreferences()</tt></td>
<td>Since the PortletPreferences object is obtained by calling PortletRequest.getPortletSession(), it is decorated by BridgePortletRequestFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>portletPreferencesValues</tt></td>
<td><tt>ExternalContext.getRequest()).getPreferences().getMap()</tt></td>
<td>Since the PortletPreferences object is obtained by calling PortletRequest.getPortletSession(), it is decorated by BridgePortletRequestFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
<tr>
<td><tt>mutablePortletPreferencesValues</tt></td>
<td>An internally constructed <tt>Map &lt;String, javax.portlet.faces.preference.Preference&gt;</tt>. There is one entry per
portlet preference. The key is the preference name. The value is an object representing a single portlet preference (as
defined by this interface). Entries may not be added or deleted but and entry can be changed.</td>
<td>Since the PortletPreferences object is obtained by calling PortletRequest.getPortletSession(), it is decorated by BridgePortletRequestFactory as described in <a href="5.2.2">Section 5.2.2</a></td>
</tr>
</tbody>
</table>
<br />
If base is <tt>null</tt>, and property does not match one of the above property names, return null.</td>
</tr>
<tr>
<td><tt>getType</tt></td>
<td>If running in a non-portlet request or base is non-null, return null.
<br />
If base is <tt>null</tt> and property is <tt>null</tt>, throw <tt>PropertyNotFoundException</tt>.
<br />
If base is <tt>null</tt> and property is a String equal to any of the above names, call
<tt>setPropertyResolved(true)</tt> on the argument <tt>ELContext</tt> and return null to indicate that no types are
accepted to setValue() for these attributes.
<br />
Otherwise, null;</td>
</tr>
<tr>
<td><tt>setValue</tt></td>
<td>If running in a non-portlet request or base is non-null, return null.
<br />
If base is <tt>null</tt> and property is <tt>null</tt>, throw <tt>PropertyNotFoundException</tt>.
<br />
If base is <tt>null</tt> and property is a String equal to any of the above names throw
<tt>javax.el.PropertyNotWriteableException</tt>, since these objects are read-only.</td>
</tr>
<tr>
<td><tt>isReadOnly</tt></td>
<td>If running in a non-portlet request or base is non-null, return null.
<br />
If base is <tt>null</tt> and property is <tt>null</tt>, throw <tt>PropertyNotFoundException</tt>.
<br />
If base is <tt>null</tt> and property is a String equal to any of the above names call
<tt>setPropertyResolved(true)</tt> on the argument <tt>ELContext</tt> and return true.</td>
</tr>
<tr>
<td>
<span>getFeatureDescriptors</span></td>
<td><span>If base is non-null, return null.</span>
<br />
<span>If base is <tt>null</tt>, return an Iterator containing java.beans.FeatureDescriptor instances, one for each of
the above names. It is required that all of these FeatureDescriptor instances set Boolean.TRUE as the value of the
ELResolver.RESOLVABLE_AT_DESIGN_TIME attribute. For the name and short of FeatureDescriptor, return the EL object name.
The appropriate Class must be stored as the value of the ELResolver.TYPE attribute as follows:</span>
<table style="border: 1px solid black;">
<thead>
<tr>
<th>EL object name</th>
<th>ELResolver.TYPE value</th>
</tr>
</thead>
<tbody>
<tr>
<td><tt>portletConfig</tt></td>
<td><tt>javax.portlet.PortletConfig.class</tt></td>
</tr>
<tr>
<td><tt>actionRequest</tt></td>
<td><tt>javax.portlet.ActionRequest.class</tt></td>
</tr>
<tr>
<td><tt>actionResponse</tt></td>
<td><tt>javax.portlet.ActionResponse.class</tt></td>
</tr>
<tr>
<td><tt>eventRequest</tt></td>
<td><tt>javax.portlet.EventRequest.class</tt></td>
</tr>
<tr>
<td><tt>eventResponse</tt></td>
<td><tt>javax.portlet.EventResponse.class</tt></td>
</tr>
<tr>
<td><tt>headerRequest</tt></td>
<td><tt>javax.portlet.HeaderRequest.class</tt></td>
</tr>
<tr>
<td><tt>headerResponse</tt></td>
<td><tt>javax.portlet.HeaderResponse.class</tt></td>
</tr>
<tr>
<td><tt>renderRequest</tt></td>
<td><tt>javax.portlet.RenderRequest.class</tt></td>
</tr>
<tr>
<td><tt>renderResponse</tt></td>
<td><tt>javax.portlet.RenderResponse.class</tt></td>
</tr>
<tr>
<td><tt>resourceRequest</tt></td>
<td><tt>javax.portlet.ResourceRequest.class</tt></td>
</tr>
<tr>
<td><tt>resourceResponse</tt></td>
<td><tt>javax.portlet.ResourceResponse.class</tt></td>
</tr>
<tr>
<td><tt>portletSession</tt></td>
<td><tt>javax.portlet.PortletSession.class</tt></td>
</tr>
<tr>
<td><tt>portletSessionScope</tt></td>
<td><tt>Map.class</tt></td>
</tr>
<tr>
<td><tt>httpSessionScope</tt></td>
<td><tt>Map.class</tt></td>
</tr>
<tr>
<td><tt>portletPreferences</tt></td>
<td><tt>javax.portlet.PortletPreferences.class</tt></td>
</tr>
<tr>
<td><tt>portletPreferencesValues</tt></td>
<td><tt>Map.class</tt></td>
</tr>
<tr>
<td><tt>mutablePortletPreferencesValues</tt></td>
<td><tt>Map.class</tt></td>
</tr>
</tbody>
</table>
The shortDescription must be a suitable description depending on the implementation. The expert and hidden properties
must be false. The preferred property must be true.</td>
</tr>
<tr>
<td><span>getCommonPropertyType</span></td>
<td>If base is non-null, return null.
<br />
If base is <tt>null</tt> and return String.class</td>
</tr>
</tbody>
</table>

#### <a name="6.5.2.3"></a>6.5.2.3 The javax.portlet.faces.preference.Preference interface

The mutablePortletPreferencesValues EL object allows one to read and update a portlet preference via EL. It relies on
the bridge defined `javax.portlet.faces.preference.Preference` interface which allows one to expose each portlet
preference as an individual object making operations on portlet preferences EL accessible. Consult the
`javax.portlet.faces.preference.Preference` javadoc for specific descriptions and requirements of objects implementing
this interface. In general there is a corresponding method for each operation in `javax.portlet.Preferences` that can be
done on a specific preference. For example, a preference named "title" managed by the `javax.portlet.Preferences` object
could have its value accessed using its corresponding `javax.portlet.faces.preference.Preference` instance via
`title.PrefObj.getValue()` rather than the typical `preferences.getValue("title")`. Equivalent EL access would be:
`"#{mutablePortletPreferencesValues['title'].value"`.

Though operations performed on `javax.portlet.faces.preference.Preference` objects are immediately passed through to the
underlying `portletPreferences` object, because the `portletPreferences` object requires an explicit commit to preserve
these changes, developers must take care to finalize changes by calling `portletPreferences.store()` directly before the
request ends. Typically this is done in the clients `ActionHandler` executed during the `InvokeApplication` phase.

## <a name="6.6"></a>6.6 Namespacing

Portlets are components that are aggregated by another application into a response page. As such a portlet is
responsible for namespacing its markup to ensure its names don't collide with other parts of the aggregated page even
when that aggregated page contains additional instances of this portlet. Traditionally, because the Java Portlet
standard assumes the aggregated page isolates each portlet in a manner that allows discrete forms, namespacing is only
required for global names such as javascript functions and variables. Unfortunately, many of today's Faces renderkits
rely on this type of client side javascript necessitating namespacing when run in the portlet environment.

Additionally, with the emergence of consumer environments based on JSF and .NET in which such forms are collapsed into a
a single all encompassing page form, the issue of namespacing form fields has emerged. Though from the standards
perspective such consumers still have the burden of parsing and transforming the portlet markup to work in the single
form environment, the ability for consumers to do this is restricted by both its needs to return a response to the user
quickly and the complexity of locating (javascript) references to field names. Portlets, therefore, though not required,
are encouraged to namespace not only their global (client) references but also their form fields as well.

Faces supports a notion of namespacing elements in its view tree which in turn impacts form field names and renderkit
resources such as its javascript names. A namespace is introduced using a concept called a `NamingContainer`. When Faces
needs to construct a name it ascends the view tree looking for the closest parent that implements `NamingContainer`. If
it finds one this parent gets an opportunity to return a namespace that will be incorporated into the name.

Though structurally supportive, Faces however doesn't inherently provide proper portlet namespacing. The bridge needs to
introduce this support. This is done by returning a `UIViewRoot` from `ViewHandler.createView()` that implements
`NamingContainer` in a manner whereby the generated container name is constructed in part by using the unique namespace
`Id` of the portlet. More specifically, a `UIViewRoot` with the `javax.portlet.faces.annotation.PortletNamingContainer`
annotation must implement `getContainerClientId()` to return a `String` containing (at least in part) the portlet's
namespace `Id`, if and only if, called during a portlet request<sup>[[6.91](tck-tests.md#6.91)]</sup>. The namespace Id
used in processing `getContainerClientId()` must be consistent for the lifetime of the view (across save and
restore)<sup>[[6.92](tck-tests.md#6.92)]</sup>. Because getContainerClientId() can be called during various portlet
lifecycle phases (action, header, or resource)<sup>[[6.93](tck-tests.md#6.93)]</sup>, care should be taken in
implementing this support to ensure such consistency as Portlet 1.0 containers only expose the portlet's namespace `Id`
during the render phase and hence `ExternalContext.encodeNamespace()` throws an exception if called during a portlet
action request.

The convenience class `javax.portlet.faces.PortletNamingContainerUIViewRoot`<sup>[[6.94](tck-tests.md#6.94)]</sup> is
provided to simplify adding portlet namespacing for Faces extensions (and for internal bridge usage). This class can
either be used directly or subclassed. The class is annotated with the
`javax.portlet.faces.annotation.PortletNamingContainer` annotation<sup>[[6.95](tck-tests.md#6.95)]</sup> ensuring the
bridge will recognize this `UIViewRoot` as one that implements the portlet namespacing behavior. It implements
`getContainerClientId()` to meet the above requirements<sup>[[6.96](tck-tests.md#6.96)]</sup>. In addition,
`getContainerClientId()` returns `null` for non-portlet requests. This ensures the class can be used by the bridge as a
replacement for the standard `javax.faces.component.UIViewRoot` because it ensures that non-portlet behavior runs
unchanged, without `NamingContainer` function.

As indicated, annotating the `UIViewRoot` class with `javax.portlet.faces.annotation.PortletNamingContainer` allows the
bridge's `FacesContext` to detect that the response will be portlet namespaced. To signal this behavior to the consumer,
`FacesContext.setViewRoot()` sets the "`X-JAVAX-PORTLET-FACES-NAMESPACED-RESPONSE`" response property with a value of
"`true`"<sup>[[nt](tck-tests.md#nt)]</sup>. Consumers needing to do response parsing to meet its namespacing
requirements (e.g. when inserting the response into a single overall page form) can use the existence of this property
as an indication that the form fields in the portlet response have already been properly namespaced and hence need not
be fixed up as part of the form parsing process.

## <a name="6.7"></a>6.7 Supporting isPostback() during HEADER_PHASE

When rendering, Faces depends on distinguishing between renders that follow action processing within the same request
and renders that do not. This is determined by calling `ResponseStateManager.isPostback()`. Because portlet renders
occur in distinct requests from actions, the state Faces depends on to make this determination isn't naturally present.
As discussed in section [5.1.2](chapter-5-request-lifecycle.md#5.1.2) the bridge is required to ensure the existence
and/or absence of such state within its header phase in order to ensure proper execution of `isPostback()`.
Specifically, the bridge is required to always preserve the `ResponseStateManager.VIEW_STATE_PARAM` parameter in each
bridge request scope. This is done at the at the end of the `ACTION_PHASE` and `EVENT_PHASE` phase. Furthermore it must
restore this request parameter at the beginning of each `HEADER_PHASE` phase that corresponds to this bridge request
scope such that a call to `ExternalContext.getRequestParameterMap().get(ResponseStateManager.VIEW_STATE_PARAM)` returns
the restored value<sup>[[6.97](tck-tests.md#6.97)]</sup>. Finally, when its able to restore this parameter the bridge
must also set the request attribute `javax.portlet.faces.isPostback` with a `Boolean` object whose value is
`Boolean.TRUE`<sup>[[6.98](tck-tests.md#6.98)]</sup>. This allows alternative `isPostback()` implementations that do
not rely on the existence of the `ResponseStateManager.VIEW_STATE_PARAM` to recognize they are running in a postback
situation.

## <a name="6.8"></a>6.8 Supporting PreDestroy Annotated Methods

Faces requires that all managed beans be given the opportunity to clean themselves up when they are being removed from
one of the three container scopes (application, session, request). The function is managed via annotations and
injection. A managed bean with one or more public no-argument void return methods annotated with
`javax.annotation.PreDestroy` will be called when either the object is removed from scope or the scope terminates.

When running in the bridge, the lifetime of the application and session scopes aren't modified but the request scope is.
The bridge implements an extended request scope called the bridge request scope. This scope preserves managed beans
across physical requests ensuring that applications written in a style where request scoped managed beans are used to
maintain state between the Faces action and render lifecycles will function properly in the portlet's multi-request
lifecycle. However, because bridge request scope data is transferred into the portlet request scope when processing a
request, special handling is required by managed beans utilizing the annotation to avoid releasing the bean prematurely.
This is because the bridge can't prevent the `PreDestroy` method from being called when the portlet request scope ends
even though it is managing the attribute in its request scope. I.e. managed beans managed by the bridge in its bridge
request scope will still be notified they are being destroyed at the end of each portlet request. To work properly
clients must change their managed bean implementations for those beans not excluded from the bridge request scope and
the bridge must provide new (additional) mechanisms.

### <a name="6.8.1"></a>6.8.1 Managed Bean Changes

Managed beans that want to utilize `PreDestroy` and run properly when not explicitly excluded from the bridge request
scope must:

- Create separate but equal methods for handling the cleanup in the bridge environment. This distinct method(s) is
identified by the `BridgePreDestroy` annotation (`javax.portlet.faces.annotation.BridgePreDestroy`).
- Add a new public no-argument void return method annotated with `BridgeRequestScopeAttributeAdded`
(`javax.portlet.faces.annotation.BridgeRequestScopeAttributeAdded`). This method is called when the managed bean is
added to the portlet request scope and it will be preserved by the bridge in the bridge request scope. When called a
managed bean is expected to set internal state such that when its `PreDestroy` method(s) is called (later) it can check
this state and if it indicates the request is running under the management of the bridge the `PreDestroy` returns
without doing any cleanup. This mechanism works around the issues related to the bridge being unable to disable the
execution of the `PreDestroy` method even when it continues to manage this bean in its request scope.
- Modify each method annotated with `PreDestroy` to check if its executing under bridge management and if so return
without performing any cleanup.

### <a name="6.8.2"></a>6.8.2 Bridge requirements

To satisfy the Faces requirement that managed beans managed in the bridge's request scope have an opportunity to release
themselves when the bridge request scope ends, the bridge must provide the following once it has acquired a
`FacesContext` for a given request:

- When the bridge preserves a request attribute in the bridge request scope, the bridge must execute on the attribute's
value all public no argument void return methods annotated by
`BridgeRequestScopeAttributeAdded`<sup>[[nt](tck-tests.md#nt)/[6.44](tck-tests.md#6.44)]</sup>.
- When an attribute that is currently maintained in the bridge's request scope is explicitly removed from the
container's request scope or its value replaced, the bridge must execute on the (old) attribute's value any public
no-argument void return method annotated by `BridgePreDestroy` if executing in the action
phase<sup>[[nt](tck-tests.md#nt)/[6.44](tck-tests.md#6.44)]</sup>. **Note**: because the bridge request scope isn't
updated during its header phase replace/removal of an attribute's value from the container's request scope must not
cause the `BridgePreDestroy` method(s) to be called<sup>[[nt](tck-tests.md#nt)/[6.44](tck-tests.md#6.44)]</sup>.
- When an attribute that is currently maintained in the bridge's request scope is implicitly removed from the
container's request scope (such as when the container's request scope ends), the bridge does not notify the attribute's
`BridgePreDestroy` methods<sup>[[nt](tck-tests.md#nt)/[6.44](tck-tests.md#6.44)]</sup>. This is because the
attribute is still managed in the bridge request scope though removed from the underlying container request scope which
is terminating.
- When the bridge is terminating a bridge request scope, the bridge must iterate over all managed attributes and execute
on the attribute's value any public no-argument void return method annotated by
`BridgePreDestroy`<sup>[[nt](tck-tests.md#nt)/[6.44](tck-tests.md#6.44)]</sup>.

## <a name="6.9"></a>6.9 Setting the RenderKit used by a Portlet

Faces resolves the renderkit used in a given request by first looking for a request parameter whose name is the value of
`ResponseStateManager.RENDER_KIT_ID_PARAM`. If this parameter doesn't exist the id is next determined from a
configuration parameter in the application's `faces-config.xml` and finally an internal setting. Given that the
`faces-config.xml` is an application wide setting, the request parameter is the sole mechanism for managing the needs of
portlets that use distinct renderkits. As this may not be uncommon and most portlets use the same renderkit throughout
all its views, the bridge simplifies the use of this request parameter if the proper context attribute has been set
prior to it being initialized.

Specifically, when the bridge is initialized, if the portlet context attribute
`javax.portlet.faces.<portletName>.defaultRenderKitId` is set, the bridge is responsible for ensuring that in every
request the request parameter `Map`(s) returned from `ExternalContext.getRequestParameterMap()` and
`ExternalContext.getRequestParameterValuesMap()` and the `Iterator` returned from the
`ExternalContext.getRequestParameterNames()` contain an entry for
`ResponseStateManager.RENDER_KIT_ID_PARAM`<sup>[[6.135](tck-tests.md#6.135)]</sup>. In the `Map`(s), the value for
this entry must be the value from the underlying request, if it exists, otherwise it must be the value in the
`javax.portlet.faces.<portletName>.defaultRenderKitId` context attribute<sup>[[6.135](tck-tests.md#6.135)]</sup>.