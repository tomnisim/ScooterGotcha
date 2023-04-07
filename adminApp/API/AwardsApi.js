import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
import axios from '../assets/AxiosInstance';
import {path} from "./Path"

const VIEW_AWARDS_PATH = path + "view_awards"
const ADD_AWARD_PATH = path + "add_award"



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
        const data = {
            emails: emails,
            award: award
          };
        return axios.post(ADD_AWARD_PATH,data)
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

}

