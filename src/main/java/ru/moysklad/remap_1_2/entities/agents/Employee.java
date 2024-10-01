package ru.moysklad.remap_1_2.entities.agents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class Employee extends Agent implements IEntityWithAttributes<Attribute> {
    private Employee owner;
    private Boolean shared;
    private String lastName;
    private String externalCode;
    private String shortFio;
    private LocalDateTime created;
    private String fullName;
    private Boolean archived;
    private String uid;
    private List<Cashier> cashiers;
    private LocalDateTime updated;
    private String email;
    private Group group;
    private String description;
    private String phone;
    private String firstName;
    private String middleName;
    private List<Attribute> attributes;
    private Image image;
    private String inn;
    private String position;

    public Employee(String id) {
        super(id);
    }
}
