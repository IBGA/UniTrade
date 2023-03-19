Feature: Edit profile

As a student
I want to view and edit my profile information
So that I can keep it updated

Scenario Outline: User edits profile information successfully (Normal Flow)
Given user is logged in
And user edits profile information with username "<new_username>", profile picture "<new_profile_picture>", university with name "university_name" and city "university_city", and enrolled course with codename "codename"
Then user's profile information is updated with username "<new_username>", profile picture "<new_profile_picture>", university with name "university_name" and city "university_city", and enrolled course with codename "codename"

Examples:
| new_username | new_profile_picture | university_name | university_city | codename | 
| 580noscope | https://i.imgur.com/1J3wQYt.jpg | Chulalongkorn University | Bangkok | CS101 |
| dracula77 | https://i.imgur.com/3WjYQYt.jpg | University of California | Los Angeles | CS102 |