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
//#ifdef includeMusic
import lancs.mobilemedia.core.ui.controller.MusicPlayController;
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
//#ifdef includeMusic
//[NC] Added in the scenario 07
import lancs.mobilemedia.core.ui.datamodel.MusicAlbumData;
//#endif
import lancs.mobilemedia.core.ui.screens.AlbumListScreen;
//#ifdef includeMusic
import lancs.mobilemedia.core.ui.screens.PlayMediaScreen;
//#endif
//#if includeMusic && includePhotoAlbum
//[NC] Added in the scenario 07
import lancs.mobilemedia.core.ui.controller.SelectMediaController;
import lancs.mobilemedia.core.ui.screens.SelectTypeOfMedia;
//#endif

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

	//	#ifdef includeMusic
	// [NC] Added in the scenario 07
	private BaseController musicRootController;
	//#endif
	
	//Model (M v c)
	// #ifdef includePhotoAlbum
	// [NC] Added in the scenario 07
	private AlbumData imageModel;
	//#endif
	
	//	#ifdef includeMusic
	// [NC] Added in the scenario 07
	private AlbumData musicModel;
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
		
		//	#ifdef includeMusic
		// [NC] Added in the scenario 07
		musicModel = new MusicAlbumData();
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
		
		
		// #ifdef includeMusic
		// [NC] Added in the scenario 07
		AlbumListScreen albumMusic = new AlbumListScreen();
		musicRootController = new BaseController(this, musicModel, albumMusic);
		
		MediaListController musicListController = new MediaListController(this, musicModel, albumMusic);
		musicListController.setNextController(musicRootController);
		
		AlbumController albumMusicController = new AlbumController(this, musicModel, albumMusic);
		albumMusicController.setNextController(musicListController);
		albumMusic.setCommandListener(albumMusicController);
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
		
		//#ifdef Music
		controller = new SmsReceiverController(this, musicModel, albumMusic);
		controller.setNextController(albumMusicController);
		smsR = new SmsReceiverThread(this, musicModel, albumMusic, controller);
		//#endif
		
		System.out.println("SmsController::Starting SMSReceiver Thread");
		new Thread(smsR).start();
		//#endif

		
		
		// #if includeMusic && includePhotoAlbum
		// [NC] Added in the scenario 07
		SelectMediaController selectcontroller = new SelectMediaController(this, imageModel, musicModel, album,imageRootController,musicRootController);
		selectcontroller.setNextController(imageRootController);
		
		SelectTypeOfMedia mainscreen = new SelectTypeOfMedia();
		mainscreen.initMenu();
		mainscreen.setCommandListener(selectcontroller);
		Display.getDisplay(this).setCurrent(mainscreen);
		ScreenSingleton.getInstance().setMainMenu(mainscreen);
		//#elif includePhotoAlbum
		imageRootController.init(imageModel);
		//#elif includeMusic
		musicRootController.init(musicModel);
		//#endif	
						
		// [Demostenes] - Comandos adicionados para executar o handleCommand de cada Controller e outros comandos.
		// #begin
		Command command = new Command("exitCommand",0,1);
		
		//#ifdef includePhotoalbum
		imageRootController.commandAction(command, album);
		//#endif
		
		//#ifdef includeMusic
		musicRootController.commandAction(command, albumMusic);
		//#endif
		
		//#ifdef includePhotoAlbum
		photoListController.commandAction(command, album);
		albumController.commandAction(command, album);
		//#endif
		
		//#ifdef includeMusic
		musicListController.commandAction(command, albumMusic);
		albumMusicController.commandAction(command, albumMusic);
		//#endif
		
		//#ifdef includeSmsFeature && Music && NotPhoto		
		controller.commandAction(command, albumMusic);
		//#endif
		
		//#ifdef includeSmsFeature && Photo		
		controller.commandAction(command, album);
		//#endif	
				
		//#ifdef includeMusic && includePhotoAlbum
		selectcontroller.commandAction(command, mainscreen);
		//#endif
		
		MediaController mediaController;		
		//#ifdef Photo && NotMusic
		mediaController = new MediaController(this, imageModel, album);
		//#endif
		
		//#ifdef includeMusic
		mediaController = new MediaController(this, musicModel, albumMusic);
		//#endif
		
		//#ifdef includeMusic && includePhotoAlbum
		mediaController.setNextController(selectcontroller);
		//#endif

		PhotoViewController photoViewController;
		//#if (includeCopyPhoto || includeSmsFeature) && Photo && NotMusic
		photoViewController = new PhotoViewController(this, imageModel, album, "imageName");
		photoViewController.setNextController(mediaController);
		//#endif
		
		//#if (includeCopyPhoto || includeSmsFeature) && Music
		photoViewController = new PhotoViewController(this, musicModel, albumMusic, "imageName");
		photoViewController.setNextController(mediaController);
		//#endif

		//#ifdef includeSmsFeature && Photo && NotMusic
		SmsSenderController smsSenderController = new SmsSenderController(this, imageModel, album, "imageName");
		smsSenderController.setNextController(photoViewController);
		
		SmsReceiverController smsReceiverController = new SmsReceiverController(this, imageModel, album);
		//#endif
		
		//#ifdef includeSmsFeature && Music
		smsSenderController = new SmsSenderController(this, musicModel, albumMusic, "imageName");
		smsSenderController.setNextController(photoViewController);
		
		smsReceiverController = new SmsReceiverController(this, musicModel, albumMusic);
		//#endif
				
		InputStream storedMusic = null;
		try {
			storedMusic = new FileInputStream("file");
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		
		//#ifdef includeMusic
		PlayMediaScreen pmscreen = new PlayMediaScreen(this, storedMusic , "type", musicRootController);
		MusicPlayController musicPlayController = new MusicPlayController(this, musicModel, albumMusic, pmscreen );
		//#endif

		//#ifdef Photo && NotMusic
		mediaController.commandAction(command, album);
		//#endif
		
		//#ifdef Photo && Music
		mediaController.commandAction(command, mainscreen);
		//#endif
		
		//#if includeCopyPhoto || includeSmsFeature
		photoViewController.commandAction(command, album);
		//#endif
		
		//#ifdef includeSmsFeature
		smsSenderController.commandAction(command, album);
		smsReceiverController.commandAction(command, album);
		//#endif
		
		//#ifdef includeMusic
		musicPlayController.commandAction(command, albumMusic);
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