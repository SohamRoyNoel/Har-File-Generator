package HarGenerator.PerformanceFrameWork.GenerateHar;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

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

		try {
			File fl = new File("E:\\ME as QA\\Performance_Framework\\"+GetUserValues.properties("provideUniqueKey"));
			if (!fl.exists()) {
				fl.mkdir();
			}
			String absPath = fl.getAbsolutePath();

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
			capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, ResourcePaths.panthomPath);

			WebDriver driver = new PhantomJSDriver(capabilities);
			try {
				driver.get(GetUserValues.properties("urlTobeRedirected"));
				String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
				//HAR
				Har har = server.getHar();
				String FileName = "_"+GetUserValues.properties("provideUniqueKey")+".har";
				FileOutputStream fos = new FileOutputStream("E:\\ME as QA\\Performance_Framework\\"+GetUserValues.properties("provideUniqueKey")+"\\"+timeStamp+FileName);
				har.writeTo(fos);
				System.out.println("Done");
				server.stop();
				driver.close();
			} catch (Exception e) {	}
		} catch (Exception e1) {}


	}

}
