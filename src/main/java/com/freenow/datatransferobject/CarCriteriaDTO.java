package com.freenow.datatransferobject;

import com.freenow.domainvalue.EngineType;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CarCriteriaDTO
{

    @NotNull(message = "LicensePlate can not be null!")
    private String licensePlate;

    @Positive(message = "Only positive values allowed!")
    @Max(message = "Maximum seatCount can not be greater than 5!", value = 5)
    private Integer seatCount;

    private Boolean convertible;

    @Min(value = 0, message = "Minimum rating can not be less than 0!")
    @Max(value = 5, message = "Maximum rating can not be greater than 5!")
    private Float rating;

    private EngineType engineType;

    @NotNull
    @Valid
    private ManufacturerCriteriaDTO manufacturerDTO;


    private CarCriteriaDTO()
    {

    }


    private CarCriteriaDTO(String licensePlate, Integer seatCount, Boolean convertible, Float rating, EngineType engineType)
    {
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
    }


    private CarCriteriaDTO(String licensePlate, Integer seatCount, Boolean convertible, Float rating, EngineType engineType, ManufacturerCriteriaDTO manufacturerDTO)
    {
        this(licensePlate, seatCount, convertible, rating, engineType);
        if (manufacturerDTO == null)
        {
            this.manufacturerDTO = ManufacturerCriteriaDTO.newBuilder().createManufacturerDTO();
        }
        else
        {
            this.manufacturerDTO = manufacturerDTO;
        }
    }


    public static CarDTOBuilder newBuilder()
    {
        return new CarDTOBuilder();
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public Integer getSeatCount()
    {
        return seatCount;
    }


    public Boolean getConvertible()
    {
        return convertible;
    }


    public Float getRating()
    {
        return rating;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public ManufacturerCriteriaDTO getManufacturerDTO()
    {
        return manufacturerDTO;
    }


    public static class CarDTOBuilder
    {
        private Long id;
        private String licensePlate;
        private Integer seatCount;
        private Boolean convertible;
        private Float rating;
        private EngineType engineType;
        private ManufacturerCriteriaDTO manufacturerDTO;


        public CarDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public CarDTOBuilder setLicensePlate(String licensePlate)
        {
            this.licensePlate = licensePlate;
            return this;
        }


        public CarDTOBuilder setSeatCount(Integer seatCount)
        {
            this.seatCount = seatCount;
            return this;
        }


        public CarDTOBuilder setConvertible(Boolean convertible)
        {
            this.convertible = convertible;
            return this;
        }


        public CarDTOBuilder setRating(Float rating)
        {
            this.rating = rating;
            return this;
        }


        public CarDTOBuilder setEngineType(EngineType engineType)
        {
            this.engineType = engineType;
            return this;
        }


        public CarDTOBuilder setManufacturerDTO(ManufacturerCriteriaDTO manufacturerDTO)
        {
            this.manufacturerDTO = manufacturerDTO;
            return this;
        }


        public CarCriteriaDTO createCarDTO()
        {
            return new CarCriteriaDTO(licensePlate, seatCount, convertible, rating, engineType, manufacturerDTO);
        }
    }
}
