package com.wj.core.service.qst.dto;

import lombok.Data;

@Data
public class TenantstructuresIssuseDTO {

    private Integer StructureID;
    private String ParentDirectory;
    private String Directory;
    private String StructureName;
    private Integer Attribute;

}
