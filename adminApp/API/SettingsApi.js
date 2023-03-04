import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
import {session_id} from '../components/LoginWindow'

const RESET_PATH = "http://localhost:8080/reset"
const SHUT_DOWN_PATH = "http://localhost:8080/shut_down"
const VIEW_SERVER_LOGGER_PATH = "http://localhost:8080/view_server_logger"
const VIEW_ERROR_LOGGER_PATH = "http://localhost:8080/view_error_logger"
const VIEW_SYSTEM_LOGGER_PATH = "http://localhost:8080/view_system_logger"
const SET_RP_CONFIG = "http://localhost:8080/set_rp_config"
const SET_SERVER_CONFIG = "http://localhost:8080/set_server_config"


const instance = require('axios');

export class SettingsApi {



    reset() {
        return instance.get(RESET_PATH,
        {
            params:{session_id : session_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    shut_down() {
        return instance.get(SHUT_DOWN_PATH,
        {
            params:{session_id : session_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    view_server_logger() {
        return instance.get(VIEW_SERVER_LOGGER_PATH,
        {
            params:{session_id : session_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    view_error_logger() {
        return instance.get(VIEW_ERROR_LOGGER_PATH,
        {
            params:{session_id : session_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    view_system_logger() {
        return instance.get(VIEW_SYSTEM_LOGGER_PATH,
        {
            params:{session_id : session_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    set_rp_config() {
        return instance.get(SET_RP_CONFIG,
        {
            params:{session_id : session_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    set_server_config() {
        return instance.get(SET_SERVER_CONFIG,
        {
            params:{session_id : session_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }


}