

/*
 * Class Manager extends class Employee
 *  contains yearlySalary
 *  and the name of the department
 */
/**
 *
 * @author VH Complete comments are NOT provided
 *
 * Inherits static fields Random r NumberFormat CURRENCY
 */
public class Manager extends Employee implements SalariedEmployee {

    public enum Department {

        PAYROLL, PRODUCTION, ACCOUNTING, RESEARCH, MARKETING;

        public static Department getRandomDepartment() {
            Department[] d = Department.values();
            int size = Department.values().length;

            return d[r.nextInt(size)];
        }
    };

    // Instance variables
    private double yearlySalary;
    private Department department;

    // Class Constants
    private static final double MIN_YEARLY_SALARY = 60000;
    private static final double MAX_YEARLY_SALARY = 120000;

    // Class Constructor
    public Manager(String name, String id, int year,
    		double yearlyRate, Department departmentID) throws InvalidEmployeeException
    {
        super(name, id, year);

        yearlySalary = MIN_YEARLY_SALARY;
        if (yearlyRate >= MIN_YEARLY_SALARY && yearlyRate <= MAX_YEARLY_SALARY) {
            yearlySalary = yearlyRate;
        }

        department = departmentID;
    }

    // Default Constructor -- Generates Manager with Random Data
    public Manager() {
        super();
        yearlySalary = MIN_YEARLY_SALARY +
        		r.nextDouble() * (MAX_YEARLY_SALARY - MIN_YEARLY_SALARY);
        department = Department.getRandomDepartment();
    }
    
    
    public Manager(String name, String id, int year, double yearlyRate, String department) throws InvalidEmployeeException
    {
    	super(name, id,year);
    	 yearlySalary = MIN_YEARLY_SALARY;
         if (yearlyRate >= MIN_YEARLY_SALARY && yearlyRate <= MAX_YEARLY_SALARY) {
             yearlySalary = yearlyRate;
         }
         setDepartment(department);
    }
    
    //temporary use, testing
    public Manager(String name, String id, int year, double yearlyRate, String department
    				,double weekSal) throws InvalidEmployeeException
    {
    	super(name,id,year);
    	yearlySalary = ((Main.r.nextDouble()*this.MAX_YEARLY_SALARY)-this.MIN_YEARLY_SALARY);
    	setDepartment(department);
    	setWeeeklyPay(weekSal);
    }
    // ACCESSORS
    public double getYearlySalary() {
        return yearlySalary;
    }

    public Department getDepartment() 
    {
        return department;
    }

    // MUTATORS
    public void setYearlySalary(double yearlyRate) {
        if (yearlyRate >= MIN_YEARLY_SALARY && yearlyRate <= MAX_YEARLY_SALARY) {
            yearlySalary = yearlyRate;
        }
    }
    
    public void setDepartment(String d) throws InvalidEmployeeException
    {
    	if(d == null)
    	{
    		throw new NullPointerException("There is no Department");
    	}
    	if(d == "")
    	{
    		throw new InvalidEmployeeException("Departments must have names");
    	}
    	switch(d)
    	{
    		case "PAYROLL": this.department = Department.PAYROLL;
			break;
    		case "PRODUCTION": this.department = Department.PRODUCTION;
    		break;
    		case "ACCOUNTING": this.department = Department.ACCOUNTING;
    		break;
    		case "MARKETING": this.department = Department.MARKETING;
    		break;
    		case "RESEARCH": this.department = Department.RESEARCH;
    		break;
    		
    	}
    }
    
    
    public void setDepartment(Department d) 
    {
        department = d;
    }

    /**
     * toString() method that returns
     * yearlySalary,department,hourlySalary,weeklySalary
     */
    @Override
    public String toString() {

        return "MANAGER"+"("+department.toString()+") "+ super.toString() + " "
                +" Yearly Salary "+ CURRENCY.format(yearlySalary);
    }

    //for read/write purposes
    public String toStringTwo()
    {

        return "MANAGER"+": "+ super.toStringTwo() + " "
                + yearlySalary +ReadFile.DELIMITER 
                + department.toString() + ReadFile.DELIMITER;
    }
    
    
    /**
     * Equals method
     */
    @Override
   

    // Implementation of Abstract Methods from Employee
    public String getSalary() {
        return " " + CURRENCY.format(yearlySalary) + " per year ";
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((department == null) ? 0 : department.hashCode());
		long temp;
		temp = Double.doubleToLongBits(yearlySalary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
		{
			return true;
		}
		
		if (!super.equals(obj))
		{
			return false;
		}
	
		if (obj instanceof Manager) 
		{
		   Manager m = (Manager) obj;
		   return this.hashCode() == m.hashCode();
		}

	    return false;
	}

	
	@Override
    public double getWeeklyPaycheckAfterDeduction() 
	{
        double weeklySalary = this.yearlySalary / Payroll.WORK_WEEKS_PER_YEAR;
        double healthInsuranceTaxRate =
                Payroll.getManagerHealthInsuranceTaxRate(this.yearlySalary);

        return weeklySalary * 
                (1 - healthInsuranceTaxRate) *                 
                (1 - Payroll.FEDERAL_STATE_TAX_RATE);
    }

    @Override
    public double getYearlySalaryBeforeTaxes() 
    {
        return yearlySalary;
    }
}