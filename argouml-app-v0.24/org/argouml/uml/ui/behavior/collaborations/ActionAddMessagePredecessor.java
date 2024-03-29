// $Id: ActionAddMessagePredecessor.java 11516 2006-11-25 04:30:15Z tfmorris $
// Copyright (c) 1996-2006 The Regents of the University of California. All
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

// $header$
package org.argouml.uml.ui.behavior.collaborations;

import java.util.Vector;

import org.argouml.i18n.Translator;
import org.argouml.model.Model;
import org.argouml.uml.ui.AbstractActionAddModelElement;

/**
 * Action to add a predecessor to some message.
 * @since Oct 2, 2002
 * @author jaap.branderhorst@xs4all.nl
 * @stereotype singleton
 */
public class ActionAddMessagePredecessor extends AbstractActionAddModelElement {

    private static final ActionAddMessagePredecessor SINGLETON =
	new ActionAddMessagePredecessor();

    /**
     * Constructor for ActionAddMessagePredecessor.
     */
    protected ActionAddMessagePredecessor() {
        super();
    }

    /*
     * @see org.argouml.uml.ui.AbstractActionAddModelElement#getChoices()
     */
    protected Vector getChoices() {
        if (getTarget() == null) return new Vector();
        Vector vec = new Vector();
        //#if defined(COLLABORATIONDIAGRAM) or defined(SEQUENCEDIAGRAM)
        //@#$LPS-COLLABORATIONDIAGRAM:GranularityType:Statement
        //@#$LPS-SEQUENCEDIAGRAM:GranularityType:Statement
        //@#$LPS-COLLABORATIONDIAGRAM:Localization:BeforeReturn
        //@#$LPS-SEQUENCEDIAGRAM:Localization:BeforeReturn
        vec.addAll(Model.getCollaborationsHelper()
                .getAllPossiblePredecessors(getTarget()));
        //#endif
        return vec;
    }

    /*
     * @see org.argouml.uml.ui.AbstractActionAddModelElement#getSelected()
     */
    protected Vector getSelected() {
        if (getTarget() == null)
	    throw new IllegalStateException(
                "getSelected may not be called with null target");
        Vector vec = new Vector();
        vec.addAll(Model.getFacade().getPredecessors(getTarget()));
        return vec;
    }

    /*
     * @see org.argouml.uml.ui.AbstractActionAddModelElement#getDialogTitle()
     */
    protected String getDialogTitle() {
        return Translator.localize("dialog.add-predecessors");
    }

    /*
     * @see org.argouml.uml.ui.AbstractActionAddModelElement#doIt(java.util.Vector)
     */
    protected void doIt(Vector selected) {
	if (getTarget() == null)
	    throw new IllegalStateException(
                "doIt may not be called with null target");
        //#if defined(COLLABORATIONDIAGRAM) or defined(SEQUENCEDIAGRAM)
        //@#$LPS-COLLABORATIONDIAGRAM:GranularityType:Statement
        //@#$LPS-SEQUENCEDIAGRAM:GranularityType:Statement
        //@#$LPS-COLLABORATIONDIAGRAM:Localization:EndMethod
        //@#$LPS-SEQUENCEDIAGRAM:Localization:EndMethod   	
	Object message = getTarget();
	Model.getCollaborationsHelper().setPredecessors(message, selected);
	//#endif
    }

    /**
     * @return Returns the SINGLETON.
     */
    public static ActionAddMessagePredecessor getInstance() {
        return SINGLETON;
    }

}
