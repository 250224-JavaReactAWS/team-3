package com.revature.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

  //Relatioships
  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;

  @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Room> rooms;

  @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Image> images;

  @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reservation> reservations;

  @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews;

  @ManyToMany(mappedBy = "hotels", cascade = CascadeType.ALL)
  private List<User> users;

  public Hotel() {
  }

  public Hotel(int hotelId, String name, String address, String cellphoneNumber, String description, User owner, List<Room> rooms, List<Image> images, List<Reservation> reservations, List<Review> reviews,
               List<User> users) {
    this.hotelId = hotelId;
    this.name = name;
    this.address = address;
    this.cellphoneNumber = cellphoneNumber;
    this.description = description;
    this.owner = owner;
    this.rooms = rooms;
    this.images = images;
    this.reservations = reservations;
    this.reviews = reviews;
    this.users = users;
  }

  public Hotel(String name, String address, String cellphoneNumber, String description, User owner, List<Room> rooms, List<Image> images, List<Reservation> reservations, List<Review> reviews,
               List<User> users) {
    this.name = name;
    this.address = address;
    this.cellphoneNumber = cellphoneNumber;
    this.description = description;
    this.owner = owner;
    this.rooms = rooms;
    this.images = images;
    this.reservations = reservations;
    this.reviews = reviews;
    this.users = users;
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

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public List<Room> getRooms() {
    return rooms;
  }

  public void setRooms(List<Room> rooms) {
    this.rooms = rooms;
  }

  public List<Image> getImages() {
    return images;
  }

  public void setImages(List<Image> images) {
    this.images = images;
  }

  public List<Reservation> getReservations() {
    return reservations;
  }

  public void setReservations(List<Reservation> reservations) {
    this.reservations = reservations;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Hotel hotel = (Hotel) o;
    return hotelId == hotel.hotelId;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(hotelId);
  }
}
