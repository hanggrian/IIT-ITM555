package com.example.playlist.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

public class Music implements Serializable {
    @SerializedName("SOLD")
    private Sold sold;

    @SerializedName("TITLE")
    private String title;

    @SerializedName("ARTIST")
    private String artist;

    @SerializedName("COUNTRY")
    private String country;

    @SerializedName("COMPANY")
    private String company;

    @SerializedName("PRICE")
    private Double price;

    @SerializedName("YEAR")
    private String year;

    public Sold getSold() {
        return sold;
    }

    public void setSold(Sold sold) {
        this.sold = sold;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isSold() {
        return sold == Sold.YES;
    }

    public String getDisplayCountry() {
        return new Locale("", country).getDisplayCountry();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Music other = (Music) obj;
        return sold.equals(other.sold)
            && title.equals(other.title)
            && artist.equals(other.artist)
            && country.equals(other.country)
            && company.equals(other.company)
            && price.equals(other.price)
            && year.equals(other.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sold, title, artist, country, company, price, year);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s (%s)", title, year);
    }

    public enum Sold {
        @SerializedName("yes") YES,
        @SerializedName("no") NO,
    }
}
