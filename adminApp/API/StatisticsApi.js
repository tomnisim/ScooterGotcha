import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

const instance = axios.create({withCredentials:true});

const VIEW_DAILY_STATS_PATH = "http://localhost:8080/view_daily_statistics"
const VIEW_GENERAL_STATS_PATH = "http://localhost:8080/view_general_statistics"
const VIEW_ALL_DAILY_STATS_PATH = "http://localhost:8080/view_all_daily_statistics"

import {path} from "./Path"
import axios from '../assets/AxiosInstance';

const VIEW_STATS_PATH = path+"view_statistics"



export class StatisticsApi {

    view_daily_statistics(){
        return axios.get(VIEW_DAILY_STATS_PATH)
                .then(res => {
                    console.log(res);
                    return res.data;
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
        
    }

    view_general_statistics(){
        return axios.get(VIEW_GENERAL_STATS_PATH)
                .then(res => {
                    console.log(res);
                    return res.data;
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
        
    }

    view_all_daily_statistics(){
        return axios.get(VIEW_ALL_DAILY_STATS_PATH)
                .then(res => {
                    console.log(res);
                    return res.data;
                })
                .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
        
    }

}