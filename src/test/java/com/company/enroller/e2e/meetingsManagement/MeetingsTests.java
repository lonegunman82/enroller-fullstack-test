package com.company.enroller.e2e.meetingsManagement;

import com.company.enroller.e2e.BaseTests;
import com.company.enroller.e2e.Const;
import com.company.enroller.e2e.authentication.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class MeetingsTests extends BaseTests {

    WebDriver driver;
    MeetingsPage page;
    LoginPage loginPage;

    @BeforeEach
    void setup() {
        this.dbInit();
        this.driver = WebDriverManager.chromedriver().create();
        this.page = new MeetingsPage(driver);
        this.loginPage = new LoginPage(driver);
        this.page.get(Const.HOME_PAGE);

        this.loginPage.loginAs(Const.USER_I_NAME);
        Const.INITIAL_MEETING_COUNT = this.page.getMeetingCount();
    }


    @Test
    @DisplayName("[SPOTKANIA.1] The meeting should be added to your meeting list. It should contain a title and description.")
    void addNewMeeting() {
        this.loginPage.loginAs(Const.USER_I_NAME);
        this.page.addNewMeeting(Const.MEETING_III_TITLE, Const.MEETING_DESC);
        // Asserts
        assertThat(this.page.getMeetingByTitle(Const.MEETING_III_TITLE)).isNotNull();
        // TODO: Dodaj sprawdzenie czy poprawnie został dodany opis.
        assertThat(this.page.getMeetingDescriptionByTitle(Const.MEETING_III_TITLE)).isEqualTo(Const.MEETING_DESC);
        // TODO: Dodaj sprawdzenie czy zgadza się aktualna liczba spotkań.
        assertThat(this.page.getMeetingCount()).isEqualTo(Const.INITIAL_MEETING_COUNT + 1);

    }

    // @Test
    // TODO: Sprawdź czy użytkownik może dodać spotkanie bez nazwy. Załóż że nie ma takiej możliwości a warunkiem
    //  jest nieaktywny przycisk "Dodaj".

    @Test
    @DisplayName("[SPOTKANIA.2] Użytkownik nie może dodawać spotkania bez nazwy")
    void addMeetingWithoutTitle() {
        this.loginPage.loginAs(Const.USER_I_NAME);
        this.page.enterMeetingDetails("", Const.MEETING_DESC);
        assertThat(this.page.isAddMeetingButtonEnabled()).isFalse();
    }

    // @Test
    // TODO: Sprawdź czy użytkownik może poprawnie zapisać się do spotkania.

    @Test
    @DisplayName("[SPOTKANIA.3] Użytkownik powninien móc zapisać się na spotkanie")
    void signUpForMeeting() {
        this.loginPage.loginAs(Const.USER_I_NAME);
        this.page.signUpForMeeting(Const.MEETING_I_TITLE);
        assertThat(this.page.isUserParticipantOfMeeting(Const.USER_I_NAME, Const.MEETING_I_TITLE)).isTrue();
    }



    // @Test
    // TODO: Sprawdź czy użytkownik może usunąć puste spotkanie.

    @Test
    @DisplayName("[SPOTKANIA.4] Użytkownik powinien móc skasować puste spotkanie.")
    void deleteEmptyMeeting() {
        this.loginPage.loginAs(Const.USER_I_NAME);
        this.page.addNewMeeting(Const.MEETING_IV_TITLE, Const.MEETING_DESC);
        assertThat(this.page.getMeetingByTitle(Const.MEETING_IV_TITLE)).isNotNull();
        this.page.deleteMeeting(Const.MEETING_IV_TITLE);
        assertThat(this.page.getMeetingByTitle(Const.MEETING_IV_TITLE)).isNull();
    }


    @AfterEach
    void exit() {
        this.page.quit();
        this.removeAllMeeting();
    }

}
