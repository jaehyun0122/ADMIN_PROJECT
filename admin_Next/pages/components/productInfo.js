import { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import { useRouter } from 'next/router'
export default function ProductInfo() {
    const router = useRouter();
    let products = useSelector(state => {
        return state.products.value
    })
    const [header, setHeader] = useState([])
    const [filter, setFilter] = useState([])
    const [pageData, setPageData] = useState(10)
    const [pageNum, setPageNum] = useState([])
    useEffect(() => {
        if(products.length > 0){
            setHeader(Object.keys(products[0]))
        }
    }, [])

    useEffect(() => {
        setFilter([...products.slice(0, pageData)])
        let arr = []
        for (let j = 1; j <= Math.ceil(products.length / pageData); j++){
            arr.push(j)
        }
        setPageNum(arr)
    },[pageData])
    return (
        <>
            <div>
                상품 정보 
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
                            let start = Number(e.target.value)-1
                            setFilter(products.slice(start == 0 ? 0 : start*pageData,
                                start*pageData > products.length ? products.length : start*pageData+pageData))
                        }}
                    >{page}</button>
                ))}
            </div>
            </div>
            <input placeholder='productName 검색 enter'
                onKeyUp={(e) => {
                    if (e.key == "Enter") {
                        let filter = products.filter((item) => {
                            if (item.productName.toLowerCase().indexOf(e.target.value.trim().toLowerCase()) > -1) {
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
                    {filter.map((product, idx) => (
                        <tr key={idx}
                            onClick={() => {
                                router.push({
                                    pathname: "/productDetail/[pid]",
                                    query: {
                                        pid: product.uuid,
                                        productName: product.productName,
                                        image: product.image,
                                        description: product.description,
                                        registeredAt: product.registeredAt
                                    }
                                })
                            }}
                        >
                            {header.map((head, idx) => (
                                <td key={idx}>

                                    {head == "image" ? (
                                        <img width="200px" src={product[head]}></img>
                                    ) : (
                                        <>{ product[head]}</>)}
                                </td>
                            ))}
                        </tr>
                    ))}
                </tbody>
            </table>
        </>
    )
}