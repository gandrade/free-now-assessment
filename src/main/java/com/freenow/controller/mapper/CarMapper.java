package com.freenow.controller.mapper;

import com.freenow.datatransferobject.CarCriteriaDTO;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.datatransferobject.ManufacturerDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.ManufacturerDO;

import java.util.List;
import java.util.stream.Collectors;

public class CarMapper
{

    public static CarDO makeDriverDO(CarCriteriaDTO carCriteriaDTO)
    {
        if (carCriteriaDTO == null)
        {
            return null;
        }

        ManufacturerDO manufacturerDO = ManufacturerMapper.makeManufacturerDO(carCriteriaDTO.getManufacturerDTO());
        return new CarDO(
            carCriteriaDTO.getLicensePlate(),
            carCriteriaDTO.getSeatCount(),
            carCriteriaDTO.getConvertible(),
            carCriteriaDTO.getRating(),
            carCriteriaDTO.getEngineType(),
            manufacturerDO);
    }


    public static CarDTO makeCarDTO(CarDO carDO)
    {
        if (carDO == null)
        {
            return null;
        }
        ManufacturerDTO manufacturerDTO = ManufacturerMapper.makeManufacturerDTO(carDO.getManufacturerDO());
        return CarDTO.newBuilder()
            .setId(carDO.getId())
            .setConvertible(carDO.getConvertible())
            .setLicensePlate(carDO.getLicensePlate())
            .setEngineType(carDO.getEngineType())
            .setRating(carDO.getRating())
            .setSeatCount(carDO.getSeatCount())
            .setManufacturerDTO(manufacturerDTO)
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
