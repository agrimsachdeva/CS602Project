package sidproj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WriteData {

	private static PrintWriter printWriter;
	private static final Logger LOGGER = Logger.getLogger(WriteData.class.getSimpleName());

	public static void writeData(String fileName, String data) {

		File file = new File(fileName);

		try {
			if (!file.exists()) {
				printWriter = new PrintWriter(file);
			} else {
				printWriter = new PrintWriter(new FileOutputStream(file, true));
			}
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		if(!data.contains("HAS ENTERED")) {
		printWriter.println(data);
		}
		printWriter.close();
	}

}