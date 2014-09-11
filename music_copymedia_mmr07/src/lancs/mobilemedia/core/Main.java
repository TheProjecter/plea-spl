package lancs.mobilemedia.core;

//import javax.microedition.midlet.MIDletStateChangeException;
import java.io.IOException;

import javax.microedition.midlet.MIDletStateChangeException;

import lancs.mobilemedia.core.ui.MainUIMidlet;



/**
 * @author Demóstenes Class necessária para indicar o EntryPoints do CallGraph.
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {
		try {
			MainUIMidlet midlet = new MainUIMidlet();

//			AlbumData albumData = new ImageAlbumData();
//			AlbumListScreen albumListScreen = new AlbumListScreen();
//			Command c = new Command("label", 1, 2);
//			InputStream storedMusic = new InputStream() {
//				
//				public int read() throws IOException {
//					// TODO Auto-generated method stub
//					return 0;
//				}
//			};
//			String type = "type";
//			List list_ = null;
//			Image img = null;
//
//			AlbumController albumController = new AlbumController(midlet,albumData, albumListScreen);
//			albumController.handleCommand(c);
//			
//			BaseController baseController = new BaseController(midlet,albumData, albumListScreen);
//			baseController.handleCommand(c);
//			
//			MediaListController mediaListController = new MediaListController(midlet, albumData, albumListScreen);
//			mediaListController.handleCommand(c);
//			
//			MediaController mediaController = new MediaController(midlet,albumData, albumListScreen);
//			mediaController.handleCommand(c);
//			
//			PlayMediaScreen pmscreen = new PlayMediaScreen(midlet, storedMusic, type, mediaController);
//			MusicPlayController musicPlayController = new MusicPlayController(midlet, albumData, albumListScreen, pmscreen);
//			musicPlayController.handleCommand(c);
//			
//			PhotoViewController photoViewController = new PhotoViewController(midlet, albumData, albumListScreen, type);
//			photoViewController.handleCommand(c);
//			
//			PhotoViewScreen photoViewScreen = new PhotoViewScreen(img);
//									
//			SelectMediaController selectMediaController = new SelectMediaController(midlet, albumData, albumListScreen);
//			selectMediaController.handleCommand(c);
//			
//			SmsReceiverController smsReceiverController = new SmsReceiverController(midlet, albumData, albumListScreen);
//			smsReceiverController.handleCommand(c);
//			
//			SmsSenderController smsSenderController = new SmsSenderController(midlet, albumData, albumListScreen, type);
//			smsSenderController.handleCommand(c);					
//			
//			InputStream storedVideo = null;
//			PlayVideoScreen pvscreen = new PlayVideoScreen(midlet, storedVideo,type, mediaController);
//			PlayVideoController playVideoController = new PlayVideoController(midlet, albumData, list_, pvscreen);
//			playVideoController.handleCommand(c);
//			
//			CaptureVideoScreen cvscreen = new CaptureVideoScreen(midlet, 0);
//			VideoCaptureController videoCaptureController = new VideoCaptureController(midlet, albumData, albumListScreen, cvscreen);
//			videoCaptureController.handleCommand(c);																					
			
			midlet.startApp();
			midlet.pauseApp();
			midlet.destroyApp(false);
		} catch (MIDletStateChangeException e) {
			e.printStackTrace();
		}

	}
}
