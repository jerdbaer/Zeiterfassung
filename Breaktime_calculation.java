import java.util.List;

public class Breaktime_calculation 
{

	public double getSum_Breaktime_in_h(List<Double> all_Breaks_in_h)
	{
		double sum = 0;
		for(double rest: all_Breaks_in_h)
			sum += rest;
		return sum;
	}
	
	public double getBreaktime_min_in_h(double worktime_in_h)
	{
		double breaktime_min_in_h = 0;
		if(worktime_in_h > 6)
			breaktime_min_in_h += 0.5;
		if (worktime_in_h > 9)
			breaktime_min_in_h += 0.25;
		return breaktime_min_in_h;
			
	}
	
	public boolean validate_Break(List<Double> all_Breaks_in_h, double breaktime_min_in_h)
	{
		double valide_Breaktime = 0;
		for(double rest :all_Breaks_in_h)
		{	
			if (rest >= 0.25)
				valide_Breaktime += rest;
		}
		if (valide_Breaktime >= breaktime_min_in_h)
			return true;
		else 
			return false;
	}
}
