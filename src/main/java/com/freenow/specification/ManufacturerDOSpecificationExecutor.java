package com.freenow.specification;

import com.freenow.domainobject.ManufacturerDO;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.freenow.domainobject.ManufacturerDO_.NAME;

@Component
public class ManufacturerDOSpecificationExecutor implements DefaultSpecificationExecutor<ManufacturerDO>
{

    @Override
    public Set<Predicate> createPredicates(Path<ManufacturerDO> path, CriteriaBuilder builder, ManufacturerDO manufacturerDO)
    {
        if (manufacturerDO == null)
        {
            return Collections.emptySet();
        }
        Set<Predicate> predicates = new HashSet<>();
        predicates.add(builder.equal(path.get(NAME), manufacturerDO.getName() == null ? null : manufacturerDO.getName().toUpperCase()));
        return predicates;
    }


    @Override
    public Set<Predicate> createPredicates(Root<ManufacturerDO> path, CriteriaBuilder builder, ManufacturerDO manufacturerDO)
    {
        return null;
    }
}
