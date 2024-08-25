package com.hotelrents.Response;

import java.time.LocalDate;

import com.hotelrents.Model.Rooms;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



public class BookingResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int booking_id;
	
	
	private LocalDate CheckinDate;
	
	
	private LocalDate CheckoutDate;
	
	
	private String User_FullName;
	
	
	private String User_Email;
	
	
	private int NumberOfAdults;
	
	
	private int Children;
	
	
	private int TotalNumberOfGuest;
	
	
	private String BookingConfirmationCode;
	 
	private RoomResponse room;

	

	public int getRoombooking_id() {
		return booking_id;
	}

	public void setRoombooking_id(int roombooking_id) {
		this.booking_id = roombooking_id;
	}

	public LocalDate getCheckinDate() {
		return CheckinDate;
	}

	public void setCheckinDate(LocalDate checkinDate) {
		this.CheckinDate = checkinDate;
	}

	public LocalDate getCheckoutDate() {
		return CheckoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.CheckoutDate = checkoutDate;
	}

	public String getUser_FullName() {
		return User_FullName;
	}

	public void setUser_FullName(String user_FullName) {
		this.User_FullName = user_FullName;
	}

	public String getUser_Email() {
		return User_Email;
	}

	public void setUser_Email(String user_Email) {
		this.User_Email = user_Email;
	}

	public int getNumberOfAdults() {
		return NumberOfAdults;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		this.NumberOfAdults = numberOfAdults;
	}

	public int getChildren() {
		return Children;
	}

	public void setChildren(int children) {
		this.Children = children;
	}

	public int getTotalNumberOfGuest() {
		return TotalNumberOfGuest;
	}

	public void setTotalNumberOfGuest(int totalNumberOfGuest) {
		this.TotalNumberOfGuest = totalNumberOfGuest;
	}

	public String getBookingConfirmationCode() {
		return BookingConfirmationCode;
	}

	public void setBookingConfirmationCode(String bookingConfirmationCode) {
		this.BookingConfirmationCode = bookingConfirmationCode;
	}

	public RoomResponse getRoom() {
		return room;
	}

	public void setRoom(RoomResponse room) {
		this.room = room;
	}

	public BookingResponse(int roombooking_id, LocalDate checkinDate, LocalDate checkoutDate,
			String bookingConfirmationCode) {
		super();
		this.booking_id = roombooking_id;
		this.CheckinDate = checkinDate;
		this.CheckoutDate = checkoutDate;
		this.BookingConfirmationCode = bookingConfirmationCode;
	}
	
	public BookingResponse(int roombooking_id, LocalDate checkinDate, LocalDate checkoutDate, String user_FullName,
			String user_Email, int numberOfAdults, int children, int totalNumberOfGuest,
			String bookingConfirmationCode,RoomResponse room ) {
		super();
		this.booking_id = roombooking_id;
		this.CheckinDate = checkinDate;
		this.CheckoutDate = checkoutDate;
		this.User_FullName = user_FullName;
		this.User_Email = user_Email;
		this.NumberOfAdults = numberOfAdults;
		this.Children = children;
		this.TotalNumberOfGuest = totalNumberOfGuest;
		this.BookingConfirmationCode = bookingConfirmationCode;
		this.room=room;
	}

	public BookingResponse() {
		super();
	}	
	
	
	
}
