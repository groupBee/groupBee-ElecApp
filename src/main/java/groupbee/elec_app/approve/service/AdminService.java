package groupbee.elec_app.approve.service;

import groupbee.elec_app.approve.data.ElecApp;
import groupbee.elec_app.approve.repository.ElecAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final ElecAppRepository repository;
    private final MongoTemplate mongoTemplate;
    //관리자페이지
    //전체 리스트 조회
    public List<ElecApp> getAdminElecApps(){
        return repository.findAll();
    }

    //삭제
    public void deleteAdminElecApp(String id) {
        {
            Optional<ElecApp> optionalApp = repository.findById(id);
            int doctype = 3;
            if (optionalApp.isPresent()) {
                ElecApp app = optionalApp.get();
                doctype = app.getAppDocType();


                switch (doctype) {
                    case 0:
                        mongoTemplate.remove(new Query(Criteria.where("elec_id").is(id)), "intent");
                        break;
                    case 1:
                        mongoTemplate.remove(new Query(Criteria.where("elec_id").is(id)), "vacation");
                        break;
                    case 2:
                        mongoTemplate.remove(new Query(Criteria.where("elec_id").is(id)), "expend");
                        break;
                    default:
                        System.out.println("Unsupported document type for deletion");
                        break;
                }
            }

        }
        repository.deleteById(id);
    }

    //디테일 조회
    public ElecApp getAdminElecAppDetail (String id){
        return repository.findById(id).get();
    }
}
