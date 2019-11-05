package com.freenow.dataaccessobject;

import com.freenow.domainobject.CarDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CarRepository extends CrudRepository<CarDO, Long>
{
    Optional<CarDO> findByIdAndDriverDOIsNull(Long carId);

    Optional<CarDO> findByIdAndDriverDO_Id(Long carId, Long driverId);
}
