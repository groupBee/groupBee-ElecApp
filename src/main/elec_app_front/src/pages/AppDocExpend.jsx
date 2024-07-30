import {useState} from "react";

const AppDocExpend=({})=>{
    //요청일자
    const [requestDate,setRequestDate]=useState('');
    //지출유형
    const [expend_type,setExpend_type]=useState(0);
    const [title,setTitle]=useState('');
    const [content,setContent]=useState('');
    const [price,setPrice]=useState(0);
    const [note,setNote]=useState('');
    const [finalPrice,setFinalPrice]=useState(0);
    const [monetaryUnit,setMonetaryUnit]=useState(0);

    const changeunit = (e) => {
        setMonetaryUnit(parseInt(e.target.value));
    }
    return(
        <>
            <tr>
                <td>요청일자</td>
                <td>
                    <input type='date' value={requestDate} onChange={(e) => setRequestDate(e.target.value)}/>
                </td>
            </tr>
            <tr>
                <td>지출유형</td>
                <td>
                    <input type='number' value={expend_type} onChange={(e) => setExpend_type(e.target.value)}/>
                </td>
            </tr>
            <tr>
                <td>제목</td>
                <td>
                    <input type='text' value={title} onChange={(e) => setTitle(e.target.value)}/>
                </td>
            </tr>
            <tr>
                <td>지출내용</td>
                <td>
                    <input type='text' value={content} onChange={(e) => setContent(e.target.value)}/>
                </td>
            </tr>
            <tr>
                <td>금액</td>
                <td>
                    <input type='number' value={price} onChange={(e) => setPrice(e.target.value)}/>
                </td>
            </tr>
            <tr>
                <td>비고</td>
                <td>
                    <input type='text' value={note} onChange={(e) => setNote(e.target.value)}/>
                </td>
            </tr>
            <tr>
                <td>최종금액</td>
                <td>
                    <input type='number' value={finalPrice} onChange={(e) => setFinalPrice(e.target.value)}/>
                    <select defaultValue={monetaryUnit} onChange={changeunit}>
                        <option value={0}>원</option>
                        <option value={1}>달러</option>
                        <option value={2}>엔</option>
                    </select>
                </td>
            </tr>
        </>
    )
}
export default AppDocExpend;