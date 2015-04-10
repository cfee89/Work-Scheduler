
import java.util.*;



/*
 * This class contains informaiton about a single shift
 *  -- Type of shift: day or night
 *  -- Day of the week
 *  -- whether the goals were met during this shift
 *  -- List of Employees working during this shift
 */
/**
 *
 * @author VH Complete comments are NOT provided
 *
 */
public class Shift {

    public static enum WeekDay {

        Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;

        public static WeekDay getDay(int i) {
            return WeekDay.values()[i % WeekDay.values().length];
        }
    };

    public static final int SHIFT_LENGTH = 8;
    public static final boolean DAYSHIFT = true;
  	public static final boolean NIGHTSHIFT = false;

    // Instance variables
    private WeekDay day;
    private boolean metProductionGoals;
    private boolean isDayShift;
    private ArrayList<Employee> workers;

    // Static class variables and constants
    private static Random r = new Random();

    /**
     * Constructor for objects of class Shift
     */
    public Shift(WeekDay weekDay, boolean shift, 
            boolean goals, ArrayList<Employee> employee)
    {
        this.day = weekDay;
        this.isDayShift = shift;
        this.metProductionGoals = goals;
        
        // Validate the correct number of employees in
        // the code that uses this class
        this.workers = employee;
    }


    public Shift(String weekDay, String shift, String goals) throws InvalidShiftException
    {
    	
    	setShiftType(shift);
    	setGoalsMet(goals);
    	this.workers = new ArrayList<Employee>();
    }
  
    
    public Shift(WeekDay weekDay, String shift, String goals) throws InvalidShiftException
    {
    	this.day = weekDay;
    	setShiftType(shift);
    	setGoalsMet(goals);
    	this.workers = new ArrayList<Employee>();
    }
    
    
    // ACCESORS
    public WeekDay getDay() 
    {
        return day;
    }

    public boolean isDayShift()
    {
        return isDayShift;
    }

    public boolean goalsMet() 
    {
        return metProductionGoals;
    }

    public ArrayList<Employee> getEmployees() 
    {
        return workers;
    }

    // MUTATORS
    
    public void setStringDay(String day)
    {
    	WeekDay[] days = WeekDay.values();
    	for(int i = 0;i< WeekDay.values().length;)
    	{
    		if(day.matches(days[i].toString()))
    		{
    			this.day = days[i];
    		}
    	}
    }
    
    public void setDay(WeekDay day)throws InvalidShiftException
    {
        this.day = day;
    }

    public void setShiftType(String shift)throws InvalidShiftException
    {
    	if(shift == null)
    	{
    		throw new InvalidShiftException("Null shift type not accepted");
    	}
    	if(shift.equals(""))
    	{
    		throw new InvalidShiftException("Empty shift type not accepted");
    	}
    	if(shift.equals("Day Shift"))
    	{
    		this.isDayShift = true;
    	}
    	else
    	{
    		this.isDayShift = false;
    	}
    }

    public void setGoalsMet(String goals)throws InvalidShiftException
    {
    	if(goals == null)
    	{
    		throw new InvalidShiftException("Null goals not accepted");
    	}
    	if(goals.equals(""))
    	{
    		throw new InvalidShiftException("Empty goals not accepted");
    	}
    	if(goals.equals("MET"))
    	{
    		this.metProductionGoals = true;
    	}
    	else
    	{
			this.metProductionGoals = false;
		}
    }

    public void setWorkers(ArrayList<Employee> eList) throws InvalidShiftException
    {
        workers = eList;
    }
            
    //*************************************************
    public static Employee getNewEmployeeOfType(String empType)
    {
    	if(empType.equals("Production Worker"))
    	{
    		ProductionWorker PW = new ProductionWorker();
    		return PW;
    	}
    	if(empType.equals("Shift Supervisor"))
    	{
    		ShiftSupervisor s = new ShiftSupervisor();
    		return s;
    	}
    	else
    	{
    		Manager m = new Manager();
    		return m;
    	}
    }
    
    
    public static Employee addRandomEmployee(String empType)
    {
    	if(empType.equals("Production Worker"))
    	{
    		ProductionWorker PW = new ProductionWorker();
    		return PW;
    	}
    	if(empType.equals("Shift Supervisor"))
    	{
    		ShiftSupervisor s = new ShiftSupervisor();
    		return s;
    	}
    	else
    	{
    		Manager m = new Manager();
    		return m;
    	}
    }
    
    /**
     * toString() method that returns the day,shift,goals,employees
     */
    @Override
    public String toString() 
    {
        String result = "\n-------------------------------------\nShift Info: "
                + day.toString();

        if (isDayShift) 
        {
            result += " Day Shift";
        } 
        else 
        {
            result += " Night Shift";
        }

        if (metProductionGoals)
        {
            result += "\n========================\nProduction Goals -- MET!\n";
        }
        else {
            result += "\n=========================\nProduction Goals -- NOT MET!\n";
        }

        result += "Workers for the shift\n================================\n";

        for (int i = 0; i < workers.size(); i++)
        {
            result += (workers.get(i).toString() + "\n");
        }

        return result;
    }
    public String readableString()
	{
		String readable = day.toString() + ReadFile.DELIMITER+" ";
		if (isDayShift) 
		{
			readable += " Day Shift"+ReadFile.DELIMITER;
		} 
		else 
		{
			readable += " Night Shift"+ReadFile.DELIMITER;
		}
		if (metProductionGoals)
		{
			readable += "GOALS MET"+ReadFile.DELIMITER+"\r\n";
		}
		else {
			readable += "GOALS NOT MET"+ReadFile.DELIMITER+"\r\n";
		}
		for (int i = 0; i < workers.size(); i++)
		{
			readable += (workers.get(i).toStringTwo() +"\r\n");
		}

		return "\r\n"+readable;
	}
}