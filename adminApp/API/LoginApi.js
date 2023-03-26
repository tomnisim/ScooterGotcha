import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
const LOGIN_PATH = "http://localhost:8080/login"



const instance = require('axios');

export class LoginApi {

    login(username, password) {
        return instance.get(LOGIN_PATH,
        {
            params:{email: username, password: password}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => {
                return new Response(1);
            });
    }

}

