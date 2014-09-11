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
//#ifdef includeMusic
import lancs.mobilemedia.core.ui.controller.MusicPlayController;
//#endif
//#if includePhoto && (includeCopyMedia || includeSmsFeature || capturePhoto)
import lancs.mobilemedia.core.ui.controller.PhotoViewController;
//#endif
//#ifdef includeVideo
import lancs.mobilemedia.core.ui.controller.PlayVideoController;
//#endif
import lancs.mobilemedia.core.ui.controller.ScreenSingleton;
//#ifdef captureVideo
import lancs.mobilemedia.core.ui.controller.VideoCaptureController;
//#endif
import lancs.mobilemedia.core.ui.datamodel.AlbumData;

//#ifdef includeVideo
//[NC] Added in the scenario 08
import lancs.mobilemedia.core.ui.datamodel.VideoAlbumData;
//#endif
//#ifdef includePhoto
//[NC] Added in the scenario 07
import lancs.mobilemedia.core.ui.datamodel.ImageAlbumData;
//#endif
//#ifdef includeMusic
//[NC] Added in the scenario 07
import lancs.mobilemedia.core.ui.datamodel.MusicAlbumData;
//#endif
import lancs.mobilemedia.core.ui.screens.AlbumListScreen;
//#if captureVideo || capturePhoto
import lancs.mobilemedia.core.ui.screens.CaptureVideoScreen;
//#endif
//#ifdef includePhoto
import lancs.mobilemedia.core.ui.screens.PhotoViewScreen;
//#endif
//#ifdef includeMusic
import lancs.mobilemedia.core.ui.screens.PlayMediaScreen;
//#endif
//#ifdef includeVideo
import lancs.mobilemedia.core.ui.screens.PlayVideoScreen;
//#endif
//#if (includeMusic && includePhoto)||(includeMusic && includeVideo) || (includeVideo && includePhoto)
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
	// #ifdef includePhoto
	// [NC] Added in the scenario 07
	private BaseController imageRootController;
	private AlbumData imageModel;
	//#endif

	//	#ifdef includeMusic
	// [NC] Added in the scenario 07
	private BaseController musicRootController;
	private AlbumData musicModel;
	//#endif

	//	#ifdef includeVideo
	// [NC] Added in the scenario 08
	private BaseController videoRootController;
	private AlbumData videoModel;
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
		
		// #ifdef includeMusic
		// [NC] Added in the scenario 07
		musicModel = new MusicAlbumData();
		
		AlbumListScreen albumMusic = new AlbumListScreen();
		musicRootController = new BaseController(this, musicModel, albumMusic);
		
		MediaListController musicListController = new MediaListController(this, musicModel, albumMusic);
		musicListController.setNextController(musicRootController);
		
		AlbumController albumMusicController = new AlbumController(this, musicModel, albumMusic);
		albumMusicController.setNextController(musicListController);
		albumMusic.setCommandListener(albumMusicController);
		//#endif

		// #ifdef includeVideo
		// [NC] Added in the scenario 08

		videoModel = new VideoAlbumData();
		
		AlbumListScreen albumVideo = new AlbumListScreen();
		videoRootController = new BaseController(this, videoModel, albumVideo);
		
		MediaListController videoListController = new MediaListController(this, videoModel, albumVideo);
		videoListController.setNextController(videoRootController);
		
		AlbumController albumVideoController = new AlbumController(this, videoModel, albumVideo);
		albumVideoController.setNextController(videoListController);
		albumVideo.setCommandListener(albumVideoController);
		//#endif
		
		//#ifdef includeSmsFeature
		/* [NC] Added in scenario 06 */
		SmsReceiverController controller = new SmsReceiverController(this, imageModel, album);
		controller.setNextController(albumController);
		SmsReceiverThread smsR = new SmsReceiverThread(this, imageModel, album, controller);
		System.out.println("SmsController::Starting SMSReceiver Thread");
		new Thread(smsR).start();
		//#endif
		
		// #if (includeMusic && includePhoto)|| (includePhoto && includeVideo)
		// [NC] Added in the scenario 07
		SelectMediaController selectcontroller = new SelectMediaController(this, imageModel, album);
		selectcontroller.setNextController(imageRootController);
			
		// #ifdef includePhoto
		// [NC] Added in the scenario 08
		selectcontroller.setImageAlbumData(imageModel);
		selectcontroller.setImageController(imageRootController);
		//#endif
		
		// #ifdef includeMusic
		// [NC] Added in the scenario 08
		selectcontroller.setMusicAlbumData(musicModel);
		selectcontroller.setMusicController(musicRootController);
		//#endif
			
		// #ifdef includeVideo
		// [NC] Added in the scenario 08
		selectcontroller.setVideoAlbumData(videoModel);
		selectcontroller.setVideoController(videoRootController);
		//#endif
		
		//#elif includeMusic && includeVideo
		/*SelectMediaController*/ selectcontroller = new SelectMediaController(this, musicModel, albumMusic);
		selectcontroller.setNextController(musicRootController);
	
		// #ifdef includeMusic
		// [NC] Added in the scenario 08
		selectcontroller.setMusicAlbumData(musicModel);
		selectcontroller.setMusicController(musicRootController);
		//#endif
		
		// #ifdef includeVideo
		// [NC] Added in the scenario 08
		selectcontroller.setVideoAlbumData(videoModel);
		selectcontroller.setVideoController(videoRootController);
		//#endif
		//#endif
		
		//#if (includeMusic && includePhoto)||(includeMusic && includeVideo) || (includeVideo && includePhoto)
		SelectTypeOfMedia mainscreen = new SelectTypeOfMedia();
		mainscreen.initMenu();
		mainscreen.setCommandListener(selectcontroller);
		Display.getDisplay(this).setCurrent(mainscreen);
		ScreenSingleton.getInstance().setMainMenu(mainscreen);
		//#elif includePhoto		
		imageRootController.init(imageModel);
		//#elif includeMusic
		musicRootController.init(musicModel);
		//#elif includeVideo
		videoRootController.init(videoModel);
		//#endif
		
		
		// [Demostenes] - adicionado para executar os comandos handleCommand dos controladores e outros comandos.
		// #begin
		Command command = new Command("command",0,1);
		
		//#ifdef includePhoto
		imageRootController.commandAction(command, mainscreen);
		//#endif
		
		photoListController.commandAction(command, mainscreen);		
		albumController.commandAction(command, mainscreen);
		
		//#ifdef includeMusic
		musicRootController.commandAction(command, mainscreen);
		musicListController.commandAction(command, mainscreen);
		albumMusicController.commandAction(command, mainscreen);
		//#endif
		
		//#ifdef includeVideo
		videoRootController.commandAction(command, mainscreen);
		videoListController.commandAction(command, mainscreen);
		albumVideoController.commandAction(command, mainscreen);
		//#endif
		
		//#ifdef includeSmsFeature
		controller.commandAction(command, mainscreen);
		//#endif
		
		//#if (includeMusic && includePhoto)||(includeMusic && includeVideo) || (includeVideo && includePhoto)
		selectcontroller.commandAction(command, mainscreen);
		//#endif
					
		MediaController mediaController = new MediaController(this, imageModel, album);
		mediaController.commandAction(command, album);
		
		InputStream storedMusic = null;
		try {
			storedMusic = new FileInputStream("file");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//#ifdef includeMusic
		PlayMediaScreen pmscreen = new PlayMediaScreen(this, storedMusic , "type", musicRootController);
		MusicPlayController musicPlayController = new MusicPlayController(this, musicModel, albumMusic, pmscreen);
		musicPlayController.commandAction(command, albumMusic);
		//#endif
		
		// #if includePhoto && (includeCopyMedia || includeSmsFeature || capturePhoto)
		PhotoViewController photoViewController = new PhotoViewController(this, imageModel, album, "image name");
		photoViewController.commandAction(command, album);
		//#endif
		
		//#ifdef includeSmsFeature
		SmsSenderController smsSenderController = new SmsSenderController(this, imageModel, album, "image name");
		smsSenderController.commandAction(command, album);
		//#endif
		
		//#ifdef includeVideo
		InputStream storedVideo = null;
		PlayVideoScreen pvscreen = new PlayVideoScreen(this, storedVideo, "type", smsSenderController);
		PlayVideoController playVideoController = new PlayVideoController(this, videoModel, albumVideo, pvscreen);
		playVideoController.commandAction(command, albumVideo);		
		CaptureVideoScreen cvscreen = new CaptureVideoScreen(this, 0);
		VideoCaptureController videoCaptureController = new VideoCaptureController(this, videoModel, albumVideo, cvscreen);
		videoCaptureController.commandAction(command, albumVideo);
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