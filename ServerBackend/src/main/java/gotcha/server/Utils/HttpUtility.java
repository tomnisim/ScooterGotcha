package gotcha.server.Utils;

import gotcha.server.Utils.Threads.ConnectThread;

import java.util.HashMap;

public class HttpUtility {

    public static final int METHOD_GET = 0; // METHOD GET
    public static final int METHOD_POST = 1; // METHOD POST

    // Callback interface
    public interface Callback {
        // abstract methods
        public String OnSuccess(String response);
        public String OnError(int status_code, String message);
    }
    // static method
    public static String newRequest(String web_url, int method, HashMap < String, String > params, Callback callback)  {
        try{
            ConnectThread connectThread = new ConnectThread(web_url, method, params, callback);
            Thread t1 = new Thread(connectThread);
            t1.start();
            t1.join();
            return connectThread.get_value();
        }
        catch (Exception e) {
            return "BAD";
        }

    }
}