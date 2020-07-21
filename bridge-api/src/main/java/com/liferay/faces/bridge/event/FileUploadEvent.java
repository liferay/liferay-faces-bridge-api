/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.faces.bridge.model.UploadedFile;


/**
 * @author  Neil Griffin
 */
@ProviderType
public class FileUploadEvent extends FacesEvent {

	// serialVersionUID
	private static final long serialVersionUID = 1950657796301668919L;

	// Private Data Members
	private UploadedFile uploadedFile;

	public FileUploadEvent(UIComponent uiComponent, UploadedFile uploadedFile) {
		super(uiComponent);
		this.uploadedFile = uploadedFile;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	@Override
	public boolean isAppropriateListener(FacesListener facesListener) {
		return false;
	}

	@Override
	public void processListener(FacesListener facesListener) {
		throw new UnsupportedOperationException();
	}

}
