import { useEffect, useState } from 'react'
import { useRouter } from 'next/router'

export default function ProductDetail() {
    const [header, setHeader] = useState([])
    const [data, setData] = useState({})
    const router = useRouter()
    useEffect(() => {
        setHeader(Object.keys(router.query))
        setData(router.query)
    }, [])

    return (
        <>
            product
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
                                {head == "image" ? (
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