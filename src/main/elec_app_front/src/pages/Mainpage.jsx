import List from "./List.jsx";
import ListSubMenu from "./ListSubMenu.jsx";
import MainMenu from "./MainMenu.jsx";
import StatusMenu from "./StatusMenu.jsx";
import "../css/MainMenuStyle.css";
import SubPage from "./SentList.jsx";
import { useParams } from "react-router-dom";


const Mainpage =()=>{
    return(
        <div>
            <div>
                <StatusMenu/>
            </div>
            <div>
                <ListSubMenu/>
            </div>
            <div>
                <List/>
            </div>
        </div>
    )
}
export default Mainpage;