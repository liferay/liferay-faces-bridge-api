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
package javax.portlet.faces;

import javax.faces.FacesException;


/**
 * @author  Neil Griffin
 */
public class BridgeException extends FacesException {

	private static final long serialVersionUID = 5803855762197476832L;

	public BridgeException() {
		super();
	}

	public BridgeException(String message) {
		super(message);
	}

	public BridgeException(Exception e) {
		super(e);
	}

	public BridgeException(Throwable cause) {
		super(cause);
	}

	public BridgeException(String message, Throwable cause) {
		super(message, cause);
	}
}
