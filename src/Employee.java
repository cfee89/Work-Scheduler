

/*
 * Class Employee that contains basic Employee Information including
 * Employee Name
 * Employee ID -- a number which consists of three digits
 *                followed by dash and a letter between A and M
 * Employee Hiring Date
 * Employee Weekly Salary -- this is a placeholder for employee weekly
 *                           salary which is set only when full week's worth
 *                           of data have been processed. By default this
 *                           field is set to 0
 */
/**
 *
 * @author VH
 *
 * Complete comments are NOT provided
 */
import java.util.*;
import java.text.*;


public abstract class Employee {

    // Object Member Fields/ Instance variables
    private String employeeName;
    private String employeeID;
    private int hireYear;
    private double weeklySalary;

    // Static class fields and constants
    protected final static Random r = new Random();
    protected static NumberFormat CURRENCY = NumberFormat.getCurrencyInstance();
    public final static int CURRENT_YEAR = 
            Calendar.getInstance().get(Calendar.YEAR);
    private static int FOUNDED_YEAR = 1970;
    public final static int MAX_YEARS_WORKED = CURRENT_YEAR - FOUNDED_YEAR;
    public final static int MAX_WEEKLY_SALARY = 10000;
    public final static DecimalFormat format = new DecimalFormat("#,####00.00");
    
    private final static char   ID_FIRST_CHAR ='A';
    private final static char   ID_LAST_CHAR ='M';
    private final static String ID_FORMAT = "[0-9][0-9][0-9]-[a-mA-M]";
    
    protected String[] firstNames = {"Caitlyn", "Laine", "Liam", "Sophia", 
            "Charlotte", "Jackson", "Ethan", "Mason", "Jack", "John", "James", 
            "John", "Robert", "Michael", "William", "David", "Richard", 
            "Charles", "Joseph", "Thomas","Christopher", "Daniel", "Paul", 
            "Mark", "Anthony", "Luke", "George", "Steven", "Malcolm", "Brian", 
            "Mary", "Patricia", "Linda", "Barbara","Elizabeth", "Corine", 
            "Susan", "Margaret", "Dorothy", "Lisa", "Nancy", "Karen",
            "Betty", "Helen", "Sandra", "Donna", "Carol", "Ruth","Sharon", 
            "Michele"};
    
    protected String[] lastNames = {"Perry", "Elis", "Gibbons", "Roster", 
            "Carmicheal", "Babins", "Smith", "Johnson", "Williams",
            "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
            "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", 
            "Thompson", "Garcia", "Martinez", "Robinson", "Clark", "Kent", 
            "Rodriguez", "Lewis", "Walker", "Hall", "Allen", "Young", 
            "Hernandez", "King", "Wright", "Lopez", "Hill", "Scott", "Green", 
            "Adams", "Baker", "Gonzalez", "Nelson", "Carter"};
    
    
    // Default Constructor that creates an Employee with Random Information
    public Employee() {
       
        employeeName = firstNames[r.nextInt(firstNames.length)] + "  "
                + lastNames[r.nextInt(lastNames.length)];
        employeeID = generateRandomID();
        hireYear = CURRENT_YEAR - r.nextInt(MAX_YEARS_WORKED);
        setRandomSalary();

        // set proper format for displaying currency     
        CURRENCY.setMaximumFractionDigits(2);
    }

    //Class Constructor
    public Employee(String name, String id, int year) throws InvalidEmployeeException
    {
    	if(!name.equals(""))
    	{
    		employeeName = name;
    	}
    	
    	else
    	{
    		throw new InvalidEmployeeException("Incorrect Name format");
    	}
    	
        if (isValidID(id))
        {
            employeeID = id;
        } else 
        {
            throw new InvalidEmployeeException("Incorrect ID format");
        }

        hireYear = CURRENT_YEAR;
        if (year > CURRENT_YEAR - MAX_YEARS_WORKED && year <= CURRENT_YEAR)
        {
            hireYear = year;
        }

        weeklySalary = 0;

        // set proper format for displaying currency     
        CURRENCY.setMaximumFractionDigits(2);
    }
    //Class Constructor
    public Employee(String name, String id, int year, double weekSal) throws InvalidEmployeeException
    {
    	if(!name.equals(""))
    	{
    		employeeName = name;
    	}
    	
    	else
    	{
    		throw new InvalidEmployeeException("Incorrect Name format");
    	}
    	
        if (isValidID(id))
        {
            employeeID = id;
        } else 
        {
            throw new InvalidEmployeeException("Incorrect ID format");
        }

        hireYear = CURRENT_YEAR;
        if (year > CURRENT_YEAR - MAX_YEARS_WORKED && year <= CURRENT_YEAR)
        {
            hireYear = year;
        }

        this.setWeeeklyPay(weekSal);

        // set proper format for displaying currency     
        CURRENCY.setMaximumFractionDigits(2);
    }
    // Supporting method to generate a random Employee ID
    // Allows duplicates
    private String generateRandomID()
    {
    	char letter = (char) (r.nextInt(12) + 'A');
    	String ID = "" + r.nextInt(10) + r.nextInt(10) +r.nextInt(10) + "-" + letter ; 
    	return ID;
    }

    // Validates provided string as a proper employee id
    private boolean isValidID(String id) throws InvalidEmployeeException
    {
    	if(id.matches(ID_FORMAT))
    	{
    		return true;
    	}
    	else
    	{
    		throw new InvalidEmployeeException("Invalid Employee ID"+
    	"\nID must match \"[0-9][0-9][0-9]-[a-mA-M]\" input ");
    	}
	
    	
        
    }

    // ACCESSORS
    public int getHireYear() 
    {
        return hireYear;
    }

    public double getWeeklySalary() 
    {
        return weeklySalary;
    }

    public String getID()
    {
        return employeeID;
    }

    public String getName()
    {
        return employeeName;
    }

    // MUTATORS
    public void setName(String name) throws InvalidEmployeeException
    {
        if(!name.equals(" "))
        {
        	employeeName = name;
        }
        else
        {
        	throw new InvalidEmployeeException("Invalid Employee name\n"+
        "an employee must have a name");
        }
    }

    public void setID(String id) throws InvalidEmployeeException
    {
        if (isValidID(id)) 
        {
            employeeID = id;
        }
    }

    public void setHireYear(int year) throws InvalidEmployeeException
    {
        if (year <= CURRENT_YEAR && year >= CURRENT_YEAR - MAX_YEARS_WORKED)
        {
            hireYear = year;
        }
        else
        {
        	throw new InvalidEmployeeException("Incorrect input for Hire Year\nMust be within"
					+ " the correct year span\n" +
					"This company didn't exist in the year you have input");
        }
    }

    public void setWeeeklyPay(double salary)
    {
        if (salary <= MAX_WEEKLY_SALARY && salary >= 0)
        {
            weeklySalary = salary;
        }
    }

    private void setRandomSalary()
    {
    	double salary;
    	salary = r.nextDouble()*this.MAX_WEEKLY_SALARY;
    	
    	format.format(salary);
    	this.weeklySalary = salary;
    	
    }
    
    /**
     * method that returns the name,number,hireYear,and weeklySalary
     */
    @Override
    public String toString()
    {
        return employeeName +"   "
        	   + getID()    +"   "
        	   + hireYear   +"   "
               + "Weekly Salary " +CURRENCY.format(getWeeklySalary());
    }
    
    //for read/Write purposes
    public String toStringTwo()
    {
        return (employeeName +ReadFile.DELIMITER + getID()+ReadFile.DELIMITER + 
        		+ hireYear+ReadFile.DELIMITER
                + format.format(this.weeklySalary) + ReadFile.DELIMITER);
    }
    
    
    // Generated by Netbeans
    @Override
    public int hashCode() 
    {
        int hash = 3;
        hash = 41 * hash
           + (this.employeeName != null ? this.employeeName.hashCode() : 0);
        hash = 41 * hash
           + (this.employeeID != null ? this.employeeID.hashCode() : 0);
        hash = 41 * hash + this.hireYear;
        hash = 41 * hash
           + (int) (Double.doubleToLongBits(this.weeklySalary)
           ^ (Double.doubleToLongBits(this.weeklySalary) >>> 32));
        return hash;
    }

    /**
     * equals method -- Generated by NetBeans
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.employeeName, other.employeeName))
        {
            return false;
        }
        if (!Objects.equals(this.employeeID, other.employeeID))
        {
            return false;
        }
        if (this.hireYear != other.hireYear) 
        {
            return false;
        }
        if (Double.doubleToLongBits(this.weeklySalary) != 
                Double.doubleToLongBits(other.weeklySalary)) 
        {
            return false;
        }
        return true;
    }

    // Abstract methods
    public abstract String getSalary();
}
