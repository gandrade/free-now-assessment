package com.freenow.service.car;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.ManufacturerDO;
import com.freenow.domainvalue.EngineType;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.manufacturer.ManufacturerService;
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

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultCarService.class)
public class CarServiceTest
{
    @Autowired
    private CarService service;

    @MockBean
    private CarRepository carRepository;

    @MockBean
    private ManufacturerService manufacturerService;


    @Test
    public void shouldSaveCarDO() throws ConstraintsViolationException, EntityNotFoundException
    {
        ManufacturerDO manufacturerDO = new ManufacturerDO("Toyota");
        CarDO carDO = new CarDO("LICENSE-NEW", 4, true, 3F, EngineType.ELETRIC, manufacturerDO);
        CarDO savedCarDO = new CarDO("LICENSE-NEW", 4, true, 3F, EngineType.ELETRIC, manufacturerDO);
        savedCarDO.setId(10L);

        BDDMockito.given(manufacturerService.findByNameIgnoreCase(manufacturerDO.getName())).willReturn(manufacturerDO);
        BDDMockito.given(carRepository.save(carDO)).willReturn(savedCarDO);

        CarDO newCarDO = service.create(carDO);

        assertThat(newCarDO.getId(), notNullValue());
        assertThat(newCarDO.getLicensePlate(), equalTo("LICENSE-NEW"));
        assertThat(newCarDO.getEngineType(), equalTo(EngineType.ELETRIC));
        assertThat(newCarDO.getConvertible(), equalTo(true));
        assertThat(newCarDO.getSeatCount(), equalTo(4));

        InOrder inOrder = BDDMockito.inOrder(manufacturerService, carRepository);

        inOrder.verify(manufacturerService).findByNameIgnoreCase(manufacturerDO.getName());
        inOrder.verify(carRepository).save(carDO);
    }


    @Test
    public void shouldThrowConstraintsViolationExceptionsWhenCreateCarDO() throws ConstraintsViolationException, EntityNotFoundException
    {
        ManufacturerDO manufacturerDO = new ManufacturerDO("Tesla");
        CarDO carDO = new CarDO("LICENSE-TST", 4, true, 3F, EngineType.ELETRIC, manufacturerDO);

        BDDMockito.given(manufacturerService.findByNameIgnoreCase(manufacturerDO.getName())).willReturn(manufacturerDO);
        BDDMockito.given(carRepository.save(carDO)).willAnswer(answer -> {
            throw new DataIntegrityViolationException("");
        });

        try
        {
            service.create(carDO);
            Assert.fail();
        }
        catch (ConstraintsViolationException e)
        {
            InOrder inOrder = BDDMockito.inOrder(manufacturerService, carRepository);
            inOrder.verify(manufacturerService).findByNameIgnoreCase(manufacturerDO.getName());
            inOrder.verify(carRepository).save(carDO);
        }
    }


    @Test
    public void shouldThrowEntityNotFoundExceptionsWhenCreateCarDO() throws ConstraintsViolationException, EntityNotFoundException
    {
        ManufacturerDO manufacturerDO = new ManufacturerDO("Non-existing");
        CarDO carDO = new CarDO("LICENSE-TST", 4, true, 3F, EngineType.ELETRIC, manufacturerDO);
        BDDMockito.given(manufacturerService.findByNameIgnoreCase(manufacturerDO.getName()))
            .willAnswer(answer -> {
                throw new EntityNotFoundException("");
            });

        try
        {
            service.create(carDO);
            Assert.fail();
        }
        catch (EntityNotFoundException e)
        {

            InOrder inOrder = BDDMockito.inOrder(manufacturerService, carRepository);
            inOrder.verify(manufacturerService)
                .findByNameIgnoreCase(manufacturerDO.getName());
            inOrder.verify(carRepository, times(0))
                .save(carDO);

        }
    }


    @Test
    public void shouldThrowConstraintsViolationExceptionWhenUpdateCarDO() throws ConstraintsViolationException, EntityNotFoundException
    {
        ManufacturerDO manufacturerDO = new ManufacturerDO("Tesla");
        CarDO carDO = new CarDO("LICENSE-TST", 4, true, 3F, EngineType.ELETRIC, manufacturerDO);
        BDDMockito.given(carRepository.findById(2L)).willReturn(Optional.of(carDO));
        BDDMockito.given(manufacturerService.findByNameIgnoreCase(manufacturerDO.getName())).willReturn(manufacturerDO);
        BDDMockito.given(carRepository.save(carDO)).willAnswer(answer -> {
            throw new DataIntegrityViolationException("");
        });
        try
        {
            service.update(2, carDO);
            Assert.fail();
        }
        catch (ConstraintsViolationException e)
        {
            InOrder inOrder = BDDMockito.inOrder(manufacturerService, carRepository);
            inOrder.verify(carRepository).findById(2L);
            inOrder.verify(manufacturerService).findByNameIgnoreCase(manufacturerDO.getName());
            inOrder.verify(carRepository).save(carDO);
        }

    }


    @Test
    public void shouldThrowEntityNotFoundExceptionWhenUpdateCarDO() throws ConstraintsViolationException, EntityNotFoundException
    {
        ManufacturerDO manufacturerDO = new ManufacturerDO("Tesla");
        CarDO carDO = new CarDO("LICENSE-TST", 4, true, 3F, EngineType.ELETRIC, manufacturerDO);

        BDDMockito.given(carRepository.findById(0L)).willAnswer(answer -> {
            throw new EntityNotFoundException("");
        });
        try
        {
            service.update(0, carDO);
        }
        catch (EntityNotFoundException e)
        {
            InOrder inOrder = BDDMockito.inOrder(manufacturerService, carRepository);
            inOrder.verify(carRepository).findById(0L);
            inOrder.verify(manufacturerService, times(0)).findByNameIgnoreCase(manufacturerDO.getName());
            inOrder.verify(carRepository, times(0)).save(carDO);
        }

    }
}
