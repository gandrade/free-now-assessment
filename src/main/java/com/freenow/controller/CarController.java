package com.freenow.controller;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("v1/cars")
@RestController
@Api(tags = {"Car management"})
public class CarController
{

    private CarService carService;

    public CarController(final CarService carService)
    {
        this.carService = carService;
    }


    @GetMapping
    @ApiOperation(value = "Returns a list of all cars available.")
    public List<CarDTO> getCars()
    {
        return CarMapper.makeCarDTOList(carService.findAll());
    }


    @GetMapping("/{carId}")
    @ApiOperation(value = "Return a car based on its identification.")
    public CarDTO getCar(@Valid @PathVariable long carId) throws EntityNotFoundException
    {
        return CarMapper.makeCarDTO(carService.find(carId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates a new Car.")
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException, EntityNotFoundException
    {
        CarDO carDO = CarMapper.makeDriverDO(carDTO);
        return CarMapper.makeCarDTO(carService.create(carDO));
    }


    @DeleteMapping("/{carId}")
    @ApiOperation(value = "Deletes a Car.")
    public void deleteCar(@Valid @PathVariable long carId) throws EntityNotFoundException
    {
        carService.delete(carId);
    }


    @PutMapping("/{carId}")
    @ApiOperation(value = "Update car attributes.")
    public void updateCar(
        @Valid @PathVariable long carId,
        @Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException, EntityNotFoundException
    {
        carService.update(carId, CarMapper.makeDriverDO(carDTO));
    }
}
