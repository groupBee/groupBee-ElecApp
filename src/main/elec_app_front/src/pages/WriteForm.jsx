import { useState } from "react";

const WriteForm=()=>{



    
    const [writer,setWriter]=useState('');
    const [second_approver,setSecond_approver]=useState('');
    const [first_approver,setFirst_approver]=useState('');
    const [Third_approver,setThird_approver]=useState('');
    const [rejection_reason,setRejection_reason]=useState('');
    const [attached_file,setAttached_file]= useState('');
    const [approve_status,setApprove_status]=useState(0);
    const [approve_type,setApprove_type]=useState(0);
    const [level,setLevel]=useState(0);
    const [approve_date,setApprove_date]=useState('');
    const [app_doc_type,setApp_doc_type]=useState(0);
    const [additionalFields,setAdditionalFields]=useState([]);
    return(
        <div>
            <h1>WriteForm</h1>
            <form>
                <table className="table table-bordered">
                    <tbody>
                        <th>
                            <th>
                                작성자
                            </th>
                            <td>
                                <input type="text" />
                            </td>
                        </th>
                    </tbody>
                </table>
            </form>
        </div>
    )
}
export default WriteForm;