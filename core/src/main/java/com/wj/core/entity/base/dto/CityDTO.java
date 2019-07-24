package com.wj.core.entity.base.dto;

import lombok.Data;

import java.util.List;

@Data
public class CityDTO {

    private Integer id;
    private String areaName;
    private String areaCode;
    private List<AreaDTO> children;

}
