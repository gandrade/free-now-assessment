package com.freenow.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "logging.level.com.freenow=DEBUG")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class HomeControllerTest
{

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldRedirectToSwaggerUI() throws Exception
    {
        this.mockMvc.perform(get("/"))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl("swagger-ui.html"));
    }


    @Test
    public void shouldReturn403UnauthorizedCallingDriversContext() throws Exception
    {
        this.mockMvc.perform(get("/v1/drivers"))
            .andExpect(status().isUnauthorized());
    }


    @Test
    public void shouldReturn403UnauthorizedCallingCarsContext() throws Exception
    {
        this.mockMvc.perform(get("/v1/cars"))
            .andExpect(status().isUnauthorized());
    }
}
