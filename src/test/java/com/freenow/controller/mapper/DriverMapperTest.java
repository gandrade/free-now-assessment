package com.freenow.controller.mapper;

import com.freenow.datatransferobject.CarCriteriaDTO;
import com.freenow.datatransferobject.DriverCriteriaDTO;
import com.freenow.datatransferobject.ManufacturerCriteriaDTO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.EngineType;
import com.freenow.domainvalue.GeoCoordinate;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class DriverMapperTest
{

    @Test
    public void shouldReturnNullMakingDriver()
    {
        Assert.assertNull(DriverMapper.makeDriverDTO(null));
    }


    @Test
    public void shouldReturnNullMakingDriverCriteria()
    {
        DriverCriteriaDTO driverDTO = DriverCriteriaDTO.newBuilder().createDriverDTO();
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        Assert.assertThat(driverDO.getUsername(), Matchers.nullValue());
        Assert.assertThat(driverDO.getCarDO(), Matchers.nullValue());
        Assert.assertThat(driverDO.getCoordinate(), Matchers.nullValue());
        Assert.assertThat(driverDO.getOnlineStatus(), Matchers.nullValue());
        Assert.assertThat(driverDO.getPassword(), Matchers.nullValue());
    }


    @Test
    public void shouldReturnDriverDOMakingDriverCriteriaDTO()
    {
        DriverCriteriaDTO driverDTO = DriverCriteriaDTO.newBuilder()
            .setUsername("username")
            .setCoordinate(new GeoCoordinate(10d, 11d))
            .setCarDTO(CarCriteriaDTO.newBuilder()
                .setId(10L)
                .setRating(5f)
                .setConvertible(false)
                .setLicensePlate("LICENSE-TST")
                .setSeatCount(2)
                .setEngineType(EngineType.GAS)
                .setManufacturerDTO(ManufacturerCriteriaDTO.newBuilder()
                    .setName("TESTE")
                    .createManufacturerDTO())
                .createCarDTO())
            .createDriverDTO();
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        Assert.assertThat(driverDO.getUsername(), Matchers.equalTo("username"));
        Assert.assertThat(driverDO.getCoordinate().getLatitude(), Matchers.equalTo(10d));
        Assert.assertThat(driverDO.getCoordinate().getLongitude(), Matchers.equalTo(11d));
        Assert.assertThat(driverDO.getDeleted(), Matchers.equalTo(false));
        Assert.assertThat(driverDO.getCarDO().getRating(), Matchers.equalTo(5f));
        Assert.assertThat(driverDO.getCarDO().getConvertible(), Matchers.equalTo(false));
        Assert.assertThat(driverDO.getCarDO().getLicensePlate(), Matchers.equalTo("LICENSE-TST"));
        Assert.assertThat(driverDO.getCarDO().getSeatCount(), Matchers.equalTo(2));
        Assert.assertThat(driverDO.getCarDO().getEngineType(), Matchers.equalTo(EngineType.GAS));
        Assert.assertThat(driverDO.getCarDO().getManufacturerDO().getName(), Matchers.equalTo("TESTE"));

    }


    @Test
    public void shouldReturnNullWhenDriverCriteriaDTOIsNull()
    {
        DriverCriteriaDTO driverCriteriaDTO = null;
        DriverDO driverDO = DriverMapper.makeDriverDO(driverCriteriaDTO);
        Assert.assertThat(driverDO, Matchers.nullValue());
    }

}
