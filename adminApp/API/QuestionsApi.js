import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"

const VIEW_ALL_OPEN_QUESTIONS_PATH = "http://localhost:8080/view_all_open_questions"
const ANSWER_USER_QUESTION_PATH = "http://localhost:8080/answer_user_question"
const SEND_MESSAGE_TO_ALL_USERS_PATH = "http://localhost:8080/send_message_to_all_users"

import {session_id} from '../components/LoginWindow'

const instance = require('axios');

export class QuestionsApi {

    view_all_open_questions() {
        return instance.get(VIEW_ALL_OPEN_QUESTIONS_PATH,
        {
            params:{session_id : session_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    answer_question(question_id, answer) {
        return instance.get(ANSWER_USER_QUESTION_PATH,
        {
            params:{question_id: question_id, answer: answer, session_id: session_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    send_message_to_all_users(message) {
        return instance.get(SEND_MESSAGE_TO_ALL_USERS_PATH,
        {
            params:{message: message, session_id : session_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }


}