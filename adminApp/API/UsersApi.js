import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
import {session_id} from '../components/LoginWindow'

const EDIT_USER_PATH = "http://localhost:8080/edit_user"
const DELETE_USER_PATH = "http://localhost:8080/delete_user"
const VIEW_USER_PATH = "http://localhost:8080/view_users"


const instance = require('axios');

export class UsersApi {

    // todo: complete params
    edit_user() {
        return instance.get(EDIT_USER_PATH,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    delete_user(user_email) {
        return instance.get(DELETE_USER_PATH,
        {
            params:{user_email: user_email, session_id : session_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    view_users(){
        return instance.get(VIEW_USER_PATH,
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