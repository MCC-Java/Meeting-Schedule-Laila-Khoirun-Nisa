/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MeetingSchedule.MeetingSchedule.controllers;

import com.MeetingSchedule.MeetingSchedule.entities.Booking;
import com.MeetingSchedule.MeetingSchedule.entities.Employee;
import com.MeetingSchedule.MeetingSchedule.services.BookingService;
import com.MeetingSchedule.MeetingSchedule.services.EmployeeService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Laila
 */
@Controller

public class MeetingScheduleController {

    @Autowired
    BookingService bookingService;

    @Autowired
    EmployeeService employeeService;
//save sisi employee

    @PostMapping("/save")
    public ModelAndView save(@Valid Booking booking, String id, String employeeid, String name, String time, String datestart, String datenow, String dateend, String room) throws Exception {
        ModelAndView mav = new ModelAndView("redirect:/booking");
        mav.addObject("bookings", bookingService.getactive());
        mav.addObject("booking", new Booking());

        String start = booking.getDatestart();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //convert String to LocalDate
        LocalDate date1 = LocalDate.parse(start, formatter);
        String end = booking.getDateend();
        LocalDate date2 = LocalDate.parse(end, formatter);
        bookingService.saverequest(id, employeeid, name, time, datestart, dateend, room);
        return mav;
    }

    //saveadmin
    @PostMapping("/savebyadmin")
    public ModelAndView saveByAdmin(@Valid Booking booking, String id, String employeeid, String name, String time, String datestart, String datenow, String dateend, String room) throws Exception {
        ModelAndView mav = new ModelAndView("redirect:/bookingAdminAccepted");
        mav.addObject("bookings", bookingService.getactive());
        mav.addObject("booking", new Booking());
        
        String start = booking.getDatestart();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //convert String to LocalDate
        LocalDate date1 = LocalDate.parse(start, formatter);
        String end = dateend;
        LocalDate date2 = LocalDate.parse(end, formatter);
        String[] part = time.split("\\-");
        String timestart = part[0];
        String timeend = part[1];
        String[] partstart = timestart.split("\\.");
        String[] partend = timeend.split("\\.");
        String timestart1 = partstart[0];
        String timeend1 = partend[0];

        int timestart2 = Integer.parseInt(timestart1);
        int timeend2 = Integer.parseInt(timeend1);

        for (LocalDate date = date1; date.isBefore(date2); date = date.plusDays(1)) {
            for (int x = timestart2; x < timeend2; x++) {
                for (int y = x+1; y <= timeend2; y++) {
                    String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String timestartstring = Integer.toString(x);
                    String timeendstring = Integer.toString(y);
                    String timestartjoin = String.join(".", timestartstring, "00");
                    String timeendjoin = String.join(".", timeendstring, "00");
                    String timejoin = String.join("-", timestartjoin, timeendjoin);
                    if (bookingService.getsame(formattedDate, timejoin, room) > 0) {
                        bookingService.deletegagaladmin(id);
                        ModelAndView mavi = new ModelAndView("redirect:/gagal");
                        return mavi;
                    } else {
                        if (date == date1) {
                         bookingService.savescheduleaccept(id, employeeid, name, time, datestart, formattedDate, dateend, room);
                        } else {
                            bookingService.savescheduleaccept(id, employeeid, name, time, datestart, formattedDate, dateend, room);
                        }
                    }

                }
            }
        }
            for (int x = timestart2; x < timeend2; x++) {
                for (int y = x + 1; y <= timeend2; y++) {

                    String timestartstring = Integer.toString(x);
                    String timeendstring = Integer.toString(y);
                    String timestartjoin = String.join(".", timestartstring, "00");
                    String timeendjoin = String.join(".", timeendstring, "00");
                    String timejoin = String.join("-", timestartjoin, timeendjoin);
                            bookingService.savescheduleaccept(id, employeeid, name, time, datestart, dateend, dateend, room);
                        }
                    }

//                }
        return mav;    }

    //halaman booking employee
    @GetMapping("/booking")
    public String bookingEmployee(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("bookings", bookingService.getactive());
        return "bookingView";
    }

    //view booking accepted employee
    @GetMapping("/bookingAccepted")
    public String bookingAccepted(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("bookings", bookingService.getactive());
        model.addAttribute("accepted", bookingService.getaccepted());
        model.addAttribute("declined", bookingService.getdeclined());
        return "bookingViewAccepted";
    }

    //view booking declined employee
    @GetMapping("/bookingDeclined")
    public String bookingDeclined(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("bookings", bookingService.getactive());
        model.addAttribute("accepted", bookingService.getaccepted());
        model.addAttribute("declined", bookingService.getdeclined());
        return "bookingViewDeclined";
    }

//updateroom    
    @GetMapping("/updateroom/{id}/{room}")
    public String updateroom(@PathVariable(name = "id") String id, @PathVariable(name = "room") String room) {
        bookingService.updateroom(id, room);
        return "redirect:/booking";
    }
    //login

    @GetMapping("")
    public String awal(Model model) {
        model.addAttribute("employee", new Employee());
        return "login";
    }

    //schedule
    @GetMapping("/meetingschedule")
    public String schedule(Model model) {

        model.addAttribute("booking", new Booking());
        model.addAttribute("accepted", bookingService.getaccepted());

        return "scheduleView";
    }

    @RequestMapping("/login")
    public ModelAndView Login(@ModelAttribute(name = "employee") Employee employee) {
        String name = employee.getName();
        String password = employee.getPassword();
        if (employeeService.getLogin(name, password) == true) {
            ModelAndView mav = new ModelAndView("index");
            return mav;
        } else {
            ModelAndView mav = new ModelAndView("login");
            return mav;
        }

    }

    //check login
    @RequestMapping("/check")
    public String checkLogin(@ModelAttribute(name = "employee") Employee employee, Model model) {

        String name = employee.getName();
        String password = employee.getPassword();
        String id = employee.getId();

        boolean result = false;
        List<Employee> employees = employeeService.getAll();
        for (Employee emp : employees) {
            if (emp.getId().equals(id)) {
                result = true;
            }
        }
        if (result == true) {
            Employee employee1 = employeeService.getById(id);
            if (employee1.getRole() == 1) {
                model.addAttribute("bookings", bookingService.getactive());
                model.addAttribute("booking", new Booking());
                model.addAttribute("id", id);
                return "redirect:/booking";
            } else {
                model.addAttribute("bookings", bookingService.getactive());
                model.addAttribute("booking", new Booking());
                model.addAttribute("id", id);
                return "redirect:/bookingAdmin";
            }
        } else {
            return "redirect:/login";
        }
    }

    //view booking accepted employee
    @GetMapping("/search")
    public String search(Model model, String datenow) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("bookings", bookingService.getactive());
        model.addAttribute("declined", bookingService.getdeclined());
        model.addAttribute("accepted", bookingService.search(datenow));
        return "redirect:/meetingschedule";
    }
    //halaman booking admin

    @GetMapping("/bookingAdmin")
    public String bookingAdmin(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("bookings", bookingService.getactive());
        model.addAttribute("accepted", bookingService.getaccepted());
        model.addAttribute("declined", bookingService.getdeclined());
        return "bookingViewAdmin";
    }

    //acceptrequest
    @GetMapping("/acceptrequest/{id}")
    public String accept(@PathVariable(name = "id") String id) {
        bookingService.adminaccept(id);
        return "redirect:/saveschedule/{id}";
    }
//save request schedule by admin
    @GetMapping("/saveschedule/{id}")
    public ModelAndView saveSchedule(@PathVariable(name = "id") String id, Booking booking) throws Exception {
        ModelAndView mav = new ModelAndView("redirect:/bookingAdminAccepted");
        String employeeid = bookingService.getEmployeeId(id);
        String name = bookingService.getNameEmployee(id);
        String time = bookingService.getTimeSchedule(id);
        String datestart = bookingService.getDateStart(id);
        String dateend = bookingService.getDateEnd(id);
        String room = bookingService.getRoomSchedule(id);

        String start = datestart;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //convert String to LocalDate
        LocalDate date1 = LocalDate.parse(start, formatter);
        String end = dateend;
        LocalDate date2 = LocalDate.parse(end, formatter);

        String[] part = time.split("\\-");
        String timestart = part[0];
        String timeend = part[1];
        String[] partstart = timestart.split("\\.");
        String[] partend = timeend.split("\\.");
        String timestart1 = partstart[0];
        String timeend1 = partend[0];

        int timestart2 = Integer.parseInt(timestart1);
        int timeend2 = Integer.parseInt(timeend1);

        for (LocalDate date = date1; date.isBefore(date2); date = date.plusDays(1)) {
            for (int x = timestart2; x < timeend2; x++) {
                for (int y = x + 1; y <= timeend2; y++) {
                    String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String timestartstring = Integer.toString(x);
                    String timeendstring = Integer.toString(y);
                    String timestartjoin = String.join(".", timestartstring, "00");
                    String timeendjoin = String.join(".", timeendstring, "00");
                    String timejoin = String.join("-", timestartjoin, timeendjoin);
                    if (bookingService.getsame(formattedDate, timejoin, room) > 0) {
                        bookingService.deletegagal(id);
                        ModelAndView mavi = new ModelAndView("redirect:/gagal");
                        return mavi;
                    } else {
                        if (date == date1) {
                            bookingService.updatedatenow(id, formattedDate);
                        } else {
                            bookingService.savescheduleaccept(id, employeeid, name, time, datestart, formattedDate, dateend, room);
                        }
                    }

                }
            }
        }
            for (int x = timestart2; x < timeend2; x++) {
                for (int y = x + 1; y <= timeend2; y++) {
                    String timestartstring = Integer.toString(x);
                    String timeendstring = Integer.toString(y);
                    String timestartjoin = String.join(".", timestartstring, "00");
                    String timeendjoin = String.join(".", timeendstring, "00");
                    String timejoin = String.join("-", timestartjoin, timeendjoin);
                            bookingService.savescheduleaccept(id, employeeid, name, time, datestart, dateend, dateend, room);
                        }
                    }

        return mav;    }

    //rejectrequest
    @GetMapping("/rejectrequest/{id}/{noteapproval}/{alternativeroom}")
    public String decline(@PathVariable(name = "id") String id,
            @PathVariable(name = "noteapproval") String noteapproval,
            @PathVariable(name = "alternativeroom") String alternativeroom
    ) {
        bookingService.adminrefuse(id, noteapproval, alternativeroom);
        return "redirect:/bookingAdminDeclined";
    }

    @GetMapping("/rejectrequestgetroom/{id}/{alternativeroom}")
    public String declinegetroom(@PathVariable(name = "id") String id,
            @PathVariable(name = "alternativeroom") String alternativeroom
    ) {
        bookingService.adminrefusegetroom(id, alternativeroom);
        return "redirect:/bookingAdminDeclined";
    }

    //halaman booking admin declined
    @GetMapping("/bookingAdminDeclined")
    public String bookingAdminDeclined(Model model
    ) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("bookings", bookingService.getactive());
        model.addAttribute("accepted", bookingService.getaccepted());
        model.addAttribute("declined", bookingService.getdeclined());
        return "bookingViewAdminDeclined";
    }

    //halaman booking admin accepted
    @GetMapping("/bookingAdminAccepted")
    public String bookingAdminAccepted(Model model
    ) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("bookings", bookingService.getactive());
        model.addAttribute("accepted", bookingService.getaccepted());
        model.addAttribute("declined", bookingService.getdeclined());
        return "bookingViewAdminAccepted";
    }

    //register
    @GetMapping("/register")
    public ModelAndView Register() {
        ModelAndView mav = new ModelAndView("registerAdmin");
        mav.addObject("employee", new Employee());
        mav.addObject("employees", employeeService.getAll());
        return mav;
    }

    //saveregister
    @PostMapping("/showRegister")
    public ModelAndView showRegister(@Valid Employee employee, String id,
            String name, String password,
            int role, String departmentid,
            String teamid
    ) {
        ModelAndView mav = new ModelAndView("redirect:/");
        mav.addObject("employees", employeeService.getAll());
        mav.addObject("employee", new Booking());

        employeeService.saveRegister(id, name, password, role, departmentid, teamid);
        return mav;
    }

    //checkregister
    @PostMapping("/checkRegister")
    public ModelAndView checkRegister(@Valid Employee employee, Model model,
            String id, String name,
            String password,
            int role, String departmentid,
            String teamid
    ) {

        ModelAndView mav = new ModelAndView("redirect:/register");
        mav.addObject("employees", employeeService.getAll());
        mav.addObject("employee", new Employee());

        employeeService.saveRegister(id, name, password, role, departmentid, teamid);
        return mav;
    }

    //halaman meeting schedule admin
    @GetMapping("/meetingScheduleAdmin")
    public String scheduleAdmin(Model model
    ) {

        model.addAttribute("booking", new Booking());
        model.addAttribute("accepted", bookingService.getaccepted());

        return "scheduleViewAdmin";
    }

    //halaman gagal
    @GetMapping("/gagal")
    public String gagalRequest(Model model) {
        model.addAttribute("booking", new Booking());
        return "gagal";
    }
}
