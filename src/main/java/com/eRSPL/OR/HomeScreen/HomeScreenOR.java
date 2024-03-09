package com.eRSPL.OR.HomeScreen;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import mobileUtility.MobileUtil;

public class HomeScreenOR {

	MobileUtil mobtil;

	public HomeScreenOR(MobileUtil mobtil) {
		PageFactory.initElements(mobtil.getDriver(), this);
	}

	@FindBy(xpath = "//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]")
	protected WebElement whileUsingAppBT;

	@FindBy(id = "com.android.permissioncontroller:id/permission_allow_one_time_button")
	protected WebElement onlyThisTimeBt;

	@FindBy(id = "com.android.permissioncontroller:id/permission_deny_and_dont_ask_again_button")
	protected WebElement dontAllowBT;

	@FindBy(id = "com.erspl.erspl:id/btn_sure")
	protected WebElement forGPSSureIWouldLikeThatBt;

	@FindBy(id = "com.erspl.erspl:id/btn_dismiss")
	protected WebElement forGPSNotNowBt;

	@FindBy(id = "android:id/switch_widget")
	protected WebElement locationAccessBT;

	@FindBy(id = "miui:id/up")
	protected WebElement backBT;

	@FindBy(id = "com.erspl.erspl:id/tv_enable")
	protected WebElement enableBt;

	@FindBy(id = "com.erspl.erspl:id/search_et")
	protected WebElement searchYourAreaTB;

	@FindBy(id = "com.erspl.erspl:id/tv")
	protected WebElement gotoCurentLocationBt;

	@FindBy(id = "com.erspl.erspl:id/btn_sure")
	protected WebElement forAllowLocationSureIWouldLikeThatBt;

	@FindBy(id = "com.erspl.erspl:id/btn_dismiss")
	protected WebElement forAllowLocationNotNowBt;

	@FindBy(xpath = "//android.widget.TextView[@text='App permissions']")
	protected WebElement app_permissions_Bt;

	@FindBy(xpath = "//android.widget.TextView[@text='Location']")
	protected WebElement location_bt;

	@FindBy(xpath = "//android.widget.RadioButton[@resource-id=\"com.android.permissioncontroller:id/allow_foreground_only_radio_button\"]")
	protected WebElement allowOnlyWhileUsingTheAppButton;
}
