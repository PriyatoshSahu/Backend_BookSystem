package com.hotelrents.Service;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelrents.Exception.InvalidBookingRequestException;
import com.hotelrents.Model.BookedRooms;
import com.hotelrents.Model.Rooms;
import com.hotelrents.Repository.BookingRepo;




@Service
public class BookingServiceImpl implements BookingService {
	
	@Autowired
	BookingRepo bookingRepo;
	
	@Autowired
	RoomService roomService;
	
	
	
	@Override
	public String saveBooking(int id, BookedRooms booking) {
		if(booking.getCheckoutDate().isBefore(booking.getCheckinDate())) {
			throw new InvalidBookingRequestException("check in date must come before checkout date");
		}
		Rooms room = roomService.getRoomById(id).get();
		List<BookedRooms> existingBooking= room.getBookedroom();
		boolean roomIsAvailable=roomIsAvailable(booking ,existingBooking );
		
		if(roomIsAvailable) {
			room.addbookings(booking);
			bookingRepo.save(booking);
		}
		else {
			throw new InvalidBookingRequestException("this room has been booked or not available for the selected dates");
			
		}
		
		return booking.getBookingConfirmationCode();
	}	
	
	
	
	@Override
	public void cancelBooking(int id) {
		bookingRepo.deleteById(id);
	}

	
	@Override
	public List<BookedRooms> getBookings() {
		return bookingRepo.findAll();
	}


	@Override
	public List<BookedRooms> getAllBookingsByRoomId(int room_id) {
		return bookingRepo.findByRoomId(room_id);
	}

	
	@Override
	public BookedRooms findByBookingConfirmationCode(String confirmationCode) {
		
		return bookingRepo.findByBookingConfirmationCode(confirmationCode);
	}
	
	
	
	
	 private boolean roomIsAvailable(BookedRooms bookingRequest, List<BookedRooms> existingBookings) {
	        return existingBookings.stream()
	                .noneMatch(existingBooking ->
	                        bookingRequest.getCheckinDate().equals(existingBooking.getCheckinDate())
	                                || bookingRequest.getCheckinDate().isBefore(existingBooking.getCheckinDate())
	                                || (bookingRequest.getCheckinDate().isAfter(existingBooking.getCheckinDate())
	                                && bookingRequest.getCheckinDate().isBefore(existingBooking.getCheckinDate()))
	                                || (bookingRequest.getCheckinDate().isBefore(existingBooking.getCheckinDate())

	                                && bookingRequest.getCheckinDate().equals(existingBooking.getCheckinDate()))
	                                || (bookingRequest.getCheckinDate().isBefore(existingBooking.getCheckinDate())

	                                && bookingRequest.getCheckinDate().isAfter(existingBooking.getCheckinDate()))

	                                || (bookingRequest.getCheckinDate().equals(existingBooking.getCheckinDate())
	                                && bookingRequest.getCheckinDate().equals(existingBooking.getCheckinDate()))

	                                || (bookingRequest.getCheckinDate().equals(existingBooking.getCheckinDate())
	                                && bookingRequest.getCheckinDate().equals(bookingRequest.getCheckinDate()))
	                );

	

	
	
	
	
	
	
	
	
	 }

	
}
