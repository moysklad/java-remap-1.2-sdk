package ru.moysklad.remap_1_2.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MassDeleteResponse {
    private String info;
    private List<ErrorResponse.Error> errors;
}
