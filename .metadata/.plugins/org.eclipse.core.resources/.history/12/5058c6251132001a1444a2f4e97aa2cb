package HarGenerator.PerformanceFrameWork.Resources;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class GetUserValues {
	
	public static String properties(String key) throws Exception {
		File fl = new File(ResourcePaths.interactableFile);
		FileInputStream file = new FileInputStream(fl);
		Properties rpop = new Properties();
		rpop.load(file);
		String data = rpop.getProperty(key);
		return data;

}

}
