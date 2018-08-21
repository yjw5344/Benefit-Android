package com.project.graduate.neartheplace.DiscountInfo;

public class DiscountInfo {

    String company;
    String telecomKT;
    String discountKT;
    String telecomSKT;
    String discountSKT;
    String telecomLGU;
    String discountLGU;
    String army;
    String discountArmy;

    public DiscountInfo(String company, String discountKT, String discountSKT, String discountLGU, String discountArmy) {
        this.company = company;
        this.discountKT = discountKT;
        this.discountSKT = discountSKT;
        this.discountLGU = discountLGU;
        this.discountArmy = discountArmy;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTelecomKT() {
        return telecomKT;
    }

    public void setTelecomKT(String telecomKT) {
        this.telecomKT = telecomKT;
    }

    public String getDiscountKT() {
        return discountKT;
    }

    public void setDiscountKT(String discountKT) {
        this.discountKT = discountKT;
    }

    public String getTelecomSKT() {
        return telecomSKT;
    }

    public void setTelecomSKT(String telecomSKT) {
        this.telecomSKT = telecomSKT;
    }

    public String getDiscountSKT() {
        return discountSKT;
    }

    public void setDiscountSKT(String discountSKT) {
        this.discountSKT = discountSKT;
    }

    public String getTelecomLGU() {
        return telecomLGU;
    }

    public void setTelecomLGU(String telecomLGU) {
        this.telecomLGU = telecomLGU;
    }

    public String getDiscountLGU() {
        return discountLGU;
    }

    public void setDiscountLGU(String discountLGU) {
        this.discountLGU = discountLGU;
    }

    public String getArmy() {
        return army;
    }

    public void setArmy(String army) {
        this.army = army;
    }

    public String getDiscountArmy() {
        return discountArmy;
    }

    public void setDiscountArmy(String discountArmy) {
        this.discountArmy = discountArmy;
    }
}
