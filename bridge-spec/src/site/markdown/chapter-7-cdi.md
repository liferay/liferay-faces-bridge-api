_Version: 5.0.0-SNAPSHOT_

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
the Portlet 3.0 and JSF 2.2 Specifications.

The FacesBridge is not required to support the `@javax.enterprise.context.ConversationScoped` annotation. Therefore,
its behavior and usage is undefined for a JSF portlet application.

The FacesBridge is not required to support the `@javax.enterprise.context.SessionScoped` annotation since its usage is
already defined by the Portlet 3.0 Specification and corresponding requirements are implemented by the underlying
portlet container.

The FacesBridge implementation must provide a required *(non-optional)* CDI extension that registers
`@javax.portlet.faces.annotation.BridgeRequestScoped` as a CDI scope, along with its associated CDI context. The
lifecycle/context of a bean annotated with `@javax.portlet.faces.annotation.BridgeRequestScoped`
<sup>[7.1](tck-tests.md#7.1)</sup> is defined by section [5.1.2](chapter-5-request-lifecycle.md#5.1.2) titled "Managing
Lifecycle State". Under certain deployment scenarios, registration of the CDI extension might not be automatic. As a
result, developers may need to have a META-INF/services/javax.enterprise.inject.spi.Extension file within the classpath
of the WAR archive. The contents of the file is the FQCN of the CDI extension provided by the FacesBridge
implementation.

By default, the FacesBridge is not required to provide any special handling for the
`@javax.enterprise.context.RequestScoped` or `@javax.portlet.annotations.PortletRequestScoped` annotations since their
usage is already defined by the Portlet 3.0 Specification and corresponding requirements are implemented by the
underlying portlet container. This means that by default, beans that are annotated with
`@javax.enterprise.context.RequestScoped` <sup>[7.2](tck-tests.md#7.2)</sup> or
`@javax.portlet.annotations.PortletRequestScoped` <sup>[7.3](tck-tests.md#7.3)</sup> will _not_ participate in the
"Bridge Request Scope" as defined by section [5.1.2](chapter-5-request-lifecycle.md#5.1.2) titled "Managing Lifecycle
State".

The FacesBridge implementation must provide an *optional* CDI extension that treats beans annotated with
`@javax.enterprise.context.RequestScoped`<sup>[7.4](tck-tests.md#7.4)</sup> or
`@javax.portlet.annotations.PortletRequestScoped`<sup>[7.5](tck-tests.md#7.5)</sup> as though they were annotated with
`@javax.portlet.faces.annotation.BridgeRequestScoped`. Developers can opt-in to the CDI extension by having a
META-INF/services/javax.enterprise.inject.spi.Extension file within the classpath of the WAR archive. The contents of
the file is the FQCN of the optional CDI extension provided by the FacesBridge implementation.

## <a name="7.3"></a>7.3 Producers

Section 20.3.1 of the Portlet 3.0 Specification titled "Portlet Request Scoped Beans" provides a table that lists all of
the `@Named` `@PortletRequestScoped` CDI beans and their corresponding EL names. Since the FacesBridge provides factories
that potentially decorate these `@PortletRequestScoped` beans, the FacesBridge must provide alternative CDI producers.

|EL Name|FacesBridge Factory|
|-------|-------------------|
|actionParams<sup>[7.6](tck-tests.md#7.6)</sup>|BridgePortletRequestFactory|
|actionRequest<sup>[7.7](tck-tests.md#7.7)</sup>|BridgePortletRequestFactory|
|actionResponse<sup>[7.8](tck-tests.md#7.8)</sup>|BridgePortletResponseFactory|
|clientDataRequest<sup>[7.9](tck-tests.md#7.9)</sup>|BridgePortletRequestFactory|
|contextPath<sup>[7.10](tck-tests.md#7.10)</sup>|BridgePortletRequestFactory|
|cookies<sup>[7.11](tck-tests.md#7.11)</sup>|BridgePortletRequestFactory|
|eventRequest<sup>[7.12](tck-tests.md#7.12)</sup>|BridgePortletRequestFactory|
|eventResponse<sup>[7.13](tck-tests.md#7.13)</sup>|BridgePortletResponseFactory|
|headerRequest<sup>[7.14](tck-tests.md#7.14)</sup>|BridgePortletRequestFactory|
|headerResponse<sup>[7.15](tck-tests.md#7.15)</sup>|BridgePortletResponseFactory|
|locales<sup>[7.16](tck-tests.md#7.16)</sup>|BridgePortletRequestFactory|
|mimeResponse<sup>[7.17](tck-tests.md#7.17)</sup>|BridgePortletResponseFactory|
|mutableRenderParams<sup>[7.18](tck-tests.md#7.18)</sup>|BridgePortletRequestFactory|
|namespace<sup>[7.19](tck-tests.md#7.19)</sup>|BridgePortletResponseFactory|
|portletConfig<sup>[7.20](tck-tests.md#7.20)</sup>|BridgePortletConfigFactory|
|portletContext<sup>[7.21](tck-tests.md#7.21)</sup>|BridgePortletConfigFactory|
|portletMode<sup>[7.22](tck-tests.md#7.22)</sup>|BridgePortletRequestFactory|
|portletName<sup>[7.23](tck-tests.md#7.23)</sup>|BridgePortletConfigFactory|
|portletPreferences<sup>[7.24](tck-tests.md#7.24)</sup>|BridgePortletRequestFactory|
|portletRequest<sup>[7.25](tck-tests.md#7.25)</sup>|BridgePortletRequestFactory|
|portletResponse<sup>[7.26](tck-tests.md#7.26)</sup>|BridgePortletResponseFactory|
|portletSession<sup>[7.27](tck-tests.md#7.27)</sup>|BridgePortletRequestFactory|
|renderParams<sup>[7.28](tck-tests.md#7.28)</sup>|BridgePortletRequestFactory|
|renderRequest<sup>[7.29](tck-tests.md#7.29)</sup>|BridgePortletRequestFactory|
|renderResponse<sup>[7.30](tck-tests.md#7.30)</sup>|BridgePortletResponseFactory|
|resourceRequest<sup>[7.31](tck-tests.md#7.31)</sup>|BridgePortletRequestFactory|
|resourceResponse<sup>[7.32](tck-tests.md#7.32)</sup>|BridgePortletResponseFactory|
|stateAwareResponse<sup>[7.33](tck-tests.md#7.33)</sup>|BridgePortletResponseFactory|
|windowId<sup>[7.34](tck-tests.md#7.34)</sup>|BridgePortletRequestFactory|
|windowState<sup>[7.35](tck-tests.md#7.35)</sup>|BridgePortletRequestFactory|
