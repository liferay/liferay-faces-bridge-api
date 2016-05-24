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
package javax.portlet.faces.event;

/**
 * @author  Neil Griffin
 */
public class EventNavigationResult {

	private String fromAction;
	private String outcome;

	public EventNavigationResult() {
	}

	public EventNavigationResult(String fromAction, String outcome) {
		this.fromAction = fromAction;
		this.outcome = outcome;
	}

	public String getFromAction() {
		return fromAction;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setFromAction(String fromAction) {
		this.fromAction = fromAction;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
}
