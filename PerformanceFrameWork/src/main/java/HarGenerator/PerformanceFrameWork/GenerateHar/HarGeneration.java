package HarGenerator.PerformanceFrameWork.GenerateHar;

import java.io.FileOutputStream;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import HarGenerator.PerformanceFrameWork.Resources.GetUserValues;
import HarGenerator.PerformanceFrameWork.Resources.ResourcePaths;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;

public class HarGeneration {
	
	public static void GenerateHar() {
		
		 //BrowserMobProxy
        BrowserMobProxy server = new BrowserMobProxyServer();
        server.start(0);
        server.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());
        server.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        server.newHar("Google");
        
      //PHANTOMJS_CLI_ARGS
        ArrayList<String> cliArgsCap = new ArrayList<>();
        cliArgsCap.add("--proxy=localhost:"+server.getPort());
        cliArgsCap.add("--ignore-ssl-errors=yes");
        
        //DesiredCapabilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"E:\\Jars\\PanthomJS\\phantomjs.exe");
		
//		System.setProperty(ResourcePaths.driverType,ResourcePaths.driverLocation);
//		WebDriver driver = new InternetExplorerDriver(); /*new ChromeDriver()*/; 
//		System.out.println("Hello");
        WebDriver driver = new PhantomJSDriver(capabilities);
		try {
			driver.get(GetUserValues.properties("urlTobeRedirected"));
			//HAR
	        Har har = server.getHar();
	        FileOutputStream fos = new FileOutputStream("E:\\ME as QA\\Performance_Framework\\HAR-Information.har");
	        har.writeTo(fos);
	        server.stop();
	        driver.close();
		} catch (Exception e) {	}
	}

}