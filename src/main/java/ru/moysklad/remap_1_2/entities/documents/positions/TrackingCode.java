package ru.moysklad.remap_1_2.entities.documents.positions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TrackingCode {
    String cis;
    TrackingCodeType type;
    List<TrackingCode> trackingCodes;

    public enum TrackingCodeType {transportpack, trackingcode}
}
