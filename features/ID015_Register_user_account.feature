Feature: Register new user

As a UniTrade potential user
I want to register an account
So that I can trade in the online marketplace

Scenario Outline: User email and username does not already exist in system. (Normal Flow)
Given user is not logged in
And a user with email "<email>" or username "<username>" does not already exist in the system
When user registers with email "<email>", username "<username>", first name "<first_name>", last name "<last_name>" and password "<password>"
Then a new user account with email "<email>", username "<username>", first name "<first_name>", last name "<last_name>" and password "<password>" is created

Examples: 
| email             | username      | first_name    | last_name     | password      |
| zidane@zidane.com | zidane        | Zinedine      | Zidane        | zidane        |
| amidon@gmail.com  | amidon        | Andre         | Agassi        | amidon        |

Scenario Outline: User email or username already exists in system. (Error Flow)
Given user is not logged in
And a user with email "<email>" or username "<username>" already exists in the system
When user registers with email "<email>", username "<username>", first name "<first_name>", last name "<last_name>" and password "<password>"
Then an error is thrown to create a new user account with email "<email>", username "<username>", first name "<first_name>", last name "<last_name>" and password "<password>"

Examples:
| email               | username      | first_name    | last_name     | password      |
| etienne@hotmail.com | etienne       | Etienne       | Capoue        | etienne       |
| dean35@outlook.com  | dean35        | Dean          | Henderson     | dean35        |