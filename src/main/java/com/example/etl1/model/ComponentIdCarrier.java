package com.example.etl1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComponentIdCarrier {
    private Integer caseId;
    private Integer cpuId;
    private Integer cpuCoolerId;
    private Integer graphicsCardId;
    private Integer internalStorageId;
    private Integer memoryId;
    private Integer motherboardId;
    private Integer powerSupplyId;
}
