package com.freenow.service.manufacturer;

import com.freenow.domainobject.ManufacturerDO;
import com.freenow.exception.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManufacturerServiceTest
{
    @Autowired
    private ManufacturerService service;


    @Test(expected = EntityNotFoundException.class)
    public void shouldNotReturnManufacturerWhenFindByNameIgnoreCase() throws EntityNotFoundException
    {
        service.findByNameIgnoreCase("Manufacturer Test");
    }


    @Test
    public void shouldReturnManufacturerWhenFindByNameIgnoreCase() throws EntityNotFoundException
    {
        ManufacturerDO result = service.findByNameIgnoreCase("Toyota");
        assertThat(result.getName(), equalTo("TOYOTA"));
        assertThat(result.getDateCreated(), notNullValue());
        assertThat(result.getId(), notNullValue());
    }
}
