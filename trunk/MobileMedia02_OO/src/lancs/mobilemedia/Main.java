package lancs.mobilemedia;

import java.io.IOException;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDletStateChangeException;

import lancs.mobilemedia.core.ui.MainUIMidlet;
import lancs.mobilemedia.core.ui.controller.BaseController;
import lancs.mobilemedia.core.ui.controller.PhotoController;
import lancs.mobilemedia.core.ui.datamodel.AlbumData;
import lancs.mobilemedia.core.ui.datamodel.ImageData;
import lancs.mobilemedia.core.ui.screens.AlbumListScreen;
import lancs.mobilemedia.core.ui.screens.PhotoViewScreen;


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
//		Command c = new Command("label", 1, 2);
//		ImageData image = new ImageData(1, "parentAlbum", "image");
//		Image img = null;
//
//		BaseController baseController = new BaseController(midlet, albumData);		
//		baseController.init(albumData);
//		baseController.handleCommand(c, albumListScreen);
//
//		PhotoController photoController = new PhotoController(image,baseController);		
//		photoController.handleCommand(c, albumListScreen);		
//
//		PhotoViewScreen photoViewScreen = new PhotoViewScreen(albumData,"label");	
//		PhotoViewScreen photoViewScreen_ = new PhotoViewScreen(img);

		try {
			midlet.startApp();
			midlet.pauseApp();
			midlet.destroyApp(false);
		} catch (MIDletStateChangeException e) {
			e.printStackTrace();
		}		
		
	}
}
