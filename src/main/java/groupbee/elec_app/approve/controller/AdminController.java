package groupbee.elec_app.approve.controller;

import groupbee.elec_app.approve.data.ElecApp;
import groupbee.elec_app.approve.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    private AdminService adminService;

    //관리자페이지
    //전체 리스트 조회
    @GetMapping("/admin/elecapp/list")
    public List<ElecApp> getAdminElecApps() {
        return adminService.getAdminElecApps();
    }

    //삭제
    @GetMapping("/admin/elecapp/delete")
    public void deleteAdmainApp(@RequestParam String id) {
        adminService.deleteAdminElecApp(id);
    }

    //디테일 조회
    @GetMapping("/admin/elecapp/detail")
    public ElecApp getAdminElecAppDetail(@RequestParam String id) {
        return adminService.getAdminElecAppDetail(id);
    }

}
