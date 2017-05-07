package sidproj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadData {

	private static Scanner scanner;
	private static final Logger LOGGER = Logger.getLogger(ReadData.class.getSimpleName());

	public static String readData(String fileName){
		String data = "";
		try {
		File file = new File(fileName);
		scanner = new Scanner(file);
		while(scanner.hasNext()){
			data = data + scanner.nextLine() + "\n";
		}
		}
		catch(FileNotFoundException e){
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		scanner.close();
		return data;
	}
}