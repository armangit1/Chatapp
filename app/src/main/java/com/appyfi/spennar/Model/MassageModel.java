package com.appyfi.spennar.Model;

public class MassageModel {

    private String massageId;
    private String senderId;
    private String massage;

    public MassageModel() {
    }

    public MassageModel(String massage, String senderId) {
        this.massage = massage;
        this.senderId = senderId;
    }

    public String getMassageId() {
        return massageId;
    }

    public void setMassageId(String massageId) {
        this.massageId = massageId;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
