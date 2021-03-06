package com.openclassrooms.calculator2.calculation.cucumber.steps;

import com.openclassrooms.calculator2.calculation.cucumber.CucumberAT;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CucumberAT.class)
@AutoConfigureMockMvc
@CucumberContextConfiguration
public class CalculatorSteps {

    @Autowired
    MockMvc mockMvc;

    private Integer lastLeftArgument;
    private Integer lastRightArgument;
    private String calculationType;

    @BeforeEach
    public void setup(){
        lastLeftArgument = null;
        lastRightArgument = null;
        calculationType = null;
    }

    @Given("a student is using the Calculator")
    public void a_student_is_using_the_Calculator() throws Exception {
        mockMvc.perform(get("/calculator")).
                andExpect(status().is2xxSuccessful()).
                andExpect((ResultMatcher) content().string(containsString("leftArgument"))).
                andExpect((ResultMatcher) content().string(containsString("rightArgument"))).
                andExpect((ResultMatcher) content().string(containsString("calculationType")));
    }
    @When("{int} and {int} are added")
    public void and_are_added(Integer leftArgument, Integer rightArgument) throws Exception {
        this.lastLeftArgument = leftArgument;
        this.lastRightArgument = rightArgument;
        this.calculationType = "ADDITION";
    }
    @Then("the student is shown {int}")
    public void the_student_is_shown(Integer expectedResult) throws Exception {
        mockMvc.perform(post("/calculator").
                param("leftArgument", lastLeftArgument.toString()).
                param("rightArgument", lastRightArgument.toString()).
                param("calculationType", "ADDITION")
        ).
                andExpect(status().is2xxSuccessful()).
                andExpect(
                        (ResultMatcher) content().string(
                                containsString(">" + expectedResult + "<")));
    }

}
