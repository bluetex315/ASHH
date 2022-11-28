import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class flowshopInstanceGen {
	
	public static void flowshopGen (String filePath, String resultSavePath) throws IOException{
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(filePath));
		// skip the first line
		br.readLine();
		
		String input;
		int count = 0;
//		boolean writeStatus = false;
		while ( (input = br.readLine()) != null ){
			System.out.println(input);
			if (input.equals("processing times :")) {
				String fileName = resultSavePath+Integer.toString(count)+".txt";
//				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
				FileWriter writer = new FileWriter(fileName);
//				boolean writeStatus = true;
				while ((input = br.readLine()) != null) {
					System.out.println("line30:"+input);
					if (!input.substring(0,6).equals("number")){
						writer.write(input);
						writer.write("\r\n");
						continue;
					}
					else {
						break;		
					}
				}
				// execute when read in "number"
				writer.close();
				count ++;
			}
			
//			else if (input.substring(0,6).equals("number")) {
//				writeStatus = false;
//				System.out.println("line40 close");
////				writer.flush();
//				writer.close();
////				fop.close();
//			}
//			else {
//				if (writeStatus) {
//					System.out.println("line47 write in"+input);
//					writer.write(input + "\n");
//					System.out.println("line49 write successful");
//				}
//			}
		}
		br.close();
	}
	

	public static void main(String[] args) throws IOException {
		String filePath = "instances/flowshop/tai500_20.txt";
		String resultSavePath = "instances/flowshop/";
		flowshopGen(filePath, resultSavePath);
	}
}



