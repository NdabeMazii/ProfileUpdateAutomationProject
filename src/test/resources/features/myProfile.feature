@myProfile @Regression @Sanity
Feature: login

  Scenario Outline: as a user i want to login to ndosi website
    Given i am on the login page
    And i enter my email <email>
    And i enter my password <password>
    When i click on the login button
    Then i should be logged in successfully
    And i click on the menu button
    And i click on the my profile button
    Then i should see my profile page
    And i click on the edit profile button
    And i click on the choose photo button
    Examples:
      | email                | password     |
      | ndabemazii@gmail.com | Mazii@053103 |

