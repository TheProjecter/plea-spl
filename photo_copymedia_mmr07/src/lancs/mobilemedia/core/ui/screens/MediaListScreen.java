/*
 * Created on Sep 13, 2004
 *
 */
package lancs.mobilemedia.core.ui.screens;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.List;


/**
 * @author trevor
 *
 * This screen shows a listing of all photos for a selected photo album.
 * This is the screen that contains most of the feature menu items. 
 * From this screen, a user can choose to view photos, add or delete photos,
 * send photos to other users etc.
 * 
 */
public class MediaListScreen extends List {
	
	//Add the core application commands always
	
	// [NC] Added in the scenario 07: to support more than one screen purpose
	public static final int SHOWPHOTO = 1;
	public static final int PLAYMUSIC = 2;
	public static final int PLAYVIDEO = 3;
	
	// #ifdef includePhoto
	// [NC] Added in the scenario 07
	public static final Command viewCommand = new Command("View", Command.ITEM, 1);
	//#endif
	
	public static final Command addCommand = new Command("Add", Command.ITEM, 1);
	public static final Command deleteCommand = new Command("Delete", Command.ITEM, 1);
	public static final Command backCommand = new Command("Back", Command.BACK, 0);

	// [EF] Added in the scenario 02 
	public static final Command editLabelCommand = new Command("Edit Label", Command.ITEM, 1);
	
	/**
     * Constructor
     */
	private int typeOfScreen;
	
	public MediaListScreen(int typeOfScreen) {
		super("Choose Items", Choice.IMPLICIT);
		this.typeOfScreen = typeOfScreen;
	}
	
	/**
	 * Initialize the menu items for this screen
	 */
	public void initMenu() {
		//Add the core application commands always
		// [NC] Added in the scenario 07: to support more than one screen purpose
		// #ifdef includePhoto
		// [NC] Added in the scenario 07
		if (typeOfScreen == SHOWPHOTO)
			this.addCommand(viewCommand);
		//#endif
		
		this.addCommand(addCommand);
		this.addCommand(deleteCommand);
		
		// [EF] Added in the scenario 02 
		this.addCommand(editLabelCommand);
		
		this.addCommand(backCommand);
	}
}
