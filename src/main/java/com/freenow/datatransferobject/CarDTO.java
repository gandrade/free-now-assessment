package com.freenow.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.freenow.domainvalue.EngineType;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO
{

    private Long id;

    @NotNull
    private String licensePlate;

    @Positive
    @Max(value = 5)
    private Integer seatCount;
    private Boolean convertible;

    @Min(value = 0)
    @Max(value = 5)
    private Float rating;

    private EngineType engineType;

    @JsonProperty("manufacturer")
    @NotNull
    @Valid
    private ManufacturerDTO manufacturerDTO;


    private CarDTO()
    {

    }


    private CarDTO(Long id, String licensePlate, Integer seatCount, Boolean convertible, Float rating, EngineType engineType)
    {
        this.id = id;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
    }


    private CarDTO(Long id, String licensePlate, Integer seatCount, Boolean convertible, Float rating, EngineType engineType, ManufacturerDTO manufacturerDTO)
    {
        this(id, licensePlate, seatCount, convertible, rating, engineType);
        if (manufacturerDTO == null)
        {
            this.manufacturerDTO = ManufacturerDTO.newBuilder().createNewManufacturerDTO();
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


    public Long getId()
    {
        return id;
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


    public ManufacturerDTO getManufacturerDTO()
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
        private ManufacturerDTO manufacturerDTO;


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


        public CarDTOBuilder setManufacturerDTO(ManufacturerDTO manufacturerDTO)
        {
            this.manufacturerDTO = manufacturerDTO;
            return this;
        }


        public CarDTO createCarDTO()
        {
            return new CarDTO(id, licensePlate, seatCount, convertible, rating, engineType, manufacturerDTO);
        }
    }
}
