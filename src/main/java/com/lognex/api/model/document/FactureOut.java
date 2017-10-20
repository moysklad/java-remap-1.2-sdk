package com.lognex.api.model.document;

import com.lognex.api.model.base.Operation;
import com.lognex.api.model.entity.Agent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FactureOut extends Facture {

    private String stateContractId;
    private List<Demand> demands = new ArrayList<>();
    private List<Operation> returns = new ArrayList<>();

    private Agent consignee;
    private String paymentNumber;
    private Date paymentDate;
}