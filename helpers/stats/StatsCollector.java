package helpers.stats;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import simulation.Patient;


// information 
public class StatsCollector {
	
	public static int totalMachineCount;

	public static HashMap<String, ArrayList<MachineStateStats>> machineStates = new HashMap<String, ArrayList<MachineStateStats>>();
	public static Map<String, Double> patientArrivals = new HashMap<String, Double>();
	//public static List<PatientStats> patients = new ArrayList<PatientStats>();
	public static Map<UUID, PatientStats> patients = new HashMap<UUID, PatientStats>();
	
	final static String outputFilePath = "C:/Users/demoadmin/Documents/study/";
	//C:\Users\demoadmin\Documents\study	
	
    /**  collectEvent to collect event statistics for all events except event new patient is queued
    /** event collector needs machine ID **/
	/*public static void collectEvent(double elapsedTime, int stateType, UUID patientID, String dockName, String machineName, String eventType ) {
		processEvent(elapsedTime, stateType, patientID, dockName, machineName, eventType ) ;
		System.out.println("6 params in statscollector, collectEvent: "+machineName);
	}*/
	//                             (time,               999,          getPatientID(),  station , "event type in patient");
	public static void collectEvent(double elapsedTime, int stateType, UUID patientID,  String machineName , String eventType, String eventName) {
		String dockName = "empty dockName";
		processEvent(elapsedTime, stateType, patientID, dockName, machineName, eventType, eventName ) ;
		//System.out.println("5 params in statscollector, collectEvent: "+machineName);
	}
	private static void processEvent(double elapsedTime, int stateType, UUID patientID, String dockName, String machineName, String eventType, String eventName) {

		//UUID eventID = UUID.randomUUID();		 
		MachineStateStats currentMachineState = new MachineStateStats();
		currentMachineState.elapsedTime = elapsedTime;
		currentMachineState.stateType = stateType;
		currentMachineState.patientID = patientID;
		currentMachineState.dockName = dockName;
		currentMachineState.eventType = eventType;
		currentMachineState.eventName = eventName;
		//currentMachineState.eventID = eventID;
	
		if(machineStates.containsKey(machineName)) {
			machineStates.get(machineName).add(currentMachineState);
		}
		else {
			ArrayList<MachineStateStats> machineStats = new ArrayList<MachineStateStats> ();
			machineStats.add(currentMachineState);
			machineStates.put(machineName, machineStats);
		}
	}
	
	/** patientType can have 3 values: A1, A2, B **/
	/** method to collect patient with ID**/
	public static void collectPatient(Patient product, double waitingTime,  String patientType) {	
		if(patients.containsKey(product.getPatientID())) {
			//PatientStats patientStats = new PatientStats();
			patients.get(product.getPatientID()).waitingTime=waitingTime;
			patients.get(product.getPatientID()).patientID = product.getPatientID();
			patients.get(product.getPatientID()).patientType = patientType;
		}
		else {
			PatientStats patientStats = new PatientStats();
			patientStats.waitingTime=waitingTime;
			patientStats.patientID = product.getPatientID();
			patientStats.patientType = patientType;
			patients.put( product.getPatientID(), patientStats);
		}		
	}
	/** patientType can have 3 values: A1, A2, B **/
	/** method to collect patient with ID**/
	public static void collectPatient(Patient product, double arrivalTimestamp,  int patientType) {	
		if(patients.containsKey(product.getPatientID())) {
			//PatientStats patientStats = new PatientStats();
			patients.get(product.getPatientID()).arrivalTimestamp=arrivalTimestamp;
			patients.get(product.getPatientID()).patientID = product.getPatientID();
			patients.get(product.getPatientID()).patientType =  Integer.toString(patientType);
		}
		else {
			PatientStats patientStats = new PatientStats();
			patientStats.arrivalTimestamp=arrivalTimestamp;
			patientStats.patientID = product.getPatientID();
			patientStats.patientType = Integer.toString(patientType);
			patients.put( product.getPatientID(), patientStats);
		}	
		
	}
	
	/*public static void collectPatient(String product, double arrivalTimestamp,  int patientType) {		
		if(patients.containsKey(product.getPatientID())) {
			//PatientStats patientStats = new PatientStats();
			patients.get(product.getPatientID()).arrivalTimestamp=arrivalTimestamp;
			patients.get(product.getPatientID()).patientID = product.getPatientID();
			patients.get(product.getPatientID()).patientType =  Integer.toString(patientType);
		}
		else {
			PatientStats patientStats = new PatientStats();
			patientStats.arrivalTimestamp=arrivalTimestamp;
			patientStats.patientID = product.getPatientID();
			patientStats.patientType = Integer.toString(patientType);
			patients.put( product.getPatientID(), patientStats);
		}	
		
		
		//Patient patient = new Patient();
		//patient.patientName = product;
		PatientStats patientStats = new PatientStats();
		patientStats.arrivalTimestamp=arrivalTimestamp;
		patientStats.patientID = patient.getPatientID();
		patientStats.patientType = Integer.toString(patientType);
		patientStats.patientName ="";
		patients.add(patientStats);
	}*/
	
	/** method to collect patient without patient ID**/
	/*public static void collectPatient(Product product, double arrivalTimestamp, String patientType) {		
		PatientStats patient = new PatientStats();
		patient.arrivalTimestamp=arrivalTimestamp;
		patient.patientType = patientType;
		patients.add(patient);
	}*/
	
	//public static int[] idleMachineCount;
	// 3 types of events:
	// 1. new patient show up, 2. machine will finish its work
	// per source
	// 3. each ambulance adds event when to drop off
	//public static int patientqueue;
	//public static int[] events; // event type (new patient, ambulance arrives at patient, ... 
	//...ambulance finishes patient, ambulance drops off patient, event time
	//machine method execute
	
	/** method returns true if it successfully dumped the content of the variables to a file at the given path **/
	public static boolean dumpToFiles() {

		String machineFileName = "machines_"+ new SimpleDateFormat("yyyyMMddHHmm").format(new Date())+".csv";
		String patientFileName = "patients_"+ new SimpleDateFormat("yyyyMMddHHmm").format(new Date())+".csv";
		File machineFile = new File(outputFilePath+machineFileName);
		File patientFile = new File(outputFilePath+patientFileName);	
		
		/** Write machineFile**/
		BufferedWriter bf = null;
		try {
			  
            bf = new BufferedWriter(new FileWriter(machineFile));
  
            // iterate map entries
            bf.write("machine name, elapsed time, dockName, patientID, stateType, event type");
            bf.newLine();
            /** generate csv in the following format for one record per event: machinename, event time, patient name **/
            for (Map.Entry<String, ArrayList<MachineStateStats>> entry :
            	machineStates.entrySet()) {
  
                // put key and value separated by a colon             
                
                for(int i = 0; i < entry.getValue().size(); i++) {
                	   bf.write(entry.getKey() + ", ");
                	   bf.write( entry.getValue().get(i).elapsedTime + ", ");
                	   bf.write( entry.getValue().get(i).dockName + ", ");
                	   bf.write( entry.getValue().get(i).patientID + ", ");
                	   bf.write( entry.getValue().get(i).stateType+ ", ");
                	   bf.write( entry.getValue().get(i).eventType+ ", ");
                	   bf.write( entry.getValue().get(i).eventName+ "");
                	  // bf.write( entry.getValue().get(i).eventID.toString() );
                	   bf.newLine();
                  }
                // new line           
            }  
            bf.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
  
            try {
  
                // always close the writer
                bf.close();
            }
            catch (Exception e) {
            }
        }
		/** Write patientFile**/
		BufferedWriter bfPatient = null;
		try {
			  
			bfPatient = new BufferedWriter(new FileWriter(patientFile));
  
            // iterate map entries
			bfPatient.write("patientID,arrivalTimestamp, patient waiting time, patient type, patientName");
			bfPatient.newLine();
            /** generate csv in the following format for one record per event: patient, event time, patient name **/
           /* for (Map.Entry<String, ArrayList<MachineStateStats>> entry :
            	machineStates.entrySet()) {*/
			 for (Map.Entry<UUID, PatientStats> entry :
				 patients.entrySet()) {
	            	bfPatient.write(entry.getKey() + ", ");
	            	bfPatient.write( entry.getValue().arrivalTimestamp+ ", ");
	            	bfPatient.write( entry.getValue().waitingTime+ ", ");
	            	bfPatient.write( entry.getValue().patientType+ ", ");
	            	bfPatient.write( entry.getValue().patientName+ "");
	            	
	            	//patientName
	            	bfPatient.newLine();
	  
	            bfPatient.flush();
	        }}
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
  
            try {
  
                // always close the writer
            	bfPatient.close();
            }
            catch (Exception e) {
            }
        }
		return true;
	}
}
