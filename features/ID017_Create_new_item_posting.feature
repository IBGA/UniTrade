Feature: Create new item posting

As an existing UniTrade user
I want to create a new item posting
So that I can sell course related items to other users.

Scenario Outline: University is found in the system and item posting is created (Normal Flow)
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" already exists in the system
And a course with codename "<course_codename>" already exists in the system
When user attempts to create a item posting with title "<title>", description "<description>", imagelink "<imageLink>", university name "<university_name>", university city "<university_city>", course codename "<course_codename>", and price "<price>"
Then a new itemposting with title "<title>", description "<description>", imagelink "<imageLink>", university name "<university_name>", university city "<university_city>", course codename "<course_codename>", and price "<price>" is added to the system

Examples:
| title           | description         | imageLink                      | university_name | university_city | course_codename | price |
| CSC108 textbook | textbook for CSC108 | https://myimage.com/image.png  | Test_university | Test_city       | Test_codename   | 50    |
| MTH101 textbook | textbook for MTH101 | https://myimage.com/image2.png | Test_university | Test_city       | Test_codename   | 50    |

Scenario: University is not found in the system and item posting is not created (Error Flow)
Given user is logged in
And a university with name "<university_name>" and city "<university_city>" does not already exists in the system
And a course with codename "<course_codename>" does not already exists in the system
When user attempts to create a item posting with title "<title>", description "<description>", imagelink "<imageLink>", university name "<university_name>", university city "<university_city>", course codename "<course_codename>", and price "<price>"
Then the item posting is not created and an error is thrown

Examples:
| title           | description         | imageLink                      | university_name | university_city | course_codename | price |
| CSC108 textbook | textbook for CSC108 | https://myimage.com/image.png  | No_university   | No_city         | No_codename     | 50    |
| MTH101 textbook | textbook for MTH101 | https://myimage.com/image2.png | No_university_2 | No_city_2       | No_codename_2   | 50    |

