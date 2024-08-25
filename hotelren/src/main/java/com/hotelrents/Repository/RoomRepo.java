package com.hotelrents.Repository;

import java.time.LocalDate;
import java.util.List;import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hotelrents.Model.Rooms;

public interface RoomRepo extends JpaRepository<Rooms, Integer>{

	@Query("SELECT DISTINCT r.Roomtype FROM Rooms r")
	List<String> findDistinctRoomTypes();
	
	
	@Query("select r from Rooms r " + "where r.Roomtype LIKE %:Roomtype% " 
	+ "and r.id NOT IN  (" + 
			"select br.room.id from BookedRooms br " + 
	"where((br.checkinDate <= :CheckoutDate) AND (br.checkoutDate >= :CheckinDate))"
			+")")
	List<Rooms> findAvailableRoomsByDateAndType(LocalDate CheckinDate , LocalDate CheckoutDate, String Roomtype);

}
