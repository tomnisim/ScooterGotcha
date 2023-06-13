
import { Response } from "./Response";
import {path} from "./Path"
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
const LOGIN_PATH = path + "login"
const VIEW_NOTIFICATIONS_PATH = path + "view_notifications"
import axios from '../assets/AxiosInstance';
import AsyncStorage from '@react-native-async-storage/async-storage';

export class LoginApi {

    login(username, password) {
        const data = {
            email: username,
            password: password
          };
        return axios.post(LOGIN_PATH,data)
            .then(async (res) => {
                return new Response(res.data);
            })
            .catch(res => {
                console.log(res);
                return new Response(1);
            });
    }

    view_notifications(){
        return axios.get(VIEW_NOTIFICATIONS_PATH,
            {
                params:{}
            })
                .then(res => {
                    console.log(res);
                    return res.data;
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    

}

