package com.platform.client.specification;

import com.platform.client.domain.Client;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientSpecification implements Specification<Client>{

    private List<SearchCriteria> list;

    public ClientSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    public void parseQuery(String query) {
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(query + ",");
        while (matcher.find()) {
            list.add(new SearchCriteria(matcher.group(1), SearchOperation.fromString(matcher.group(2)), matcher.group(3)));
        }
    }

    @Override
    public Specification<Client> and(Specification<Client> other) {
        return null;
    }

    @Override
    public Specification<Client> or(Specification<Client> other) {
        return null;
    }

    @Override
    public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        //create a new predicate list
        List<Predicate> predicates = new ArrayList<>();

        //add add criteria to predicates
        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(builder.equal(
                        root.get(criteria.getKey()), criteria.getValue()));
            }

//            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
//                predicates.add(builder.greaterThan(
//                        root.get(criteria.getKey()), criteria.getValue().toString()));
//            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
//                predicates.add(builder.lessThan(
//                        root.get(criteria.getKey()), criteria.getValue().toString()));
//            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
//                predicates.add(builder.greaterThanOrEqualTo(
//                        root.get(criteria.getKey()), criteria.getValue().toString()));
//            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
//                predicates.add(builder.lessThanOrEqualTo(
//                        root.get(criteria.getKey()), criteria.getValue().toString()));
//            } else
//            if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
//                predicates.add(builder.notEqual(
//                        root.get(criteria.getKey()), criteria.getValue()));
//            }
//            else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
//                predicates.add(builder.equal(
//                        root.get(criteria.getKey()), criteria.getValue()));
//            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
//                predicates.add(builder.like(
//                        builder.lower(root.get(criteria.getKey())),
//                        "%" + criteria.getValue().toString().toLowerCase() + "%"));
//            } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
//                predicates.add(builder.like(
//                        builder.lower(root.get(criteria.getKey())),
//                        criteria.getValue().toString().toLowerCase() + "%"));
//            } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
//                predicates.add(builder.like(
//                        builder.lower(root.get(criteria.getKey())),
//                        "%" + criteria.getValue().toString().toLowerCase()));
//            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
//                predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
//            } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
//                predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
//            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
