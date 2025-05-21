package com.mindtrack.enums;

public enum Status {
    ATIVO("Ativo"), Inativo("Inativo");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
