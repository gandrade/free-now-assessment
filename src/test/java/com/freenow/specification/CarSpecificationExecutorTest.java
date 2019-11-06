package com.freenow.specification;

import com.freenow.domainobject.CarDO;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Collections;

public class CarSpecificationExecutorTest
{
    private CarDOSpecificationExecutor carDOSpecificationExecutor;
    private ManufacturerDOSpecificationExecutor manufacturerDOSpecificationExecutor;


    @Before
    public void setup()
    {
        manufacturerDOSpecificationExecutor = new ManufacturerDOSpecificationExecutor();
        carDOSpecificationExecutor = new CarDOSpecificationExecutor(manufacturerDOSpecificationExecutor);
    }


    @Test
    public void shouldReturnEmptyPredicatesSet()
    {
        Assert.assertThat(carDOSpecificationExecutor.createPredicates((Path<CarDO>) null, null, null), Matchers.is(Collections.emptySet()));
    }


    @Test
    public void shouldReturnEmptyPredicatesSetWhenRoot()
    {
        Assert.assertThat(carDOSpecificationExecutor.createPredicates((Root<CarDO>) null, null, null), Matchers.nullValue());
    }
}
