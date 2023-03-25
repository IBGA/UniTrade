Feature: Edit item posting

As a student
I want to edit or delete my item postings
So that I can keep them up to date

Scenario Outline: Item posting is edited (Normal Flow)
Given user is logged in
And item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>" exists in the system
And user is the owner of the item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>"
When user edits the item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>" to have description "<new_description>" and price <new_price>
Then the item posting with title "<title>" and description "<new_description>" and price <new_price> for university with name "<university_name>" and city "<university_city>" exists in the system

Examples:
| university_name        | university_city | title           | description         | price | new_description          | new_price |
| University of Montreal | Montreal        | CSC109 textbook | textbook for CSC109 | 50    | mint textbook for CSC109 | 60        |
| University of Montreal | Montreal        | MTH103 textbook | textbook for MTH103 | 50    | mint textbook for MTH103 | 60        |

Scenario: Item posting is deleted (Alternate Flow)
Given user is logged in
And item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>" exists in the system
And user is the owner of the item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>"
When user deletes the item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>"
Then the item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>" does not exist in the system

Examples:
| university_name        | university_city | title           | description         | price |
| University of Montreal | Montreal        | CSC109 textbook | textbook for CSC109 | 50    |
| University of Montreal | Montreal        | MTH103 textbook | textbook for MTH103 | 50    |
