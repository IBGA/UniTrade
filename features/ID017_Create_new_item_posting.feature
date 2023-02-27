Feature: Create new item posting

As an existing UniTrade user
I want to create a new item posting
So that I can sell course related items to other users.

Scenario Outline: University is found in the system and item posting is created (Normal Flow)
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" already exists in the system
When user creates an item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>"
Then the item posting is created and linked to the university with name "<university_name>" and city "<university_city>"

Examples:
| university_name | university_city | title | description | price |
| University of Toronto | Toronto | CSC108 textbook | textbook for CSC108 | 50 |
| University of Toronto | Toronto | MTH101 textbook | textbook for MTH101 | 50 |

Scenario: University is not found in the system and item posting is not created (Error Flow)
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" does not already exist in the system
When user creates an item posting with title "<title>" and description "<description>" and price <price> for university with name "<university_name>" and city "<university_city>"
Then the item posting is not created and an error is thrown

Examples:
| university_name | university_city | title | description | price |
| University of Amogus | Amogus | CSC108 textbook | textbook for CSC108 | 50 |
| University of Amogus | Amogus | MTH101 textbook | textbook for MTH101 | 50 |

