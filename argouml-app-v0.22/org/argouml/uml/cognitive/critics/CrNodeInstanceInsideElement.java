//#if defined(COGNITIVE)
//@#$LPS-COGNITIVE:GranularityType:Package
//@#$LPS-DEPLOYMENTDIAGRAM:GranularityType:Class

// $Id: CrNodeInstanceInsideElement.java 10737 2006-06-11 19:01:27Z mvw $
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

package org.argouml.uml.cognitive.critics;

import java.util.Collection;
import java.util.Iterator;

import org.argouml.cognitive.Designer;
import org.argouml.cognitive.ListSet;
import org.argouml.cognitive.ToDoItem;
import org.argouml.uml.cognitive.UMLDecision;
import org.argouml.uml.cognitive.UMLToDoItem;
//#if defined(DEPLOYMENTDIAGRAM)
import org.argouml.uml.diagram.deployment.ui.FigMNodeInstance;
import org.argouml.uml.diagram.deployment.ui.UMLDeploymentDiagram;
//#endif

/**
 * A critic to detect when there are node-instances that
 * are inside another element
 *
 * @author jrobbins
 */
public class CrNodeInstanceInsideElement extends CrUML {

    /**
     * The constructor.
     *
     */
    public CrNodeInstanceInsideElement() {
        setupHeadAndDesc();
	addSupportedDecision(UMLDecision.PATTERNS);
    }

    /**
     * @see org.argouml.uml.cognitive.critics.CrUML#predicate2(
     * java.lang.Object, org.argouml.cognitive.Designer)
     */
    public boolean predicate2(Object dm, Designer dsgr) {
    //#if defined(DEPLOYMENTDIAGRAM)
	if (!(dm instanceof UMLDeploymentDiagram))
	//#endif
		return NO_PROBLEM;
	//#if defined(DEPLOYMENTDIAGRAM)
	UMLDeploymentDiagram dd = (UMLDeploymentDiagram) dm;
	ListSet offs = computeOffenders(dd);
	if (offs == null) return NO_PROBLEM;
	return PROBLEM_FOUND;
	//#endif
    }

    /**
     * @see org.argouml.cognitive.critics.Critic#toDoItem(
     * java.lang.Object, org.argouml.cognitive.Designer)
     */
    public ToDoItem toDoItem(Object dm, Designer dsgr) {
    //#if defined(DEPLOYMENTDIAGRAM)
	UMLDeploymentDiagram dd = (UMLDeploymentDiagram) dm;
	ListSet offs = computeOffenders(dd);
	//#endif
	return new UMLToDoItem(this
			//#if defined(DEPLOYMENTDIAGRAM)
			, offs, dsgr
			//#endif
			);
    }

    /**
     * @see org.argouml.cognitive.Poster#stillValid(
     * org.argouml.cognitive.ToDoItem, org.argouml.cognitive.Designer)
     */
    public boolean stillValid(ToDoItem i, Designer dsgr) {
	if (!isActive()) return false;
	//#if defined(DEPLOYMENTDIAGRAM)
	ListSet offs = i.getOffenders();
	UMLDeploymentDiagram dd = (UMLDeploymentDiagram) offs.firstElement();
	//if (!predicate(dm, dsgr)) return false;
	ListSet newOffs = computeOffenders(dd);
	//#endif
	boolean res = 
		//#if defined(DEPLOYMENTDIAGRAM)
		offs.equals(newOffs) && 
		//#endif
		true;
	return res;
    }

  //#if defined(DEPLOYMENTDIAGRAM)
    /**
     * If there are node-instances that have an enclosing Fig
     * the returned vector-set is not null. Then in the vector-set
     * are the UMLDeploymentDiagram and all FigMNodeInstances with an
     * enclosing Fig
     *
     * @param dd the diagram to check
     * @return the set of offenders
     */
    public ListSet computeOffenders(UMLDeploymentDiagram dd) {
	Collection figs = dd.getLayer().getContents();
        Iterator figIter = figs.iterator();
	ListSet offs = null;
	while (figIter.hasNext()) {
	    Object obj = figIter.next();
	    if (!(obj instanceof FigMNodeInstance)) {
                continue;
            }
	    FigMNodeInstance fn = (FigMNodeInstance) obj;
	    if (fn.getEnclosingFig() != null) {
		if (offs == null) {
		    offs = new ListSet();
		    offs.addElement(dd);
		}
		offs.addElement(fn);
	    }
	}
	return offs;
    }
    //#endif
} /* end class CrNodeInstanceInsideElement.java */

//#endif