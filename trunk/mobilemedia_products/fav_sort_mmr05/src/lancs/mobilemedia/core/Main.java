package lancs.mobilemedia.core;

import java.io.IOException;

import javax.microedition.midlet.MIDletStateChangeException;

import lancs.mobilemedia.core.ui.MainUIMidlet;




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

//		AlbumData albumData = new AlbumData();
//		AlbumListScreen albumListScreen = new AlbumListScreen();
//		Command c = new Command("label",1,2);			
//		String type = "type";	
//		Image img = null;
//
//		BaseController baseController = new BaseController(midlet, albumData, albumListScreen);		
//
//		AlbumController albumController = new AlbumController(midlet,albumData, albumListScreen);
//		albumController.setNextController(baseController);
//
//		PhotoListController photoListController = new PhotoListController(midlet,albumData,albumListScreen);
//		photoListController.setNextController(albumController);
//
//		PhotoController photoController = new PhotoController(midlet,albumData,albumListScreen);
//		photoController.setNextController(photoListController);
//
//		PhotoViewController photoViewController = new PhotoViewController(midlet, albumData, albumListScreen, type);
//		photoViewController.setNextController(photoController);
//
//		PhotoViewScreen photoViewScreen = new PhotoViewScreen(albumData,"label");			
//		PhotoViewScreen photoViewScreen_ = new PhotoViewScreen(img ); 
//
//		SmsReceiverController smsReceiverController = new SmsReceiverController(midlet, albumData, albumListScreen);
//		smsReceiverController.setNextController(photoViewController);
//		
//		SmsSenderController smsSenderController = new SmsSenderController(midlet, albumData, albumListScreen, type);
//		smsSenderController.setNextController(smsReceiverController);
//		
//		baseController.init(albumData);
//		
//		baseController.handleCommand(c);
//		albumController.handleCommand(c);
//		photoListController.handleCommand(c);
//		photoController.handleCommand(c);
//		photoViewController.handleCommand(c);
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
