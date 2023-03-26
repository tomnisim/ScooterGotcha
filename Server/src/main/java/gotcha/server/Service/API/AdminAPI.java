package gotcha.server.Service.API;

import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Service.UserContext;
import gotcha.server.Utils.Response;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AdminAPI {

    Response view_all_open_questions(@SessionAttribute("userContext") UserContext userContext);
    Response answer_user_question(int question_id, String answer, @SessionAttribute("userContext") UserContext userContext);
    Response send_message_to_all_users(String message, @SessionAttribute("userContext") UserContext userContext);

    Response view_rides(@SessionAttribute("userContext") UserContext userContext);

    Response view_statistics(@SessionAttribute("userContext") UserContext userContext);

    Response view_advertisements(@SessionAttribute("userContext") UserContext userContext);
    Response add_advertisement(LocalDateTime final_date, String owner, String message, String photo, String url,@SessionAttribute("userContext") UserContext userContext);
    Response delete_advertisement(int advertise_id,@SessionAttribute("userContext") UserContext userContext);

    Response view_awards(@SessionAttribute("userContext") UserContext userContext);
    Response add_award(@SessionAttribute("userContext") UserContext userContext, String[] emails, String award);

    Response view_admins( @SessionAttribute("userContext") UserContext userContext);
    Response add_admin(String user_email, String user_password, String phoneNumber, LocalDate birthDay, String gender, @SessionAttribute("userContext") UserContext userContext);
    Response delete_admin(String user_email, @SessionAttribute("userContext") UserContext userContext);

    Response view_users(@SessionAttribute("userContext") UserContext userContext);
    Response delete_user(String user_email, @SessionAttribute("userContext") UserContext userContext);

}
