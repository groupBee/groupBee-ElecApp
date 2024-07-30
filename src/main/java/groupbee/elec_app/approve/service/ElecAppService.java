package groupbee.elec_app.approve.service;

import groupbee.elec_app.approve.data.ElecApp;
import groupbee.elec_app.approve.repository.ElecAppRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ElecAppService {

    private final ElecAppRepository repository;
    private final MongoTemplate mongoTemplate;

    //elec_app 전체 출력
    public List<ElecApp> findAll() {
        return repository.findAll();
    }

    //elec_app 저장 && type에 따른 additionalField table에 저장
    public String save(ElecApp elecApp) {
        // 작성일 설정
        if (elecApp.getWriteday() == null) {
            elecApp.setWriteday(new Date());
        }
        System.out.println(elecApp.toString());

        // 기본 테이블 저장
        ElecApp savedElecApp = repository.save(elecApp);
        // 저장 후 id 가져오기
        String id = savedElecApp.getId();
        System.out.println(id);

        // 타입에 따른 저장
        if (savedElecApp.getAdditionalFields() != null) {
            Map<String, Object> additionalFields = new HashMap<>();
            additionalFields.put("elec_id", id);
            additionalFields.putAll(savedElecApp.getAdditionalFields());

            switch (savedElecApp.getAppDocType()) {
                case 0:
                    mongoTemplate.save(additionalFields, "intent");
                    break;
                case 1:
                    mongoTemplate.save(additionalFields, "vacation");
                    break;
                case 2:
                    mongoTemplate.save(additionalFields, "expend");
                    break;
                case 3:
                    mongoTemplate.save(additionalFields, additionalFields.get("type").toString());
                default:
                    System.out.println("Unsupported document type");
            }
            return "success";
        } else {
            return "fail";
        }
    }

    public List<Object> getElecAppDetails(String ele_id){
        List<Object> details = new ArrayList<>();



        return details;
    }

    //문서 상태 (임시저장인지 아닌지)변경하기
    public String chageAppStatus(String elecAppId, int approve_status){

        ElecApp elecApp=repository.findById(elecAppId).get();
        elecApp.setApproveStatus(approve_status);

        repository.save(elecApp);

        return "success";

    }

    //문서 승인상태 바꾸기 (몇번째 승인자까지 갔느냐)
    public String chageAppType(String elecAppId){
        ElecApp elecApp=repository.findById(elecAppId).get();
        elecApp.setApproveType(elecApp.getApproveType()+1);
        repository.save(elecApp);
        return "success";
    }

    //거절사유 입력받고 문서 상태 거절로 만들기
    public String appRejection(String elecAppId,String rejectionReason){
        ElecApp elecApp=repository.findById(elecAppId).get();
        elecApp.setRejectionReason(rejectionReason);
        repository.save(elecApp);
        return "success";
    }


    //****조회****

    //로그인된 아이디가 올린 결재 리스트 구하기
    public List<ElecApp> findByElecId(String writer){
        return repository.findByWriter(writer);

    }

        /*
    	- 로그인된 회원아이디가 approver 1,2,3에 포함될때 리스트에서 문서 결재 상태로 찾기(count)
			: 내가 1번 approver인데 문서 상태가 1번일때 숫자 … (count)
			: 내가 1번 approver인데 문서 상태가 1이상일때 숫자
			: 내가 1번 approver인데 문서 상태가 3이상일때 숫자
    */
        public Map<String, Integer> countByApproverAndStatus(String memberId) {
            List<ElecApp> list = repository.findByAnyApprover(memberId);
            Map<String, Integer> countMap = new HashMap<>();
            int readyCount = 0;
            int ingCount = 0;
            int doneCount = 0;

            for (ElecApp app : list) {
                if (memberId.equals(app.getFirstApprover())) {
                    if (app.getApproveType() < 1) {
                        readyCount++;
                    } else if (app.getApproveType() == 1) {
                        ingCount++;
                    } else {
                        doneCount++;
                    }
                } else if (memberId.equals(app.getSecondApprover())) {
                    if (app.getApproveType() < 2) {
                        readyCount++;
                    } else if (app.getApproveType() == 2) {
                        ingCount++;
                    } else {
                        doneCount++;
                    }
                } else if (memberId.equals(app.getThirdApprover())) {
                    if (app.getApproveType() < 3) {
                        readyCount++;
                    } else if (app.getApproveType() == 3) {
                        ingCount++;
                    } else {
                        doneCount++;
                    }
                }
            }

            countMap.put("readyCount", readyCount);
            countMap.put("ingCount", ingCount);
            countMap.put("doneCount", doneCount);
            return countMap;
        }


    // 결재 상태에 따른 문서 리스트 반환(기본정렬 order안보내면 최신순 writer의 department,appdoctype,position별로 나열 가능
    public List<ElecApp> getElecAppsByApproverAndStatus(String memberId, String status, String order) {
        List<ElecApp> list = repository.findByAnyApprover(memberId);

        if (list == null || list.isEmpty()) {
            return List.of(); // 리스트가 비어있거나 null일 경우 빈 리스트 반환
        }

        List<ElecApp> filteredList = list.stream()
                .filter(app -> {
                    switch (status.toLowerCase()) {
                        case "ready":
                            return (memberId.equals(app.getFirstApprover()) && app.getApproveType() < 1)
                                    || (memberId.equals(app.getSecondApprover()) && app.getApproveType() < 2)
                                    || (memberId.equals(app.getThirdApprover()) && app.getApproveType() < 3);
                        case "ing":
                            return (memberId.equals(app.getFirstApprover()) && app.getApproveType() == 1)
                                    || (memberId.equals(app.getSecondApprover()) && app.getApproveType() == 2)
                                    || (memberId.equals(app.getThirdApprover()) && app.getApproveType() == 3);
                        case "done":
                            return (memberId.equals(app.getFirstApprover()) && app.getApproveType() > 1)
                                    || (memberId.equals(app.getSecondApprover()) && app.getApproveType() > 2)
                                    || (memberId.equals(app.getThirdApprover()) && app.getApproveType() > 3);
                        default:
                            return false;
                    }
                })
                .sorted(getComparator(order))
                .collect(Collectors.toList());

        return filteredList;
    }
    //리스트 나열 order
    private Comparator<ElecApp> getComparator(String order) {
        switch (order.toLowerCase()) {
            case "appdoctype":
                return Comparator.comparing(ElecApp::getAppDocType, Comparator.nullsLast(Comparator.naturalOrder()));
            case "position":
                return Comparator.comparing(ElecApp::getPosition, Comparator.nullsLast(Comparator.naturalOrder()));
            case "department":
                return Comparator.comparing(ElecApp::getDepartment, Comparator.nullsLast(Comparator.naturalOrder()));
            case "no":
            default:
                return Comparator.comparing(ElecApp::getWriteday, Comparator.nullsLast(Comparator.reverseOrder()));
        }
    }



}
