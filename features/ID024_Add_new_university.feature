Feature: Add new university to system

As an existing UniTrade user,
I want to add a new university to the system
So that I can sell items related to courses given in this university

Scenario Outline: Fields are filled in correctly (Normal Flow)
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" does not already exist in the system
When user attempts to create a university with name "<university_name>", city "<university_city>", and description "<university_description>"
Then a new university with name "<university_name>", city "<university_city>", and description "<university_description>" is added to the system

Examples:
| university_name      | university_city | university_description |
| University of Sydney | Sydney          | A university in Sydney |
| University of Oxford | Oxford          | A university in Oxford |

Scenario Outline: University already exists (Error Flow)
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" already exists in the system
When user attempts to create a university with name "<university_name>", city "<university_city>", and description "<university_description>"
Then an error is thrown to create a new university with name "<university_name>", city "<university_city>", and description "<university_description>"

Examples:
| university_name      | university_city | university_description |
| University of Disney | Disney          | A university in Disney |
| University of Roquefort | Roquefort          | A university in Roquefort |