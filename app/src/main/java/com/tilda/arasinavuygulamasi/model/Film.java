package com.tilda.arasinavuygulamasi.model;

import java.io.Serializable;

public class Film implements Serializable {

    private int id;//Sadece favoriler için gerekli

    private String kullanici_adi;//Sadece paylaşım için gerekli

    private String Type;

    private String Year;

    private String imdbID;

    private String Poster;

    private String Title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String Year) {
        this.Year = Year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String Poster) {
        this.Poster = Poster;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public Film() {
    }

    public Film(int id, String kullanici_adi, String imdbID, String title, String type, String year, String poster) {
        this.id = id;
        this.kullanici_adi = kullanici_adi;
        Type = type;
        Year = year;
        this.imdbID = imdbID;
        Poster = poster;
        Title = title;
    }

    @Override
    public String toString() {
        return "ClassPojo [Type = " + Type + ", Year = " + Year + ", imdbID = " + imdbID + ", Poster = " + Poster + ", Title = " + Title + "]";
    }
}
