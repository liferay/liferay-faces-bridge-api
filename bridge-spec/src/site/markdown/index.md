_Version: 6.0.0-edr1-SNAPSHOT_

# Table of Contents

1. [Preface](chapter-1-preface.md)

    [1.1 Additional Sources](chapter-1-preface.md#1.1)

    [1.2 Who Should Read This Specification](chapter-1-preface.md#1.2)

    [1.3 API Reference](chapter-1-preface.md#1.3)

    [1.4 Important Dependent Java&trade; Specifications](chapter-1-preface.md#1.4)

    [1.5 Dependent Java&trade; Versions](chapter-1-preface.md#1.5)

    [1.6 Terminology](chapter-1-preface.md#1.6)

    [1.7 Providing Feedback](chapter-1-preface.md#1.7)

    [1.8 Acknowledgments](chapter-1-preface.md#1.8)

2. [Overview](chapter-2-overview.md)

    [2.1 What is a Portlet Bridge?](chapter-2-overview.md#2.1)

    [2.2 What is a Portlet?](chapter-2-overview.md#2.2)

    [2.3 What is JavaServer&trade; Faces](chapter-2-overview.md#2.3)

    [2.4 Architectural Overview](chapter-2-overview.md#2.4)

    [2.5 Terminology](chapter-2-overview.md#2.5)

    [2.6 What's New in Portlet 2.0?](chapter-2-overview.md#2.6)

    [2.6.1 Events](chapter-2-overview.md#2.6.1)

    [2.6.2 Portlet Served Resources](chapter-2-overview.md#2.6.2)

    [2.6.3 Public Render Parameters](chapter-2-overview.md#2.6.3)

    [2.6.4 Portlet Filters and Wrappable Request/Response Objects](chapter-2-overview.md#2.6.4)

    [2.6.5 Dispatch Changes](chapter-2-overview.md#2.6.5)

3. [Bridge Interface](chapter-3-bridge-interface.md)

    [3.1 Discovering and Instantiating the Bridge](chapter-3-bridge-interface.md#3.1)

    [3.2 Initializing the Bridge](chapter-3-bridge-interface.md#3.2)

    [3.3 Destroying the Bridge](chapter-3-bridge-interface.md#3.3)

    [3.4 Request Processing](chapter-3-bridge-interface.md#3.4)

4. [GenericFacesPortlet](chapter-4-genericfacesportlet.md)

    [4.1 Configuration](chapter-4-genericfacesportlet.md#4.1)

    [4.2 Structure](chapter-4-genericfacesportlet.md#4.2)

    [4.2.1 init():](chapter-4-genericfacesportlet.md#4.2.1)

    [4.2.2 destroy():](chapter-4-genericfacesportlet.md#4.2.2)

    [4.2.3 doDispatch():](chapter-4-genericfacesportlet.md#4.2.3)

    [4.2.4 doView(), doEdit(), doHelp(), processAction(), serveResource(), processEvent():](chapter-4-genericfacesportlet.md#4.2.4)

    [4.2.5 Request Processing](chapter-4-genericfacesportlet.md#4.2.5)

    [4.2.6 getBridgeClassName()](chapter-4-genericfacesportlet.md#4.2.6)

    [4.2.7 getDefaultViewIdMap()](chapter-4-genericfacesportlet.md#4.2.7)

    [4.2.8 getExcludedRequestAttributes()](chapter-4-genericfacesportlet.md#4.2.8)

    [4.2.9 isPreserveActionParameters()](chapter-4-genericfacesportlet.md#4.2.9)

    [4.2.10 getResponseContentType()](chapter-4-genericfacesportlet.md#4.2.10)

    [4.2.11 getResponseCharacterSetEncoding()](chapter-4-genericfacesportlet.md#4.2.11)

    [4.2.12 getBridgeEventHandler()](chapter-4-genericfacesportlet.md#4.2.12)

    [4.2.13 getBridgePublicRenderParameterHandler()](chapter-4-genericfacesportlet.md#4.2.13)

    [4.2.14 isAutoDispatchEvents()](chapter-4-genericfacesportlet.md#4.2.14)

    [4.2.15 getFacesBridge()](chapter-4-genericfacesportlet.md#4.2.15)

    [4.2.16 getDefaultRenderKitId()](chapter-4-genericfacesportlet.md#4.2.16)

5. [Request Lifecycle](chapter-5-request-lifecycle.md)

    [5.1 Request Lifecycle](chapter-5-request-lifecycle.md#5.1)

    [5.1.1 Bridge Request Lifecycle](chapter-5-request-lifecycle.md#5.1.1)

    [5.1.2 Managing Lifecycle State](chapter-5-request-lifecycle.md#5.1.2)

    [5.1.2.1 Excluding Attributes from the Bridge Request Scope](chapter-5-request-lifecycle.md#5.1.2.1)

    [5.1.2.2 Considerations in Managing the VIEW_STATE_PARAM parameter](chapter-5-request-lifecycle.md#5.1.2.2)

    [5.1.2.3 Considerations for Managing Faces Messages](chapter-5-request-lifecycle.md#5.1.2.3)

    [5.2 Executing a Faces Request](chapter-5-request-lifecycle.md#5.2)

    [5.2.1 Acquiring a Faces Lifecycle](chapter-5-request-lifecycle.md#5.2.1)

    [5.2.2 Acquiring the FacesContext](chapter-5-request-lifecycle.md#5.2.2)

    [5.2.2.1 Identifying the Portlet Request Phase](chapter-5-request-lifecycle.md#5.2.2.1)

    [5.2.3 Determining the Target View](chapter-5-request-lifecycle.md#5.2.3)

    [5.2.4 Executing a Portlet Action Request](chapter-5-request-lifecycle.md#5.2.4)

    [5.2.5 Executing a Portlet Event Request](chapter-5-request-lifecycle.md#5.2.5)

    [5.2.6 Executing a Portlet Render Request](chapter-5-request-lifecycle.md#5.2.6)

    [5.2.7 Executing a Portlet Resource Request](chapter-5-request-lifecycle.md#5.2.7)

    [5.2.8 Releasing the FacesContext](chapter-5-request-lifecycle.md#5.2.8)

    [5.3 Processing Public Render Parameters](chapter-5-request-lifecycle.md#5.3)

    [5.3.1 Configuring a Public Render Parameter Mapping](chapter-5-request-lifecycle.md#5.3.1)

    [5.3.2 Processing incoming Public Render Parameters](chapter-5-request-lifecycle.md#5.3.2)

    [5.3.3  Processing outgoing Public Render Parameters (changes)](chapter-5-request-lifecycle.md#5.3.3)

    [5.4 Supporting PortletMode changes](chapter-5-request-lifecycle.md#5.4)

    [5.4.1 Mode changes and Lifecycle management](chapter-5-request-lifecycle.md#5.4.1)

    [5.4.2 Encoding `PortletMode` changes in Faces navigation](chapter-5-request-lifecycle.md#5.4.2)

    [5.4.3 Navigating to a mode's last `viewId`](chapter-5-request-lifecycle.md#5.4.3)

6. [Managing Faces](chapter-6-managing-faces.md)

    [6.1 ExternalContext](chapter-6-managing-faces.md#6.1)

    [6.1.1 FacesContextFactory](chapter-6-managing-faces.md#6.1.1)

    [6.1.2 FacesContext](chapter-6-managing-faces.md#6.1.2)

    [6.1.3 ExternalContext Methods](chapter-6-managing-faces.md#6.1.3)

    [6.1.3.1 Methods that deviate from Faces 1.2 Javadoc](chapter-6-managing-faces.md#6.1.3.1)

    [6.1.3.2 Methods that conform with Faces 1.2 Javadoc](chapter-6-managing-faces.md#6.1.3.2)

    [6.2 ViewHandler](chapter-6-managing-faces.md#6.2)

    [6.2.1 Method Requirements](chapter-6-managing-faces.md#6.2.1)

    [6.2.2 Other Methods](chapter-6-managing-faces.md#6.2.2)

    [6.3 StateManager](chapter-6-managing-faces.md#6.3)

    [6.4 Phase Listeners](chapter-6-managing-faces.md#6.4)

    [6.5 Expression Language Resolution](chapter-6-managing-faces.md#6.5)

    [6.5.1 Implicit Objects](chapter-6-managing-faces.md#6.5.1)

    [6.5.2 Bridge ELResolver](chapter-6-managing-faces.md#6.5.2)

    [6.5.2.1 Additional Implicit Object Support for Portlets](chapter-6-managing-faces.md#6.5.2.1)

    [6.5.2.2 ELResolver Requirements](chapter-6-managing-faces.md#6.5.2.2)

    [6.5.2.3 The javax.portlet.faces.preference.Preference interface](chapter-6-managing-faces.md#6.5.2.3)

    [6.6 Namespacing](chapter-6-managing-faces.md#6.6)

    [6.7 Supporting isPostback() during RENDER_PHASE](chapter-6-managing-faces.md#6.7)

    [6.8 Supporting PreDestroy Annotated Methods](chapter-6-managing-faces.md#6.8)

    [6.8.1 Managed Bean Changes](chapter-6-managing-faces.md#6.8.1)

    [6.8.2 Bridge requirements](chapter-6-managing-faces.md#6.8.2)

    [6.9 Setting the RenderKit used by a Portlet](chapter-6-managing-faces.md#6.9)

7. _RESERVED_

8. [Faces Extensions](chapter-8-faces-extensions.md)

    [8.1 Configuration](chapter-8-faces-extensions.md#8.1)

    [8.2 Initializing Extensions](chapter-8-faces-extensions.md#8.2)

    [8.3 Portlet Considerations](chapter-8-faces-extensions.md#8.3)

    [8.4 General Considerations](chapter-8-faces-extensions.md#8.4)

    [8.5 Cohabiting with the Bridge's ViewHandler](chapter-8-faces-extensions.md#8.5)

    [8.6 Cohabiting with the Bridge's StateManager](chapter-8-faces-extensions.md#8.6)

    [8.7 Excluding Attributes](chapter-8-faces-extensions.md#8.7)

    [Appendix. TCK Tests](tck-tests.md)

    [Chapter 3 Tests](tck-tests.md#3)

    [Chapter 4 Tests](tck-tests.md#4)

    [Chapter 5 Tests](tck-tests.md#5)

    [Chapter 6 Tests](tck-tests.md#6)

    [Chapter 7 Tests](tck-tests.md#7)

    [Chapter 8 Tests](tck-tests.md#8)

