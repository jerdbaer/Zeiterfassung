package exceptions;

/**
 * Exception that is is thrown whenever the password input is wrong.
 */
public class FalsePasswordException extends Exception {
  public FalsePasswordException(){}

  public FalsePasswordException(String s){
    super(s);
  }
}
