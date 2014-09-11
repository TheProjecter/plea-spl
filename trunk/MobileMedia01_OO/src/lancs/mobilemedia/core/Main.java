package lancs.mobilemedia.core;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDletStateChangeException;

import lancs.mobilemedia.core.ui.MainUIMidlet;
import lancs.mobilemedia.core.ui.controller.BaseController;
import lancs.mobilemedia.core.ui.datamodel.AlbumData;
import lancs.mobilemedia.core.ui.datamodel.ImageData;
import lancs.mobilemedia.core.ui.screens.AlbumListScreen;
import lancs.mobilemedia.core.ui.screens.PhotoViewScreen;


/**
 * @author Demóstenes Class necessária para indicar o EntryPoints do CallGraph.
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
//
//		BaseController baseController = new BaseController(midlet, albumData);			
//		baseController.init(albumData);			
//		baseController.handleCommand(c, albumListScreen);	

		try {
			midlet.startApp();
			midlet.pauseApp();
			midlet.destroyApp(false);
		} catch (MIDletStateChangeException e) {
			e.printStackTrace();
		}		

	}
}
