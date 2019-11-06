package com.freenow.domainobject;

import com.freenow.domainvalue.EngineType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "car",
    uniqueConstraints = @UniqueConstraint(name = "uc_license_plate", columnNames = {"licensePlate"}))
public class CarDO
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    private String licensePlate;

    private Integer seatCount;

    private Boolean convertible = false;

    private Float rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EngineType engineType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private ManufacturerDO manufacturerDO;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private DriverDO driverDO;


    private CarDO()
    {
    }


    public CarDO(String licensePlate, Integer seatCount, Boolean convertible, Float rating, EngineType engineType)
    {
        this.licensePlate = licensePlate == null ? null : licensePlate.toUpperCase();
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
    }


    public CarDO(String licensePlate, Integer seatCount, Boolean convertible, Float rating, EngineType engineType, ManufacturerDO manufacturerDO)
    {
        this(licensePlate, seatCount, convertible, rating, engineType);
        this.manufacturerDO = manufacturerDO;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }


    public Integer getSeatCount()
    {
        return seatCount;
    }


    public void setSeatCount(Integer seatCount)
    {
        this.seatCount = seatCount;
    }


    public Boolean getConvertible()
    {
        return convertible;
    }


    public void setConvertible(Boolean convertible)
    {
        this.convertible = convertible;
    }


    public Float getRating()
    {
        return rating;
    }


    public void setRating(Float rating)
    {
        this.rating = rating;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public void setEngineType(EngineType engineType)
    {
        this.engineType = engineType;
    }


    public ManufacturerDO getManufacturerDO()
    {
        return manufacturerDO;
    }


    public void setManufacturerDO(ManufacturerDO manufacturerDO)
    {
        this.manufacturerDO = manufacturerDO;
    }


    public DriverDO getDriverDO()
    {
        return driverDO;
    }


    public void setDriverDO(DriverDO driverDO)
    {
        if (driverDO == null)
        {
            if (this.driverDO != null)
            {
                this.driverDO.setCarDO(null);
            }
        }
        else
        {
            driverDO.setCarDO(this);
        }
        this.driverDO = driverDO;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        CarDO carDO = (CarDO) o;
        return Objects.equals(id, carDO.id);
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}
