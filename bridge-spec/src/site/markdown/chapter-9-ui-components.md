_Version: 6.0.0-SNAPSHOT_

# 9. UI Components

* * *

The JSF 2.2 Specification requires the **JSF runtime** to implement renderers for `UIComponent` classes that are defined
in the JSF API module. In most cases, these renderers function equally well in both webapp and portlet environments.
However, some component renderers must be overridden by the FacesBridge in order for them to function properly in a
portlet environment.

Additionally, the FacesBridge is required to provide JSF `UIComponent` equivalents for the Portlet 3.0 JSP tags.

## <a name="9.1"></a>9.1 h:body

According to the portlet specification, elements such as `<head>`, `<title>`, and `<body>` may not be output to the
response by a portlet. Because of this, the FacesBridge must prevent the Faces runtime from writing `<body>...</body>`
to the response when it invokes the renderer for `<h:body/>`. Instead, the FacesBridge must ensure that a
`<div>...</div>` is written and the value of the "id" attribute must be the clientId of the UIViewRoot.

The FacesBridge must also support the following use-case:

    <?xml version="1.0" encoding="UTF-8"?>
    <f:view xmlns="http://www.w3.org/1999/xhtml"
	    xmlns:f="http://xmlns.jcp.org/jsf/core" xmlns:h="http://xmlns.jcp.org/jsf/html"
	    xmlns:ui="http://xmlns.jcp.org/jsf/facelets" xmlns:c="http://xmlns.jcp.org/jsp/jstl/core">
	    <h:head />
	    <h:body>
		    <h:form>
			    <h:commandButton>
				    <f:ajax execute="@form" render="@all" />
			    </h:commandButton>
		    </h:form>
	    </h:body>
    </f:view>

When the commandButton is clicked, this will cause a partial-response similar to the following to be generated by the
FacesRuntime:

    <partial-response id="j_id1">
	    <changes>
		    <update id="javax.faces.ViewRoot">
			    <![CDATA[...]]>
		    </update>
	    <update id="j_id1:javax.faces.ViewState:0">
		    <![CDATA[ -234567890098765432:123456789076543217 ]]>
	    </update>
	    </changes>
    </partial-response>

In this scenario, the FacesBridge must ensure that only the `<div>...</div>` is replaced in the client rather than the
entire `<body>...</body>`.

The manner in which the FacesBridge fulfills this requirement is an implementation detail. One possibility is for the
FacesBridge to provide a custom jsf.js resource. Another might be for the FacesBridge to introduce a custom
`ResponseWriter` that monitors calls to `ResponseWriter.startElement(String name, UIComponent component)` and
`ResponseWriter.writeAttribute(String name, Object value, String property)` in order to transform value of the id
attribute from "javax.faces.ViewRoot" to the id of the `<div>...</div>`.

As a consequence of the FacesBridge specifying the id of the `<div>...</div>` "javax.faces.ViewRoot", the JavaScript
code
in the Mojarra implementation of jsf.js will not be able to dynamically add the `<input type="hidden"
name="javax.faces.ViewState"/>` hidden field to forms. Because of this, FacesBridge must ensure that this takes place,
but the manner in which it takes place is an implementation detail. Note that this will not be a problem with JSF 2.3,
due to the solution provided by
[JAVASERVERFACES_SPEC_PUBLIC-790](https://github.com/javaee/javaserverfaces-spec/issues/790) in the Faces runtime.

Test: <sup>[[9.1](tck-tests.md#9.1)]</sup>

## <a name="9.2"></a>9.2 h:head

According to the portlet specification, elements such as `<head>`, `<title>`, and `<body>` may not be output to the
response by a portlet. Because of this, the FacesBridge must prevent the Faces runtime from writing `<head>...</head>`
to the response when it invokes the renderer for `h:head`. Instead, the FacesBridge must ensure that a JSF resources
(which are targeted for the head) are _added_ to the head by calling `HeaderResponse.addResourceDependency(String name,
String scope, String version, String markup)` for each resource.

The following Facelet markup demonstrates usage of the `h:head` tag as well as the "jsf.js" resource being targeted for
the head due to the presence of the `f:ajax` tag:

    <f:view xmlns="http://www.w3.org/1999/xhtml" xmlns:h="http://xmlns.jcp.org/jsf/html">
        <h:head/>
        <h:form>
            <h:inputText>
                <f:ajax render="@form" />
            </h:inputText>
        </h:form>
    </f:view>

Test: <sup>[[9.2](tck-tests.md#9.2)]</sup>

## <a name="9.3"></a>9.3 h:inputFile

The `h:inputFile` component tag causes an instance of
[javax.faces.component.html.HtmlInputFile](https://javaserverfaces.github.io/docs/2.2/javadocs/javax/faces/component/html/HtmlInputFile.html)
to be placed in the component tree. The FacesBridge must support the following typical use-cases, typically by
overriding the corresponding renderer provided by the JSF runtime. Java backing beans may be managed by CDI and/or the
Managed Bean Facility:

XHTML Facelet View:

    <ui:composition xmlns="http://www.w3.org/1999/xhtml" xmlns:h="http://xmlns.jcp.org/jsf/html"
	    xmlns:ui="http://xmlns.jcp.org/jsf/facelets">
	    <h:form enctype="multipart/form-data">
		    <h:inputFile value="#{htmlInputFileBackingBean.uploadedPart}" />
		    <h:commandButton value="Submit" />
	    </h:form>
    </ui:composition>
  
Java Backing Bean:

    @Named
    @RequestScoped
    public class HtmlInputFileBackingBean {
    
    	private javax.servlet.http.Part uploadedPart;

    	public void setUploadedPart(javax.servlet.http.Part uploadedPart) {
    		this.uploadedPart = uploadedPart;
    	}
    }

The FacesBridge must also support uploading of files via XmlHttpRequest using the standard `<f:ajax/>` tag.

XHTML Facelet View:

    <ui:composition xmlns="http://www.w3.org/1999/xhtml" xmlns:h="http://xmlns.jcp.org/jsf/html"
	    xmlns:ui="http://xmlns.jcp.org/jsf/facelets">
	    <h:form enctype="multipart/form-data">
		    <h:inputFile id="file" 
			    value="#{htmlInputFileBackingBean.uploadedPart}">
			    <f:ajax render="@form" />
			</h:inputFile>
	    </h:form>
    </ui:composition>

Test: <sup>[[9.3](tck-tests.md#9.3)]</sup>

## <a name="9.4"></a>9.4 h:message and h:messages

The Portlet Specification defines the following CSS class names for the portlet container to supply via CSS style
sheets:

- portlet-msg-alert
- portlet-msg-error
- portlet-msg-info
- portlet-msg-status
- portlet-msg-success
 
This feature provides developers with a way to rely on standard CSS class names for styling text base elements.

The JSF Specification defines the `h:message` and `h:messages` component tags that developers can use to provide
feedback to the user. The FacesBridge must ensure that the `errorClass`, `fatalClass`, `infoClass`, and `warnClass`
attribute values are appended with the standard portlet CSS class names according to the following mapping:

|UIComponent Attribute|Portlet CSS Class|
|---------------------|-----------------|
|errorClass|portlet-msg-error|
|fatalClass|portlet-msg-error|
|infoClass|portlet-msg-info|
|warnClass|portlet-msg-alert|

Test: <sup>[[9.4](tck-tests.md#9.4)]</sup>

## <a name="9.5"></a>9.5 portlet:actionURL

The FacesBridge API must define the `javax.portlet.faces.component.PortletActionURL` component that extends
`javax.faces.component.UIComponentBase`. The component family must be `"javax.portlet.faces.URL"` and the component type
must be `"javax.portlet.faces.ActionURL"`.

The FacesBridge implementation must provide the `portlet:actionURL` component tag with the following attributes:

|Name|Type|
|----|----|
|copyCurrentRenderParameters|`boolean`|
|escapeXml|`boolean`|
|name|`java.lang.String`|
|portletMode|`java.lang.String`|
|secure|`java.lang.Boolean`|
|var|`java.lang.String`|
|windowState|`java.lang.String`|

The FacesBridge implementation must provide a corresponding renderer that will output the `toString()` value of a
`javax.portlet.ActionURL` according to the requirements listed in Section 26.2 of the Portlet 3.0 Specification titled
"actionURL Tag".

Test: <sup>[[9.5](tck-tests.md#9.5)]</sup>

## <a name="9.6"></a>9.6 portlet:namespace

The FacesBridge API must define the `javax.portlet.faces.component.PortletNamespace` component that extends
`javax.faces.component.UIComponentBase`. The component family must be `"javax.portlet.faces.Namespace"` and the component type
must be `"javax.portlet.faces.Namespace"`.

The FacesBridge implementation must provide the `portlet:actionURL` component tag with the following attribute:

|Name|Type|
|----|----|
|var|`java.lang.String`|

The FacesBridge implementation must provide a corresponding renderer that will output the response namespace according
to the requirements listed in Section 26.5 of the Portlet 3.0 Specification titled "namespace Tag". However, the value
of the response namespace must be obtained by calling
`FacesContext.getCurrentInstance().getExternalContext().encodeNamespace("")` rather than
`PortletResponse.getNamespace()` in order to allow for decoration via the `ExternalContext` extension point.

Test: <sup>[[9.6](tck-tests.md#9.6)]</sup>

## <a name="9.7"></a>9.7 portlet:param

The FacesBridge API must define the `javax.portlet.faces.component.PortletParam` component that extends
`javax.faces.component.UIComponentBase`. The component family must be `"javax.portlet.faces.URL"` and the component type
must be `"javax.portlet.faces.Param"`.

The FacesBridge implementation must provide the `portlet:param` component tag with the following attributes:

|Name|Type|
|----|----|
|name|`java.lang.String`|
|type|`java.lang.String`|
|value|`java.lang.String`|

The `portlet:param` component tag is designed to be used as a child of a surrounding `portlet:actionURL`,
`portlet:renderURL`, or `portlet:renderURL` component tag. The FacesBridge implementation must provide a corresponding
renderer that will set parameters on the underlying portlet URL according to the requirements listed in Section 26.6 of
the Portlet 3.0 Specification titled "param Tag".

Test: <sup>[[9.7](tck-tests.md#9.7)]</sup>

## <a name="9.8"></a>9.8 portlet:property

The FacesBridge API must define the `javax.portlet.faces.component.PortletProperty` component that extends
`javax.faces.component.UIComponentBase`. The component family must be `"javax.portlet.faces.URL"` and the component type
must be `"javax.portlet.faces.Property"`.

The FacesBridge implementation must provide the `portlet:property` component tag with the following attributes:

|Name|Type|
|----|----|
|name|`java.lang.String`|
|value|`java.lang.String`|

The `portlet:property` component tag is designed to be used as a child of a surrounding `portlet:actionURL`,
`portlet:renderURL`, or `portlet:renderURL` component tag. The FacesBridge implementation must provide a corresponding
renderer that will add properties on the underlying portlet URL according to the requirements listed in Section 26.7 of
the Portlet 3.0 Specification titled "property Tag".

Test: <sup>[[9.8](tck-tests.md#9.8)]</sup>

## <a name="9.9"></a>9.9 portlet:renderURL

The FacesBridge API must define the `javax.portlet.faces.component.PortletRenderURL` component that extends
`javax.faces.component.UIComponentBase`. The component family must be `"javax.portlet.faces.URL"` and the component type
must be `"javax.portlet.faces.RenderURL"`.

The FacesBridge implementation must provide the `portlet:renderURL` component tag with the following attributes:

|Name|Type|
|----|----|
|copyCurrentRenderParameters|`boolean`|
|escapeXml|`boolean`|
|portletMode|`java.lang.String`|
|secure|`java.lang.Boolean`|
|var|`java.lang.String`|
|windowState|`java.lang.String`|

The FacesBridge implementation must provide a corresponding renderer that will output the `toString()` value of a
`javax.portlet.RenderURL` according to the requirements listed in Section 26.3 of the Portlet 3.0 Specification titled
"renderURL Tag".

Test: <sup>[[9.9](tck-tests.md#9.9)]</sup>

## <a name="9.10"></a>9.10 portlet:resourceURL

The FacesBridge API must define the `javax.portlet.faces.component.PortletResourceURL` component that extends
`javax.faces.component.UIComponentBase`. The component family must be `"javax.portlet.faces.URL"` and the component type
must be `"javax.portlet.faces.ResourceURL"`.

The FacesBridge implementation must provide the `portlet:resourceURL` component tag with the following attributes:

|Name|Type|
|----|----|
|cacheability|`java.lang.String`|
|escapeXml|`boolean`|
|id|`java.lang.String`|
|secure|`java.lang.Boolean`|
|var|`java.lang.String`|

The FacesBridge implementation must provide a corresponding renderer that will output the `toString()` value of a
`javax.portlet.ResourceURL` according to the requirements listed in Section 26.4 of the Portlet 3.0 Specification titled
"resourceURL Tag".

Test: <sup>[[9.10](tck-tests.md#9.10)]</sup>
