import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
const ADD_ADMIN_PATH = "http://localhost:8080/add_admin"
const DELETE_ADMIN_PATH = "http://localhost:8080/delete_admin"
const VIEW_ADMINS_PATH = "http://localhost:8080/view_admins"


const instance = require('axios');

export class AdminApi {

    add_admin(admin_email, admin_password, current_admin_id, phone, birthDate, gender) {
        return instance.get(ADD_ADMIN_PATH,
        {
            params:{user_email: admin_email, user_password: admin_password, admin_id:current_admin_id, phoneNumbeR:phone, birthDay: birthDate, gender: gender}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    delete_admin(user_email, admin_id) {
        return instance.get(DELETE_ADMIN_PATH,
        {
            params:{user_email: user_email, admin_id : admin_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    view_admins(admin_id){
        return instance.get(VIEW_ADMINS_PATH,
            {
                params:{admin_id : admin_id}
            })
                .then(res => {
                    console.log(res);
                    return res.data;
                    // return new Response(res.data);
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
        
    }


}