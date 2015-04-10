

/*
 * Class ShiftSupervisor extend class Employee, contains two additional fields
 *   -- Yearly Salary
 *   -- Number of Times Production Goals were met during the week: this is a
 *         placeholder to be filled only when processing a week worth of data
 *
 *
 */

/**
 *
 * @author VH
 * Complete comments are NOT provided
 *
 * Inherits static fields
 *   Random r
 *   NumberFormat CURRENCY
 */
public class ShiftSupervisor extends Employee implements SalariedEmployee {
    // instance variables
    private int numTimesGoalsMet;
    private double yearlySalary;
    
    // Class Constants
    private static final double MIN_YEARLY_SALARY = 40000;
    private static final double MAX_YEARLY_SALARY = 80000;


    // Default Constructor -- - creates Shift Supervisor with Random Data
    public ShiftSupervisor() {
        super();
        numTimesGoalsMet = 0;
        yearlySalary = MIN_YEARLY_SALARY +
                r.nextDouble() * (MAX_YEARLY_SALARY - MIN_YEARLY_SALARY);
    }

    // Constructor that calls variables from Class Employee to 
    // create object ShiftSuperVisor
    public ShiftSupervisor(String name, String id, 
            int year, double yearlyRate, int numGoalsMet) throws InvalidEmployeeException
    {
        super(name, id, year);

        yearlySalary = MIN_YEARLY_SALARY;
        if (yearlyRate >= MIN_YEARLY_SALARY && 
                yearlyRate <= MAX_YEARLY_SALARY) {
            yearlySalary = yearlyRate;
        }

        setNumTimesGaolsMet(numGoalsMet);
    }
    public ShiftSupervisor(String name, String id, 
            int year, double yearlyRate, int numGoalsMet, double weekSal) throws InvalidEmployeeException
    {
        super(name, id, year);

        yearlySalary = MIN_YEARLY_SALARY;
        if (yearlyRate >= MIN_YEARLY_SALARY && 
                yearlyRate <= MAX_YEARLY_SALARY) {
            yearlySalary = yearlyRate;
        }
        setWeeeklyPay(weekSal);
        setNumTimesGaolsMet(numGoalsMet);
    }

    // ACCESORS
    public double getYearlySalary() {
        return yearlySalary;
    }

    public int getNumTimesGaolsMet() {
        return numTimesGoalsMet;
    }

    // MUTATORS
    public void setYearlySalary(double yearlyRate) {
        if (yearlyRate >= MIN_YEARLY_SALARY && 
                yearlyRate <= MAX_YEARLY_SALARY) {
            yearlySalary = yearlyRate;
        }
    }

    public void setNumTimesGaolsMet(int n) {
        if (n >= 0) {
            numTimesGoalsMet = n;
        }
    }

    public void incrementNumTimesGaolsMet() {
        numTimesGoalsMet++;
    }

    /**
     * toString() method that returns the hourlySalary,yearlySalary,
     * eName,eNumber,hireYear,weeklySalary,goal
     */
    @Override
    public String toString() 
    {
        return "Shift Supervisor: "+ super.toString() + " "
                +"Yearly Salary  " +CURRENCY.format(yearlySalary)
                +"  Goals Met " + this.getNumTimesGaolsMet();
    }
    //for read write purposes 
    public String toStringTwo() 
    {
        return "Shift Supervisor"+ super.toStringTwo() + " "
                + yearlySalary +ReadFile.DELIMITER
                +this.getNumTimesGaolsMet()+ ReadFile.DELIMITER;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        if (! super.equals(obj))
        {
            return false;
        }
        
        final ShiftSupervisor other = (ShiftSupervisor) obj;
        if (this.numTimesGoalsMet != other.numTimesGoalsMet) 
        {
            return false;
        }
        if (Double.doubleToLongBits(this.yearlySalary) != 
                Double.doubleToLongBits(other.yearlySalary)) {
            return false;
        }
        return true;
    }

   
    
    // Implementation of Abstract Methods from Employee
    public String getSalary() {
        return " " + CURRENCY.format(yearlySalary) + " per year ";
    }

    //  Methods to implement for interface Salaried Employee
    public double getWeeklyPayWithBonusesBeforeTaxes() 
    {
        double weeklyPay = this.yearlySalary / Payroll.WORK_WEEKS_PER_YEAR;

        // Update salary if goals met bonus was paid
        if (getNumTimesGaolsMet() >= Payroll.SS_NUM_GOALS_MET_4_BONUS) 
        {
            int yearsWorked = Employee.CURRENT_YEAR - getHireYear();
            weeklyPay *= (1 + Payroll.getSupevisorBonusRate(yearsWorked));
        }
                    
        return weeklyPay;
    }

    public double getWeeklyPaycheckAfterDeduction() 
    {
        return (this.getWeeklyPayWithBonusesBeforeTaxes())*
                (1-Payroll.FEDERAL_STATE_TAX_RATE);
    }
    
    public double getYearlySalaryBeforeTaxes() {
        return this.yearlySalary;
    }
    
}