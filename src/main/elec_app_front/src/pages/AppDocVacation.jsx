import { useState } from "react";

const AppDocVacation=()=>{
    const [days,setDays]=useState(0);
    const [start,setStart]=useState('');
    const [end,setEnd]=useState('');
    const [day,setDay]=useState('');
    const [type,setType]=useState('');
    return(
        <>       
            <tr>
                <th>휴가 일수</th>
                <td>
                    <input type="number" value={days} onChange={(e)=>setDays(e.target.value)}/>
                </td>
            </tr>
            {days==1?
                    <tr>
                    <td>날짜</td>
                    <td><input type="date" value={day} name="day" onChange={(e)=>setDay(e.target.value)}/></td>
                    </tr>
            :days>1?
            <>
            <tr>
                <th>휴가 시작일</th>
                <td>
                    <input type="date" value={start} name="satrt" onChange={(e)=>setStart(e.target.value)}/>
                </td>
            </tr>
            <tr>
                <th>휴가 종료일</th>
                <td>
                    <input type="date" value={end} name="end" onChange={(e)=>setEnd(e.target.value)}/>
                </td>
            </tr>
            </>
            :''}
            <tr>
                <th>휴가 종류</th>
                <td>
                    <select value={type} onChange={(e)=>setType(e.target.value)}>
                        <option>반차</option>
                        <option>월차</option>
                        <option>병가</option>
                        <option>기타</option>
                    </select>
                </td>
            </tr>
        </>

    )
}
export default AppDocVacation;