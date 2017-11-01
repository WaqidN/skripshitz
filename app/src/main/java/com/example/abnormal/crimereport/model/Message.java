package com.example.abnormal.crimereport.model;



public class Message {

    public String getIdnya() {
        return idnya;
    }

    public void setIdnya(String idnya) {
        this.idnya = idnya;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesk() {
        return desk;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public String getGetTimestamp() {
        return getTimestamp;
    }

    public void setGetTimestamp(String getTimestamp) {
        this.getTimestamp = getTimestamp;
    }

    public String idnya,nama, title,desk,getTimestamp,from,email,website,nomor_telpon,status;
}
