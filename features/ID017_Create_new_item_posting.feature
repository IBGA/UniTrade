Feature: Create new item posting

As an existing UniTrade user
I want to create a new item posting
So that I can sell course related items to other users.

Scenario Outline: University is found in the system and item posting is created (Normal Flow)
Given user is logged in
And a university with id "<universityId>" already exists in the system
And a course with id "<courseIds>" already exists in the system
When user attempts to create a item posting with title "<title>", description "<description>", imagelink "<imageLink>", universityId "<universityId>", courseIds "<courseIds>", and price "<price>"
Then a new itemposting with title "<title>", description "<description>", imagelink "<imageLink>", universityId "<universityId>", courseIds "<courseIds>", and price "<price>" is added to the system

Examples:
| title | description | imageLink | universityId | courseIds | price |
| CSC108 textbook | textbook for CSC108 | https://myimage.com/image.png | 1 | 1 | 50 |
| MTH101 textbook | textbook for MTH101 | https://myimage.com/image2.png | 1 | 1 | 50 |

Scenario: University is not found in the system and item posting is not created (Error Flow)
Given user is logged in
And a university with id "<universityId>" does not already exists in the system
And a course with id "<courseIds>" does not already exists in the system
When user attempts to create a item posting with title "<title>", description "<description>", imagelink "<imageLink>", universityId "<universityId>", courseIds "<courseIds>", and price "<price>"
Then the item posting is not created and an error is thrown

Examples:
| title | description | imageLink | universityId | courseIds | price |
| CSC108 textbook | textbook for CSC108 | https://myimage.com/image.png | 99 | 1 | 50 |
| MTH101 textbook | textbook for MTH101 | https://myimage.com/image2.png | 100 | 1 | 50 |

