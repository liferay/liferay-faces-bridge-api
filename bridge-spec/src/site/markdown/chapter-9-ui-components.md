_Version: 6.0.0-pr-SNAPSHOT_

# 9. UI Components

* * *

The JSF 2.2 Specification requires the **JSF runtime** to implement renderers for `UIComponent` classes that are defined
in the JSF API module. In most cases, these renderers function equally well in both webapp and portlet environments.
However, some component renderers must be overridden by the FacesBridge in order for them to function properly in a
portlet environment.

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
