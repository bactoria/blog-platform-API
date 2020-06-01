package me.bacto.blog.account.domain;

public enum AccountRole {
    ADMIN("ADMIN"),
    USER("USER");

    private String value;

    AccountRole(String value) {
        this.value = value;
    }

    public String getRoleValue() {
        return this.value;
    }
}
