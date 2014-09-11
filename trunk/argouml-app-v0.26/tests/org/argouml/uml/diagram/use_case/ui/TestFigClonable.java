// $Id: TestFigClonable.java 13911 2007-12-13 00:09:36Z euluis $
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

package org.argouml.uml.diagram.use_case.ui;

import junit.framework.TestCase;
import org.argouml.model.InitializeModel;
import org.argouml.profile.init.InitProfileSubsystem;

/**
 * Test if a Fig is cloneable.
 */
public class TestFigClonable extends TestCase {

    /**
     * The constructor.
     *
     * @param name the test name
     */
    public TestFigClonable(String name) {
	super(name);
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() throws Exception {
	super.setUp();
        InitializeModel.initializeDefault();
        new InitProfileSubsystem().init();
    }

    /**
     * Try to clone {@link FigUseCase}.
     */
    public void testUseCaseClonable() {
	FigUseCase usecase = new FigUseCase();
	FigUseCase usecaseclone;

	usecaseclone = (FigUseCase) usecase.clone();
    }

    /**
     * Try to clone {@link FigActor}.
     */
    public void testActorClonable() {
	FigActor actor = new FigActor();
	FigActor actorclone;

	actorclone = (FigActor) actor.clone();
    }
}