package com.freenow.service.manufacturer;

import com.freenow.domainobject.ManufacturerDO;
import com.freenow.exception.EntityNotFoundException;

public interface ManufacturerService
{

    /**
     * Find manufacturer by name ignoring case.
     *
     * @param name Manufacture's name.
     * @return Manufacturer
     * @throws EntityNotFoundException When manufacturer name was not found.
     */
    ManufacturerDO findByNameIgnoreCase(String name) throws EntityNotFoundException;
}

