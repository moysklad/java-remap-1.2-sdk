package com.lognex.api.entities;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.List;

public class Contract extends MetaEntity {
    public String id;
    public String accountId;
    public Employee owner;
    public Boolean shared;
    public MetaEntity group;
    public Integer version;
    public LocalDateTime updated;
    public String name;
    public String description;
    public String code;
    public String externalCode;
    public Boolean archived;
    public LocalDateTime moment;
    public Integer sum;
    public Type contractType;
    public RewardType rewardType;
    public Double rewardPercent;
    public Organization ownAgent;
    public Agent agent;
    public State state;
    public MetaEntity organizationAccount;
    public MetaEntity agentAccount;
    public Rate rate;
    public List<Attribute> attributes;

    public enum Type {
        @SerializedName("Commission") commission,
        @SerializedName("Sales") sales
    }

    public enum RewardType {
        @SerializedName("PercentOfSales") percentOfSales,
        @SerializedName("None") none
    }
}
