package com.freenow.specification;

import com.freenow.domainobject.DriverDO;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Collections;

public class DriverSpecificationExecutorTest
{
    private DriverDOSpecificationExecutor driverDOSpecificationExecutor;
    private CarDOSpecificationExecutor carDOSpecificationExecutor;
    private ManufacturerDOSpecificationExecutor manufacturerDOSpecificationExecutor;


    @Before
    public void setup()
    {
        manufacturerDOSpecificationExecutor = new ManufacturerDOSpecificationExecutor();
        carDOSpecificationExecutor = new CarDOSpecificationExecutor(manufacturerDOSpecificationExecutor);
        driverDOSpecificationExecutor = new DriverDOSpecificationExecutor(carDOSpecificationExecutor);
    }


    @Test
    public void shouldReturnEmptyPredicatesSet()
    {
        Assert.assertThat(driverDOSpecificationExecutor.createPredicates((Path<DriverDO>) null, null, null), Matchers.nullValue());
    }


    @Test
    public void shouldReturnEmptyPredicatesSetWhenRoot()
    {
        Assert.assertThat(driverDOSpecificationExecutor.createPredicates((Root<DriverDO>) null, null, null), Matchers.is(Collections.emptySet()));
    }
}
