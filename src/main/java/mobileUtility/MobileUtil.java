package mobileUtility;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.io.Files;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;



@SuppressWarnings({ "unused", "deprecation" })
public class MobileUtil {
	private static AndroidDriver driver;
	private static IOSDriver ioDriver;

	private DesiredCapabilities capabilities;
	private URL url;
	private ExtentReports extReport;
	private ExtentTest extentLogger;
	private static MobileUtil utils;
	private WebDriverWait wait;
	private MobileUtil() {

	}
    
	public static MobileUtil getobj() {
		if (utils == null) {
			utils = new MobileUtil();
		}
		return utils;
	}

	public AppiumDriver launchPhone(String deviceType, String version, String deviceName, String deviceId,
			String uiAutomator, String appName) {

		if (deviceType.equalsIgnoreCase("Android")) {
			capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAUTOMATOR2");
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
	//		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);
	//		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
	//		capabilities.setCapability(MobileCapabilityType.UDID, deviceId);

			
	//		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 3000);
	//		capabilities.setCapability("adbExecTimeout", "200000");
			capabilities.setCapability(MobileCapabilityType.APP,System.getProperty("user.dir") +File.separator + appName+ ".apk");
			try {
				url = new URL("http://127.0.0.1:4723/wd/hub");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block   
				e.printStackTrace();
			}
			driver = new AndroidDriver(url, capabilities);
			extentLogger.log(Status.INFO, deviceType + " Driver is launched successFully  with " + appName + " APK"
					+ " Device(" + deviceName + ") , Version (" + version + ")");
		} else if (deviceName.equalsIgnoreCase("IOS")) {

			try {
				url = new URL("http://127.0.0.1:4723/wd/hub");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			capabilities = new DesiredCapabilities();
			ioDriver = new IOSDriver(url, capabilities);
			ioDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			wait = new WebDriverWait(driver, Duration.ofSeconds(60));

			extentLogger.log(Status.INFO, deviceType + " Driver is launched successFully");
		}
		holdOn(5);
		 driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		 extentLogger.log(Status.INFO, "ImplicitlyWait is applied for 20 seconds");
//		return ioDriver;
		return driver;

	}

	public WebDriver getDriver() {
		return driver;
	}

	public void inputData(WebElement element, String value) {
		try {
			
			driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			for(int i=0;i<80;i++) {
				try {
					element.sendKeys(value);
					break;
				} catch (NoSuchElementException e) {
					Actions act = new Actions(driver);
					element.clear();
					act.moveToElement(element).sendKeys(value).build().perform();
					e.printStackTrace();
				} catch (Exception e) {
					element.clear();
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].value='" + value + "';", element);

				}
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			
			extentLogger.log(Status.INFO, "Value(" + value + ") is  inputed SuccesFully in " + element.toString());

		} catch (Exception e) {
			extentLogger.log(Status.INFO, "Value(" + value + ") is  inputed SuccesFully in " + element.toString());
			e.getStackTrace();
		}
	}

	// ========================Click Perform =========================\\

	public void click(WebElement we, String elementname) {

		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
			for(int i=0;i<80;i++) {
				try {
					we.click();
					break;
				}catch(NoSuchElementException e) {
					Thread.sleep(500);
				}
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			extentLogger.log(Status.INFO, "Clicked performed on " + elementname);
		} catch (Exception e) {
			extentLogger.log(Status.WARNING, "Clicked is not able to performed on " + elementname);
		}
	}

	public void dynamicWait(long time) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
	}

	public Properties loadPropertyFile(String Filename) {

		Properties propObj = new Properties();
		File fle = new File(System.getProperty("user.dir") + "\\" + "src\\main\\resources\\propertiesFiles\\" + Filename
				+ ".properties");
		FileInputStream fis = null;

		try {

			fis = new FileInputStream(fle);
			propObj.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propObj;
	}

	public void holdOn(int timeInSeconds) {
		try {
			Thread.sleep(timeInSeconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pressBackButton() {
		try {
			driver.navigate().back();
			extentLogger.log(Status.INFO, " Navigate back action performed successFully");
		} catch (Exception e) {
			extentLogger.log(Status.WARNING, " not able to perform  back action ");
		}
	}

	public void scrollHorizontal(String text) {
		try {
			driver.findElement(MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
							+ text + "\").instance(0))"));
			extentLogger.log(Status.INFO, "Scroll Action performed successfully on " + text);

		} catch (Exception e) {
			extentLogger.log(Status.WARNING, "Not able to perform Scroll Action  on " + text);
		}
	}

	// ========================close and quit=========================\\

	public void quitDriver() {
		try {
			driver.quit();
			extentLogger.log(Status.INFO, "Driver has been closed successfully");
		} catch (Exception e) {
			extentLogger.log(Status.WARNING, "Driver has not been closed successfully");
		}
	}

	// ===============================ScreenShot======================================\\

	public String takeSnapshot(String methodname) {

		TakesScreenshot tcc = (TakesScreenshot) driver;
		File srcFile = tcc.getScreenshotAs(OutputType.FILE);
		File destFile = new File("ExtentReport/" + methodname + SimpleDateTimeFormate() + ".png");
		String path = destFile.getAbsolutePath();

		try {
			Files.copy(srcFile, destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}

	public void verifyText(WebElement element, String expectedText) {
		waitForVisible(element, 30);
		String actualText = element.getText();
		if (actualText.equalsIgnoreCase(expectedText)) {
			extentLogger.log(Status.PASS,
					MarkupHelper.createLabel(
							"Actual Text (" + actualText + ") is matched with Expected Text(" + expectedText + ")",
							ExtentColor.GREEN));
		} else {
			extentLogger.log(Status.PASS,
					MarkupHelper.createLabel(
							"Actual Text (" + actualText + ") is missmatched with Expected Text(" + expectedText + ")",
							ExtentColor.RED));
			Assert.assertEquals(actualText, expectedText);
		}

	}

	public void verifyAttributeValue(WebElement element, String Atttribute, String ExpectedattributeValue) {
		waitForVisible(element, 30);
		String actualAttributeVal = element.getAttribute(Atttribute);
		if (actualAttributeVal.contains(ExpectedattributeValue)) {
			extentLogger.log(Status.PASS,
					MarkupHelper.createLabel(
							"TestCase is Passed :-  Actual Value(" + actualAttributeVal
									+ ") is matched with Expected Value(" + ExpectedattributeValue + ")",
							ExtentColor.GREEN));
		} else {
			extentLogger.log(Status.FAIL,
					MarkupHelper.createLabel(
							"TestCase Is Failed :-Actual Value(" + actualAttributeVal
									+ ") is Mismatched with Expected Value(" + ExpectedattributeValue + ")",
							ExtentColor.GREEN));
			Assert.assertEquals(actualAttributeVal, ExpectedattributeValue);
		}
	}

	public void waitForVisible(WebElement element, int time) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void verifyElement_IsVisible(WebElement element, String elementname) {
		waitForVisible(element, 30);
		boolean actual = element.isDisplayed();
		if (actual == true) {
			extentLogger.log(Status.PASS, MarkupHelper
					.createLabel("TestCase is Passed :- " + elementname + " is Visible On Page", ExtentColor.GREEN));
		} else {
			extentLogger.log(Status.FAIL, MarkupHelper
					.createLabel("TestCase is Failed :- " + elementname + " is InVisible On Page", ExtentColor.RED));
			Assert.assertEquals(actual, true);

		}

	}

	public void verifyElement_IsEnable(WebElement element, String elementname) {
		boolean actual = element.isEnabled();

		if (actual == true) {
			extentLogger.log(Status.FAIL, MarkupHelper
					.createLabel("TestCase is Passed :- " + elementname + " is Enable ", ExtentColor.GREEN));
		} else {
			extentLogger.log(Status.FAIL,
					MarkupHelper.createLabel("TestCase is Failed :- " + elementname + " is Dible ", ExtentColor.RED));
			Assert.assertEquals(actual, true);

		}

	}

	public void verifyElement_IsSelected(WebElement element, String elementname) {
		boolean actual = element.isDisplayed();

		if (actual == true) {
			extentLogger.log(Status.FAIL, MarkupHelper
					.createLabel("TestCase is Passed :- " + elementname + " is Selected ", ExtentColor.GREEN));
		} else {
			extentLogger.log(Status.FAIL, MarkupHelper
					.createLabel("TestCase is Failed :- " + elementname + " is UnSelected ", ExtentColor.RED));
			Assert.assertEquals(actual, true);

		}

	}

	public String SimpleDateTimeFormate() {
		SimpleDateFormat timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss");
		Date date = new Date();
		return timestamp.format(date);

	}

	public void initHtmlReport() {

		String path = System.getProperty("user.dir") + "//ExtentReport// ";
		ExtentSparkReporter htmlReport = new ExtentSparkReporter(path + "MobileReport.html");
		htmlReport.config().setReportName(" Mobile Automation");
		htmlReport.config().setDocumentTitle("Mobile Automation Report");
		htmlReport.config().setTheme(Theme.DARK);
		extReport = new ExtentReports();
		extReport.attachReporter(htmlReport);
		extReport.setSystemInfo("Username", System.getProperty("user.name"));
		extReport.setSystemInfo("Envoirment", "QA");
		extReport.setSystemInfo("OS", System.getProperty("os.name"));

	}

	public ExtentTest getExtentLogger() {
		return extentLogger;
	}

	public void setExtentLogger(String testCaseName) {

		extentLogger = extReport.createTest(testCaseName);
	}

	public void flushReport() {
		extReport.flush();
	}
	public void implicitywait(int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

//	public void hideKeyBoard() {
//		androidBTNS(AndroidKey.ENTER);
//		staticWait(1);
//		((HidesKeyboard) driver).hideKeyboard();
//
//	}

//	public void click(WebElement we) {
//		we.click();
//	}
//
//	public void inputValue(AndroidElement we, String inputvalueTxt) {
//		we.sendKeys(inputvalueTxt);
//	}

	public void javaScriptSendKeys(String value, WebElement webElement) {
		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		jse.executeScript("arguments[0].value=arguments[1]", webElement, value);

	}

	public void sendKey(WebElement we, String value) {
		if (we.isDisplayed() && we.isEnabled()) {
			try {
				we.clear();
				we.sendKeys(value);
			} catch (NoSuchElementException e) {
				Actions act = new Actions(driver);
				we.clear();
				act.moveToElement(we).sendKeys(value).build().perform();
				e.printStackTrace();
			} catch (Exception e) {
				we.clear();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].value='" + value + "';", we);

			}

		}
	}

	public void navigateBack() {
		driver.navigate().back();

	}
	///////////////// Gestures //////////////////////////

	public void tap(WebElement we) {
		TouchAction tp = new TouchAction(driver);
		tp.tap(ElementOption.element(we)).perform();

// agar tap ki jagah press likhate to press karata rahega		
		/*
		 * upar keval wwebdriver likha hai isiliye new TouchAction( driver); ye likha
		 * hai agar AdroidDriver likha hota to new TouchAction(driver); isase kaam chal
		 * jata
		 */
	}

	// appium inspector me ek teer rahata hai upar arrow jaisa - left side me upar
	// cursor rakhege to swipeBycordinates par click karake exact coOrdinate dekh
	// sakate hai thoda dhyan dege to ho jayega

	public void javaScriptCLick(WebElement we) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", we);

	}

	public void tap(int xAxis, int yAxis) {
		PointOption coOrdinateForPress = new PointOption().point(xAxis, yAxis);
		TouchAction tc = new TouchAction(driver);
		tc.tap(coOrdinateForPress).perform();
// new Actions(driver).moveByOffset(0, 0).click();  Is tarike se click kara sakate hai yah tap by co-Ordinate ka jaagh le sakata hai
//new Actions(driver).moveByOffset(0, 0).clickAndHold().build().perform();-- long press ki jagah le sakaat hai

	}

	public void scrollAndClick() {

	}

	public void swipe(int yaxisStartPointPercent, int yaxisEndPointPercet) {
		org.openqa.selenium.Dimension windowSize = driver.manage().window().getSize();
		int screenHeight = windowSize.getHeight();
		int screenWidth = windowSize.getWidth();

		// init start point and end point to touch and release
		int xStartPoint = 50 * screenWidth / 100;
		int xEndPoint = xStartPoint;
		int yStartPoint = yaxisStartPointPercent * screenHeight / 100;
		int yEndPoint = yaxisEndPointPercet * screenHeight / 100;
		// perfoms touch actions
		PointOption startPoint = new PointOption().withCoordinates(xStartPoint, yStartPoint);
		PointOption endPoint = new PointOption().withCoordinates(xEndPoint, yEndPoint);
		// scroll up -- swipe from bottom to top
		TouchAction touchAction = new TouchAction(driver);
		touchAction.press(startPoint).waitAction(new WaitOptions().withDuration(Duration.ofSeconds(2))).moveTo(endPoint)
				.release().perform();

	}

	public void swipe(int xStartPoint, int yStartPoint, int xEndPoint, int yEndPoint) {
		// perfoms touch actions
		PointOption startPoint = new PointOption().withCoordinates(xStartPoint, yStartPoint);
		PointOption endPoint = new PointOption().withCoordinates(xEndPoint, yEndPoint);
		TouchAction touchAction = new TouchAction(driver);
		touchAction.press(startPoint).waitAction(new WaitOptions().withDuration(Duration.ofSeconds(2))).moveTo(endPoint)
				.release().perform();
	}

	public void swipeUntilVisibilityOfTheElement() {

	}

	public void longPress(WebElement we, int timeInSecond) {
		TouchAction touch = new TouchAction(driver);
		touch.longPress(ElementOption.element(we))
				.waitAction(new WaitOptions().withDuration(Duration.ofSeconds(timeInSecond))).perform();

		// agar tap ki jagah press likhate to press karata rahega
		/*
		 * upar keval wwebdriver likha hai isiliye new TouchAction( driver); ye likha
		 * hai agar AdroidDriver likha hota to new TouchAction(driver); isase kaam chal
		 * jata
		 */

	}

	public void longPress(int xAxis, int yAxis, int timeInSecond) {
		PointOption coOrdinatefortouch = new PointOption().point(xAxis, yAxis);
		TouchAction touch = new TouchAction(driver);
		touch.longPress(coOrdinatefortouch).waitAction(new WaitOptions().withDuration(Duration.ofSeconds(timeInSecond)))
				.perform();

// appium inspector me ek teer rahata hai upar arrow jaisa - left side me upar 
// cursor rakhege to swipeBycordinates par click karake exact coOrdinate dekh sakate hai thoda dhyan dege to ho jayega		

	}

	public void dragAndDrop(WebElement sourceWebElement, int x, int y) {
		TouchAction touch = new TouchAction(driver);
		touch.press(ElementOption.element(sourceWebElement))
				.waitAction(new WaitOptions().withDuration(Duration.ofSeconds(2))).moveTo(new PointOption().point(x, y))
				.release().perform();
	}

	public void dragAndDrop(WebElement source, WebElement targetWebElement) {
		TouchAction touch = new TouchAction(driver);
		touch.press(ElementOption.element(source)).waitAction(new WaitOptions().withDuration(Duration.ofSeconds(2)))
				.moveTo(ElementOption.element(targetWebElement)).release().perform();
	}

	public void zoomIn(int xAxis, int yAxis) {
		MultiTouchAction multiTouchAction = new MultiTouchAction(driver);
		PointOption startPointzoomout = new PointOption().withCoordinates(xAxis, yAxis - 10);
		PointOption endpointzoomout = new PointOption().withCoordinates(xAxis, yAxis - 200);
		TouchAction zoomOut = new TouchAction(driver);
		zoomOut.press(startPointzoomout).moveTo(endpointzoomout).release();
		PointOption startPointzoomIn = new PointOption().withCoordinates(xAxis, yAxis + 10);
		PointOption endPointzoomOut = new PointOption().withCoordinates(xAxis, yAxis + 200);
		TouchAction zoomIn = new TouchAction(driver);
		zoomIn.press(startPointzoomIn).moveTo(endPointzoomOut).release();
		multiTouchAction.add(zoomOut).add(zoomIn).perform();

	}

	public void zoomOut(int xAxis, int yAxis) {
		// ise coOrdinate ke base par karana hai
		MultiTouchAction multiTouchAction = new MultiTouchAction( driver);
		PointOption startPointzoomout = new PointOption().withCoordinates(xAxis, yAxis - 10);
		PointOption endpointzoomout = new PointOption().withCoordinates(xAxis, yAxis - 200);
        TouchAction zoomOut = new TouchAction( driver);
		zoomOut.press(startPointzoomout).moveTo(endpointzoomout).release();
        PointOption startPointzoomIn = new PointOption().withCoordinates(xAxis, yAxis + 10);
		PointOption endPointzoomOut = new PointOption().withCoordinates(xAxis, yAxis + 200);
        TouchAction zoomIn = new TouchAction( driver);
		zoomIn.press(startPointzoomIn).moveTo(endPointzoomOut).release();
        multiTouchAction.add(zoomOut).add(zoomIn).perform();

		
	}

	public void zoomInByElement() {

	}

	public void zoomTwoFinger(TouchAction finger1, TouchAction finger2) {
		MultiTouchAction multiTouch = new MultiTouchAction(driver);
		multiTouch.add(finger1).add(finger2).perform();

	}

	public void zoomThreeFinger(TouchAction finger1, TouchAction finger2, TouchAction finger3) {
		MultiTouchAction multiToch = new MultiTouchAction(driver);
		multiToch.add(finger1).add(finger2).add(finger3).perform();

	}

	//////////// BUTTONS--- LIKE HOME BTN, POWER BTN./////////////////
//	public void homeBtn() {
//		((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.HOME));
//	}
//
//	public void volumeBtn(String btn) {
//		if (btn.equalsIgnoreCase("up")) {
//			((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.VOLUME_UP));
//		} else if (btn.equalsIgnoreCase("down")) {
//			((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.VOLUME_DOWN));
//		} else if (btn.equalsIgnoreCase("mute")) {
//			((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.VOLUME_MUTE));
//		} else {
//			System.out.println("You have Entered wrong BTN ");
//		}
//	}

}
