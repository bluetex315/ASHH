import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.StringTokenizer;

// *** Not to have any blank space after the data ***
public class kp_Gen {
	
	public static void Generator (String[] fileArr, String resultPath) throws IOException{
//		int file_count = 0;
		for (int i = 0; i < fileArr.length; i ++) {
			String filePath = "instances/kp_pisinger/"+fileArr[i];
			String [] abandon = fileArr[i].split("/");
			String fileName = abandon[abandon.length-1];
			// Cut off ".txt" for naming
			fileName = fileName.substring(0,fileName.length()-4);
			
			// create dir for each file
			String outputPath = resultPath + "/" + fileName;
			File folder = new File(outputPath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			
			System.out.println("line30"+fileName);
			System.out.println(filePath);
			
			BufferedReader br = null;
			br = new BufferedReader(new FileReader(filePath));
			br.readLine();
			String input;
			int fileCount = 0;
			
//			boolean writeStatus = true;
			while (true){
				
				String line1 = br.readLine(); // n
				String line2 = br.readLine(); // c
				String line3 = br.readLine(); // z
				String line4 = br.readLine(); // time
				
				if (line1 == null) {
					break;
				}
				
				else {
					String resultName = outputPath+"/"+fileCount+".txt";
					System.out.println(resultName);
					FileWriter writer = new FileWriter(resultName);
//					int nums_count = 0;
					StringTokenizer st1 = new StringTokenizer(line1);
					StringTokenizer st2 = new StringTokenizer(line2);
					st1.nextToken();
					st2.nextToken();
					long n = Long.parseLong(st1.nextToken());
					long c = Long.parseLong(st2.nextToken());
					writer.write("npieces:"+" "+n);
					writer.write("\r\n");
					writer.write("capacity:"+" "+c);
					writer.write("\r\n");
					
					while ( (input = br.readLine()) != null ) {
//						System.out.println("line35:"+input);
//						System.out.println(input==null);
						if (!input.startsWith("-")){
//							System.out.println("line37:"+input);
							String[] inputArr = input.split(",");
							for (int j = 0; j < inputArr.length; j++) {
								if (j != 3) {
									writer.write(inputArr[j]);
									writer.write(" ");
									System.out.println("line69"+" "+inputArr[j]);
								}
							}
							writer.write("\r\n");
						}
						else {
							br.readLine();
							br.readLine();
							System.out.println("************");
							fileCount ++;
							break;
						}
					}
					System.out.println("*****out of loop*****");
					writer.close();
				}
				
			}
			br.close();
		}
	}
	
	public static String[] getFileName(String path) {
//		String path = System.getProperty("user.dir") + "/instances//"; //path
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
//  			System.out.println(instanceNames[j]+" is pop out of the stack and into the result array");
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
          		int nameIndex = fa[i].getParent().indexOf("kp_pisinger");
          		String rootName = fa[i].getParent().substring(nameIndex+"kp_pisinger".length()+1);
          		String resultName = rootName + "/" + fa[i].getName();
//          		String[] rootArr = fa[i].getParent().split("/");           		
//          		String resultName = rootArr[rootArr.length-1]+"/"+fa[i].getName();
//         		System.out.println(resultName+" "+"is put into stack");
          		instanceNameStack.push(resultName);
          	}
          }
      }
	}
	
	public static void main(String[] args) throws IOException {
		String filePath_hard = "instances/kp_pisinger/hardinstances_pisinger";
		String resultPath_hard = "instances/kp/hard";
		
		String filePath_large = "instances/kp_pisinger/largecoeff_pisinger";
		String resultPath_large = "instances/kp/largecoef";
		
		String filePath_small = "instances/kp_pisinger/smallcoeff_pisinger";
		String resultPath_small = "instances/kp/smallcoef";

		String[] hard_Name= getFileName(filePath_hard);
		String[] largecoeff_Name = getFileName(filePath_large);
		String[] smallcoeff_Name = getFileName(filePath_small);
				
		Generator(hard_Name, resultPath_hard);
		Generator(largecoeff_Name, resultPath_large);
		Generator(smallcoeff_Name, resultPath_small);
	}
}



