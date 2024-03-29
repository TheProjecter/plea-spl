// $Id: Action.java 11266 2006-10-01 08:01:23Z linus $
// Copyright (c) 2006 The Regents of the University of California. All
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

package org.argouml.ui.cmd;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

/**
 * This class represents a "shortcuttable" action
 * 
 * @author andrea.nironi@gmail.com
 */
public class Action {

    private KeyStroke defaultShortcut;

    private KeyStroke currentShortcut;

    private String key;

    private AbstractAction actionInstance;

    private String actionInstanceName;

    /**
     * Constructor for Action class
     * 
     * @param actionKey
     *            the key of this action
     * @param currentKeyStroke
     *            the current shortcut for this action, if present
     * @param defaultKeyStroke
     *            the default shortcut for this action, if present
     * @param action
     *            the AbstractAction that represents the real class
     * @param actionName
     *            the name of the action
     */
    protected Action(String actionKey, KeyStroke currentKeyStroke,
            KeyStroke defaultKeyStroke, AbstractAction action, 
            String actionName) {
        this.key = actionKey;
        this.currentShortcut = currentKeyStroke;
        this.defaultShortcut = defaultKeyStroke;
        this.actionInstance = action;
        this.actionInstanceName = actionName;
    }

    /**
     * Getter for key
     * 
     * @return the id for this shortcut
     */
    public String getKey() {
        return key;
    }

    /**
     * Getter for currentShortcut
     * 
     * @return the current keyStroke for this shortcut
     */
    public KeyStroke getCurrentShortcut() {
        return currentShortcut;
    }

    /**
     * Setter for currentShortcut
     * 
     * @param actualShortcut
     *            the new shortCut
     */
    public void setCurrentShortcut(KeyStroke actualShortcut) {
        this.currentShortcut = actualShortcut;
    }

    /**
     * Getter for defaultShortcut
     * 
     * @return the default keyStroke for this shortcut
     */
    public KeyStroke getDefaultShortcut() {
        return defaultShortcut;
    }

    /**
     * Getter for Action's name
     * 
     * @return the name of the Action
     */
    public String getActionName() {
        return actionInstanceName;
    }

    /**
     * Getter for Action instance
     * 
     * @return the Action
     */
    public AbstractAction getActionInstance() {
        return this.actionInstance;
    }
}
