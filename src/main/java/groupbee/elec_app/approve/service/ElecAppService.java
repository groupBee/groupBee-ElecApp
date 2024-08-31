package groupbee.elec_app.approve.service;

import groupbee.elec_app.approve.data.ElecApp;
import groupbee.elec_app.approve.repository.ElecAppRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

        // 우선 해당 파라미터값에 id가 포함되어있으면 아가 테이블 가서 삭제 로직
        if (elecApp.getId() != null) {
            String existingId = elecApp.getId();
            switch (elecApp.getAppDocType()) {
                case 0:
                    mongoTemplate.remove(new Query(Criteria.where("elec_id").is(existingId)), "intent");
                    break;
                case 1:
                    mongoTemplate.remove(new Query(Criteria.where("elec_id").is(existingId)), "vacation");
                    break;
                case 2:
                    mongoTemplate.remove(new Query(Criteria.where("elec_id").is(existingId)), "expend");
                    break;
                case 3:
                    String collectionName = elecApp.getAdditionalFields().get("type").toString();
                    mongoTemplate.remove(new Query(Criteria.where("elec_id").is(existingId)), collectionName);
                    break;
                default:
                    System.out.println("Unsupported document type for deletion");
                    break;
            }
        }

        // 기본 테이블 저장
        ElecApp savedElecApp = repository.save(elecApp);
        // 저장 후 id 가져오기
        String id = savedElecApp.getId();
        System.out.println(id);

        // 타입에 따른 추가 테이블 저장
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
                    break;
                default:
                    System.out.println("Unsupported document type");
                    break;
            }

            return "success";
        } else {
            return "fail";
        }
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

    //문서아이디로 디테일 구하기
    public ElecApp findByID(String elecAppId){
        return repository.findById(elecAppId).get();
    }

    //로그인된 아이디가 올린 결재 리스트 구하기
    public List<ElecApp> findByElecId(String writer){
        return repository.findByWriterOrderByApproveType(writer);

    }

        /*
    	- 로그인된 회원아이디가 approver 1,2,3에 포함될때 리스트에서 문서 결재 상태로 찾기(count)
			: 내가 1번 approver인데 문서 상태가 1번일때 숫자 … (count)
			: 내가 1번 approver인데 문서 상태가 1이상일때 숫자
			: 내가 1번 approver인데 문서 상태가 3이상일때 숫자
    */
        public Map<String, Integer> countByApproverAndStatus(String memberId) {
            // memberId가 이미 문자열이라면, 추가적인 변환은 필요하지 않습니다.
            List<ElecApp> list = repository.findByAnyApprover(memberId);

            Map<String, Integer> countMap = new HashMap<>();
            int readyCount = 0;
            int ingCount = 0;
            int doneCount = 0;

            for (ElecApp app : list) {
                if (memberId.equals(app.getFirstApprover())) {
                    if (app.getApproveType() == 1) {
                        ingCount++;
                    } else {
                        doneCount++;
                    }
                } else if (memberId.equals(app.getSecondApprover())) {
                    if (app.getApproveType()!=0&&app.getApproveType() < 2) {
                        readyCount++;
                    } else if (app.getApproveType() == 2) {
                        ingCount++;
                    } else {
                        doneCount++;
                    }
                } else if (memberId.equals(app.getThirdApprover())) {
                    if (app.getApproveType()!=0&&app.getApproveType() < 3) {
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

    //결재 상태가 어떻든 내가 온 문서 전체 리스트 반환
    public List<ElecApp> getAllReceived (String memberId){
            List<ElecApp> list = repository.findByAnyApprover(memberId);
            return list;
    }
// 결재 상태에 따른 문서 리스트 반환(기본정렬 order 안보내면 최신순 writer의 department, appdoctype, position별로 나열 가능)
public List<ElecApp> getElecAppsByApproverAndStatus(String memberId, String status, String order) {
    List<ElecApp> list = repository.findByAnyApprover(memberId);

    if (list == null || list.isEmpty()) {
        return List.of(); // 리스트가 비어있거나 null일 경우 빈 리스트 반환
    }

    // 필터링 및 상태 라벨 설정 (approveStatus가 1인 경우를 제외)
    List<ElecApp> filteredList = list.stream()
            .filter(app -> app.getApproveStatus() != 1)  // approveStatus가 1인 것을 제외 (임시저장 제외)
            .filter(app -> shouldInclude(app, memberId, status))
            .filter(app -> {
                // approveType이 1일 때 memberId와 thirdApprover가 같지 않은 경우만 포함 (아직 전 승인자가 승인을 하지 않았을때)
                if (app.getApproveType() == 1) {
                    return !memberId.equals(app.getThirdApprover());
                }
                return true;
            })
            .map(app -> {
                String statusLabel = determineStatusLabel(app, memberId, status);
                app.getAdditionalFields().put("status", statusLabel);
                return app;
            })
            .sorted(getComparator(order))  // 정렬
            .collect(Collectors.toList());
    System.out.println(filteredList);
    return filteredList;
}

    // 문서를 포함할지 결정하는 로직 (필터링 로직)
    private boolean shouldInclude(ElecApp app, String memberId, String status) {
        // writer가 memberId와 같으면서, firstApprover도 memberId와 같은 경우, 리스트에서 제외
        if (app.getWriter().equals(memberId) && app.getFirstApprover().equals(memberId)) {
            return false;
        }

        return filterByStatus(app, memberId, status);
    }

    // 상태에 따른 필터링 로직
    private boolean filterByStatus(ElecApp app, String memberId, String status) {
        if (status.equalsIgnoreCase("all")) {
            return true; // "all"일 경우 필터링을 건너뜀
        }

        if (status.equalsIgnoreCase("rejected")) {
            return isRejected(app, memberId); // 반려 상태만 필터링
        } else {
            return !isRejected(app, memberId) && (
                    (status.equalsIgnoreCase("ready") && isReady(app, memberId)) ||
                            (status.equalsIgnoreCase("ing") && isInProgress(app, memberId)) ||
                            (status.equalsIgnoreCase("done") && isDone(app, memberId))
            );
        }
    }

    // 상태 라벨 결정 로직
    private String determineStatusLabel(ElecApp app, String memberId, String status) {
        if (isRejected(app, memberId)) {
            return "반려";
        } else if (isReady(app, memberId)) {
            return "결재 대기";
        } else if (isInProgress(app, memberId)) {
            return "결재 중";
        } else if (isDone(app, memberId)) {
            return "결재 완료";
        } else {
            return "알 수 없음"; // 이 부분은 절대로 실행되지 않도록 보장해야 합니다.
        }
    }

    // 개별 상태 확인 메서드
    private boolean isRejected(ElecApp app, String memberId) {
        return (memberId.equals(app.getFirstApprover()) && app.getApproveType() == 0)
                || (memberId.equals(app.getSecondApprover()) && app.getApproveType() == 0)
                || (memberId.equals(app.getThirdApprover()) && app.getApproveType() == 0);
    }

    private boolean isReady(ElecApp app, String memberId) {
        return (memberId.equals(app.getSecondApprover()) && app.getApproveType() < 2)
                || (memberId.equals(app.getThirdApprover()) && app.getApproveType() < 3);
    }

    private boolean isInProgress(ElecApp app, String memberId) {
        return (memberId.equals(app.getFirstApprover()) && app.getApproveType() == 1)
                || (memberId.equals(app.getSecondApprover()) && app.getApproveType() == 2);
    }

    private boolean isDone(ElecApp app, String memberId) {
        return (memberId.equals(app.getFirstApprover()) && app.getApproveType() > 1)
                || (memberId.equals(app.getSecondApprover()) && app.getApproveType() > 2)
                || (memberId.equals(app.getThirdApprover()) && app.getApproveType() == 3);
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
            case "writer":
                return Comparator.comparing(ElecApp::getWriter, Comparator.nullsLast(Comparator.naturalOrder()));
            default:
                return Comparator.comparing(ElecApp::getWriteday, Comparator.nullsLast(Comparator.reverseOrder()));
        }
    }


}
