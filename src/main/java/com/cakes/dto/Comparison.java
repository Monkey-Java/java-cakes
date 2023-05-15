package com.cakes.dto;

import lombok.Data;

@Data
public class Comparison {

    private static final long serialVersionUID = 1L;

    private String field;

    private Object before;

    private Object after;

    private Object isUpdate;


}
