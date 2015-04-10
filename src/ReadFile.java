
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import asfd.Manager.Department;
import asfd.Shift.WeekDay;


	public class ReadFile {
	  //System.getProperty("user.home"); // get current user home dir
		public static  String DELIMITER_STRING ="\\s*#\\s*";
		public static String DELIMITER = "#";
		static final String userHomeFolder = System.getProperty("user.home");
		static final String employeePath = (userHomeFolder + "\\payroll.txt");
		static final String shiftPath = (userHomeFolder + "\\shift.txt");
	   	   
		public static void main(String args[])
		{
	        readEmployeeInfo(employeePath);
	    }

	
	    public static ArrayList<Employee> readEmployeeInfo(String filename)  {
	    	ArrayList<Employee> newEmps = new ArrayList<Employee>();
	        String line = "";
	        try {
	            BufferedReader br = new BufferedReader(new FileReader(filename));	            
	            do {
	                line = br.readLine();
	                if (line != null)
	                {
	                	newEmps.add(parseEmployeeInfoLine(line));
	                }
	            } while (line != null);	            	            
	            br.close();
	        }
	        catch (IOException e)
	        {
	        	JOptionPane.showMessageDialog(null, e);
	        }
	        catch(InvalidEmployeeException e)
	        {
	        	JOptionPane.showMessageDialog(null, e);
	        }
	        finally
	        {
	        	JOptionPane.showMessageDialog(null, "Complete");
	        }
	        return newEmps;
	    }
	    //this will read the lines within the file and return an employee of whatever type
	    //determined by the first word
	    public static Employee parseEmployeeInfoLine(String line) throws InvalidEmployeeException {
	        String name, id, empType, departmentID = null;
	        int hireDate,goalsMet = 0;
	        double salary, hourlyRate,weeklyEarning = 0;
	        Employee newEmployee = null;
	        String result = null;

	        if (line != null) {
	            Scanner sc = new Scanner(line).useDelimiter("\\s*#\\s*");

	            try {
	                name = sc.next();
	                if(name.contains("Production"))
	                {
	                	id = sc.next();
	                	hireDate = sc.nextInt();
	                	weeklyEarning = sc.nextDouble();
	                	hourlyRate = sc.nextDouble();
	                	newEmployee = new ProductionWorker(name, id, hireDate, weeklyEarning);
	                	
	                }
	                else if(name.contains("Shift"))
	                {
	                	id = sc.next();
	                	hireDate = sc.nextInt();
	                	weeklyEarning = sc.nextDouble();
	                	salary = sc.nextDouble();
	                	goalsMet = sc.nextInt();
	                	newEmployee = new ShiftSupervisor(name, id, hireDate,
	                									  salary,goalsMet,weeklyEarning);
	    	        }
	                else if(name.contains("MANAGER"))
	                {
	                	id = sc.next();
	                	hireDate = sc.nextInt();
	                	weeklyEarning = sc.nextDouble();
	                	salary = sc.nextDouble();
	                	departmentID = sc.next();
	                	newEmployee = new Manager(name, id, hireDate, salary
	                							  ,departmentID,weeklyEarning);
	                }
	                
	                
	             
	             } catch (InputMismatchException e) 
	             {
	            	JOptionPane.showMessageDialog(null, line +"\n"+ e);
	                result = null;
	            }

	            sc.close();
	        }
	        return newEmployee;
	    }
	    
	    public static ArrayList<Shift> readShiftInfo(String filename)  {
	    	ArrayList<Shift> newShifts = new ArrayList<Shift>();
	    	String line = "";
	        try {
	            BufferedReader br = new BufferedReader(new FileReader(filename));	            
	            do {
	                line = br.readLine();
	                if (line != null) 
	                {
	                    newShifts.add(parseShiftInfoLine(line));
	                }
	            } while (line != null);	            	            
	            br.close();
	        }
	        catch (IOException e)
	        {
	        	JOptionPane.showMessageDialog(null, e);
	        }
	        catch(InvalidEmployeeException e)
	        {
	        	JOptionPane.showMessageDialog(null, e);
	        }
	        finally
	        {
	            JOptionPane.showMessageDialog(null, "Complete");
	        }
	        return newShifts;
	    }

	    public static Shift parseShiftInfoLine(String line) throws InvalidEmployeeException {
	        String day = null;
	        String goals, shift = null;
	        ArrayList<Employee> shiftEmps = new ArrayList<Employee>();
	        
	        Shift result = null;

	        if (line != null) {
	            Scanner sc = new Scanner(line).useDelimiter("\\s*#\\s*");
	            if(line.contains("GOALS"))
	            {
	            	try
	            	{
	            		day = sc.next();
	            		shift = sc.next();
	            		goals = sc.next();
	            		try
	            		{
	            			result = new Shift(day, shift, goals);
	            		}
	            		catch(InvalidShiftException e)
	            		{
	            			JOptionPane.showMessageDialog(null, e);
	            		}
	            	}
	            	catch(InputMismatchException e)
	            	{
	            		JOptionPane.showMessageDialog(null,line +" \n" + e);
	            		
	            	}
	            }
	            else
	          {		
	            try
	            {
	            	shiftEmps.add(parseEmployeeInfoLine(line));
	                
	            } catch (InputMismatchException e)
	            {
	                JOptionPane.showMessageDialog(null, line +"\n"+ e);
	                result = null;
	            }
	          }
	            sc.close();
	            result.getEmployees().addAll(shiftEmps);
	        }
	        return result;
	    }
	  //Craig: This is the writing method for all current shifts
	  //The intent is to use the getPath() method for the filename
	  //And use the PayrollGUI arraylist getter as the ArrayList param
	  public static void writeAllShifts(String filename,ArrayList<Shift> writeList) {



	  	try {
	  		FileWriter fw = new FileWriter(new File(filename));
	  		//uses the write(string) method as opposed to the write(char[]) version
	  		//the latter is inefficient for the amount of writing we have to do
	  		for(Shift s: writeList)
	  		{
	  			fw.write(s.readableString());
	  		}

	  		fw.close();
	  	} catch (IOException e) {
	  		JOptionPane.showMessageDialog(null, e);
	  	} finally {
	  		JOptionPane.showMessageDialog(null, "We are done");
	  	}
	  }
	  public static void writeAllEmployee(String fileName, ArrayList<Employee> saveTheseGuys)
	  {
		  try {
		  		FileWriter fw = new FileWriter(new File(fileName));
		  		//uses the write(string) method as opposed to the write(char[]) version
		  		//the latter is inefficient for the amount of writing we have to do
		  		for(Employee e: saveTheseGuys)
		  		{
		  			fw.write(e.toStringTwo()+ "\r\n");
		  		}

		  		fw.close();
		  	}
		  	catch (IOException e) 
		  	{
		  		JOptionPane.showMessageDialog(null, e);
		  	}
		  	finally 
		  	{
		  		JOptionPane.showMessageDialog(null, "We are done");
		  	}
	  }
	  
	  public static void writeEmployee(String filename,Employee saveThisGuy) {



	  	try {
	  		FileWriter fw = new FileWriter(new File(filename));
	  		//uses the write(string) method as opposed to the write(char[]) version
	  		//the latter is inefficient for the amount of writing we have to do

	  		fw.write(saveThisGuy.toStringTwo());

	  		fw.close();
	  	}
	  	
	  	catch (IOException e) {
	  		JOptionPane.showMessageDialog(null, e);
	  	} 
	  	finally
	  	{
	  		JOptionPane.showMessageDialog(null, "We are done");
	  	}
	  }
	  //Craig: I moved the system stuff Nick did for finding a path to write to
	  //into this method so that it could be used for multiple writing/reading methods
	  public static void writeShift(String filename, Shift thisShift)
	  {
	  	try
	  	{
	  		FileWriter fw = new FileWriter(new File(filename));
	  		//uses the write(string) method as opposed to the write(char[]) version
	  		//the latter is inefficient for the amount of writing we have to do
	  		
	  		fw.write(thisShift.readableString());

	  		fw.close();
	  	} catch (IOException e) {
	  		JOptionPane.showMessageDialog(null, e);
	  	} finally {
	  		JOptionPane.showMessageDialog(null, "We are done");
	  	}
	  } 


	  public static String getPath(String ending)
	  {
	  	System.getProperty("user.home"); // get current user home dir
	  	String userHomeFolder = System.getProperty("user.home");
	  	String path = (userHomeFolder + ending);
	  	return path;
	  }

}
