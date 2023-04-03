Feature: Browse all available item postings

As an existing UniTrade user
I want to browse all available item postings
So that I can find items I may be interested in

Scenario: Browse all available item postings (Normal Flow)
Given user is logged in
And there exists at least one item posting
When user searches for all available item postings
Then all available item postings are displayed

Scenario: No available item postings (Alternate Flow)
Given user is logged in
And there are no available item postings
When user searches for all available item postings
Then no item postings are displayed