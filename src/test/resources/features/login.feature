@login @Regression @Sanity
  Feature: login

Scenario Outline: as a user i want to login to ndosi website
    Given i am on the login page
    And i enter my email <email>
    And i enter my password <password>
    When i click on the login button
    Then i should be logged in successfully
    Examples:
      | email           | password  |
      | admin@gmail.com | @12345678 |

