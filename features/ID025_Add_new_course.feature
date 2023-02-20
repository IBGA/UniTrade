Feature: Add new course to system

As an existing UniTrade user
I want to add a new course to the system
So that I can sell items related to this course

Scenario Outline: Fields are filled in correctly (Normal Flow)
Given user is logged in
And user is on the "Add Course" page
And user has filled in course name "<course_name>"
And user has filled in course code "<course_code>"
And user has filled in course description "<course_description>"
And user has filled in course university "<course_university>"
And user has filled in course faculty "<course_faculty>"
And user has filled in course department "<course_department>"
When user clicks "Add Course" button
Then the new course with name "<course_name>", code "<course_code>", description "<course_description>", university "<course_university>", faculty "<course_faculty>", and department "<course_department>" is added to the system

Examples:
| course_name          | course_code | course_description                                   | course_university  | course_faculty         | course_department  |
| Software Engineering | ECSE 428    | This course is about software engineering            | McGill University  | Faculty of Engineering | department of ECSE |
| Introduction to Law  | LGLS 1010   | This course is an into to the legal system of the US | Harvard University | Harvard Law School     | Department of Law  |

Scenario Outline: Fields are filled in incorrectly (Error Flow)
Given user is logged in
And user is on the "Add Course" page
And user has filled in at least one field "<incorrectly_filled_fields>" incorrectly
When user clicks "Add Course" button
Then a "Fields filled in incorrectly" error message is issued and the field(s) "<incorrectly_filled_fields>" is/are highlighted
    Examples:
        | incorrectly_filled_fields |
        | course_name               |
        | course_code, course_description |
