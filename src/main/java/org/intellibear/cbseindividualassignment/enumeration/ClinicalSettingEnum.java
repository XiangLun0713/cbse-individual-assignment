package org.intellibear.cbseindividualassignment.enumeration;

public enum ClinicalSettingEnum {
    OUTPATIENT_CLINIC("Outpatient Clinic"),
    OUTPATIENT_EMERGENCY("Outpatient Emergency"),
    INPATIENT_PLANNED("Inpatient Planned"),
    INPATIENT_EMERGENCY("Inpatient Emergency"),
    PROCEDURE_OR_TESTS("Procedure/tests"),
    OTHERS("Others");

    private final String value;

    ClinicalSettingEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}