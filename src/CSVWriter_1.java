import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVWriter_1 {
	
	public void CSVWrite(String filename, ArrayList<String> results) throws FileNotFoundException{
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		try (PrintWriter writer = new PrintWriter(filename+".csv")) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("best_quality"+"\n");
			for (String i : results) {
				System.out.println(i);
				sb.append(i+"\n");
				}
			
			writer.write(sb.toString());
			
			System.out.println("writing to csv done!");
			
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
			
	}
}
