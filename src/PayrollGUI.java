

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;


public class PayrollGUI implements java.io.Serializable {
	// Craig Fee,Joe Cardona, Nick Silvestri
	private JFrame frame;
	private static String ABOUT_US_MESSAGE = "This program allows you to form a week populated with shifts"
			+ "\nit allows you to add an employee of any type to the shift of your choosing"
			;
	public static String DAYSHIFTSTRING = "Day Shift";
	public static String NIGHTSHIFTSTRING = "Night Shift";
	private static int CURRENT_YEAR = Employee.CURRENT_YEAR;
	private static int FOUNDED_YEAR = 1970;
	private static enum ErrorCode
	{
		NO_ERROR, HIT_CANCEL, WRONG_YEAR_RANGE, WRONG_YEAR_TYPE, WRONG_ID,NO_NAME;
	}

	public static PayrollGUI g;
	// ArrayList of the week needed to add all of the employees
	private ArrayList<Shift> shiftWeek = makeWeek();

	// default constructor
	public PayrollGUI()
	{
		makeFrame();
	}

	private static ArrayList<Shift> makeWeek() 
	{
		return Main.makeWeek();
	}

	public static void main(String[] args) 
	{
		
		PayrollGUI g = new PayrollGUI();
	}

	// Each component type has its own method for cohesion
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void makeFrame() {

		// containers and panes for the frame
		// ******************************************************
		frame = new JFrame("Shift Manager");
		Container contentPane = frame.getContentPane();
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container borderContainer = new Container();
		borderContainer.setLayout(new BorderLayout());
		contentPane.add(borderContainer);
		Container eastContainer = new Container();
		Container westContainer = new Container();
		Container buttonContainer = new Container();
		Container northContainer = new Container();
		northContainer.setLayout(new FlowLayout());
		eastContainer.setLayout(new FlowLayout());
		westContainer.setLayout(new FlowLayout());
		buttonContainer.setLayout(new FlowLayout());
		
		borderContainer.add(northContainer,BorderLayout.NORTH);
		borderContainer.add(eastContainer, BorderLayout.EAST);
		borderContainer.add(westContainer, BorderLayout.WEST);
		

		// ******************************************************
		// View Menu items are separated into 2 sub menus to keep the interface
		// clean

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		//MenuBar Items
		// ****************************************************
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenu importExportMenu = new JMenu("Import/Export");
		fileMenu.add(importExportMenu);

		JMenuItem fileSave = new JMenuItem("Save");
		fileMenu.add(fileSave);

		JMenuItem fileNew = new JMenuItem("New Project");
		fileMenu.add(fileNew);

		JMenuItem fileOpen = new JMenuItem("Open");
		fileMenu.add(fileOpen);

		JMenuItem exportAllToFile = new JMenuItem("Export all Data");
		importExportMenu.add(exportAllToFile);
		
		JMenuItem exportAllEmployeeToFile = new JMenuItem("Export all Employees");
		importExportMenu.add(exportAllEmployeeToFile);
		
		JMenuItem importShiftFromFile = new JMenuItem("Import all Shifts");
		importExportMenu.add(importShiftFromFile);
		
		JMenuItem importEmployeeFromFile = new JMenuItem("Import all Employees");
		importExportMenu.add(importEmployeeFromFile);
		
		JMenuItem fileQuit = new JMenuItem("Quit");
		fileMenu.add(fileQuit);

		
		// ****************************************************

		JMenu viewMenu = new JMenu("View");
		menuBar.add(viewMenu);
		// Sub menu for Employee information
		JMenu viewEmployees = new JMenu("Employee Data");
		viewMenu.add(viewEmployees);

		JMenuItem viewWorkers = new JMenuItem("View ProductionWorkers");
		viewEmployees.add(viewWorkers);

		JMenuItem viewSupes = new JMenuItem("View Supervisors");
		viewEmployees.add(viewSupes);

		JMenuItem viewManagers = new JMenuItem("View Managers");
		viewEmployees.add(viewManagers);
		// Sub menu for Shift Information
		// ****************************************************
		JMenu shiftDataMenu = new JMenu("Shift Statistics");
		viewMenu.add(shiftDataMenu);

		JMenuItem viewAllShiftData = new JMenuItem("View All Shift Data");
		shiftDataMenu.add(viewAllShiftData);

		JMenuItem viewShiftWorkers = new JMenuItem("View Shift Production Workers");
		shiftDataMenu.add(viewShiftWorkers);

		JMenuItem viewShiftSupes = new JMenuItem("View Shift Supervisors");
		shiftDataMenu.add(viewShiftSupes);
		// ****************************************************
		
		
		
		
		// ****************************************************
		
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);

		JMenuItem about = new JMenuItem("About");
		helpMenu.add(about);
		about.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
				JOptionPane.showMessageDialog(null, ABOUT_US_MESSAGE,
						"About Us", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// ****************************************************
	

		// ****************************************************



		final JComboBox empTypeDropDown = new JComboBox();
		empTypeDropDown.addItem("Production Worker");
		empTypeDropDown.addItem("Shift Supervisor");
		empTypeDropDown.addItem("Manager");
		
		

		// ****************************************************
		// Information stored in textboxes and drop downs will
		// be added to selected shift
		JButton addEmployeeToShift = new JButton("Add Employee");
		northContainer.add(addEmployeeToShift, BorderLayout.NORTH);

		JButton addRandomEmployeeToShift = new JButton("Add Random");
		northContainer.add(addRandomEmployeeToShift, BorderLayout.NORTH);

		JButton addNewShift = new JButton("Add New Shift");
		northContainer.add(addNewShift);
		
		JButton showAllShiftData = new JButton("Shift Data");
		westContainer.add(showAllShiftData);
		
		
		
		JButton clear = new JButton("Clear");
		eastContainer.add(clear);
		

		JButton deleteShift = new JButton("Delete Shift");
		northContainer.add(deleteShift);
		// ****************************************************

		final JTextArea mainTextArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(mainTextArea);
		mainTextArea.setLineWrap(true);
		mainTextArea.setWrapStyleWord(true);
		scrollPane.setPreferredSize(new Dimension(500, 350));
		borderContainer.add(scrollPane, BorderLayout.CENTER);
		// ****************************************************

		showAllShiftData.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{

				mainTextArea.setText("");
				for (int i = 0;i < shiftWeek.size();i++)
				{
					mainTextArea.append(shiftWeek.get(i).toString());					
				}
			}
		});
		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				mainTextArea.setText("");
			}
		});
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//Methods that add all of the actionListeners
		addTextFileMenuActionListeners(exportAllToFile, importEmployeeFromFile, exportAllEmployeeToFile,
				importShiftFromFile);
		addNewShiftButtonActionListener(addNewShift, deleteShift);

		addEmployeeButtonActionListeners(addEmployeeToShift);
		addViewMenuItemActionListeners(viewWorkers,viewSupes,viewManagers,viewAllShiftData,
				viewShiftWorkers,viewShiftSupes);
		
		addFileMenuItemActionListeners(fileSave, fileOpen, fileQuit); 
		
		// ***************************************************************************************

		// adds a random employee of a type chosen in the empTypeDropDown to a
		// random shift within
		// the shiftWeek arrayList
		addRandomEmployeeToShift.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				int typeResponse = JOptionPane.showConfirmDialog(null, empTypeDropDown, "Employee of type", JOptionPane.OK_CANCEL_OPTION);
				if(!(typeResponse == JOptionPane.CANCEL_OPTION ||typeResponse == JOptionPane.CLOSED_OPTION))
				{
					int randomShift = Main.r.nextInt(shiftWeek.size());
					shiftWeek
						.get(randomShift)
						.getEmployees()
						.add(Shift.addRandomEmployee(empTypeDropDown
								.getSelectedItem().toString()));

				mainTextArea.setText(shiftWeek.get(randomShift).toString());
				}
			}
		});

		frame.pack();
		frame.setVisible(true);
	}

	private void addNewShiftButtonActionListener(JButton shiftButton, JButton delete)
	{
		final JComboBox<String> dayOrNightDropDown = new JComboBox<String>();
		dayOrNightDropDown.addItem("Day Shift");
		dayOrNightDropDown.addItem("Night Shift");

		final JComboBox<String> metGoalsDropDown = new JComboBox<String>();
		metGoalsDropDown.addItem("MET");
		metGoalsDropDown.addItem("NOT MET");
		
		final JComboBox shiftIndex = new JComboBox();
		for(int i = 0; i< shiftWeek.size();i++)
		{
			shiftIndex.addItem(i);
		}
		
		final JComboBox<Shift.WeekDay> weekDayDropDown = new JComboBox<Shift.WeekDay>(
				Shift.WeekDay.values());
		int paneResponse;

		final JPanel comboBox = new JPanel();
		comboBox.add(weekDayDropDown);
		comboBox.add(dayOrNightDropDown);
		comboBox.add(metGoalsDropDown);
		delete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				int paneResponse =  JOptionPane.showConfirmDialog(null, shiftIndex
						, "Delete which Shift Number", JOptionPane.OK_CANCEL_OPTION);
				if(!(paneResponse ==JOptionPane.CANCEL_OPTION ||
						paneResponse == JOptionPane.CLOSED_OPTION))
				{
					shiftWeek.remove(shiftIndex.getSelectedItem());
				}
			}
		});
		
		shiftButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				
				int paneResponse =  JOptionPane.showConfirmDialog(null, comboBox
						, "Shift Info", JOptionPane.OK_CANCEL_OPTION);
				if(!(paneResponse ==JOptionPane.CANCEL_OPTION ||
						paneResponse == JOptionPane.CLOSED_OPTION))
				{
					try 
					{
						shiftWeek.add(new Shift((Shift.WeekDay) weekDayDropDown.getSelectedItem()
								,(String) dayOrNightDropDown.getSelectedItem()
								,(String) metGoalsDropDown.getSelectedItem()));
					}
					catch (InvalidShiftException e)
					{
						JOptionPane.showMessageDialog(null, e);
					}
				}
			}
		});
	}
	
	private void addTextFileMenuActionListeners(JMenuItem exportAllToFile,
			JMenuItem importEmployeeFromFile, JMenuItem exportAllEmployeeToFile,
			JMenuItem importShiftFromFile)
	{
	//********************************************************************************
		exportAllEmployeeToFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				JOptionPane.showMessageDialog(null, "You have saved a file to the directory\n"+
						ReadFile.employeePath);
				ArrayList<Employee> workerList = new ArrayList<Employee>();
				for (Shift thisShift : shiftWeek)
				{
					for (Employee thisEmployee : thisShift.getEmployees())
					{
							workerList.add(thisEmployee);
					}
				}
				ReadFile.writeAllEmployee(ReadFile.employeePath, workerList);
			}
		});

		//********************************************************************************
		exportAllToFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				JOptionPane.showMessageDialog(null, "You have saved a file to the directory\n"+
						ReadFile.shiftPath);
				ReadFile.writeAllShifts(ReadFile.shiftPath, shiftWeek);
			}
		});

		//********************************************************************************
		
		importShiftFromFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				shiftWeek.addAll(ReadFile.readShiftInfo(ReadFile.shiftPath));
				
			}
		});
		
		//********************************************************************************
		importEmployeeFromFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				final JComboBox<Shift.WeekDay> weekDayDropDown = new JComboBox<Shift.WeekDay>(
						Shift.WeekDay.values());

				final JComboBox<String> dayOrNightDropDown = new JComboBox<String>();
				dayOrNightDropDown.addItem("Day Shift");
				dayOrNightDropDown.addItem("Night Shift");
				
				final JPanel comboBox = new JPanel();
				comboBox.add(weekDayDropDown);
				comboBox.add(dayOrNightDropDown);
				int response = 0;

				response = JOptionPane.showConfirmDialog(null, comboBox ,"Add Employees from to which Shift?"
						,JOptionPane.OK_CANCEL_OPTION );
				
				if(!(response == JOptionPane.CANCEL_OPTION||
						response == JOptionPane.CLOSED_OPTION))
				{

					Shift foundShift = findShift((Shift.WeekDay)weekDayDropDown.getSelectedItem(),
							(String)dayOrNightDropDown.getSelectedItem());
					ArrayList<Employee> newEmps = new ArrayList<Employee>();
					newEmps = ReadFile.readEmployeeInfo(ReadFile.employeePath);
					for(int i = 0;i<newEmps.size();)
					{
						if(newEmps.get(i) == null)
						{
							newEmps.remove(newEmps.get(i));
						}
						else
						{
							i++;
						}
						
					}
					try 
					{
						foundShift.setWorkers(newEmps);
					} catch (InvalidShiftException e)
					{
						JOptionPane.showMessageDialog(null, e);
					}
				}
			}
		});

		//********************************************************************************
		
	}

	// Adds ActionListeners to all of the View Menu Items
	private void addViewMenuItemActionListeners(JMenuItem viewWorkers,
			JMenuItem viewSupes, JMenuItem viewManagers,
			JMenuItem viewAllShiftData, JMenuItem viewShiftWorkers,
			JMenuItem viewShiftSupes) {
		// ***************************************************************************************
		viewWorkers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ArrayList<ProductionWorker> workerList = new ArrayList<ProductionWorker>();
				for (Shift thisShift : shiftWeek) {
					for (Employee thisEmployee : thisShift.getEmployees()) {
						if (thisEmployee instanceof ProductionWorker) {
							workerList.add((ProductionWorker) thisEmployee);
						}
					}
				}
				String workerString = "";
				for (ProductionWorker thisWorker : workerList) {
					workerString += (thisWorker.toString() + "\n");
				}
				JTextArea textArea = new JTextArea(workerString);
				JScrollPane scrollPane = new JScrollPane(textArea);
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				scrollPane.setPreferredSize(new Dimension(500, 500));
				JOptionPane.showMessageDialog(null, scrollPane, "All Managers",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		// ***********************************************************************************
		viewSupes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ArrayList<ShiftSupervisor> superList = new ArrayList<ShiftSupervisor>();
				for (Shift thisShift : shiftWeek) {
					for (Employee thisEmployee : thisShift.getEmployees()) {
						if (thisEmployee instanceof ShiftSupervisor) {
							superList.add((ShiftSupervisor) thisEmployee);
						}
					}
				}
				String superString = "";
				for (ShiftSupervisor thisSupe : superList) {
					superString += (thisSupe + "\n");
				}
				JTextArea textArea = new JTextArea(superString);
				JScrollPane scrollPane = new JScrollPane(textArea);
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				scrollPane.setPreferredSize(new Dimension(500, 500));
				JOptionPane.showMessageDialog(null, scrollPane,
						"All Supervisors", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		// ***************************************************************************************

		viewManagers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ArrayList<Manager> managerList = new ArrayList<Manager>();
				for (Shift thisShift : shiftWeek) {
					for (Employee thisEmployee : thisShift.getEmployees()) {
						if (thisEmployee instanceof Manager) {
							managerList.add((Manager) thisEmployee);
						}
					}
				}
				String managerString = "";
				for (Manager thisManager : managerList) {
					managerString += (thisManager + "\n");
				}
				JTextArea textArea = new JTextArea(managerString);
				JScrollPane scrollPane = new JScrollPane(textArea);
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				scrollPane.setPreferredSize(new Dimension(500, 500));
				JOptionPane.showMessageDialog(null, scrollPane, "All Managers",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		viewAllShiftData.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// Shows all of the Employees on each shift with the help of an
				// OptionPane and a scroll function
				// tested with the addRandom button
				JTextArea textArea = new JTextArea(shiftWeek.toString());
				JScrollPane scrollPane = new JScrollPane(textArea);
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				scrollPane.setPreferredSize(new Dimension(500, 500));
				JOptionPane.showMessageDialog(null, scrollPane, "All Shifts",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	// Adds ActionListeners to all of the File Menu Items
	private void addFileMenuItemActionListeners(JMenuItem fileSave,
			JMenuItem fileOpen,JMenuItem fileQuit) {
		fileSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.getProperty("user.home"); // get current user home dir
				String userHomeFolder = System.getProperty("user.home");
				String path = (userHomeFolder + "\\payroll.ser");
				// Serialize data object to a byte array
				ByteArrayOutputStream bos = null;
				try {
					// Serialize data object to a file
					FileOutputStream flOut = new FileOutputStream(path);
					ObjectOutputStream out = new ObjectOutputStream(flOut);
					out.writeObject(g);
					out.close();

					bos = new ByteArrayOutputStream();
					out = new ObjectOutputStream(bos);
					out.writeObject(g);
					out.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "File saved to path: " + path, "File Saved",
						JOptionPane.INFORMATION_MESSAGE);
				// Get the bytes of the serialized object
				// byte[] buf = bos.toByteArray();
			}
		});

		fileOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.getProperty("user.home"); // get current user home dir
				String userHomeFolder = System.getProperty("user.home");
				String path = (userHomeFolder + "\\payroll.ser");

				try {
					// Read from disk using FileInputStream
					FileInputStream f_in = new FileInputStream(path);

					// Read object using ObjectInputStream
					ObjectInputStream obj_in = new ObjectInputStream(f_in);

					// Read an object
					PayrollGUI g = (PayrollGUI) obj_in.readObject();

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "No File Found.",
							"File Not Found", JOptionPane.ERROR_MESSAGE);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "File succesfully open: " + path, "File Restored",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		fileQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
					System.exit(0);
			};
		});
	}

	public void addEmployeeButtonActionListeners(JButton addEmployeeToShift)
	{
		addEmployeeToShift.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int yearInt = 0;
				ErrorCode errorCode;
				int empTextBoxResponse = 0;
				int shiftDayResponse = 0;
				
				final JComboBox<Shift.WeekDay> weekDayDropDown = new JComboBox<Shift.WeekDay>(
						Shift.WeekDay.values());

				final JComboBox dayOrNightDropDown = new JComboBox();
				dayOrNightDropDown.addItem("Day Shift");
				dayOrNightDropDown.addItem("Night Shift");

				final JComboBox empTypeDropDown = new JComboBox();
				empTypeDropDown.addItem("Production Worker");
				empTypeDropDown.addItem("Shift Supervisor");
				empTypeDropDown.addItem("Manager");
				
				

				final JPanel comboBox = new JPanel();
				comboBox.add(weekDayDropDown);
				comboBox.add(dayOrNightDropDown);
				comboBox.add(empTypeDropDown);
				//****************************************************************
				
				// Adds the text Fields for input of the Name, ID Number, and Hire year
				final JTextField employeeFirstName = new JTextField();

				final JTextField employeeLastName = new JTextField();

				final JTextField employeeID = new JTextField();

				final JTextField hireYear = new JTextField();

				final JPanel textBoxes = new JPanel();
				textBoxes.setLayout(new GridLayout(2, 4, 15, 15));
				textBoxes.add(new JLabel("First Name"));
				textBoxes.add(employeeFirstName);
				textBoxes.add(new JLabel("Last Name"));
				textBoxes.add(employeeLastName);
				textBoxes.add(new JLabel("ID Number"));
				textBoxes.add(employeeID);
				textBoxes.add(new JLabel("Hire Year"));
				textBoxes.add(hireYear);
				
				//*****************************************************************
				// int takes response given from the option pane
				// this makes it show just one pane and ask the user for input
				// once
				
				int empTypeResponse = JOptionPane.showConfirmDialog(null, empTypeDropDown, "Employee type", 
						JOptionPane.OK_CANCEL_OPTION);
				

				//if an employee type was selected then continue to enter an employee
				if (!(empTypeResponse == (JOptionPane.CANCEL_OPTION) ||
						empTypeResponse == JOptionPane.CLOSED_OPTION))
				{
					String type = (String) empTypeDropDown.getSelectedItem().toString();
					
					Employee newEmp = Shift.getNewEmployeeOfType(type);
					
					do {
						errorCode = ErrorCode.NO_ERROR;
						//put up input box
						empTextBoxResponse = JOptionPane.showConfirmDialog( null, textBoxes, "Employee Information",
								JOptionPane.OK_CANCEL_OPTION);
						
						//***************
						//verify data entered was correct
						//***************
						
						// verify YEAR  entered was correct
						// convert year from string to number and verify it is of correct format
						try 
						{
							if ((empTextBoxResponse == JOptionPane.CANCEL_OPTION) ||
									(empTextBoxResponse == JOptionPane.CLOSED_OPTION))
							{
								errorCode = ErrorCode.HIT_CANCEL;
							}
							else
							{
								newEmp.setHireYear(Integer.parseInt(hireYear.getText().toString()));
							}
							
						}
						//if it is nondigit then we will catch
						catch (java.lang.NumberFormatException e)
						{
							errorCode = ErrorCode.WRONG_YEAR_TYPE;
							JOptionPane.showMessageDialog(null, "Years only contain numbers"
									,"Error",JOptionPane.ERROR_MESSAGE);
						}// end catch block
						catch(InvalidEmployeeException e)
						
						{
							errorCode = ErrorCode.WRONG_YEAR_RANGE;
							JOptionPane.showMessageDialog(null, e,"Error",JOptionPane.ERROR_MESSAGE);
						}//end catch block
						
						// verify EMP ID  entered was correct
						try
						{
							if ((empTextBoxResponse == JOptionPane.CANCEL_OPTION) ||
									(empTextBoxResponse == JOptionPane.CLOSED_OPTION))
							{
								errorCode = ErrorCode.HIT_CANCEL;
							}
							else
							{
								newEmp.setID(employeeID.getText());
							}
						}
						catch(InvalidEmployeeException e)
						{
							errorCode = ErrorCode.WRONG_ID;
							JOptionPane.showMessageDialog(null, e,"Error",JOptionPane.ERROR_MESSAGE);
						}//end Catch block
						
						//verify if name is a correct value
						try
						{
							if ((empTextBoxResponse == JOptionPane.CANCEL_OPTION) ||
									(empTextBoxResponse == JOptionPane.CLOSED_OPTION))
							{
								errorCode = ErrorCode.HIT_CANCEL;
							}
							else
							{
							newEmp.setName(employeeFirstName.getText() + " "
									+ employeeLastName.getText());
							}
						}
						catch(InvalidEmployeeException e)
						{
							errorCode = ErrorCode.NO_NAME;
							JOptionPane.showMessageDialog(null, e,"Error",JOptionPane.ERROR_MESSAGE);
						}
						
						if ((empTextBoxResponse == JOptionPane.CANCEL_OPTION) ||
								(empTextBoxResponse == JOptionPane.CLOSED_OPTION))
						{
							errorCode = ErrorCode.HIT_CANCEL;
						}
						
//						
					
					// only proceed if input is correct or user canceled transaction	
					} while(errorCode != ErrorCode.HIT_CANCEL && errorCode != ErrorCode.NO_ERROR);	
				
					//if above input was correct then proceed with shift input
					if (errorCode == ErrorCode.NO_ERROR) {
						
					//shift imput section
						shiftDayResponse = JOptionPane.showConfirmDialog(null,
								comboBox, "Shift Time",
								JOptionPane.OK_CANCEL_OPTION);

						if (!(shiftDayResponse == JOptionPane.CANCEL_OPTION)
								|| (shiftDayResponse == JOptionPane.CLOSED_OPTION))
						{
							
							Shift.WeekDay selectedDay = (Shift.WeekDay) (weekDayDropDown
									.getSelectedItem());

							String shiftTime = (String) dayOrNightDropDown
									.getSelectedItem().toString();

							Shift certainShift = findShift(selectedDay,
									shiftTime);

							
							
							certainShift.getEmployees().add(newEmp);
							
							
							JTextArea mainTextArea = new JTextArea();
							JScrollPane scrollPane = new JScrollPane(mainTextArea);
							mainTextArea.setLineWrap(true);
							mainTextArea.setWrapStyleWord(true);
							scrollPane.setPreferredSize(new Dimension(500, 350));
							mainTextArea.setText(certainShift.toString());
							JOptionPane.showMessageDialog(null, mainTextArea,"shift",JOptionPane.INFORMATION_MESSAGE);
							
						}// end if (!(shiftDayResponse
					}
					
				}//end of 	if (!(empTypeResponse == 	
			}//end of action listener
		});
	}
	
	
	public Shift findShift(Shift.WeekDay day, String dayOrNight) {
		Shift selectedShift = null;
		for (int i = 0; i < shiftWeek.size(); i++) {
			if (shiftWeek.get(i).getDay().equals(day)) 
			{
				if (shiftWeek.get(i).isDayShift()
						&& dayOrNight.equals(DAYSHIFTSTRING))
				{
					selectedShift = shiftWeek.get(i);
					return selectedShift;
				}
				else 
				{
					selectedShift = shiftWeek.get(i+1);
					return selectedShift;
				}
			}

		}
		return selectedShift;
	}
}
