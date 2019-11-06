package com.freenow.specification;

import com.freenow.domainobject.ManufacturerDO;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Collections;

public class ManufacturerSpecificationExecutorTest
{

    private ManufacturerDOSpecificationExecutor manufacturerDOSpecificationExecutor;


    @Before
    public void setup()
    {
        manufacturerDOSpecificationExecutor = new ManufacturerDOSpecificationExecutor();
    }


    @Test
    public void shouldReturnEmptyPredicatesSet()
    {
        Assert.assertThat(manufacturerDOSpecificationExecutor.createPredicates((Root<ManufacturerDO>) null, null, null), Matchers.nullValue());
    }


    @Test
    public void shouldReturnEmptyPredicatesSetWhenRoot()
    {
        Assert.assertThat(manufacturerDOSpecificationExecutor.createPredicates((Path<ManufacturerDO>) null, null, null), Matchers.is(Collections.emptySet()));
    }

}
