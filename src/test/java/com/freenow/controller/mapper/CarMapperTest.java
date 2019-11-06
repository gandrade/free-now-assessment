package com.freenow.controller.mapper;

import com.freenow.datatransferobject.CarCriteriaDTO;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.datatransferobject.ManufacturerCriteriaDTO;
import com.freenow.datatransferobject.ManufacturerDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.ManufacturerDO;

import com.freenow.domainvalue.EngineType;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class CarMapperTest
{

    private ManufacturerDO manufacturerDO;


    @Before
    public void setup()
    {
        manufacturerDO = new ManufacturerDO("Tesla");
    }


    @Test
    public void shouldMakeCarDTO()
    {
        CarDO carDO = new CarDO("LICENSE-1231", 4, true, 2.2F, EngineType.ELETRIC, manufacturerDO);
        CarDTO carDTO = CarMapper.makeCarDTO(carDO);
        assertThat(carDTO.getId(), nullValue());
        assertThat(carDTO.getConvertible(), equalTo(true));
        assertThat(carDTO.getEngineType(), equalTo(EngineType.ELETRIC));
        assertThat(carDTO.getLicensePlate(), equalTo("LICENSE-1231"));
        assertThat(carDTO.getRating(), equalTo(2.2F));
        assertThat(carDTO.getSeatCount(), equalTo(4));
        assertThat(carDTO.getManufacturerDTO().getName(), equalTo("TESLA"));
    }


    @Test
    public void shouldMakeCarDTOWithNoSeatCount()
    {
        CarDO carDO = new CarDO("LICENSE-1231", null, true, 2.2F, EngineType.ELETRIC, manufacturerDO);
        CarDTO carDTO = CarMapper.makeCarDTO(carDO);
        assertThat(carDTO.getId(), nullValue());
        assertThat(carDTO.getConvertible(), equalTo(true));
        assertThat(carDTO.getEngineType(), equalTo(EngineType.ELETRIC));
        assertThat(carDTO.getLicensePlate(), equalTo("LICENSE-1231"));
        assertThat(carDTO.getRating(), equalTo(2.2F));
        assertThat(carDTO.getSeatCount(), nullValue());
        assertThat(carDTO.getManufacturerDTO().getName(), equalTo("TESLA"));
    }


    @Test
    public void shouldMakeCarDTOWithNoManufacturer()
    {
        CarDO carDO = new CarDO("LICENSE-1231", 4, true, 2.2F, EngineType.ELETRIC);
        CarDTO carDTO = CarMapper.makeCarDTO(carDO);
        assertThat(carDTO.getId(), nullValue());
        assertThat(carDTO.getConvertible(), equalTo(true));
        assertThat(carDTO.getEngineType(), equalTo(EngineType.ELETRIC));
        assertThat(carDTO.getLicensePlate(), equalTo("LICENSE-1231"));
        assertThat(carDTO.getRating(), equalTo(2.2F));
        assertThat(carDTO.getSeatCount(), equalTo(4));
        assertThat(carDTO.getManufacturerDTO().getName(), nullValue());
    }


    @Test
    public void shouldMakeCarDO()
    {
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO.ManufacturerDTOBuilder().setName("Tesla").createNewManufacturerDTO();
        CarDTO carDTO = new CarDTO.CarDTOBuilder()
            .setManufacturerDTO(manufacturerDTO)
            .setSeatCount(4)
            .setConvertible(true)
            .setEngineType(EngineType.ELETRIC)
            .setLicensePlate("LICENSE123").createCarDTO();
        CarDO carDO = CarMapper.makeDriverDO(carDTO);
        assertThat(carDO.getConvertible(), equalTo(true));
        assertThat(carDO.getSeatCount(), equalTo(4));
        assertThat(carDO.getEngineType(), equalTo(EngineType.ELETRIC));
        assertThat(carDO.getLicensePlate(), equalTo("LICENSE123"));
        assertThat(carDO.getManufacturerDO().getName(), equalTo("TESLA"));
    }


    @Test
    public void shouldMakeCarDOUsingCarCriteria()
    {
        ManufacturerCriteriaDTO manufacturerDTO = new ManufacturerCriteriaDTO.ManufacturerDTOBuilder().setName("Tesla").createManufacturerDTO();
        CarCriteriaDTO carDTO = new CarCriteriaDTO.CarDTOBuilder()
            .setManufacturerDTO(manufacturerDTO)
            .setSeatCount(4)
            .setConvertible(true)
            .setEngineType(EngineType.ELETRIC)
            .setLicensePlate("LICENSE123")
            .setId(10L)
            .setRating(2f)
            .createCarDTO();
        CarDO carDO = CarMapper.makeDriverDO(carDTO);
        assertThat(carDO.getRating(), equalTo(2f));
        assertThat(carDO.getConvertible(), equalTo(true));
        assertThat(carDO.getSeatCount(), equalTo(4));
        assertThat(carDO.getEngineType(), equalTo(EngineType.ELETRIC));
        assertThat(carDO.getLicensePlate(), equalTo("LICENSE123"));
        assertThat(carDO.getManufacturerDO().getName(), equalTo("TESLA"));
    }
}
