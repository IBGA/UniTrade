Feature: Add new university to system

As an existing UniTrade user,
I want to add a new university to the system
So that I can sell items related to courses given in this university

Scenario: Fields are filled in correctly (Normal Flow)
Given user is logged in
And user is on the "Add University" page
And user has filled in all fields correctly
When user clicks "Add University" button
Then the new university is added to the system

Scenario: Fields are filled in incorrectly (Error Flow)
Given user is logged in
And user is on the "Add University" page
And user has filled in at least one field incorrectly
When user clicks "Add University" button
Then a "Fields filled in incorrectly" error message is issued