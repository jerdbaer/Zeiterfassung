package exceptions;

/**
 * Exception that is is thrown whenever the old password equals the new password input.
 */
public class NoChangeException extends Exception {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1752275230711037353L;

public NoChangeException(){}

  public NoChangeException(String s){
    super(s);
  }
}
