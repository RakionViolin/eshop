import OrderButton from"./OrderButton";
import axios from "axios";
import { useState, useEffect } from "react";
import {useNavigate, useParams} from "react-router-dom";
import Orders from "./Orders";


const OrderView = () => {
    // const price = cart.reduce((accumulator, product) => accumulator + product.price*product.quantity, 0);    

    
    const [order, setOrder] = useState([]);
    let {id} = useParams();


    useEffect(() =>{
      axios.get("http://localhost:8081/eshop/order/get/" + id).then((response) => {
      
        setOrder(response.data);
      });
    }, []);
    let navigate = useNavigate();
    const count = order.products?.reduce((total,product) => total + parseInt(product.quantity), 0);
/*
     const pList = cart.map(product =>
       <li class="list-group-item d-flex justify-content-between align-items-center" key={product.id}> 
         <div class="card flex-row w-75">
         <img class="card-img-left" src={'../'+product.image} alt={product.description} width="300"></img>
           <div class="card-body">
             <h4 class="card-title h5, h4-sm">{product.description} </h4>
             <p class="card-text">${product.price}</p>
           </div>
         </div>
        
         <div class="btn-group">
           <CartButton quantity={product.quantity} handleDelete={handleDelete} id={product.id}/>
           <button class="btn btn-danger bi bi-trash" onClick={()=>handleDelete(product.id)}/>
           <a class="btn btn-info" href={'product/'+product.id} role="button"><i class="bi bi-info-square"></i></a>          
         </div>
       </li>
     );  
     */

    const oList = order.products?.map((product, index) =>
      <li class ="list-group-item d-flex justify-content-between align-items-center" key={product.id}>
        <h4>{index+1}.&nbsp;</h4>
        <div class="card flex-row w-100">
          <img class="card-img-left" src={'../'+product.image} alt={product.description} width="300"></img>
            <div class="card-body">
                <h4 class="card-title ht, h4-sm">{product.description}</h4>
                <p class="card-text">${product.price}</p>
                <p class="card-text">Quantity: {product.quantity}</p>
             </div> 
        </div>
      
        </li>
      )
  
    return (
      <>
        <div class="container">
          <ul class="list-group">
            <li class="list-group-item d-flex justify-content-between align-items-center bg-success text-light"><h2>Order #: {order.orderID}</h2>
            {/* <div class="align-right"><a class="btn btn-info" href={'productCreate'} role="button"><i class="bi bi-plus-square"></i> Create</a></div> */}
            
            </li>  
          <div class="card bg-secondary">

            <div class="card-body d-flex justify-content-between align-items-center">
                <h5 class="card-title">Order Placed: {new Date(order.date).toLocaleDateString()}</h5>
                <h5 class="card-title">Items:{count}</h5>
                <h5 class="card-title">Total: ${order.price}</h5>   
                <p class="card-text">Order Description: {order.description}</p>   
            </div>
          </div>
          {oList}
          </ul> 

          <hr style={{height:"2px"}}></hr>             
        </div>                      
      </>
    );
}

export default OrderView;