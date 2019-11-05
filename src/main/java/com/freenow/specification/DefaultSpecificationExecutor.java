package com.freenow.specification;

import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Default base implementation for creating predicates following Specification implementation.
 * @param <T> Entity object.
 */
interface DefaultSpecificationExecutor<T>
{

    /**
     * Creates a Specification object given an object.
     *
     * @param doObject Data Access Object object.
     * @return Specification containing all predicates for querying objects.
     */
    default Specification<T> makeSpecification(T doObject)
    {
        return (Specification<T>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>(this.createPredicates(root, builder, doObject));
            Predicate[] filteredPredicates = predicates.stream()
                .filter(predicate -> predicate instanceof ComparisonPredicate)
                .filter(predicate -> {
                    Expression rightHandOperand = ((ComparisonPredicate) predicate).getRightHandOperand();
                    Object literal = ((LiteralExpression) rightHandOperand).getLiteral();
                    return literal != null;
                })
                .toArray(Predicate[]::new);
            return builder.and(filteredPredicates);
        };
    }

    Set<Predicate> createPredicates(Root<T> path, CriteriaBuilder builder, T doObject);

    Set<Predicate> createPredicates(Path<T> path, CriteriaBuilder builder, T doObject);
}
