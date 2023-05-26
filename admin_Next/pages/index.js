import adminData from "./data/admin.json"

import { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'

import UserInfo from "./components/userInfo"
import LoginForm from "./components/loginForm"
import ProductInfo from "./components/productInfo"

export default function Home() {

  const dispatch = useDispatch();
  const admins = useSelector(state => {
    return state.admins.value
  })
  const loginId = useSelector(state => {
    return state.loginId.value
  })
  const [toggle, setToggle] = useState(true)

  useEffect(() => {
    dispatch({type: "admins/set", admins: adminData})
  }, [])


  return (
    <>
      {loginId == -1 ? (
        <>
          <LoginForm adminData={admins} />
        </>
      ): (
          <>
            <button
              style={{backgroundColor: "#d4e5f6"}}
              onClick={() => {
                dispatch({type: "loginId/set", id: -1})
              }}
            >logout</button>
            <div>
              <button onClick={() => {
                setToggle(true)
              }}>유저 정보</button>
              <button onClick={() => {
                setToggle(false)
              }}>상품 정보</button>
            </div>
            {toggle ? (
              <UserInfo />
            ): (
                <ProductInfo />
            )}
            
          </>  
      )}
    </>
  )
}
