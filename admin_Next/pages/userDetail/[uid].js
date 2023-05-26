import { useEffect, useState } from 'react'
import { useRouter } from 'next/router'
export default function UserDetail() {
    const [header, setHeader] = useState([])
    const [data, setData] = useState({})
    const router = useRouter()
    useEffect(() => {
        setHeader(Object.keys(router.query))
        setData(router.query)
    }, [])
    return (
        <>
            user
            <table>
                <thead>
                    <tr>
                    {header.map((head, idx) => (
                        <th key={idx}>
                        {head}
                        </th>
                    ))}
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        {header.map((head, idx)=>(
                            <td key={idx}>
                                {head == "avatar" ? (
                                    <img src={data[head]}></img>
                                ) : (
                                    <>
                                        {data[head]}
                                    </>
                                )}
                            </td>
                        ))}
                    </tr>
                </tbody>
            </table>
        </>
    )
}