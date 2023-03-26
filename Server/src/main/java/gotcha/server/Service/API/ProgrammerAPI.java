package gotcha.server.Service.API;

import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Service.UserContext;
import gotcha.server.Utils.Formula;
import gotcha.server.Utils.Response;

import java.util.Dictionary;

public interface ProgrammerAPI {

    Response set_server_config(UserContext userContext);
    Response set_rp_config(UserContext userContext);

    Response view_error_logger(UserContext userContext);
    Response view_system_logger(UserContext userContext);
    Response view_server_logger(UserContext userContext);

    Response reset(UserContext userContext);
    Response shut_down(UserContext userContext);

    // admin or programmer?

//    Response update_user_rate_tables(Dictionary<String, Dictionary<Integer, Integer>> tables, UserContext userContext);
//    Response update_hazard_formula(HazardType type, Formula formula, UserContext userContext);


}
