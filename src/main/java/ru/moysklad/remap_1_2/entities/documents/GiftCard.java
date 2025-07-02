package ru.moysklad.remap_1_2.entities.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GiftCard {
    private String name;
    private Long paymentSum;
}
