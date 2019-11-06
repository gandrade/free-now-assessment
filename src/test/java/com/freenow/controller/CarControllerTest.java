package com.freenow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.datatransferobject.ManufacturerDTO;
import com.freenow.domainvalue.EngineType;
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
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "logging.level.com.freenow=DEBUG")
@AutoConfigureMockMvc
@WithMockUser(username = "freenow", password = "freenow")
public class CarControllerTest
{
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithAnonymousUser
    public void shoulReturn401UnauthorizedFindingCars() throws Exception
    {
        this.mockMvc.perform(get("/v1/cars"))
            .andExpect(status().isUnauthorized());
    }


    @Test
    public void shouldFindCars() throws Exception
    {
        this.mockMvc.perform(get("/v1/cars"))
            .andExpect(status().isOk());
    }


    @Test
    public void shouldFindCarById() throws Exception
    {
        this.mockMvc.perform(get("/v1/cars/1"))
            .andExpect(status().isOk());
    }


    @Test
    public void shoulThrowEntityNotFoundExceptionFindingNonExistingCarId() throws Exception
    {
        this.mockMvc.perform(get("/v1/cars/0"))
            .andExpect(status().isNotFound());
    }


    @Test
    @DirtiesContext
    public void shouldDeleteCar() throws Exception
    {
        this.mockMvc.perform(delete("/v1/cars/1"))
            .andExpect(status().isOk());
    }


    @Test
    public void shouldDeleteNonExistingCar() throws Exception
    {
        this.mockMvc.perform(delete("/v1/cars/0"))
            .andExpect(status().isNotFound());
    }


    @Test
    public void shouldCreateACar() throws Exception
    {
        CarDTO car = CarDTO.newBuilder()
            .setConvertible(true)
            .setSeatCount(4)
            .setLicensePlate("XXXX-XXXX")
            .setEngineType(EngineType.ELETRIC)
            .setRating(5f)
            .setManufacturerDTO(
                ManufacturerDTO.newBuilder()
                    .setName("toyota")
                    .createNewManufacturerDTO())
            .createCarDTO();
        String payload = new ObjectMapper()
            .writeValueAsString(car);

        this.mockMvc.perform(post("/v1/cars/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(notNullValue())))
            .andExpect(jsonPath("$.manufacturer").exists());
    }


    @Test
    public void shouldReturnBadRequestWhenManufacturerIsMissing() throws Exception
    {
        CarDTO car = CarDTO.newBuilder()
            .setConvertible(true)
            .setSeatCount(4)
            .setLicensePlate("XXXX-XXXX")
            .setEngineType(EngineType.ELETRIC)
            .setRating(5f)
            .createCarDTO();
        String payload = new ObjectMapper()
            .writeValueAsString(car);

        this.mockMvc.perform(post("/v1/cars/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldReturnBadRequestWhenRatingGreaterThan5() throws Exception
    {
        CarDTO car = CarDTO.newBuilder()
            .setConvertible(true)
            .setSeatCount(4)
            .setLicensePlate("XXXX-XXXX")
            .setEngineType(EngineType.ELETRIC)
            .setRating(6f)
            .setManufacturerDTO(
                ManufacturerDTO.newBuilder()
                    .setName("TOYOTA")
                    .createNewManufacturerDTO())
            .createCarDTO();

        String payload = new ObjectMapper().writeValueAsString(car);

        this.mockMvc.perform(post("/v1/cars/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldReturnBadRequestWhenSeatCountIsNegativeValue() throws Exception
    {
        CarDTO car = CarDTO.newBuilder()
            .setConvertible(true)
            .setSeatCount(-4)
            .setLicensePlate("XXXX-XXXX")
            .setEngineType(EngineType.ELETRIC)
            .setRating(5f)
            .setManufacturerDTO(
                ManufacturerDTO.newBuilder()
                    .setName("TOYOTA")
                    .createNewManufacturerDTO())
            .createCarDTO();
        String payload = new ObjectMapper()
            .writeValueAsString(car);

        this.mockMvc.perform(post("/v1/cars/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldReturnBadRequestWhenRatingIsNegative() throws Exception
    {
        CarDTO car = CarDTO.newBuilder()
            .setConvertible(true)
            .setSeatCount(4)
            .setLicensePlate("XXXX-XXXX")
            .setEngineType(EngineType.ELETRIC)
            .setRating(-5f)
            .setManufacturerDTO(
                ManufacturerDTO.newBuilder()
                    .setName("TOYOTA")
                    .createNewManufacturerDTO())
            .createCarDTO();
        String payload = new ObjectMapper()
            .writeValueAsString(car);

        this.mockMvc.perform(post("/v1/cars/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldUpdateACar() throws Exception
    {
        CarDTO car = CarDTO.newBuilder()
            .setConvertible(true)
            .setSeatCount(4)
            .setLicensePlate("1111-1111")
            .setEngineType(EngineType.HYBRID)
            .setRating(5f)
            .setManufacturerDTO(
                ManufacturerDTO.newBuilder()
                    .setName("toyota")
                    .createNewManufacturerDTO())
            .createCarDTO();
        String payload = new ObjectMapper()
            .writeValueAsString(car);

        MvcResult result = this.mockMvc.perform(post("/v1/cars/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(notNullValue())))
            .andExpect(jsonPath("$.manufacturer").exists())
            .andReturn();

        CarDTO newCar = CarDTO.newBuilder()
            .setConvertible(true)
            .setSeatCount(4)
            .setLicensePlate("2222-2222")
            .setEngineType(EngineType.GAS)
            .setRating(5f)
            .setManufacturerDTO(
                ManufacturerDTO.newBuilder()
                    .setName("toyota")
                    .createNewManufacturerDTO())
            .createCarDTO();
        String newPayload = new ObjectMapper().writeValueAsString(newCar);
        Object carId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        this.mockMvc.perform(put("/v1/cars/" + carId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(newPayload))
            .andExpect(status().isOk());

    }
}
