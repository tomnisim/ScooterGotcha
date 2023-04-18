
import { Response } from "./Response";
import {path, CONNECTION_ERROR, CATCH} from "./AppConstans"
import axios from "./axiosInstance"

const LOGIN_PATH = path + "login"
const LOGOT_PATH = path + "logout"
const REGISTER_PATH = path + "register"

const VIEW_NOTIFICATIONS_PATH = path + "view_notifications"

const CHANGE_PASSWORD_PATH = path + "change_password"
const RESET_PASSWORD_PATH = path + "reset_password"

const VIEW_RIDES_HISTORY_PATH = path + "view_user_rides_history"

const ADD_QUESTION_PATH = path + "add_user_question"
const VIEW_QUESTIONS_PATH = path + "view_all_user_questions"

const GET_ROUTES_PATH = path + "get_safe_routes"

const VIEW_ADVERTISEMENTS_PATH = path + "view_all_advertisement"
const ADD_ADV_CLICK_PATH = path + "" // TODO
const VIEW_AWARDS_PATH = path + "view_awards"



export class LoginApi {

    

    get_notifications_list(origin, destination) { //TODO
        return axios.get(GET_ROUTES_PATH,
        {
            params:{origin: origin, destination: destination}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

}

