package gotcha.server.Domain.ExternalService;

import org.springframework.stereotype.Component;

@Component

public class MapsAdapterImpl extends MapsAdapter {

    public MapsAdapterImpl(){
        super();
    }
    @Override
    public boolean handshake()
    {
        return true;
    }

}
