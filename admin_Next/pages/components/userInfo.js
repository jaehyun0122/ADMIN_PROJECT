import { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useRouter } from 'next/router'
export default function UserInfo() {
    const router = useRouter();
    let users = useSelector(state => {
        return state.users.value
    })
    const [header, setHeader] = useState([])
    const [filter, setFilter] = useState([])
    const [pageData, setPageData] = useState(10)
    const [pageNum, setPageNum] = useState([])
    useEffect(() => {
        if(users.length > 0){
            setHeader(Object.keys(users[0]))
        }
    }, [])
    useEffect(() => {
        setFilter([...users.slice(0, pageData)])
        let arr = []
        for (let j = 1; j <= Math.ceil(users.length / pageData); j++){
            arr.push(j)
        }
        setPageNum(arr)
    },[pageData])
    return (
        <>
            <div>
                <div
                    style={{color: "#a6746d"}}
                >
                유저 정보 
                </div>
                <button value={10}
                    onClick={(e) => {
                        setPageData(Number(e.target.value))
                    }}
                >10개 보기</button>
                <button value={30}
                    onClick={(e) => {
                        
                        setPageData(Number(e.target.value))
                    }}
                >30개 보기</button>
                <div>
                    페이지번호
                {pageNum.map((page, idx) => (
                    <button
                        key={idx}
                        value={page}
                        onClick={(e) => {
                            let start = Number(e.target.value) - 1
                            setFilter(users.slice(start==0 ? 0 : start*pageData,
                                start + pageData > users.length ? users.length : start*pageData + pageData))
                        }}
                    >{page}</button>
                ))}
                </div>
            </div>
            <input placeholder='nickname 검색 enter'
                onKeyUp={(e) => {
                    if (e.key == "Enter") {
                        let filter = users.filter((item) => {
                            if (item.nickname.toLowerCase().indexOf(e.target.value.trim().toLowerCase()) > -1) {
                                return true;
                            }
                        })
                        setFilter([...filter])
                    }
                }}
            ></input>
            <table>
                <thead>
                    <tr>
                    {header.map((head, idx) => (
                        <th key={idx}>{head}</th>
                    ))}
                    </tr>
                </thead>
                <tbody>
                    {filter.map((user, idx) => (
                        <tr key={idx}
                        onClick={() => {
                            router.push({
                                pathname: "/userDetail/[uid]",
                                query: {
                                    uid: user.uuid,
                                    nickname: user.nickname,
                                    email: user.email,
                                    avatar: user.avatar,
                                    password: user.password,
                                    birthdate: user.birthdate,
                                    registeredAt: user.registeredAt
                                }
                            })
                        }}
                        >
                            {header.map((head, idx) => (
                                <td key={idx}>
                                    {head == "avatar" ? (
                                        <>
                                            <img width="200px" src={user[head]}></img>
                                        </>
                                    ): (<>
                                    {user[head]}
                                    </>
                                        )}
                                </td>
                            ))}
                        </tr>
                    ))}
                </tbody>
            </table>
        </>
    )
}