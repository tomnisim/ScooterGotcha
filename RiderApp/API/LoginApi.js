
import { Response } from "./Response";
import {path, CONNECTION_ERROR, CATCH} from "./AppConstans"
import axios from "./axiosInstance"






export class LoginApi {

    

    get_notifications_list(origin, destination) { //TODO
        return axios.get(GET_ROUTES_PATH,
        {
            params:{origin: origin, destination: destination}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

}

