package com.company.enroller.e2e;

import com.company.enroller.App;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BasePage {

    public WebDriver driver;
    public WebDriverWait wait;
    int timeoutSec = 5;

    public BasePage(WebDriver driver) {
        this.setupWebDriver(driver, this.timeoutSec);
    }

    public BasePage(WebDriver driver, int timeoutSec) {
        this.setupWebDriver(driver, timeoutSec);
    }

    private void setupWebDriver(WebDriver driver, int timeoutSec) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeoutSec));
        PageFactory.initElements(this.driver, this);
    }

    public void get(String url) {
        this.driver.get(url);
    }

    public void click(WebElement element) {
        this.wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    public Boolean elementIsPresent(WebElement element) {
        return this.wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }

    public String getElementText(WebElement element) {
        return this.wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    public void sleep(Integer seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WebElement getMeetingByTitle(String meetingName) {
        String xPath = "//td[contains(text(), '" + meetingName + "')]/parent::tr";
        try {
            return this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
        } catch (NoSuchElementException ex) {
            System.out.println("NoSuchElementException has been handled." + ex);
        } catch (Exception e) {
            System.out.println("Exception: An unexpected error occurred for meeting title: " + meetingName + e.getMessage());
        }
        return null;
    }

    public List<WebElement> getMeetings() {
        String meetingSel = "table > tbody > tr";
        try {
            return this.wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(meetingSel), 0));
        } catch (TimeoutException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: An unexpected error occurred." + e.getMessage());
        }
        return null;
    }

    public List<String> getParticipantsListForMeeting(String meetingName) {
        String participantsItemSel = "td li";
        WebElement meeting = this.getMeetingByTitle(meetingName);
        return Optional.ofNullable(meeting)
                .map(m -> m.findElements(By.cssSelector(participantsItemSel))
                        .stream()
                        .map(WebElement::getText)
                        .toList())
                .orElse(List.of());
    }

    // New Methods to Support the Tests

    public void addNewMeeting(String title, String description) {
        WebElement titleInput = driver.findElement(By.id("meetingTitle")); // Replace with actual locator
        WebElement descriptionInput = driver.findElement(By.id("meetingDescription")); // Replace with actual locator
        WebElement addButton = driver.findElement(By.id("addMeetingButton")); // Replace with actual locator

        titleInput.sendKeys(title);
        descriptionInput.sendKeys(description);
        addButton.click();
    }

    public boolean isAddMeetingButtonEnabled() {
        WebElement addButton = driver.findElement(By.id("addMeetingButton")); // Replace with actual locator
        return addButton.isEnabled();
    }

    public void enterMeetingDetails(String title, String description) {
        WebElement titleInput = driver.findElement(By.id("meetingTitle")); // Replace with actual locator
        WebElement descriptionInput = driver.findElement(By.id("meetingDescription")); // Replace with actual locator

        titleInput.sendKeys(title);
        descriptionInput.sendKeys(description);
    }

    public void signUpForMeeting(String meetingTitle) {
        WebElement meetingRow = this.getMeetingByTitle(meetingTitle);
        if (meetingRow != null) {
            WebElement signUpButton = meetingRow.findElement(By.cssSelector(".signUpButton")); // Replace with actual locator
            signUpButton.click();
        }
    }

    public boolean isUserParticipantOfMeeting(String userName, String meetingTitle) {
        WebElement meetingRow = this.getMeetingByTitle(meetingTitle);
        if (meetingRow != null) {
            List<WebElement> participants = meetingRow.findElements(By.cssSelector("td.participant")); // Replace with actual locator
            return participants.stream().anyMatch(p -> p.getText().equals(userName));
        }
        return false;
    }

    public void deleteMeeting(String meetingTitle) {
        WebElement meetingRow = this.getMeetingByTitle(meetingTitle);
        if (meetingRow != null) {
            WebElement deleteButton = meetingRow.findElement(By.cssSelector(".deleteButton")); // Replace with actual locator
            deleteButton.click();
        }
    }

    public String getMeetingDescriptionByTitle(String meetingTitle) {
        WebElement meetingRow = this.getMeetingByTitle(meetingTitle);
        if (meetingRow != null) {
            WebElement descriptionCell = meetingRow.findElement(By.cssSelector("td.description")); // Replace with actual locator
            return descriptionCell.getText();
        }
        return null;
    }

    public int getMeetingCount() {
        List<WebElement> meetings = this.getMeetings();
        return (meetings != null) ? meetings.size() : 0;
    }
}
