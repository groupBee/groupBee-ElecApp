package groupbee.elec_app.approve.repository;

import groupbee.elec_app.approve.data.ElecApp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElecAppRepository extends MongoRepository<ElecApp,String> {


    // writer 이름으로 문서 찾기
    List<ElecApp> findByWriterOrderByApproveType(String writer);

    /*	- 로그인된 회원아이디가 approver 1,2,3에 포함될때 리스트에서 문서 결재 상태로 찾기(count)
			: 내가 1번 approver인데 문서 상태가 1번일때 숫자 … (count)
			: 내가 1번 approver인데 문서 상태가 1이상일때 숫자
			: 내가 1번 approver인데 문서 상태가 3이상일때 숫자*/

    // approver 이름으로 문서 찾기
    @Query("{ '$or': [ { 'firstApprover': ?0 }, { 'secondApprover': ?0 }, { 'thirdApprover': ?0 } ] }")
    List<ElecApp> findByAnyApprover(String memberId);


}
