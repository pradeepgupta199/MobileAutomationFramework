package launchMobileapplication;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import mobileUtility.MobileUtil;

public class CheckLaunchapplication {

	private static AndroidDriver driver;
	private static IOSDriver ioDriver;

	private static DesiredCapabilities capabilities;
	private static URL url;
	private static MobileUtil utils;
	private static WebDriverWait wait;

	public static void main(String[] args) {
		capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAUTOMATOR2");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
//		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);
//		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
//		capabilities.setCapability(MobileCapabilityType.UDID, deviceId);

//		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 3000);
//		capabilities.setCapability("adbExecTimeout", "200000");
		capabilities.setCapability(MobileCapabilityType.APP,
				"C:\\Users\\dell\\Downloads\\StarApp\\src\\main\\resources\\appFiles\\app.apk");
		try {
			url = new URL("http://127.0.0.1:4723/wd/hub");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver = new AndroidDriver(url, capabilities);

	}

}
