package com.freenow.controller.mapper;

import com.freenow.datatransferobject.DriverCriteriaDTO;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.GeoCoordinate;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DriverMapper
{
    public static DriverDO makeDriverDO(DriverDTO driverDTO)
    {
        return new DriverDO(driverDTO.getUsername(), driverDTO.getPassword());
    }

    public static DriverDO makeDriverDO(DriverCriteriaDTO driverDTO)
    {
        if (driverDTO == null)
        {
            return null;
        }
        DriverDO driverDO = new DriverDO(driverDTO.getUsername(), null);
        driverDO.setOnlineStatus(driverDTO.getOnlineStatus());
        driverDO.setCoordinate(driverDTO.getCoordinate());
        driverDO.setCarDO(CarMapper.makeDriverDO(driverDTO.getCarDTO()));
        return driverDO;
    }

    public static DriverDTO makeDriverDTO(DriverDO driverDO)
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder = DriverDTO.newBuilder()
            .setId(driverDO.getId())
            .setPassword(driverDO.getPassword())
            .setUsername(driverDO.getUsername());

        GeoCoordinate coordinate = driverDO.getCoordinate();
        if (coordinate != null)
        {
            driverDTOBuilder.setCoordinate(coordinate);
        }

        driverDTOBuilder.setCarDTO(CarMapper.makeCarDTO(driverDO.getCarDO()));
        return driverDTOBuilder.createDriverDTO();
    }


    public static List<DriverDTO> makeDriverDTOList(Collection<DriverDO> drivers)
    {
        return drivers.stream()
            .map(DriverMapper::makeDriverDTO)
            .collect(Collectors.toList());
    }
}
