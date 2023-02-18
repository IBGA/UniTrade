Feature: Add new university to system

As an existing UniTrade user,
I want to add a new university to the system
So that I can sell items related to courses given in this university

Scenario Outline: Fields are filled in correctly (Normal Flow)
Given user is logged in
And user is on the "Add University" page
And user has filled in university name "<university_name>"
And user has filled in university city "<university_city>"
And user has filled in university address "<university_address>"
And user has filled in university postcode "<university_postcode>"
And user has filled in university country "<university_country>"
And user has filled in university website "<university_website>"
When user clicks "Add University" button
Then the new university with name "<university_name>", city "<university_city>", address "<university_address>", postcode "<university_postcode>", country "<university_country>" and website "<university_website>" is added to the system

Examples:
| university_name      | university_city | university_address | university_postcode | university_country | university_website |
| University of Sydney | Sydney          | 2006, Broadway     | 2006                | Australia          | www.sydney.edu.au  |
| University of Oxford | Oxford          | 1, Wellington Sq   | OX1 2JD             | UK                 | www.ox.ac.uk       |

Scenario Outline: Fields are filled in incorrectly (Error Flow)
Given user is logged in
And user is on the "Add University" page
And user has filled in at least one field "<incorrectly_filled_fields>" incorrectly
When user clicks "Add University" button
Then a "Fields filled in incorrectly" error message is issued and the "<incorrectly_filled_fields>" field(s) are/is highlighted
    Examples:
        | incorrectly_filled_fields |
        | university_name           |
        | university_city           |
        | university_name, university_city, university_address, university_postcode, university_country, university_website |