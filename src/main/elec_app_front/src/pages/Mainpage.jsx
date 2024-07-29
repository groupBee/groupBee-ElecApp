import List from "./List.jsx";
import ListSubMenu from "./ListSubMenu.jsx";
import MainMenu from "./MainMenu.jsx";
import StatusMenu from "./StatusMenu.jsx";
import "../css/MainMenuStyle.css";


const Mainpage =()=>{
    return(
        <div>
            <h1 style={{marginTop:'30px'}}>Mainpage</h1>
            <div style={{border:'1px solid gray'}}>
                <MainMenu/>
            </div>
            <div className="mainPosition">
                <div style={{border:'1px solid gray'}}>
                    <StatusMenu/>
                </div>
                <br/><br/><br/><br/>
                <h3>결재수신 목록</h3>
                <div style={{border:'1px solid gray'}}>
                    <ListSubMenu/>
                </div>
                <div style={{border:'1px solid gray'}}>
                    <List/>
                </div>
            </div>
        </div>
    )
}
export default Mainpage;