import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
import axios from '../assets/AxiosInstance';
import {path} from "./Path"

const VIEW_HAZARDS_PATH = path + "view_hazards"
const ADD_HAZARD_PATH = path + "add_hazard"


export class HazardsApi {

    view_hazards() {
        return axios.get(VIEW_HAZARDS_PATH,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    add_hazard(lng, lat, city, type, size) {
        return axios.get(ADD_HAZARD_PATH,
            {
                params:{lng: lng,lat: lat,city: city,type: type,size: size}
            })
                .then(res => {
                    return new Response(res.data);
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }
}

