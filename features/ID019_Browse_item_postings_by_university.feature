Feature: Browse item postings by university

As an existing UniTrade User
I want to be able to browse item postings by university 
So that I can buy items that are relevant to me

Scenario Outline: University and relevant postings are is found (Normal Flow)
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" already exists in the system
When user searches for item postings for university with name "<university_name>" and city "<university_city>"
Then a list of item postings for university with name "<university_name>" and city "<university_city>" is returned

Examples:
| university_name        | university_city |
| University of Toronto  | Toronto         |
| University of Waterloo | Waterloo        |

Scenario Outline: University is not found (Error Flow)
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" does not already exist in the system
When user searches for item postings for university with name "<university_name>" and city "<university_city>"
Then no item postings are returned

Examples:
| university_name         | university_city |
| University of Amogus    | Amogus          |
| University of Minecraft | Minecraft       |