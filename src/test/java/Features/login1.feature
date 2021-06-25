
Feature:Check if user is logged in 1
@runTests
 Scenario Outline: Login 1
    Given Input <userId> and <Password>
    When Click Login button
    Then Logout button is displayed

Examples:
   |userId|Password|
   |"test_buggycar_1@mailinator.com"|"testBuggy#1"|
   |"test_buggycar_2@mailinator.com"|"testBuggy#1"|


