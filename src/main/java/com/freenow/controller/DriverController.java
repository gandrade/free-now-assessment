package com.freenow.controller;

import com.freenow.controller.mapper.DriverMapper;
import com.freenow.controller.specification.DriverDOSpecification;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import com.freenow.service.driver.DriverService;
import java.util.List;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
@Api(tags = {"Driver management"})
public class DriverController
{

    private final DriverService driverService;


    public DriverController(final DriverService driverService, final CarService carService)
    {
        this.driverService = driverService;
    }


    @GetMapping("/{driverId}")
    @ApiOperation(value = "Return a driver based on its identification.")
    public DriverDTO getDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates a new Driver.")
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    @ApiOperation(value = "Deletes a Driver.")
    public void deleteDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    @ApiOperation(value = "Updates Driver's location.")
    public void updateLocation(
        @Valid @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    @PutMapping("/{driverId}/cars/{carId}/select")
    @ApiOperation(value = "Selects a Driver for a given Car.")
    public DriverDTO selectCar(@PathVariable Long driverId, @PathVariable Long carId) throws EntityNotFoundException, ConstraintsViolationException, CarAlreadyInUseException
    {
        return DriverMapper.makeDriverDTO(driverService.select(driverId, carId));
    }


    @PutMapping("/{driverId}/cars/{carId}/deselect")
    @ApiOperation(value = "Deselects a Driver for a given Car.")
    public void deselectCar(@PathVariable Long driverId, @PathVariable Long carId) throws EntityNotFoundException
    {
        driverService.deselect(driverId, carId);
    }


    @GetMapping
    @ApiOperation(value = "Returns a list of all drivers based on given criteria.")
    public List<DriverDTO> findDrivers(DriverDTO driverDTO)
    {
        Specification<DriverDO> spec = DriverDOSpecification.makeDriverDOSpecification(driverDTO);
        return DriverMapper.makeDriverDTOList(driverService.findAll(spec));
    }


    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.initDirectFieldAccess();
    }
}
