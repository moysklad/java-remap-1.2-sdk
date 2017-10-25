package com.lognex.api.model.document;

import com.lognex.api.model.base.EntityLegendable;
import com.lognex.api.model.entity.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProcessingPlan extends EntityLegendable {

    private String pathName;
    private Group parent;
    private double cost;
    private List<ProcessingPlanMaterial> materials = new ArrayList<>();
    private List<ProcessingPlanProduct> products = new ArrayList<>();
}
