package com.revature.models;

import jakarta.persistence.*;

@Entity
@Table(name="hotels")
public class Hotel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int hotelId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String address;

  @Column(nullable = false, unique = true)
  private String cellphoneNumber;

  @Column(nullable = false)
  private String description;

  //Relation ManyToOne to User Id
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;


  public Hotel() {}

  public Hotel(int hotelId, String name, String address, String cellphoneNumber, String description, User user) {
    this.hotelId = hotelId;
    this.name = name;
    this.address = address;
    this.cellphoneNumber = cellphoneNumber;
    this.description = description;
    this.user = user;
  }

  public Hotel(String name, String address, String cellphoneNumber, String description, User user) {
    this.name = name;
    this.address = address;
    this.cellphoneNumber = cellphoneNumber;
    this.description = description;
    this.user = user;
  }

  public int getHotelId() {
    return hotelId;
  }

  public void setHotelId(int hotelId) {
    this.hotelId = hotelId;
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

  public String getCellphoneNumber() {
    return cellphoneNumber;
  }

  public void setCellphoneNumber(String cellphoneNumber) {
    this.cellphoneNumber = cellphoneNumber;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
