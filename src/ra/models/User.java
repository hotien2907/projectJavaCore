package ra.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static ra.constant.Contant.Role.*;
import static ra.constant.Contant.Status.*;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String fullName;
    private String password;
    private String email;
    private String phone;
    private boolean status;
    private String address;
    private int role;
    private List<Cart> cart = new ArrayList<>();

    public User() {
        status = false;
    }

    public User(int id, String username, String fullName, String password, String email, String phone, boolean status, String address, int role, List<Cart> cart) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.address = address;
        this.role = role;
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public void disphay() {
        System.out.printf("Username: %s - Full Name: %s \n", this.username, this.fullName);
        System.out.printf("%sPassword: %s-Email: %sPhone: ===Status:%s=== %b-Address: %s-Role:  \n",
                this.password, this.email, this.phone, (this.status ?("'ACTIVE'")  : ("'INACTIVE'")), this.address, this.role == ADMIN ? "'ADMIN'" : "'USER'");
    }
}