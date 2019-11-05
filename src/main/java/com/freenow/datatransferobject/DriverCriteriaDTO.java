package com.freenow.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.domainvalue.OnlineStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverCriteriaDTO
{
    private String username;

    private GeoCoordinate coordinate;

    private OnlineStatus onlineStatus;

    private CarCriteriaDTO carDTO = CarCriteriaDTO.newBuilder().createCarDTO();

    private boolean deleted;


    private DriverCriteriaDTO()
    {
    }


    private DriverCriteriaDTO(String username, GeoCoordinate coordinate, CarCriteriaDTO carDTO, boolean deleted)
    {
        this.username = username;
        this.coordinate = coordinate;
        this.carDTO = carDTO;
        this.deleted = deleted;
    }


    public static DriverDTOBuilder newBuilder()
    {
        return new DriverDTOBuilder();
    }


    public String getUsername()
    {
        return username;
    }


    public GeoCoordinate getCoordinate()
    {
        return coordinate;
    }


    public OnlineStatus getOnlineStatus()
    {
        return onlineStatus;
    }


    public CarCriteriaDTO getCarDTO()
    {
        return carDTO;
    }


    public boolean isDeleted()
    {
        return deleted;
    }


    public static class DriverDTOBuilder
    {
        private String username;
        private GeoCoordinate coordinate;
        private CarCriteriaDTO carDTO;


        public DriverDTOBuilder setUsername(String username)
        {
            this.username = username;
            return this;
        }


        public DriverDTOBuilder setCoordinate(GeoCoordinate coordinate)
        {
            this.coordinate = coordinate;
            return this;
        }


        public DriverDTOBuilder setCarDTO(CarCriteriaDTO carDTO)
        {
            this.carDTO = carDTO;
            return this;
        }


        public DriverCriteriaDTO createDriverDTO()
        {
            return new DriverCriteriaDTO(username, coordinate, carDTO, false);
        }
    }
}
