package HarGenerator.PerformanceFrameWork;

import java.io.File;

import HarGenerator.PerformanceFrameWork.GenerateHar.HarGeneration;
import HarGenerator.PerformanceFrameWork.Resources.GetUserValues;
import HarGenerator.PerformanceFrameWork.Resources.ResourcePaths;

public class App 
{
    public static void main( String[] args )
    {
    	File resultFolder = new File(ResourcePaths.resultHolder);
    	if (!resultFolder.exists()) {
			resultFolder.mkdir();
		}
    	
    	try {
			String urls = GetUserValues.properties("urlTobeRedirected");
			String uniqueKeys = GetUserValues.properties("provideUniqueKey");
			String iterators = GetUserValues.properties("iterationNumber");
			String[] TempURLS = urls.split(",");
			String[] TempKeys = uniqueKeys.split(",");
			String[] TempIterators = iterators.split(",");
			
			
			// Checking if URLS have same number of KEYS and same number of iteration number which can be different in value
			if (TempKeys.length==TempURLS.length && TempKeys.length == TempIterators.length) {
				for (int i = 0; i < TempKeys.length; i++) {
					String url = TempURLS[i];
					String keys = TempKeys[i];
					int itrs = Integer.parseInt(TempIterators[i]);
					for (int j = 0; j < itrs; j++) {
						HarGeneration.GenerateHar(url, keys.trim());
						System.out.println("Round : " + (j+1));
					}
					System.out.println("Cycle : " + (i+1));
				}
			}
		} catch (Exception e) {

		}
    }
}
