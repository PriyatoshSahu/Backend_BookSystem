package com.hotelrents.Controller;

import java.io.IOException;   
import java.sql.Blob; 
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hotelrents.Exception.PhotoRetrievalException;
import com.hotelrents.Exception.ResourceNotFoundException;
import com.hotelrents.Model.BookedRooms;
import com.hotelrents.Model.Rooms;
import com.hotelrents.Response.BookingResponse;
import com.hotelrents.Response.RoomResponse;
import com.hotelrents.Service.BookingService;
import com.hotelrents.Service.RoomService;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "http://localhost:5173")

@RequestMapping("/rooms")
public class RoomController {

	@Autowired
	private RoomService roomService;

	@Autowired
	private BookingService bookingService;
	
//	in this controller section we write the method as per service class method and take all the 3 argument 
//	and then because all the 3 attributes are 
	
//	then we call the Roomservice interface's addnewroom () method and store that object in Rooms type reference
//	and when the method is called the Roomserviceimpl class's addnewroom() method is going to called
//	then there the new room is created and the 3 attributes are set from the method argument
//	
//	we are not going to effect the entity class thats why we create a Roomresponse class to display 
//	the data or output on the browser 
	
//	so now we need to change the 
//	Rooms savedrooms to Roomresponse and in Roomresponse class we createdd Paramaterised constructor so here 
//	when we create the object of the Roomresponse we pass the argument 
//	we use the Rooms savedrooms object because the Rooms object is already created and the datas are there 
//	so we use the Rooms object and set the data to Roomresponse object
	
	@PostMapping("/add/newroom")
	public ResponseEntity<RoomResponse> addNewRooms(
	@RequestParam("photo")	    MultipartFile photo , 
	@RequestParam("roomType")	String rooomType , 
	@RequestParam("roomPrice")	int roomPrice){
		
		Rooms savedrooms = roomService.addnewroom(photo, rooomType, roomPrice);
		
		RoomResponse response=new RoomResponse(savedrooms.getRoom_id(),savedrooms.getRoomtype(),savedrooms.getRooomPrice());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/room/types")
	public List<String> getRoomTypes(){
		
		return roomService.getAllAndRoomTypes();
		
	}
	
	@GetMapping("/all-rooms")
	public ResponseEntity<List<RoomResponse>> getAllRooom() throws SQLException{
		List <Rooms> room = roomService.getAllRooms();
		List<RoomResponse> roomResponses = new ArrayList<>();
		for(Rooms roooms : room) {
			
			byte[] photoBytes = roomService.getRoomPhotoById(roooms.getRoom_id());
			
			if(photoBytes!=null && photoBytes.length > 0) {
				
				String base64Photo = Base64.encodeBase64String(photoBytes);
				
				RoomResponse roomResponse = getRoomResponse(roooms);
				
				roomResponse.setPhoto(base64Photo);
				
				roomResponses.add(roomResponse);
			}
		}
		return ResponseEntity.ok(roomResponses);
	}
	
	
	@DeleteMapping("/delete/room/{room_id}")
	public ResponseEntity<Void> deleteRoomsById(@PathVariable("room_id") int room_id) {
		roomService.deleteRoomsById(room_id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@PutMapping("/update/{room_id}")
	public ResponseEntity<RoomResponse> updateRoom(@PathVariable Integer room_id ,
			
			@RequestParam (required = false) String roomType,
			
			@RequestParam(required = false) int roomPrice , 
		
			@RequestParam (required = false) MultipartFile photo) throws IOException, SQLException{
		
		byte[] photoBytes = photo != null && !photo.isEmpty()? photo.getBytes():roomService.getRoomPhotoById(room_id);
		Blob photoBlob = photoBytes != null && photoBytes.length > 0  ? new SerialBlob(photoBytes):null;
		
		
		Rooms theRoom = roomService.updateRoom(room_id, roomType, roomPrice, photoBytes);
			theRoom.setPhoto(photoBlob);
			RoomResponse roomResponse = getRoomResponse(theRoom);
			return ResponseEntity.ok(roomResponse);		
	}
	
	
//	The responsibility of the getRoomById method is to retrieve the room from the database and return 
//	it to the client. The transformation of the room into a response object is delegated to a separate method,
//	making the code more modular and easier to maintain. this is in all the method inside which the getroomresponse method is called 
	
	@GetMapping("/room/{roomId}")
	@CrossOrigin(origins = "http://localhost:5173")
	public ResponseEntity<Optional<RoomResponse>> getRoomById(@PathVariable("roomId") int roomId){
		Optional<Rooms> theRooom = roomService.getRoomById(roomId);
		return theRooom.map(room-> {
			RoomResponse roomResponse = getRoomResponse(room);
			return ResponseEntity.ok(Optional.of(roomResponse));
		}).orElseThrow(()-> new ResourceNotFoundException("room not found"));
	}
	
	
	@GetMapping("/available-rooms")
	public ResponseEntity<List<RoomResponse>> availableRooms(
			@RequestParam("checkInDate")@DateTimeFormat(iso=ISO.DATE) LocalDate checkInDate ,
			@RequestParam("checkOutDate") @DateTimeFormat(iso=ISO.DATE) LocalDate checkOutDate , 
			@RequestParam("roomType") String roomType) throws SQLException{
		
		List<Rooms> availablerooms = roomService.availableRooms(checkInDate, checkOutDate, roomType);
		List<RoomResponse> roomresponse = new ArrayList<>();
		
//		this below code is to set the photo  
//		so here we just going to check for all rooms we gotten from availablerooms. we are going to withdraw the photo 
//		that we going to fetch along with them
		
		for(Rooms rom : availablerooms) {
			byte[] photobyte = roomService.getRoomPhotoById(rom.getRoom_id());
			if(photobyte != null && photobyte.length >0) {
			String photo = Base64.encodeBase64String(photobyte);
			RoomResponse roomRespon = getRoomResponse(rom);
			roomRespon.setPhoto(photo);
			roomresponse.add(roomRespon);
			}
		}
			if(roomresponse.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			else {
				return ResponseEntity.ok(roomresponse);
			}
		
	}
	
	
	
	
	

// getAllBookingsByroomId(roooms.getRoom_id()): This line retrieves all bookings associated with the room	
//	List<BookingResponse> bookingInfo = bookings.stream().map()..toList(): It maps each BookedRooms object to a 
//	BookingResponse object and collects them into a list.
	
	
	
	
	private RoomResponse getRoomResponse(Rooms roooms) {		
		 List<BookedRooms> bookings = getAllBookingsByroomId(roooms.getRoom_id());
		 List<BookingResponse> bookingInfo = bookings.stream()
				 .map(booking -> new BookingResponse(
						 booking.getRoombooking_id(), 
						 booking.getCheckinDate(), 
						 booking.getCheckoutDate(), 
						 booking.getBookingConfirmationCode())).toList();
		 byte[] photoBytes=null;
		 Blob photoBlob = roooms.getPhoto();
		 if(photoBlob!=null) {
			 try {
				 photoBytes=photoBlob.getBytes(1, (int)photoBlob.length());
			 }
			 catch(SQLException e) {
				 throw new PhotoRetrievalException("Error retrieving photo");
			 }
		 }
		return new RoomResponse(roooms.getRoom_id(),
				roooms.getRoomtype(),
				roooms.getRooomPrice(),photoBytes,roooms.isIsbooked(),bookingInfo);
		 }

	
	
	
	private List<BookedRooms> getAllBookingsByroomId(int room_id) {
			return bookingService.getAllBookingsByRoomId(room_id);
	}
	
	
	
	
	
}

