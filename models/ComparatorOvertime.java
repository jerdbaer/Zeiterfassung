package models;

import java.util.Comparator;

/**
 * comparator for overtime to sort them by date 
 * past to future 
 * 
 * @author Tom_W
 *
 */
public class ComparatorOvertime implements Comparator<Overtime> {

	@Override
	public int compare(Overtime o1, Overtime o2) {
			var date1 = o1.date;
			var date2 = o2.date;
			return date1.compareTo(date2);
	}

}
