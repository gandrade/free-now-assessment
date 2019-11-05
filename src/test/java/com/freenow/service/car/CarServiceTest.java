package com.freenow.service.car;

import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.ManufacturerDO;
import com.freenow.domainvalue.EngineType;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest
{
    @Autowired
    private CarService service;


    @Test
    public void shouldSaveCarDO() throws ConstraintsViolationException, EntityNotFoundException
    {
        CarDO carDO = new CarDO("LICENSE-NEW", 4, true, 3F, EngineType.ELETRIC, new ManufacturerDO("Toyota"));
        CarDO newCarDO = service.create(carDO);

        assertThat(newCarDO.getId(), notNullValue());
        assertThat(newCarDO.getLicensePlate(), equalTo("LICENSE-NEW"));
        assertThat(newCarDO.getEngineType(), equalTo(EngineType.ELETRIC));
        assertThat(newCarDO.getConvertible(), equalTo(true));
        assertThat(newCarDO.getSeatCount(), equalTo(4));
    }


    @Test(expected = ConstraintsViolationException.class)
    public void shouldThrowConstraintsViolationExceptionsWhenCreateCarDO() throws ConstraintsViolationException, EntityNotFoundException
    {
        CarDO carDO = new CarDO("LICENSE-TST", 4, true, 3F, EngineType.ELETRIC, new ManufacturerDO("Tesla"));
        service.create(carDO);
    }


    @Test(expected = ConstraintsViolationException.class)
    public void shouldThrowConstraintsViolationExceptionWhenUpdateCarDO() throws ConstraintsViolationException, EntityNotFoundException
    {
        CarDO carDO = new CarDO("LICENSE-TST", 4, true, 3F, EngineType.ELETRIC, new ManufacturerDO("Tesla"));
        service.update(2, carDO);
    }


}
