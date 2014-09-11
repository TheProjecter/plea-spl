package lancs.mobilemedia.core;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDletStateChangeException;

import lancs.mobilemedia.core.ui.MainUIMidlet;
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
//#if includeMusic && includePhotoAlbum
import lancs.mobilemedia.core.ui.controller.SelectMediaController;
//#endif
import lancs.mobilemedia.core.ui.datamodel.AlbumData;
//#ifdef includePhotoAlbum
import lancs.mobilemedia.core.ui.datamodel.ImageAlbumData;
//#endif
//#ifdef includeMusic
import lancs.mobilemedia.core.ui.datamodel.MusicAlbumData;
//#endif
import lancs.mobilemedia.core.ui.screens.AlbumListScreen;
//#ifdef includePhotoAlbum
import lancs.mobilemedia.core.ui.screens.PhotoViewScreen;
//#endif
//#ifdef includeMusic
import lancs.mobilemedia.core.ui.screens.PlayMediaScreen;

/**
 * @author Demóstenes
 * Class necessária para indicar o EntryPoints do CallGraph.
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {

		MainUIMidlet midlet = new MainUIMidlet();

//		ImageAlbumData imageData = new ImageAlbumData();
//		MusicAlbumData musicData = new MusicAlbumData(); 
//
//		AlbumListScreen albumListScreen = new AlbumListScreen();
//		AlbumListScreen albumMusic = new AlbumListScreen();
//		Command c = new Command("label",1,2);
//		InputStream storedMusic = new InputStream() {
//
//			public int read() throws IOException {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//		};
//		String type = "type";
//		List list_ = null;
//		Image img = null;				
//
//		//#ifdef Photo
//		BaseController imageRootController = new BaseController(midlet, imageData, albumListScreen);		
//		
//		AlbumController albumController = new AlbumController(midlet,imageData, albumListScreen);
//		albumController.setNextController(imageRootController);
//
//		MediaListController photoListController = new MediaListController(midlet, imageData, albumListScreen);
//		photoListController.setNextController(albumController);
//
//		MediaController mediaController = new MediaController(midlet, imageData, albumListScreen);
//		mediaController.setNextController(photoListController);
//		
//		PhotoViewController photoViewController = new PhotoViewController(midlet, imageData, albumListScreen, type);
//		photoViewController.setNextController(mediaController);
//
//		PhotoViewScreen photoViewScreen = new PhotoViewScreen(img);			
//		//#endif
//		
//		//#ifdef Music
//		BaseController musicRootController = new BaseController(midlet, musicData, albumListScreen);	
//		
//		PlayMediaScreen pmscreen = new PlayMediaScreen(midlet, storedMusic , type , mediaController);
//		MusicPlayController musicPlayController = new MusicPlayController(midlet, musicData, albumListScreen, pmscreen);
//		musicPlayController.setNextController(mediaController);
//
//		//#endif
//		
//
//		SelectMediaController selectMediaController = new SelectMediaController(midlet, imageData, imageData, list_, imageRootController, imageRootController);
//		selectMediaController.setNextController(photoViewController);
//
//		SmsReceiverController smsReceiverController = new SmsReceiverController(midlet, imageData, albumListScreen);
//		smsReceiverController.setNextController(selectMediaController);
//
//		SmsSenderController smsSenderController = new SmsSenderController(midlet, imageData, albumListScreen, type);
//		smsSenderController.setNextController(smsReceiverController);
//		
//		imageRootController.init(imageData);
//		
//		imageRootController.handleCommand(c);
//		albumController.handleCommand(c);	
//		photoListController.handleCommand(c);
//		mediaController.handleCommand(c);
//		musicPlayController.handleCommand(c);
//		photoViewController.handleCommand(c);
//		selectMediaController.handleCommand(c);
//		smsReceiverController.handleCommand(c);
//		smsSenderController.handleCommand(c);
		
		try {
			midlet.startApp();
			midlet.pauseApp();
			midlet.destroyApp(false);
		} catch (MIDletStateChangeException e) {
			e.printStackTrace();
		}				
		
	}
}
