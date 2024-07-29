const StatusMenu=()=>{
    return(
        <div>
            <div style={{display:"flex" , justifyContent:'space-between', float:'left',border:'1px solid gray'}}>
                <div style={{width:'300px',textAlign:'center'}}>
                    <h3>결재대기</h3>
                </div>
                <div style={{width:'200px',textAlign:'center'}}>
                    <h3>결재중</h3>
                </div>
                <div style={{width:'200px',textAlign:'center'}}>
                    <h3>결재 완료</h3>
                </div>
            </div>
        </div>
    )
}
export default StatusMenu ;