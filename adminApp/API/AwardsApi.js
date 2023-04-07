import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
import axios from '../assets/AxiosInstance';

const VIEW_AWARDS_PATH = "http://localhost:8080/view_awards"
const ADD_AWARD_PATH = "http://localhost:8080/add_award"



export class AwardsApi {

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

    add_award(emails, award) {
        return axios.get(ADD_AWARD_PATH,
        {
            params:{emails: emails, award: award}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }



}