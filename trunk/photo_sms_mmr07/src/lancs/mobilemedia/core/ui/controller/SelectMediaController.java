// #if (includeMusic && includePhoto)||(includeMusic && includeVideo) || (includeVideo && includePhoto)
// [NC] Added in the scenario 07 and changed in scenario 8

package lancs.mobilemedia.core.ui.controller;

import javax.microedition.lcdui.Command;

import javax.microedition.lcdui.Display;

import javax.microedition.lcdui.List;

import lancs.mobilemedia.core.ui.MainUIMidlet;
import lancs.mobilemedia.core.ui.datamodel.AlbumData;


public class SelectMediaController extends AbstractController {
	
	// #ifdef includePhoto
	// [NC] Added in the scenario 08
	BaseController imageController;
	AlbumData imageAlbumData;
	//#endif
	
	// [NC] Changed in the scenario 08: to be more variable
	public SelectMediaController(MainUIMidlet midlet, AlbumData imageAlbumData,	List albumListScreen) {
		super(midlet, imageAlbumData, albumListScreen);
	}
	
	// #ifdef includePhoto
	// [NC] Added in the scenario 08
	public BaseController getImageController() {
		return imageController;
	}

	public void setImageController(BaseController imageController) {
		this.imageController = imageController;
	}

	public AlbumData getImageAlbumData() {
		return imageAlbumData;
	}

	public void setImageAlbumData(AlbumData imageAlbumData) {
		this.imageAlbumData = imageAlbumData;
	}
	//#endif

	public boolean handleCommand(Command command) {
		String label = command.getLabel();
      	System.out.println( "<* SelectMediaController.handleCommand() *>: " + label);
		
      	if (label.equals("Select")) {
 			List down = (List) Display.getDisplay(midlet).getCurrent();
 			// #ifdef includePhoto
 			// [NC] Added in the scenario 08
 			if (down.getString(down.getSelectedIndex()).equals("Photos"))
 				imageController.init(imageAlbumData);
 			//#endif
 			
 			return true;	
      	}
      	return false;
	}
}
//#endif