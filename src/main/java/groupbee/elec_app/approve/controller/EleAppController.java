package groupbee.elec_app.approve.controller;


import groupbee.elec_app.approve.data.ElecApp;
import groupbee.elec_app.approve.feign.EmployeeFeignClient;
import groupbee.elec_app.approve.service.ElecAppService;
import groupbee.elec_app.service.minio.MinioService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class EleAppController {

    final private ElecAppService elecAppService;
    final private MinioService minioService;
    final private RedisTemplate<String, Object> redisTemplate;
    final private EmployeeFeignClient employeeFeignClient;

    //****작성****

    //*** 작성하기전 로그인 된 정보 넣기 ***
    @GetMapping("/elecapp/getinfo")
    public Map<String,Object> getMemberInfo(){
        Map<String, Object> map = new HashMap<>();

        // Feign 클라이언트를 통해 데이터를 받아옵니다.
        Map<String, Object> userInfo = employeeFeignClient.getUserInfo();
        String name="";
        String position="";
        String departmentName="";
//        if(userInfo.get("data")!=null) {
            // "data" 필드에서 필요한 정보를 추출합니다.
            Map<String, Object> data = (Map<String, Object>) userInfo.get("data");
            // 필요한 정보 추출
            name = (String) data.get("name");
            position = (String) data.get("position");
            departmentName = (String) data.get("departmentName");
//        }else   {
//            name="손가원";
//            position="사장";
//            departmentName="인사부";}
        //테스트코드 없으면 바꿔도됩

        // map에 정보 담기
        map.put("name", name);
        map.put("position", position);
        map.put("departmentName", departmentName);

        return map;
    }

    @PostMapping("/elecapp/create")
    public String saveEleApp(@RequestBody ElecApp elecApp){
        System.out.println("department>>>" + elecApp.getDepartment());
        System.out.println("writer>>>" + elecApp.getWriter());
        System.out.println("firstApprover>>>" + elecApp.getFirstApprover());
        System.out.println("additionalFields>>>" + elecApp.getAdditionalFields());
        elecAppService.save(elecApp);
        return "success";
    }
    //전자결재 파일 업로드
    @PostMapping("/elecapp/uploadfile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = minioService.uploadFile("groupbee", "elec_app", file);
        return fileName;
    }

    //****변경****

    //문서 상태 (임시저장인지 아닌지)변경하기
    @PostMapping("/elecapp/chageAppStatus")
    public  String chageAppStatus(@RequestParam String elecAppId, @RequestParam int approve_status) {
        return elecAppService.chageAppStatus(elecAppId, approve_status);

    }
    //문서 승인상태 바꾸기 (몇번째 승인자까지 갔느냐)
    @PostMapping("/elecapp/chageAppType")
    public String chageAppType(@RequestBody Map<String, String> request) {
        String elecAppId = request.get("elecAppId");
        return elecAppService.chageAppType(elecAppId);
    }

    //문서 거절사유 받고 문서상태 거절로 바꾸기
    @PostMapping("/elecapp/rejection")
    public String rejection(@RequestParam String elecAppId, @RequestParam String rejectionReason) {

        ElecApp elecApp=elecAppService.findByID(elecAppId);
        elecApp.setApproveType(0);
        elecAppService.save(elecApp);

        return elecAppService.appRejection(elecAppId, rejectionReason);
    }

    //****조회****

    //문서 아이디로 디테일 조회하기
    @GetMapping("/elecapp/findById")
    public ElecApp findById(@RequestParam String elecAppId){
        return elecAppService.findByID(elecAppId);
    }

    //로그인된 아이디가 올린 결재 리스트 구하기
    @PostMapping("/elecapp/sentapp")
    public List<ElecApp> findByWriter(@RequestBody Map<String, String> requestData) {
        String writer = requestData.get("writer");
        return elecAppService.findByElecId(writer);
    }

    /*
    	- 로그인된 회원아이디가 approver 1,2,3에 포함될때 리스트에서 문서 결재 상태로 찾기(count)
			: 내가 1번 approver인데 문서 상태가 1번일때 숫자 … (count)
			: 내가 1번 approver인데 문서 상태가 1이상일때 숫자
			: 내가 1번 approver인데 문서 상태가 3이상일때 숫자

			memberId가
    */

    // 결재 상태에 따른 문서 리스트 반환
    // 결재 상태에 따른 문서 리스트 반환(기본정렬 order안보내면 최신순 writer의 department,appdoctype,position별로 나열 가능
    @GetMapping("/elecapp/status")
    public List<ElecApp> getElecAppsByStatus(
            @RequestParam String memberId,
            @RequestParam String status,
            @RequestParam(required = false, defaultValue = "all") String order) {
        return elecAppService.getElecAppsByApproverAndStatus(memberId, status, order);
    }
    //문서상태 전체 리스트 반환
    @GetMapping("/elecapp/allreceived")
    public List<ElecApp> getElecAppsReceived(@RequestParam String memberId) {
        return elecAppService.getAllReceived(memberId);
    }
}
