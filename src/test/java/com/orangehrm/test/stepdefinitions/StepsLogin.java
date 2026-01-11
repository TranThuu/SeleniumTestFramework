package com.orangehrm.test.stepdefinitions;

import io.cucumber.java.en.*;
public class StepsLogin {
    @When("I enter my username and password")
    public void enterCredentials() {
    }

    @And("I click the Login button")
    public void clickLogin() {
    }

    @Then("I should be taken to the Dashboard page")
    public void openDashboard() {
    }

    @And("I should see the {string} menu")
    public void seeMenu(String menuName) {
    }


    @Then("It should display an error message")
    public void displayErrorMessage(){

    }
}
