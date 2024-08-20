import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";
import axios from "axios";

import ProductForm from "./ProductForm";

const ProductUpdate = () => {
    const [product, setProduct] = useState([]);
    let {id} = useParams();

    console.log(id);

    useEffect(() => {
        axios.get("http://localhost:8081/eshop/product/get/" + id).then((response) => {
            console.log(response);
            setProduct(response.data);
        });
    }, []);

    const navigate = useNavigate();    

    const handleChange = (event) => {
        setProduct({
            ...product,
            [event.target.id]: event.target.value,
        });
    }

    const handleSubmit= (event) => {
        event.preventDefault();
        updateProduct(product);
        navigate("/admin")
        window.location.reload();
    }

    return <ProductForm product={product} handleChange={handleChange} handleSubmit={handleSubmit} />
}

const updateProduct = async (product) => {
    axios.put("http://localhost:8081/eshop/product/update", JSON.stringify(product), {headers: {'content-type': 'application/json'}}).then((response) => {
        console.log(response.data);
    });
}

export default ProductUpdate;
