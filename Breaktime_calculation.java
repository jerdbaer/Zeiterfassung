
public class Breaktime_calculation 
{
	/*
	 *  < 6h keine Pause
	 *  6-9h 30min 
	 *  > 9h 45min
	 *  
	 *  
	 *  _Pause_
	 *  min. 15min am stück
	 *  nicht am Anfang, am Ende der Arbeit
	 */

	public double getBreaktime_min_in_h(double worktime_in_h)
	{
		double breaktime_min_in_h = 0;
		if(worktime_in_h > 6)
			breaktime_min_in_h += 0.5;
		if (worktime_in_h > 9)
			breaktime_min_in_h += 0.25;
		return breaktime_min_in_h;
			
	}
}
