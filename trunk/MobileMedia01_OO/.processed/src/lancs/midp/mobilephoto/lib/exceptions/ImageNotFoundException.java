package lancs.midp.mobilephoto.lib.exceptions;

public class ImageNotFoundException extends Exception {
	
	public ImageNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public ImageNotFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	private Throwable cause;
	public ImageNotFoundException(Throwable arg0) {
		cause = arg0;
		// TODO Auto-generated constructor stub
	}
	public Throwable getCause(){
		return cause;
	}

	
}
