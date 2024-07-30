import { NavLink } from "react-router-dom";

const MainMenu=()=>{
  
    return(
        <div>
            MainMenu
            <div>
                <ul className="menu">
                    <li><NavLink to={'/elecapp/mainList'}>결재현황</NavLink></li>
                    <li><NavLink to={'/elecapp/create'}>작성하기</NavLink></li>
                    <li><NavLink to={'/elecapp/myList'}>발신목록</NavLink> </li>
                </ul>
            </div>
        </div>
    )
}
export default MainMenu;

