import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"

const VIEW_ALL_OPEN_QUESTIONS_PATH = "http://localhost:8080/view_all_open_questions"
const ANSWER_USER_QUESTION_PATH = "http://localhost:8080/answer_user_question"
const SEND_MESSAGE_TO_ALL_USERS_PATH = "http://localhost:8080/send_message_to_all_users"


const instance = require('axios');

export class QuestionsApi {

    view_all_open_questions(admin_id) {
        return instance.get(VIEW_ALL_OPEN_QUESTIONS_PATH,
        {
            params:{admin_id : admin_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    answer_question(question_id, answer, admin_id) {
        return instance.get(ANSWER_USER_QUESTION_PATH,
        {
            params:{question_id: question_id, answer: answer, admin_id: admin_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));
    }

    send_message_to_all_users(message, admin_id) {
        return instance.get(SEND_MESSAGE_TO_ALL_USERS_PATH,
        {
            params:{message: message, admin_id : admin_id}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }


}