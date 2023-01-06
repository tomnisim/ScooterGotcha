package gotcha.server.Service.API;

import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Utils.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AdminAPI {

    Response view_all_open_questions(int admin_id);
    Response answer_user_question(int question_id, String answer, int admin_id);
    Response send_message_to_all_users(String message, int admin_id);

    Response view_rides(int admin_id);

    Response view_statistics(int admin_id);

    Response view_advertisements(int admin_id);
    Response add_advertisement(LocalDateTime final_date, String owner, String message, String photo, String url, int admin_id);
    Response delete_advertisement(int advertise_id, int admin_id);

    Response view_awards(int admin_id);
    Response add_award(int admin_id);
    Response delete_award(int award_id, int admin_id);

    Response view_admins(int admin_id);
    Response add_admin(String user_email, String user_password, int admin_id, String phoneNumber, String birthDay, String gender);
    Response delete_admin(String user_email, int admin_id);

    Response view_users(int admin_id);
    Response edit_user(String user_email, int admin_id);
    Response delete_user(String user_email, int admin_id);
}
