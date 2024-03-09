package com.eRSPL.Functionality.loginScreen;

import org.openqa.selenium.support.PageFactory;

import com.eRSPL.OR.loginScreen.LoginScreenOR;

import mobileUtility.MobileUtil;

public class LoginScreen extends LoginScreenOR {

	private MobileUtil mobUtil;

	public LoginScreen(MobileUtil mobUtil) {

//		super(mobUtil);
		this.mobUtil = mobUtil;
		PageFactory.initElements(mobUtil.getDriver(), this);
	}

	public void loginApplication(String MoNumber, String entersOTP) throws InterruptedException {
		System.out.println("start testcase");
		mobUtil.click(dontAllowBt, "Click Perform on Don't Allow");
		 Thread.sleep(5000);
		mobUtil.click(popUpOKBT, "Clik on while Using The App Text");
		 Thread.sleep(5000);
		mobUtil.click(sureIWouldLikeThat, "Clik on Sure i would like That");
		mobUtil.click(locationAccessBT, "Clik on Location Access");
		mobUtil.click(backBT, "Clik on Back Icon");

		mobUtil.inputData(enterYourMobNumberED, MoNumber);
		mobUtil.click(logInBT, "Login Button Click Performed");
		mobUtil.inputData(enterOTP, entersOTP);
		mobUtil.click(otpLoginBT, "Login Click Perform Successfully");
		mobUtil.click(yesBT, "Verify Yes Button Perform Successfully");

	}

}
