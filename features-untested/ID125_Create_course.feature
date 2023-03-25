Feature: Create course

As a student moderator
I want to create a course associated to a university that I manage 
So that students can create postings for that course

Scenario Outline: Fields are filled in correctly (Normal Flow)
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" exists in the system
And user is a moderator for university with name "<university_name>" and city "<university_city>"
And a course for university "<university_name>" with codename "<course_codename>" does not already exist in the system
When user attempts to create a course for university "<university_name>" with title "<course_title>", codename "<course_codename>", and description "<course_description>"
Then a new course for university "<university_name>" with title "<course_title>", codename "<course_codename>", and description "<course_description>" is added to the system

Examples:
| university_name      | university_city | course_title | course_codename | course_description   |
| University of Sydney | Sydney          | COMP331      | COMP331         | Software Engineering |
| University of Oxford | Oxford          | POLI101      | POLI101         | Politics             |

Scenario Outline: Course already exists
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" exists in the system
And user is a moderator for university with name "<university_name>" and city "<university_city>"
And a course for university "<university_name>" with codename "<course_codename>" already exists in the system
When user attempts to create a course for university "<university_name>" with title "<course_title>", codename "<course_codename>", and description "<course_description>"
Then an error is thrown
And a new course for university "<university_name>" with title "<course_title>", codename "<course_codename>", and description "<course_description>" is not added to the system

Examples:
| university_name         | university_city | course_title | course_codename | course_description   |
| University of Disney    | Disney          | SOCI204      | SOCI204         | Sociology            |
| University of Roquefort | Roquefort       | MATH101      | MATH101         | Mathematics          |
