import { useState, useEffect } from "react";
import axios from "axios";


const ProductList = () => {
    const [products, setProducts] = useState([]);

    useEffect(() => {
      axios.get("http://localhost:8081/eshop/product/getall").then((response) => {
        console.log(response);
        setProducts(response.data);
      });
    }, []);  

    const pList = products.map(product =>
      <li class="list-group-item d-flex justify-content-between align-items-center"
        key={product.id} style={{color: product.quantity === 0 ? 'red' : 'blue'}}>
        {product.description} ${product.price} {product.quantity === 0 ? 'Out of Stock' : product.quantity} 
        
        <div class="btn-group">
          
          <a class="btn btn-info" href={'product/'+product.id} role="button"><i class="bi bi-info-square"></i> Details</a>          
        </div>
      </li>
    );
    return (
      <>
        <div class="container">
          <ul class="list-group">
            <li class="list-group-teim d-flex justify-content-between align-items-center bg-success text-right"><h2>Product List</h2></li>
          {pList}
          </ul>
        </div>

      </>
  );  
}



export default ProductList;