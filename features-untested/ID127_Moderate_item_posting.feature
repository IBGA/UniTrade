Feature: Moderate item posting

As a student moderator
I want to delete inappropriate item postings
So that the content of the marketplace remains trustworthy

Scenario Outline: Item posting is deleted by moderator (Normal Flow)

Given user is logged in
And item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>" exists in the system
And user is a moderator for university with name "<university_name>" and city "<university_city>"
When user deletes the item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>"
Then the item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>" does not exist in the system

Examples:
| university_name        | university_city | title           | description         | price |
| University of American | American        | CSC109 textbook | textbook for CSC109 | 50    |
| University of Mexican  | Mexican         | MTH103 textbook | textbook for MTH103 | 50    |