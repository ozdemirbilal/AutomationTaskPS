package com.paysera.pages;

import com.paysera.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

}
