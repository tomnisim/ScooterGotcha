package gotcha.server.Service.API;

import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Utils.Formula;
import gotcha.server.Utils.Response;

import java.util.Dictionary;

public interface ProgrammerAPI {

    Response set_server_config(int session_id);
    Response set_rp_config(int session_id);

    Response view_error_logger(int session_id);
    Response view_system_logger(int session_id);
    Response view_server_logger(int session_id);

    Response reset(int session_id);
    Response shut_down(int session_id);

    // admin or programmer?

//    Response update_user_rate_tables(Dictionary<String, Dictionary<Integer, Integer>> tables, int session_id);
//    Response update_hazard_formula(HazardType type, Formula formula, int session_id);


}
