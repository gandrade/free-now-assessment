package com.freenow.datatransferobject;

import javax.validation.constraints.NotNull;

public class ManufacturerCriteriaDTO
{

    private Long id;

    @NotNull(message = "Name can not be null!")
    private String name;


    private ManufacturerCriteriaDTO()
    {

    }


    private ManufacturerCriteriaDTO(String name)
    {
        this.name = name;
    }


    public static ManufacturerDTOBuilder newBuilder()
    {
        return new ManufacturerDTOBuilder();
    }


    public String getName()
    {
        return name;
    }


    public static class ManufacturerDTOBuilder
    {
        private String name;


        public ManufacturerDTOBuilder setName(String name)
        {
            this.name = name;
            return this;
        }


        public ManufacturerCriteriaDTO createManufacturerDTO()
        {
            return new ManufacturerCriteriaDTO(name);
        }
    }

}
