package groupbee.elec_app.approve.repository;

import groupbee.elec_app.approve.data.ElecApp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElecAppRepository extends MongoRepository<ElecApp,String> {


}
