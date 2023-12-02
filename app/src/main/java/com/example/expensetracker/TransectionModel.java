package com.example.expensetracker;

public class TransectionModel {
    private String id,amount,desc,type,date;

    public TransectionModel() {

    }

    public TransectionModel(String id, String amount, String desc, String type,String date) {
        this.id = id;
        this.amount = amount;
        this.desc = desc;
        this.type = type;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
