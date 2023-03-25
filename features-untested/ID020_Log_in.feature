Feature: Log in

As a student
I want to log into my existing account
So that I can access the online marketplace

Scenario Outline: User username exists in system and password is correct. (Normal Flow)
Given user is not logged in
And a user with username "<username>" exists in the system
And the password for user "<username>" is "<password>"
When user logs in with username "<username>" and password "<password>"
Then user is logged in and redirected to the home page

Examples:
| username          | password          |
| elonmusk          | spacex            |
| billgates         | microsoft         |

Scenario Outline: User username exists in system but password is incorrect. (Error Flow)
Given user is not logged in
And a user with username "<username>" exists in the system
And the password for user "<username>" is not "<password>"
When user logs in with username "<username>" and password "<password>"
Then an error is thrown and user is not logged in

Examples:
| username          | password          |
| elonmusk          | ford              |
| billgates         | apple             |

Scenario Outline: Username does not exist in system. (Error Flow)
Given user is not logged in
And a user with username "<username>" does not in the system
When user logs in with username "<username>" and password "<password>"
Then an error is thrown and user is not logged in

Examples:
| username          | password          |
| stevejobs         | apple             |
| markzuckerberg    | facebook          |