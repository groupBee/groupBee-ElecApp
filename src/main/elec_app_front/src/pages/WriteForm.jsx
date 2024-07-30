import { Button } from "@mui/material";
import axios from "axios";
import { useRef, useState } from "react";
import AppDocVacation from "./AppDocVacation";
import AppDocExpend from "./AppDocExpend";
import AppDocIntent from "./AppDocIntent";
import NewAppDocType from "./NewAppDocType";
import '../css/WriteForm.css';

const WriteForm = () => {
    const [writer, setWriter] = useState('');
    const [secondApprover, setSecondApprover] = useState('');
    const [firstApprover, setFirstApprover] = useState('');
    const [thirdApprover, setThirdApprover] = useState('');
    const fileRef = useRef(null);
    const [originalFile, setOriginalFile] = useState(null);
    const [attachedFile, setAttachedFile] = useState('');
    const [approveStatus, setApproveStatus] = useState(0);
    const [approveType, setApproveType] = useState(0);
    const [level, setLevel] = useState(0);
    const [approveDate, setApproveDate] = useState('');
    const [appDocType, setAppDocType] = useState(0);
    const [position, setPosition] = useState('');
    const [department, setDepartment] = useState('');
    const [additionalFields, setAdditionalFields] = useState({});

    const uploadPhoto = (e) => {
        const uploadFile = e.target.files[0];
        setOriginalFile(uploadFile);
        const uploadForm = new FormData();
        uploadForm.append("file", uploadFile);
        axios.post('http://localhost:9522/elecapp/uploadfile', uploadForm, {
            headers: { "Content-Type": "multipart/form-data" }
        })
        .then(res => {
            console.log(res.data);
            setAttachedFile(res.data);
        })
        .catch(err => {
            console.error('파일 업로드 중 오류 발생:', err);
        });
    }

    const changeAppDoc = (e) => {
        setAppDocType(parseInt(e.target.value));
    }

    const handleAdditionalFieldChange = (key, value) => {
        setAdditionalFields(prevFields => ({
            ...prevFields,
            [key]: value
        }));
    };

    const createApp = () => {
        const originalFileName = originalFile ? originalFile.name : '';
        axios.post('http://localhost:9522/elecapp/create', {
            writer, firstApprover, secondApprover, thirdApprover,
            originalFile: originalFileName, attachedFile, approveStatus, appDocType, level,
            approveType, position, department, additionalFields
        })
            .then(res => {
                alert("성공");
            })
            .catch(err => {
                console.error('데이터 전송 중 오류 발생:', err);
            });
    }

    return (
        <div>
            <h1>WriteForm</h1>
            <table style={{border:'3px solid black'}}>
                <caption align='top'>
                    <select defaultValue={appDocType} onChange={changeAppDoc}>
                        <option value={0}>품의서</option>
                        <option value={1}>휴가신청서</option>
                        <option value={2}>지출보고서</option>
                    </select>
                </caption>
                <tbody className='tableborder'>
                <tr>
                    <td colSpan={4} rowSpan={3}>{appDocType === 0 ? '품의서' : appDocType === 1 ? '휴가신청서' : '지출보고서'}</td>
                    <td rowSpan={3}>결제</td>
                    <td>최초승인자</td>
                    <td>중간승인자</td>
                    <td>최종승인자</td>
                </tr>
                <tr>
                    <td style={{height:'100px'}}></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>
                        <input type="text" value={firstApprover} onChange={(e) => setFirstApprover(e.target.value)}/>
                    </td>
                    <td>
                        <input type="text" value={secondApprover} onChange={(e) => setSecondApprover(e.target.value)}/>
                    </td>
                    <td>
                        <input type="text" value={thirdApprover} onChange={(e) => setThirdApprover(e.target.value)}/>
                    </td>
                </tr>
                <tr>
                    <td style={{width:'70px'}}>성명</td>
                    <td><input type="text" value={writer} onChange={(e) => setWriter(e.target.value)}/></td>
                    <td style={{width:'70px'}}>부서</td>
                    <td><input type="text" value={department} onChange={(e) => setDepartment(e.target.value)}/></td>
                    <td style={{width:'70px'}}>직급</td>
                    <td><input type="text" value={position} onChange={(e) => setPosition(e.target.value)}/></td>
                    <td style={{width:'70px'}}>보안등급</td>
                    <td><input type="number" value={level} onChange={(e) => setLevel(e.target.value)}/></td>
                </tr>
                {appDocType === 0 && <AppDocIntent/>}
                {appDocType === 1 && <AppDocVacation handleAdditionalFieldChange={handleAdditionalFieldChange}/>}
                {appDocType === 2 && <AppDocExpend/>}
                {appDocType > 2 && <NewAppDocType/>}
                <tr>
                    <td>첨부파일</td>
                    <td colSpan={7}><input type="file" ref={fileRef} onChange={uploadPhoto}/></td>
                </tr>
                </tbody>
                <tbody>
                <tr>
                    <td colSpan={8}><input type="date" value={approveDate} onChange={(e) => setApproveDate(e.target.value)}
                    style={{marginTop:'50px'}}/></td>
                </tr>
                <tr>
                    <td colSpan={4} style={{height:'50px'}}></td>
                    <td>서명</td>
                    <td>신청자 : </td>
                    <td></td>
                    <td>(인)</td>
                </tr>
                <tr>
                    <td colSpan={8}>
                        <Button variant="outlined" color="warning" onClick={() => setApproveStatus('1')}>임시저장</Button>
                        <Button variant="outlined" color="warning" onClick={createApp}>작성완료</Button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    );
}

export default WriteForm;
