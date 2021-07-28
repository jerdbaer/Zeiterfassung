package models.time;

/**
 * Interface define break characteristics
 * 
 * @author Tom Weißflog
 * @version 1.0
 */

public interface IBreak {
	
	/**
	 * Validates, if break fulfills legal requirements
	 */
	public boolean isLegal();

}
