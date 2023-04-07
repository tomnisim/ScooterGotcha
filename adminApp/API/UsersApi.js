import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
import axios from '../assets/AxiosInstance';
import {path} from "./Path"

const EDIT_USER_PATH =path+"edit_user"
const DELETE_USER_PATH = path+"delete_user"
const VIEW_USER_PATH = path+"view_users"


export class UsersApi {

    // todo: complete params
    edit_user() {
        return axios.get(EDIT_USER_PATH,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    delete_user(user_email) {
        alert("trying to delete: " + user_email);
        return axios.post(DELETE_USER_PATH,
        {
            params:{user_email: user_email}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    view_users(){
        alert("in view users");
        return axios.get(VIEW_USER_PATH)
                .then(res => {
                    console.log(res);
                    return res.data;
                    // return new Response(res.data);
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
        
    }


}