package TestMobile;

import org.testng.annotations.Test;

import com.eRSPL.Functionality.loginScreen.LoginScreen;

//import com.companyName.ProjectName.pages.HomePage;
//import com.companyName.ProjectName.pages.Login;
import basetest.TestBase;

public class LoginTestScript extends TestBase {
	
	@Test
	public void login() throws InterruptedException {
		
		LoginScreen lgn=new LoginScreen(utilityObj);
		lgn.loginApplication(prop.getProperty("mobileNo"),prop.getProperty("OTP"));
//		HomePage home=new HomePage(utilityObj);
//		home.verifyAddToCart();
		
	}

	
	 
	
	
	
}
