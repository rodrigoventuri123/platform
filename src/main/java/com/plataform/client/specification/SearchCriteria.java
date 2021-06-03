package com.plataform.client.specification;

import lombok.Data;

@Data
public class SearchCriteria {

    private String key;
    private Object value;
    private SearchOperation operation;

    public SearchCriteria() {
    }

    public SearchCriteria(String key, SearchOperation operation, Object value) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

}
