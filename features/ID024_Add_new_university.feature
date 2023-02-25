Feature: Add new university to system

As an existing UniTrade user,
I want to add a new university to the system
So that I can sell items related to courses given in this university

Scenario Outline: Fields are filled in correctly (Normal Flow)
Given user is logged in
And user is on the "Add University" page
And user has filled in university name "<university_name>"
And user has filled in university city "<university_city>"
And user has filled in university description "<university_description>"
When user clicks "Add University" button
Then the new university with name "<university_name>", city "<university_city>", and description "<university_description>" is added to the system

Examples:
| university_name      | university_city | university_description |
| University of Sydney | Sydney          | A university in Sydney |
| University of Oxford | Oxford          | A university in Oxford |

Scenario Outline: Fields are filled in incorrectly (Error Flow)
Given user is logged in
And user is on the "Add University" page
And user has filled in at least one field "<incorrectly_filled_fields>" incorrectly
When user clicks "Add University" button
Then a "Fields filled in incorrectly" error message is issued and the "<incorrectly_filled_fields>" field(s) is/are highlighted
    Examples:
        | incorrectly_filled_fields |
        | university_name           |
        | university_city           |
        | university_description    |