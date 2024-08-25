package com.hotelrents.Service;

import java.math.BigDecimal;  
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.hotelrents.Model.Rooms;
 
public interface RoomService {
	
//	This custom method is for addnew Rooms to the database
//	So while adding new room you have to give the Photo , roomtype , and roomprice
	
	Rooms addnewroom(MultipartFile photo, String Roomtype, int RoomPrice );
	
	List<String> getAllAndRoomTypes();
	
	List<Rooms> getAllRooms();

	byte[] getRoomPhotoById(int id) throws SQLException;
	
	void deleteRoomsById(int id);
	
	Rooms updateRoom(Integer roomId,String roomType , int roomPrice , byte[] photoBytes);
	
	Optional<Rooms> getRoomById(int id);
	
	List<Rooms> availableRooms(LocalDate checkInDate , LocalDate checkOutDate, String roomType);
}
