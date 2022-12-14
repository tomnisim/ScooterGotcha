package gotcha.server.Utils;

public class Response<T> {
    private T value;
    private boolean was_exception=false;
    private String message;

    public Response(String message, Exception e){
        this.value = (T) e;
        was_exception = true;
        this.message = message;
    }

    public Response(T value, String message){
        this.value = value;
        this.was_exception = false;
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public boolean WasException() {
        return was_exception;
    }

    public String getMessage() {
        return message;
    }

    public Response() {
    }

    public boolean iswas_exception() {
        return was_exception;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setwas_exception(boolean was_exception) {
        this.was_exception = was_exception;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}