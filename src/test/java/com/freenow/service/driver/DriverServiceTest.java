package com.freenow.service.driver;

import com.freenow.domainobject.DriverDO;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DriverServiceTest
{
    @Autowired
    private DriverService driverService;


    @Test(expected = ConstraintsViolationException.class)
    public void shouldThrownConstraintsViolationExceptionCreatingSameUsernameForDifferentDrivers() throws ConstraintsViolationException
    {
        DriverDO driverDO = new DriverDO("duplicated-user", "duplicated-pass");
        driverService.create(driverDO);
        driverService.create(driverDO);
    }


    @Test(expected = CarAlreadyInUseException.class)
    @DirtiesContext
    public void shouldThrowCarAlreadyInUseExceptionSelectingSameCarForDifferentDrivers() throws CarAlreadyInUseException, EntityNotFoundException, ConstraintsViolationException
    {
        driverService.select(6L, 1L);
        driverService.select(5L, 1L);
    }


    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionWhenSelectCarUnknownCarForADriver() throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException
    {
        driverService.select(999L, 999L);
    }


    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionWhenSelectUnknownCarForADriver() throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException
    {
        driverService.select(0000L, 1L);
    }


    @Test
    @DirtiesContext
    public void shouldDeselectCarForADriver() throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException
    {
        driverService.select(6L, 1L);
        driverService.deselect(6L, 1L);
    }


    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionWhenUnknownDriver() throws EntityNotFoundException
    {
        driverService.deselect(999L, 1L);
    }

}
