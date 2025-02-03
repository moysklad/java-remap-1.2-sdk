package ru.moysklad.remap_1_2.entities;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
    @JsonSerialize(using = CashiersSerializer.class)
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
    private Address addressFull;
    private Boolean onlyInStock;
    private Agent acquire;
    private State orderToState;
    private List<State> customerOrderStates;
    private TaxSystem defaultTaxSystem;
    private TaxSystem orderTaxSystem;
    private Boolean allowCreateProducts;
    private PriorityOfdSend priorityOfdSend;
    private String demandPrefix;
    private Boolean allowSellTobaccoWithoutMRC;
    private TobaccoMrcControlType tobaccoMrcControlType;
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
    private FiscalType fiscalType;
    private Boolean qrPayEnabled;
    private Agent qrAcquire;
    private MinionToMasterType minionToMasterType;
    private ListEntity<RetailStore> masterRetailStores;
    private Double qrBankPercent;
    private MarkingSellingMode markingSellingMode;
    private Boolean sendMarksForCheck;
    private Boolean requiredFio;
    private Boolean requiredPhone;
    private Boolean requiredEmail;
    private Boolean requiredBirthdate;
    private Boolean requiredSex;
    private Boolean requiredDiscountCardNumber;
    private Boolean allowDeleteReceiptPositions;
    private Boolean syncAgents;
    private Boolean showBeerOnTap;
    private MarkingSellingMode marksCheckMode;
    private Boolean sendMarksToChestnyZnakOnCloud;

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

    public void setCashiers(Collection<Employee> employees) {
        this.cashiers = new ListEntity<>();
        this.cashiers.setRows(employees.stream()
                .map(e -> {
                    Cashier cashier = new Cashier();
                    cashier.setEmployee(e);
                    return cashier;
                })
                .collect(toList()));
    }

    @JsonSetter("cashiers")
    public void setCashiers(ListEntity<Cashier> cashiers) {
        this.cashiers = cashiers;
    }

    public static class CashiersSerializer extends JsonSerializer<ListEntity<Cashier>> {
        @Override
        public void serialize(ListEntity<Cashier> src, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (src.getRows() == null) {
                gen.writeNull();
                return;
            }

            List<Employee> employees = src.getRows().stream()
                    .map(Cashier::getEmployee)
                    .collect(Collectors.toList());

            gen.writeObject(employees);
        }
    }

    public enum FiscalType {
        STANDARD, MASTER, CLOUD
    }

    public enum MinionToMasterType {
        ANY, SAME_GROUP, CHOSEN
    }

    public enum TobaccoMrcControlType {
        USER_PRICE, MRC_PRICE, SAME_PRICE;
    }

    public enum MarkingSellingMode {
        CORRECT_MARKS_ONLY, WITHOUT_ERRORS, ALL
    }
}