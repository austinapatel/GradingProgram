import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class WritePropertiesFile {
	public static void write() {
		try {
			Properties properties = new Properties();
			properties.setProperty("db.url", "jdbc:mysql://98.248.155.100:3306/gradingprogram?autoReconnect=true&useSSL=false");
			properties.setProperty("db.user", "cheetahgod");
			properties.setProperty("db.passwd", "Dave123");

			File file = new File("db.properties");
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.store(fileOut, "Data base properties");
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}