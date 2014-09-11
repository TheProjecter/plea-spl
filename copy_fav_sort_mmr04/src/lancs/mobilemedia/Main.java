package lancs.mobilemedia;

import java.io.IOException;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDletStateChangeException;

import lancs.mobilemedia.core.ui.MainUIMidlet;
import lancs.mobilemedia.core.ui.controller.AlbumController;
import lancs.mobilemedia.core.ui.controller.BaseController;
import lancs.mobilemedia.core.ui.controller.PhotoController;
import lancs.mobilemedia.core.ui.controller.PhotoListController;
import lancs.mobilemedia.core.ui.controller.PhotoViewController;
import lancs.mobilemedia.core.ui.datamodel.AlbumData;
import lancs.mobilemedia.core.ui.datamodel.ImageData;
import lancs.mobilemedia.core.ui.screens.AlbumListScreen;
import lancs.mobilemedia.core.ui.screens.PhotoViewScreen;



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
//		ImageData image = new ImageData(1, "parentAlbum", "image");
//		Image img = null;
//
//		BaseController baseController = new BaseController(midlet, albumData, albumListScreen);
//				
//		PhotoListController photoListController = new PhotoListController(midlet, albumData, albumListScreen);				
//		photoListController.setNextController(baseController);
//		
//		PhotoController photoController = new PhotoController(midlet, albumData, albumListScreen); 			
//		photoController.setNextController(photoListController);
//
//		PhotoViewController photoViewController = new PhotoViewController(midlet, albumData, albumListScreen, "imagemName");
//		photoViewController.setNextController(photoController);
//
//		AlbumController albumController = new AlbumController(midlet, albumData, albumListScreen);
//		albumController.setNextController(photoViewController);
//						
//		PhotoViewScreen photoViewScreen = new PhotoViewScreen(albumData,"label");
//		PhotoViewScreen photoViewScreen_ = new PhotoViewScreen(img);
//		
//		baseController.init(albumData);
//		
//		baseController.handleCommand(c);
//		photoListController.handleCommand(c);
//		photoController.handleCommand(c);
//		photoViewController.handleCommand(c);
//		albumController.handleCommand(c);
		
		try {
			midlet.startApp();
			midlet.pauseApp();
			midlet.destroyApp(false);
		} catch (MIDletStateChangeException e) {
			e.printStackTrace();
		}		
	}

}
