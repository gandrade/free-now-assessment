package com.freenow.controller.mapper;


import com.freenow.datatransferobject.ManufacturerCriteriaDTO;
import com.freenow.datatransferobject.ManufacturerDTO;
import com.freenow.domainobject.ManufacturerDO;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class ManufacturerMapperTest
{
    @Test
    public void shouldMakeManufacterDTO()
    {
        ManufacturerDO manufacturerDO = new ManufacturerDO("Toyota");
        ManufacturerDTO manufacturerDTO = ManufacturerMapper.makeManufacturerDTO(manufacturerDO);
        assertThat(manufacturerDTO.getName(), equalTo("TOYOTA"));
    }


    @Test
    public void shouldMakeManufacturerDO()
    {
        ManufacturerDTO manufacturerDTO = ManufacturerDTO.newBuilder()
            .setId(123l)
            .setName("Tesla")
            .createNewManufacturerDTO();
        ManufacturerDO manufacturerDO = ManufacturerMapper.makeManufacturerDO(manufacturerDTO);
        assertThat(manufacturerDO.getId(), nullValue());
        assertThat(manufacturerDO.getName(), equalTo("TESLA"));
    }


    @Test
    public void shouldReturnNullWhenManufacturerCriteriaDTOIsNull()
    {
        ManufacturerCriteriaDTO manufacturerCriteriaDTO = null;
        assertThat(ManufacturerMapper.makeManufacturerDO(manufacturerCriteriaDTO), Matchers.nullValue());
    }


    @Test
    public void shouldMakeManufacturerDOFilter()
    {
        ManufacturerCriteriaDTO manufacturerDTO = ManufacturerCriteriaDTO.newBuilder()
            .setName("Tesla")
            .createManufacturerDTO();
        ManufacturerDO manufacturerDO = ManufacturerMapper.makeManufacturerDO(manufacturerDTO);
        assertThat(manufacturerDO.getName(), equalTo("TESLA"));
    }
}
