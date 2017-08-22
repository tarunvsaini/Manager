package com.tarun.saini.manager;

/**
 * Created by Tarun on 19-08-2017.
 */

public class Customer {
    private String name,
            address,
            uid,
            phone,
            gst,
            pan,
            email,
            notes,
            downloadUrl,
            date;
    boolean important;

    public Customer() {
    }

    public Customer(String name, String address,String downloadUrl, String uid, String phone, String gst, String pan, String email, String notes, String date,boolean important) {
        this.name = name;
        this.address = address;
        this.uid = uid;
        this.phone = phone;
        this.downloadUrl=downloadUrl;
        this.gst = gst;
        this.pan = pan;
        this.email = email;
        this.notes = notes;
        this.date = date;
        this.important=important;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }
}
