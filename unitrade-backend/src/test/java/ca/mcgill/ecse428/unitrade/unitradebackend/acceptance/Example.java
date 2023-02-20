package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import io.cucumber.java.en.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Example extends AcceptanceTest {
    private String skyColor;
    private String grassColor;
    private Person[] peopleAvailable;
    private String place;
    private String food;
    private boolean havingFun = false;

    private class Person {
        String name;
        public Person(String name) {
            this.name = name;
        }
    }


    @Given("the sky is blue")
    public void theSkyIsBlue() {
        skyColor = "blue";
    }

    @Given("the grass is green")
    public void theGrassIsGreen() {
        grassColor = "green";
    }

    @Given("the following people are available {string}")
    public void theFollowingPeopleAreAvailablePeople(String people) {
        var peopleSplit = people.split(",");
        peopleAvailable = Arrays.stream(peopleSplit).map(Person::new).toArray(Person[]::new);
    }

    @When("we go to the place {string}")
    public void weGoToThePlacePlace(String location) {
        place = location;
        if (!place.equals("mines") && skyColor.equals("blue") && grassColor.equals("green")) {
            havingFun = true;
        }
    }

    @Then("we have fun")
    public void weHaveFun() {
        assertTrue(havingFun);
    }

    @Given("Steve is available")
    public void steveIsAvailable() {
        peopleAvailable = new Person[]{new Person("Steve")};
    }

    @When("we go to the mines")
    public void weGoToTheMines() {
        place = "mines";
    }

    @When("we eat {string} in {string}")
    public void weEatFoodInPlace(String food, String place) {
        this.food = food;
        this.place = place;
        if (!place.equals("mines") && skyColor.equals("blue") && grassColor.equals("green")) {
            havingFun = true;
        }
    }


    @Then("we do not have fun")
    public void weDoNotHaveFun() {
        assertFalse(havingFun);
    }
}
