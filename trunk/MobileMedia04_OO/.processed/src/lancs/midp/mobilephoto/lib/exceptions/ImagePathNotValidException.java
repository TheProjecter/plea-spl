package lancs.midp.mobilephoto.lib.exceptions;

public class ImagePathNotValidException extends InvalidImageDataException {

	public ImagePathNotValidException() {
		// TODO Auto-generated constructor stub
	}

	public ImagePathNotValidException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	private Throwable cause;
	public ImagePathNotValidException(Throwable arg0) {
		cause = arg0;
		// TODO Auto-generated constructor stub
	}
	public Throwable getCause(){
		return cause;
	}


}
