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
package jakarta.portlet.faces.event;

import jakarta.faces.context.FacesContext;
import jakarta.portlet.Event;

import org.osgi.annotation.versioning.ProviderType;


/**
 * An <code>EventNavigationResult</code> is the type of object that can be returned from a {@link
 * jakarta.portlet.faces.BridgeEventHandler#handleEvent(FacesContext, Event)} call. When it is returned as a (non-null)
 * value, it conveys the Faces navigation information to the bridge that it needs to utilize the Faces {@link
 * jakarta.faces.application.NavigationHandler} to evaluate the navigation according to the configured rules. The <code>
 * fromAction</code> corresponds to the <code>fromAction</code> string in the faces-config.xml navigation rule. The
 * <code>outcome</code> corresponds to the <code>outcome</code> string in the navigation rule.
 *
 * @author  Michael Freedman
 * @author  Neil Griffin
 */
@ProviderType
public class EventNavigationResult {

	private String fromAction;
	private String outcome;

	public EventNavigationResult() {
	}

	/**
	 * Constructor which sets the object to the desired fromAction and outcome.
	 *
	 * @param  fromAction  Value corresponding to the &lt;from-action&gt; element of a navigation-rule.
	 * @param  outcome     Value corresponding to the &lt;outcome&gt; element of a navigation-rule.
	 */
	public EventNavigationResult(String fromAction, String outcome) {
		this.fromAction = fromAction;
		this.outcome = outcome;
	}

	/**
	 * @return  Value corresponding to the &lt;from-action&gt; element of a navigation-rule.
	 */
	public String getFromAction() {
		return fromAction;
	}

	/**
	 * @return  Value corresponding to the &lt;outcome&gt; element of a navigation-rule.
	 */
	public String getOutcome() {
		return outcome;
	}

	/**
	 * Sets the value that corresponds to the &lt;from-action&gt; element of a navigation-rule.
	 *
	 * @param  fromAction  The value that corresponds to the &lt;from-action&gt; element of a navigation-rule.
	 */
	public void setFromAction(String fromAction) {
		this.fromAction = fromAction;
	}

	/**
	 * Sets the value that corresponds to the &lt;outcome&gt; element of a navigation-rule.
	 *
	 * @param  outcome  The value that corresponds to the &lt;from-action&gt; element of a navigation-rule.
	 */
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
}
