package groupbee.elec_app.approve.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Builder
@AllArgsConstructor
@Data
@Document("elec_app")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})//오류가 날 경우 추가 불필요한 직렬화를 막기위한 어노테이션
public class ElecApp {

    @Id
    private String id;

    private String firstApprover;

    private String secondApprover;

    private String thirdApprover;

    private String rejectionReason;
    //UUID 첨부파일
    private String attachedFile;
    //원본파일명
    private String originalFile;

    private String writer;
    // 임시저장 : 1, 저장(작성완료) : 0
    private int approveStatus;
    // 현재 결재 상황 몇번째 승인자까지 갔는지
    private int approveType;
    // 보안등급
    private int level;

    //작성자 정보
    private String position;

    private String department;

    // 작성일
    private Date writeday;
    // 결재 기한 최종일
    private Date approveDate;

    // 결재문서 종류 0:품의서 1:휴가신청서 2:지출보고서
    private int appDocType;

    // 이어지는 테이블에 들어갈 양식
    private Map<String, Object> additionalFields;

    // Custom setter for writeday to prevent updates after initial set
    public void setWriteday(Date writeday) {
        if (this.writeday == null) {
            this.writeday = writeday;
        } else {
            throw new UnsupportedOperationException("writeday cannot be modified after initial set");
        }
    }
}
