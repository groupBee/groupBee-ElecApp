import { useState } from "react";

const AppDocVacation = ({ handleAdditionalFieldChange }) => {
    const [days, setDays] = useState(0);
    const [start, setStart] = useState('');
    const [end, setEnd] = useState('');
    const [day, setDay] = useState('');
    const [type, setType] = useState('');

    // 휴가 정보 변경 시 상위 컴포넌트에 알림
    const handleDaysChange = (e) => {
        setDays(e.target.value);
        handleAdditionalFieldChange("days", e.target.value);
    };

    const handleStartChange = (e) => {
        setStart(e.target.value);
        handleAdditionalFieldChange("start", e.target.value);
    };

    const handleEndChange = (e) => {
        setEnd(e.target.value);
        handleAdditionalFieldChange("end", e.target.value);
    };

    const handleDayChange = (e) => {
        setDay(e.target.value);
        handleAdditionalFieldChange("day", e.target.value);
    };

    const handleTypeChange = (e) => {
        setType(e.target.value);
        handleAdditionalFieldChange("type", e.target.value);
    };

    return (
        <>
            <tr>
                <th>휴가 일수</th>
                <td>
                    <input type="number" value={days} onChange={handleDaysChange} />
                </td>
            </tr>
            {days == 1 &&
                <tr>
                    <td>날짜</td>
                    <td>
                        <input type="date" value={day} name="day" onChange={handleDayChange} />
                    </td>
                </tr>
            }
            {days > 1 &&
                <>
                    <tr>
                        <th>휴가 시작일</th>
                        <td>
                            <input type="date" value={start} name="start" onChange={handleStartChange} />
                        </td>
                    </tr>
                    <tr>
                        <th>휴가 종료일</th>
                        <td>
                            <input type="date" value={end} name="end" onChange={handleEndChange} />
                        </td>
                    </tr>
                </>
            }
            <tr>
                <th>휴가 종류</th>
                <td>
                    <select value={type} onChange={handleTypeChange}>
                        <option value="반차">반차</option>
                        <option value="월차">월차</option>
                        <option value="병가">병가</option>
                        <option value="기타">기타</option>
                    </select>
                </td>
            </tr>
        </>
    );
};

export default AppDocVacation;
