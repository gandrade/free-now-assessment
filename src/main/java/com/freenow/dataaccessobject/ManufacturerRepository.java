package com.freenow.dataaccessobject;

import com.freenow.domainobject.ManufacturerDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Database Access Object for manufacturer table.
 * <p/>
 */
public interface ManufacturerRepository extends CrudRepository<ManufacturerDO, Long>
{

    /**
     * Find manufacturer by name ignoring case
     * @param name Manufacturer name
     * @return Optional of {@link ManufacturerDO}
     */
    Optional<ManufacturerDO> findByNameIgnoreCase(String name);
}
