Feature: Example feature

  Background:
    Given the sky is blue
    Given the grass is green

  Scenario Outline: Going to the park
    Given the following people are available "<people>"
    When we go to the place "<place>"
    Then we have fun

    Examples:
      | people        | place         |
      | Isaac         | Central Park  |
      | Ed, Edd, Eddy | Timbuktu      |

    Scenario: Going to the mines with Steve
      Given Steve is available
      When we go to the mines
      Then we do not have fun

    Scenario Outline: Eating food in a park
      Given the following people are available "<people>"
      When we eat "<food>" in "<place>"
      Then we have fun

      Examples:
        | people        | food         | place         |
        | Isaac         | pizza        | Central Park  |
        | Ed, Edd, Eddy | ice cream    | Timbuktu      |