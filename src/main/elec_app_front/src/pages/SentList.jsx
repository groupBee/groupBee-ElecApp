import axios from "axios";
import { useEffect, useState } from "react";

const SentList = () => {
    const [list, setList] = useState([]);
    const [writer, setWriter] = useState('손가원');

    const getList = () => {
        axios.post("/elecapp/sentapp", { writer: writer })
            .then(res => {
                setList(res.data);
            })
            .catch(err => {
                console.error('데이터를 가져오는 중 오류 발생:', err);
            });
    }

    useEffect(() => {
        getList();
    }, [writer]);

    return (
        <div>
            <h1>문서 리스트</h1>
            <input 
                type="text" 
                value={writer} 
                onChange={(e) => setWriter(e.target.value)} 
                placeholder="작성자 이름을 입력하세요" 
            />
            <button onClick={getList}>리스트 가져오기</button>
            <table className="table table-bordered">
                <caption></caption>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>종류</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>부서</th>
                        <th>작성일</th>
                        <th>상태</th>
                    </tr>
                </thead>
                <tbody>
                    {list &&
                        list.map((item, idx) => (
                            <tr key={idx}>
                                <td>{idx + 1}</td>
                                <td>
                                    {item.appDocType === 0 ? '품의서' :
                                     item.appDocType === 1 ? '휴가신청서' :
                                     item.appDocType === 2 ? '지출보고서' : ''}
                                </td>
                                <th>
                                    {
                                        item.additionalFields.title?item.additionalFields.title:'휴가신청서'
                                    }
                                </th>
                                <td>{item.writer}</td>
                                <td>{item.department}</td>
                                <td>
                                    {
                                       item.writeday.substring(0,10)
                                    }
                                </td> {/* 유효한 날짜 값이 없을 경우 'N/A' 표시 */}
                                <td>{item.approveType==0?'반려':item.approveType==1?'제출완료':item.approveType==2?'진행중':'결재완료'}</td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
        </div>
    )
}
export default SentList;
