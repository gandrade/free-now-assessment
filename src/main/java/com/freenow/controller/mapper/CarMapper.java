package com.freenow.controller.mapper;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;

import java.util.List;
import java.util.stream.Collectors;

public class CarMapper
{

    public static CarDTO makeCarDTO(CarDO carDO)
    {
        if (carDO == null)
        {
            return null;
        }
        return CarDTO.newBuilder()
            .setId(carDO.getId())
            .setConvertible(carDO.getConvertible())
            .setLicensePlate(carDO.getLicensePlate())
            .setEngineType(carDO.getEngineType())
            .setRating(carDO.getRating())
            .setSeatCount(carDO.getSeatCount())
            .setManufacturerDTO(ManufacturerMapper.makeManufacturerDTO(carDO.getManufacturerDO()))
            .createCarDTO();
    }


    public static CarDO makeDriverDO(CarDTO carDTO)
    {
        return new CarDO(
            carDTO.getLicensePlate(),
            carDTO.getSeatCount(),
            carDTO.getConvertible(),
            carDTO.getRating(),
            carDTO.getEngineType(),
            ManufacturerMapper.makeManufacturerDO(carDTO.getManufacturerDTO()));
    }


    public static List<CarDTO> makeCarDTOList(List<CarDO> cars)
    {
        return cars.stream()
            .map(CarMapper::makeCarDTO)
            .collect(Collectors.toList());
    }
}
