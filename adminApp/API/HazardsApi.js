import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
import axios from '../assets/AxiosInstance';
import {path} from "./Path"

const VIEW_HAZARDS_PATH = path + "view_hazards"
const ADD_HAZARD_PATH = path + "add_hazard"
const DELETE_HAZARD_PATH = path + "delete_hazard"
const REPORT_HAZARD_PATH = path + "report_hazard"
const GET_HAZARDS_IN_CITY_PATH = path + "get_hazards_in_city"


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

    delete_hazard(hazard_to_remove) {
        return axios.get(DELETE_HAZARD_PATH,
            {
                params:{hazard_id: hazard_to_remove}
            })
                .then(res => {
                    return new Response(res.data);
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    report_hazard(hazard_to_report) {
        return axios.get(REPORT_HAZARD_PATH,
            {
                params:{hazard_id: hazard_to_report}
            })
                .then(res => {
                    return new Response(res.data);
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    get_hazards_in_city(city){
        return axios.get(GET_HAZARDS_IN_CITY_PATH,
            {
                params:{city: city}
            })
                .then(res => {
                    return new Response(res.data);
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));  
    }
}

