// $Id: PrivateHandler.java 10090 2006-03-14 22:57:37Z linus $
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
package org.argouml.persistence;

import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import org.argouml.ui.ArgoDiagram;

import org.argouml.uml.diagram.ui.FigEdgeModelElement;
import org.argouml.uml.diagram.ui.FigNodeModelElement;

import org.tigris.gef.persistence.pgml.Container;
import org.tigris.gef.persistence.pgml.FigEdgeHandler;
import org.tigris.gef.persistence.pgml.FigGroupHandler;
import org.tigris.gef.persistence.pgml.PGMLHandler;

import org.xml.sax.SAXException;

/**
 * Will set the ItemUID for objects represented by
 * PGML elements that contain private elements that have
 * ItemUID assignments in them.<p>
 *
 * Currently, there are three possibilities: ArgoDiagram,
 * FigNode, FigEdge
 */
public class PrivateHandler
    extends org.tigris.gef.persistence.pgml.PrivateHandler {

    private Container container;

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(PrivateHandler.class);

    /**
     * The constructor.
     *
     * @param parser
     * @param cont
     */
    public PrivateHandler(PGMLStackParser parser, Container cont) {
        super(parser, cont);
        container = cont;
    }

    /**
     * Utility class to pair a name and a value String together.
     */
    static class NameVal {
        private String name;
        private String value;

        /**
         * The constructor.
         *
         * @param n the name
         * @param v the value
         */
        NameVal(String n, String v) {
            name = n.trim();
            value = v.trim();
        }

        /**
         * @return returns the name
         */
        String getName() {
            return name;
        }

        /**
         * @return returns the value
         */
        String getValue() {
            return value;
        }
    }

    /**
     * Splits a name value pair into a NameVal instance. A name value pair is
     * a String on the form &lt; name = ["] value ["] &gt;.
     *
     * @param str A String with a name value pair.
     * @return A NameVal, or null if they could not be split.
     */
    protected NameVal splitNameVal(String str) {
        NameVal rv = null;
        int lqpos, rqpos;
        int eqpos = str.indexOf('=');

        if (eqpos < 0) {
            return null;
        }

        lqpos = str.indexOf('"', eqpos);
        rqpos = str.lastIndexOf('"');

        if (lqpos < 0 || rqpos <= lqpos) {
            return null;
        }

        rv =
            new NameVal(str.substring(0, eqpos),
                str.substring(lqpos + 1, rqpos));

        return rv;
    }
}
