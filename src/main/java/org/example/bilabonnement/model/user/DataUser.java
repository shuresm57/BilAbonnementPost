package org.example.bilabonnement.model.user;

public class DataUser extends User {

    public DataUser() {}

    public DataUser(String name, String username, String password, String email, String phoneNo) {
        super(name, username, password, email, phoneNo);
    }

    @Override
    public String getRole() {
        return "DATA";
    }

}
