/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
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

import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import com.liferay.faces.bridge.event.FileUploadEvent;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = InputFile.COMPONENT_TYPE)
public class InputFile extends InputFileBase {

	@Override
	public void broadcast(FacesEvent facesEvent) throws AbortProcessingException {
		super.broadcast(facesEvent);

		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {
			MethodExpression methodExpression = getFileUploadListener();

			if ((methodExpression != null) && (facesEvent instanceof FileUploadEvent)) {
				methodExpression.invoke(facesContext.getELContext(), new Object[] { facesEvent });
			}
		}
		catch (Exception e) {
			throw new AbortProcessingException(e);
		}
	}

	@Override
	public String getStyleClass() {

		// getStateHelper().eval(PropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(PropertyKeys.styleClass, null);

		return concatCssClasses(styleClass, "bridge-input-file");
	}

	private String concatCssClasses(String... classNames) {

		StringBuilder cssClassBuilder = new StringBuilder();
		boolean first = true;

		for (String className : classNames) {

			if (className != null) {

				if (!first) {
					cssClassBuilder.append(" ");
				}

				cssClassBuilder.append(className);
				first = false;
			}
		}

		String allClasses = cssClassBuilder.toString();

		if (allClasses.length() == 0) {
			allClasses = null;
		}

		return allClasses;
	}
}
