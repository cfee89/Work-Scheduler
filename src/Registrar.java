package asfd;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
public class Registrar {
	
	private String semester;
	private HashMap<String, HashSet<String>> courseSchedule;
	
	
	public static void main(String[] args)
	{
		Registrar university = new Registrar();
		
		//Adding students to classes, note some courses are repeating to test 
		//whether it will add them to the same key or not
		university.addStudent("OOPDA" , "Craig Fee");
		university.addStudent("OOPDA" , "Nick E.");
		university.addStudent("Intro to Mechanics" , "Craig Fee");
		university.addStudent("OOPDA" , "Sammy Sam");
		university.addStudent("OOPDA" , "Craig Fee");
		university.addStudent("Linear Algebra" , "Craig Fee");
		university.addStudent("Linear Algebra" , "Sammy Sam");
		university.addStudent("Foundations of CS" , "Craig Fee");
		university.addStudent("Creative Writing" , "Nick E.");
		university.addStudent("Anthropology" , "A. Aron");
		
		university.printStudent("Craig Fee");
		university.printOfferedClasses();
		
				
	}
	public Registrar()
	{
	courseSchedule = new HashMap<String,HashSet<String>>();
	semester = "Spring 2014";
	}
	//addStudent method can add a student to an existing class or
	//if a course doesnt exist it will create it and add the student
	public void addStudent(String course, String student)
	{
		//If a course isnt mapped it will create one to add to using 
		//the addCourse method
		if(!(courseSchedule.containsKey(course)))
		{
			
			addCourse(course);
			courseSchedule.get(course).add(student);
		}
		else
		{
			
			courseSchedule.get(course).add(student);
		
		}
		
	}
	//addCourse to make addStudent more cohesive, creates a new 
	//course on the HashMap with an empty set
	public void addCourse(String newCourse)
	{
		HashSet<String> newClass = new HashSet<String>();
		courseSchedule.put(newCourse, newClass);
	}
	public void printStudent(String student)
	{	
		System.out.println(student+"\n");
		//An arraylist to store the string sets the student name is found in
		//this allows you to match the sets with the appropriate keys later
		ArrayList<HashSet<String>> studentClasses = new ArrayList<HashSet<String>>();
		//This for loop goes through each of the course schedule's value sets 
		//to find the student
		for(HashSet<String> thisSet: courseSchedule.values())
		{
			//if the student is found in a set the set is added to the previous arraylist
			if(thisSet.contains(student))
			{
				studentClasses.add(thisSet);
			}
		}
		//This for loop goes through the set of keys belonging to the map
		//in order to match them to the sets in the arraylist
		for(String thisString:courseSchedule.keySet())
		{
			//if the set returned by the key matches one in the arraylist
			//then it is printed as a valid course for the student
			if(studentClasses.contains(courseSchedule.get(thisString)))
			{
				System.out.println(thisString);
			}
		}
	}
	//Prints all courses stored in the course schedule
	public void printOfferedClasses()
	{
		//This for loop goes through the entire set of keys in the hashmap
		//This loop will ensure all courses are printed and will automatically terminate
		//at the end of the set
		for(String thisString:courseSchedule.keySet())
		{
			System.out.println(thisString);
		}
	}
		
	
		
}

