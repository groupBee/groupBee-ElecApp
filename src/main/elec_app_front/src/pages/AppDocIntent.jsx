import {useState} from "react";

const AppDocIntent=({handleAdditionalFieldChange})=>{
    const [title,setTitle]=useState('');
    const [content,setContent]=useState('');

    const handleTitleChange = (e) => {
        setTitle(e.target.value);
        handleAdditionalFieldChange("title", e.target.value);
    };
    const handleContentChange = (e) => {
        setContent(e.target.value);
        handleAdditionalFieldChange("content", e.target.value);
    };

    return(
        <>
            <tr>
                <td>제목</td>
                <td>
                    <input type='text' value={title} name='title' onChange={handleTitleChange}/>
                </td>
            </tr>
            <tr>
                <td>품의내용</td>
                <td>
                    <input type='text' value={content} name='title' onChange={handleContentChange}/>
                </td>
            </tr>
        </>
    )
}
export default AppDocIntent;