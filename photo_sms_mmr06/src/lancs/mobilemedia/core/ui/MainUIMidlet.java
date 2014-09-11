package lancs.mobilemedia.core.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import lancs.mobilemedia.core.ui.controller.AlbumController;
import lancs.mobilemedia.core.ui.controller.BaseController;
import lancs.mobilemedia.core.ui.controller.MediaController;
import lancs.mobilemedia.core.ui.controller.MediaListController;
//#endif
//#if includeCopyPhoto || includeSmsFeature
import lancs.mobilemedia.core.ui.controller.PhotoViewController;
//#endif
import lancs.mobilemedia.core.ui.controller.ScreenSingleton;
import lancs.mobilemedia.core.ui.datamodel.AlbumData;
//#ifdef includePhotoAlbum
//[NC] Added in the scenario 07
import lancs.mobilemedia.core.ui.datamodel.ImageAlbumData;
//#endif
import lancs.mobilemedia.core.ui.screens.AlbumListScreen;
//#ifdef includeSmsFeature
import lancs.mobilemedia.sms.SmsReceiverController;
import lancs.mobilemedia.sms.SmsReceiverThread;
import lancs.mobilemedia.sms.SmsSenderController;
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
	// #ifdef includePhotoAlbum
	// [NC] Added in the scenario 07
	private BaseController imageRootController;
	//#endif

	//Model (M v c)
	// #ifdef includePhotoAlbum
	// [NC] Added in the scenario 07
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
		
		// #ifdef includePhotoAlbum
		// [NC] Added in the scenario 07
		imageModel = new ImageAlbumData();
		//#endif
		
		// #ifdef includePhotoAlbum
		// [NC] Added in the scenario 07
		AlbumListScreen album = new AlbumListScreen();
		imageRootController = new BaseController(this, imageModel, album);								
		
		// [EF] Add in scenario 04: initialize sub-controllers
		MediaListController photoListController = new MediaListController(this, imageModel, album);
		photoListController.setNextController(imageRootController);
		
		AlbumController albumController = new AlbumController(this, imageModel, album);
		albumController.setNextController(photoListController);
		album.setCommandListener(albumController);
		//#endif
		
		
		//#ifdef includeSmsFeature
		/* [NC] Added in scenario 06 */
		SmsReceiverController controller;
		SmsReceiverThread smsR;
		
		//#ifdef Photo && NotMusic
		controller = new SmsReceiverController(this, imageModel, album);
		controller.setNextController(albumController);
		smsR = new SmsReceiverThread(this, imageModel, album, controller);
		//#endif
		
		System.out.println("SmsController::Starting SMSReceiver Thread");
		new Thread(smsR).start();
		//#endif

		
		
		//#elif includePhotoAlbum
		imageRootController.init(imageModel);
		// [Demostenes] - Comandos adicionados para executar o handleCommand de cada Controller e outros comandos.
		// #begin
		Command command = new Command("exitCommand",0,1);
		
		//#ifdef includePhotoalbum
		imageRootController.commandAction(command, album);
		//#endif
		
		//#ifdef includePhotoAlbum
		photoListController.commandAction(command, album);
		albumController.commandAction(command, album);
		//#endif
		
		//#ifdef includeSmsFeature && Photo		
		controller.commandAction(command, album);
		//#endif	
				
		MediaController mediaController;		
		//#ifdef Photo && NotMusic
		mediaController = new MediaController(this, imageModel, album);
		//#endif
		
		PhotoViewController photoViewController;
		//#if (includeCopyPhoto || includeSmsFeature) && Photo && NotMusic
		photoViewController = new PhotoViewController(this, imageModel, album, "imageName");
		photoViewController.setNextController(mediaController);
		//#endif
		
		//#ifdef includeSmsFeature && Photo && NotMusic
		SmsSenderController smsSenderController = new SmsSenderController(this, imageModel, album, "imageName");
		smsSenderController.setNextController(photoViewController);
		
		SmsReceiverController smsReceiverController = new SmsReceiverController(this, imageModel, album);
		//#endif
		
		InputStream storedMusic = null;
		try {
			storedMusic = new FileInputStream("file");
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		
		//#ifdef Photo && NotMusic
		mediaController.commandAction(command, album);
		//#endif
		
		//#if includeCopyPhoto || includeSmsFeature
		photoViewController.commandAction(command, album);
		//#endif
		
		//#ifdef includeSmsFeature
		smsSenderController.commandAction(command, album);
		smsReceiverController.commandAction(command, album);
		//#endif
		
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