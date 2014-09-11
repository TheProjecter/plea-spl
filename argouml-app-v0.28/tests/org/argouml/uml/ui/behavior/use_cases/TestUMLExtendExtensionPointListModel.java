// $Id: TestUMLExtendExtensionPointListModel.java 43 2010-04-03 20:28:12Z marcusvnac $
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

package org.argouml.uml.ui.behavior.use_cases;

import org.argouml.model.Model;
import org.argouml.uml.ui.AbstractUMLModelElementListModel2Test;

/**
 * @since Oct 29, 2002
 * @author jaap.branderhorst@xs4all.nl
 */
public class TestUMLExtendExtensionPointListModel
    extends AbstractUMLModelElementListModel2Test {

    /**
     * Constructor for TestUMLExtendExtensionPointListModel.
     * @param arg0 is the name of the test case.
     */
    public TestUMLExtendExtensionPointListModel(String arg0) {
        super(arg0);
    }

    /*
     * @see org.argouml.uml.ui.AbstractUMLModelElementListModel2Test#buildElement()
     */
    protected void buildElement() {
        setElem(Model.getUseCasesFactory().createExtend());
    }

    /*
     * @see org.argouml.uml.ui.AbstractUMLModelElementListModel2Test#buildModel()
     */
    protected void buildModel() {
        setModel(new UMLExtendExtensionPointListModel());
    }

    /*
     * @see org.argouml.uml.ui.AbstractUMLModelElementListModel2Test#fillModel()
     */
    protected Object[] fillModel() {
        Object[] points = new Object[10];
        for (int i = 0; i < 10; i++) {
            points[i] = Model.getUseCasesFactory().createExtensionPoint();
            Model.getUseCasesHelper().addExtensionPoint(getElem(), points[i]);
        }
        return points;
    }

    /*
     * @see org.argouml.uml.ui.AbstractUMLModelElementListModel2Test#removeHalfModel(Object[])
     */
    protected void removeHalfModel(Object[] elements) {
        for (int i = 0; i < 5; i++) {
            Model.getUseCasesHelper().removeExtensionPoint(
                    getElem(),
                    elements[i]);
        }
    }

}
