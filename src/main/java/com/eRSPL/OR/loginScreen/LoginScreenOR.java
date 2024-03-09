package com.eRSPL.OR.loginScreen;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import mobileUtility.MobileUtil;

public class LoginScreenOR {
	
//	MobileUtil mobtil;
//	public LoginScreenOR(MobileUtil mobtil) {
//		 PageFactory.initElements(mobtil.getDriver(), this);
//	}
	
	@FindBy(id="//android.widget.Button[@resource-id='android:id/button1']")
	protected WebElement popUpOKBT;
 
	@FindBy(xpath="//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_deny_button\"]")
	protected WebElement dontAllowBt;
	
	@FindBy(id="com.erspl.star:id/btn_sure")
	protected WebElement sureIWouldLikeThat;
	
	@FindBy(xpath="//android.widget.TextView[@text=\"Location access\"]")
	protected WebElement locationAccessBT;
	

	@FindBy(id="//android.widget.ImageView[@content-desc='Back']")
	protected WebElement backBT;
	

	@FindBy(xpath="//android.widget.EditText[@resource-id=\"com.erspl.star:id/edname1\"]")
	protected WebElement enterYourMobNumberED;
	
	@FindBy(xpath="//android.widget.Button[@resource-id=\"com.erspl.star:id/loginbtn\"]")
	protected WebElement logInBT;
	
	@FindBy(id="com.erspl.star:id/edotpview")
	protected WebElement enterOTP;
	

	@FindBy(id="com.erspl.star:id/loginbtn")
	protected WebElement otpLoginBT;
	
	
	@FindBy(id="com.erspl.star:id/tvresend")
	protected WebElement resendOTP;
	
	@FindBy(id="com.erspl.star:id/yess")
	protected WebElement yesBT;
	
	@FindBy(xpath="//android.widget.Button[@resource-id='com.erspl.star:id/noo']")
	protected WebElement noBT;
	
	
	
}



