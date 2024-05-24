package com.company.enroller.e2e.authentication;

import com.company.enroller.e2e.BasePage;
import com.company.enroller.e2e.Const;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoginPage extends BasePage {

    @FindBy(css = "div > input + button")
    WebElement loginBtn;

    @FindBy(css = "h1 + div > h2 + button")
    WebElement logoutBtn;

    @FindBy(css = "div > input")
    WebElement loginInput;

    @FindBy(css = "h1 + div > label")
    WebElement loginLabel;

    @FindBy(css = "h1 + div > h2")
    WebElement welcomeLabel;

    @FindBy(xpath = "//*[contains(text(), \"" + Const.ADD_NEW_MEETING_BTN_LABEL + "\")]")
    WebElement addNewMeetingBtn;


    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public void setLogin(String login) {
        this.loginInput.sendKeys(login);
    }

    public void pressLoginBtn() {
        this.click(this.loginBtn);
    }

    public Boolean loginBtnIsPresent() {
        return this.elementIsPresent(this.loginBtn);
    }

    public Boolean logoutBtnIsPresent() {
        return this.elementIsPresent(this.logoutBtn);
    }

    public Boolean addNewMeetingBtnIsPresent() {
        return this.elementIsPresent(this.addNewMeetingBtn);
    }

    public String getLoginLabelText() {
        return this.getElementText(this.loginLabel);
    }

    public String getWelcomeLabelText() {
        return this.getElementText(this.welcomeLabel).replaceAll("\\s", "");
    }


}
