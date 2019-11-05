package com.freenow.datatransferobject;

import javax.validation.constraints.NotNull;

public class ManufacturerDTO
{

    private Long id;

    @NotNull
    private String name;


    private ManufacturerDTO()
    {

    }


    private ManufacturerDTO(Long id, String name)
    {
        this.id = id;
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
        private Long id;
        private String name;


        public ManufacturerDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public ManufacturerDTOBuilder setName(String name)
        {
            this.name = name;
            return this;
        }


        public ManufacturerDTO createNewManufacturerDTO()
        {
            return new ManufacturerDTO(id, name);
        }
    }

}
