import { BrowserRouter } from "react-router-dom"
import RouterMain from "./RouterMain.jsx"

const Root =()=>{
    return(
        <BrowserRouter>
            <RouterMain/>
        </BrowserRouter>
    )
}

export default Root;