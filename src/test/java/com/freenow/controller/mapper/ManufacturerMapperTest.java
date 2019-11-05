package com.freenow.controller.mapper;

import com.freenow.datatransferobject.ManufacturerDTO;
import com.freenow.domainobject.ManufacturerDO;
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
}
