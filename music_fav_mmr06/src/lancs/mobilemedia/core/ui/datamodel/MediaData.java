/*
 * Created on Nov 26, 2004
 *
 */
package lancs.mobilemedia.core.ui.datamodel;

/**
 * @author trevor
 *
 * This class holds meta data associated with a photo or image. There is a one-to-one
 * relationship between images and image metadata. (ie. Every photo in MobileMedia will
 * have a corresonding ImageData object). 
 * It stores the recordId of the image record in RMS, the recordID of the metadata record
 * the name of the photo album(s) it belongs to, the text label, associated phone numbers
 * etc.
 * 
 */
public class MediaData {
	
	private int recordId; //imageData recordId 
	private int foreignRecordId; //image recordId
	private String parentAlbumName; //Should we allow single image to be part of multiple albums?
	private String mediaLabel;
	
	// #ifdef includeFavourites
	// [EF] Added in the scenario 03 
	private boolean favorite = false;
	// #endif

	/**
	 * @param foreignRecordId
	 * @param parentAlbumName
	 * @param imageLabel
	 */
	public MediaData(int foreignRecordId, String parentAlbumName,String mediaLabel) {
		super();
		this.foreignRecordId = foreignRecordId;
		this.parentAlbumName = parentAlbumName;
		this.mediaLabel = mediaLabel;
	}
	
	/**
	 * @return Returns the recordId.
	 */
	public int getRecordId() {
		return recordId;
	}
	
	/**
	 * @param recordId The recordId to set.
	 */
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	
	/**
	 * @return Returns the foreignRecordId.
	 */
	public int getForeignRecordId() {
		return foreignRecordId;
	}
	
	/**
	 * @param foreignRecordId The foreignRecordId to set.
	 */
	public void setForeignRecordId(int foreignRecordId) {
		this.foreignRecordId = foreignRecordId;
	}
	
	/**
	 * @return Returns the imageLabel.
	 */
	public String getMediaLabel() {
		return mediaLabel;
	}
	
	/**
	 * @param imageLabel The imageLabel to set.
	 */
	public void setMediaLabel(String mediaLabel) {
		this.mediaLabel = mediaLabel;
	}
	
	/**
	 * @return Returns the parentAlbumName.
	 */
	public String getParentAlbumName() {
		return parentAlbumName;
	}
	
	/**
	 * @param parentAlbumName The parentAlbumName to set.
	 */
	public void setParentAlbumName(String parentAlbumName) {
		this.parentAlbumName = parentAlbumName;
	}

	// #ifdef includeFavourites
	/**
	 * [EF] Added in the scenario 03
	 */
	public void toggleFavorite() {
		this.favorite = ! favorite;
	}
	
	/**
	 * [EF] Added in the scenario 03
	 * @param favorite
	 */
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	/**
	 * [EF] Added in the scenario 03
	 * @return the favorite
	 */
	public boolean isFavorite() {
		return favorite;
	}
	// #endif	
}
