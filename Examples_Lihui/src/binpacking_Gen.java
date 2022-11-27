import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

// *** Not to have any blank space after the data ***
public class binpacking_Gen {
	
	public static void binpacking_hard28Gen (String filePath, String resultPath) throws IOException{
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(filePath));
		// skip the first three lines
		br.readLine();
		String input;

		int file_count = 0;
		String num_a, num_b;
//		boolean writeStatus = true;
		while (true){
//			System.out.println(input+"lala");
//			input = br.readLine();
			num_a = br.readLine();
			num_b = br.readLine();
	
			if (num_a == null) {
				break;
			}
			else {
				String fileName = resultPath+Integer.toString(file_count)+".txt";
				FileWriter writer = new FileWriter(fileName);
				int nums_count = 0;
				writer.write("waescher");
				writer.write("\r\n");
				writer.write("ins"+" "+file_count);
				writer.write("\r\n");
				
				// some instanecs will have 1000 200. Don't know how to solve yet (May 1)
				writer.write(" 1000 160");
				writer.write("\r\n");
				while ( (input = br.readLine()) != null ) {
//					System.out.println("line35:"+input);
//					System.out.println(input==null);
					if (!input.substring(1,4).equals("BPP")){
//						System.out.println("line37:"+input);
						StringTokenizer st = new StringTokenizer(input);
						String num = st.nextToken();
						int iters = Integer.parseInt(st.nextToken());
						for (int i = 0; i < iters; i ++) {
							writer.write(num);
							System.out.println(num+" has been written");
							writer.write("\r\n");
							nums_count ++;
						}
					}
					else {
						file_count ++;
						System.out.println("************"+nums_count);
						break;
					}
				}
				System.out.println("*****out of loop*****");
				writer.close();
			}
			
		}
		br.close();
	}
	
	public static void binpacking_falkGen (String filePath, String resultPath, String[] fileNames) throws IOException{
		int file_count = 0;
		for (int i = 0; i < fileNames.length; i ++) {
			BufferedReader br = null;
			br = new BufferedReader(new FileReader(filePath+fileNames[i]));
			// skip the first two lines
			br.readLine();
			br.readLine();
			String input;
			// input_nums is a string including bin capacities and number of items
			String input_nums;
	
			while (true){
				input_nums = br.readLine();
				System.out.println("lala"+input_nums);
				if (input_nums == null) {
					break;
				}
				else {
					String fileName = resultPath+Integer.toString(file_count)+".txt";
					System.out.println("kkkkkkkkkk "+file_count+" .txt created");
					FileWriter writer = new FileWriter(fileName);
					writer.write("falkenauer");
					writer.write("\r\n");
					writer.write("ins"+" "+file_count);
					writer.write("\r\n");
					StringTokenizer st = new StringTokenizer(input_nums);
					int bin_capa = Integer.parseInt(st.nextToken());
					int num_items = Integer.parseInt(st.nextToken());
					writer.write(" "+bin_capa+" "+num_items);
					writer.write("\r\n");
					
					while ( (input = br.readLine()) != null ) {
						if (!input.substring(0,2).equals(" u") && !input.substring(0,2).equals(" t")) {
							writer.write(input);
//							System.out.println(input+" has been written");
							writer.write("\r\n");
							
						}
						else {
							System.out.println("*****start of another instance*****");
//							file_count ++;
							break;
						}
					}
					System.out.println("*****out of loop*****");
					writer.close();
					file_count ++;
				}
				
			}
			br.close();
		}
	}
	
	public static void main(String[] args) throws IOException {
		String filePath_hard28 = "instances/binpack_hard28/hard28";
		String resultPath_hard28 = "instances/binpacking/";
		String filePath_falk = "instances/binpack_falkenauer/";
		String resultPath_falk = "instances/binpacking/";
		
		// skip "binpack1.txt" because it contains 2.txt, 3.txt etc.
		String[] falkName = {"binpack2.txt", "binpack3.txt", "binpack4.txt"};
//								"binpack5.txt", "binpack6.txt", "binpack7.txt", "binpack8.txt"};
		
		
//		binpacking_hard28Gen(filePath_hard28, resultPath_hard28);
		
		binpacking_falkGen(filePath_falk, resultPath_falk, falkName);
	}
}



