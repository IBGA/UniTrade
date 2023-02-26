Feature: Register new user

As a UniTrade potential user
I want to register an account
So that I can trade in the online marketplace

Scenario Outline: User email and username does not already exist in system. (Normal Flow)
Given user is not logged in
And a user with email "<email>" or username "<username>" does not already exist in the system
When user registers with email "<email>" and username "<username>"
Then a new user account with email "<email>" and username "<username>" is created

Examples:
| email                  | username          |
| bob@gmail.com          | b.o.b.            |
| samson@hotmail.com     | samsonmamson      |


Scenario Outline: User email or username already exists in system. (Error Flow)
Given user is not logged in
And a user with email "<email>" or username "<username>" already exists in the system
When user registers with email "<email>" and username "<username>"
Then an error message is thrown and no new user account is created

Examples:
| email                  | username          |
| bob@gmail.com          | b.o.b.            |
| samson@hotmail.com     | samsonmamson      |