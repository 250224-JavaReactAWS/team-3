package com.revature.models;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int image_id;

  @Column(nullable = false)
  private String url;

  @Column(nullable = false)
  private String alt;

  //Relationship
  @ManyToOne
  @JoinColumn(name = "hotel_id")
  private Hotel hotel;

  public Image() {}

  public Image(int image_id, String url, String alt, Hotel hotel) {
    this.image_id = image_id;
    this.url = url;
    this.alt = alt;
    this.hotel = hotel;
  }

  public Image(String url, String alt, Hotel hotel) {
    this.url = url;
    this.alt = alt;
    this.hotel = hotel;
  }

  public int getImage_id() {
    return image_id;
  }

  public void setImage_id(int image_id) {
    this.image_id = image_id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getAlt() {
    return alt;
  }

  public void setAlt(String alt) {
    this.alt = alt;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }
}
