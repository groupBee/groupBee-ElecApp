import { Route, Routes } from 'react-router-dom';
import WriteForm from "../pages/WriteForm";
import Mainpage from '../pages/Mainpage';

const RouterMain=()=>{
    return(
        <div>
            <Routes>
                <Route path="/" element={<Mainpage/>}/>
                <Route path='/elecapp/create'  element={<WriteForm/>}/>
            </Routes>
        </div>
    )
}
export default RouterMain;