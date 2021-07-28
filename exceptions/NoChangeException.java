package exceptions;

/**
 * Exception that is is thrown whenever the old password equals the new password input.
 */
public class NoChangeException extends Exception {
  public NoChangeException(){}

  public NoChangeException(String s){
    super(s);
  }
}
