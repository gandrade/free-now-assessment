package com.freenow.dataaccessobject;

import com.freenow.domainobject.CarDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Database Access Object for car table.
 * <p/>
 */
public interface CarRepository extends CrudRepository<CarDO, Long>
{
    /**
     * Find a car filtering by car's identifier.
     *
     * @param carId Car identifier
     * @return Optional of {@link CarDO}
     */
    Optional<CarDO> findByIdAndDriverDOIsNull(Long carId);

    /**
     * Find a car filtering by car's identifier and driver's identifier.
     * @param carId Car identifier
     * @param driverId Driver identifier
     * @return Optional of {@link CarDO}
     */
    Optional<CarDO> findByIdAndDriverDO_Id(Long carId, Long driverId);
}
