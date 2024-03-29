// #if includeMusic || includeVideo
// [NC] Added in the scenario 07
package lancs.mobilemedia.core.ui.datamodel;

import lancs.mobilemedia.core.util.MusicMediaUtil;
import lancs.mobilemedia.lib.exceptions.ImageNotFoundException;
import lancs.mobilemedia.lib.exceptions.ImagePathNotValidException;
import lancs.mobilemedia.lib.exceptions.InvalidArrayFormatException;
import lancs.mobilemedia.lib.exceptions.InvalidImageDataException;
import lancs.mobilemedia.lib.exceptions.InvalidImageFormatException;
import lancs.mobilemedia.lib.exceptions.PersistenceMechanismException;

public class MusicMediaAccessor extends MediaAccessor {

	private MusicMediaUtil converter = new MusicMediaUtil();

	public MusicMediaAccessor(AlbumData mod) {
		super("mmp-","mmpi-","My Music Album");
	}
	
	// #ifdef includeVideo
	// [NC] Added in the scenario 08
	public MusicMediaAccessor(AlbumData mod, String album_label, String info_label, String default_album_name) {
		super(album_label,info_label,default_album_name);
	}
	//#endif

	protected  byte[] getMediaArrayOfByte(String path)	throws ImagePathNotValidException, InvalidImageFormatException {
		byte[] data1 = converter.readMediaAsByteArray(path);
		return data1;
	}
	
	protected byte[] getByteFromMediaInfo(MediaData ii) throws InvalidImageDataException {
			return converter.getBytesFromMediaInfo(ii);
	}
	
	protected MediaData getMediaFromBytes(byte[] data) throws InvalidArrayFormatException {
		MediaData iiObject = converter.getMultiMediaInfoFromBytes(data);
		return iiObject;
	}

	public void resetRecordStore() throws InvalidImageDataException, PersistenceMechanismException {
		removeRecords();
		
		MediaData media = null;
//		MultiMediaData mmedi = null;

		addMediaData("Applause", "/images/applause.wav", default_album_name);
		addMediaData("Baby", "/images/baby.wav", default_album_name);
		addMediaData("Bong", "/images/bong.wav", default_album_name);
//		addMediaData("Frogs", "/images/frogs.mp3", default_album_name);
		addMediaData("Jump", "/images/jump.wav", default_album_name);
		addMediaData("Printer", "/images/printer.wav", default_album_name);
//		addMediaData("Tango", "/images/cabeza.mid", default_album_name);
	
		loadMediaDataFromRMS(default_album_name);
		try {
			media = this.getMediaInfo("Applause");
			media.setTypeMedia("audio/x-wav");
			this.updateMediaInfo(media, media);

			media = this.getMediaInfo("Baby");
			media.setTypeMedia("audio/x-wav");
			this.updateMediaInfo(media, media);

			media = this.getMediaInfo("Bong");
			media.setTypeMedia("audio/x-wav");
			this.updateMediaInfo(media, media);

//			media = this.getMediaInfo("Frogs");
//			mmedi = new MultiMediaData(media, "audio/mpeg");
//			this.updateMediaInfo(media, media);

			media = this.getMediaInfo("Jump");
			media.setTypeMedia("audio/x-wav");
			this.updateMediaInfo(media, media);

			media = this.getMediaInfo("Printer");
			media.setTypeMedia("audio/x-wav");
			this.updateMediaInfo(media, media);
			
//			media = this.getMediaInfo("Tango");
//			mmedi = new MultiMediaData(media, "audio/midi");
//			this.updateMediaInfo(media, media);
		} catch (ImageNotFoundException e) {
			e.printStackTrace();
		}
	}
}
//#endif
