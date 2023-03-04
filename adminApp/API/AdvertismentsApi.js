import { Response } from "./Response";
import {session_id} from '../components/LoginWindow'


export const CONNECTION_ERROR = "The system is not available right now, come back later";
export const CATCH = "CATCH";



const ADD_ADVERTISE_PATH = "http://localhost:8080/add_advertisement"
const DELETE_ADVERTISE_PATH = "http://localhost:8080/delete_advertisement"
const VIEW_ADVERTISES_PATH = "http://localhost:8080/view_advertisements"

const instance = require('axios');

export class AdvertismentsApi {

    add_advertisement(final_date, owner, message, photo, url) {
        return instance.get(ADD_ADVERTISE_PATH,
        {
            params:{final_date: final_date, owner: owner, message: message, photo: photo, url: url, session_id: session_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    delete_advertisement(advertise_id) {
        return instance.get(DELETE_ADVERTISE_PATH,
        {
            params:{advertise_id: advertise_id, session_id : session_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    view_advertisements(){
        return instance.get(VIEW_ADVERTISES_PATH,
            {
                params:{session_id : session_id}
            })
                .then(res => {
                    console.log(res);
                    return res.data;
                    // return new Response(res.data);
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
        
    }


}