package com.freenow.service.driver;

import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;

import java.util.List;

public interface DriverService
{

    /**
     * Selects a driver by id.
     *
     * @param driverId Driver identification.
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    DriverDO find(Long driverId) throws EntityNotFoundException;

    /**
     * Creates a new driver.
     *
     * @param driverDO {@link DriverDO}
     * @return created driver
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    /**
     * Deletes an existing driver by id.
     *
     * @param driverId Driver identification.
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    void delete(Long driverId) throws EntityNotFoundException;

    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude Longitude coordinate
     * @param latitude Latitude coordinate
     * @throws EntityNotFoundException
     */
    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    /**
     * Select/Assigns a car {@link CarDO} for a given Driver {@link DriverDO}.
     *
     * @param driverId Driver identification.
     * @param carId Car identification
     * @return DriverDO
     * @throws EntityNotFoundException Either applied whenever a driver or car wasn't found.
     * @throws CarAlreadyInUseException Whenever chosen car was already been selected by another driver.
     * @throws ConstraintsViolationException Whenever a driver tries to select a car even thought that's already one assigned to them.
     */
    DriverDO select(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException, ConstraintsViolationException;

    /**
     * Unassign/deselect a car {@link CarDO} for a given Driver {@link DriverDO}.
     *
     * @param driverId Driver identification.
     * @param carId Car identification
     * @throws EntityNotFoundException Either applied whenever a driver or can wasn't found.
     */
    void deselect(Long driverId, Long carId) throws EntityNotFoundException;

    /**
     * Returns drivers that matching the attributes provided.
     * @param driverDO {@link DriverDO}
     * @return List of drivers were attributes have matched.
     */
    List<DriverDO> findAll(DriverDO driverDO);

}
