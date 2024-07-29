import { NavLink } from "react-router-dom";

const MainMenu=()=>{
  
    return(
        <div>
            MainMenu
            <div>
                <ul className="menu">
                    <li>결재현황</li>
                    <li><NavLink to={'/elecapp/create'}>작성하기</NavLink></li>
                    <li>발신목록</li>
                </ul>
            </div>
        </div>
    )
}
export default MainMenu;