package models;

/**
 * Class to define employee objects.
 * 
 * @author Tom Weißflog
 * @version 1.0
 */

public class MA_Data {
	
	/**
	 * individual employee's id in int
	 */
	private int MA_ID;
	/**
	 * password for login account in String
	 */
	private String password;
	
	/**
	 * gets employee's id
	 * @return employee's id
	 */
	public int getMA_ID() {
		return MA_ID;
	}
	
	/**
	 * sets employee's id to a given value
	 */
	public void setMA_ID(int MA_ID) {
		this.MA_ID = MA_ID;
	}
	

	/**
	 * gets the password of employee
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * sets password to a given value
	 */
	public void setPassword(String codedPassword) {
		this.password = codedPassword;
	}

}
