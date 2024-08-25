package com.hotelrents.Service;



import java.sql.Blob; 
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hotelrents.Exception.InternalServerErrorException;
import com.hotelrents.Exception.ResourceNotFoundException;
import com.hotelrents.Model.Rooms;
import com.hotelrents.Repository.RoomRepo;


@Service
public class RoomServiceImpl implements RoomService{

	@Autowired
	private RoomRepo roomRepo;
	
	
//	The method is override here and here we create a Rooms class object to set the 3 attributes (photo , roomtype and roomprice)
//	and then save to Repository
	
	
	
	@Override
	public Rooms addnewroom(MultipartFile file, String Roomtype, int RoomPrice) {
	
		Rooms room = new Rooms();
		room.setRoomtype(Roomtype);
		room.setRooomPrice(RoomPrice);
		
		if(!file.isEmpty()) {
			
			try{
				byte[]photobyte=file.getBytes();
			
			Blob photoblob=new SerialBlob(photobyte);
			room.setPhoto(photoblob);
		 }
			catch(Exception e) {
				System.out.println("error");
			}
		}
		
		
		return roomRepo.save(room);
	}


	@Override
	public List<String> getAllAndRoomTypes() {
		return roomRepo.findDistinctRoomTypes();
	}


	@Override
	public List<Rooms> getAllRooms() {
		return roomRepo.findAll();
	}
	
	
	
	@Override
	public Optional<Rooms> getRoomById(int id) {
	return Optional.of(roomRepo.findById(id).get());
//		return roomRepo.findById(id);
	
	
	}


	@Override
	public byte[] getRoomPhotoById(int id) throws SQLException{
		Optional<Rooms> theRoom = roomRepo.findById(id);
		if(theRoom.isEmpty()) {
			throw new ResourceNotFoundException(" sorry no room availabel");
		}
		Blob photoBlob = theRoom.get().getPhoto();	
		if(photoBlob!=null) {
			return photoBlob.getBytes(1,(int) photoBlob.length());
		}
		return null;
	}


	@Override
	public void deleteRoomsById(int id) {
		Optional<Rooms> theRoom= roomRepo.findById(id);
		if(theRoom.isPresent()) {
			roomRepo.deleteById(id);
		}
		
		
	}


	@Override
	public Rooms updateRoom(Integer roomId, String roomType, int roomPrice, byte[] photoBytes) {
		Rooms roomById = roomRepo.findById(roomId).orElseThrow(()-> new ResourceNotFoundException("room not found"));
		if(roomType != null) roomById.setRoomtype(roomType);
		if(roomPrice != 0) roomById.setRooomPrice(roomPrice);
		if(photoBytes != null && photoBytes.length>0) {
			
			try{
				roomById.setPhoto(new SerialBlob(photoBytes));
			}
			catch(SQLException e) {
				throw new InternalServerErrorException("error updating room");
			}
		}
		return roomRepo.save(roomById);
	}


	@Override
	public List<Rooms> availableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
		return roomRepo.findAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType);
	}


	




	



	

}
