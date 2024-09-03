package groupbee.elec_app.approve.feign;

import groupbee.elec_app.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "employee", url = "${FEIGN_BASE_URL}", configuration = FeignConfig.class)
public interface EmployeeFeignClient {
    @GetMapping("/api/employee/info")
    Map<String,Object> getUserInfo();

    @PostMapping("/api/hr/info")
    Map<String,Object> getEmployeeInfo();

    @PostMapping("/api/hr/info/id")
    Map<String,Object> getEmployeeIdByIdNumber(@RequestParam String idNumber);


}
