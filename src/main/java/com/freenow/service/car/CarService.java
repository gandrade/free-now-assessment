package com.freenow.service.car;

import com.freenow.domainobject.CarDO;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;

import java.util.List;

public interface CarService
{
    CarDO find(Long carId) throws EntityNotFoundException;

    CarDO findAvailable(Long carId) throws CarAlreadyInUseException;

    CarDO deselect(Long carId, Long driverId) throws EntityNotFoundException;

    CarDO create(CarDO carDO) throws ConstraintsViolationException, EntityNotFoundException;

    void update(long carId, CarDO carDO) throws EntityNotFoundException, ConstraintsViolationException;

    void delete(Long carId) throws EntityNotFoundException;

    boolean existsById(Long carId);

    List<CarDO> findAll();
}
