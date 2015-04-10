
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author VH
 *
 * Complete comments are NOT provided
 */
import java.text.NumberFormat;
import java.util.*;

public class Main {

	public static final Random r = new Random();
    private static final int NUM_DAYS = 7;
    private static final int SHIFTS_PER_DAY = 2;
    protected static NumberFormat CURRENCY = NumberFormat.getCurrencyInstance();

    private static final int MAX_NUM_PW_SHIFT = 20;
    private static final int MAX_NUM_S_SHIFT = 3;
    private static final int MAX_NUM_M_SHIFT = 1;
    private static final int MIN_NUM_PW_SHIFT = 5;
    private static final int MIN_NUM_S_SHIFT = 1;
    private static final int MIN_NUM_M_SHIFT = 1;
    private static final int PROB_GOALS_MET = 75;

    // Extra credit instance variables for keeping track of weekly bonuses
    private static double bonus4Goals;              // Not implemented
    private static double bonus4NightShift;         // Not implemented
    private static double totalPayout;

    public static void main(String[] args) {
      //  PayrollGUI g = new PayrollGUI();
    	runTest();
    }

    public static void runTest() {
    	
        ArrayList<Shift> weeklyShift = new ArrayList<Shift>();
       
        // ArrayList of ALL employees
        ArrayList<Employee> workforce = WorkforceManagement.createWorkerForce();

        for (int i = 0; i < NUM_DAYS * SHIFTS_PER_DAY; i++) {

            int numPW = r.nextInt(MAX_NUM_PW_SHIFT) + MIN_NUM_PW_SHIFT;
            int numS = r.nextInt(MAX_NUM_S_SHIFT) + MIN_NUM_S_SHIFT;
            int numM = r.nextInt(MAX_NUM_M_SHIFT) + MIN_NUM_M_SHIFT;

            weeklyShift.add(new Shift(Shift.WeekDay.getDay(i / 2),
                    i % 2 == 0, // Shift type
                    r.nextInt(100) < PROB_GOALS_MET, // Probability of goals met
                    WorkforceManagement.getShiftWorkforce(numPW, numS, numM,
                            workforce)));
        }

        processPayroll(weeklyShift, workforce);
        System.out.println(weeklyShift);

        System.out.println("\n\n==============================================");
        System.out.println("Total weekly payout:\t" + CURRENCY.format(totalPayout));
        System.out.println("Night Shift Bonuses:\t" + CURRENCY.format(bonus4NightShift));
        System.out.println("Goals Met Bonuses:  \t" + CURRENCY.format(bonus4Goals));

//        for (Shift s : weeklyShift) {
//             System.out.print(s);
//         }
    }

	public static ArrayList<Shift> makeWeek()
	{
		ArrayList<Shift> weeklyShift = new ArrayList<Shift>();

		
		for (int i = 0; i < NUM_DAYS * SHIFTS_PER_DAY; i++) {

			ArrayList<Employee> workForce = new ArrayList<Employee>();
			weeklyShift.add(new Shift(Shift.WeekDay.getDay(i / 2), i % 2 == 0, // Shift
					r.nextInt(100) < PROB_GOALS_MET // Probability of goals met
					,workForce));
			
		}
		return weeklyShift;
    }

    public static void processPayroll(ArrayList<Shift> shiftData, ArrayList<Employee> workforce) {

        // Ensure that all weekly salaries, number of times goals met
        // and totals accumulators are set to 0
        resetTotals(workforce);

        // Update the number of times the goals met for each shift supervisor
        // Update production workers weekly salary
        for (Shift s : shiftData) {
            ArrayList<Employee> shiftEmployees = s.getEmployees();

            for (Employee e : shiftEmployees) {

                // Update the number of times production goals were met for 
                // Shift supervisors
                if (e instanceof ShiftSupervisor) {
                    ShiftSupervisor supervisor = (ShiftSupervisor) e;
                    if (s.goalsMet()) {
                        supervisor.incrementNumTimesGaolsMet();
                    }
                }
                if (e instanceof ProductionWorker) {
                    Payroll.updateWeeklySalary(s, (ProductionWorker) e);
                }
            }
        } // End of processing shift employee

        // Update weekly salary for Salaried Employees and compute bonuses
        for (Employee e : workforce) {
            if (e instanceof SalariedEmployee) {

                double weeklyPay
                        = ((SalariedEmployee) e).getWeeklyPaycheckAfterDeduction();
                e.setWeeeklyPay(weeklyPay);
            }

            totalPayout += e.getWeeklySalary();
        }

    } // End of method for processing weekly shift data

    public static void resetTotals(ArrayList<Employee> workforce) {
        // Sanity Check... Make sure that all employees have their weekly salary
        // and number of times goals met are initialized to 0
        for (Employee e : workforce) {
            e.setWeeeklyPay(0);

            if (e instanceof ShiftSupervisor) {
                ShiftSupervisor supervisor = (ShiftSupervisor) e;
                supervisor.setNumTimesGaolsMet(0);
            }
        }
        bonus4Goals = 0;
        bonus4NightShift = 0;
        totalPayout = 0;
    }
} // End of class