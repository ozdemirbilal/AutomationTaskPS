package com.paysera.pages;

import com.paysera.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

public class CurrencyExchangePage extends BasePage{

    @FindBy(xpath = "//label[.='Sell']/../input")
    public WebElement sellAmountInputBox;

    @FindBy(xpath = "//label[.='Buy']/../input")
    public WebElement buyAmountInputBox;

    @FindBy(xpath = "(//span[@tabindex='-1'])[1]/span/span")
    public WebElement sellCurrency;

    @FindBy(xpath = "//tr[2]/td[3]")
    public WebElement USDRate;

    @FindBy(xpath = "(//tbody/tr/td[4])/span/span/span")
    public List<WebElement> payseraAmaunts;

    @FindBy(xpath = "//tbody/tr")
    public List<WebElement> currencyList;

    @FindBy(xpath = "//button[contains(.,'Filter')]")
    public WebElement filterButton;

    @FindBy(xpath = "//td[1]")
    public WebElement filteredCurrencyResult;

    public WebElement getInputBox(String boxName){
        WebElement inputBox = Driver.get().findElement(By.xpath("//label[.='" + boxName + "']/../input"));
        return inputBox;
    }

    public void validateLossAmount(){

        int tr = 1;

        for (WebElement payseraAmaunt : payseraAmaunts) {

            for (int i = 5; i <8 ; i++) {

                String path="(//tbody/tr["+tr+"]/td["+i+"])/span";

                if(!Driver.get().findElement(By.xpath(path)).getText().equals("-")){

                    String otherAmountsPath = "(//tbody/tr["+tr+"]/td["+i+"])/span/span/span[1]";
                    WebElement otherAmount = Driver.get().findElement(By.xpath(otherAmountsPath));

                    String lossAmountsPath = "(//tbody/tr["+tr+"]/td["+i+"])/span/span/span[2]";
                    WebElement lossAmount = Driver.get().findElement(By.xpath(lossAmountsPath));

                    double paysera_amount = Double.parseDouble(payseraAmaunt.getText().trim().replaceAll(",",""));
                    System.out.println("paysera_amount = " + paysera_amount);
                    double other_amount = Double.parseDouble(otherAmount.getText().trim().replaceAll(",",""));
                    System.out.println("other_amount = " + other_amount);

                    double expectedLossAmount = paysera_amount-other_amount;
                    double actualLossAmount =Double.parseDouble(lossAmount.getText().trim().substring(2,lossAmount.getText().trim().length()-1));

                    System.out.println("expectedLossAmount = " + expectedLossAmount);
                    System.out.println("actualLossAmount = " + actualLossAmount);

                    Assert.assertEquals(expectedLossAmount,actualLossAmount,0.01);

                    System.out.println("==================================================");
                }

            }

            tr++;

        }

    }

    //since there is no documentation, an expected country list was created for assertion
    public List<String> getExpectedCountries() {
        String[] countries = {"Lithuania", "Latvia", "Estonia", "Bulgaria",
                "Spain","Romania", "Poland", "United Kingdom",
                "Germany", "Russia", "Algeria", "Albania",
                "Kosovo", "Ukraine", "France", "Another country"};
        List<String> expectedCountries = Arrays.asList(countries);
        return expectedCountries;
    }
    //since there is no documentation, an expected language list was created for assertion
    public List<String> getExpectedLanguages() {
        String[] languages = {"Lietuvių", "English", "Русский", "Polski", "Latviešu", "Eesti", "Български",
                "Español", "Română", "Deutsch", "Shqip", "Shqip (Kosovë)", "Українська", "Français"};
        List<String> expectedLanguages = Arrays.asList(languages);
        return expectedLanguages;
    }
    //since there is no documentation, an expected currency list was created for assertion
    public List<String> getExpectedCurrencies() {
        String[] currencies = {"EUR","USD","RUB","DKK","PLN","NOK","GBP","SEK","CZK","AUD",
                "CHF","JPY","CAD","HUF","RON","BGN","GEL","TRY","HRK","CNY","KZT","NZD",
                "HKD","INR","ILS","MXN","ZAR","RSD","SGD","PHP","BYN","THB","ALL"};
        List<String> expectedCurrencies = Arrays.asList(currencies);
        return expectedCurrencies;
    }
}
