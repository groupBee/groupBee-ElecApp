
const List = ({appList}) => {
        

    return (
        <table className="table table-bordered">
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
                
                    {   appList &&
                        appList.map((item, idx) => (
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
                                </td>
                                <td>
                                    {
                                        


                                    }
                               </td>
                            </tr>
                        ))
                    }
                
                
            </tbody>
        </table>
    );
}

export default List;
