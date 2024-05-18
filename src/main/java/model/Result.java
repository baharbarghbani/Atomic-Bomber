package model;

public class Result {
    private String message;
    private boolean success;
    public Result(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
    @Override
    public String toString() {
        return message;
    }
    public boolean isSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
}
