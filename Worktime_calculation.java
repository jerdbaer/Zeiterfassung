
public class Worktime_calculation 
{
	public double getWorktime_in_h(double time_at_work, double sum_Breaktime_in_h)
	{
		return time_at_work - sum_Breaktime_in_h;
	}
}
