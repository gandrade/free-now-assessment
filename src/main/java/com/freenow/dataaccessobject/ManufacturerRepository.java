package com.freenow.dataaccessobject;

import com.freenow.domainobject.ManufacturerDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ManufacturerRepository extends CrudRepository<ManufacturerDO, Long>
{

    Optional<ManufacturerDO> findByNameIgnoreCase(String name);
}
