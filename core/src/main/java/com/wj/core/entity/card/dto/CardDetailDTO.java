package com.wj.core.entity.card.dto;

import lombok.Data;

import java.util.List;

@Data
public class CardDetailDTO {

    private Integer id;
    private String content;
    private List<CardServicesDTO> services;

}
