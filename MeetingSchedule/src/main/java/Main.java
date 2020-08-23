
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

//    private static int start2;
//    private static int end2;
//     String start = "16/08/2016";
//    static  String time="09.00-12.00";
//  static String[] part = time.split("-");
//  static String timestart= part[0];
//  static String timeend= part[1];
//  
//    public static int splitTimeStart(){
         
      
        
  
  public static void main(String[] args) {
//      private static int start2;
//    private static int end2;
     String start = "16/08/2016";
    String time="9.00-12.00";
  String[] part = time.split("\\-");
  String timestart= part[0];
  String timeend= part[1];
  String[] partstart=timestart.split("\\.");
 String[] partend=timeend.split("\\.");
  String timestart1=partstart[0];
  String timeend1=partend[0];
      
  int timestart2=Integer.parseInt(timestart1);
  int timeend2=Integer.parseInt(timeend1);
//      System.out.println(timeend.substring(0,1));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        convert String to LocalDate
LocalDate date1 = LocalDate.parse(start, formatter);
        String end = "18/08/2016";
        LocalDate date2 = LocalDate.parse(end, formatter);

    for (LocalDate date = date1; date.isBefore(date2); date = date.plusDays(1)) {
          
           
  for (int x=timestart2; x<timeend2; x++){
      for (int y=x+1;y<=timeend2;y++){
      System.out.println("jam"+x+"-"+y+"tanggal"+date);
  }
   
    } 
     
     }for (int x=timestart2; x<timeend2; x++){
      for (int y=x+1;y<=timeend2;y++){
      System.out.println("jam"+x+"-"+y+"tanggal"+date2);
  }
   
    }
    
      
}

  
  
}
 

