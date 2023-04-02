Feature: Edit profile

As a student
I want to view and edit my profile information
So that I can keep it updated

Scenario Outline: User edits profile information successfully (Normal Flow)
Given user is logged in
And user edits profile information with profile picture "<new_profile_picture>", university with name "<university_name>" and city "<university_city>", and enrolled course with codename "<codename>"
Then user's profile information is updated with profile picture "<new_profile_picture>", university with name "<university_name>" and city "<university_city>", and enrolled course with codename "<codename>"

Examples:
| new_profile_picture             | university_name          | university_city | codename |
| https://i.imgur.com/1J3wQYt.jpg | Chulalongkorn University | Bangkok         | CS101    |
| https://i.imgur.com/3WjYQYt.jpg | University of California | Los Angeles     | CS102    |