/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.component.inputfile;
//J-

import javax.annotation.Generated;
import javax.faces.component.html.HtmlInputFile;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputFileBase extends HtmlInputFile {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.bridge.component.inputfile.InputFile";
	public static final String RENDERER_TYPE = "com.liferay.faces.bridge.component.inputfile.InputFileRenderer";

	// Protected Enumerations
	protected enum InputFilePropertyKeys {
		auto,
		fileUploadListener,
		multiple
	}

	public InputFileBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	/**
	 * <code>auto</code> attribute description:
	 * <br /><br />
	 * When this flag is true, files are uploaded automatically as soon as they are selected. The default value is false.
	 */
	public boolean isAuto() {
		return (Boolean) getStateHelper().eval(InputFilePropertyKeys.auto, false);
	}

	/**
	 * <code>auto</code> attribute description:
	 * <br /><br />
	 * When this flag is true, files are uploaded automatically as soon as they are selected. The default value is false.
	 */
	public void setAuto(boolean auto) {
		getStateHelper().put(InputFilePropertyKeys.auto, auto);
	}

	/**
	 * <code>fileUploadListener</code> attribute description:
	 * <br /><br />
	 * A method that is executed when a file is uploaded. The method must be <code>public</code>, return <code>void</code>, and take a <code>FileUploadEvent</code> argument.
	 */
	public javax.el.MethodExpression getFileUploadListener() {
		return (javax.el.MethodExpression) getStateHelper().eval(InputFilePropertyKeys.fileUploadListener, null);
	}

	/**
	 * <code>fileUploadListener</code> attribute description:
	 * <br /><br />
	 * A method that is executed when a file is uploaded. The method must be <code>public</code>, return <code>void</code>, and take a <code>FileUploadEvent</code> argument.
	 */
	public void setFileUploadListener(javax.el.MethodExpression fileUploadListener) {
		getStateHelper().put(InputFilePropertyKeys.fileUploadListener, fileUploadListener);
	}

	public String getMultiple() {
		return (String) getStateHelper().eval(InputFilePropertyKeys.multiple, null);
	}

	/**
	 * <code>multiple</code> attribute description:
	 * <br /><br />
	 * HTML passthrough attribute specifying whether or not multiple files can be uploaded. Valid values are blank (to upload a single file) or "multiple" (to upload multiple files).
	 */
	public void setMultiple(String multiple) {
		getStateHelper().put(InputFilePropertyKeys.multiple, multiple);
	}
}
//J+
