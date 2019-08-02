package com.lognex.api.responses.metadata;

import com.lognex.api.entities.State;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetadataAttributeSharedStatesResponse extends MetadataAttributeSharedResponse {
    private List<State> states;
}
