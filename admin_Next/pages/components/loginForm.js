import { useRef } from 'react'
import { useDispatch } from 'react-redux'
import userData from "../data/user"
import productData from "../data/product"

export default function loginForm({adminData}) {

    const dispatch = useDispatch()
    const idRef = useRef("")
    const pwdRef = useRef("")

    const refChange = () => {
        let id = idRef.current.value
        let pwd = pwdRef.current.value
        let isLogin = false
        adminData.map((admin) => {
            if (admin.uuid == id && admin.password == pwd) {
                dispatch({ type: "loginId/set", id: admin.uuid })
                dispatch({type: "users/set", users: userData})                
                dispatch({ type: "products/set", products: productData })
                isLogin = true
            }
        })
        if (!isLogin) alert("아이디, 비밀번호 확인하세요")
    }

    return (
        <div id='loginForm'>
            <label>아이디</label>
            <input ref={idRef}>
            </input>
            <label>비밀번호</label>
            <input type='password' ref={pwdRef}>
            </input>
            <button 
                style={{backgroundColor: "#d4e5f6"}}
                onClick={refChange}
            >로그인</button>
        </div>
    )
}