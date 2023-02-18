Feature: Register new user

As a UniTrade potential user
I want to register an account
So that I can trade in the online marketplace

Scenario Outline: User email does not already exist in system and password is valid. (Normal Flow)
Given user is on the register account page
And user enters a non-existing email address "<new_email>"
And user enters a valid password "<valid_pass>"
When user clicks on the register button
Then user with email "<new_email>" and password "<valid_pass>" is registered and redirected to the login page

Examples:
| new_email          | valid_pass        |
| alex@newemail.com  | STRONGPASSWORD123 |
| steve@newemail.com | STRONGPASSWORD456 |

Scenario Outline: User password is invalid. (Error Flow)
Given user is on the register account page
And user enters an existing or non existing email address "<email>"
And user enters an invalid password "<invalid_pass>"
When user clicks on the register button
Then a "invalid password" error message is issued

Examples:
| email              | invalid_pass |
| alex@email.com     | easypass1    |
| steve@newemail.com | easypass2    |

Scenario Outline: User email already exists in system. (Error Flow)
Given user is on the register account page
And user enters an existing email address "<existing_email>"
And user enters a valid password "<valid_pass>"
When user clicks on the register button
Then a "email already exists" error message is issued

Examples:
| existing_email          | valid_pass        |
| alex@existingemail.com  | STRONGPASSWORD123 |
| steve@existingemail.com | STRONGPASSWORD456 |