import { Response } from "./Response";
import {path} from "./Path"

export const CONNECTION_ERROR = "The system is not available right now, come back later";
export const CATCH = "CATCH";
import axios from '../assets/AxiosInstance';

const ADD_ADVERTISE_PATH = path + "add_advertisement"
const DELETE_ADVERTISE_PATH = path + "delete_advertisement"
const VIEW_ADVERTISES_PATH = path + "view_advertisements"


export class AdvertismentsApi {

    add_advertisement(final_date, owner, message, photo, url) {
        const data = {
            url: url,
            owner: owner,
            photo: photo,
            message: message,
            final_date: final_date
        };
        return axios.post(ADD_ADVERTISE_PATH,data)
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR));
    }

    delete_advertisement(advertise_id) {
        return axios.get(DELETE_ADVERTISE_PATH,
        {
            params:{advertise_id: advertise_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    view_advertisements(){
        return axios.get(VIEW_ADVERTISES_PATH,
            {
                params:{}
            })
                .then(res => {
                    console.log(res);
                    return res.data;
                    // return new Response(res.data);
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));        
    }
}


