package com.revature.models;

import jakarta.persistence.*;

@Entity
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int roomId;

  @Enumerated(value = EnumType.STRING)
  private RoomType type;

  @Column(nullable = false)
  private int beds;

  @Column(nullable = false)
  private int baths;

  @Column(nullable = false)
  private double price;

  @Column(nullable = false)
  private boolean isAvailable;

  //Relationship with Hotel
  @ManyToOne
  @JoinColumn(name = "hotel_id")
  private Hotel hotel;

  public Room() {}

  public Room(int roomId, RoomType type, int beeds, int baths, double price, boolean isAvailable, Hotel hotel) {
    this.roomId = roomId;
    this.type = type;
    this.beds = beeds;
    this.baths = baths;
    this.price = price;
    this.isAvailable = isAvailable;
    this.hotel = hotel;
  }

  public Room(RoomType type, int beds, int baths, double price, boolean isAvailable, Hotel hotel) {
    this.type = type;
    this.beds = beds;
    this.baths = baths;
    this.price = price;
    this.isAvailable = isAvailable;
    this.hotel = hotel;
  }

  public int getRoomId() {
    return roomId;
  }

  public void setRoomId(int roomId) {
    this.roomId = roomId;
  }

  public RoomType getType() {
    return type;
  }

  public void setType(RoomType type) {
    this.type = type;
  }

  public int getBeds() {
    return beds;
  }

  public void setBeds(int beds) {
    this.beds = beds;
  }

  public int getBaths() {
    return baths;
  }

  public void setBaths(int baths) {
    this.baths = baths;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }
}
