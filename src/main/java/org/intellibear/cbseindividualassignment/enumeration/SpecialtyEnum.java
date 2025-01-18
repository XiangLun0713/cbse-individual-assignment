package org.intellibear.cbseindividualassignment.enumeration;

public enum SpecialtyEnum {
    CARDIOLOGY("Cardiology"),
    DERMATOLOGY("Dermatology"),
    ENDOCRINOLOGY("Endocrinology"),
    GASTROENTEROLOGY("Gastroenterology"),
    GYNECOLOGY("Gynecology"),
    HEMATOLOGY("Hematology"),
    NEPHROLOGY("Nephrology"),
    NEUROLOGY("Neurology"),
    ONCOLOGY("Oncology"),
    OPHTHALMOLOGY("Ophthalmology"),
    ORTHOPEDICS("Orthopedics"),
    OTOLARYNGOLOGY("Otolaryngology"),
    PEDIATRICS("Pediatrics"),
    PSYCHIATRY("Psychiatry"),
    PULMONOLOGY("Pulmonology"),
    RHEUMATOLOGY("Rheumatology"),
    UROLOGY("Urology"),
    OTHERS("Others");

    private final String value;

    SpecialtyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}