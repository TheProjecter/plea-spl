// $Id: TabProps.java 11669 2006-12-29 03:44:28Z bobtarling $
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

package org.argouml.uml.ui;

import java.awt.BorderLayout;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;
import org.argouml.model.Model;
import org.argouml.ui.AbstractArgoJPanel;
import org.argouml.ui.ArgoDiagram;
import org.argouml.ui.targetmanager.TargetEvent;
import org.argouml.ui.targetmanager.TargetListener;
import org.argouml.ui.targetmanager.TargetManager;
//#if defined(ACTIVITY)
import org.argouml.uml.diagram.activity.ui.UMLActivityDiagram;
//#endif
//#if defined(COLLABORATION)
import org.argouml.uml.diagram.collaboration.ui.UMLCollaborationDiagram;
//#endif
//#if defined(DEPLOYMENT)
import org.argouml.uml.diagram.deployment.ui.UMLDeploymentDiagram;
//#endif
//#if defined(SEQUENCE)
import org.argouml.uml.diagram.sequence.ui.UMLSequenceDiagram;
//#endif
//#if defined(STATE)
import org.argouml.uml.diagram.state.ui.UMLStateDiagram;
//#endif
import org.argouml.uml.diagram.static_structure.ui.UMLClassDiagram;
import org.argouml.uml.diagram.ui.PropPanelString;
//#if defined(ACTIVITY)
import org.argouml.uml.diagram.ui.PropPanelUMLActivityDiagram;
//#endif
import org.argouml.uml.diagram.ui.PropPanelUMLClassDiagram;
//#if defined(COLLABORATION)
import org.argouml.uml.diagram.ui.PropPanelUMLCollaborationDiagram;
//#endif
//#if defined(DEPLOYMENT)
import org.argouml.uml.diagram.ui.PropPanelUMLDeploymentDiagram;
//#endif
//#if defined(SEQUENCE)
import org.argouml.uml.diagram.ui.PropPanelUMLSequenceDiagram;
//#endif
//#if defined(STATE)
import org.argouml.uml.diagram.ui.PropPanelUMLStateDiagram;
//#endif
//#if defined(USECASE)
import org.argouml.uml.diagram.ui.PropPanelUMLUseCaseDiagram;
import org.argouml.uml.diagram.use_case.ui.UMLUseCaseDiagram;
//#endif
//#if defined(STATE)
import org.argouml.uml.ui.behavior.activity_graphs.PropPanelActionState;
//#endif
//#if defined(ACTIVITY)
import org.argouml.uml.ui.behavior.activity_graphs.PropPanelActivityGraph;
//#endif
//#if defined(STATE)
import org.argouml.uml.ui.behavior.activity_graphs.PropPanelCallState;
import org.argouml.uml.ui.behavior.activity_graphs.PropPanelClassifierInState;
import org.argouml.uml.ui.behavior.activity_graphs.PropPanelObjectFlowState;
//#endif
import org.argouml.uml.ui.behavior.activity_graphs.PropPanelPartition;
//#if defined(STATE)
import org.argouml.uml.ui.behavior.activity_graphs.PropPanelSubactivityState;
//#endif
import org.argouml.uml.ui.behavior.collaborations.PropPanelAssociationEndRole;
import org.argouml.uml.ui.behavior.collaborations.PropPanelAssociationRole;
import org.argouml.uml.ui.behavior.collaborations.PropPanelClassifierRole;
//#if defined(COLLABORATION)
import org.argouml.uml.ui.behavior.collaborations.PropPanelCollaboration;
//#endif
import org.argouml.uml.ui.behavior.collaborations.PropPanelInteraction;
import org.argouml.uml.ui.behavior.collaborations.PropPanelMessage;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelActionSequence;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelArgument;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelCallAction;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelComponentInstance;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelCreateAction;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelDestroyAction;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelLink;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelLinkEnd;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelNodeInstance;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelObject;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelReception;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelReturnAction;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelSendAction;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelSignal;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelStimulus;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelTerminateAction;
import org.argouml.uml.ui.behavior.common_behavior.PropPanelUninterpretedAction;
import org.argouml.uml.ui.behavior.state_machines.PropPanelCallEvent;
import org.argouml.uml.ui.behavior.state_machines.PropPanelChangeEvent;
import org.argouml.uml.ui.behavior.state_machines.PropPanelCompositeState;
import org.argouml.uml.ui.behavior.state_machines.PropPanelFinalState;
import org.argouml.uml.ui.behavior.state_machines.PropPanelGuard;
import org.argouml.uml.ui.behavior.state_machines.PropPanelPseudostate;
import org.argouml.uml.ui.behavior.state_machines.PropPanelSignalEvent;
import org.argouml.uml.ui.behavior.state_machines.PropPanelSimpleState;
import org.argouml.uml.ui.behavior.state_machines.PropPanelStateMachine;
import org.argouml.uml.ui.behavior.state_machines.PropPanelStubState;
import org.argouml.uml.ui.behavior.state_machines.PropPanelSubmachineState;
import org.argouml.uml.ui.behavior.state_machines.PropPanelSynchState;
import org.argouml.uml.ui.behavior.state_machines.PropPanelTimeEvent;
import org.argouml.uml.ui.behavior.state_machines.PropPanelTransition;
import org.argouml.uml.ui.behavior.use_cases.PropPanelActor;
import org.argouml.uml.ui.behavior.use_cases.PropPanelExtend;
import org.argouml.uml.ui.behavior.use_cases.PropPanelExtensionPoint;
import org.argouml.uml.ui.behavior.use_cases.PropPanelInclude;
import org.argouml.uml.ui.behavior.use_cases.PropPanelUseCase;
import org.argouml.uml.ui.foundation.core.PropPanelAbstraction;
import org.argouml.uml.ui.foundation.core.PropPanelAssociation;
import org.argouml.uml.ui.foundation.core.PropPanelAssociationClass;
import org.argouml.uml.ui.foundation.core.PropPanelAssociationEnd;
import org.argouml.uml.ui.foundation.core.PropPanelAttribute;
import org.argouml.uml.ui.foundation.core.PropPanelClass;
import org.argouml.uml.ui.foundation.core.PropPanelComment;
import org.argouml.uml.ui.foundation.core.PropPanelComponent;
import org.argouml.uml.ui.foundation.core.PropPanelDataType;
import org.argouml.uml.ui.foundation.core.PropPanelDependency;
import org.argouml.uml.ui.foundation.core.PropPanelEnumeration;
import org.argouml.uml.ui.foundation.core.PropPanelEnumerationLiteral;
import org.argouml.uml.ui.foundation.core.PropPanelFlow;
import org.argouml.uml.ui.foundation.core.PropPanelGeneralization;
import org.argouml.uml.ui.foundation.core.PropPanelInterface;
import org.argouml.uml.ui.foundation.core.PropPanelMethod;
import org.argouml.uml.ui.foundation.core.PropPanelNode;
import org.argouml.uml.ui.foundation.core.PropPanelOperation;
import org.argouml.uml.ui.foundation.core.PropPanelParameter;
import org.argouml.uml.ui.foundation.core.PropPanelPermission;
import org.argouml.uml.ui.foundation.core.PropPanelUsage;
import org.argouml.uml.ui.foundation.extension_mechanisms.PropPanelStereotype;
import org.argouml.uml.ui.foundation.extension_mechanisms.PropPanelTagDefinition;
import org.argouml.uml.ui.foundation.extension_mechanisms.PropPanelTaggedValue;
import org.argouml.uml.ui.model_management.PropPanelModel;
import org.argouml.uml.ui.model_management.PropPanelPackage;
import org.argouml.uml.ui.model_management.PropPanelSubsystem;
import org.argouml.util.ConfigLoader;
import org.tigris.gef.base.Diagram;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigText;
import org.tigris.swidgets.Orientable;
import org.tigris.swidgets.Orientation;

/**
 * This is the tab on the details panel (DetailsPane) that holds the property
 * panel. On change of target, the property panel in TabProps is changed. <p>
 *
 * With the introduction of the TargetManager,
 * this class holds its original power
 * of controlling its target. The property panels (subclasses of PropPanel) for
 * which this class is the container are being registered as TargetListeners in
 * the setTarget method of this class.
 * They are not registered with TargetManager
 * but with this class to prevent race-conditions while firing TargetEvents from
 * TargetManager.
 *
 * TODO: Once the old module loader is removed from ArgoUML the
 * {@link org.argouml.application.events.ArgoModuleEventListener}
 * interface can be removed.
 */
public class TabProps
    extends AbstractArgoJPanel
    implements TabModelTarget {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(TabProps.class);
    ////////////////////////////////////////////////////////////////
    // instance variables
    private boolean shouldBeEnabled = false;
    private JPanel blankPanel = new JPanel();
    private Hashtable panels = new Hashtable();
    private JPanel lastPanel;
    private String panelClassBaseName = "";

    private Object target;

    /**
     * The list with targetlisteners, these are the property panels
     * managed by TabProps.
     * It should only contain one listener at a time.
     */
    private EventListenerList listenerList = new EventListenerList();

    /**
     * The constructor.
     *
     */
    public TabProps() {
        this("tab.properties", "ui.PropPanel");
    }

    /**
     * The constructor.
     *
     * @param tabName the name of the tab
     * @param panelClassBase the panel class base
     */
    public TabProps(String tabName, String panelClassBase) {
        super(tabName);
        TargetManager.getInstance().addTarget(this);
        setOrientation(ConfigLoader.getTabPropsOrientation());
        panelClassBaseName = panelClassBase;
        setLayout(new BorderLayout());
    }

    /**
     * Set the orientation of the property panel.
     *
     * @param orientation the new orientation for this property panel
     *
     * @see org.tigris.swidgets.Orientable#setOrientation(org.tigris.swidgets.Orientation)
     */
    public void setOrientation(Orientation orientation) {
        super.setOrientation(orientation);
        Enumeration pps = panels.elements();
        while (pps.hasMoreElements()) {
            Object o = pps.nextElement();
            if (o instanceof Orientable) {
                Orientable orientable = (Orientable) o;
                orientable.setOrientation(orientation);
            }
        }
    }

    /**
     * Adds a property panel to the internal list. This allows a plugin to
     * add / register a new property panel at run-time.
     * This property panel will then
     * be displayed in the detatils pane whenever an element
     * of the given metaclass is selected.
     *
     * @param c the metaclass whose details show be displayed
     *          in the property panel p
     * @param p an instance of the property panel for the metaclass m
     *
     */
    public void addPanel(Class c, PropPanel p) {
        panels.put(c, p);
    }


    ////////////////////////////////////////////////////////////////
    // accessors
    /**
     * Sets the target of the property panel. The given target t
     * may either be a Diagram or a modelelement. If the target
     * given is a Fig, a check is made if the fig has an owning
     * modelelement and occurs on the current diagram.
     * If so, that modelelement is the target.
     *
     * @deprecated As of ArgoUml version 0.13.5,
     *         the visibility of this method will change in the future,
     *         replaced by {@link org.argouml.ui.targetmanager.TargetManager}.
     *
     * @param t the new target
     * @see org.argouml.ui.TabTarget#setTarget(java.lang.Object)
     */
    public void setTarget(Object t) {
        // targets ought to be modelelements or diagrams
        t = (t instanceof Fig) ? ((Fig) t).getOwner() : t;
        if (!(t == null || Model.getFacade().isAModelElement(t)
                || t instanceof ArgoDiagram)) {
            return;
        }

        if (lastPanel != null) {
            remove(lastPanel);
            if (lastPanel instanceof TargetListener) {
                removeTargetListener((TargetListener) lastPanel);
            }
        }
        target = t;
        if (t == null) {
            add(blankPanel, BorderLayout.CENTER);
            shouldBeEnabled = false;
            lastPanel = blankPanel;
        } else {
            shouldBeEnabled = true;
            TabModelTarget newPanel = null;
            newPanel = findPanelFor(t);
            if (newPanel != null) {
                addTargetListener(newPanel);
            }
            if (newPanel instanceof JPanel) {
                add((JPanel) newPanel, BorderLayout.CENTER);
                shouldBeEnabled = true;
                lastPanel = (JPanel) newPanel;
            } else {
                add(blankPanel, BorderLayout.CENTER);
                shouldBeEnabled = false;
                lastPanel = blankPanel;
            }

        }
    }

    /*
     * @see org.argouml.ui.TabTarget#refresh()
     */
    public void refresh() {
        setTarget(TargetManager.getInstance().getTarget());
    }

    /**
     * Find the correct properties panel for the target.
     *
     * @param trgt the target class
     * @return the tab panel
     */
    private TabModelTarget findPanelFor(Object trgt) {
        /* 1st attempt: get a panel that we created before: */
        TabModelTarget p = (TabModelTarget) panels.get(trgt.getClass());
        if (p != null && LOG.isDebugEnabled()) {
            LOG.debug("Getting prop panel for: " + trgt.getClass().getName()
                    + ", " + "found (in cache?) " + p);
            return p;
        }

        /* 2nd attempt: If we didn't find the panel then
         * use the factory to create a new one
	 */
        p = createPropPanel(trgt);
        if (p != null) {
            LOG.debug("Factory created " + p.getClass().getName()
                    + " for " + trgt.getClass().getName());
            panels.put(trgt.getClass(), p);
            if (p instanceof PropPanel) {
        	((PropPanel) p).buildToolbar();
            }
            return p;
        }

        // TODO: If the factory didn't know how to create the panel then
        // we fall through to the old reflection method. The code below
        // should be removed one the createPropPanel method is complete.

        /* 3rd attempt: use the reflection method: */
        Class panelClass = panelClassFor(trgt.getClass());
        if (panelClass == null) {
            LOG.error("No panel class found for: " + trgt.getClass());
            return null;
        }
        LOG.debug("panelClass found for: " + panelClass);
        try {
            // if a class is abstract we do not need to try
            // to instantiate it.
            if (Modifier.isAbstract(panelClass.getModifiers())) {
                return null;
            }
            p = (TabModelTarget) panelClass.newInstance();
            if (p instanceof PropPanel) {
        	((PropPanel) p).buildToolbar();
            }
            // moved next line inside try block to avoid filling
            // the hashmap with bogus values.
            panels.put(trgt.getClass(), p);
        } catch (IllegalAccessException ignore) {
            // doubtfull if this must be ignored.
            LOG.error("Failed to create a prop panel", ignore);
            return null;
        } catch (InstantiationException ignore) {
            // doubtfull if this must be ignored.
            LOG.error("Failed to create a prop panel", ignore);
            return null;
        }

        LOG.warn(p.getClass().getName()
                + " has been created by reflection. "
                + "This should be added to the createPropPanel method.");
        return p;
    }

    /**
     * A factory method to create a PropPanel for a particular model
     * element.
     *
     * @param modelElement The model element
     * @return A new prop panel to display any model element of the given type
     */
    private TabModelTarget createPropPanel(Object modelElement) {

	TabModelTarget propPanel = null;
	
        // Create prop panels for diagrams
        //#if defined(ACTIVITY)
        if (modelElement instanceof UMLActivityDiagram) {
            propPanel = new PropPanelUMLActivityDiagram();
        } else 
        //#endif
        if (modelElement instanceof UMLClassDiagram) {
            propPanel = new PropPanelUMLClassDiagram();            
        } 
        //#if defined(COLLABORATION)
        else if (modelElement instanceof UMLCollaborationDiagram) {
            propPanel = new PropPanelUMLCollaborationDiagram();
        } 
        //#endif
        //#if defined(DEPLOYMENT)
        else if (modelElement instanceof UMLDeploymentDiagram) {
            propPanel = new PropPanelUMLDeploymentDiagram();
        } 
        //#endif
        //#if defined(SEQUENCE)
        else if (modelElement instanceof UMLSequenceDiagram) {
            propPanel = new PropPanelUMLSequenceDiagram();
        } 
        //#endif
        //#if defined(STATE)
        else if (modelElement instanceof UMLStateDiagram) {
            propPanel = new PropPanelUMLStateDiagram();
        } 
        //#endif
        //#if defined(USECASE)
        else if (modelElement instanceof UMLUseCaseDiagram) {
            propPanel = new PropPanelUMLUseCaseDiagram();
        } 
        //#endif
        //#if defined(STATE)
        else if (Model.getFacade().isASubmachineState(modelElement)) {
            propPanel = new PropPanelSubmachineState();
        } else if (Model.getFacade().isASubactivityState(modelElement)) {
            propPanel = new PropPanelSubactivityState();           
        }
        //#endif
        else if (Model.getFacade().isAAbstraction(modelElement)) {
            propPanel = new PropPanelAbstraction();
        } 
        //#if defined(STATE)
        else if (Model.getFacade().isACallState(modelElement)) {
            propPanel = new PropPanelCallState();            
        } 
        //#endif
        //#if defined(SEQUENCE)
        else if (Model.getFacade().isAActionSequence(modelElement)) {
            propPanel = new PropPanelActionSequence();            
        }
        //#endif
        //#if defined(STATE)
        else if (Model.getFacade().isAActionState(modelElement)) {
            propPanel = new PropPanelActionState();
        }
        //#endif
        //#if defined(ACTIVITY)
         else if (Model.getFacade().isAActivityGraph(modelElement)) {
            propPanel = new PropPanelActivityGraph();
        } 
        //#endif
        //#if defined(USECASE)
        else if (Model.getFacade().isAActor(modelElement)) {
            propPanel = new PropPanelActor();
        }
        //#endif
        else if (Model.getFacade().isAArgument(modelElement)) {
            propPanel = new PropPanelArgument();
        } else if (Model.getFacade().isAAssociationClass(modelElement)) {
            propPanel = new PropPanelAssociationClass();
        } else if (Model.getFacade().isAAssociationRole(modelElement)) {
            propPanel = new PropPanelAssociationRole();
        } else if (Model.getFacade().isAAssociation(modelElement)) {
            propPanel = new PropPanelAssociation();
        } else if (Model.getFacade().isAAssociationEndRole(modelElement)) {
            propPanel = new PropPanelAssociationEndRole();
        } else if (Model.getFacade().isAAssociationEnd(modelElement)) {
            propPanel = new PropPanelAssociationEnd();
        } else if (Model.getFacade().isAAttribute(modelElement)) {
            propPanel = new PropPanelAttribute();            
        }
        //#if defined(ACTIVITY)
        else if (Model.getFacade().isACallAction(modelElement)) {
            propPanel = new PropPanelCallAction();
        }
        //#endif
        //#if defined(STATE)
        else if (Model.getFacade().isAClassifierInState(modelElement)) {
            propPanel = new PropPanelClassifierInState();
        }
        //#endif
        else if (Model.getFacade().isAClass(modelElement)) {
            propPanel = new PropPanelClass();
        } else if (Model.getFacade().isAClassifierRole(modelElement)) {
            propPanel = new PropPanelClassifierRole();
        }
        //#endif
        //#if defined(COLLABORATION)
        else if (Model.getFacade().isACollaboration(modelElement)) {
            propPanel = new PropPanelCollaboration();
        }
        //#endif
        else if (Model.getFacade().isAComment(modelElement)) {
            propPanel = new PropPanelComment();
        } else if (Model.getFacade().isAComponent(modelElement)) {
            propPanel = new PropPanelComponent();
        } else if (Model.getFacade().isAComponentInstance(modelElement)) {
            propPanel = new PropPanelComponentInstance();
        }
        //#if defined(STATE)
        else if (Model.getFacade().isACompositeState(modelElement)) {
            propPanel = new PropPanelCompositeState();
        }
        //#endif
        //#if defined(ACTIVITY)
        else if (Model.getFacade().isACreateAction(modelElement)) {
            propPanel = new PropPanelCreateAction();
        }
        //#endif
        else if (Model.getFacade().isAEnumeration(modelElement)) {
            propPanel = new PropPanelEnumeration();
        } else if (Model.getFacade().isADataType(modelElement)) {
            propPanel = new PropPanelDataType();
        } else if (Model.getFacade().isADestroyAction(modelElement)) {
            propPanel = new PropPanelDestroyAction();
        } else if (Model.getFacade().isAEnumerationLiteral(modelElement)) {
            propPanel = new PropPanelEnumerationLiteral();            
        //#if defined(USECASEDIAGRAM)
        //@#$LPS-USECASEDIAGRAM:GranularityType:Statement
        } else if (Model.getFacade().isAExtend(modelElement)) {
            propPanel = new PropPanelExtend();
        } else if (Model.getFacade().isAExtensionPoint(modelElement)) {
            propPanel = new PropPanelExtensionPoint();                  
        //#endif
        //#if defined(STATE)
        }else if (Model.getFacade().isAFinalState(modelElement)) {
            propPanel = new PropPanelFinalState();       
		//#endif
        }else if (Model.getFacade().isAFlow(modelElement)) {
            propPanel = new PropPanelFlow();
        } else if (Model.getFacade().isAGeneralization(modelElement)) {
            propPanel = new PropPanelGeneralization();
        } else if (Model.getFacade().isAGuard(modelElement)) {
            propPanel = new PropPanelGuard();
        //#if defined(USECASEDIAGRAM)
        //@#$LPS-USECASEDIAGRAM:GranularityType:Statement            
        } else if (Model.getFacade().isAInclude(modelElement)) {
            propPanel = new PropPanelInclude();
        //#endif
        } else if (Model.getFacade().isAInteraction(modelElement)) {
            propPanel = new PropPanelInteraction();
        } else if (Model.getFacade().isAInterface(modelElement)) {
            propPanel = new PropPanelInterface();
        } else if (Model.getFacade().isALink(modelElement)) {
            propPanel = new PropPanelLink();
        } else if (Model.getFacade().isALinkEnd(modelElement)) {
            propPanel = new PropPanelLinkEnd();
        } else if (Model.getFacade().isAMessage(modelElement)) {
            propPanel = new PropPanelMessage();
        } else if (Model.getFacade().isAMethod(modelElement)) {
            propPanel = new PropPanelMethod();
        } else if (Model.getFacade().isAModel(modelElement)) {
            propPanel = new PropPanelModel();
        } else if (Model.getFacade().isANode(modelElement)) {
            propPanel = new PropPanelNode();
        } else if (Model.getFacade().isANodeInstance(modelElement)) {
            propPanel = new PropPanelNodeInstance();
        } else if (Model.getFacade().isAObject(modelElement)) {
            propPanel = new PropPanelObject();
        //#if defined(ACTIVITYDIAGRAM)
        //@#$LPS-ACTIVITYDIAGRAM:GranularityType:Statement
        //@#$LPS-ACTIVITYDIAGRAM:Localization:NestedStatement
        } else if (Model.getFacade().isAObjectFlowState(modelElement)) {
            propPanel = new PropPanelObjectFlowState();
        //#endif
        } else if (Model.getFacade().isAOperation(modelElement)) {
            propPanel = new PropPanelOperation();
        } else if (Model.getFacade().isAPackage(modelElement)) {
            propPanel = new PropPanelPackage();
        } else if (Model.getFacade().isAParameter(modelElement)) {
            propPanel = new PropPanelParameter();
        } else if (Model.getFacade().isAPartition(modelElement)) {
            propPanel = new PropPanelPartition();
        } else if (Model.getFacade().isAPermission(modelElement)) {
            propPanel = new PropPanelPermission();
        }
        //#if defined(STATE)
        else if (Model.getFacade().isAPseudostate(modelElement)) {
            propPanel = new PropPanelPseudostate();
        }
        //#endif
        else if (Model.getFacade().isAReception(modelElement)) {
            propPanel = new PropPanelReception();
        }       
        //#if defined(ACTITVY)
        else if (Model.getFacade().isAReturnAction(modelElement)) {
            propPanel = new PropPanelReturnAction();
        } else if (Model.getFacade().isASendAction(modelElement)) {
            propPanel = new PropPanelSendAction();
        } else if (Model.getFacade().isASignal(modelElement)) {
            propPanel = new PropPanelSignal();
        }
        //#endif
        //#if defined(STATE)
        else if (Model.getFacade().isASimpleState(modelElement)) {
            propPanel = new PropPanelSimpleState(); //TODO: Check!
        } else if (Model.getFacade().isAStateMachine(modelElement)) {
            propPanel = new PropPanelStateMachine();
        }
		//#endif
        else if (Model.getFacade().isAStereotype(modelElement)) {
            propPanel = new PropPanelStereotype();
        } else if (Model.getFacade().isAStimulus(modelElement)) {
            propPanel = new PropPanelStimulus();        
        //#if defined(STATEDIAGRAM) or defined(ACTIVITYDIAGRAM)
        //@#$LPS-STATEDIAGRAM:GranularityType:Statement
        //@#$LPS-ACTIVITYDIAGRAM:GranularityType:Statement
    	}else if (Model.getFacade().isAStubState(modelElement)) {
            propPanel = new PropPanelStubState();       
        //#endif
    	}else if (Model.getFacade().isASubsystem(modelElement)) {
            propPanel = new PropPanelSubsystem();        
        //#if defined(STATEDIAGRAM) or defined(ACTIVITYDIAGRAM)
        //@#$LPS-STATEDIAGRAM:GranularityType:Statement
        //@#$LPS-ACTIVITYDIAGRAM:GranularityType:Statement
    	}else if (Model.getFacade().isASynchState(modelElement)) {
            propPanel = new PropPanelSynchState();        
        //#endif
    	}else if (Model.getFacade().isATaggedValue(modelElement)) {
            propPanel = new PropPanelTaggedValue();
        } else if (Model.getFacade().isATagDefinition(modelElement)) {
            propPanel = new PropPanelTagDefinition();        
        //#if defined(ACTIVITY)
        }else if (Model.getFacade().isATerminateAction(modelElement)) {
            propPanel = new PropPanelTerminateAction();
        } else if (Model.getFacade().isATransition(modelElement)) {
            propPanel = new PropPanelTransition();
        } else if (Model.getFacade().isAUninterpretedAction(modelElement)) {
            propPanel = new PropPanelUninterpretedAction();        
        //#endif
    	}else if (Model.getFacade().isAUsage(modelElement)) {
            propPanel = new PropPanelUsage();        
        //#if defined(USECASE)
    	}else if (Model.getFacade().isAUseCase(modelElement)) {
            propPanel = new PropPanelUseCase();        
        //#endif
    	}else if (Model.getFacade().isACallEvent(modelElement)) {
            propPanel = new PropPanelCallEvent();
        } else if (Model.getFacade().isAChangeEvent(modelElement)) {
            propPanel = new PropPanelChangeEvent();
        } else if (Model.getFacade().isASignalEvent(modelElement)) {
            propPanel = new PropPanelSignalEvent();
        } else if (Model.getFacade().isATimeEvent(modelElement)) {
            propPanel = new PropPanelTimeEvent();
        } else if (Model.getFacade().isADependency(modelElement)) {
            propPanel = new PropPanelDependency();
        } else if (modelElement instanceof FigText) {
            propPanel = new PropPanelString();
        }
        
        if (propPanel instanceof PropPanel) {
            ((PropPanel) propPanel).buildToolbar();
        }

        return propPanel;
    }

    /**
     * Locate the panel for the given class.
     * TODO: Remove when createPropPanel complete
     *
     * @param targetClass the given class
     * @return the properties panel for the given class, or null if not found
     */
    private Class panelClassFor(Class targetClass) {

        String panelClassName = "";
        String pack = "org.argouml.uml";
        String base = "";

        String targetClassName = targetClass.getName();
        LOG.info("Trying to locate panel for: " + targetClassName);
        int lastDot = targetClassName.lastIndexOf(".");

        //remove "org.omg.uml."
        if (lastDot > 0) {
            base = targetClassName.substring(12, lastDot + 1);
        } else {
            base = targetClassName.substring(12);
        }

        targetClassName = Model.getMetaTypes().getName(targetClass);

        // This doesn't work for panel property tabs - they are being put in the
        // wrong place. Really we should have defined these are preloaded them
        // along with ArgoDiagram in initPanels above.

        try {
            panelClassName =
                pack + ".ui." + base + "PropPanel" + targetClassName;
            LOG.info("Looking for: " + panelClassName);
            return Class.forName(panelClassName);
        } catch (ClassNotFoundException ignore) {
            LOG.error(
		      "Class " + panelClassName + " for Panel not found!",
		      ignore);
        }
        return null;
    }

    /**
     * @return the name
     */
    protected String getClassBaseName() {
        return panelClassBaseName;
    }

    /**
     * Returns the current target.
     * @deprecated As of ArgoUml version 0.13.5,
     * the visibility of this method will change in the future, replaced by
     * {@link org.argouml.ui.targetmanager.TargetManager#getTarget()
     * TargetManager.getInstance().getTarget()}.
     *
     * @return the target
     * @see org.argouml.ui.TabTarget#getTarget()
     */
    public Object getTarget() {
        return target;
    }

    /**
     * Determines if the property panel should be enabled. 
     * The property panel should always be enabled if the
     * target is an instance of a modelelement or an argodiagram.
     * If the target given is a Fig, a check is made if the fig
     * has an owning modelelement and occurs on
     * the current diagram. If so, that modelelement is the target.
     * @param t the target
     * @return true if property panel should be enabled
     * @see org.argouml.ui.TabTarget#shouldBeEnabled(Object)
     */
    public boolean shouldBeEnabled(Object t) {
        t = (t instanceof Fig) ? ((Fig) t).getOwner() : t;
        if (t instanceof Diagram || Model.getFacade().isAModelElement(t)) {
            shouldBeEnabled = true;
        } else {
            shouldBeEnabled = false;
        }

        return shouldBeEnabled;
    }

    /*
     * @see org.argouml.ui.targetmanager.TargetListener#targetAdded(org.argouml.ui.targetmanager.TargetEvent)
     */
    public void targetAdded(TargetEvent e) {
        setTarget(TargetManager.getInstance().getSingleTarget());
        fireTargetAdded(e);
        if (listenerList.getListenerCount() > 0) {
            validate();
            repaint();
        }

    }

    /*
     * @see org.argouml.ui.targetmanager.TargetListener#targetRemoved(org.argouml.ui.targetmanager.TargetEvent)
     */
    public void targetRemoved(TargetEvent e) {
        setTarget(TargetManager.getInstance().getSingleTarget());
        fireTargetRemoved(e);
        validate();
        repaint();
    }

    /*
     * @see org.argouml.ui.targetmanager.TargetListener#targetSet(org.argouml.ui.targetmanager.TargetEvent)
     */
    public void targetSet(TargetEvent e) {
        setTarget(TargetManager.getInstance().getSingleTarget());
        fireTargetSet(e);
        validate();
        repaint();
    }

    /**
     * Adds a listener.
     * @param listener the listener to add
     */
    private void addTargetListener(TargetListener listener) {
        listenerList.add(TargetListener.class, listener);
    }

    /**
     * Removes a target listener.
     * @param listener the listener to remove
     */
    private void removeTargetListener(TargetListener listener) {
        listenerList.remove(TargetListener.class, listener);
    }

    private void fireTargetSet(TargetEvent targetEvent) {
        //      Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TargetListener.class) {
                // Lazily create the event:
		((TargetListener) listeners[i + 1]).targetSet(targetEvent);
            }
        }
    }

    private void fireTargetAdded(TargetEvent targetEvent) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TargetListener.class) {
                // Lazily create the event:
		((TargetListener) listeners[i + 1]).targetAdded(targetEvent);
            }
        }
    }

    private void fireTargetRemoved(TargetEvent targetEvent) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TargetListener.class) {
                // Lazily create the event:
                ((TargetListener) listeners[i + 1]).targetRemoved(targetEvent);
            }
        }
    }

} /* end class TabProps */

