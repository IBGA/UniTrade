Feature: Browse items by university and course

As an existing UniTrade User
I want to be able to browse items by university and course
So that I can buy items that are relevant to me

Scenario Outline: University and course are found and selected from the dropdown menu (Normal Flow)
Given user is logged in
And user is on the browse page
And user finds and selects both a university <existing_university> and course <existing_course> from the dropdown menu
When user clicks the update search button
Then the browse page is updated with items tagged with university <existing_university> and course <existing_course>

| existing_university    | existing_course |
| McGill University      | ECSE 428        |
| Harvard University     | CS 50           |

Scenario Outline: University or course are not found (Alternate Flow)
Given user is logged in
And user is on the browse page
And user does not find the university <nonexisting_university> or course <nonexisting_course> they are looking for
When user clicks the update search button
Then a University <nonexisting_university> {or} course <nonexisting_course> not found message is displayed

| nonexisting_university    | nonexisting_course |
| Among Us University       | SUS 100            |
| Minecraft University      | RED 200            |