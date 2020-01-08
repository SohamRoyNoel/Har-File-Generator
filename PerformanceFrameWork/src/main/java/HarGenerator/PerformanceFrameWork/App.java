package HarGenerator.PerformanceFrameWork;

import HarGenerator.PerformanceFrameWork.GenerateHar.HarGeneration;
import HarGenerator.PerformanceFrameWork.Resources.GetUserValues;

public class App 
{
    public static void main( String[] args )
    {
    	try {
			int getIterationNumber = Integer.parseInt(GetUserValues.properties("iterationNumber"));
			for (int i = 0; i < getIterationNumber; i++) {
				HarGeneration.GenerateHar();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
}
