// $Id: ActionAddConcurrentRegion.java 10318 2006-04-04 17:29:39Z mvw $
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


package org.argouml.uml.diagram.ui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.Action;

import org.apache.log4j.Logger;
import org.argouml.application.helpers.ResourceLoaderWrapper;
import org.argouml.i18n.Translator;
import org.argouml.model.Model;
import org.argouml.ui.targetmanager.TargetManager;
//#endif
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;
import org.tigris.gef.base.LayerDiagram;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.graph.MutableGraphModel;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.undo.UndoableAction;

/**
 * Add a concurrent region to a concurrent composite state
 *
 * @author pepargouml@yahoo.es
 */
public class ActionAddConcurrentRegion extends UndoableAction {

    ////////////////////////////////////////////////////////////////
    // static variables

	/** logger */
    private static final Logger LOG =
        Logger.getLogger(ActionAddConcurrentRegion.class);

    ////////////////////////////////////////////////////////////////
    // constructors

    public ActionAddConcurrentRegion() {
        super(Translator.localize("action.add-concurrent-region"),
                ResourceLoaderWrapper.lookupIcon("action.add-concurrent-region"));
        // Set the tooltip string:
        putValue(Action.SHORT_DESCRIPTION, 
                Translator.localize("action.add-concurrent-region"));
    }

    ////////////////////////////////////////////////////////////////
    // main methods

    /**
     * @see javax.swing.Action#isEnabled()
     */
    public boolean isEnabled() {
        Object target = TargetManager.getInstance().getModelTarget();
        //#if defined(STATEDIAGRAM) or defined(ACTIVITYDIAGRAM)
        //@#$LPS-STATEDIAGRAM:GranularityType:Statement
        //@#$LPS-ACTIVITYDIAGRAM:GranularityType:Statement
        //@#$LPS-STATEDIAGRAM:Localization:BeforeReturn
        //@#$LPS-ACTIVITYDIAGRAM:Localization:BeforeReturn
        if (Model.getStateMachinesHelper().isTopState(target)) {
            return false;
        }
        //#endif
        return TargetManager.getInstance().getModelTargets().size() < 2;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae) {
        super.actionPerformed(ae);
        try {
            /*Here the actions to divide a region*/
            Fig f = (Fig) TargetManager.getInstance().getFigTarget();

            if (Model.getFacade().isAConcurrentRegion(f.getOwner()))
                f = f.getEnclosingFig();

			//#endif
            Editor editor = Globals.curEditor();
            GraphModel gm = editor.getGraphModel();
            LayerDiagram lay =
                ((LayerDiagram) editor.getLayerManager().getActiveLayer());

            Rectangle rName =
                ((FigNodeModelElement) f).getNameFig().getBounds();
            Rectangle rFig = f.getBounds();
            Fig encloser = null;
            encloser = f;
            if (!(gm instanceof MutableGraphModel))
                  return;
			//#endif
            editor.getSelectionManager().select(f);
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

}
