package com.freenow.dataaccessobject;

import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long>, JpaSpecificationExecutor<DriverDO>
{

    /**
     * Find Driver by identification and marked as deleted.
     *
     * @param driverId Driver identification
     * @return DriverDO
     */
    Optional<DriverDO> findByIdAndDeletedFalse(Long driverId);

    /**
     * Find for Online drivers.
     *
     * @param onlineStatus {@link OnlineStatus}
     * @return List of online drivers.
     */
    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);

    /**
     * Find driver by identifier and {@link OnlineStatus}
     *
     * @param driverId Driver's Identifier
     * @param online {@link OnlineStatus}
     * @return Online driver
     */
    Optional<DriverDO> findByIdAndOnlineStatusAndDeletedFalse(Long driverId, OnlineStatus online);

    /**
     * Checks whether exists an assigned car for a given driver identifier.
     *
     * @param driverId Driver identifier
     * @return true when exists and false otherwise.
     */
    boolean existsByIdAndCarDO_IdIsNotNull(Long driverId);
}
