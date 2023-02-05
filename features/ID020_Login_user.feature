Feature: Login to account

As an existing UniTrade user
I want to login to my account
So I can access my existing account

Scenario: Email exists in system and password is correct (Normal Flow)
Given user is on the login page
And user enters existing email address
And user enters correct password
When user clicks login button
Then user is logged in to their account

Scenario: Email exists in system and password is incorrect (Error Flow)
Given user is on the login page
And user enters existing email address
And user enters incorrect password
When user clicks login button
Then a "password is incorrect" error is issued

Scenario: Email does not exist in system (Error Flow)
Given user is on the login page
And user enters non-existing email address
When user clicks login button
Then a "email does not exist" error is issued