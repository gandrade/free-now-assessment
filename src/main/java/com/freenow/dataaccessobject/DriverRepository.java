package com.freenow.dataaccessobject;

import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long>, JpaSpecificationExecutor<DriverDO>
{

    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);

    Optional<DriverDO> findByIdAndOnlineStatus(Long driverId, OnlineStatus online);

    @Query("SELECT CASE WHEN COUNT(driver) > 0 THEN true ELSE false END FROM DriverDO driver INNER JOIN driver.carDO as carDO WHERE carDO IS NOT NULL AND driver.id=:id ")
    boolean existsByIdAndCarDOIsNotNull(@Param("id") Long driverId);
}
