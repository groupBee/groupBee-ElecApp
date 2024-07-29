package groupbee.elec_app.approve.service;

import groupbee.elec_app.approve.data.ElecApp;
import groupbee.elec_app.approve.repository.ElecAppRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // 기본 테이블 저장
        ElecApp savedElecApp = repository.save(elecApp);
        // 저장 후 id 가져오기
        String id = savedElecApp.getId();

        // 타입에 따른 저장
        if (savedElecApp.getAdditionalFields() != null) {
            Map<String, Object> additionalFields = new HashMap<>();
            additionalFields.put("elec_id", id);
            additionalFields.putAll(savedElecApp.getAdditionalFields());

            switch (savedElecApp.getApp_doc_type()) {
                case 0:
                    mongoTemplate.save(additionalFields, "intent");
                    break;
                case 1:
                    mongoTemplate.save(additionalFields, "vacation");
                    break;
                case 2:
                    mongoTemplate.save(additionalFields, "expend");
                    break;
                default:
                    return "fail";
            }
            return "success";
        } else {
            return "fail";
        }
    }
}
