package com.lognex.api.model.entity.discount;

import com.lognex.api.model.base.Entity;
import com.lognex.api.model.entity.good.Good;
import com.lognex.api.model.entity.good.ProductFolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Discount extends Entity {

    private String name;
    private boolean active;
    private boolean allProducts;
    private boolean allAgents;
    private List<String> agentTags;
    private List<ProductFolder> productfolders;
    private List<Good> assortment;
    private double discount;
}
