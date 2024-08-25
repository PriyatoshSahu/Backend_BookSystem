package com.hotelrents.Controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotelrents.Exception.InvalidBookingRequestException;
import com.hotelrents.Exception.ResourceNotFoundException;
import com.hotelrents.Model.BookedRooms;
import com.hotelrents.Model.Rooms;
import com.hotelrents.Response.BookingResponse;
import com.hotelrents.Response.RoomResponse;
import com.hotelrents.Service.BookingService;
import com.hotelrents.Service.RoomService;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/booking")
public class BookedRoomController {

	@Autowired
	BookingService bookingService;
	
	@Autowired
	RoomService roomService;
	
	
	@GetMapping("/all-booking")
	@CrossOrigin(origins = "http://localhost:5173")
	public ResponseEntity<List<BookingResponse>> getAllBooking(){
		List<BookedRooms> bookins = bookingService.getBookings();
		List<BookingResponse> bookingResponses = new ArrayList<>();
		for(BookedRooms rooms : bookins) {
			BookingResponse bookingResponse = getBookingResponse(rooms);
			bookingResponses.add(bookingResponse);
		}
		return ResponseEntity.ok(bookingResponses);
	}
	
	
	@GetMapping("/confirm/{confirmation}")
	public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable("confirmation") String confirmation){
		try {
		BookedRooms booking=bookingService.findByBookingConfirmationCode(confirmation);
		BookingResponse bookingresponse = getBookingResponse(booking);
		return ResponseEntity.ok(bookingresponse);
		}
		catch(ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	
	
	

	@PostMapping("/room/{roomId}/booking")
	public ResponseEntity<?> saveBoking( @PathVariable("roomId")  int roomId , @RequestBody  BookedRooms bookedRooms){
		try {
			String confirmationcode = bookingService.saveBooking(roomId, bookedRooms);
			return ResponseEntity.ok("your room booked successfully and your bookingconfirmationcode is : "+confirmationcode );
		}
		catch(InvalidBookingRequestException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	
	@DeleteMapping("/booking/{booking_id}")
	public void cancelBooking( @PathVariable("booking_id") int id) {
		bookingService.cancelBooking(id);
	}
	
	
	
	
	private BookingResponse getBookingResponse(BookedRooms booking) {
		Rooms theRoom = roomService.getRoomById(booking.getRoom().getRoom_id()).get();
		RoomResponse room = new RoomResponse(theRoom.getRoom_id(), theRoom.getRoomtype(), theRoom.getRooomPrice());
		
		return new BookingResponse(booking.getRoombooking_id(), booking.getCheckinDate(),
				booking.getCheckoutDate(), booking.getUser_FullName(), 
				booking.getUser_Email(), booking.getNumberOfAdults(),
				booking.getChildren(), booking.getTotalNumberOfGuest(), 
				booking.getBookingConfirmationCode(), room);
	}
	
	
}
