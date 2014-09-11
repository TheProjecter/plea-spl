package lancs.mobilemedia.core.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import lancs.mobilemedia.core.ui.controller.AlbumController;
import lancs.mobilemedia.core.ui.controller.BaseController;
import lancs.mobilemedia.core.ui.controller.PhotoController;
import lancs.mobilemedia.core.ui.controller.PhotoListController;
import lancs.mobilemedia.core.ui.controller.PhotoViewController;
import lancs.mobilemedia.core.ui.datamodel.AlbumData;
import lancs.mobilemedia.core.ui.screens.AlbumListScreen;
import lancs.mobilemedia.core.ui.screens.PhotoViewScreen;


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
	private BaseController rootController;

	//Model (M v c)
	private AlbumData model;

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
		model = new AlbumData();
		AlbumListScreen album = new AlbumListScreen();
		rootController = new BaseController(this, model, album);
		
		// [EF] Add in scenario 04: initialize sub-controllers
		PhotoListController photoListController = new PhotoListController(this, model, album);
		photoListController.setNextController(rootController);
		
		AlbumController albumController = new AlbumController(this, model, album);
		albumController.setNextController(photoListController);
		album.setCommandListener(albumController);
		
		//Only the first (last?) controller needs to be initialized (?)
		rootController.init(model);
		
		//[Demostenes] - comandos adicionados para executar o handleCommand de cada controlador e outros comandos.
		Command command = new Command("command",0,1);																					
				
		rootController.commandAction(command, album);
		photoListController.commandAction(command, album);
		albumController.commandAction(command, album);
				
		PhotoController photoController = new PhotoController(this, model, album); 			
		photoController.commandAction(command, album);

		PhotoViewController photoViewController = new PhotoViewController(this, model, album, "imagemName");
		photoViewController.commandAction(command, album);

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