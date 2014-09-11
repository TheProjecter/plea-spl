package lancs.midp.mobilephoto.lib.exceptions;

public class InvalidImageDataException extends Exception {

	public InvalidImageDataException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidImageDataException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	private Throwable cause;
	public InvalidImageDataException(Throwable arg0) {
		cause = arg0;
		// TODO Auto-generated constructor stub
	}
	public Throwable getCause(){
		return cause;
	}


}
