package org.intellibear.cbseindividualassignment.enumeration;

public enum ConditionEnum {
    ANORECTAL_MALFORMATION("Anorectal Malformation"),
    DOWNS_SYNDROME("Down's Syndrome"),
    HIRSCHSPRUNG_DISEASE("Hirschsprung Disease"),
    NEURAL_TUBE_DEFECTS("Neural Tube Defects"),
    SPINA_BIFIDA("Spina Bifida"),
    TETHRED_CORD("Tethred Cord"),
    VACTERL("VACTERL"),
    THALASSAEMIA("Thalassaemia"),
    OTHERS("Others");

    private final String value;

    ConditionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
