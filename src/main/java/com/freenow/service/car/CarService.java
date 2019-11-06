package com.freenow.service.car;

import com.freenow.domainobject.CarDO;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;

import java.util.List;

public interface CarService
{
    /**
     * Find a car given its identification.
     *
     * @param carId Car identification.
     * @return CarDO
     * @throws EntityNotFoundException Whenever a car wasn't find.
     */
    CarDO find(Long carId) throws EntityNotFoundException;

    /**
     * Find available car given its identification.
     * @param carId Car identification.
     * @return CarDO
     * @throws CarAlreadyInUseException Whenever a given car it's already assigned for another Driver.
     */
    CarDO findAvailable(Long carId) throws CarAlreadyInUseException;

    /**
     * Unassing/deselect a car.
     * @param carId Car identification.
     * @param driverId Driver identification.
     * @return CarDO
     * @throws EntityNotFoundException Whenever a given car identification doesn't exists.
     */
    CarDO deselect(Long carId, Long driverId) throws EntityNotFoundException;

    /**
     * Creates a new car.
     *
     * @param carDO {@link CarDO}
     * @return created car.
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException Whenever a <code>manufacturer</code> for given car won't found.
     */
    CarDO create(CarDO carDO) throws ConstraintsViolationException, EntityNotFoundException;

    /**
     * Update {@link CarDO} attributes.
     *
     * @param carId Car identification.
     * @param carDO {@link CarDO}
     * @throws EntityNotFoundException Wh
     * @throws ConstraintsViolationException
     */
    void update(long carId, CarDO carDO) throws EntityNotFoundException, ConstraintsViolationException;

    /**
     * Delete an existing car.
     *
     * @param carId Car identification
     * @throws EntityNotFoundException whenever a given car doesn't exist onto database.
     */
    void delete(Long carId) throws EntityNotFoundException;

    /**
     * Checks whether a given car exists.
     *
     * @param carId Car identification.
     * @return Returns <code>true</code> whenever a given car exists, otherwise <code>false</code>.
     */
    boolean existsById(Long carId);

    /**
     * Return all cars available.
     * @return List of cars available.
     */
    List<CarDO> findAll();
}
