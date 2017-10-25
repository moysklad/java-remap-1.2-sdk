package com.lognex.api.model.base.field;


public enum StateType {

    Regular(false, "Обычный"),
    Successful(true, "Финальный положительный"),
    Unsuccessful(true, "Финальный отрицательный");

    private final boolean final_;
    private final String label;

    StateType(boolean final_, String label) {
        this.final_ = final_;
        this.label = label;
    }

    public boolean isFinal() {
        return final_;
    }

    public String getLabel() {
        return label;
    }
}
