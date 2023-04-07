import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
import {path} from "./Path"


const VIEW_ALL_OPEN_QUESTIONS_PATH = path+"view_all_open_questions"
const ANSWER_USER_QUESTION_PATH = path+"answer_user_question"
const SEND_MESSAGE_TO_ALL_USERS_PATH = path+"send_message_to_all_users"


const instance = require('axios');

export class QuestionsApi {

    view_all_open_questions() {
        return instance.get(VIEW_ALL_OPEN_QUESTIONS_PATH,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    answer_question(question_id, answer) {
        return instance.get(ANSWER_USER_QUESTION_PATH,
        {
            params:{question_id: question_id, answer: answer}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    send_message_to_all_users(message) {
        return instance.get(SEND_MESSAGE_TO_ALL_USERS_PATH,
        {
            params:{message: message}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }


}