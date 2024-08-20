import OrderButton from"./OrderButton";
import axios from "axios";
import { useState, useEffect } from "react";
import {useNavigate, useParams} from "react-router-dom";

const OrderUpdate = () => {
    let [order, setOrder] = useState({});
    let {id} = useParams();
    const [localTotal, setLocalTotal] = useState(0);
    const [localDesc, setLocalDesc] = useState(0);

    const handleDelete = (id) => {
      setOrder((order)=>({
        ...order,
        products : order.products.filter((product) => product.id !== id)
      }));
    }

    const handleUpdate = (id, quantity) => {
     
      setOrder((order)=({
        ...order,
        products: order.products.map((product)=>
          product.id === id ? {...product, quantity : quantity} : product
        ),
      }));

      if (order.products?.reduce((total,product) => total + parseInt(product.quantity), 0) === 0)
        cancelOrder(order.orderID);
    };  

    useEffect(() =>{
      axios.get("http://localhost:8081/eshop/order/get/" + id).then((response) => {
        setOrder(response.data);
        setLocalTotal(response.data.price);
      });
    }, [id]);
    let navigate = useNavigate();
    let count = order.products?.reduce((total,product) => total + parseInt(product.quantity), 0);
    

    useEffect(() => {
      if(order.products){
        const total = order.products.reduce(
          (total,product) => total + parseInt(product.quantity) * product.price, 700);
          setLocalTotal(total);
        const description = "Default Computer + " +  order.products.map((product) =>  product.description).join(" + ") ;
          setLocalDesc(description);
      }
    }, [order.products]);
   

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
        <div class="btn-group">
          <OrderButton quantity={product.quantity} handleDelete={handleDelete} id={product.id} handleUpdate={handleUpdate}/>
        </div>
        </li>
      )

      const cancelOrder = async (id) => {
        console.log(id);
        axios.delete("http://localhost:8081/eshop/order/cancel/"+id).then((response)=>{
          console.log(response);
          navigate("/orders");
        }
      );
      }

      const updateOrder = async (id) => {
        setOrder((order)=({
          ...order,
          price : localTotal,
          description : localDesc
        }));
        axios.put("http://localhost:8081/eshop/order/update", JSON.stringify(order), {headers: {'content-type':'application/json'}}).then((response)=>{
          console.log(response.data);
        });
      }
  
    return (
      <>
        <div class="container">
          <ul class="list-group">
            <li class="list-group-item d-flex justify-content-between align-items-center bg-success text-light"><h2>Order #: {order.orderID}</h2>
            <div class="align-right"><a class="btn btn-info" role="button" onClick={()=> updateOrder(order.orderID)}><i class="bi bi-plus=square"></i>Update</a></div>
            <div class="align-right"><a class="btn btn-info" role="button" onClick={()=> cancelOrder(order.orderID)}><i class="bi bi-plus=square"></i>Cancel</a></div>
            </li>  
          <div class="card bg-secondary">

            <div class="card-body d-flex justify-content-between align-items-center">
                <h5 class="card-title">Order Placed: {new Date(order.date).toLocaleDateString()}</h5>
                <h5 class="card-title">Items:{count}</h5>
                
                <h5 class="card-title">Total: ${localTotal.toFixed(2)}</h5>   
                <p class="card-text">Order Description: {localDesc}</p>   
            </div>
          </div>
          {oList}
          </ul> 

          <hr style={{height:"2px"}}></hr>             
        </div>                      
      </>
    );
}

export default OrderUpdate;