package com.wj.core.entity.base.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProvinceDTO {

    private Integer id;
    private String areaName;
    private String areaCode;
    private List<CityDTO> children;

}
