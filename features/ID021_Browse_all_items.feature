Feature: Browse all items available for sale.

As an existing UniTrade user
I want to browse all items available for sale
So that I can find items I may be interested in

Scenario: Browse all items available for sale (Normal Flow)
Given user is logged in
And user is on any page
When user clicks on the Browse button
Then user is taken to the Browse page where all items available for sale are listed