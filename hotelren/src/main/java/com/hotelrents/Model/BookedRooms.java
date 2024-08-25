package com.hotelrents.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class BookedRooms {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int booking_id;
	
	@Column(name = "check_in")
	private LocalDate checkinDate;
	
	@Column(name = "check_out")
	private LocalDate checkoutDate;
	
	@Column(name = "Guest_Full_Name")
	private String User_FullName;
	
	@Column(name = "Guest_Email")
	private String User_Email;
	
	@Column(name = "Adults")
	private int NumberOfAdults;
	
	@Column(name = "Child")
	private int Children;
	
	@Column(name = "Total_Guest")
	private int TotalNumberOfGuest;
	
	@Column(name = "Confirm_Code")
	private String bookingConfirmationCode;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Rooms room; 
	
	
	
	public void CalculateNumOfMember() {
		TotalNumberOfGuest=NumberOfAdults+Children;
	}

	
	public void SetNumOfAdults(int numberofadults) {
		this.NumberOfAdults=numberofadults;
		CalculateNumOfMember();
	}
	
	
	public void SetNumOfChildrens(int numberofchildren) {
		this.Children=numberofchildren;
		CalculateNumOfMember();	
	}
	
	
	public void setBookingConfirmationCode(String bookingConfirmationCode) {
		this.bookingConfirmationCode = bookingConfirmationCode;
	}
	
	
	
	
	
	public Rooms getRoom() {
		return room;
	}


	public void setRoom(Rooms room) {
		this.room = room;
	}


	public String getUser_FullName() {
		return User_FullName;
	}


	public void setUser_FullName(String user_FullName) {
		User_FullName = user_FullName;
	}


	public String getUser_Email() {
		return User_Email;
	}


	public void setUser_Email(String user_Email) {
		User_Email = user_Email;
	}


	public int getNumberOfAdults() {
		return NumberOfAdults;
	}


	public void setNumberOfAdults(int numberOfAdults) {
		NumberOfAdults = numberOfAdults;
	}


	public int getChildren() {
		return Children;
	}


	public void setChildren(int children) {
		Children = children;
	}


	public int getTotalNumberOfGuest() {
		return TotalNumberOfGuest;
	}


	public void setTotalNumberOfGuest(int totalNumberOfGuest) {
		TotalNumberOfGuest = totalNumberOfGuest;
	}





	public int getRoombooking_id() {
		return booking_id;
	}


	public void setRoombooking_id(int booking_id) {
		this.booking_id = booking_id;
	}


	public LocalDate getCheckinDate() {
		return checkinDate;
	}


	public void setCheckinDate(LocalDate checkinDate) {
		this.checkinDate = checkinDate;
	}


	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}


	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}


	public String getBookingConfirmationCode() {
		return bookingConfirmationCode;
	}
}
