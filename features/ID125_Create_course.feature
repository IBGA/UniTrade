Feature: Create course

As a student moderator
I want to create a course associated to a university that I manage 
So that students can create postings for that course

Scenario Outline: Fields are filled in correctly (Normal Flow)
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" already exists in the system
And user is a moderator for university
And a course with codename "<course_codename>" does not already exist in the system
When user attempts to create a course for university with name "<university_name>" and city "<university_city>" with title "<course_title>", codename "<course_codename>", and description "<course_description>"
Then a new course for university with name "<university_name>" and city "<university_city>" with title "<course_title>", codename "<course_codename>", and description "<course_description>" is added to the system

Examples:
| university_name      | university_city | course_title | course_codename | course_description   |
| University of Canada | Canada          | COMP331      | COMP331         | Software Engineering |
| University of USA    | USA             | POLI101      | POLI101         | Politics             |

Scenario Outline: Course already exists
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" already exists in the system
And user is a moderator for university
And a course with codename "<course_codename>" for university with name "<university_name>" and city "<university_city>" already exists in the system
When user attempts to create a course for university with name "<university_name>" and city "<university_city>" with title "<course_title>", codename "<course_codename>", and description "<course_description>"
Then an error is thrown
And a new course for university with name "<university_name>" and city "<university_city>" with title "<course_title>", codename "<course_codename>", and description "<course_description>" is not added to the system

Examples:
| university_name         | university_city | course_title | course_codename | course_description   |
| University of France    | France          | SOCI204      | SOCI204         | Sociology            |
| University of England   | England         | MATH101      | MATH101         | Mathematics          |
