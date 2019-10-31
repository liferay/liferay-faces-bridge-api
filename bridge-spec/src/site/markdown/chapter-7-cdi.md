_Version: 5.0.0-pr-SNAPSHOT_

# 7. CDI

* * *

The Portlet 3.0 Specification requires the *portlet container* to support portlets that rely on Contexts and Dependency
Injection (CDI). Such portlets are commonly referred to as "bean" portlets. The portlet container must extend the
functionality of the *CDI container* in order to instantiate bean portlets (which makes it possible for developers to
use the `@Inject` annotation within bean portlet classes).

## <a name="7.1"></a>7.1 Bean Portlet Registration.

If a web archive contains a WEB-INF/beans.xml descriptor, then the portlet container will attempt to register all
portlets annotated with `@PortletConfiguration` as well as any portlets defined in WEB-INF/portlet.xml as bean portlets.
The FacesBridge must support bean portlet registration but requires the developer to use `GenericFacesPortlet` (or a
class that extends `GenericFacesPortlet`). To this end, the `GenericFacesPortlet` class is annotated with
`@javax.enterprise.context.ApplicationScoped` which will ensure that the bean portlet is registered as a singleton.
