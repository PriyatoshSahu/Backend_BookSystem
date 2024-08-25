package com.hotelrents.Response;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;

import jakarta.annotation.Generated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class RoomResponse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int room_id;
	
	private String Roomtype;
	
	private int RooomPrice;
	
	private String photo; 
	
	private boolean isbooked=false;
	
	private List<BookingResponse> bookingRespone = new ArrayList<BookingResponse>();

	
	
	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public String getRoomtype() {
		return Roomtype;
	}

	public void setRoomtype(String roomtype) {
		this.Roomtype = roomtype;
	}

	public int getRooomPrice() {
		return RooomPrice;
	}

	public void setRooomPrice(int rooomPrice) {
		this.RooomPrice = rooomPrice;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isIsbooked() {
		return isbooked;
	}

	public void setIsbooked(boolean isbooked) {
		this.isbooked = isbooked;
	}

	public List<BookingResponse> getBookingRespone() {
		return bookingRespone;
	}

	public void setBookingRespone(List<BookingResponse> bookingRespone) {
		this.bookingRespone = bookingRespone;
	}

	public RoomResponse(int room_id, String roomtype, int rooomPrice) {
		super();
		this.room_id = room_id;
		this.Roomtype = roomtype;
		this.RooomPrice = rooomPrice;
	}
//List<BookingResponse> bookingRespone it is going to add in the below constructor as argument
	
	public RoomResponse(int room_id, String roomtype, int rooomPrice, byte[] photobyte, boolean isbooked, List<BookingResponse> bookingRespone) {
		super();
		this.room_id = room_id;
		this.Roomtype = roomtype;
		this.RooomPrice = rooomPrice;
		if (photobyte != null) {
			this.photo = Base64.encodeBase64String(photobyte);
		}
		else {
			this.photo = null;
		}
		this.isbooked = isbooked;
		this.bookingRespone = bookingRespone;
	}
	
	
}
