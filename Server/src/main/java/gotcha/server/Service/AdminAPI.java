package gotcha.server.Service;

import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Utils.Response;

public interface AdminAPI {

    Response view_all_open_questions();
    Response answer_user_question(int question_id, String answer, Admin admin);
    Response send_message_to_all_users(String message);

    Response view_rides();

    Response view_statistics();

    Response view_awards();
    Response add_award();
    Response delete_award();

    Response view_admins();
    Response add_admin();
    Response delete_admin();

    Response view_users();
    Response edit_user();
    Response delete_user();
}
