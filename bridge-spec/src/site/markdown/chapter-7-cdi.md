_Version: 5.0.0-pr-SNAPSHOT_

# 7. CDI

* * *

The Portlet 3.0 Specification requires the **portlet container** to support portlets that rely on Contexts and
Dependency Injection (CDI). Such portlets are commonly referred to as "bean" portlets. The portlet container must extend
the functionality of the **CDI container** in order to instantiate bean portlets (which makes it possible for developers
to use the `@Inject` annotation within bean portlet classes). The portlet container must implement CDI scopes in order
to support beans annotated with `@javax.portlet.annotations.PortletRequestScoped`,
`@javax.portlet.annotations.PortletSessionScoped`, and `@javax.portlet.annotations.RenderStateScoped`.

The CDI 1.1 Specification requires the **CDI runtime** to implement scopes in order to support beans annotated with
`@javax.enterprise.context.ApplicationScoped`, `@javax.enterprise.context.RequestScoped`,
`@javax.enterprise.context.SessionScoped`, and `@javax.enterprise.context.ConversationScoped`. However, the Portlet 3.0
Specification requires that beans annotated with `@javax.enterprise.context.ApplicationScoped` be stored as a
`javax.portlet.PortletContext` attribute. It also requires the portlet container to regard beans annotated with
`@javax.enterprise.context.RequestScoped` as though they were annotated with
`@javax.portlet.annotations.PortletRequestScoped`. Similarly, it requires the portlet container to regard beans
annotated with `@javax.enterprise.context.SessionScoped` as though they were annotated with
`@javax.portlet.annotations.PortletSessionScoped`. Since it does not define requirements for
`@javax.enterprise.context.ConversationScoped`, the conversation scope is unsupported in a portlet environment.

The JSF 2.2 Specification requires the **JSF runtime** to implement CDI scopes for beans annotated with
`@javax.faces.view.ViewScoped` and `@javax.faces.flow.FlowScoped`.

## <a name="7.1"></a>7.1 Bean Portlet Registration

If a web archive contains a WEB-INF/beans.xml descriptor, then the portlet container will attempt to register all
portlets annotated with `@PortletConfiguration` as well as any portlets defined in WEB-INF/portlet.xml as bean portlets.
The FacesBridge must support bean portlet registration but requires the developer to use `GenericFacesPortlet` (or a
class that extends `GenericFacesPortlet`).
 
The `GenericFacesPortlet` class is annotated with `@javax.enterprise.context.ApplicationScoped` in order to ensure that
the bean portlet is registered as a singleton. In order to ensure that the CDI runtime can discover the
`GenericFacesPortlet` class, the FacesBridge API module must contain the following beans.xml descriptor:

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://xmlns.jcp.org/xml/ns/javaee" bean-discovery-mode="annotated" version="1.1"
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/beans_1_1.xsd">
    </beans>

## <a name="7.2"></a>7.2 Bean Portlet Scopes

The FacesBridge must not prevent the developer from utilizing any of the aforementioned CDI scopes that are supported by
the Portlet 3.0 and JSF 2.2 Specifications. However, the developer must realize that a bean annotated with
`@javax.enterprise.context.RequestScoped` or `@javax.portlet.annotations.PortletRequestScoped` will _not_ participate in
the "Bridge Request Scope" as defined by section [5.1.2](chapter-5-request-lifecycle.md#5.1.2) titled "Managing
Lifecycle State". Instead, the developer must annotate such beans with
`@javax.portlet.faces.annotation.BridgeRequestScoped`.

## <a name="7.3"></a>7.3 Producers

Section 20.3.1 of the Portlet 3.0 Specification titled "Portlet Request Scoped Beans" provides a table that lists all of
the `@Named` `@PortletRequestScoped` CDI beans and their corresponding EL names. Since the FacesBridge provides factories
that potentially decorate these `@PortletRequestScoped` beans, the FacesBridge must provide alternative CDI producers.

|EL Name|FacesBridge Factory|
|-------|-------------------|
|actionParams|BridgePortletRequestFactory|
|actionRequest|BridgePortletRequestFactory|
|actionResponse|BridgePortletResponseFactory|
|clientDataRequest|BridgePortletRequestFactory|
|contextPath|BridgePortletRequestFactory|
|cookies|BridgePortletRequestFactory|
|eventRequest|BridgePortletRequestFactory|
|eventResponse|BridgePortletResponseFactory|
|headerRequest|BridgePortletRequestFactory|
|headerResponse|BridgePortletResponseFactory|
|locales|BridgePortletRequestFactory|
|mimeResponse|BridgePortletResponseFactory|
|mutableRenderParams|BridgePortletRequestFactory|
|namespace|BridgePortletResponseFactory|
|portletConfig|BridgePortletConfigFactory|
|portletContext|BridgePortletConfigFactory|
|portletMode|BridgePortletRequestFactory|
|portletName|BridgePortletConfigFactory|
|portletPreferences|BridgePortletRequestFactory|
|portletRequest|BridgePortletRequestFactory|
|portletResponse|BridgePortletResponseFactory|
|portletSession|BridgePortletRequestFactory|
|renderParams|BridgePortletRequestFactory|
|renderRequest|BridgePortletRequestFactory|
|renderResponse|BridgePortletResponseFactory|
|resourceRequest|BridgePortletRequestFactory|
|resourceResponse|BridgePortletResponseFactory|
|stateAwareResponse|BridgePortletResponseFactory|
|windowId|BridgePortletRequestFactory|
|windowState|BridgePortletRequestFactory|
