package org.example.bilabonnement.model.user;

public class BusinessUser extends User {

    public BusinessUser() {}

    public BusinessUser(String name, String username, String password, String email, String phoneNo) {
        super(name, username, password, email, phoneNo);
    }

    @Override
    public String getRole() {
        return "Forretningsudvikler";
    }
}
