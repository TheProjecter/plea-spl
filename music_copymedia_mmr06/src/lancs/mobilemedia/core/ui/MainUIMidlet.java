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
//#endif
//#ifdef includeMusic
//[NC] Added in the scenario 07
import lancs.mobilemedia.core.ui.datamodel.MusicAlbumData;
//#endif
import lancs.mobilemedia.core.ui.screens.AlbumListScreen;
//#ifdef includeMusic
import lancs.mobilemedia.core.ui.screens.PlayMediaScreen;

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

	//	#ifdef includeMusic
	// [NC] Added in the scenario 07
	private BaseController musicRootController;
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
		
		//	#ifdef includeMusic
		// [NC] Added in the scenario 07
		musicModel = new MusicAlbumData();
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
		
		//#elif includeMusic
		musicRootController.init(musicModel);
		//#endif	
						
		// [Demostenes] - Comandos adicionados para executar o handleCommand de cada Controller e outros comandos.
		// #begin
		Command command = new Command("exitCommand",0,1);
		
		//#ifdef includeMusic
		musicRootController.commandAction(command, albumMusic);
		//#endif
		
		//#ifdef includeMusic
		musicListController.commandAction(command, albumMusic);
		albumMusicController.commandAction(command, albumMusic);
		//#endif
		
		MediaController mediaController;		
		//#ifdef includeMusic
		mediaController = new MediaController(this, musicModel, albumMusic);
		//#endif
		
		PhotoViewController photoViewController;
		//#if (includeCopyPhoto || includeSmsFeature) && Music
		photoViewController = new PhotoViewController(this, musicModel, albumMusic, "imageName");
		photoViewController.setNextController(mediaController);
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

//		//#if includeCopyPhoto || includeSmsFeature
//		photoViewController.commandAction(command, album);
//		//#endif
		
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