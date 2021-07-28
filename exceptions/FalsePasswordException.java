package exceptions;

/**
 * Exception that is is thrown whenever the password input is wrong.
 */
public class FalsePasswordException extends Exception {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1821864281929027421L;

public FalsePasswordException(){}

  public FalsePasswordException(String s){
    super(s);
  }
}
