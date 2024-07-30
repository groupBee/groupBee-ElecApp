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
            <tr style={{fontSize:'23px'}}>
                <td>요청일자</td>
                <td colSpan={3}>
                    <input type='date' value={requestDate} name='requestDate' onChange={handleRequestDateChange}/>
                </td>
                <td>지출유형</td>
                <td>
                    <input type='number' value={expendType} name='expend_type' onChange={handleExpendTypeChange}/>
                </td>
                <td>최종금액</td>
                <td style={{width:'40px'}}>
                    <input type='number' value={finalPrice} name='finalPrice' onChange={handleFinalPriceChange}
                    style={{width:'70px'}}/>
                    <select defaultValue={monetaryUnit} onChange={handleMonetaryUnitChange} name='monetaryUnit'>
                        <option value={0}>원</option>
                        <option value={1}>달러</option>
                        <option value={2}>엔</option>
                    </select>
                </td>
            </tr>
            <tr  style={{fontSize:'23px'}}>
                <td>제목</td>
                <td colSpan={7}>
                    <input type='text' value={title} name='title' onChange={handleTitleChange}/>
                </td>
            </tr>
            <tr style={{fontSize:'23px'}}>
                <td rowSpan={10}>내역</td>
                <td colSpan={3} style={{height:'50px'}}>지출내용</td>
                <td colSpan={3}>금액</td>
                <td colSpan={2}>비고</td>
            </tr>
            <tr style={{fontSize:'23px'}}>
                <td colSpan={3} style={{height:'65px'}}>
                    <input type='text' value={content} name='content' onChange={handleContentChange}/>
                </td>
                <td colSpan={3}>
                    <input type='number' value={price} name='price' onChange={handlePriceChange}/>
                </td>
                <td colSpan={2}>
                    <input type='text' value={note} name='note' onChange={handleNoteChange}/>
                </td>
            </tr>
            <tr style={{fontSize:'23px'}}>
                <td colSpan={3} style={{height:'65px'}}>
                    <input type='text' value={content} name='content' onChange={handleContentChange}/>
                </td>
                <td colSpan={3}>
                    <input type='number' value={price} name='price' onChange={handlePriceChange}/>
                </td>
                <td colSpan={2}>
                    <input type='text' value={note} name='note' onChange={handleNoteChange}/>
                </td>
            </tr>
            <tr style={{fontSize:'23px'}}>
                <td colSpan={3} style={{height:'65px'}}>
                    <input type='text' value={content} name='content' onChange={handleContentChange}/>
                </td>
                <td colSpan={3}>
                    <input type='number' value={price} name='price' onChange={handlePriceChange}/>
                </td>
                <td colSpan={2}>
                    <input type='text' value={note} name='note' onChange={handleNoteChange}/>
                </td>
            </tr>
            <tr style={{fontSize:'23px'}}>
                <td colSpan={3} style={{height:'65px'}}>
                    <input type='text' value={content} name='content' onChange={handleContentChange}/>
                </td>
                <td colSpan={3}>
                    <input type='number' value={price} name='price' onChange={handlePriceChange}/>
                </td>
                <td colSpan={2}>
                    <input type='text' value={note} name='note' onChange={handleNoteChange}/>
                </td>
            </tr>
            <tr style={{fontSize:'23px'}}>
                <td colSpan={3} style={{height:'65px'}}>
                    <input type='text' value={content} name='content' onChange={handleContentChange}/>
                </td>
                <td colSpan={3}>
                    <input type='number' value={price} name='price' onChange={handlePriceChange}/>
                </td>
                <td colSpan={2}>
                    <input type='text' value={note} name='note' onChange={handleNoteChange}/>
                </td>
            </tr>
            <tr style={{fontSize:'23px'}}>
                <td colSpan={3} style={{height:'65px'}}>
                    <input type='text' value={content} name='content' onChange={handleContentChange}/>
                </td>
                <td colSpan={3}>
                    <input type='number' value={price} name='price' onChange={handlePriceChange}/>
                </td>
                <td colSpan={2}>
                    <input type='text' value={note} name='note' onChange={handleNoteChange}/>
                </td>
            </tr>
            <tr style={{fontSize:'23px'}}>
                <td colSpan={3} style={{height:'65px'}}>
                    <input type='text' value={content} name='content' onChange={handleContentChange}/>
                </td>
                <td colSpan={3}>
                    <input type='number' value={price} name='price' onChange={handlePriceChange}/>
                </td>
                <td colSpan={2}>
                    <input type='text' value={note} name='note' onChange={handleNoteChange}/>
                </td>
            </tr>
            <tr style={{fontSize:'23px'}}>
                <td colSpan={3} style={{height:'65px'}}>
                    <input type='text' value={content} name='content' onChange={handleContentChange}/>
                </td>
                <td colSpan={3}>
                    <input type='number' value={price} name='price' onChange={handlePriceChange}/>
                </td>
                <td colSpan={2}>
                    <input type='text' value={note} name='note' onChange={handleNoteChange}/>
                </td>
            </tr>
            <tr style={{fontSize:'23px'}}>
                <td colSpan={3} style={{height:'65px'}}>
                    <input type='text' value={content} name='content' onChange={handleContentChange}/>
                </td>
                <td colSpan={3}>
                    <input type='number' value={price} name='price' onChange={handlePriceChange}/>
                </td>
                <td colSpan={2}>
                    <input type='text' value={note} name='note' onChange={handleNoteChange}/>
                </td>
            </tr>
            <tr style={{fontSize:'23px'}}>
                <td colSpan={9} style={{height:'50px'}}>위 금액을 청구하오니 결제바랍니다.</td>
            </tr>
        </>
    )
}
export default AppDocExpend;