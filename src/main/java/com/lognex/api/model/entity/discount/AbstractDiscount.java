package com.lognex.api.model.entity.discount;

import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.model.entity.good.AbstractGood;
import com.lognex.api.model.entity.good.ProductFolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AbstractDiscount extends AbstractEntity {

    private String name;
    private boolean active;
    private boolean allProducts;
    private boolean allAgents;
    private List<String> agentTags;
    private List<ProductFolder> productfolders;
    private List<AbstractGood> assortment;
    private double discount;
}
