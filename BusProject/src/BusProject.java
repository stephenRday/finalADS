import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BusProject {

	public static void main(String[] args) {
		//myFileReader("stops.txt");
		//myFileReader("transfers.txt");
		myFileReader("stop_times.txt");
		
		System.out.println("Finished");
	}

	public static void myFileReader(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			line = br.readLine();
			while(line != null) {
				//System.out.println(line);
				//stops stop = new stops(line);
				//transfers transfer = new transfers(line);
				stopTimes stopTime = new stopTimes(line);
				//System.out.println(stopTime.toString());
				line = br.readLine();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
