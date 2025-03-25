/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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
package jakarta.portlet.faces.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.osgi.annotation.versioning.ConsumerType;


/**
 * The <code>ExcludeFromManagedRequestScope</code> annotation is used on a class as a signal that instances of this
 * class are not to be managed by the bridge in the bridge request scope if/when the instance is added to the portlet
 * container's request scope. This annotation is the preferred method for marking a class for such exclusion and should
 * be used where ever possible over the secondary configuration techniques additional supported.
 *
 * @author  Michael Freedman
 * @author  Neil Griffin
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@ConsumerType
public @interface ExcludeFromManagedRequestScope {
}
