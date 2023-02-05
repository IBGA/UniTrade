Feature: Browse items by university and course

As an existing UniTrade User
I want to be able to browse items by university and course
So that I can buy items that are relevant to me

Scenario: University and course are found and selected from the dropdown menu (Normal Flow)
Given user is logged in
And user is on the browse page
And user finds and selects both a university and course from the dropdown menu
When user clicks the update search button
Then the browse page is updated with items tagged with the selected university and course

Scenario: University or course are not found (Alternate Flow)
Given user is logged in
And user is on the browse page
And user does not find the university and/or course they are looking for
When user clicks the update search button
Then a "University {university} {or} course {course} not found" message is displayed