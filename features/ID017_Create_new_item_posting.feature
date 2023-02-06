Feature: Create new item posting

As an existing UniTrade user
I want to create a new item posting
So that I can sell course related items to other users.

Scenario Outline: University and course are found in the system, and all fields are filled in correctly (Normal Flow)
Given user is logged in
And user is on the create new item posting page
And user has found and selected the university <existing_university> from the dropdown menu
And user has found and selected the course <existing_course> from the dropdown menu
And user has filled in the item category <item_category>
And user has filled in the item name <item_name>
And user has filled in the item description <item_description>
And user has appended the item image <item_image>
And user has filled in the item condition <item_condition>
And user has filled in the item price <item_price>
And user has filled in the item quantity <item_quantity>
When user clicks the create posting button
Then the item posting of item with name <item_name>,
    item description <item_description>,
    item image <item_image>,
    item condition <item_condition>,
    item price <item_price>,
    item quantity <item_quantity>,
    item category <item_category>,
    is created and tagged with university <existing_university> and course <existing_course>

| existing_university   | existing_course | item_category | item_name                 | item_description                            | item_image     | item_condition | item_price | item_quantity |
| University of Toronto | CSC108          | textbook      | Intro to CS               | This is a textbook for CSC108               | textbook.jpg   | new            | 50         | 1             |
| McGill University     | STAT241         | calculator    | TI-84 Plus CE             | This is a TI-84 Plus CE graphing calculator | calculator.png | new            | 120        | 2             |

Scenario Outline: University and course are found in the system, and some fields are filled in incorrectly (Error Flow)
Given user is logged in
And user is on the create new item posting page
And user has found and selected the university <existing_university> from the dropdown menu
And user has found and selected the course <existing_course> from the dropdown menu
And user has filled in at least one field <incorrectly_filled_fields> incorrectly
When user clicks the create posting button
Then a "Fields filled incorrectly" error message is issued and the fields <incorrectly_filled_fields> are highlighted in red

Scenario: University and/or course is not found in the system (Alternate Flow)
Given user is logged in
And user is on the create new item posting page
When user has not found the university <nonexisting_university> or course <nonexisting_course> from the dropdown menu and clicks "add new"
Then user is prompted to add a new university or course to the system with the fields prefilled with <nonexisting_university> or <nonexisting_course>