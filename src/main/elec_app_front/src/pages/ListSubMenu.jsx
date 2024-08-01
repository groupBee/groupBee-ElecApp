import { NavLink } from "react-router-dom";

const ListSubMenu=({setOrder})=>{
    return(
        <div>
            <ul className="minimenu">
                <li onClick={() => setOrder('appdoctype')}>종류</li>
                <li onClick={() => setOrder('writer')}>기안자</li>
                <li onClick={() => setOrder('department')}>부서</li>
                <li onClick={() => setOrder('no')}>기안일</li>
            </ul>
        </div>
    )
}
export default ListSubMenu;