package com.freenow.service.manufacturer;

import com.freenow.dataaccessobject.ManufacturerRepository;
import com.freenow.domainobject.ManufacturerDO;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.driver.DefaultDriverService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultManufacturerService implements ManufacturerService
{

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final ManufacturerRepository repository;


    public DefaultManufacturerService(ManufacturerRepository repository)
    {
        this.repository = repository;
    }


    @Override
    public ManufacturerDO findByNameIgnoreCase(String name) throws EntityNotFoundException
    {
        return repository.findByNameIgnoreCase(name).orElseThrow(() -> new EntityNotFoundException(""));
    }
}
