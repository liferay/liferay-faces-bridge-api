/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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
package javax.portlet.faces.preference;

import java.util.List;

import javax.portlet.ReadOnlyException;

import org.osgi.annotation.versioning.ProviderType;


/**
 * The <code>Preference</code> interface allows one to access each value from {@link javax.portlet.PortletPreferences}
 * as a discrete object. This allows one to more easily access a preference via EL. Operations made on a <code>
 * Preference</code> object are immediately reflected in the underlying {@link javax.portlet.PortletPreferences}.
 * Changes are not committed until {@link javax.portlet.PortletPreferences#store()} is called.
 *
 * @author  Michael Freedman
 * @author  Neil Griffin
 */
@ProviderType
public interface Preference {

	/**
	 * Returns the name of this preference.
	 *
	 * @return  the name of this preference.
	 */
	public String getName();

	/**
	 * Returns the first String value associated with this preference. If there is one or more values associated with
	 * this preference it returns the first associated value. If there are no values associated with this preference, or
	 * the backing preference database is unavailable, it returns <code>nulll</code>.
	 *
	 * @return  the first value associated with this preference, or <code>null</code> if there isn't an associated value
	 *          or the backing store is inaccessible.
	 *
	 * @see     #getValues()
	 */
	public String getValue();

	/**
	 * Returns a {@link List} of values associated with this preference.
	 *
	 * <p>Returns <code>null</code> if there aren't any values, or if the backing store is inaccessible.</p>
	 *
	 * <p>If the implementation supports <i>stored defaults</i> and such a default exists and is accessible, they are
	 * returned in a situation where null otherwise would have been returned.</p>
	 *
	 * @return  the list of values associated with this preference, or <code>null</code> if the associated value does
	 *          not exist.
	 *
	 * @see     #getValue()
	 */
	public List<String> getValues();

	/**
	 * <p>Returns true, if the value of this preference cannot be modified by the user.</p>
	 *
	 * <p>Modifiable preferences can be changed by the portlet in any standard portlet mode (<code>EDIT, HELP,
	 * VIEW</code>). Per default every preference is modifiable.</p>
	 *
	 * <p>Read-only preferences cannot be changed by the portlet in any standard portlet mode, but inside of custom
	 * modes it may be allowed changing them. Preferences are read-only, if they are defined in the deployment
	 * descriptor with <code>read-only</code> set to <code>true</code>, or if the portlet container restricts write
	 * access.</p>
	 *
	 * @return  <code>true</code> if the value of this preference cannot be modified by the user. Otherwise <code>
	 *          false</code>.
	 */
	public boolean isReadOnly();

	/**
	 * <p>Resets or removes the value(s) of this preference.</p>
	 *
	 * <p>If this implementation supports stored defaults, and there is such a default for the specified preference, the
	 * preference will be reset to the stored default.</p>
	 *
	 * <p>If there is no default available the preference will be removed from the underlying system.</p>
	 *
	 * @throws  ReadOnlyException  - if this preference cannot be modified for this request.
	 */
	public void reset() throws ReadOnlyException;

	/**
	 * Sets the name of this preference.
	 *
	 * @param  name  the new name for this preference.
	 */
	public void setName(String name);

	/**
	 * <p>Associates the specified String value with this preference.</p>
	 *
	 * <p><code>null</code> values for the value parameter are permitted.</p>
	 *
	 * @param   value  The value that is to be associated with the preference name.
	 *
	 * @throws  ReadOnlyException  - if this preference cannot be modified for this request.
	 *
	 * @see     #setValues(String[])
	 */
	public void setValue(String value) throws ReadOnlyException;

	/**
	 * <p>Associates the specified String array value with this preference.</p>
	 *
	 * <p><code>null</code> values in the values parameter are permitted.</p>
	 *
	 * @param   values  The values that are to be associated with the preference name.
	 *
	 * @throws  ReadOnlyException  - if this preference cannot be modified for this request.
	 *
	 * @see     #setValue(String)
	 */
	public void setValues(String[] values) throws ReadOnlyException;
}
