package exceptions;

/**
 * Ein Fehler, der geworfen wird, wenn altes Passwort und neues Passwort übereinstimmen
 */
public class NoChangeException extends Exception {
  public NoChangeException(){}

  public NoChangeException(String s){
    super(s);
  }
}
