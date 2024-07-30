import {useState} from "react";

const AppDocExpend=({handleAdditionalFieldChange})=>{
    //요청일자
    const [requestDate,setRequestDate]=useState('');
    //지출유형
    const [expendType,setExpendType]=useState(0);
    const [title,setTitle]=useState('');
    const [content,setContent]=useState('');
    const [price,setPrice]=useState(0);
    const [note,setNote]=useState('');
    const [finalPrice,setFinalPrice]=useState(0);
    const [monetaryUnit,setMonetaryUnit]=useState(0);

    const handleRequestDateChange = (e) => {
        setRequestDate(e.target.value);
        handleAdditionalFieldChange("requestDate", e.target.value);
    };

    const handleExpendTypeChange = (e) => {
        setExpendType(e.target.value);
        handleAdditionalFieldChange("expendType", e.target.value);
    };

    const handleTitleChange = (e) => {
        setTitle(e.target.value);
        handleAdditionalFieldChange("title", e.target.value);
    };

    const handleContentChange = (e) => {
        setContent(e.target.value);
        handleAdditionalFieldChange("content", e.target.value);
    };

    const handlePriceChange = (e) => {
        setPrice(e.target.value);
        handleAdditionalFieldChange("price", e.target.value);
    };

    const handleNoteChange = (e) => {
        setNote(e.target.value);
        handleAdditionalFieldChange("note", e.target.value);
    };

    const handleFinalPriceChange = (e) => {
        setFinalPrice(e.target.value);
        handleAdditionalFieldChange("finalPrice", e.target.value);
    };

    const handleMonetaryUnitChange = (e) => {
        setMonetaryUnit(parseInt(e.target.value));
        handleAdditionalFieldChange("monetaryUnit", e.target.value);
    }
    return(
        <>
            <tr>
                <td>요청일자</td>
                <td>
                    <input type='date' value={requestDate} name='requestDate' onChange={handleRequestDateChange}/>
                </td>
            </tr>
            <tr>
                <td>지출유형</td>
                <td>
                    <input type='number' value={expendType} name='expend_type' onChange={handleExpendTypeChange}/>
                </td>
            </tr>
            <tr>
                <td>제목</td>
                <td>
                    <input type='text' value={title} name='title' onChange={handleTitleChange}/>
                </td>
            </tr>
            <tr>
                <td>지출내용</td>
                <td>
                    <input type='text' value={content} name='content' onChange={handleContentChange}/>
                </td>
            </tr>
            <tr>
                <td>금액</td>
                <td>
                    <input type='number' value={price} name='price' onChange={handlePriceChange}/>
                </td>
            </tr>
            <tr>
                <td>비고</td>
                <td>
                    <input type='text' value={note} name='note' onChange={handleNoteChange}/>
                </td>
            </tr>
            <tr>
                <td>최종금액</td>
                <td>
                    <input type='number' value={finalPrice} name='finalPrice' onChange={handleFinalPriceChange}/>
                    <select defaultValue={monetaryUnit} onChange={handleMonetaryUnitChange} name='monetaryUnit'>
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