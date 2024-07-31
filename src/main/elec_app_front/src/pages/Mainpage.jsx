import List from "./List.jsx";
import ListSubMenu from "./ListSubMenu.jsx";
import MainMenu from "./MainMenu.jsx";
import StatusMenu from "./StatusMenu.jsx";
import "../css/MainMenuStyle.css";
import SubPage from "./SentList.jsx";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";


const Mainpage =()=>{
    const {memberId,status,sort}=useParams();
    const [appList,setAppList]=useState([]);
    const [order,setOrder]=useState('');
    const chageList = () => {
        axios.get(`/elecapp/status?memberId=${memberId}&status=${status}&order=${order === '' ? sort : order}`)
            .then(res => {
                setAppList(res.data);
            });
    }

    useEffect(() => {
        console.log('changedOrder=' + order)
        chageList();
    }, [status, order]); // 상태 배열에 'order' 추가
    return(
        <div>
            <div>
                <StatusMenu/>
            </div>
            <br/><br/>
            <div>
                <ListSubMenu setOrder={setOrder}/>
            </div>
            {memberId==null&&status==null&&sort?'':<div><br/><br/><List appList={appList}/></div>}
        </div>
    )
}
export default Mainpage;