package lancs.midp.mobilephoto.lib.exceptions;



public class PersistenceMechanismException extends Exception {

	public PersistenceMechanismException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public PersistenceMechanismException() {
		// TODO Auto-generated constructor stub
	}
	private Throwable cause;
	public PersistenceMechanismException(Throwable arg0) {
		cause = arg0;
		// TODO Auto-generated constructor stub
	}
	public Throwable getCause(){
		return cause;
	}
}
