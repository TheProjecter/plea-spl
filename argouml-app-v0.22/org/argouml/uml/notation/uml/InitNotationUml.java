// $Id: InitNotationUml.java 10543 2006-05-06 19:28:11Z mvw $
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

package org.argouml.uml.notation.uml;

import org.argouml.application.api.Argo;
import org.argouml.notation.Notation;
import org.argouml.notation.NotationName;
import org.argouml.notation.NotationProviderFactory2;

/**
 * This class initialises the Notation subsystem.
 * 
 * This class is the only one that has the knowledge of the complete list of
 * NotationProvider4 implementations for UML. <p>
 * 
 * @author mvw@tigris.org
 */
public class InitNotationUml {

    /**
     * static initializer, register all appropriate notations.
     */
    public static void init() {
        NotationProviderFactory2 npf = NotationProviderFactory2.getInstance();
        NotationName name =
            Notation.makeNotation(
                    "UML",
                    "1.4",
                    Argo.lookupIconResource("UmlNotation"));

        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_NAME,
                name, ModelElementNameNotationUml.class);
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_TRANSITION,
                name, TransitionNotationUml.class);
        //#if defined(STATEDIAGRAM) or defined(ACTIVITYDIAGRAM)
        //@#$LPS-STATEDIAGRAM:GranularityType:Statement
        //@#$LPS-ACTIVITYDIAGRAM:GranularityType:Statement
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_STATEBODY,
                name, StateBodyNotationUml.class);
      //#endif        
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_ACTIONSTATE,
                name, ActionStateNotationUml.class);
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_OBJECT,
                name, ObjectNotationUml.class);
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_COMPONENTINSTANCE,
                name, ComponentInstanceNotationUml.class);
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_NODEINSTANCE,
                name, NodeInstanceNotationUml.class);
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_OBJECTFLOWSTATE_TYPE,
                name, ObjectFlowStateTypeNotationUml.class);
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_OBJECTFLOWSTATE_STATE,
                name, ObjectFlowStateStateNotationUml.class);
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_CALLSTATE,
                name, CallStateNotationUml.class);
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_CLASSIFIERROLE,
                name, ClassifierRoleNotationUml.class);
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_MESSAGE,
                name, MessageNotationUml.class);
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_ATTRIBUTE,
                name, AttributeNotationUml.class);
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_OPERATION,
                name, OperationNotationUml.class);
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_EXTENSION_POINT,
                name, ExtensionPointNotationUml.class);
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_ASSOCIATION_END_NAME,
                name, AssociationEndNameNotationUml.class);
        npf.addNotationProvider(
                NotationProviderFactory2.TYPE_ASSOCIATION_ROLE,
                name, AssociationRoleNotationUml.class);


        NotationProviderFactory2.getInstance().setDefaultNotation(name);

        /* Initialise the NotationUtilityUml: */
        (new NotationUtilityUml()).init();
    }

}
