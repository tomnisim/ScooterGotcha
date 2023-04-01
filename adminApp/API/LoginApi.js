import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
const LOGIN_PATH = "http://localhost:5050/login"
import axios from '../components/AxiosInstance';
import AsyncStorage from '@react-native-async-storage/async-storage';


export class LoginApi {

    login(username, password) {
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

