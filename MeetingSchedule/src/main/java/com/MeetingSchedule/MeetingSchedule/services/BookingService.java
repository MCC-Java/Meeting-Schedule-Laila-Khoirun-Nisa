/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MeetingSchedule.MeetingSchedule.services;

import com.MeetingSchedule.MeetingSchedule.entities.Booking;
import com.MeetingSchedule.MeetingSchedule.repositories.BookingRepository;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Laila
 */
@Service
@Transactional
    
public class BookingService {
    
    @Autowired
    BookingRepository bookingRepository;
    

    public List<Booking> getactive(){
        return (List<Booking>) bookingRepository.findactive();
    }
    public List<Booking> getaccepted(){
        return (List<Booking>) bookingRepository.findaccept();
    }
    
    public List<Booking> getdeclined(){
        return (List<Booking>) bookingRepository.finddecline();
    }
    
    public List<Booking> getdeclinedgetroom(){
        return (List<Booking>) bookingRepository.finddecline();
    }
    
//    public List<Booking> getAll() {
//        return bookingRepository.findAll();
//    }    
     public Booking save(Booking booking) {
        return bookingRepository.save(booking);

    }
//       public List<Siswa> getAll() {
//        return siswaRepository.findAll();
//    }
    //read
//    public List<Booking> getById(String id) {
//        return bookingRepository.findById(id).get();
//    }
     public void saverequest(String id, String employeeid, String name, String time, String datestart, String datenow,String dateend, String room){
        bookingRepository.saverequest(id, employeeid, name, time, datestart, datenow, dateend, room, 1);
    }
     public Booking getById(String id) {
        return bookingRepository.findById(id).get();
    }
    public void adminaccept(String id, String noteapproval){
        bookingRepository.findById(id).get().setNoteapproval(noteapproval);
        bookingRepository.findById(id).get().setKodeapproval(2);
        
     bookingRepository.adminaccept(id);
    }
    
    public void adminrefuse(String id, String noteapproval){
        bookingRepository.findById(id).get().setNoteapproval(noteapproval);
        bookingRepository.findById(id).get().setKodeapproval(3);

        bookingRepository.admindecline(id);
    }
    public void adminrefusegetroom(String id, String alternativeroom){
        bookingRepository.findById(id).get().setAlternativeroom(alternativeroom);
        bookingRepository.findById(id).get().setKodeapproval(3);

        bookingRepository.admindecline(id);
    }
    public void updateroom(String id, String room){
        bookingRepository.findById(id).get().setRoom(room);
        bookingRepository.findById(id).get().setKodeapproval(2);
        bookingRepository.updateroom(id, room);
    }
}