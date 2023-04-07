
import { Response } from "./Response";
import {path} from "./Path"
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
const LOGIN_PATH = path+"login"
import axios from '../assets/AxiosInstance';
import AsyncStorage from '@react-native-async-storage/async-storage';


export class LoginApi {

    login(username, password) {
        alert(path)
        alert(LOGIN_PATH)
        const data = {
            email: username,
            password: password
          };
        return axios.post(LOGIN_PATH,data)
            .then(async (res) => {
                alert("test");
                return new Response(res.data);
            })
            .catch(res => {
                alert("in catch");
                return new Response(1);
            });
    }

}

