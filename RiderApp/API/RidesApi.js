
import { Response } from "./Response";
import {path, CONNECTION_ERROR, CATCH} from "./AppConstans"
import axios from "./axiosInstance"

const VIEW_RIDES_PATH = path + "view_user_rides_history"



export class RidesApi {

    
    view_user_rides_history() { 
        return axios.get(VIEW_RIDES_PATH,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    
}

