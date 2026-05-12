package cadence_api.common.exception;

public class LogNotFoundException extends RuntimeException{
    public LogNotFoundException(Long id) {
        super("Log not found with id: " + id);
    }
}
