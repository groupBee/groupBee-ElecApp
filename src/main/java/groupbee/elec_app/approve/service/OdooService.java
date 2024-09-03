package groupbee.elec_app.approve.service;

import groupbee.elec_app.approve.data.ElecApp;
import groupbee.elec_app.approve.feign.EmployeeFeignClient;
import lombok.AllArgsConstructor;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Arrays.asList;

@AllArgsConstructor
@Service
public class OdooService {

    private final EmployeeFeignClient employeeFeignClient;

    private final String url = System.getenv("ODOO_URL");
    private final String db=System.getenv("ODOO_DB");
    private final String uid=System.getenv("ODOO_UID");
    private final String password=System.getenv("ODOO_PASSWORD");
    private final String username=System.getenv("ODOO_USERNAME");


    public String sendLeaveReport(ElecApp elecApp){
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
            Map<String,Object> getEmployeeIdByIdNumber = employeeFeignClient.getEmployeeIdByIdNumber(elecApp.getWriterIdNumber());
            XmlRpcClient client = new XmlRpcClient();
            client.setConfig(config);

            //userId
            List<Object> userIdList = (List<Object>) getEmployeeIdByIdNumber.get("user_id");
            int user_id=(int)userIdList.get(0);

            int holiday_status_id = 1;  // 휴가 유형 ID

            int employee_id =(int)getEmployeeIdByIdNumber.get("id");
            //company_id
            List<Object> companyIdList = (List<Object>) getEmployeeIdByIdNumber.get("company_id");
            int employee_company_id =(int)companyIdList.get(0) ;

            //department_id
            List<Object> department_id_list = (List<Object>) getEmployeeIdByIdNumber.get("department_id");
            int department_id =(int)department_id_list.get(0) ;
            int resource_calendar_id = 1;
            int create_uid = Integer.parseInt(uid);
            int write_uid = Integer.parseInt(uid);
            String private_name =(String)elecApp.getAdditionalFields().get("detail");
            String holiday_type = "employee"; // 개인 휴가 타입
            Object request_date_from = elecApp.getAdditionalFields().get("start"); // 휴가 시작일
            Object request_date_to = elecApp.getAdditionalFields().get("end");   // 휴가 종료일

            // Odoo에 전송할 데이터 준비
            Map<String, Object> leaveRequestData = new HashMap<>();
            leaveRequestData.put("user_id", user_id);
            leaveRequestData.put("holiday_status_id", holiday_status_id);
            leaveRequestData.put("employee_id", employee_id);
            leaveRequestData.put("employee_company_id", employee_company_id);
            leaveRequestData.put("department_id", department_id);
            leaveRequestData.put("resource_calendar_id", resource_calendar_id);
            leaveRequestData.put("create_uid", create_uid);
            leaveRequestData.put("write_uid", write_uid);
            leaveRequestData.put("name", private_name);  // 필드명 수정: 'private_name' -> 'name'
            leaveRequestData.put("holiday_type", holiday_type);
            leaveRequestData.put("request_date_from", request_date_from); // 휴가 시작일
            leaveRequestData.put("request_date_to", request_date_to);     // 휴가 종료일

            Object[] params = new Object[] {
                    db,
                    2, // user ID
                    password, // password
                    "hr.leave", // Odoo 모델 이름
                    "create", // 메서드 이름
                    new Object[] { leaveRequestData }
            };

            client.execute("execute_kw", params);
            System.out.println("Odoo 작동 성공");

        } catch (MalformedURLException e) {
            System.err.println("URL 형식이 잘못되었습니다: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Odoo 서버와의 통신 중 오류가 발생했습니다: " + e.getMessage());
            return e.getMessage();
        }
        return "휴가가 성공적으로 처리되었습니다";
    }

    public void sendExpenseReport(ElecApp elecApp) throws XmlRpcException, MalformedURLException, ParseException {
        List<Integer> totalPrice = new ArrayList<>();
        List<Integer> allHrExpense = new ArrayList<>();

        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);

        Map<String,Object> getEmployeeIdByIdNumber = employeeFeignClient.getEmployeeIdByIdNumber(elecApp.getWriterIdNumber());

        // requestDate를 문자열로 가져와 java.util.Date로 변환
        String requestDateStr = (String) elecApp.getAdditionalFields().get("requestDate");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date utilDate = formatter.parse(requestDateStr);

        int employee_id = (int)getEmployeeIdByIdNumber.get("id");

        // userId
        List<Object> userIdList = (List<Object>) getEmployeeIdByIdNumber.get("user_id");
        int user_id = (int)userIdList.get(0);
        System.out.println("getEmployeeIdByIdNumber" + getEmployeeIdByIdNumber);

        // company_id
        List<Object> companyIdList = (List<Object>) getEmployeeIdByIdNumber.get("company_id");
        int employee_company_id = (int)companyIdList.get(0);

        // department_id
        List<Object> departmentList = (List<Object>) getEmployeeIdByIdNumber.get("department_id");
        int department_id = (int)departmentList.get(0);

        System.out.println(elecApp.getAdditionalFields().size());
        int count = elecApp.getAdditionalFields().size();
        count = (count - 5) / 2; // 기본 컬럼 삭제 후, 가격과 제목 포함

        // Odoo에 전송할 데이터 준비
        for (int i = 0; i <= count; i++) {
            // 겹치는 데이터 정리
            Map<String, Object> expenseReportData = new HashMap<>();
            expenseReportData.put("employee_id", employee_id);
            expenseReportData.put("company_id", employee_company_id);
            //지출 종류
            int expendType = Integer.parseInt(elecApp.getAdditionalFields().get("expend_Type").toString());
            int productId;

            switch (expendType) {
                case 4:
                    productId = 1;
                    break;
                case 5:
                    productId = 2;
                    break;
                case 6:
                    productId = 3;
                    break;
                case 7:
                    productId = 4;
                    break;
                default:
                    productId = 6;
                    break;
            }

            expenseReportData.put("product_id", productId);

            expenseReportData.put("product_uom_id", 1);
            expenseReportData.put("account_id", 30);
            expenseReportData.put("create_uid", uid);
            expenseReportData.put("write_uid", uid);
            expenseReportData.put("state", "draft");
            expenseReportData.put("date", formatter.format(utilDate));  // 날짜를 String 형식으로 변환하여 전송
            expenseReportData.put("description", elecApp.getAdditionalFields().get("title"));
            expenseReportData.put("payment_mode", "own_account");
            expenseReportData.put("quantity", 1);
            expenseReportData.put("currency_id", 32); // 한화
            expenseReportData.put("name", elecApp.getAdditionalFields().get("details_" + i + "_content"));

            // 쉼표 제거 후 int로 변환
            String cleanedPriceStr = (elecApp.getAdditionalFields().get("details_" + i + "_price").toString()).replace(",", "");
            int price = Integer.parseInt(cleanedPriceStr);
            expenseReportData.put("total_amount", price);
            expenseReportData.put("total_amount_currency", price);

            // Odoo의 'hr.expense' 모델에 create 메서드 호출
            Object[] params = new Object[]{
                    db,
                    uid, // user ID
                    password, // password
                    "hr.expense", // Odoo 모델 이름
                    "create", // 메서드 이름
                    new Object[]{expenseReportData}
            };
            Integer expenseId = (Integer) client.execute("execute_kw", params);
            totalPrice.add(price);
            allHrExpense.add(expenseId);
        }

        int total_amount = totalPrice.stream().mapToInt(Integer::intValue).sum();

        // 지출 보고서 생성하기
        Map<String, Object> expenseSheetData = new HashMap<>();
        expenseSheetData.put("employee_id", employee_id);
        expenseSheetData.put("company_id", employee_company_id);
        expenseSheetData.put("department_id", department_id);
        expenseSheetData.put("currency_id", 32);
        expenseSheetData.put("employee_journal_id", 2);
        expenseSheetData.put("create_uid", uid);
        expenseSheetData.put("write_uid", uid);
        expenseSheetData.put("name", Integer.parseInt((String) elecApp.getAdditionalFields().get("expendType")) == 0 ? "자재비" : Integer.parseInt((String) elecApp.getAdditionalFields().get("expendType")) == 1 ? "배송비" : Integer.parseInt((String) elecApp.getAdditionalFields().get("expendType")) == 2 ? "교육비" : "기타");
        expenseSheetData.put("total_amount", total_amount);

        // 'hr.expense.sheet' 모델에 create 메서드 호출
        Object[] sheetParams = new Object[]{
                db,
                uid, // user ID
                password, // password
                "hr.expense.sheet", // Odoo 모델 이름
                "create", // 메서드 이름
                new Object[]{expenseSheetData}
        };
        Integer createdSheetId = (Integer) client.execute("execute_kw", sheetParams);

// 각 hr.expense에 대해 sheet_id 업데이트
        for (Integer expenseId : allHrExpense) {
            // hr.expense 모델의 write 메서드를 사용하여 sheet_id 업데이트
            Map<String, Object> updateData = new HashMap<>();
            updateData.put("sheet_id", createdSheetId);

            // 파라미터 배열 생성
            Object[] updateParams = new Object[]{
                    db,
                    uid, // user ID
                    password, // password
                    "hr.expense", // Odoo 모델 이름
                    "write", // 메서드 이름
                    asList(
                            Collections.singletonList(expenseId), // 수정할 레코드의 ID 리스트로 전달
                            new HashMap<String, Object>() {{ put("sheet_id", createdSheetId); }} // 수정할 데이터
                    )
            };
            client.execute("execute_kw", updateParams);
        }

    }

    public int getLeaveDays() throws MalformedURLException {
        int leaveDays = 0;

        // 사용자 ID로부터 직원 번호(employeeId) 가져오기
        Map<String, Object> employeeinfo = employeeFeignClient.getEmployeeInfo();

        // employeeIdNumber를 올바른 타입으로 처리
        int employeeIdNumber = Integer.parseInt(String.valueOf(employeeinfo.get("id")));
        System.out.println("employeeIdNumber: " + employeeIdNumber);
        // Odoo와의 연결 설정
        // Odoo와의 연결 설정
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);

        try {
            // "hr.leave.allocation" 모델에서 특정 employee_id에 해당하는 number_of_days 값을 가져오기 위한 검색 조건 설정
            Object[] searchParams = new Object[] {
                    db,
                    uid, // user ID
                    password, // password
                    "hr.leave.allocation", // Odoo 모델 이름
                    "search",
                    asList(asList(
                            asList("employee_id", "=", employeeIdNumber)))
            };

            // 검색 결과로 얻은 ID 목록
            List<Object> ids = asList((Object[]) client.execute("execute_kw", searchParams));

            if (!ids.isEmpty()) {
                // 검색된 ID로 "number_of_days" 필드 값 읽기
                Object[] readParams = new Object[] {
                        db,
                        uid, // user ID
                        password, // password
                        "hr.leave.allocation", // Odoo 모델 이름
                        "read",
                        asList(ids),
                        new HashMap<String, Object>() {{
                            put("fields", new String[]{"number_of_days"});
                        }}
                };

                // 서버에서 응답을 받음
                Object[] records = (Object[]) client.execute("execute_kw", readParams);

                // 모든 number_of_days 값을 합산
                for (Object recordObj : records) {
                    Map<String, Object> record = (Map<String, Object>) recordObj;
                    Object daysObj = record.get("number_of_days");
                    if (daysObj instanceof Double) {
                        leaveDays += ((Double) daysObj).intValue();
                    } else if (daysObj instanceof Integer) {
                        leaveDays += (Integer) daysObj;
                    } else {
                        throw new IllegalArgumentException("Unexpected type for number_of_days: " + daysObj.getClass().getName());
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Odoo 서버와의 통신 중 오류가 발생했습니다: " + e.getMessage());
        }

        return leaveDays;
    }

}