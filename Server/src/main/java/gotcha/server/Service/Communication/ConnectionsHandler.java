package gotcha.server.Service.Communication;

import gotcha.server.Utils.Response;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionsHandler {
    public ConnectionsHandler(){}

    @RequestMapping(value = "/login")
    @CrossOrigin
    public Response login(){
        System.out.println("login");
        return null;
    }

}
