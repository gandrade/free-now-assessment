package com.freenow.service.car;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.ManufacturerDO;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.driver.DefaultDriverService;
import com.freenow.service.manufacturer.ManufacturerService;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCarService implements CarService
{

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final CarRepository carRepository;

    private final ManufacturerService manufacturerService;


    public DefaultCarService(final CarRepository carRepository, final ManufacturerService manufacturerService)
    {
        this.carRepository = carRepository;
        this.manufacturerService = manufacturerService;
    }


    /** {@inheritDoc */
    @Override
    public CarDO find(Long carId) throws EntityNotFoundException
    {
        return carRepository.findById(carId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find Car entity with id: " + carId));
    }


    /** {@inheritDoc */
    @Override
    public CarDO findAvailable(Long carId) throws CarAlreadyInUseException
    {
        return carRepository.findByIdAndDriverDOIsNull(carId)
            .orElseThrow(() -> new CarAlreadyInUseException("Car " + carId + " already in use."));
    }


    /** {@inheritDoc */
    @Override
    public CarDO deselect(Long carId, Long driverId) throws EntityNotFoundException
    {
        return carRepository.findByIdAndDriverDO_Id(carId, driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find Car with id: " + carId + " for the driver: " + driverId));
    }


    /** {@inheritDoc */
    @Override
    public CarDO create(CarDO carDO) throws ConstraintsViolationException, EntityNotFoundException
    {
        try
        {
            ManufacturerDO manufacturerDO = manufacturerService.findByNameIgnoreCase(carDO.getManufacturerDO().getName());
            carDO.setManufacturerDO(manufacturerDO);
            return carRepository.save(carDO);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new ConstraintsViolationException("Some constraints are thrown due to driver creation", e);
        }
    }


    /** {@inheritDoc */
    @Override
    public void update(long carId, CarDO carDO) throws EntityNotFoundException, ConstraintsViolationException
    {
        try
        {
            CarDO car = this.find(carId);
            car.setEngineType(carDO.getEngineType());
            car.setSeatCount(carDO.getSeatCount());
            car.setConvertible(carDO.getConvertible());
            car.setLicensePlate(carDO.getLicensePlate());
            car.setRating(carDO.getRating());
            ManufacturerDO manufacturerDO = manufacturerService.findByNameIgnoreCase(carDO.getManufacturerDO().getName());
            car.setManufacturerDO(manufacturerDO);
            car.setDriverDO(carDO.getDriverDO());
            carRepository.save(car);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new ConstraintsViolationException("Some constraints are thrown due to driver creation", e);
        }
    }


    /** {@inheritDoc */
    @Override
    public void delete(Long carId) throws EntityNotFoundException
    {
        try
        {
            carRepository.deleteById(carId);
        }
        catch (EmptyResultDataAccessException e)
        {
            throw new EntityNotFoundException("Couldn't delete car " + carId + ".", e);
        }
    }


    /** {@inheritDoc */
    @Override
    public boolean existsById(Long carId)
    {
        return carRepository.existsById(carId);
    }


    /** {@inheritDoc */
    @Override
    public List<CarDO> findAll()
    {
        return (List<CarDO>) carRepository.findAll();
    }
}
