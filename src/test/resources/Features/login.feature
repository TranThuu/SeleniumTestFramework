#Author
  #Date
  #Descripton
Feature: feature to test login functionality

  @SmokeTest
  Scenario: Check log in is successful with valid credential
    Given user is on the login page
    When user enter username and password
    And clicks on login button
    Then user is navigated to the home page

  Scenario Outline: Check log in is successful with valid credential
    Given user is on the login page
    When user enter <email> and <password>
    And clicks on login button
    Then user is navigated to the home page

    Examples:
    |email|password|
    |tranthu131200@gmail.com|abcde12345-|
    |user2|user2|