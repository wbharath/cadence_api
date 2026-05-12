package cadence_api.common.exception;

public class DuplicateLogException extends RuntimeException{
    public DuplicateLogException(Long habitId, String date){
        super("Habit " + habitId + " already logged for date: " + date);
    }
}
