Feature: Register new user

As a UniTrade potential user
I want to register an account
So that I can trade in the online marketplace

Scenario: User email does not already exist in system and password is valid. (Normal Flow)
Given user is on the register account page
And user enters a non-existing email address
And user enters a valid password
When user clicks on the register button
Then user is registered and redirected to the login page

Scenario: User password is invalid. (Error Flow)
Given user is on the register account page
And user enters an invalid password
And user enters an existing or non existing email address
When user clicks on the register button
Then a "invalid password" error message is issued

Scenario: User email already exists in system. (Error Flow)
Given user is on the register account page
And user enters an existing email address
And user enters a valid password
When user clicks on the register button
Then a "email already exists" error message is issued