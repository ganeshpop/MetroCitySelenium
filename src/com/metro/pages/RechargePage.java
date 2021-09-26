package com.metro.pages;

import com.metro.objectmap.ObjectRepos;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RechargePage {

    @FindBy(xpath = ObjectRepos.RechargePage.amount_text_box_xpath)
    public WebElement amountTextBox;
    @FindBy(css = ObjectRepos.RechargePage.recharge_button_css_selector)
    public WebElement rechargeButton;
    @FindBy(xpath = ObjectRepos.RechargePage.balance_container_xpath)
    public WebElement balanceContainer;
    public WebDriver webDriver;

    public RechargePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void setAmount (String amount) {
        amountTextBox.clear();
        amountTextBox.sendKeys(amount);
    }

    public void clickRechargeButton() {
        rechargeButton.click();
    }

    public int getCurrentBalance() {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(ObjectRepos.RechargePage.balance_container_xpath)));
            return Integer.parseInt(balanceContainer.getText());
        } catch (Exception e) {
            return -1;
        }
    }

}
