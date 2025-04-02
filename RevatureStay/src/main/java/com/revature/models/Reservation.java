package com.revature.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Entity
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int reservationId;

  @Column
  private LocalDate checkInDate;

  @Column
  private LocalDate checkOutDate;

  @Column
  private int numGuests;

  @Enumerated(value = EnumType.STRING)
  private ReservationStatus status;

  //Relationships
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "hotel_id")
  private Hotel hotel;


  @ManyToMany
  @JoinTable(
          name = "booked_rooms",
          joinColumns = @JoinColumn(name = "reservation_id"),
          inverseJoinColumns = @JoinColumn(name = "room_id")
  )
  private List<Room> rooms;

  public Reservation() {}

  public Reservation(int reservationId, LocalDate checkInDate, LocalDate checkOutDate, int numGuests,
                     ReservationStatus status, User user, Hotel hotel, List<Room> rooms) {
    this.reservationId = reservationId;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.numGuests = numGuests;
    this.status = status;
    this.user = user;
    this.hotel = hotel;
    this.rooms = rooms;
  }

  public Reservation(LocalDate checkInDate, LocalDate checkOutDate, int numGuests, ReservationStatus status,
                     User user, Hotel hotel, List<Room> rooms) {
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.numGuests = numGuests;
    this.status = status;
    this.user = user;
    this.hotel = hotel;
    this.rooms = rooms;
  }

  public int getReservationId() {
    return reservationId;
  }

  public void setReservationId(int reservationId) {
    this.reservationId = reservationId;
  }

  public LocalDate getCheckInDate() {
    return checkInDate;
  }

  public void setCheckInDate(LocalDate checkInDate) {
    this.checkInDate = checkInDate;
  }

  public LocalDate getCheckOutDate() {
    return checkOutDate;
  }

  public void setCheckOutDate(LocalDate checkOutDate) {
    this.checkOutDate = checkOutDate;
  }

  public int getNumGuests() {
    return numGuests;
  }

  public void setNumGuests(int numGuests) {
    this.numGuests = numGuests;
  }

  public ReservationStatus getStatus() {
    return status;
  }

  public void setStatus(ReservationStatus status) {
    this.status = status;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }

  public List<Room> getRooms() {
    return rooms;
  }

  public void setRooms(List<Room> rooms) {
    this.rooms = rooms;
  }
}
