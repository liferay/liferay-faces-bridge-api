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
package javax.portlet.faces.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * The <code>BridgePreDestroy</code> annotation is used on methods as a callback notification to signal that the
 * instance is in the process of being removed by the bridge from the bridge request scope. This method complements one
 * using {@link javax.annotation.PreDestroy} to allow the object to release resources that it has been holding. It
 * exists because the existing pre-destroy method must be ignored as it is called by the container even though the
 * bridge continues to manage the object in its request scope. The method on which this annotation is applied MUST
 * fulfill all of the following criteria:
 *
 * <ul>
 *   <li>The method MUST NOT have any parameters</li>
 *   <li>The return type of the method MUST be void.</li>
 *   <li>The method MUST NOT throw a checked exception.</li>
 *   <li>The method on which it is applied MUST be public.</li>
 *   <li>The method MUST NOT be static.</li>
 *   <li>The method MAY be final.</li>
 *   <li>If the method throws an unchecked exception it is ignored.</li>
 * </ul>
 *
 * @author  Michael Freedman
 * @author  Neil Griffin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface BridgePreDestroy {
}
