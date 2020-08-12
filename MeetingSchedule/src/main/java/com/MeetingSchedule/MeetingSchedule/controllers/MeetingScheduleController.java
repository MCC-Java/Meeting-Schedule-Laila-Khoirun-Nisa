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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //convert String to LocalDate
LocalDate date1 = LocalDate.parse(start, formatter);
//        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(start);
        String end = booking.getDateend();
        LocalDate date2 = LocalDate.parse(end, formatter);
//        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(end);

//        LocalDate date11 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate date22 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    for (LocalDate date = date1; date.isBefore(date2); date = date.plusDays(1)) {
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            bookingService.saverequest(id, employeeid, name, time, datestart, formattedDate, dateend, room);
    }

    bookingService.saverequest(id, employeeid, name, time, datestart, dateend, dateend, room);
    return mav;
    }
    
       
    

//     @PostMapping("/save")
//    public ModelAndView save(@Valid Fakultas fakultas) {
//        
//        ModelAndView mav = new ModelAndView("redirect:/fakultas");
//        mav.addObject("fakultass", fakultasService.getAll());
//        mav.addObject("fakultas", new Fakultas());
//        fakultasService.save(fakultas); 
//        return mav; 
//    }
    
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
            if (employee1.getRole()== 1) {
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

 //halaman booking admin
    @GetMapping("/bookingAdmin")
    public String bookingAdmin(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("bookings", bookingService.getactive());
        model.addAttribute("accepted", bookingService.getaccepted());
        model.addAttribute("declined", bookingService.getdeclined());
        return "bookingViewAdmin";
    }

    @GetMapping("/acceptrequest/{id}/{noteapproval}")
    public String accept(@PathVariable(name = "id") String id, @PathVariable(name = "noteapproval") String noteapproval) {
        bookingService.adminaccept(id, noteapproval);
        return "redirect:/bookingAdminAccepted";
    }

    @GetMapping("/rejectrequest/{id}/{noteapproval}")
    public String decline(@PathVariable(name = "id") String id, @PathVariable(name = "noteapproval") String noteapproval) {
        bookingService.adminrefuse(id, noteapproval);
        return "redirect:/bookingAdminDeclined";
    }
    
    
    @GetMapping("/rejectrequestgetroom/{id}/{alternativeroom}")
    public String declinegetroom(@PathVariable(name = "id") String id, @PathVariable(name = "alternativeroom") String alternativeroom) {
        bookingService.adminrefusegetroom(id, alternativeroom);
        return "redirect:/bookingAdminDeclined";
    }
    
    //halaman booking admin declined
    @GetMapping("/bookingAdminDeclined")
    public String bookingAdminDeclined(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("bookings", bookingService.getactive());
        model.addAttribute("accepted", bookingService.getaccepted());
        model.addAttribute("declined", bookingService.getdeclined());
        return "bookingViewAdminDeclined";
    }
    
     //halaman booking admin accepted
    @GetMapping("/bookingAdminAccepted")
    public String bookingAdminAccepted(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("bookings", bookingService.getactive());
        model.addAttribute("accepted", bookingService.getaccepted());
        model.addAttribute("declined", bookingService.getdeclined());
        return "bookingViewAdminAccepted";
    }
//    @GetMapping("")
//    public ModelAndView homeLogin(@Valid Booking booking) {
//        ModelAndView mav = new ModelAndView("index");
//        mav.addObject("bookings", bookingService.getactive());
//        mav.addObject("booking", new Booking());
//        System.out.println("Home jalan");
//        return mav;
//    }
//    @GetMapping("/saverequest/")
//    public String createrequest(@PathVariable(name = "id") String id, @PathVariable(name = "employeeid") String employeeid,
//            @PathVariable(name = "name") String name,
//            @PathVariable(name = "time") String time, @PathVariable(name = "datestart") Date datestart, @PathVariable(name = "dateend") Date dateend,
//            @PathVariable(name = "room") String room) {
//        bookingServices.saverequest(id, employeeid, name, time, datestart, dateend, room);
//        return "redirect:/requestmeeting/" + id;
//    }
    @GetMapping("/requestmeeting/{id}")
    public String request(@PathVariable(name = "nim") String id, Model model) {
        model.addAttribute("booking", bookingService.getById(id));
//        model.addAttribute("active", Requestservices.findactivemhs(nim));
//        model.addAttribute("accept", Requestservices.findaccmhs(nim));
//        model.addAttribute("reject", Requestservices.findrejectmhs(nim));
        return "index";
    }
    @GetMapping("/register")
    public ModelAndView Register() {
        ModelAndView mav = new ModelAndView("registerAdmin");
        mav.addObject("employee", new Employee());
        mav.addObject("employees", employeeService.getAll());
        return mav;
    }
    
    @PostMapping("/showRegister")
    public ModelAndView showRegister(@Valid Employee employee, String id, String name, String password, int role, String departmentid, String teamid){
        ModelAndView mav = new ModelAndView("redirect:/");
        mav.addObject("employees", employeeService.getAll());
        mav.addObject("employee", new Booking());

    employeeService.saveRegister(id, name, password, role, departmentid, teamid);
    return mav;
    }
    @PostMapping("/checkRegister")
    public ModelAndView checkRegister(@Valid Employee employee, Model model, String id,String name,String password,int role,String departmentid,String teamid) {
        
        ModelAndView mav = new ModelAndView("redirect:/register");
        mav.addObject("employees", employeeService.getAll());
        mav.addObject("employee", new Employee());
        
//        String name1 = employee.getName();
        
//        Integer type = 2;
       
//        List<Employee> employees = employeeService.getAll();
//        boolean result = false;
//        for (Employee emp : employees) {
//            System.out.println(emp.getName());
//            if (emp.getName().equals(name1)) {
//                result = true;
//            }
//        }
//        if (result == false) {
            employeeService.saveRegister(id, name, password, role, departmentid, teamid);
//            System.out.println(result);
//            return "redirect:/register";
//        } else {
            return mav;
//        }
    }
 @GetMapping("/meetingScheduleAdmin")
    public String scheduleAdmin(Model model) {
       
         model.addAttribute("booking", new Booking());
        model.addAttribute("accepted", bookingService.getaccepted());
       
        return "scheduleViewAdmin";
       }
}
