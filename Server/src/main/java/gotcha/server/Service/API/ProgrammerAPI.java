package gotcha.server.Service.API;

import gotcha.server.Domain.HazardsModule.HazardType;
import gotcha.server.Utils.Formula;
import gotcha.server.Utils.Response;

import java.util.Dictionary;

public interface ProgrammerAPI {

    Response set_server_config();
    Response set_rp_config();

    Response view_error_logger();
    Response view_system_logger();
    Response view_server_logger();

    Response reset();
    Response shout_down();

    // admin or programmer?

    Response update_user_rate_tables(Dictionary<String, Dictionary<Integer, Integer>> tables);
    Response update_hazard_formula(HazardType type, Formula formula);


}
