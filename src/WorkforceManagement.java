
import java.util.*;


/**
 *
 * @author student
 */
public class WorkforceManagement {
    private static final int MAX_NUM_PW = 50;
    private static final int MAX_NUM_S = 10;
    private static final int MAX_NUM_M = 5;
  
    private static final Random r = new Random();
  
    
    // Creates a full List of random Employees who work for the company 
    public static ArrayList <Employee> createWorkerForce() {
        ArrayList<Employee> workforceEmployees = new ArrayList<Employee>();

        workforceEmployees.addAll(createList(MAX_NUM_PW, 
                ProductionWorker.class));
        workforceEmployees.addAll(createList(MAX_NUM_S, 
                ShiftSupervisor.class));
        workforceEmployees.addAll(createList(MAX_NUM_M, 
                Manager.class));

        return workforceEmployees;
    }

    // supporting method that generates a list of specified size 
    // with specified type of employees
    private static ArrayList<Employee> createList(int size, Class type) {
        ArrayList<Employee> e = new ArrayList<Employee>();

        for (int i = 0; i < size; i++) {
            if (type == ProductionWorker.class) {
                e.add(new ProductionWorker());
            }
            if (type == ShiftSupervisor.class) {
                e.add(new ShiftSupervisor());
            }
            if (type == Manager.class) {
                e.add(new Manager());
            }
        }

        return e;
    }

    
    // Creates a ArrayList which is a subset of original list but with a 
    // different (i.e., smaller or equal) number of employees of different types
    public static ArrayList<Employee> getShiftWorkforce(int sizePW, int sizeS, 
            int sizeM, ArrayList<Employee> allEmployees)
    {
        ArrayList <Employee> shiftWorkforce = new ArrayList <Employee>();
        
        shiftWorkforce.addAll(
                getSubset(getEmployeesOfType(allEmployees,
                        ProductionWorker.class), sizePW));
        shiftWorkforce.addAll(
                getSubset(getEmployeesOfType(allEmployees,
                        ShiftSupervisor.class), sizeS));
        shiftWorkforce.addAll(
                getSubset(getEmployeesOfType(allEmployees,
                        Manager.class), sizeM));
         
        return shiftWorkforce;
    }
    
    // Returns a subset of Employees of a specific Type from 
    // the given list of employees
    public static ArrayList<Employee> getEmployeesOfType(
            ArrayList<Employee> allEmployees, Class type) 
    {
        ArrayList <Employee> employeesOfType = new ArrayList <Employee>();
        
        for(Employee e: allEmployees)
        {
            if (e.getClass() == type)
            {
                employeesOfType.add(e);
            }
        }
        
        return employeesOfType;
    }
    
    // Returns a subset of  specified size from a given ArrayList of Employees
    private static ArrayList<Employee> getSubset(
            ArrayList<Employee> allEmployees, int size) 
    {
        // Idea: make a copy of the allEmployees arrayList, 
        // then remove random items form the copied list until it
        // becomes of specified size
        
        ArrayList<Employee> subsetE =  (ArrayList) allEmployees.clone();
        
        while(subsetE.size() > size)
        {
            subsetE.remove(r.nextInt(subsetE.size()));
        }
        
        /*
        HashSet<Employee> hashSetE =  new HashSet <Employee>();
        
        while(subsetE.size() < size)
        {
        	hashSetE.add(allEmployees.get(r.nextInt(allEmployees.size())));
        }
        */
        
        
        return subsetE;       
    }
    
    
     
    
   
}