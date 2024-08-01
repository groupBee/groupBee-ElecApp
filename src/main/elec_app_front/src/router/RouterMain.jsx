import { Route, Routes } from 'react-router-dom';
import WriteForm from "../pages/WriteForm";
import Mainpage from '../pages/Mainpage';
import MainMenu from '../pages/MainMenu';
import SentList from '../pages/SentList';
import Detail from '../pages/Detail';

const RouterMain=()=>{
    return(
        <div>
            <h1 style={{marginTop:'30px'}}>Mainpage</h1>
            <div style={{border:'1px solid gray'}}>
                <MainMenu/>
            </div>
            <Routes>
                <Route path='/' element={<Mainpage/>}/>
                <Route path={`/:memberId/:status/:sort`} element={<Mainpage/>}/>
                <Route path='/elecapp/'>
                    <Route path='create' element={<WriteForm/>}/>
                    <Route path='myList' element={<SentList/>}/>
                    <Route path='mainList' element={<Mainpage/>}/>
                    <Route path={'sign/:appDocType/:memberId/:appId'} element={<Detail/>}/>
                </Route>
            </Routes>
        </div>
    )
}
export default RouterMain;