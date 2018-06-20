/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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
package javax.portlet.faces;

import org.osgi.annotation.versioning.ConsumerType;


/**
 * Thrown when the one of the bridge's <code>doFacesRequest</code> methods is called and the bridge is in an
 * uninitialized state.
 *
 * @author  Michael Freedman
 * @author  Neil Griffin
 */
@ConsumerType
public class BridgeUninitializedException extends BridgeException {

	private static final long serialVersionUID = 5011392390692503483L;

	public BridgeUninitializedException() {
		super();
	}

	public BridgeUninitializedException(String message) {
		super(message);
	}

	public BridgeUninitializedException(Exception e) {
		super(e);
	}

	public BridgeUninitializedException(Throwable cause) {
		super(cause);
	}

	public BridgeUninitializedException(String message, Throwable cause) {
		super(message, cause);
	}
}
