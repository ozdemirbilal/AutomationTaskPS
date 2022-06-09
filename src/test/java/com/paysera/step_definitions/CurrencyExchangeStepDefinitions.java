package com.paysera.step_definitions;

import com.paysera.pages.CurrencyExchangePage;
import com.paysera.utilities.Utilities;
import com.paysera.utilities.ConfigurationReader;
import com.paysera.utilities.Driver;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static com.paysera.utilities.Utilities.*;
import static org.junit.Assert.*;

public class CurrencyExchangeStepDefinitions {

    CurrencyExchangePage currencyExchangePage = new CurrencyExchangePage();
    String firstUSDRate;

    @Given("navigate to the {string} page;")
    public void navigate_to_the_page(String pageName) {

        Driver.get().get(ConfigurationReader.get(pageName));
        waitForPageToLoad(10);

        firstUSDRate = currencyExchangePage.USDRate.getText();
    }

    @Then("sell amount input box should be filled")
    public void sell_amount_input_box_should_be_filled() {

        WebElement sellAmountInputBox = currencyExchangePage.sellAmountInputBox;
        String value = sellAmountInputBox.getAttribute("value");
        assertFalse(value.isEmpty());

        System.out.println("Sell amount input box is filled");
    }

    @Then("buy amount input box should be empty")
    public void buy_amount_input_box_should_be_empty() {

        WebElement buyAmountInputBox = currencyExchangePage.buyAmountInputBox;
        String value = buyAmountInputBox.getAttribute("value");
        assertTrue(value.isEmpty());

        System.out.println("Buy amount input box is empty");
    }

    @When("user enter {string} into the buy amount input box")
    public void user_enter_an_amount_into_the_buy_amount_input_box(String amount) {

        WebElement buyAmountInputBox = currencyExchangePage.buyAmountInputBox;
        buyAmountInputBox.clear();
        buyAmountInputBox.sendKeys(amount);
        waitFor(1);
    }

    @Then("sell amount input box should be empty")
    public void sell_amount_input_box_should_be_empty() {

        WebElement sellAmountInputBox = currencyExchangePage.sellAmountInputBox;
        String value = sellAmountInputBox.getAttribute("value");
        assertTrue(value.isEmpty());

        System.out.println("Sell amount input box is empty");
    }

    @When("user enter {string} into the sell amount input box")
    public void user_enter_an_amount_into_the_sell_amount_input_box(String amount) {

        WebElement sellAmountInputBox = currencyExchangePage.sellAmountInputBox;
        sellAmountInputBox.clear();
        sellAmountInputBox.sendKeys(amount);
        waitFor(1);
    }

    @When("user should select {string} from country options")
    public void user_should_select_from_country_options(String country) {

        Utilities.scrollToElement(currencyExchangePage.flagIcon);
        currencyExchangePage.flagIcon.click();
        currencyExchangePage.countries.click();
        Utilities.waitFor(2);
        Driver.get().findElement(By.xpath("//a[contains(.,'" + country + "')]")).click();
        waitForPageToLoad(10);
    }

    @Then("{string} should be displayed as currency")
    public void should_be_displayed_as_currency(String expectedCurrency) {

        try {
            currencyExchangePage.cookies.click();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            Utilities.waitForVisibility(currencyExchangePage.sellCurrency,30);
            Utilities.scrollToElement(currencyExchangePage.sellCurrency);
            waitFor(2);
            String actualCurrency = currencyExchangePage.sellCurrency.getText();
            assertEquals(expectedCurrency, actualCurrency);

        }

    }

    @Then("rates should be updated after change the currency")
    public void rates_should_be_updated_after_change_the_currency() {

        String lastUSDRate = currencyExchangePage.USDRate.getText();
        assertNotEquals(lastUSDRate,firstUSDRate);
        firstUSDRate = lastUSDRate;
    }

    @Then("verify that loss amounts are correct")
    public void verify_that_loss_amounts_are_correct() {

        try {
            currencyExchangePage.cookies.click();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JavascriptExecutor jse = (JavascriptExecutor) Driver.get();
            jse.executeScript("window.scrollBy(0,1500)");
            currencyExchangePage.validateLossAmount();
        }

    }


    @And("click on currency options for {string}")
    public void click_on_currency_options_for(String boxName) {
        Driver.get().findElement(By.xpath("//label[.='" + boxName + "']/..//span[@tabindex='-1']")).click();
    }

    @And("select {string} from the currency  list")
    public void select_from_the_currency_list(String currency) {
        Driver.get().findElement(By.xpath("//span[@data-ng-bind='currency'][.='" + currency + "']")).click();

        System.out.println(currency + " is selected");
    }

    @And("click on the Filter button")
    public void click_on_the_filter_button() {
        currencyExchangePage.filterButton.click();
        waitFor(3);
    }

    @Then("only one row should be displayed")
    public void onlyOneRowShouldBeDisplayed() {

        Assert.assertTrue(currencyExchangePage.currencyList.size()==1);

    }
}
