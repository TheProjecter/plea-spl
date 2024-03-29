// $Id: ActionVisibilityPublic.java 10733 2006-06-11 14:56:02Z mvw $
// Copyright (c) 2005-2006 The Regents of the University of California. All
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

package org.argouml.uml.diagram.ui;

import org.argouml.model.Model;

class ActionVisibilityPublic extends AbstractActionRadioMenuItem {
    /**
     * Serial version generated for rev 1.5
     */
    private static final long serialVersionUID = -4288749276325868991L;

    /**
     * The constructor.
     *
     * @param o the target
     */
    public ActionVisibilityPublic(Object o) {
        super("checkbox.visibility.public-uc", false);
        putValue("SELECTED", Boolean.valueOf(
            Model.getVisibilityKind().getPublic()
                .equals(valueOfTarget(o))));
    }

    /**
     * @see org.argouml.uml.diagram.ui.AbstractActionRadioMenuItem#toggleValueOfTarget(java.lang.Object)
     */
    void toggleValueOfTarget(Object t) {
        Model.getCoreHelper().setVisibility(t,
            Model.getVisibilityKind().getPublic());
    }

    /**
     * Make use of the default visibility, which is public...
     * TODO: centralise this knowledge.
     *
     * @see org.argouml.uml.diagram.ui.AbstractActionRadioMenuItem#valueOfTarget(java.lang.Object)
     */
    Object valueOfTarget(Object t) {
        Object v = Model.getFacade().getVisibility(t);
        return v == null ? Model.getVisibilityKind().getPublic() : v;
    }
}
