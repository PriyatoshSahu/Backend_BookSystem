package com.hotelrents.Service;

import java.util.List;

import com.hotelrents.Model.BookedRooms;



public interface BookingService {
	
	
	public void cancelBooking(int id);
	
	public List<BookedRooms> getBookings();
	
	public String saveBooking(int id , BookedRooms booking);

	public List<BookedRooms> getAllBookingsByRoomId(int room_id);
	
	public BookedRooms findByBookingConfirmationCode(String confirmationCode);
}
