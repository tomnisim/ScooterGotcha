import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
import axios from '../assets/AxiosInstance';
import {path} from "./Path"

const ADD_USER_RP_PATH =path + "add_rp_serial_number"
const DELETE_USER_PATH = path + "delete_user"
const VIEW_USER_PATH = path + "view_users"
const VIEW_WAITING_RP_PATH = path + "view_waiting_rp"


export class UsersApi {

    add_user_rp(user_rp_to_add) {
        return axios.get(ADD_USER_RP_PATH,
        {
            params:{rp_serial: user_rp_to_add}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    delete_user(user_email) {
        return axios.get(DELETE_USER_PATH,
        {
            params:{user_email: user_email}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    view_users(){
        return axios.get(VIEW_USER_PATH)
                .then(res => {
                    console.log(res);
                    return res.data;
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
        
    }
    view_waiting_rp(){
        return axios.get(VIEW_WAITING_RP_PATH)
                .then(res => {
                    return res.data;
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
        
    }
    


}