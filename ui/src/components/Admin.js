import { useState, useEffect } from "react";
import axios from "axios";


const Admin = () => {
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
        <a class="btn btn-success" href={'product/'+product.id} role="button"><i class="bi bi-info-square"></i> View</a>         
          <a class="btn btn-warning" href={'productUpdate/'+product.id} role="button"><i class="bi bi-info-square"></i> Edit</a>          
          <button type="buttons" onClick={()=> deleteProduct(product.id)} class="btn btn-danger"><i class="bi bi-x-square"></i> Delete</button>
        </div>
      </li>
    );

    const deleteProduct = async (id) => {
      axios.delete("http://localhost:8081/eshop/product/delete/"+id).then((response)=>{
        console.log(response);
        const updatedProducts = products.filter((product) =>product.id != id)
        setProducts(updatedProducts);
      });
    }
  
    return (
        <>
          <div class="container">
            <ul class="list-group">
              <li class="list-group-teim d-flex justify-content-between align-items-center bg-success text-right"><h2>Product List</h2>
                <div class="align-right"><a class="btn btn-info" href={'productCreate'} role="button"><i class="bi bi-plus-square"></i> Create</a></div>
              </li>
            {pList}
            </ul>
          </div>
 
        </>
    );  
}



export default Admin;