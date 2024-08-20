import { useState, useEffect } from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";

import Select from "./Select";

const Product = ({handleClick}) => {
    const [product, setProduct] = useState([]);
    const [quantity, setQuantity] = useState([1]);
    let {id} = useParams();

    useEffect(() => {
        axios.get("http://localhost:8081/eshop/product/get/" + id).then((response) => {
          console.log(response);
          setProduct(response.data);
        });
      }, []); 
      
      product.quantity = quantity;


      let navigate = useNavigate();

      const next = () => {
        handleClick(product);
        navigate("/productlist");
      }
  

    return (
        <div class="card" style={{width: '30%'}}>
            <img class="card-img-top" src={'../'+product.image} alt={product.description}></img>
            <div class="card-body">
                <h5 class="card-title">{product.description}</h5>
                <h6 class="card-title">${product.price}</h6>   
                <h6 class="card-title">Quantity: {product.quantity}</h6>                              
                <p class="card-text">{product.description}</p>
                <p class="card-text"><Select desc={"Quantity"} n={10} setQuantity={setQuantity}/></p>              
                <button class="btn btn-warning" onClick={()=>next()}> Add to Cart</button>
            </div>
        </div>
    );
}

export default Product;
