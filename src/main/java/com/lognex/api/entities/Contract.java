package com.lognex.api.entities;

import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Contract extends MetaEntity {
    private String id;
    private String accountId;
    private Employee owner;
    private Boolean shared;
    private MetaEntity group;
    private Integer version;
    private LocalDateTime updated;
    private String name;
    private String description;
    private String code;
    private String externalCode;
    private Boolean archived;
    private LocalDateTime moment;
    private Integer sum;
    private Type contractType;
    private RewardType rewardType;
    private Double rewardPercent;
    private Organization ownAgent;
    private Agent agent;
    private State state;
    private MetaEntity organizationAccount;
    private MetaEntity agentAccount;
    private Rate rate;
    private List<Attribute> attributes;

    public enum Type {
        @SerializedName("Commission") commission,
        @SerializedName("Sales") sales
    }

    public enum RewardType {
        @SerializedName("PercentOfSales") percentOfSales,
        @SerializedName("None") none
    }
}
