
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Java Program to demonstrate how to convert Date to LocalDateTime class in
 * Java 8. Just remember that, the equivalent class of Date in 
 * new Date and Time
 * API is not LocalDateTime but the Instant.
 *
 * @author WINDOWS 8
 */


public class Main {
  public static void main(String[] args) {
       String start = "16/08/2016";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //convert String to LocalDate
LocalDate date1 = LocalDate.parse(start, formatter);
//        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(start);
        String end = "18/08/2016";
        LocalDate date2 = LocalDate.parse(end, formatter);
//        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(end);

//        LocalDate date11 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate date22 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    for (LocalDate date = date1; date.isBefore(date2); date = date.plusDays(1)) {
           System.out.println(date);
     
    }      System.out.println(date2);
    }

}
 

