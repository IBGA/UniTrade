Feature: Create new item posting

As an existing UniTrade user
I want to create a new item posting
So that I can sell course related items to other users.

Scenario: University and course are found in the system, and all fields are filled in correctly (Normal Flow)
Given user is logged in
And user is on the create new item posting page
And user has found and selected the university from the dropdown menu
And user has found and selected the course from the dropdown menu
And user has filled in the fields correctly
When user clicks the create posting button
Then the item posting is created

Scenario: University and course are found in the system, and some fields are filled in incorrectly (Error Flow)
Given user is logged in
And user is on the create new item posting page
And user has found and selected the university from the dropdown menu
And user has found and selected the course from the dropdown menu
And user has filled in the fields incorrectly
When user clicks the create posting button
Then a "Fields filled incorrectly" error message is issued

Scenario: University and/or course is not found in the system (Alternate Flow)
Given user is logged in
And user is on the create new item posting page
When user has not found the university and/or course from the dropdown menu and clicks "add new"
Then user is prompted to add a new university and/or course