
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



export class UserApi {

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
                return new Response(1);
            });
    }

    logout() {
        return axios.post(LOGOT_PATH)
            .then(async (res) => {
                return new Response(res.data);
            })
            .catch(res => {
                return new Response(1);
            });
    }

    register(email, password, name, lastName, phoneNumber, gender, rpSerial, licenseIssueDate, scooterType, birthDate) {
        const data = {
            email: email,
            password: password,
            name: name,
            lastName: lastName,
            phoneNumber: phoneNumber,
            gender: gender,
            raspberrySerialNumber: rpSerial, 
            licenseIssueDate: licenseIssueDate,
            scooterType: scooterType,
            birthDate: birthDate
          };
        return axios.post(REGISTER_PATH,data)
            .then(async (res) => {
                return new Response(res.data);
            })
            .catch(res => {
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

    change_password(old_password, password) {
        const data = {
            old_password: old_password,
            password: password
          };
        return axios.post(CHANGE_PASSWORD_PATH,data)
            .then(async (res) => {
                return new Response(res.data);
            })
            .catch(res => {
                return new Response(1);
            });
    }

    reset_password(email) {
        const data = {
            email: email
          };
        return axios.post(RESET_PASSWORD_PATH,data)
            .then(async (res) => {
                return new Response(res.data);
            })
            .catch(res => {
                return new Response(1);
            });
    }

    view_rides() {
        return axios.get(VIEW_RIDES_HISTORY_PATH,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    view_questions() {
        return axios.get(VIEW_QUESTIONS_PATH,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    add_question(message) {
        const data = {
            message: message
          };
        return axios.post(ADD_QUESTION_PATH,data)
            .then(async (res) => {
                return new Response(res.data);
            })
            .catch(res => {
                return new Response(1);
            });
    }

    view_advertisements() {
        return axios.get(VIEW_ADVERTISEMENTS_PATH,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    add_adv_click(adv_id) {
        const data = {
            advertisement_id: adv_id
          };
        return axios.post(ADD_ADV_CLICK_PATH,data)
            .then(async (res) => {
                return new Response(res.data);
            })
            .catch(res => {
                return new Response(1);
            });
    }

    view_awards() {
        return axios.get(VIEW_AWARDS_PATH,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    get_routes(origin, destination) {
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

