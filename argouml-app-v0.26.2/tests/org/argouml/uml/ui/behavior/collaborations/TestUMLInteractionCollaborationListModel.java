// $Id: TestUMLInteractionCollaborationListModel.java 12483 2007-05-02 20:20:37Z linus $
// Copyright (c) 1996-2007 The Regents of the University of California. All
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

package org.argouml.uml.ui.behavior.collaborations;

import junit.framework.TestCase;
import org.argouml.model.InitializeModel;

import org.argouml.model.Model;

/**
 * @since Oct 30, 2002
 * @author jaap.branderhorst@xs4all.nl
 */
public class TestUMLInteractionCollaborationListModel extends TestCase {

    private Object elem;
    private UMLInteractionContextListModel model;

    /**
     * Constructor for TestUMLInteractionCollaborationListModel.
     *
     * @param arg0 is the name of the test case.
     */
    public TestUMLInteractionCollaborationListModel(String arg0) {
        super(arg0);
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        InitializeModel.initializeDefault();

        elem = Model.getCollaborationsFactory().createInteraction();
        model = new UMLInteractionContextListModel();
        model.setTarget(elem);
        Model.getPump().flushModelEvents();
    }

    /*
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        Model.getUmlFactory().delete(elem);
        model = null;
    }

    /**
     * Test setContext().
     */
    public void testSetContext() {
        Object col =
	    Model.getCollaborationsFactory().createCollaboration();
        Model.getCollaborationsHelper().setContext(elem, col);
        Model.getPump().flushModelEvents();
        assertEquals(1, model.getSize());
        assertEquals(col, model.getElementAt(0));
    }

    /**
     * Test setContext(null).
     */
    public void testRemoveContext() {
        Object col =
	    Model.getCollaborationsFactory().createCollaboration();
        Model.getCollaborationsHelper().setContext(elem, col);
        Model.getCollaborationsHelper().setContext(elem, null);
        Model.getPump().flushModelEvents();
        assertEquals(0, model.getSize());
        assertTrue(model.isEmpty());
    }

}
