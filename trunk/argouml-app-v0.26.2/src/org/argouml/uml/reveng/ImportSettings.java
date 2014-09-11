// $Id: ImportSettings.java 15041 2008-06-22 22:03:27Z tfmorris $
// Copyright (c) 2006-2008 The Regents of the University of California. All
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

package org.argouml.uml.reveng;

/**
 * Interface for the generic import settings that a pluggable importer module
 * can query the value of.
 */

public interface ImportSettings {

    /**
     * Import only classifiers
     */
    public static final int DETAIL_CLASSIFIER = 0;

    /**
     * Import classifiers and their features
     */
    public static final int DETAIL_CLASSIFIER_FEATURE = 1;

    /**
     * Import full detail
     */
    public static final int DETAIL_FULL = 2;

    /**
     * @return the level of import detail requested by the user. One of
     *         DETAIL_CLASSIFIER, DETAIL_CLASSIFIER_FEATURE, or DETAIL_FULL.
     */
    public int getImportLevel();

    /**
     * @return string representing the character encoding of the input source
     *         files.
     */
    public String getInputSourceEncoding();

    /**
     * @return always returns false.
     * @deprecated for 0.25.6 by tfmorris.  This is a Java-specific setting
     * that was inadvertantly included in the interface for 0.24.  Not 
     * intended for public use.  Instead use the Settings interface to
     * importer-specific settings.
     */
    @Deprecated
    public boolean isAttributeSelected();

    /**
     * @return always returns false.
     * @deprecated for 0.25.6 by tfmorris.  This is a Java-specific setting
     * that was inadvertantly included in the interface for 0.24.  Not 
     * intended for public use.  Instead use the Settings interface to
     * importer-specific settings.
     */
    @Deprecated
    public boolean isDatatypeSelected();

    /**
     * @return true if the user has request diagrams to be created for packages
     *         contained in the imported source code.
     * @deprecated for 0.25.6 by tfmorris. This is handled by the import
     *             framework so specific importers don't need to worry about it.
     */
    /*
     * NOTE: When this is removed from here, it should NOT be removed from
     * ImportSettingsInternal to guarantee that GUI implementations are forced
     * to implement it.
     */
    @Deprecated
    public boolean isCreateDiagramsSelected();
    
    /**
     * @return true, if user has requested that new figures placed in diagrams
     *         should be minimized so they don't show internal compartments.
     * @deprecated for 0.25.6 by tfmorris. This is handled by the import
     *             framework so specific importers don't need to worry about it.
     */
    /*
     * NOTE: When this is removed from here, it should NOT be removed from
     * ImportSettingsInternal to guarantee that GUI implementations are forced
     * to implement it.
     */
    @Deprecated
    public boolean isMinimizeFigsSelected();


}
