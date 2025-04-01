package com.revature.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int userId;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Enumerated(value = EnumType.STRING)
  private UserRole userRole;

  //Relationships
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reservation> reservations;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Hotel> ownerHotels;

  @ManyToMany
  @JoinTable(
          name = "favorites",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "hotel_id")
  )
  private List<Hotel> hotels;

  public User() {}

  public User(int userId, String firstName, String lastName,
              String email, String password, UserRole userRole, List<Reservation> reservations,
              List<Review> reviews, List<Hotel> hotels, List<Hotel> ownerHotels) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.userRole = userRole;
    this.reservations = reservations;
    this.reviews = reviews;
    this.hotels = hotels;
    this.ownerHotels = ownerHotels;
  }

  public User(String firstName, String lastName, String email, String password, UserRole userRole, List<Reservation> reservations,
              List<Review> reviews, List<Hotel> hotels, List<Hotel> ownerHotels) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.userRole = userRole;
    this.reservations = reservations;
    this.reviews = reviews;
    this.hotels = hotels;
    this.ownerHotels = ownerHotels;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserRole getRole() {
    return userRole;
  }

  public void setRole(UserRole userRole) {
    this.userRole = userRole;
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

  public List<Hotel> getOwnerHotels() {
    return ownerHotels;
  }

  public void setOwnerHotels(List<Hotel> ownerHotels) {
    this.ownerHotels = ownerHotels;
  }

  public List<Hotel> getHotels() {
    return hotels;
  }

  public void setHotels(List<Hotel> hotels) {
    this.hotels = hotels;
  }
}
