package gotcha.server.Service.API;

import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Utils.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AdminAPI {

    Response view_all_open_questions(String admin_id);
    Response answer_user_question(int question_id, String answer, String admin_id);
    Response send_message_to_all_users(String message, String admin_id);

    Response view_rides(String admin_id);

    Response view_statistics(String admin_id);

    Response view_advertisements(String admin_id);
    Response add_advertisement(LocalDateTime final_date, String owner, String message, String photo, String url, String admin_id);
    Response delete_advertisement(int advertise_id, String admin_id);

    Response view_awards(String admin_id);
    Response add_award(String admin_id);
    Response delete_award(int award_id, String admin_id);

    Response view_admins(String admin_id);
    Response add_admin(String user_email, String user_password, String admin_id, String phoneNumber, LocalDate birthDay, String gender, String firstName, String lastName);
    Response delete_admin(String user_email, String admin_id);

    Response view_users(String admin_id);
    Response edit_user(String user_email, String admin_id);
    Response delete_user(String user_email, String admin_id);
}
