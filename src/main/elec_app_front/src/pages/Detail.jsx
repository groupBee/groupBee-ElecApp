import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const Detail = () => {
    const [approveStatus, setApproveStatus] = useState(0);
    const [rejectionReason, setRejectionReason] = useState('');
    const [approveType, setApproveType] = useState(2);
    const { memberId, appDocType, appId } = useParams();
    const [list, setList] = useState({});
    const [open, setOpen] = useState(false);
    const navi=useNavigate();
    const getSignForm = () => {
        axios.get("/elecapp/findById?elecAppId=" + appId)
            .then(res => {
                setList(res.data);
                console.log(res.data);
            })
    }

    useEffect(() => {
        getSignForm();
    }, [])

    const handleRejectionOpen = () => {
        setOpen(true);
    };

    const handleRejectionClose = () => {
        setOpen(false);
    };

    const handleRejectionSubmit = () => {
        console.log("Rejection Reason:", rejectionReason);
        axios.post('/elecapp/rejection', null, {
            params: {
                elecAppId: appId,
                rejectionReason: rejectionReason
            }
        })
        .then(res => {
            setOpen(false);
            
        })
        .catch(err => {
            console.error("Rejection failed:", err);
        });
    };

    const formatDateForInput = (dateString) => {
        if (!dateString) return '';
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }
// 승인 클릭
const acception = () => {
    let a = confirm('결재를 승인하시겠습니까?');
    if (a) {
        axios.post('/elecapp/chageAppType', { elecAppId: appId })
            .then(res => {
                navi('/');
            })
            .catch(err => {
                console.error("Approval failed:", err);
            });
    } else {
        return;
    }
}

    return (
        <div>
            <table style={{ border: '3px solid black' }}>
                <tbody className='tableborder'>
                    <tr>
                        <td colSpan={4} rowSpan={3} style={{ fontSize: '60px' }}>
                            {appDocType == 0 ? '품 의 서' : appDocType == 1 ? '휴 가 신 청 서' : '지 출 보 고 서'}
                        </td>
                        <td rowSpan={3} style={{ fontSize: '23px' }}>결제</td>
                        <td style={{ height: '50px', fontSize: '23px' }}>최초승인자</td>
                        <td style={{ fontSize: '23px' }}>중간승인자</td>
                        <td style={{ fontSize: '23px' }}>최종승인자</td>
                    </tr>
                    <tr>
                        <td style={{ height: '150px' }}></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                    <td className="stampFirst">
                            <input type="text" value={list.firstApprover || ''} readOnly />
                        </td>
                        <td className="stampSecond">
                            <input type="text" value={list.secondApprover || ''} readOnly />
                            {list.approveType === 2 && memberId === list.secondApprover && (
                                <>
                                    <Button variant='outlined' color='warning' onClick={acception}>승인</Button>
                                    <Button variant='outlined' color='warning' onClick={handleRejectionOpen}>반려</Button>
                                </>
                            )}
                        </td>
                        <td className="stampThird">
                            <input type="text" value={list.thirdApprover || ''} readOnly />
                            {list.approveType === 3 && memberId === list.thirdApprover && (
                                <>
                                    <Button variant='outlined' color='warning' onClick={acception}>승인</Button>
                                    <Button variant='outlined' color='warning' onClick={handleRejectionOpen}>반려</Button>
                                </>
                            )}
                        </td>
                    </tr>
                    <tr>
                        <td style={{ width: '70px', fontSize: '23px' }}>성명</td>
                        <td>
                            <input type="text" value={list.writer || ''} 
                                   style={{ fontSize: '23px', width: '175px' }} readOnly />
                        </td>
                        <td style={{ width: '70px', fontSize: '23px' }}>부서</td>
                        <td>
                            <input type="text" value={list.department || ''} 
                                   style={{ fontSize: '23px', width: '175px' }} readOnly />
                        </td>
                        <td style={{ width: '70px', fontSize: '23px' }}>직급</td>
                        <td>
                            <input type="text" value={list.position || ''} 
                                   style={{ fontSize: '23px', width: '175px' }} readOnly />
                        </td>
                        <td style={{ width: '70px', fontSize: '23px' }}>보안등급</td>
                        <td>
                            <input type="number" value={list.level || ''} 
                                   style={{ fontSize: '23px', width: '175px' }} readOnly />
                        </td>
                    </tr>
                {
    appDocType == 0 ? (
        <>
            <tr>
                <td style={{fontSize:'23px'}}>제목</td>
                <td colSpan={7}>
                    <input type='text' value={list.additionalFields?.title || ''} 
                    style={{width:'100%',fontSize:'23px'}} readOnly />
                </td>
            </tr>
            <tr>
                <td colSpan={8} style={{height:'50px',fontSize:'23px'}}>품의내용</td>
            </tr>
            <tr>
                <td colSpan={8}>
                    <input type='text' value={list.additionalFields?.content || ''} 
                        style={{height:'650px', width:'100%',fontSize:'23px'}} readOnly />
                </td>
            </tr>
            <tr>
                <td colSpan={8} style={{height:'50px',fontSize:'23px'}}>위와 같이 품의사유로 검토 후 결제 바랍니다.</td>
            </tr>
        </>
    ) : appDocType == 1 ? (
    <>
         <tr>
                <th colSpan={2}>휴가 종류</th>
                <td colSpan={6}>
                    <input type="radio" value="반차" checked={list.additionalFields?.type == "반차"}
                    style={{width:'20px'}} readOnly />반차
                    <input type="radio" value="월차" checked={list.additionalFields?.type == "월차"} 
                           style={{width:'20px'}} readOnly />월차
                    <input type="radio" value="병가" checked={list.additionalFields?.type == "병가"}
                           style={{width:'20px'}} readOnly />병가
                    <input type="radio" value="기타" checked={list.additionalFields?.type == "기타"} 
                           style={{width:'20px'}} readOnly />기타
                </td>
            </tr>
            <tr>
                <th colSpan={2}>휴가 기간</th>
                <td colSpan={6}>
                    <input type="date" value={list.additionalFields?.start || ''} readOnly />
                    ~<input type="date" value={list.additionalFields?.end || ''} readOnly />
                </td>
            </tr>
            <tr>
                <td colSpan={8} style={{height: '50px'}}>내 용</td>
            </tr>
            <tr>
                <td colSpan={8}>
                    <input type='text' value={list.additionalFields?.detail || ''} 
                           style={{height: '600px', width: '100%'}} readOnly />
                </td>
            </tr>
            <tr>
                <td colSpan={8} style={{height:'50px'}}>위와 같이 휴가을 신청하오니 허락해주시기 바랍니다.</td>
            </tr>
    </>)
    : (
        <>
        <tr style={{ fontSize: '23px' }}>
            <td>요청일자</td>
            <td colSpan={3}>
                <input type='date' value={list.requestDate} />
            </td>
            <td>지출유형</td>
            <td>
                <input type='number' value={list.additionalFields.expendType}/>
            </td>
            <td>최종금액</td>
            <td style={{ width: '40px' }}>
                <input type='number' value={list.additionalFields.finalPrice} 
                       style={{ width: '70px' }} />
                <select defaultValue={list.additionalFields.monetaryUnit} >
                    <option value={0}>원</option>
                    <option value={1}>달러</option>
                    <option value={2}>엔</option>
                </select>
            </td>
        </tr>
        {/* <tr style={{ fontSize: '23px' }}>
            <td>제목</td>
            <td colSpan={7}>
                <input type='text' value={list.additionalFields.title}  />
            </td>
        </tr>
        <tr style={{ fontSize: '23px' }}>
            <td rowSpan={list.additionalFields.details.length + 1}>내역</td>
            <td colSpan={3} style={{ height: '50px' }}>지출내용</td>
            <td colSpan={3}>금액</td>
            <td colSpan={2}>비고

            </td>
        </tr> */}
        {/* {details.map((detail, index) => (
            <tr key={index} style={{ fontSize: '23px' }}>
                <td colSpan={3} style={{ height: '65px' }}>
                    <input
                        type='text'
                        value={list.additionalFields.detail.content} />
                </td>
                <td colSpan={3}>
                    <input
                        type='number'
                        value={list.additionalFields.detail.price}
                        />
                </td>
                <td colSpan={2}>
                    <input
                        type='text'
                        value={list.additionalFields.detail.note}
                        />
                </td>
            </tr>
        ))} */}
    </>

    )
}


                <tr style={{fontSize:'23px'}}>
                    <td colSpan={2}>첨부파일</td>
                    <td colSpan={6}>{list.originalFile}</td>
                </tr>
                </tbody>
                <tbody>
                <tr style={{fontSize:'23px'}}>
                    <td colSpan={8}>
                        <input type="date"  value={formatDateForInput(list.writeday)  || ''} readOnly
                    style={{marginTop:'50px'}}/></td>
                </tr>
                <tr style={{fontSize:'23px'}}>
                    <td colSpan={4} style={{height:'50px'}}></td>
                    <td>서명</td>
                    <td>신청자 : {list.writer}</td>
                    <td></td>
                    <td>(인)</td>
                </tr>
                <tr>
                    <td colSpan={8}>
                        <Button variant="outlined" color="warning" >작성완료</Button>
                    </td>
                </tr>
                </tbody>
            </table>

            <Dialog open={open} onClose={handleRejectionClose}>
                <DialogTitle>반려 사유 입력</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        반려 사유를 입력하세요:
                    </DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="반려 사유"
                        type="text"
                        fullWidth
                        variant="standard"
                        value={rejectionReason}
                        onChange={(e) => setRejectionReason(e.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleRejectionClose}>취소</Button>
                    <Button onClick={handleRejectionSubmit}>확인</Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}
export default Detail;