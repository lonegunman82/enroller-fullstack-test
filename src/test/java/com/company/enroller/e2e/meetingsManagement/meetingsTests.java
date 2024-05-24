package com.company.enroller.e2e.meetingsManagement;

import com.company.enroller.e2e.BaseTests;
import com.company.enroller.e2e.Const;
import com.company.enroller.e2e.authentication.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class meetingsTests extends BaseTests {

    WebDriver driver;
    LoginPage page;

    @BeforeEach
    void setup() {
        this.driver = WebDriverManager.chromedriver().create();
        this.page = new LoginPage(driver);
        this.page.get(Const.HOME_PAGE);
    }

}
