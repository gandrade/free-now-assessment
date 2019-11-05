package com.freenow.service.manufacturer;

import com.freenow.domainobject.ManufacturerDO;
import com.freenow.exception.EntityNotFoundException;

public interface ManufacturerService
{

    ManufacturerDO findByNameIgnoreCase(String name) throws EntityNotFoundException;
}

