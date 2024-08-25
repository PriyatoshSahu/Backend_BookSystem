package com.hotelrents.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotelrents.Model.BookedRooms;



@Repository
public interface BookingRepo extends JpaRepository<BookedRooms, Integer> {
	
	BookedRooms findByBookingConfirmationCode(String confirmationCode);
	
	List<BookedRooms> findByRoomId(int roomId);
}