import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
import {path} from "./Path"


const ADD_ADMIN_PATH = path + "add_admin"
const DELETE_ADMIN_PATH = path + "delete_admin"
const VIEW_ADMINS_PATH = path + "view_admins"
import axios from '../assets/AxiosInstance';


export class AdminApi {

    add_admin(admin_email, admin_password, phone, birthDate, gender) {
        return axios.get(ADD_ADMIN_PATH,
        {
            params:{user_email: admin_email, user_password: admin_password, phoneNumber:phone, birthDay: birthDate, gender: gender}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    delete_admin(user_email) {
        return axios.get(DELETE_ADMIN_PATH,
        {
            params:{user_email: user_email}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    view_admins(){
        return axios.get(VIEW_ADMINS_PATH,
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