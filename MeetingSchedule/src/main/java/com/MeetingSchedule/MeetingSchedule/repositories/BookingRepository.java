/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MeetingSchedule.MeetingSchedule.repositories;

import com.MeetingSchedule.MeetingSchedule.entities.Booking;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Laila
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    @Modifying
    @Query(value = "INSERT INTO booking(id,employeeid,name,time,datestart, dateend, room, kodeapproval) VALUES (?1,?2,?3,?4,?5,?6,?7,?8);", nativeQuery = true)
    void saverequest(String id, String employeeid, String name, String time, String datestart, String dateend, String room, int kodeapproval);

    @Modifying
    @Query(value = "INSERT INTO booking(id,employeeid,name,time,datestart, datenow, dateend, room, kodeapproval) VALUES (?1,?2,?3,?4,?5,?6,?7,?8, ?9);", nativeQuery = true)
    void saveschedule(String id, String employeeid, String name, String time, String datestart, String datenow, String dateend, String room, int kodeapproval);

    @Query(value = "SELECT b.id, b.employeeid, b.noteapproval, b.room, e.id, b.name, b.time, b.datestart, b.datenow,b.dateend, b.alternativeroom, b.kodeapproval, r.name "
            + "FROM booking b join employee e "
            + "ON e.id=b.employeeid"
            + " JOIN room r ON r.id=b.room WHERE kodeapproval=1",
            nativeQuery = true
    )
    Collection<Booking> findactive();

    @Query(value = "SELECT b.id, b.employeeid, b.noteapproval,b.room, e.id, b.name, b.time, b.datestart, b.datenow,b.dateend, b.alternativeroom, b.kodeapproval, r.name "
            + "FROM booking b join employee e "
            + "ON e.id=b.employeeid"
            + " JOIN room r ON r.id=b.room WHERE kodeapproval=2",
            nativeQuery = true
    )
    Collection<Booking> findaccept();

    @Query(value = "SELECT b.id, b.employeeid, b.noteapproval,b.room, e.id, b.name, b.time, b.datestart, b.datenow,b.dateend, b.alternativeroom, b.kodeapproval, r.name "
            + "FROM booking b join employee e "
            + "ON e.id=b.employeeid"
            + " JOIN room r ON r.id=b.room WHERE kodeapproval=3",
            nativeQuery = true
    )
    Collection<Booking> finddecline();

    @Modifying
    @Query(value = "UPDATE booking b SET b.kodeapproval =2 WHERE b.id  =:id", nativeQuery = true)
    void adminaccept(@Param("id") String id);

    @Modifying
    @Query(value = "UPDATE booking b SET b.kodeapproval =3, b.noteapproval=:noteapproval, b.alternativeroom=:alternativeroom WHERE b.id  =:id", nativeQuery = true)
    void admindecline(@Param("id") String id, @Param("noteapproval") String noteapproval, @Param("alternativeroom") String alternativeroom  );

    
    @Modifying
    @Query(value = "UPDATE booking b SET b.kodeapproval =3, b.alternativeroom=:alternativeroom WHERE b.id =:id", nativeQuery = true)
    void admindeclineroom(@Param("id") String id,@Param("alternativeroom") String alternativeroom);

    @Modifying
    @Query(value = "UPDATE booking b SET b.room =:room, b.kodeapproval =2 WHERE b.id  =:id", nativeQuery = true)
    void updateroom(@Param("id") String id, @Param("room") String room);

    @Query(value = "SELECT * FROM booking b WHERE b.datenow like %?1%", nativeQuery = true)
    Collection<Booking> search(String datenow);


    @Modifying
    @Query(value = "UPDATE booking b SET b.datenow =:datenow WHERE b.id  =:id", nativeQuery = true)
    void updatedatenow(@Param("id") String id, @Param("datenow") String datenow);

    @Query(value = "SELECT count(*) FROM booking b WHERE b.datenow=:datenow AND time=:time AND room=:room AND kodeapproval=2;", nativeQuery = true)
    Long countsame(@Param("datenow") String datenow, @Param("time") String time, @Param("room") String room);

    @Modifying
    @Query(value = "DELETE FROM booking WHERE (id =:id);", nativeQuery = true)
    void deletegagal(@Param("id") String id);

}
