package com.freenow.service.driver;

import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.EngineType;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import com.freenow.specification.DriverDOSpecificationExecutor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultDriverService.class)
public class DriverServiceTest
{
    @Autowired
    private DriverService driverService;

    @MockBean
    private DriverRepository driverRepository;

    @MockBean
    private DriverDOSpecificationExecutor driverDOSpecification;

    @MockBean
    private CarService carService;


    @Test
    public void shouldThrownConstraintsViolationExceptionCreatingSameUsernameForDifferentDrivers() throws ConstraintsViolationException
    {
        DriverDO driverDO = new DriverDO("duplicated-user", "duplicated-pass");

        BDDMockito.given(driverRepository.save(driverDO)).willAnswer(answer -> {
            throw new DataIntegrityViolationException("");
        });
        try
        {
            driverService.create(driverDO);
            Assert.fail();
        }
        catch (ConstraintsViolationException e)
        {
            BDDMockito.verify(driverRepository).save(driverDO);
        }
    }


    @Test
    public void shouldThrowCarAlreadyInUseExceptionSelectingSameCarForDifferentDrivers() throws CarAlreadyInUseException, EntityNotFoundException, ConstraintsViolationException
    {
        BDDMockito.given(carService.existsById(1L)).willReturn(true);
        BDDMockito.given(driverRepository.existsByIdAndCarDO_IdIsNotNull(6L)).willReturn(false);
        DriverDO driverDO = new DriverDO("username", "password");
        BDDMockito.given(carService.findAvailable(1L)).willAnswer(answer -> {
            throw new CarAlreadyInUseException("");
        });
        try
        {
            driverService.select(6L, 1L);
            Assert.fail();
        }
        catch (CarAlreadyInUseException e)
        {
            InOrder inOrder = BDDMockito.inOrder(carService, driverRepository);
            inOrder.verify(carService).existsById(1L);
            inOrder.verify(driverRepository).existsByIdAndCarDO_IdIsNotNull(6L);
            inOrder.verify(carService).findAvailable(1L);
            inOrder.verify(driverRepository, times(0)).findByIdAndOnlineStatusAndDeletedFalse(BDDMockito.any(), BDDMockito.any());
        }
    }


    @Test
    public void shouldThrowConstrainsViolationExceptionWhenDriverAlreadyHaveASelectedCar() throws CarAlreadyInUseException, EntityNotFoundException, ConstraintsViolationException
    {
        BDDMockito.given(carService.existsById(1L)).willReturn(true);
        BDDMockito.given(driverRepository.existsByIdAndCarDO_IdIsNotNull(6L)).willReturn(true);
        try
        {
            driverService.select(6L, 1L);
            Assert.fail();
        }
        catch (ConstraintsViolationException e)
        {
            InOrder inOrder = BDDMockito.inOrder(carService, driverRepository);
            inOrder.verify(carService).existsById(1L);
            inOrder.verify(driverRepository).existsByIdAndCarDO_IdIsNotNull(6L);
            inOrder.verify(carService, times(0)).findAvailable(1L);
            inOrder.verify(driverRepository, times(0)).findByIdAndOnlineStatusAndDeletedFalse(BDDMockito.any(), BDDMockito.any());
        }
    }


    @Test
    public void shouldThrowEntityNotFoundExceptionWhenSelectUnknownCarForADriver() throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException
    {
        BDDMockito.given(carService.existsById(999L)).willReturn(false);
        try
        {
            driverService.select(999L, 999L);
            Assert.fail();
        }
        catch (EntityNotFoundException e)
        {
            InOrder inOrder = BDDMockito.inOrder(carService, driverRepository);
            inOrder.verify(carService).existsById(999L);
            inOrder.verify(driverRepository, times(0)).existsByIdAndCarDO_IdIsNotNull(BDDMockito.any());
            inOrder.verify(carService, times(0)).findAvailable(BDDMockito.any());
            inOrder.verify(driverRepository, times(0)).findByIdAndOnlineStatusAndDeletedFalse(BDDMockito.any(), BDDMockito.any());
        }
    }


    @Test
    public void shouldThrowEntityNotFoundExceptionWhenSelectUnknownDriverForACar() throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException
    {
        BDDMockito.given(carService.existsById(1L)).willReturn(true);
        BDDMockito.given(driverRepository.existsByIdAndCarDO_IdIsNotNull(0000L)).willReturn(false);
        CarDO carDO = new CarDO("LICENSE-TST", 4, false, 5f, EngineType.GAS);
        BDDMockito.given(carService.findAvailable(1L)).willReturn(carDO);
        BDDMockito.given(driverRepository.findByIdAndOnlineStatusAndDeletedFalse(0000L, OnlineStatus.ONLINE)).willAnswer(answer -> {
            throw new EntityNotFoundException("");
        });
        try
        {
            driverService.select(0000L, 1L);
            Assert.fail();
        }
        catch (EntityNotFoundException e)
        {
            InOrder inOrder = BDDMockito.inOrder(carService, driverRepository);
            inOrder.verify(carService).existsById(1L);
            inOrder.verify(driverRepository).existsByIdAndCarDO_IdIsNotNull(BDDMockito.any());
            inOrder.verify(carService).findAvailable(BDDMockito.any());
            inOrder.verify(driverRepository).findByIdAndOnlineStatusAndDeletedFalse(BDDMockito.any(), BDDMockito.any());
        }

    }


    @Test
    public void shouldDeselectCarForADriver() throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException
    {
        CarDO carDO = new CarDO("LICENSE-TST", 4, false, 5f, EngineType.GAS);
        DriverDO driverDO = new DriverDO("username", "password");
        driverDO.setCarDO(carDO);
        BDDMockito.given(carService.deselect(6L, 1L)).willReturn(carDO);
        driverService.deselect(1L, 6L);
        BDDMockito.verify(carService).deselect(6L, 1L);
    }


    @Test
    public void shouldThrowEntityNotFoundExceptionWhenUnknownDriver() throws EntityNotFoundException
    {
        BDDMockito.given(carService.deselect(1L, 999L)).willAnswer(answer -> {
            throw new EntityNotFoundException("");
        });
        try
        {
            driverService.deselect(999L, 1L);
            Assert.fail();
        }
        catch (EntityNotFoundException e)
        {
            BDDMockito.verify(carService).deselect(1L, 999L);
        }
    }

}
