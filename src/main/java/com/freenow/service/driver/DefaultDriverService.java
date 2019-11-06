package com.freenow.service.driver;

import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import com.freenow.specification.DriverDOSpecificationExecutor;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService
{

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;
    private final CarService carService;
    private final DriverDOSpecificationExecutor driverDOSpecificationExecutor;


    public DefaultDriverService(final DriverRepository driverRepository, CarService carService, DriverDOSpecificationExecutor driverDOSpecificationExecutor)
    {
        this.driverRepository = driverRepository;
        this.carService = carService;
        this.driverDOSpecificationExecutor = driverDOSpecificationExecutor;
    }



    /** {@inheritDoc} */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return this.findDriverChecked(driverId);
    }


    /** {@inheritDoc} */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        try
        {
            return driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new ConstraintsViolationException("Some constraints are thrown due to driver creation", e);
        }
    }


    /** {@inheritDoc} */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = this.findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /** {@inheritDoc} */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = this.findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /** {@inheritDoc} */
    @Override
    @Transactional
    public DriverDO select(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException
    {
        if (!carService.existsById(carId))
        {
            throw new EntityNotFoundException("Could not find Car entity with id: " + carId);
        }
        if (driverRepository.existsByIdAndCarDO_IdIsNotNull(driverId))
        {
            throw new ConstraintsViolationException("Driver " + driverId + " already has a car selected.");
        }
        CarDO carDO = carService.findAvailable(carId);
        DriverDO driverDO = this.findOnlineDriver(driverId);
        carDO.setDriverDO(driverDO);
        return driverDO;
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void deselect(Long driverId, Long carId) throws EntityNotFoundException
    {
        CarDO carDO = carService.deselect(carId, driverId);
        carDO.setDriverDO(null);
    }


    /** {@inheritDoc} */
    @Override
    public List<DriverDO> findAll(DriverDO driverDO)
    {
        Specification<DriverDO> driverDOSpecification = this.driverDOSpecificationExecutor.makeSpecification(driverDO);
        return driverRepository.findAll(driverDOSpecification);
    }


    private DriverDO findOnlineDriver(Long driverId) throws EntityNotFoundException
    {
        return driverRepository.findByIdAndOnlineStatusAndDeletedFalse(driverId, OnlineStatus.ONLINE)
            .orElseThrow(() -> new EntityNotFoundException("Could not find Driver entity with id: " + driverId));
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository.findByIdAndDeletedFalse(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find Driver entity with id: " + driverId));
    }

}
