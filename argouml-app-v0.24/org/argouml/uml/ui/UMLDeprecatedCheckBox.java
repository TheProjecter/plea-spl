// $Id: UMLDeprecatedCheckBox.java 11416 2006-11-07 05:03:09Z tfmorris $
// Copyright (c) 2003-2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
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

package org.argouml.uml.ui;

import org.argouml.model.Model;

/**
 * Class to represent a checkbox for the deprecated checkbox in the
 * documentation tab.
 *
 * TODO: This needs to be reworked to use UML 1.4 TagDefinitions
 * instead of the UML 1.3 String typed tag type. - tfm
 * 
 * @author mkl
 *
 */
public class UMLDeprecatedCheckBox extends UMLCheckBox2 {

    /**
     * The constructor.
     *
     */
    public UMLDeprecatedCheckBox() {
        super(null, new ActionBooleanTaggedValue("deprecated"), "deprecated");
    }

    /**
     * Set the checkbox according to the tagged values of the target.
     *
     * @see org.argouml.uml.ui.UMLCheckBox2#buildModel()
     */
    public void buildModel() {

        String tagName = "deprecated";
        setSelected(false);

        Object tv = Model.getFacade().getTaggedValue(getTarget(), tagName);
        if (tv != null) {
            String tag = Model.getFacade().getValueOfTag(tv);
            if ("true".equals(tag)) {
                setSelected(true);
            }
        }
    }
}
