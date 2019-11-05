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


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    @Override
    @Transactional
    public DriverDO select(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException
    {
        if (!carService.existsById(carId))
        {
            throw new EntityNotFoundException("Could not find car with id: " + carId);
        }
        if (driverRepository.existsByIdAndCarDOIsNotNull(driverId))
        {
            throw new ConstraintsViolationException("Driver " + driverId + " already has a car selected.");
        }
        CarDO carDO = carService.findAvailable(carId);
        DriverDO driverDO = findOnlineDriver(driverId);
        carDO.setDriverDO(driverDO);
        return driverDO;
    }


    @Override
    @Transactional
    public void deselect(Long driverId, Long carId) throws EntityNotFoundException
    {
        CarDO carDO = carService.deselect(carId, driverId);
        carDO.setDriverDO(null);
    }


    @Override
    public List<DriverDO> findAll(Specification<DriverDO> spec)
    {
        return driverRepository.findAll(spec);
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
        return driverRepository.findByIdAndOnlineStatus(driverId, OnlineStatus.ONLINE)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository.findById(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }

}
