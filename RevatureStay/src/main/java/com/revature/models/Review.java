package com.revature.models;

import jakarta.persistence.*;

@Entity
@Table(name="reviews")
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int reviewId;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String comment;

  @Column(nullable = false)
  private int rate;

  //Relationships
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "hotel_id")
  private Hotel hotel;

  public Review() {};

  public Review( int reviewId, String title, String comment, int rate, User user, Hotel hotel) {
    this.reviewId = reviewId;
    this.title = title;
    this.comment = comment;
    this.rate = rate;
    this.user = user;
    this.hotel = hotel;
  }

  public Review(String title, String comment, int rate, User user, Hotel hotel) {
    this.title = title;
    this.comment = comment;
    this.rate = rate;
    this.user = user;
    this.hotel = hotel;
  }

  public int getReview_id() {
    return reviewId;
  }

  public void setReview_id(int reviewId) {
    this.reviewId = reviewId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public int getRate() {
    return rate;
  }

  public void setRate(int rate) {
    this.rate = rate;
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
}
