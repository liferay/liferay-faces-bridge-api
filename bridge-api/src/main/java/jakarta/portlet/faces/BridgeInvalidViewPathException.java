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
 * Thrown when the bridge can't resolve the target view from the ViewPath portlet request attribute {@link
 * Bridge#FACES_VIEW_PATH_PARAMETER}.
 *
 * @author  Michael Freedman
 * @author  Neil Griffin
 */
@ConsumerType
public class BridgeInvalidViewPathException extends BridgeException {

	private static final long serialVersionUID = 8192388822641501588L;

	public BridgeInvalidViewPathException() {
		super();
	}

	public BridgeInvalidViewPathException(String message) {
		super(message);
	}

	public BridgeInvalidViewPathException(Exception e) {
		super(e);
	}

	public BridgeInvalidViewPathException(Throwable cause) {
		super(cause);
	}

	public BridgeInvalidViewPathException(String message, Throwable cause) {
		super(message, cause);
	}
}
