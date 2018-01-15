package hello;

public class Utils {

    public static String sendUpdatedMessage(Long id){
        return "Record with ID " + id + " is updated.";
    }

    public static String sendAddedMessage(){
        return "New record added.";
    }

    public static String sendDeletedMessage(Long id){
        return "Record with ID " + id + " is deleted.";
    }
}
