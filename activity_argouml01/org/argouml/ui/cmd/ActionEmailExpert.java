// $Id: ActionEmailExpert.java 10733 2006-06-11 14:56:02Z mvw $
// Copyright (c) 2004-2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.ui.cmd;

import java.awt.event.ActionEvent;

//#endif
import org.argouml.ui.EmailExpertDialog;
import org.argouml.util.osdep.OsUtil;



/**
 * The action to send an email to an expert.
 *
 */
public class ActionEmailExpert extends ToDoItemAction {

    /**
     * The constructor.
     */
    public ActionEmailExpert() {
        super("action.send-email-to-expert", true);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae) {
    	super.actionPerformed(ae);
    	
    	//#if defined(COGNITIVE)
        if (OsUtil.isWin32()) {
		} else {        	
        //#endif
            EmailExpertDialog dialog = new EmailExpertDialog();
            //#endif
            dialog.setVisible(true);
        //#if defined(COGNITIVE)
        }
        //#endif
    }

    /**
     * @see org.argouml.ui.cmd.ToDoItemAction#isEnabled(java.lang.Object)
     */
    public boolean isEnabled(Object target) {
        return getRememberedTarget() != null
        	
            ;
    }

} /* end class ActionEmailExpert */

