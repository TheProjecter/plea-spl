// $Id: ImportCommon.java 11963 2007-02-03 20:21:02Z tfmorris $
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

package org.argouml.uml.reveng;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.argouml.application.api.Argo;
import org.argouml.application.api.Configuration;
import org.argouml.application.api.PluggableImport;
import org.argouml.application.api.ProgressMonitor;
import org.argouml.application.modules.ModuleLoader;
//#if defined(COGNITIVE)
//@#$LPS-COGNITIVE:GranularityType:Import
import org.argouml.cognitive.Designer;
//#endif
import org.argouml.i18n.Translator;
import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.Model;
import org.argouml.moduleloader.ModuleInterface;
import org.argouml.ui.ProjectBrowser;
import org.argouml.ui.explorer.ExplorerEventAdaptor;
import org.argouml.uml.diagram.static_structure.ClassDiagramGraphModel;
import org.argouml.uml.diagram.static_structure.layout.ClassdiagramLayouter;
import org.argouml.uml.diagram.ui.UMLDiagram;
import org.argouml.uml.reveng.ImportInterface.ImportException;
import org.tigris.gef.base.Globals;

/**
 * Source language import class - GUI independent superclass.
 * 
 * Specific Swing and SWT/Eclipse importers will extend this class.
 * 
 * @author Tom Morris
 */
public abstract class ImportCommon implements ImportSettingsInternal {
    
    private Logger LOG = Logger.getLogger(ImportCommon.class);
    
    
    /**
     * The % maximum progress required to preparing for import. 
     */
    protected static final int MAX_PROGRESS_PREPARE = 1;
    
    /**
     * The % maximum progress required to import.
     */
    protected static final int MAX_PROGRESS_IMPORT = 99;
    
    /**
     * keys are module name, values are PluggableImport instance.
     */
    private Hashtable modules;

    /**
     * Current language module.
     */
    private Object currentModule;


    /**
     * Imported directory.
     */
    private String srcPath;

    /**
     * Create a interface to the current diagram.
     */
    private DiagramInterface diagramInterface;

    private File selectedFile;
    
    protected ImportCommon() {
        super();
        modules = new Hashtable();
        
        // Get all old style modules
        List arraylist = 
            ModuleLoader.getInstance().getPlugins(PluggableImport.class, null);
        ListIterator iterator = arraylist.listIterator();
        while (iterator.hasNext()) {
            PluggableImport pIModule = (PluggableImport) iterator.next();
            modules.put(pIModule.getModuleName(), pIModule);
        }
        if (modules.size() == 0) {
            LOG.warn("No old style import modules defined");
        }

        Set newPlugins = ImporterManager.getInstance().getImporters();
        for (Iterator it = newPlugins.iterator(); it.hasNext();) {
            ModuleInterface mod = (ModuleInterface) it.next();
            modules.put(mod.getName(), mod);
        }
        if (modules.size() == 0) {
            throw new RuntimeException("Internal error. "
                    + "No importer modules found.");
        }

        // "Java" is a default module
        currentModule = modules.get("Java");
        if (currentModule == null) {
            throw new RuntimeException("Internal error. "
                       + "Default import module not found");
        }
    }

    /*
     * @see org.argouml.uml.reveng.ImportSettings#getImportLevel()
     */
    public abstract int getImportLevel();
    
    /**
     * If we have modified any diagrams, the project was modified and
     * should be saved. I don't consider a import, that only modifies
     * the metamodel, at this point (Andreas Rueckert).
     * Calling Project.setNeedsSave(true) doesn't work here, because
     * Project.postLoad() is called after the import and it sets the
     * needsSave flag to false.<p>
     *
     * @return true, if any diagrams where modified and the project
     * should be saved before exit.
     * @deprecated by tfmorris for 0.23.2 - use standard project
     * "save needed" mechanisms
     */
    public boolean needsSave() {
        return (diagramInterface.getModifiedDiagrams().size() > 0);
    }

    /*
     * @see org.argouml.uml.reveng.ImportSettings#getDiagramInterface()
     */
    public DiagramInterface getDiagramInterface() {
        return diagramInterface;
    }

    /**
     * Compute and cache the current diagram interface.
     */
    protected void initCurrentDiagram() {
        diagramInterface = getCurrentDiagram();
    }
    
    /**
     * Set target diagram.<p>
     *
     * @return selected diagram, if it is class diagram,
     * else return null.
     */
    private DiagramInterface getCurrentDiagram() {
        DiagramInterface result = null;
        if (Globals.curEditor().getGraphModel()
                instanceof ClassDiagramGraphModel) {
            result =  new DiagramInterface(Globals.curEditor());
        }
        return result;
    }
    
    /*
     * @see org.argouml.uml.reveng.ImportSettings#getInputSourceEncoding()
     */
    public abstract String getInputSourceEncoding();

    /*
     * @see org.argouml.uml.reveng.ImportSettings#isAttributeSelected()
     */
    public abstract boolean isAttributeSelected();
    
    /*
     * @see org.argouml.uml.reveng.ImportSettings#isDatatypeSelected()
     */
    public abstract boolean isDatatypeSelected();

    /*
     * @see org.argouml.uml.reveng.ImportSettings#getImportSession()
     */
    public ImportCommon getImportSession() {
        return this;
    }

    /**
     * Get the files. For old style modules, this asks the module for the list.
     * For new style modules we generate it ourselves based on their specified
     * file suffixes.
     * 
     * @return the list of files to be imported
     */
    protected List getFileList() {
        List files;
        if (currentModule instanceof PluggableImport) {
            // Old style importer
            PluggableImport pi = (PluggableImport) currentModule;
            if (this instanceof Import) {
                files = pi.getList((Import) this);
            } else {
                throw new RuntimeException(
                        "Old style modules only supported for Swing");
            }
        } else if (currentModule instanceof ImportInterface) {
            files = FileImportUtils.getList(
                    getSelectedFile(), isDescendSelected(),
                    ((ImportInterface) currentModule)
                            .getSuffixFilters());
            // New style importer - we did the file selection
            if (getSelectedFile().isDirectory()) {
                setSrcPath(getSelectedFile().getAbsolutePath());
            } else {
                setSrcPath(null);
            }
        } else {
            throw new RuntimeException("Unrecognized module type");
        }

        if (isChangedOnlySelected()) {
            // filter out all unchanged files
            Object model =
                ProjectManager.getManager().getCurrentProject().getModel();
            for (int i = files.size() - 1; i >= 0; i--) {
                File f = (File) files.get(i);
                String fn = f.getAbsolutePath();
                String lm = String.valueOf(f.lastModified());
                if (lm.equals(
                        Model.getFacade().getTaggedValueValue(model, fn))) {
                    files.remove(i);
                }
            }
        }

        return files;
    }

    /**
     * Set path for processed directory.
     *
     * @param path the given path
     */
    public void setSrcPath(String path) {
        srcPath = path;
    }

    /**
     * @return path for processed directory.
     */
    public String getSrcPath() {
        return srcPath;
    }

    /**
     * Parse a source file or directory.
     *
     * @param project the project
     * @param f The file to parse.
     * @exception Exception ??? 
     * TODO: Couldn't we throw a narrower exception? - tfm
     * TODO: Does this need to be public? - tfm
     */
    public void parseFile(Project project, Object f) throws Exception {
        File file = (File) f;
        if (currentModule instanceof PluggableImport) {
            // Old style importer
            PluggableImport pi = (PluggableImport) currentModule;
            // Is this file a compatible source file?
            if (pi.isParseable(file)) {
                ProjectBrowser.getInstance()
                    .showStatus("Parsing " + file.toString() + "...");
                if (this instanceof Import) {
                    pi.parseFile(project, file, diagramInterface, (Import) this);
                } else {
                    throw new RuntimeException(
                            "Old style modules only supported for Swing");
                }
            }
        } else {
            throw new RuntimeException("Unrecognized module type " 
                    + currentModule);
        }
    }

    /*
     * Create a TaggedValue with a tag/type matching our source module
     * filename and a value of the file's last modified timestamp.
     * @param project
     * @param f
     */
    private void setLastModified(Project project, File file) {
        // set the lastModified value
        String fn = file.getAbsolutePath();
        String lm = String.valueOf(file.lastModified());
        if (lm != null) {
            Model.getCoreHelper()
                .setTaggedValue(project.getModel(), fn, lm);
        }
    }

    /*
     * @see org.argouml.uml.reveng.ImportSettings#isCreateDiagramsSelected()
     */
    public abstract boolean isCreateDiagramsSelected();

    /*
     * @see org.argouml.uml.reveng.ImportSettings#isMinimiseFigsSelected()
     */
    public abstract boolean isMinimizeFigsSelected();
    
    /*
     * @see org.argouml.uml.reveng.ImportSettingsInternal#isDiagramLayoutSelected()
     */
    public abstract boolean isDiagramLayoutSelected();

    /*
     * @see org.argouml.uml.reveng.ImportSettingsInternal#isDescendSelected()
     */
    public abstract boolean isDescendSelected();

    /*
     * @see org.argouml.uml.reveng.ImportSettingsInternal#isChangedOnlySelected()
     */
    public abstract boolean isChangedOnlySelected();

    protected Hashtable getModules() {
        return modules;
    }

    protected void setSelectedFile(File file) {
        selectedFile = file;
    }

    protected File getSelectedFile() {
        return selectedFile;
    }

    protected void setCurrentModule(Object module) {
        currentModule = module;
    }

    protected Object getCurrentModule() {
        return currentModule;
    }

    /**
     * Returns the possible languages in which the user can import the sources. 
     * @return a list of Strings with the names of the languages available
     */
    public List getLanguages() {
        List languages = new ArrayList();
        Enumeration iterator = modules.keys();
        while (iterator.hasMoreElements()) {
            languages.add((iterator.nextElement()));
        }
        return languages;
    }

    /**
     * The default encoding. This should be asked by the GUI for
     * initialization.
     * @return the encoding stored in Argo.KEY_INPUT_SOURCE_ENCODING key or if
     * this is null the default system encoding
     */
    public String getEncoding() {
        String enc = Configuration.getString(Argo.KEY_INPUT_SOURCE_ENCODING); 
        if (enc == null || enc.trim().equals("")) { //$NON-NLS-1$
            enc = System.getProperty("file.encoding"); //$NON-NLS-1$
        }
        
        return enc;
    }

    /**
     * Gets the import classpaths. This should be asked by the GUI for
     * initialization.
     * @return a list with Strings representing the classpaths
     */
    public List getImportClasspath() {
        List list = new ArrayList();
        URL[] urls = ImportClassLoader.getURLs(Configuration.getString(
                Argo.KEY_USER_IMPORT_CLASSPATH, "")); //$NON-NLS-1$
        for (int i = 0; i < urls.length; i++) {
            list.add(urls[i].getFile());
        }
        return list;
    }

    
    /**
     * Layouts the diagrams.
     * 
     * @param monitor
     *            the progress meter.  Null if not progress updates desired.
     * @param startingProgress
     *            the actual progress until now
     */
    public void layoutDiagrams(ProgressMonitor monitor, int startingProgress) {
        
        // ArgoEclipse implementation
        DiagramInterface di = getDiagramInterface();
        if (di == null) {
            return;
        }
//        if (monitor != null) {
//            monitor.updateSubTask(ImportsMessages.layoutingAction);
//        }
        Vector diagrams = di.getModifiedDiagrams();
        int total = startingProgress + diagrams.size()
                / 10;
        for (int i = 0; i < diagrams.size(); i++) {
            UMLDiagram diagram = (UMLDiagram) diagrams.elementAt(i);
            ClassdiagramLayouter layouter;
            if (getCurrentModule() instanceof PluggableImport) {
                // There are no known modules which implement this,
                // but just in case ...
                layouter = 
                    ((PluggableImport) getCurrentModule()).getLayout(diagram);
            } else {
                layouter = new ClassdiagramLayouter(diagram);
            }

            layouter.layout();
            int act = startingProgress + (i + 1) / 10;
            int progress = MAX_PROGRESS_PREPARE 
                    + MAX_PROGRESS_IMPORT * act / total;
            if (monitor != null) {
                monitor.updateProgress(progress);
            }
//          iss.setValue(countFiles + (i + 1) / 10);
        }

    }


    /**
     * Import the selected source modules.
     * 
     * @param monitor
     *            a ProgressMonitor to both receive progress updates and to be
     *            polled for user requests to cancel.
     */
    protected void doImport(ProgressMonitor monitor) {
        // Roughly equivalent to and derived from old Import.doFile()
        monitor.setMaximumProgress(MAX_PROGRESS_PREPARE + MAX_PROGRESS_IMPORT);
        int progress = 0;
        monitor.updateSubTask(Translator.localize("dialog.import.preImport"));
        List files = getFileList();
        progress += MAX_PROGRESS_PREPARE;
        monitor.updateProgress(progress);
        if (files.size() == 0) {
            monitor.notifyNullAction();
            return;
        }
        Model.getPump().stopPumpingEvents();
        //#if defined(COGNITIVE)
        //@#$LPS-COGNITIVE:GranularityType:Statement
        boolean criticThreadWasOn = Designer.theDesigner().getAutoCritique();
        if (criticThreadWasOn) {
            Designer.theDesigner().setAutoCritique(false);
        }
        //#endif
        try {
            doImportInternal(files, monitor, progress);
        } finally {
            //#if defined(COGNITIVE)
            //@#$LPS-COGNITIVE:GranularityType:Statement
            if (criticThreadWasOn) {
                Designer.theDesigner().setAutoCritique(true);
            }
            //#endif
            ExplorerEventAdaptor.getInstance().structureChanged();
            Model.getPump().startPumpingEvents();
        }
    }


    /**
     * Do the import.
     * @param filesLeft the files to parse
     * @param monitor the progress meter
     * @param progress the actual progress until now
     * @throws ImportException exception thrown my import module
     */
    private void doImportInternal(List filesLeft, final ProgressMonitor monitor,
            int progress) {
        Project project =  ProjectManager.getManager().getCurrentProject();
        initCurrentDiagram();
        final StringBuffer problems = new StringBuffer();
        Collection newElements = new HashSet();
        if (currentModule instanceof ImportInterface) {
            try {
                newElements.addAll(((ImportInterface) currentModule)
                        .parseFiles(project, filesLeft, this, monitor));
            } catch (ImportException e) {
                problems.append(printToBuffer(e));
            }
            // New style importers don't create diagrams, so we'll do it
            // based on the list of newElements that they created
            if (isCreateDiagramsSelected()) {
                addFiguresToDiagrams(newElements);
            }
        } else {
            progress =
                    processFiles(
                            filesLeft, monitor, progress, project, problems);
        }
        
        // TODO: Skip layout if problems during import? 
        if (isDiagramLayoutSelected()) {
            monitor.updateMainTask(
                    Translator.localize("dialog.import.postImport"));
            monitor.updateSubTask(
                    Translator.localize("dialog.import.layoutAction"));
            layoutDiagrams(monitor, progress + filesLeft.size());
        }
        monitor.updateMainTask(Translator.localize("dialog.import.done"));
        monitor.updateSubTask(""); //$NON-NLS-1$
        monitor.updateProgress(MAX_PROGRESS_PREPARE
                + MAX_PROGRESS_IMPORT);
        if (problems != null && problems.length() > 0) {
            monitor.notifyMessage(
                    Translator.localize(
                            "dialog.title.import-problems"), //$NON-NLS-1$
                            Translator.localize(
                            "label.import-problems"),        //$NON-NLS-1$
                            problems.toString());
        } else {
            monitor.close();
        }
    }


    /**
     * Create diagram figures for a collection of model elements.
     * 
     * @param newElements
     *            the collection of elements for which figures should be
     *            created.
     */
    private void addFiguresToDiagrams(Collection newElements) {
        for (Iterator it = newElements.iterator(); it.hasNext();) {
            Object element = it.next();
            if (Model.getFacade().isAClassifier(element)
                    || Model.getFacade().isAPackage(element)) {

                Object ns = Model.getFacade().getNamespace(element);
                if (ns == null) {
                    diagramInterface.createRootClassDiagram();
                } else {
                    String packageName = getQualifiedName(ns);
                    // Select the correct diagram (implicitly creates it)
                    if (packageName != null 
                            && !packageName.equals("")) {
                        diagramInterface.selectClassDiagram(ns,
                                packageName);
                    } else {
                        diagramInterface.createRootClassDiagram();
                    }
                    // Add the element to the diagram
                    if (Model.getFacade().isAInterface(element)) {
                        diagramInterface.addInterface(element,
                                isMinimizeFigsSelected());
                    } else if (Model.getFacade().isAClass(element)) {
                        diagramInterface.addClass(element,
                                isMinimizeFigsSelected());
                    } else if (Model.getFacade().isAPackage(element)) {
                        diagramInterface.addPackage(element);
                    }
                }
            }
        }
    }

    /**
     * Return the fully qualified name of a model element in Java (dot
     * separated) format.
     * <p>
     * TODO: We really need a language independent format here. Perhaps the list
     * of names that form the hierarchy? - tfm
     */
    private String getQualifiedName(Object element) {
        StringBuffer sb = new StringBuffer();

        Object root = Model.getModelManagementFactory().getRootModel();
        
        Object ns = element;
        while (ns != null && !root.equals(ns)) {
            String name = Model.getFacade().getName(ns);
            if (name == null) {
                name = "";
            }
            sb.insert(0, name);
            ns = Model.getFacade().getNamespace(ns);
            if (ns != null && !root.equals(ns)) {
                sb.insert(0, ".");
            }
        }
        return sb.toString();
    }
        

    /*
     * This is the functional equivalent of the old Import.ImportRun.run(). It
     * used to implement a two-pass reverse engineering process as used by the
     * Java importer, but the interface has been changed so that it is now up to
     * the importers to decide what processing heuristics they want to use
     * internally.
     */
    private int processFiles(List filesLeft, final ProgressMonitor monitor,
            int progress, Project project, final StringBuffer problems) {
        int countFiles = filesLeft.size();
        int countFilesThisPass = countFiles;
        List nextPassFiles = new ArrayList();

        while (filesLeft.size() > 0) {

            File curFile = (File) filesLeft.get(0);
            filesLeft.remove(0);
    
            try {
                int tot = countFiles;
                if (getDiagramInterface() != null) {
                    tot += 
                        getDiagramInterface().getModifiedDiagrams().size() / 10;
                }
                int act = countFiles - filesLeft.size() - nextPassFiles.size();
                monitor.updateMainTask(
                        Translator.localize("dialog.import.pass1"));
                monitor.updateSubTask(Translator.localize(
                        "dialog.import.parsingAction", 
                        new Object[] {curFile.toString() }));
                parseFile(
                       project,
                        curFile);
                setLastModified(project, curFile);
                progress = MAX_PROGRESS_PREPARE + MAX_PROGRESS_IMPORT * act
                        / tot;
                monitor.updateProgress(progress);
            } catch (Exception e) {
                nextPassFiles.add(curFile);
                StringBuffer sb = new StringBuffer(80);
                if (e instanceof RuntimeException) {
                    sb.append(Translator.localize(
                            "dialog.import.runtimeException",
                            new Object[] {curFile.toString()}));
                } else {
                    sb.append(Translator.localize(
                            "dialog.import.nonRuntimeException",
                            new Object[] {curFile.toString()}));
                }
                sb.append("\n"); //$NON-NLS-1$
                sb.append(printToBuffer(e));
                problems.append(sb);
            }
    
            if (monitor.isCanceled()) {         // if the user hit cancel
                return 0;
            }
            
            if (filesLeft.size() == 0 && nextPassFiles.size() > 0
                    && nextPassFiles.size() < countFilesThisPass) {
                filesLeft = nextPassFiles;
                nextPassFiles = new ArrayList();
                countFilesThisPass = filesLeft.size();
            }
        }
        return progress;
    }


    /*
     * Print an exception trace to a string buffer
     */
    private StringBuffer printToBuffer(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new java.io.PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.getBuffer();
    }
    
}
