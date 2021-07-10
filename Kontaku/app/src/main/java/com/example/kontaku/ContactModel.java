package com.example.kontaku;


public class ContactModel {

    String id;
    String nama;
    String nomor;

    public ContactModel(String id, String nama, String nomor) {
        this.id = id;
        this.nama = nama;
        this.nomor = nomor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }
}
