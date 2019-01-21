/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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
import org.osgi.annotation.versioning.ProviderType;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
@ProviderType
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
	 * <p><code>auto</code> attribute description:</p>
	 *
	 * <p>When this flag is true, files are uploaded automatically as soon as they are selected. The default value is false.</p>
	 */
	public boolean isAuto() {
		return (Boolean) getStateHelper().eval(InputFilePropertyKeys.auto, false);
	}

	/**
	 * <p><code>auto</code> attribute description:</p>
	 *
	 * <p>When this flag is true, files are uploaded automatically as soon as they are selected. The default value is false.</p>
	 */
	public void setAuto(boolean auto) {
		getStateHelper().put(InputFilePropertyKeys.auto, auto);
	}

	/**
	 * <p><code>fileUploadListener</code> attribute description:</p>
	 *
	 * <p>A method that is executed when a file is uploaded. The method must be <code>public</code>, return <code>void</code>, and take a <code>FileUploadEvent</code> argument.</p>
	 */
	public javax.el.MethodExpression getFileUploadListener() {
		return (javax.el.MethodExpression) getStateHelper().eval(InputFilePropertyKeys.fileUploadListener, null);
	}

	/**
	 * <p><code>fileUploadListener</code> attribute description:</p>
	 *
	 * <p>A method that is executed when a file is uploaded. The method must be <code>public</code>, return <code>void</code>, and take a <code>FileUploadEvent</code> argument.</p>
	 */
	public void setFileUploadListener(javax.el.MethodExpression fileUploadListener) {
		getStateHelper().put(InputFilePropertyKeys.fileUploadListener, fileUploadListener);
	}

	public String getMultiple() {
		return (String) getStateHelper().eval(InputFilePropertyKeys.multiple, null);
	}

	/**
	 * <p><code>multiple</code> attribute description:</p>
	 *
	 * <p>HTML passthrough attribute specifying whether or not multiple files can be uploaded. Valid values are blank (to upload a single file) or "multiple" (to upload multiple files).</p>
	 */
	public void setMultiple(String multiple) {
		getStateHelper().put(InputFilePropertyKeys.multiple, multiple);
	}
}
//J+
