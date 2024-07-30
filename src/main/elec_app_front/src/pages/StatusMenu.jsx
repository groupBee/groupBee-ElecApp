import axios from "axios";
import { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";

const StatusMenu=()=>{
    const [ready,setReady] = useState(0);
    const [ing,setIng] = useState(0);
    const [done,setDone] = useState(0);
    const [memberId,setMemberId]=useState('손가원');
    const getStatusCount=()=>{
        axios.post("/elecapp/receivedApp", { memberId: memberId })
        .then(res => {
            console.log(res.data);
            setReady(res.data.readyCount);
            setIng(res.data.ingCount);
            setDone(res.data.doneCount);
        });
    }
    useEffect(()=>{
        getStatusCount();
    },[])
    
    return(
        <div>
            <div style={{display:"flex" , justifyContent:'space-between', float:'left',border:'1px solid gray'}}>
                <div style={{width:'300px',textAlign:'center'}}>
                    <NavLink to={`/${memberId}/ready`}><h3>결재대기{ready}</h3></NavLink>
                </div>
                <div style={{width:'200px',textAlign:'center'}}>
                    <NavLink to={`/${memberId}/ing`}><h3>결재중{ing}</h3></NavLink>
                </div>
                <div style={{width:'200px',textAlign:'center'}}>
                    <NavLink to={`/${memberId}/done`}><h3>결재 완료{done}</h3></NavLink>
                </div>
            </div>
        </div>
    )
}
export default StatusMenu ;