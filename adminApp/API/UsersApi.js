import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
import axios from '../assets/AxiosInstance';
import {path} from "./Path"

const ADD_USER_RP_PATH =path+"add_user_rp"
const DELETE_USER_PATH = path+"delete_user"
const VIEW_USER_PATH = path+"view_users"


export class UsersApi {

    add_user_rp(user_rp_to_add) {
        return axios.get(ADD_USER_RP_PATH,
        {
            params:{rp_serial_number: user_rp_to_add}
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


}