package lancs.mobilemedia.core.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import lancs.mobilemedia.core.ui.controller.AlbumController;
import lancs.mobilemedia.core.ui.controller.BaseController;
import lancs.mobilemedia.core.ui.controller.MediaController;
import lancs.mobilemedia.core.ui.controller.MediaListController;
//#endif
import lancs.mobilemedia.core.ui.controller.ScreenSingleton;
//#endif
import lancs.mobilemedia.core.ui.datamodel.AlbumData;

//#endif
//#ifdef includePhoto
//[NC] Added in the scenario 07
import lancs.mobilemedia.core.ui.datamodel.ImageAlbumData;
//#endif
import lancs.mobilemedia.core.ui.screens.AlbumListScreen;
//#endif
//#ifdef includePhoto
import lancs.mobilemedia.core.ui.screens.PhotoViewScreen;
//#endif
//#if (includeMusic && includePhoto)||(includeMusic && includeVideo) || (includeVideo && includePhoto)
//[NC] Added in the scenario 07
import lancs.mobilemedia.core.ui.controller.SelectMediaController;
import lancs.mobilemedia.core.ui.screens.SelectTypeOfMedia;
//#endif

//Following are pre-processor statements to include the required
//classes for device specific features. They must be commented out
//if they aren't used, otherwise it will throw exceptions trying to
//load classes that aren't available for a given platform.

/* 
 * @author trevor
 *
 * This is the main Midlet class for the core J2ME application
 * It contains all the basic functionality that should be executable
 * in any standard J2ME device that supports MIDP 1.0 or higher. 
 * Any additional J2ME features for this application that are dependent
 * upon a particular device (ie. optional or proprietary library) are
 * de-coupled from the core application so they can be conditionally included
 * depending on the target platform 
 * 
 * This Application provides a basic Photo Album interface that allows a user to view
 * images on their mobile device. 
 * */
public class MainUIMidlet extends MIDlet {

	//(m v C) Controller
	// #ifdef includePhoto
	// [NC] Added in the scenario 07
	private BaseController imageRootController;
	private AlbumData imageModel;
	//#endif

	/**
	 * Constructor -
	 */
	public MainUIMidlet() {
	    //do nothing
	}

	/**
	 * Start the MIDlet by creating new model and controller classes, and
	 * initialize them as necessary
	 */
	public void startApp() throws MIDletStateChangeException {
		// #ifdef includePhoto
		// [NC] Added in the scenario 07
		imageModel = new ImageAlbumData();

		AlbumListScreen album = new AlbumListScreen();
		imageRootController = new BaseController(this, imageModel, album);
		
		// [EF] Add in scenario 04: initialize sub-controllers
		MediaListController photoListController = new MediaListController(this, imageModel, album);
		photoListController.setNextController(imageRootController);
		
		AlbumController albumController = new AlbumController(this, imageModel, album);
		albumController.setNextController(photoListController);
		album.setCommandListener(albumController);
		//#endif
		
		//#if (includeMusic && includePhoto)||(includeMusic && includeVideo) || (includeVideo && includePhoto)
		SelectTypeOfMedia mainscreen = new SelectTypeOfMedia();
//		mainscreen.initMenu();
//		mainscreen.setCommandListener(selectcontroller);
//		Display.getDisplay(this).setCurrent(mainscreen);
//		ScreenSingleton.getInstance().setMainMenu(mainscreen);
//		//#elif includePhoto		
		imageRootController.init(imageModel);
		// [Demostenes] - adicionado para executar os comandos handleCommand dos controladores e outros comandos.
		// #begin
		Command command = new Command("command",0,1);
		
		//#ifdef includePhoto
		imageRootController.commandAction(command, mainscreen);
		//#endif
		
		photoListController.commandAction(command, mainscreen);		
		albumController.commandAction(command, mainscreen);
		
//		//#if (includeMusic && includePhoto)||(includeMusic && includeVideo) || (includeVideo && includePhoto)
//		selectcontroller.commandAction(command, mainscreen);
//		//#endif
					
		MediaController mediaController = new MediaController(this, imageModel, album);
		mediaController.commandAction(command, album);
		
		InputStream storedMusic = null;
		try {
			storedMusic = new FileInputStream("file");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// #end
		
		
	}

	/**
	 * Pause the MIDlet
	 * This method does nothing at the moment.
	 */
	public void pauseApp() {
		//do nothing
	}

	/**
	 * Destroy the MIDlet
	 */
	public void destroyApp(boolean unconditional) {
		notifyDestroyed();
	}
}