import { Response } from "./Response";
import {path} from "./Path"

export const CONNECTION_ERROR = "The system is not available right now, come back later";
export const CATCH = "CATCH";

const ADD_ADVERTISE_PATH = path + "add_advertisement"
const DELETE_ADVERTISE_PATH = path + "delete_advertisement"
const VIEW_ADVERTISES_PATH = path + "view_advertisements"

const instance = require('axios');

export class AdvertismentsApi {

    add_advertisement(final_date, owner, message, photo, url) {
        return instance.get(ADD_ADVERTISE_PATH,
        {
            params:{final_date: final_date, owner: owner, message: message, photo: photo, url: url }
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    delete_advertisement(advertise_id) {
        return instance.get(DELETE_ADVERTISE_PATH,
        {
            params:{advertise_id: advertise_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    view_advertisements(){
        return instance.get(VIEW_ADVERTISES_PATH,
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