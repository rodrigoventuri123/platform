package com.plataform.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private Integer page = 0;
    private Integer size = 10;
    private String query;
}
