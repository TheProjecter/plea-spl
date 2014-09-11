// $Id: DiagramFactory.java 14995 2008-06-17 21:22:38Z bobtarling $
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

package org.argouml.uml.diagram;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//#if defined(ACTIVITYDIAGRAM)
//@#$LPS-ACTIVITYDIAGRAM:GranularityType:Import
import org.argouml.model.ActivityDiagram;
//#endif
import org.argouml.model.ClassDiagram;
//#if defined(COLLABORATIONDIAGRAM)
//@#$LPS-COLLABORATIONDIAGRAM:GranularityType:Import
import org.argouml.model.CollaborationDiagram;
//#endif
import org.argouml.model.DeploymentDiagram;
import org.argouml.model.DiDiagram;
import org.argouml.model.Model;
//#if defined(SEQUENCEDIAGRAM)
//@#$LPS-SEQUENCEDIAGRAM:GranularityType:Import
import org.argouml.model.SequenceDiagram;
//#endif
//#if defined(STATEDIAGRAM)
//@#$LPS-STATEDIAGRAM:GranularityType:Import
import org.argouml.model.StateDiagram;
//#endif
//#if defined(USECASEDIAGRAM)
//@#$LPS-USECASEDIAGRAM:GranularityType:Import
import org.argouml.model.UseCaseDiagram;
//#endif
//#if defined(ACTIVITYDIAGRAM)
//@#$LPS-ACTIVITYDIAGRAM:GranularityType:Import
import org.argouml.uml.diagram.activity.ui.UMLActivityDiagram;
//#endif
//#if defined(COLLABORATIONDIAGRAM)
//@#$LPS-COLLABORATIONDIAGRAM:GranularityType:Import
import org.argouml.uml.diagram.collaboration.ui.UMLCollaborationDiagram;
//#endif
//#if defined(DEPLOYMENTDIAGRAM)
//@#$LPS-DEPLOYMENTDIAGRAM:GranularityType:Import
import org.argouml.uml.diagram.deployment.ui.UMLDeploymentDiagram;
//#endif
//#if defined(SEQUENCEDIAGRAM)
//@#$LPS-SEQUENCEDIAGRAM:GranularityType:Import
import org.argouml.uml.diagram.sequence.ui.UMLSequenceDiagram;
//#endif
//#if defined(STATEDIAGRAM)
//@#$LPS-STATEDIAGRAM:GranularityType:Import
import org.argouml.uml.diagram.state.ui.UMLStateDiagram;
//#endif
import org.argouml.uml.diagram.static_structure.ui.UMLClassDiagram;
//#if defined(USECASEDIAGRAM)
//@#$LPS-USECASEDIAGRAM:GranularityType:Import
import org.argouml.uml.diagram.use_case.ui.UMLUseCaseDiagram;
//#endif
import org.tigris.gef.base.Diagram;
import org.tigris.gef.graph.GraphNodeRenderer;

/**
* Provide a factory method to create different UML diagrams.
* 
* @author Bob Tarling
*/
public final class DiagramFactory {

    private final Map noStyleProperties = new HashMap();

    /**
     * Map from our public enum to our internal implementation classes.
     * This allows use to hide the implementation classes from users of
     * the factory.
     * NOTE: This needs to be initialized before the constructor is called
     * to initialize the singleton.
     */
    private static Map<DiagramType, Class> diagramClasses = 
        new EnumMap<DiagramType, Class>(DiagramType.class);
    
    /**
     * The singleton instance.
     */
    private static DiagramFactory diagramFactory = new DiagramFactory();

    /**
     * Enumeration containing all the different types of UML diagrams.
     */
    public enum DiagramType {
        Class, 
        //#if defined(USECASEDIAGRAM)
        //@#$LPS-USECASEDIAGRAM:GranularityType:Enumeration
        UseCase,
        //#endif
        //#if defined(STATEDIAGRAM)
        //@#$LPS-STATEDIAGRAM:GranularityType:Enumeration
        State, 
        //#endif
        //#if defined(DEPLOYMENTDIAGRAM)
        //@#$LPS-DEPLOYMENTDIAGRAM:GranularityType:Enumeration
        Deployment,
        //#endif
        //#if defined(COLLABORATIONDIAGRAM)
        //@#$LPS-COLLABORATIONDIAGRAM:GranularityType:Enumeration
        Collaboration,
        //#endif
        //#if defined(ACTIVITYDIAGRAM)
        //@#$LPS-ACTIVITYDIAGRAM:GranularityType:Enumeration
        Activity, 
        //#endif
        //#if defined(SEQUENCEDIAGRAM)
        //@#$LPS-SEQUENCEDIAGRAM:GranularityType:Enumeration
        Sequence
        //#endif
    }
   
    private List<ArgoDiagram> diagrams = new ArrayList<ArgoDiagram>();

    private Map<DiagramType, DiagramFactoryInterface> factories =
        new EnumMap<DiagramType, DiagramFactoryInterface>(DiagramType.class);

    private DiagramFactory() {
        super();
        diagramClasses.put(DiagramType.Class, UMLClassDiagram.class);
        //#if defined(USECASEDIAGRAM)
        //@#$LPS-USECASEDIAGRAM:GranularityType:Statement
        diagramClasses.put(DiagramType.UseCase, UMLUseCaseDiagram.class);
        //#endif
        //#if defined(STATEDIAGRAM)
        //@#$LPS-STATEDIAGRAM:GranularityType:Statement
        diagramClasses.put(DiagramType.State, UMLStateDiagram.class);
        //#endif
        //#if defined(DEPLOYMENTDIAGRAM)
        //@#$LPS-DEPLOYMENTDIAGRAM:GranularityType:Statement
        diagramClasses.put(DiagramType.Deployment, UMLDeploymentDiagram.class);
        //#endif
        //#if defined(COLLABORATIONDIAGRAM)
        //@#$LPS-COLLABORATIONDIAGRAM:GranularityType:Statement
        diagramClasses.put(DiagramType.Collaboration, UMLCollaborationDiagram.class);
        //#endif
        //#if defined(ACTIVITYDIAGRAM)
        //@#$LPS-ACTIVITYDIAGRAM:GranularityType:Statement
        diagramClasses.put(DiagramType.Activity, UMLActivityDiagram.class);
        //#endif
        //#if defined(SEQUENCEDIAGRAM)
        //@#$LPS-ACTIVITYDIAGRAM:GranularityType:Statement
        diagramClasses.put(DiagramType.Sequence, UMLSequenceDiagram.class);
        //#endif        
    }

    /**
     * @return the singleton
     */
    public static DiagramFactory getInstance() {
        return diagramFactory;
    }

    /**
     * @return the list of diagrams
     * @deprecated in 0.26 By Bob Tarling
     */
    public List<ArgoDiagram> getDiagram() {
        // TODO: This list is currently unused in ArgoUML.  Since it's session
        // wide, it's not clear what value it has since we'll usually want
        // diagrams per project. - tfm
        return diagrams;
    }

    
    /**
     * Factory method to create a new default instance of an ArgoDiagram.
     * @param namespace The namespace that (in)directly 
     *                        owns the elements on the diagram
     * @return the newly instantiated class diagram
     */
    public ArgoDiagram createDefaultDiagram(Object namespace) {
        return createDiagram(DiagramType.Class, namespace, null);
    }

    /**
     * Factory method to create a new instance of an ArgoDiagram.
     *
     * @param type The class of rendering diagram to create
     * @param namespace The namespace that (in)directly 
     *                        owns the elements on the diagram
     * @param machine The StateMachine for the diagram
     *                         (only: statemachine - activitygraph)
     * @return the newly instantiated class diagram
     */
    public ArgoDiagram createDiagram(
            final DiagramType type, 
            final Object namespace,
            final Object machine) {
        
        DiagramFactoryInterface factory = factories.get(type);
        if (factory != null) {
            final ArgoDiagram diagram =
                factory.createDiagram(namespace, machine);
            //keep a reference on it in the case where we must add all the
            //diagrams as project members (loading)
            diagrams.add(diagram);
            return diagram;
        } else {
            return createDiagram(diagramClasses.get(type), namespace, machine);
        }
    }
    
    /**
     * Factory method to create a new instance of an ArgoDiagram.
     *
     * @param type The class of rendering diagram to create
     * @param namespace The namespace that (in)directly 
     *                        owns the elements on the diagram
     * @param machine The StateMachine for the diagram
     *                         (only: statemachine - activitygraph)
     * @return the newly instantiated class diagram
     * @deprecated for 0.25.4 by tfmorris.  Use 
     * {@link #createDiagram(DiagramType, Object, Object)}.
     */
    @Deprecated
    public ArgoDiagram createDiagram(Class type, Object namespace,
            Object machine) {

        ArgoDiagram diagram = null;
        Class diType = null;

        if (type == UMLClassDiagram.class) {
            diagram = new UMLClassDiagram(namespace);
            diType = ClassDiagram.class;
        //#if defined(USECASEDIAGRAM)
        //@#$LPS-USECASEDIAGRAM:GranularityType:Statement
        } else if (type == UMLUseCaseDiagram.class) {
            diagram = new UMLUseCaseDiagram(namespace);
            diType = UseCaseDiagram.class;
        //#endif
        //#if defined(STATEDIAGRAM)
        //@#$LPS-STATEDIAGRAM:GranularityType:Statement
        } else if (type == UMLStateDiagram.class) {
            diagram = new UMLStateDiagram(namespace, machine);
            diType = StateDiagram.class;
        //#endif
        //#if defined(DEPLOYMENTDIAGRAM)
        //@#$LPS-DEPLOYMENTDIAGRAM:GranularityType:Statement
        } else if (type == UMLDeploymentDiagram.class) {
            diagram = new UMLDeploymentDiagram(namespace);
            diType = DeploymentDiagram.class;
        //#endif
        //#if defined(COLLABORATIONDIAGRAM)
        //@#$LPS-COLLABORATIONDIAGRAM:GranularityType:Statement
        } else if (type == UMLCollaborationDiagram.class) {
            diagram = new UMLCollaborationDiagram(namespace);
            diType = CollaborationDiagram.class;
        //#endif
        //#if defined(ACTIVITYDIAGRAM)      
        //@#$LPS-ACTIVITYDIAGRAM:GranularityType:Statement
        } else if (type == UMLActivityDiagram.class) {
            diagram = new UMLActivityDiagram(namespace, machine);
            diType = ActivityDiagram.class;
        //#endif
        }
        //#if defined(SEQUENCEDIAGRAM)      
        //@#$LPS-SEQUENCEDIAGRAM:GranularityType:Statement
         else if (type == UMLSequenceDiagram.class) {
            diagram = new UMLSequenceDiagram(namespace);
            diType = SequenceDiagram.class;
        }
        //#endif
        if (diagram == null) {
            throw new IllegalArgumentException ("Unknown diagram type");
        }
        
        if (Model.getDiagramInterchangeModel() != null) {
            // TODO: This is never executed as Ludos DI work was never
            // finished.
            diagram.getGraphModel().addGraphEventListener(
                 GraphChangeAdapter.getInstance());
            /*
             * The diagram are always owned by the model
             * in this first implementation.
             */
            DiDiagram dd = GraphChangeAdapter.getInstance()
                .createDiagram(diType, namespace);
            ((UMLMutableGraphSupport) diagram.getGraphModel()).setDiDiagram(dd);
        }

        //keep a reference on it in the case where we must add all the diagrams
        //as project members (loading)
        diagrams.add(diagram);
        return diagram;
    }

    /**
     * Factory method to create a new instance of a Class Diagram.
     *
     * @param diagram the diagram
     * @return the newly instantiated class diagram
     */
    public ArgoDiagram removeDiagram(ArgoDiagram diagram) {

        DiDiagram dd =
            ((UMLMutableGraphSupport) diagram.getGraphModel()).getDiDiagram();
        if (dd != null) {
            GraphChangeAdapter.getInstance().removeDiagram(dd);
        }
        return diagram;
    }

    // Unused - tfm - 20070706
//    public DiDiagram getDiDiagram(Object graphModel) {
//        if (graphModel instanceof UMLMutableGraphSupport) {
//            return ((UMLMutableGraphSupport) graphModel).getDiDiagram();
//        }
//        throw new IllegalArgumentException("graphModel: " + graphModel);
//    }

    // Unused - tfm 20070706
//    public void addElement(Object diagram, Object element) {
//        if (!(diagram instanceof ArgoDiagram)) {
//            throw new IllegalArgumentException("diagram: " + diagram);
//        }
//        if (!(element instanceof Fig)) {
//            throw new IllegalArgumentException("fig: " + element);
//        }
//        ((ArgoDiagram) diagram).add((Fig) element);
//    }


    public Object createRenderingElement(Object diagram, Object model) {
        GraphNodeRenderer rend =
            ((Diagram) diagram).getLayer().getGraphNodeRenderer();
        Object renderingElement =
                rend.getFigNodeFor(model, 0, 0, noStyleProperties);
        return renderingElement;
    }
    
    /**
     * Register a specific factory class to create diagram instances for a
     * specific diagram type
     * @param type the diagram type
     * @param factory the factory instance
     */
    public void registerDiagramFactory(
            final DiagramType type,
            final DiagramFactoryInterface factory) {
        factories.put(type, factory);
    }
}
