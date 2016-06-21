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
package javax.portlet.faces;

import java.io.IOException;


/**
 * <p>The <code>BridgeWriteBehindResponse</code> interface defines the API that the bridge relies on to acquire the
 * buffered JSP output from the response(Wrapper) used to handle the Faces implementation dependent writeBehindResponse
 * methodology/interface.</p>
 *
 * <p>Note: the Portlet 1.0 Bridge relied on Portlet 1.0 which didn't support response wrappers. In that version the
 * writeBehindResponse behavior is provided in a Servlet ResponseWrapper inserted in a Servlet filter set up to be
 * called on included JSPs. In Portlet 2.0 Bridge this behavior can now be implemented directly in a Portlet
 * ResponseWrapper which can then be registered for use with the bridge. So that the bridge recognizes and use this
 * support, such wrappers must implement this interface.</p>
 *
 * <p>Implementations must be one of the Portlet 2.0 <code>ResponseWrappers</code> and have a null constructor that
 * utilizes <code>FacesContext.getCurrentInstance().getExternalContext().getResponse()</code> to acquire the response to
 * be wrapped.</p>
 *
 * @author  Michael Freedman
 * @author  Neil Griffin
 */
public interface BridgeWriteBehindResponse {

	/**
	 * Called by the bridge after dispatching to flush the current buffered content to the wrapped response (this could
	 * be a Servlet or Portlet response). This is done in a situation where we aren't supporting writeBehind behavior.
	 * We stil use a wrapped/buffered response because we use dispatch.forward which in many environments closes the
	 * writer at the end of the forward. If not wrapped, not further writing to the output would be feasible.
	 *
	 * @throws  IOException  - if content cannot be written.
	 */
	void flushMarkupToWrappedResponse() throws IOException;

	/**
	 * Called by the bridge after dispatching is complete to acquire the {@link Bridge#AFTER_VIEW_CONTENT} when the
	 * response has been written as bytes. The bridge writes this buffer to the (real) response.
	 *
	 * @return  the response content as a byte array.
	 */
	byte[] getBytes();

	/**
	 * Called by the bridge after dispatching is complete to acquire the {@link Bridge#AFTER_VIEW_CONTENT} when the
	 * response has been written as characters. The bridge writes this buffer to the (real) response.
	 *
	 * @return  the response content as a character array.
	 */
	char[] getChars();

	/**
	 * Called by the bridge to detect whether this response actively participated in the Faces writeBehind support and
	 * hence has data that should be written after the View is rendered.
	 *
	 * @return  <code>true</code> if the response actually particpated in the writeBehind mechanism. Otherwise <code>
	 *          false</code>.
	 */
	boolean hasFacesWriteBehindMarkup();

	/**
	 * Called by the bridge after dispatching is complete to determine whether the JSP AfterViewContent was written as
	 * bytes (written via an {@link java.io.OutputStream}).
	 *
	 * @return  <code>true</code> if the response (buffer) is represented as bytes written via the <code>
	 *          OutputStream</code>. Otherwise <code>false</code>.
	 */
	boolean isBytes();

	/**
	 * Called by the bridge after dispatching is complete to determine whether the JSP {@link Bridge#AFTER_VIEW_CONTENT}
	 * was written as chars (written via a {@link java.io.PrintWriter}).
	 *
	 * @return  <code>true</code> if the response (buffer) is represented as chars written via the {@link
	 *          java.io.PrintWriter}. Otherwise <code>false</code>.
	 */
	boolean isChars();
}
