/**
 * Copyright (c) 2000-2025 Liferay, Inc. All rights reserved.
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
package jakarta.portlet.faces;

import org.osgi.annotation.versioning.ConsumerType;


/**
 * Thrown when the portlet invokes one of the bridge's <code>doFacesRequest</code> methods even though the {@link
 * Bridge#NONFACES_TARGET_PATH_PARAMETER} parameter is present, indicating it is a non-Faces target.
 *
 * @author  Michael Freedman
 * @author  Neil Griffin
 */
@ConsumerType
public class BridgeNotAFacesRequestException extends BridgeException {

	private static final long serialVersionUID = 5510695372019305574L;

	public BridgeNotAFacesRequestException() {
		super();
	}

	public BridgeNotAFacesRequestException(String message) {
		super(message);
	}

	public BridgeNotAFacesRequestException(Exception e) {
		super(e);
	}

	public BridgeNotAFacesRequestException(Throwable cause) {
		super(cause);
	}

	public BridgeNotAFacesRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
