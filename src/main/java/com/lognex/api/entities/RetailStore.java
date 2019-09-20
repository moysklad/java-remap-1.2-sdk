package com.lognex.api.entities;

import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Employee;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.responses.ListEntity;
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
public class RetailStore extends MetaEntity implements Fetchable {
    private Boolean shared;
    private Boolean controlCashierChoice;
    private String externalCode;
    private String frNumber;
    private Boolean controlShippingStock;
    private Boolean ofdEnabled;
    private Boolean archived;
    private Boolean issueOrders;
    private List<LastOperationNamesItem> lastOperationNames;
    private RetailStoreState state;
    private Group group;
    private Employee owner;
    private Boolean allowCustomPrice;
    private PriceType priceType;
    private ListEntity<Cashier> cashiers;
    private Boolean active;
    private Store store;
    private Boolean discountEnable;
    private Environment environment;
    private Boolean sellReserves;
    private Boolean authTokenAttached;
    private Boolean egaisEnabled;
    private Organization organization;
    private Integer discountMaxPercent;
    private LocalDateTime updated;
    private String description;
    private String address;
    private Boolean onlyInStock;
    private Agent acquire;
    private State orderToState;
    private ListEntity<State> customerOrderStates;
    private TaxSystem defaultTaxSystem;
    private TaxSystem orderTaxSystem;
    private Boolean allowCreateProducts;
    private PriorityOfdSend priorityOfdSend;
    private String demandPrefix;
    private Boolean allowSellTobaccoWithoutMRC;
    private ListEntity<ProductFolder> productFolders;
    private List<String> createAgentsTags;
    private List<String> filterAgentsTags;
    private Boolean printAlways;
    private ReceiptTemplate receiptTemplate;
    private Boolean createPaymentInOnRetailShiftClosing;
    private Boolean createCashInOnRetailShiftClosing;
    private Boolean returnFromClosedShiftEnabled;
    private Boolean enableReturnsWithNoReason;
    private State createOrderWithState;
    private Boolean reservePrepaidGoods;
    private Double bankPercent;

    public RetailStore(String id) {
        super(id);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class LastOperationNamesItem {
        private String name;
        private Meta.Type entity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Environment {
        private String device;
        private String os;
        private Software software;
        private ChequePrinter chequePrinter;
        private PaymentTerminalState paymentTerminal;

        @Getter
        @Setter
        @NoArgsConstructor
        public static class Software {
            private String name;
            private String vendor;
            private String version;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        public static class ChequePrinter {
            private String vendor;
            private String name;
            private String serial;
            private String fiscalDataVersion;
            private Driver driver;
            private FiscalMemory fiscalMemory;
            private String firmwareVersion;

            @Getter
            @Setter
            @NoArgsConstructor
            public class Driver {
                private String name;
                private String version;
            }

            @Getter
            @Setter
            @NoArgsConstructor
            public class FiscalMemory {
                private String fiscalDataVersion;
            }
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RetailStoreState {
        private Sync sync;
        private LocalDateTime lastCheckMoment;
        private FiscalMemoryState fiscalMemory;
        private PaymentTerminalState paymentTerminal;

        @Getter
        @Setter
        @NoArgsConstructor
        public static class Sync {
            private String message;
            private String lastAttempMoment;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        public static class FiscalMemoryState {
            private Error error;
            private Integer notSendDocCount;
            private LocalDateTime notSendFirstDocMoment;

            @Getter
            @Setter
            @NoArgsConstructor
            public static class Error {
                private String code;
                private String message;
            }
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PaymentTerminalState {
        private String acquiringType;
    }

    public enum PriorityOfdSend {
        PHONE, EMAIL, NONE
    }
}