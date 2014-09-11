package lancs.midp.mobilephoto.lib.exceptions;

public class UnavailablePhotoAlbumException extends Exception {

	public UnavailablePhotoAlbumException() {
		// TODO Auto-generated constructor stub
	}

	public UnavailablePhotoAlbumException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	private Throwable cause;
	public UnavailablePhotoAlbumException(Throwable arg0) {
		cause = arg0;
		// TODO Auto-generated constructor stub
	}
	public Throwable getCause(){
		return cause;
	}

}
