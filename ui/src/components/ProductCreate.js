import { useState, useEffect } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import axios from "axios";

import ProductForm from "./ProductForm";


const ProductCreate = () => {
    const [product, setProduct] = useState({
        description: '',
        price: '',
        quantity:'',
        image:''
    });
    
const navigate = useNavigate();

    const handleChange = (event) => {
        setProduct({
            ...product,
            [event.target.id]: event.target.value,
        });
    }


    const handleSubmit= (event) => {
        event.preventDefault();      
        createProduct(product);
        navigate("/admin")
    }
   

    return <ProductForm product ={product} handleChange={handleChange} handleSubmit={handleSubmit} />
}

const createProduct = async (product) => {
    axios.post("http://localhost:8081/eshop/product/create", JSON.stringify(product), {headers: {'content-type': 'application/json'}}).then((response) => {
        console.log(response.data);
    });
}

export default ProductCreate;
