package com.freenow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainvalue.GeoCoordinate;
import com.jayway.jsonpath.JsonPath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "mytaxi", password = "mytaxi")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DriverControllerTest
{

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithAnonymousUser
    public void shouldReturn401UnauthorizedGettingAllDrivers() throws Exception
    {
        this.mockMvc.perform(get("/v1/drivers"))
            .andExpect(status().isUnauthorized());
    }


    @Test
    @DirtiesContext
    public void shouldReturnAllDrivers() throws Exception
    {
        this.mockMvc.perform(get("/v1/drivers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].id", is(notNullValue())))
            .andExpect(jsonPath("$.length()", is(8)));
    }


    @Test
    @DirtiesContext
    public void shouldReturnAllDriversButDeletedFalse() throws Exception
    {
        this.mockMvc.perform(delete("/v1/drivers/8"))
            .andExpect(status().isOk());

        this.mockMvc.perform(get("/v1/drivers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].id", is(notNullValue())))
            .andExpect(jsonPath("$.length()", is(7)));
    }


    @Test
    @DirtiesContext
    public void shouldNotReturnADeletedDriver() throws Exception
    {
        this.mockMvc.perform(delete("/v1/drivers/8"))
            .andExpect(status().isOk());

        this.mockMvc.perform(get("/v1/drivers?onlineStatus=ONLINE&username=driver08"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", is(0)));
    }


    @Test
    @DirtiesContext
    public void shouldReturnDriverUsingAllFilters() throws Exception
    {

        this.mockMvc.perform(put("/v1/drivers/8/cars/5/select")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        this.mockMvc.perform(get("/v1/drivers")
            .param("username", "driver08")
            .param("carDTO.seatCount", "2")
            .param("carDTO.rating", "4")
            .param("carDTO.licensePlate", "license-888")
            .param("carDTO.engineType", "HYBRID")
            .param("carDTO.manufacturerDTO.name", "tesla"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].id", is(notNullValue())))
            .andExpect(jsonPath("$.length()", is(1)));

    }

    @Test
    public void shouldReturnDriversNonConvertibleCarsOnly() throws Exception
    {
        this.mockMvc.perform(get("/v1/drivers")
            .param("carDTO.convertible", "false"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", is(0)));
    }


    @Test
    @DirtiesContext
    public void shouldReturnDriversTeslaCarsOnly() throws Exception
    {
        this.mockMvc.perform(put("/v1/drivers/8/cars/5/select")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        this.mockMvc.perform(get("/v1/drivers")
            .param("carDTO.manufacturerDTO.name", "TESLA"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].id", is(notNullValue())))
            .andExpect(jsonPath("$.length()", is(1)));
    }


    @Test
    @DirtiesContext
    public void shouldReturnADriverWithNoCarAssigned() throws Exception
    {
        this.mockMvc.perform(get("/v1/drivers")
            .param("username", "driver08"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].id", is(notNullValue())))
            .andExpect(jsonPath("$.[0].car").doesNotExist())
            .andExpect(jsonPath("$.length()", is(1)));
    }


    @Test
    public void shouldReturnASingleDriver() throws Exception
    {
        this.mockMvc.perform(get("/v1/drivers/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(notNullValue())));
    }


    @Test
    @DirtiesContext
    public void shouldReturnASingleDriverNonDeleted() throws Exception
    {

        this.mockMvc.perform(delete("/v1/drivers/1"))
            .andExpect(status().isOk());

        this.mockMvc.perform(get("/v1/drivers/1"))
            .andExpect(status().isNotFound());
    }


    @Test
    @DirtiesContext
    public void shouldCreateADriver() throws Exception
    {
        DriverDTO driverDTO = DriverDTO.newBuilder()
            .setUsername("driver-tst")
            .setPassword("driver-tst-pw")
            .createDriverDTO();
        String payload = new ObjectMapper().writeValueAsString(driverDTO);

        this.mockMvc.perform(post("/v1/drivers/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(notNullValue())));
    }


    @Test
    public void shouldReturnErrorCreatingADriverWithoutUsername() throws Exception
    {
        DriverDTO driverDTO = DriverDTO.newBuilder()
            .setPassword("driver-tst-pw")
            .createDriverDTO();
        String payload = new ObjectMapper().writeValueAsString(driverDTO);

        this.mockMvc.perform(post("/v1/drivers/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldReturnErrorCreatingADriverWithoutPassword() throws Exception
    {
        DriverDTO driverDTO = DriverDTO.newBuilder()
            .setUsername("driver-tst")
            .createDriverDTO();
        String payload = new ObjectMapper().writeValueAsString(driverDTO);

        this.mockMvc.perform(post("/v1/drivers/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isBadRequest());
    }


    @Test
    @DirtiesContext
    public void shouldReturnContraintValidationErrorCreating2DriversUsingSameUsername() throws Exception
    {
        DriverDTO driverDTO = DriverDTO.newBuilder()
            .setUsername("driver-tst")
            .setPassword("driver-tst-pwd")
            .createDriverDTO();
        String payload = new ObjectMapper().writeValueAsString(driverDTO);

        this.mockMvc.perform(post("/v1/drivers/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isCreated());

        this.mockMvc.perform(post("/v1/drivers/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldUpdateDriverLocation() throws Exception
    {
        GeoCoordinate coordinate = new GeoCoordinate(12, 12);
        this.mockMvc.perform(put("/v1/drivers/1")
            .param("longitude", String.valueOf(coordinate.getLongitude()))
            .param("latitude", String.valueOf(coordinate.getLatitude()))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }


    @Test
    @DirtiesContext
    public void shouldDeleteDriver() throws Exception
    {
        DriverDTO driverDTO = DriverDTO.newBuilder()
            .setUsername("driver-to-be-deleted")
            .setPassword("driver-to-be-deleted")
            .createDriverDTO();
        String payload = new ObjectMapper().writeValueAsString(driverDTO);

        MvcResult mvcResult = this.mockMvc.perform(post("/v1/drivers/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isCreated()).andReturn();
        Integer id = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.id");
        this.mockMvc.perform(delete("/v1/drivers/" + id))
            .andExpect(status().isOk());
    }


    @Test
    @DirtiesContext
    public void shouldSelectCarForADriver() throws Exception
    {
        this.mockMvc.perform(put("/v1/drivers/8/cars/1/select")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }


    @Test
    @DirtiesContext
    public void shouldReturnCarAlreadyInUseSelectingCarForADriver() throws Exception
    {
        this.mockMvc.perform(put("/v1/drivers/8/cars/1/select")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        this.mockMvc.perform(put("/v1/drivers/6/cars/1/select")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict());
    }


    @Test
    public void shouldReturnErrorWhenSelectingCarForAOfflineDriver() throws Exception
    {
        this.mockMvc.perform(put("/v1/drivers/1/cars/1/select")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }


    @Test
    @DirtiesContext
    public void shouldDeselectCarForADriver() throws Exception
    {
        this.mockMvc.perform(put("/v1/drivers/8/cars/1/select")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        this.mockMvc.perform(put("/v1/drivers/8/cars/1/deselect")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
