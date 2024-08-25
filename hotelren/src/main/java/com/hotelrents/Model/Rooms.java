package com.hotelrents.Model;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity

public class Rooms {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String Roomtype;
	
	private int RooomPrice;
	
	@Lob
	private Blob photo; 
	
	private boolean isbooked=false;
	
//	Cascade= cascadetype.All mean when ever i will delete a room the all booking history of that room is going to delete
//	in simple if i delete some data then all the history of that data is going to be delete
		
//	@OneToMany annotation we added here because one room can be booked by many people 
	
	
	@OneToMany( mappedBy = "room" , fetch = FetchType.LAZY , cascade = CascadeType.ALL) 
	private List<BookedRooms> bookedroom ;

	public Rooms() {
	this.bookedroom=new ArrayList<BookedRooms>();
} 
// bookedroom arraylist object is for storing the booking data mean the 
//	room is booked or not 
	

	
	
	
	
	
	
//
//
	public int getRoom_id() {
		return id;
	}

	public void setRoom_id(int room_id) {
		this.id = room_id;
	}

	public String getRoomtype() {
		return Roomtype;
	}

	public void setRoomtype(String roomtype) {
		Roomtype = roomtype;
	}

	public int getRooomPrice() {
		return RooomPrice;
	}

	public void setRooomPrice(int rooomPrice) {
		RooomPrice = rooomPrice;
	}

	public Blob getPhoto() {
		return photo;
	}

	public void setPhoto(Blob photo) {
		this.photo = photo;
	}

	public boolean isIsbooked() {
		return isbooked;
	}

	public void setIsbooked(boolean isbooked) {
		this.isbooked = isbooked;
	}

	public List<BookedRooms> getBookedroom() {
		return bookedroom;
	}

	public void setBookedroom(List<BookedRooms> bookedroom) {
		this.bookedroom = bookedroom;
	}
	
	
	
	
//	In this addbookings method which is just below this  we take the argument as BookedRooms Object 
//	and assign or add the BookedRoooms Object in the bookedroom list 
//	
	
	public void addbookings(BookedRooms bookedRoom) {
		if(bookedroom==null) {
			bookedroom=new ArrayList<>();
		}
		
		bookedroom.add(bookedRoom);
		
		bookedRoom.setRoom(this);
		
		isbooked=true;
		
//		here the random class and its method is used to generate random number 
		Random random=new Random();
		int random_int=random.nextInt(1000);
		
		String Bookingode = String.valueOf(random_int);  
		//Here the string.valueOf()is used to convert integer to string
		
		bookedRoom.setBookingConfirmationCode(Bookingode);
//		here we set the bookingconfirmation code to the BookedRooms bookedRooms object
	}
public Rooms(int room_id, String roomtype, int rooomPrice, Blob photo, boolean isbooked,
		List<BookedRooms> bookedrooms) {
	super();
	this.id = room_id;
	this.Roomtype = roomtype;
	this.RooomPrice = rooomPrice;
	this.photo = photo;
	this.isbooked = isbooked;
	this.bookedroom = bookedrooms;
}
}
