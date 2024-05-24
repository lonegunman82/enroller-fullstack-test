package com.company.enroller.e2e.authentication;

import com.company.enroller.e2e.BaseTests;
import com.company.enroller.e2e.Const;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTests extends BaseTests {

    WebDriver driver;
    LoginPage page;

    @BeforeEach
    void setup() {
        this.driver = WebDriverManager.chromedriver().create();
        this.page = new LoginPage(driver);
        this.page.get(Const.HOME_PAGE);
    }


    @Test
    @DisplayName("[LOGOWANIE.1] No login, system should not confirm the user")
    void emptyLoginName() {
        this.page.pressLoginBtn();
        // Asserts
        assertThat(this.page.getLoginLabelText()).isEqualTo("Zaloguj się e-mailem");
        assertThat(this.page.loginBtnIsPresent()).isTrue();
    }

    @Test
    @DisplayName("[LOGOWANIE.2] The system should accept the login and display the meeting definition view")
    void correctLoginName() {
        this.page.setLogin(Const.EXIST_USER_1_MAIL);
        this.page.pressLoginBtn();
        this.page.sleep(100);
        // Asserts
        assertThat(this.page.getWelcomeLabelText()).isEqualTo("Witaj" + Const.EXIST_USER_1_MAIL + "!");
        assertThat(this.page.logoutBtnIsPresent()).isTrue();
        assertThat(this.page.addNewMeetingBtnIsPresent()).isTrue();
    }

    @AfterEach
    void exit() {
        this.page.quit();
    }

}
