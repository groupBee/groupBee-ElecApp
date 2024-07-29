package groupbee.elec_app.approve.repository;

import groupbee.elec_app.approve.data.ElecApp;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ElecAppRepository extends MongoRepository<ElecApp,String> {
}
