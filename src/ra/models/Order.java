package ra.models;

import java.util.Date;

import static ra.config.OderStatus.*;

public class Order {
    private int id ;
    private int idUser ;
    private double total ;
    private Date buyDate = new Date();
    private String receiver;
    private String numberPhone ;
    private String address ;
    private  byte status = 0 ;

    public Order() {
    }

    public Order(int id, int idUser, double total, Date buyDate, String receiver, String numberPhone, String address, byte status) {
        this.id = id;
        this.idUser = idUser;
        this.total = total;
        this.buyDate = buyDate;
        this.receiver = receiver;
        this.numberPhone = numberPhone;
        this.address = address;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
    public void display(){
        System.out.println("Id : " +id + " | Ten nguoi nhan : "+receiver+ " | So dien thoai : "+ numberPhone);
        System.out.println("địa chỉ" + this.address);
        System.out.println("Trang thai : " + getStatusByCode(status));
    }
}
