import { Response } from "./Response";
import {path} from "./Path"
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
import axios from '../assets/AxiosInstance';

const VIEW_STATS_PATH = path+"view_statistics"



export class StatisticsApi {

    view_statistics(){
        return axios.get(VIEW_STATS_PATH)
                .then(res => {
                    console.log(res);
                    return res.data;
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
        
    }

}