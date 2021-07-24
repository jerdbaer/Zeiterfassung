package exceptions;

/**
 * Ein Fehler, der geworfen wird, wenn das Passwort falsch ist
 */
public class FalsePasswordException extends Exception {
  public FalsePasswordException(){}

  public FalsePasswordException(String s){
    super(s);
  }
}
