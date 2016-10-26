_Version: 2.0.1-mr1-SNAPSHOT_

# 1. Preface

* * *

This document is the **Portlet 2.0 Bridge for JavaServer&trade; Faces 1.2 Specification**. It defines the subsystem that
allows the JavaServer&trade; Faces (JSF) runtime to execute and satisfy a portlet request. The bridge is layered between
the portlet container and the JSF runtime. As such, its behavior and implementation depends on both. There would need to
be a discrete bridge specification and implementation for each version combination of the Portlet API and JSF API. This
version specifies the bridging function necessary to execute JSF 1.2 views and resources in a Java&trade; Portlet 2.0
environment.

## <a name="1.1"></a>1.1 Additional Sources

The specification is intended to be a complete and clear explanation of the portlet bridge function. If questions remain
the following may be consulted:

- An open source reference implementation (RI) has been made available via the [Liferay Faces
Bridge](http://www.liferay.com/community/liferay-projects/liferay-faces) project. It runs with the Mojarra 1.2
implementation in any portlet container. This RI provides a behavioral benchmark for this specification. Where the
specification leaves implementation of a particular feature open to interpretation, implementors may use the reference
implementation as a model that illustrates the intention of the specification.
- A Technology Compatibility Kit (TCK) has been provided for assessing whether implementations meet the compatibility
requirements of the this specification. The test results have normative value for resolving questions about whether or
not an implementation is compliant.
- If further clarification is required, the JSR 329 Expert Group should be consulted. It is the final arbiter of such
issues.

Comments and feedback are welcomed, and will be used to improve future versions.

## <a name="1.2"></a>1.2 Who Should Read This Specification

The intended audience for this specification includes the following groups:

- **Bridge Implementor:** e.g. Java&trade; Portlet container implementors that want to expand the reach of their
environment to support portlets whose implementations are either in part, or in whole, implemented using JSF.
- **Portlet Developer:** e.g. The portlet author interested in using the JSF standard to author  portlets.
- **JSF Developer:** e.g. JSF authoring tool developers who wish to support web applications that publish portions of
their Faces views as portlets or JSF extension developers who want to ensure that their extension can run in a portlet
(bridge) environment.

The specification is not a user's guide for developing portlets using JSF and is not intended to be used as such. As it
focuses on the behavioral details of the bridge, it is primarily oriented towards bridge implementors. Portlet
developers or JSF developers who want quick access to the information that is most pertinent to them should consult:

*Basic information:*

- [Chapter 2](chapter-2-overview.html): This chapter provides a short overview of portlets, JSF, and the bridge
architecture. It also introduces you to the terminology used in the specification.

- [Chapter 4](chapter-4-genericfacesportlet.html): This chapter defines the behavior of the GenericFacesPortlet; a
portlet implementation provided by the bridge API that is commonly reference in the `portlet-class` element of the
portlet.xml descriptor. It hides all the details of initializing, configuring, managing, and executing the bridge.

- [Chapter 3](chapter-3-bridge-interface.html): This chapter defines the bridge interface and configuration which a
portlet uses to initialize, configure, manage, and execute it. I.e. defines the interface and configuration details
hidden to you in [Chapter 4](chapter-4-genericfacesportlet.html) by the GenericFacesPortlet implementation.

- [Chapter 6, section 6.5](chapter-6-managing-faces.html#6.5) Through subsection 6.5.2.1 plus section 6.5.2.3: these
sections provide information related to portlet specific EL evaluation.

*Advanced Information:*

- [Chapter 6, section 6.8](chapter-6-managing-faces.html#6.8): JSF provides a facility allowing managedbean implementors
to be notified before the bean is destroyed. This section includes important information for supporting this
notification (correctly) in a portlet environment.

- [Chapter 5, section 5.1](chapter-5-request-lifecycle.html#5.1): This section describes how the portlet and JSF
lifecycles differ and how the bridge manages state to ensure expected behavior in JSF. You will need to understand this
information and how to exclude specific state from being managed by the bridge if you determine that such state
management is detrimental/not needed.

- [Chapter 7](chapter-7-writebehindresponse.html): This chapter defines the behavior of a servlet filter that can be
used with the bridge to provide correct markup ordering when your JSF view is represented by a JSP whose output comes
from regular JSP markup as well has JSF components. **Note**: filter implementations aren't part of the formal
specification because JSF is structured in such a way as to require distinct implementations per JSF implementation. If
you need such a filter to correct the jsp ordering problem you will have to search for a publicly available
implementation for your JSF implementation type or write one yourself.

## <a name="1.3"></a>1.3 API Reference

An accompanying Javadoc&trade; includes the full specifications of classes, interfaces, and method signatures.

## <a name="1.4"></a>1.4 Important Dependent Java&trade; Specifications

As a bridge between a Java Portlet 2.0 container and a JSF 1.2 environment, this specification depends directly on each
of these specifications:

- [JavaServer&trade; Faces 1.2](https://www.jcp.org/en/jsr/detail?id=252)
- [Java&trade; Portlet Specification 2.0](https://www.jcp.org/en/jsr/detail?id=286)

These specifications are available in their respective JSR homes (JSR 252; JSR 286) on
[http://www.jcp.org](http://www.jcp.org).

## <a name="1.5"></a>1.5 Dependent Java&trade; Versions

The portlet bridge should run on any Java Portlet 2.0 container. It should also run on a portlet container running a
newer version of the Java Portlet specification. However, it is recommended that the version of the bridge be paired
with both the version of the Java Portlet container and JSF runtime it is deployed with. This ensures the best
implementation for the given environment.

The bridge requires a Java&trade; 1.5 environment because this is the minimum required version for running JSF 1.2.

## <a name="1.6"></a>1.6 Terminology

The key words MUST, MUST NOT, REQUIRED, SHALL, SHALL NOT, SHOULD, SHOULD NOT, RECOMMENDED, MAY, and OPTIONAL in this
document are to be interpreted as described in [RFC2119](https://www.ietf.org/rfc/rfc2119.txt).

## <a name="1.7"></a>1.7 Providing Feedback

We welcome any and all feedback about this specification. Please subscribe and e-mail your comments to
jsr329-observers@faces-bridge-spec.java.net. Please note that due to the volume of feedback that we receive, you will
not normally receive a reply from an engineer. However, each and every comment is read, evaluated, and archived by the
specification team.

## <a name="1.8"></a>1.8 Acknowledgments

The Portlet 2.0 Bridge Specification V1.0 is the result of the work of JSR 329 Expert Group which migrated from JSR 301:
Subbu Allamaraju, Brodi Beartusk, Andy Bosch, Matt Brasier, Benjamin Bratkus, Wesley Budziwojski, Raschka Christian,
Shankar Djeyassilane, Nikolai Dokovski, Ate Douma, Deepak Gothe, Ted Goddard, Wesley Hales, Jondean Haley, Roger Kitain,
Danny Machak, Kito Mann, Martin Marinschek, Scott O'Bryan, Stan Silvert, Thomas Spiegl, Shashank Tiwari, Julien Viet,
James Ward, and Carsten Ziegeler. Besides those above representing themselves, the following companies were represented
in the expert group: BEA Systems, SAP AG, Oracle Corporation, HIPPO, Sun Microsystems, Icesoft Technologies Inc., Red
Hat Middleware LLC., Adobe Systems, and S&N AG. Though the contributions of all were important, special thanks is given
to Nikolai Dokovski of SAP AG and Scott O'Bryan of Oracle Corporation who were particularly active throughout the entire
specification process and provided immeasurable support and feedback.
