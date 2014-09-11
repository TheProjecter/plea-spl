package lancs.midp.mobilephoto.lib.exceptions;

public class NullAlbumDataReference extends Exception {

	public NullAlbumDataReference() {
		// TODO Auto-generated constructor stub
	}

	public NullAlbumDataReference(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	private Throwable cause;
	public NullAlbumDataReference(Throwable arg0) {
		cause = arg0;
		// TODO Auto-generated constructor stub
	}
	public Throwable getCause(){
		return cause;
	}



}
