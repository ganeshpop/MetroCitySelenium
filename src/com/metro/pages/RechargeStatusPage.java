package com.metro.pages;

import com.metro.objectmap.ObjectRepos;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RechargeStatusPage {
    @FindBy(xpath = ObjectRepos.RechargeStatusPage.updated_balance_container_xpath)
    WebElement updatedBalanceContainer;
    @FindBy(id = ObjectRepos.RechargeStatusPage.error_container_id)
    WebElement errorContainer;
    public WebDriver webDriver;

    public RechargeStatusPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public int getUpdatedBalance() {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(ObjectRepos.RechargeStatusPage.updated_balance_container_xpath)));
            return Integer.parseInt(updatedBalanceContainer.getText());
        } catch (Exception e) {
            return -1;
        }
    }

    public String getErrorMessage() {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id(ObjectRepos.RechargeStatusPage.error_container_id)));
            return errorContainer.getText().trim();
        } catch (Exception e) {
            return "Error Message Not Found";
        }
    }

}
