Feature: Add Moderator

As a student moderator
I want to assign other selected students as moderators for my university
So that other students can help manage it

Scenario Outline: Moderator gives other user moderator abilities (Normal Flow)
Given user is logged in
And user is a moderator for a university with name "<university_name>" and city "<university_city>"
And another user with username "<username>" exists in the system and is a member of university with name "<university_name>" and city "<university_city>"
When user attempts to give user with username "<username>" moderator abilities for university with name "<university_name>" and city "<university_city>"
Then user with username "<username>" is given moderator abilities for university with name "<university_name>" and city "<university_city>"

Examples:
| university_name                     | university_city   | username |
| University of Southern California  | Southern California | user1 |
