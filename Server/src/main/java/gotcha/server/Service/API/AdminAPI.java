package gotcha.server.Service.API;

import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Utils.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AdminAPI {

    Response view_all_open_questions(int session_id);
    Response answer_user_question(int question_id, String answer, int session_id);
    Response send_message_to_all_users(String message, int session_id);

    Response view_rides(int session_id);

    Response view_statistics(int session_id);

    Response view_advertisements(int session_id);
    Response add_advertisement(LocalDateTime final_date, String owner, String message, String photo, String url, int session_id);
    Response delete_advertisement(int advertise_id, int session_id);

    Response view_awards(int session_id);
    Response add_award(int session_id, String[] emails, String award);


    Response view_admins(int session_id);
    Response add_admin(String user_email, String user_password, int session_id, String phoneNumber, String birthDay, String gender);
    Response delete_admin(String user_email, int session_id);

    Response view_users(int session_id);
    Response delete_user(String user_email, int session_id);
}
