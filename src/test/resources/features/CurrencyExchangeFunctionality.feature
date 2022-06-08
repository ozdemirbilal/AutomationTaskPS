@smoke
Feature: Currency Exchange Features

  Background:
    Given navigate to the "CurrencyExchangePage" page;


  Scenario: When user fills "BUY" amount "SELL" amount box should be empty
    Then sell amount input box should be filled
    And buy amount input box should be empty
    When user enter "100" into the buy amount input box
    Then sell amount input box should be empty
    When user enter "100" into the sell amount input box
    Then buy amount input box should be empty


  Scenario: When user selects country, rates and currency option must be updated
    When user should select "Russia" from country options
    Then "RUB" should be displayed as currency
    And rates should be updated after change the currency
    When user should select "United Kingdom" from country options
    Then "GBP" should be displayed as currency
    And rates should be updated after change the currency


  Scenario:When bank provider's exchange amount is lower than provided by Paysera, a text box which is representing the loss should be displayed
    When user should select "Poland" from country options
    Then verify that loss amounts are correct


  Scenario Outline: When user select one currency for sell and buy input box only one row should be displayed
    When user enter "500" into the sell amount input box
    And click on currency options for "Sell"
    And select "<sell currency>" from the currency  list
    And click on currency options for "Buy"
    And select "<buy currency>" from the currency  list
    And click on the Filter button
    Then only one row should be displayed

    Examples:
      | sell currency | buy currency |
      | USD           | EUR          |
      | EUR           | TRY          |































