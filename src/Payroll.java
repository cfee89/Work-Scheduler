
/**
 *
 * @author Hnatyshin
 */
public class Payroll {

    public static final double PW_NIGHT_SHIFT_BONUS_RATE = .2;
    public static final double PW_GOALS_MET_BONUS_RATE = .05;
    public static final int SS_NUM_GOALS_MET_4_BONUS = 3;
    public static final int WORK_WEEKS_PER_YEAR = 52;
    
    // salary 80K - 3.5% health insurance rate
    // salary 100K - 4.25% health insurance rate
    // salary 120K - 4.95% health insurance rate
    // else - 5.00%
    public static final double[][] MANAGER_HEALTH_TAX
            = {{80000.0, 100000.0, 120000.0}, {0.035, 0.0425, 0.0495}};
    public static final double DEFAULT_MANAGER_HEALTH_TAX = 0.05;
    
    // 0 - 5 years : bonus is 5% of weekly salary
    // 6 � 10 years: bonus is 10% of weekly salary
    // 11 � 20 years: bonus is 12% of weekly salary
    // More than 20 years: bonus is 15% of weekly salary
    public static final double[][] SS_YEARS_WORKED_BONUS
            = {{5.0, 10.0, 20.0, Employee.MAX_YEARS_WORKED }, 
                {0.05, 0.1, 0.12, 0.15}};

    public static final double FEDERAL_STATE_TAX_RATE = 0.3;

    // update PW Weekly Salary after computing bonus
    public static void updateWeeklySalary(Shift s, ProductionWorker p) {

        double thisShiftSalary = Shift.SHIFT_LENGTH * p.getRate();
        double bonus4NightShift = 0, bonus4Goals = 0;

        // Add night shift bonus
        if (!s.isDayShift()) {
            bonus4NightShift += thisShiftSalary * PW_NIGHT_SHIFT_BONUS_RATE;
            thisShiftSalary *= (1 + PW_NIGHT_SHIFT_BONUS_RATE);

        }

        // Add production goals met rate
        if (s.goalsMet()) {
            bonus4Goals += thisShiftSalary * PW_GOALS_MET_BONUS_RATE;
            thisShiftSalary *= (1 + PW_GOALS_MET_BONUS_RATE);
        }

        p.setWeeeklyPay(p.getWeeklySalary() + thisShiftSalary);
    }


    public static double getManagerHealthInsuranceTaxRate(double salary) 
    {
        double taxRate = Payroll.DEFAULT_MANAGER_HEALTH_TAX;
            
        // Find correct tax bracket
        int i;
        for(i=0; i < Payroll.MANAGER_HEALTH_TAX.length &&
                Payroll.MANAGER_HEALTH_TAX[i][0] < salary;
               i++);
        
        // correct tax rate found
        if (i < Payroll.MANAGER_HEALTH_TAX.length)
        {
            taxRate = Payroll.MANAGER_HEALTH_TAX[i][1];
        }
       
        return taxRate;
    }

    
    public static double getSupevisorBonusRate(int yearsServed) 
    {
        // Last rate value is the default one
         double taxRate = SS_YEARS_WORKED_BONUS[
                 SS_YEARS_WORKED_BONUS.length -1][1];
            
        // Find correct tax bracket
        int i;
        for(i=0; i < SS_YEARS_WORKED_BONUS.length && 
                SS_YEARS_WORKED_BONUS[i][0] < yearsServed; i++);
        
        // correct tax rate found
        if (i < SS_YEARS_WORKED_BONUS.length)
        {
            taxRate = Payroll.SS_YEARS_WORKED_BONUS[i][1];
        }
       
        return taxRate; 
       
    }

}