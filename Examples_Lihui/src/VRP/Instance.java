package VRP;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Instance
{
	private ArrayList<Location> demands = new ArrayList<Location>();
	private String instanceName;
	private int vehicleNumber;
	private int vehicleCapacity;

	public Instance(int id)
	{

//		String fileName = "data/vrp/";
//		if(id<17)
//		{
//			fileName = fileName+"C/C";
//			if(id<9)
//			{
//				fileName = fileName+"10"+(id+1)+".txt";
//			}
//			else
//			{
//				fileName = fileName+"20"+(id-8)+".txt";
//			}
//		}
//		else if(id<40)
//		{
//			fileName = fileName+"R/R";
//			if(id<26)
//			{
//				fileName = fileName+"10"+(id-16)+".txt";
//			}
//			else if(id<29)
//			{
//				fileName = fileName+"11"+(id-26)+".txt";
//			}
//			else if(id<38)
//			{
//				fileName = fileName+"20"+(id-28)+".txt";
//			}
//			else
//			{
//				fileName = fileName+"21"+(id-38)+".txt";
//			}
//		}
//		else
//		{
//			fileName = fileName+"RC/RC";
//			if(id<48)
//			{
//				fileName = fileName+"10"+(id-39)+".txt";
//			}
//			else
//			{
//				fileName = fileName+"20"+(id-47)+".txt";
//			}
//		}
		String pathName = "instances/vrp/";
		String[] instanceNames = getFileName();
		String fileName = pathName + instanceNames[id];
		System.out.println("Instance line71: "+fileName);
		
		BufferedReader reader = null;
		try	{
			reader = new BufferedReader(new FileReader(new File(fileName)));
		} catch (FileNotFoundException a) {
			try {
				InputStream fis = this.getClass().getClassLoader().getResourceAsStream(fileName); 
				reader = new BufferedReader(new InputStreamReader(fis));	
			} catch(NullPointerException n) {
				System.err.println("cannot find file " + fileName);
				System.exit(-1);
			}
		}
			
		try
		{
			instanceName = reader.readLine();
			reader.readLine();
			reader.readLine();
			reader.readLine();
			StringTokenizer info = new StringTokenizer(reader.readLine());
			vehicleNumber = Integer.parseInt(info.nextToken());
			vehicleCapacity = Integer.parseInt(info.nextToken());
			reader.readLine();
			reader.readLine();
			reader.readLine();
			reader.readLine();
			String line = "";
			while((line = reader.readLine()) != null)
			{
				info = new StringTokenizer(line);
				Location loc = new Location(Integer.parseInt(info.nextToken()), Integer.parseInt(info.nextToken()), Integer.parseInt(info.nextToken()), Integer.parseInt(info.nextToken()), Integer.parseInt(info.nextToken()), Integer.parseInt(info.nextToken()), Integer.parseInt(info.nextToken()));
				demands.add(loc);
			}
			/*
			System.out.println("Instance name is " + instanceName + ", there are " + vehicleNumber + " vehicles with capacity of " + vehicleCapacity);
			for(int i=0; i<demands.size(); i++)
			{
				Location l = demands.get(i);
				System.out.println("For location " + l.getId() + ", at (" + l.getXCoord() + "," + l.getYCoord() + "), demand is " + l.getDemand() + ", ready time is " + l.getReadyTime() + ", due time is " + l.getDueDate() + " and service time is " + l.getServiceTime());
			}
			 */
		}
		catch(IOException e)
		{
			System.out.println("Exception found: " + e);
			System.out.println("Could not load instance, or instance does not exist");
			System.exit(-1);
		}
	}
	public static String[] getFileName() {
		String path = System.getProperty("user.dir") + "/instances/vrp/"; //path
		Stack<String> instanceStack = new Stack<>();
		File f = new File(path);
		if (!f.exists()) {
			System.out.println(path + " not exists");
		    return null;
		}
		else {
			getAllFiles(f, instanceStack);
		}
		int len = instanceStack.size();
		String[] instanceNames = new String[len];
		for (int j = 0; j < instanceNames.length; j ++) {
			instanceNames[j] = instanceStack.pop();
//			System.out.println(instanceNames[j]+" is pop out of the stack and into the result array");
		}
		return instanceNames;
	}

	public static void getAllFiles(File file, Stack<String> instanceNameStack) {
		File fa[] = file.listFiles();
        for (int i = 0; i < fa.length; i ++) {
        	if (fa[i].isDirectory()) {
        		getAllFiles(fa[i], instanceNameStack);	
        	}
        	else {
            	if (!fa[i].getName().equals(".DS_Store") && !fa[i].getName().substring(0,4).equals("read")) {
            		int nameIndex = fa[i].getParent().indexOf("vrp");
            		String rootName = fa[i].getParent().substring(nameIndex+"vrp".length()+1);
            		String resultName = rootName + "/" + fa[i].getName();
//            		String[] rootArr = fa[i].getParent().split("/");           		
//            		String resultName = rootArr[rootArr.length-1]+"/"+fa[i].getName();
//            		System.out.println(resultName+" "+"is put into stack");
            		instanceNameStack.push(resultName);
            	}
            }
        }
//	public static String[] getFileName() {
//        String path = System.getProperty("user.dir") + "/instances/vrp"; //path
//        File f = new File(path);
//        if (!f.exists()) {
//            System.out.println(path + " not exists");
//            return null;
//        }
//        else {
//        	File[] fa = f.listFiles();
////        	String[] instanceNames = new String[fa.length-1];
////        	for (int i = 0, j = 0; i < instanceNames.length; i ++) {
////        		if (fa[i].getName().equals(".DS_Store")) {
////        			j = i + 1;
////        		}
////        		else {
////        			j = i;
////        		}
////				instanceNames[i] = fa[j].getName();
////        	}
//        	int len = 0;
//            Stack<String> instanceStack = new Stack<>();
//            for (int i = 0; i < fa.length; i ++) {
//            	if (!fa[i].getName().equals(".DS_Store")) {
//            		instanceStack.push(fa[i].getName());
//            		len ++;
////            		System.out.println(fa[i].getName()+" is put into stack"+"\n");
//            	}
//            }
//            String[] instanceNames = new String[len];
//            for (int j = 0; j < instanceNames.length; j ++) {
////            	This assumes that instanceNames.length == length of stack
//            	instanceNames[j] = instanceStack.pop();
////            	System.out.println(instanceNames[j]+" is pop out of the stack and into the result array");
//            }
//            return instanceNames;
//        }
	}
	public ArrayList<Location> getDemands() {
		return demands;
	}

	public void setDemands(ArrayList<Location> demands) {
		this.demands = demands;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public int getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(int vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public int getVehicleCapacity() {
		return vehicleCapacity;
	}

	public void setVehicleCapacity(int vehicleCapacity) {
		this.vehicleCapacity = vehicleCapacity;
	}
}
