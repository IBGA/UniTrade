Feature: Login to account

As an existing UniTrade user
I want to login to my account
So I can access my existing account

Scenario Outline: Email exists in system and password is correct (Normal Flow)
Given user is on the login page
And user enters existing email address <existing_email>
And user enters correct password <correct_password>
When user clicks login button
Then user is logged in to their account

| existing_email          | correct_password  |
| alex@existingemail.com  | STRONGPASSWORD123 |
| steve@existingemail.com | STRONGPASSWORD456 |

Scenario Outline: Email exists in system and password is incorrect (Error Flow)
Given user is on the login page
And user enters existing email address <existing_email>
And user enters incorrect password <incorrect_password>
When user clicks login button
Then a "password is incorrect" error is issued

| existing_email          | correct_password  | incorrect_password |
| alex@existingemail.com  | STRONGPASSWORD123 | wrongpassword123   |
| steve@existingemail.com | STRONGPASSWORD456 | wrongpassword456   |

Scenario Outline: Scenario Outline name: Email does not exist in system (Error Flow)
Given user is on the login page
And user enters non-existing email address
And user enters a password <password>
When user clicks login button
Then a "email does not exist" error is issued

| nonexisting_email          | password          |
| alex@nonexistingemail.com  | randompassword123 |
| steve@nonexistingemail.com | randompassword456 |