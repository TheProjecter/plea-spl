// $Id: ActionAddEventAsDeferrableEvent.java 11516 2006-11-25 04:30:15Z tfmorris $
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

package org.argouml.uml.ui.behavior.state_machines;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.argouml.i18n.Translator;
import org.argouml.model.Model;
import org.argouml.uml.ui.AbstractActionAddModelElement;

/**
 * Provide a dialog which helps the user to select events
 * out of an existing list,
 * which will be used as the deferrable events of the state.
 *
 * @author MarkusK
 */
public class ActionAddEventAsDeferrableEvent
    extends AbstractActionAddModelElement {

    /**
     * The one and only instance of this class.
     */
    public static final ActionAddEventAsDeferrableEvent SINGLETON =
        new ActionAddEventAsDeferrableEvent();

    /**
     * Constructor for ActionAddClassifierRoleBase.
     */
    protected ActionAddEventAsDeferrableEvent() {
        super();
        setMultiSelect(true);
    }

    /*
     * @see org.argouml.uml.ui.AbstractActionAddModelElement#getChoices()
     */
    protected Vector getChoices() {
        Vector vec = new Vector();
        // TODO: the namespace of created events is currently the model.
        // I think this is wrong, they should be
        // in the namespace of the activitygraph!
//        vec.addAll(
//                Model.getModelManagementHelper().getAllModelElementsOfKind(
//                        Model.getFacade().getNamespace(getTarget()),
//                        Model.getMetaTypes().getEvent()));
        vec.addAll(Model.getModelManagementHelper().getAllModelElementsOfKind(
                Model.getFacade().getModel(getTarget()),
                Model.getMetaTypes().getEvent()));

        return vec;
    }

    /*
     * @see org.argouml.uml.ui.AbstractActionAddModelElement#getSelected()
     */
    protected Vector getSelected() {
        Vector vec = new Vector();
        Collection events = Model.getFacade().getDeferrableEvents(getTarget());
        if (events != null) {
            vec.addAll(events);
        }
        return vec;
    }

    /*
     * @see org.argouml.uml.ui.AbstractActionAddModelElement#getDialogTitle()
     */
    protected String getDialogTitle() {
        return Translator.localize("dialog.title.add-events");
    }

    /*
     * @see org.argouml.uml.ui.AbstractActionAddModelElement#doIt(
     *         java.util.Vector)
     */
    protected void doIt(Vector selected) {
        Object state = getTarget();
        if (!Model.getFacade().isAState(state)) return;
        Collection oldOnes = new Vector(Model.getFacade()
                .getDeferrableEvents(state));
        Collection toBeRemoved = new Vector(oldOnes);
        Iterator i = selected.iterator();
        while (i.hasNext()) {
            Object o = i.next();
            if (oldOnes.contains(o)) {
                toBeRemoved.remove(o);
            } 
            //#if defined(STATEDIAGRAM) or defined(ACTIVITYDIAGRAM)
            //@#$LPS-STATEDIAGRAM:GranularityType:Statement
            //@#$LPS-ACTIVITYDIAGRAM:GranularityType:Statement    
            //@#$LPS-ACTIVITYDIAGRAM:Localization:NestedStatement
            //@#$LPS-STATEDIAGRAM:Localization:NestedStatement
            else {
                Model.getStateMachinesHelper().addDeferrableEvent(state, o);
            }
            //#endif
        }
        //#if defined(STATEDIAGRAM) or defined(ACTIVITYDIAGRAM)
        //@#$LPS-STATEDIAGRAM:GranularityType:Statement
        //@#$LPS-ACTIVITYDIAGRAM:GranularityType:Statement    
        //@#$LPS-STATEDIAGRAM:Localization:EndMethod
        //@#$LPS-ACTIVITYDIAGRAM:Localization:EndMethod
        i = toBeRemoved.iterator();
        while (i.hasNext()) {
            Object o = i.next();
            Model.getStateMachinesHelper().removeDeferrableEvent(state, o);
        }
        //#endif
    }

    /**
     * The UID.
     */
    private static final long serialVersionUID = 1815648968597093974L;
}
