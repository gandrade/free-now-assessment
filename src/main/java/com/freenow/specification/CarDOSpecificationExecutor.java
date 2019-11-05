package com.freenow.specification;

import com.freenow.domainobject.CarDO;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.freenow.domainobject.CarDO_.*;

@Component
public class CarDOSpecificationExecutor implements DefaultSpecificationExecutor<CarDO>
{

    private final ManufacturerDOSpecificationExecutor manufacturerDOSpecification;


    public CarDOSpecificationExecutor(ManufacturerDOSpecificationExecutor manufacturerDOSpecification)
    {
        this.manufacturerDOSpecification = manufacturerDOSpecification;
    }


    @Override
    public Set<Predicate> createPredicates(Path<CarDO> path, CriteriaBuilder builder, CarDO doObject)
    {
        if (doObject == null)
        {
            return Collections.emptySet();
        }
        final Set<Predicate> predicates = new HashSet<>();
        predicates.add(builder.equal(path.get(SEAT_COUNT), doObject.getSeatCount()));
        predicates.add(builder.equal(path.get(LICENSE_PLATE), doObject.getLicensePlate() == null ? null : doObject.getLicensePlate().toUpperCase()));
        predicates.add(builder.equal(path.get(CONVERTIBLE), doObject.getConvertible()));
        predicates.add(builder.equal(path.get(RATING), doObject.getRating()));
        predicates.add(builder.equal(path.get(ENGINE_TYPE), doObject.getEngineType()));
        Set<Predicate> manufacturerPredicates = manufacturerDOSpecification.createPredicates(path.get(MANUFACTURER_DO), builder, doObject.getManufacturerDO());
        predicates.addAll(manufacturerPredicates);
        return predicates;
    }


    @Override
    public Set<Predicate> createPredicates(Root<CarDO> path, CriteriaBuilder builder, CarDO dtoObject)
    {
        return null;
    }
}
