/*
 * Lancaster University
 * Computing Department
 * 
 * Created by Eduardo Figueiredo
 * Date: 17 Jun 2007
 * 
 */
package lancs.mobilemedia.core.ui.controller;


import java.io.InputStream;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;

import javax.microedition.lcdui.List;
import javax.microedition.rms.RecordStoreFullException;

import lancs.mobilemedia.lib.exceptions.ImageNotFoundException;
import lancs.mobilemedia.lib.exceptions.ImagePathNotValidException;
import lancs.mobilemedia.lib.exceptions.InvalidImageDataException;
import lancs.mobilemedia.lib.exceptions.PersistenceMechanismException;
import lancs.mobilemedia.core.ui.MainUIMidlet;
import lancs.mobilemedia.core.ui.datamodel.AlbumData;

//#ifdef includePhotoAlbum
//[NC] Added in the scenario 07
import lancs.mobilemedia.core.ui.datamodel.ImageAlbumData;
import javax.microedition.lcdui.Image;
//#endif
import lancs.mobilemedia.core.ui.datamodel.MediaData;
//#endif
import lancs.mobilemedia.core.ui.screens.AddMediaToAlbum;
import lancs.mobilemedia.core.ui.screens.AlbumListScreen;
import lancs.mobilemedia.core.ui.screens.NewLabelScreen;

//#ifdef includePhotoAlbum
//[NC] Added in the scenario 07
import lancs.mobilemedia.core.ui.screens.PhotoViewScreen;
//#endif
import lancs.mobilemedia.core.util.Constants;

//#ifdef includeSmsFeature
import lancs.mobilemedia.sms.SmsSenderController;
//#endif

/**
 * @author Eduardo Figueiredo
 * Added in the Scenario 02
 */
public class MediaController extends MediaListController {

	private MediaData media;
	private NewLabelScreen screen;

	public MediaController (MainUIMidlet midlet, AlbumData albumData, AlbumListScreen albumListScreen) {
		super(midlet, albumData, albumListScreen);
	}

	public boolean handleCommand(Command command) {
		String label = command.getLabel();
		System.out.println( "<* PhotoController.handleCommand() *> " + label);
		
		/** Case: Save Add photo * */
		if (label.equals("Add")) {
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.ADDPHOTOTOALBUM_SCREEN);
			AddMediaToAlbum form = new AddMediaToAlbum("Add new item to Album");
			form.setCommandListener(this);
			setCurrentScreen(form);
			return true;
		
		}
		// #ifdef includePhotoAlbum
		// [NC] Added in the scenario 07
		else if (label.equals("View")) {
			String selectedImageName = getSelectedMediaName();
			showImage(selectedImageName);
			//#endif
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGE_SCREEN);
			return true;
		}
		//#endif
		
		// #ifdef includeMusic
		// [NC] Added in the scenario 07
		else if (label.equals("Play")) {
		} else if (label.equals("Save Item")) {
			try {
				getAlbumData().addNewMediaToAlbum(((AddMediaToAlbum) getCurrentScreen()).getItemName(), 
						((AddMediaToAlbum) getCurrentScreen()).getPath(), getCurrentStoreName());

			} catch (InvalidImageDataException e) {
				Alert alert = null;
				if (e instanceof ImagePathNotValidException)
					alert = new Alert("Error", "The path is not valid", null, AlertType.ERROR);
				else
					alert = new Alert("Error", "The file format is not valid", null, AlertType.ERROR);
				Display.getDisplay(midlet).setCurrent(alert, Display.getDisplay(midlet).getCurrent());
				return true;
				// alert.setTimeout(5000);
			} catch (PersistenceMechanismException e) {
				Alert alert = null;
				if (e.getCause() instanceof RecordStoreFullException)
					alert = new Alert("Error", "The mobile database is full", null, AlertType.ERROR);
				else
					alert = new Alert("Error", "The mobile database can not add a new photo", null, AlertType.ERROR);
				Display.getDisplay(midlet).setCurrent(alert, Display.getDisplay(midlet).getCurrent());
			}
			//#endif
			return goToPreviousScreen();
			/** Case: Delete selected Photo from recordstore * */
		} else if (label.equals("Delete")) {
			String selectedMediaName = getSelectedMediaName();
			try {
				getAlbumData().deleteMedia(getCurrentStoreName(), selectedMediaName);
			} catch (PersistenceMechanismException e) {
				Alert alert = new Alert("Error", "The mobile database can not delete this item", null, AlertType.ERROR);
				Display.getDisplay(midlet).setCurrent(alert, Display.getDisplay(midlet).getCurrent());
				return true;
			} catch (ImageNotFoundException e) {
				Alert alert = new Alert("Error", "The selected item was not found in the mobile device", null, AlertType.ERROR);
				Display.getDisplay(midlet).setCurrent(alert, Display.getDisplay(midlet).getCurrent());
				return true;
			}
			showMediaList(getCurrentStoreName(), false, false);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
			return true;

		/** Case: Edit photo label
		 *  [EF] Added in the scenario 02 */
		} else if (label.equals("Edit Label")) {
			String selectedImageName = getSelectedMediaName();
			try {
				media = getAlbumData().getMediaInfo(
						selectedImageName);
				// PhotoController photoController = new PhotoController(image,
				// this);
				NewLabelScreen formScreen = new NewLabelScreen(
						"Edit Label Item", NewLabelScreen.LABEL_PHOTO);
				formScreen.setCommandListener(this);
				this.setScreen(formScreen);
				setCurrentScreen(formScreen);
				formScreen = null;
			} catch (ImageNotFoundException e) {
				Alert alert = new Alert(
						"Error",
						"The selected item was not found in the mobile device",
						null, AlertType.ERROR);
				Display.getDisplay(midlet).setCurrent(alert,
						Display.getDisplay(midlet).getCurrent());
			}
			return true;

		// #ifdef includeSorting
		/**
		 * Case: Sort photos by number of views 
		 * [EF] Added in the scenario 02 */
		} else if (label.equals("Sort by Views")) {

		// #ifdef includeFavourites
		/**
		 * Case: Set photo as favorite
		 * [EF] Added in the scenario 03 */
		} else if (label.equals("Set Favorite")) {
		} else if (label.equals("View Favorites")) {
			
			/** Case: Save new Photo Label */
		} else if (label.equals("Save")) {
				System.out
						.println("<* PhotoController.handleCommand() *> Save Photo Label = "
								+ this.screen.getLabelName());
				this.getMedia().setMediaLabel(this.screen.getLabelName());
				try {
					updateMedia(media);
				} catch (InvalidImageDataException e) {
					Alert alert = null;
					if (e instanceof ImagePathNotValidException)
						alert = new Alert("Error", "The path is not valid", null, AlertType.ERROR);
					else
						alert = new Alert("Error", "The image file format is not valid", null, AlertType.ERROR);
					Display.getDisplay(midlet).setCurrent(alert, Display.getDisplay(midlet).getCurrent());
				} catch (PersistenceMechanismException e) {
					Alert alert = new Alert("Error", "The mobile database can not update this photo", null, AlertType.ERROR);
					Display.getDisplay(midlet).setCurrent(alert, Display.getDisplay(midlet).getCurrent());
				}
				return goToPreviousScreen();
		
		/** Case: Go to the Previous or Fallback screen * */
		} else if (label.equals("Back")) {
			return goToPreviousScreen();

			/** Case: Cancel the current screen and go back one* */
		} else if (label.equals("Cancel")) {
			return goToPreviousScreen();

		}

		return false;
	}

	
	void updateMedia(MediaData media) throws InvalidImageDataException, PersistenceMechanismException {
			getAlbumData().updateMediaInfo(media, media);
	
	}
	
    /**
     * Get the last selected image from the Photo List screen.
	 * TODO: This really only gets the selected List item. 
	 * So it is only an image name if you are on the PhotoList screen...
	 * Need to fix this
	 */
	public String getSelectedMediaName() {
		List selected = (List) Display.getDisplay(midlet).getCurrent();
		if (selected == null)
		    System.out.println("Current List from display is NULL!");
		String name = selected.getString(selected.getSelectedIndex());
		return name;
	}
	
    /**
     * Show the current image that is selected
	 */
	// #ifdef includePhotoAlbum
	// [NC] Added in the scenario 07
	public void showImage(String name) {
// [EF] Instead of replicating this code, I change to use the method "getSelectedImageName()". 		
		Image storedImage = null;
		try {
			storedImage = ((ImageAlbumData)getAlbumData()).getImageFromRecordStore(getCurrentStoreName(), name);
		} catch (ImageNotFoundException e) {
			Alert alert = new Alert( "Error", "The selected photo was not found in the mobile device", null, AlertType.ERROR);
			Display.getDisplay(midlet).setCurrent(alert, Display.getDisplay(midlet).getCurrent());
	        return;
		} catch (PersistenceMechanismException e) {
			Alert alert = new Alert( "Error", "The mobile database can open this photo", null, AlertType.ERROR);
			Display.getDisplay(midlet).setCurrent(alert, Display.getDisplay(midlet).getCurrent());
	        return;
		}		
		
		//We can pass in the image directly here, or just the name/model pair and have it loaded
		PhotoViewScreen canv = new PhotoViewScreen(storedImage);
		canv.setCommandListener( this );
		AbstractController nextcontroller = this; 
		// #if includeCopyPhoto || includeSmsFeature
		// [NC] Changed in the scenario 07: just the first line below to support generic AbstractController
		PhotoViewController controller = new PhotoViewController(midlet, getAlbumData(), (AlbumListScreen) getAlbumListScreen(), name);
		controller.setNextController(nextcontroller);
		canv.setCommandListener(controller);
		nextcontroller = controller;
		// #endif
		
		//#ifdef includeSmsFeature
		/* [NC] Added in scenario 06 */
		// [NC] Changed in the scenario 07: just the first line below to support generic AbstractController
		SmsSenderController smscontroller = new SmsSenderController(midlet, getAlbumData(), (AlbumListScreen) getAlbumListScreen(), name);
		//this.setNextController(smscontroller);
		smscontroller.setNextController(nextcontroller);
		canv.setCommandListener(smscontroller);
		nextcontroller = smscontroller;
		//#endif
		
		setCurrentScreen(canv);
	}
	//#endif

   /**
    * TODO [EF] update this method or merge with method of super class.
     * Go to the previous screen
	 */
    private boolean goToPreviousScreen() {
	    System.out.println("<* PhotoController.goToPreviousScreen() *>");
		String currentScreenName = ScreenSingleton.getInstance().getCurrentScreenName();
	    if (currentScreenName.equals(Constants.ALBUMLIST_SCREEN)) {
		    System.out.println("Can't go back here...Should never reach this spot");
		} else if (currentScreenName.equals(Constants.IMAGE_SCREEN)) {		    
		    //Go to the image list here, not the main screen...
		    showMediaList(getCurrentStoreName(), false, false);
		    ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
		    return true;
		}
    	else if (currentScreenName.equals(Constants.ADDPHOTOTOALBUM_SCREEN)) {
    		showMediaList(getCurrentStoreName(), false, false);
		    ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
		    return true;
    	}
	    return false;
    } 

	/**
	 * @param image the image to set
	 */
	public void setMedia(MediaData media) {
		this.media = media;
	}

	/**
	 * @return the image
	 */
	public MediaData getMedia() {
		return media;
	}

	public void setScreen(NewLabelScreen screen) {
		this.screen = screen;
	}

	public NewLabelScreen getScreen() {
		return screen;
	}

}
