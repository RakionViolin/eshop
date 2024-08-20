import {useNavigate} from "react-router-dom";
import axios from "axios";
import { useState, useEffect } from "react";
import OrderButton from "./OrderButton";

const ShoppingCart = ({cart, setCart, count}) => {
    const price = cart.reduce((accumulator, product) => accumulator + product.price*product.quantity, 0);  


    const handleDelete = (id) => {
      setCart((cart) => cart.filter((product) => product.id !== id));
    }

    const handleUpdate = (id, quantity) => {
      setCart((cart)=>
      cart.map((product)=>
        product.id === id ? {...product, quantity : quantity} : product
    )
      );
    }

    const createOrder = async (order) => {
      axios.post("http://localhost:8081/eshop/order/create", order, {hearders: {'content-type': 'application/json'}}).then((response) => {
        console.log(response.data);
      setCart([]);
      });

    }

    const [computerBase, setComputerBase] = useState([]);


    useEffect(() =>{
      axios.get("http://localhost:8081/eshop/product/get/0").then((response) => {
        setComputerBase(response.data);
      });
    }, []);
    let navigate = useNavigate();
    const order = () => {
      
      const od= {
        
         
          "date": new Date(),
          "products": cart
      };

      console.log(od);
      if(count >= 1){
        createOrder(od); 
      
      }
      navigate("/orders");
    }

    useEffect(() =>{
      const fetchProductQuantities = async () =>{
        try {
          const productQuantities = await Promise.all(
            cart.map(product=>
              axios.get(`http://localhost:8081/eshop/product/get/${product.id}`)
            )
          );
          productQuantities.forEach(response => {
            const productData = response.data;
            console.log(`Product ID: ${productData.id}, Quantity: ${productData.quantity}`)
          });
        }catch(error){
          console.log("Error fetching product quantity: ",error);
        }
      };
      fetchProductQuantities();
    }, [cart]);
    

    const pList = cart.map(product =>
      <li class="list-group-item d-flex justify-content-between align-items-center"
        key={product.id}> 
        <div class="card flex-row w-75">
          <img class="card-img-left" src={'../'+product.image} alt={product.description} width="300"></img>
          <div class="card-body">
            <h4 class="card-title h5, h4-sm">{product.description} </h4>
            <p class="card-text">${product.price}</p>
          </div>
        </div>
        
        <div class="btn-group">
          <OrderButton quantity={product.quantity} handleDelete={handleDelete} id={product.id} handleUpdate={handleUpdate}/>
          <button class="btn btn-danger bi bi-trash" onClick={()=>handleDelete(product.id)}/>
          <a class="btn btn-info" href={'product/'+product.id} role="button"><i class="bi bi-info-square"></i></a>          
        </div>
      </li>
    );  
  
    return (
      <>
        <div class="container d-flex justify-content-center">
          <ul class="list-group">
            <li class="list-group-item d-flex justify-content-between align-items-center bg-success text-light"><h2>Shopping Cart</h2></li>  
            {pList}   
          </ul> 

          <hr style={{height:"2px"}}></hr>
          
          

          <div class="d-flex justify-content-center">
            <div class="container">
              <ul class="list-group">
                <li class="list-group-item d-flex justify-content-between align-items-center bg-warning text-light ">Payment Method</li>
              </ul>

              <div class="row p-2">
                <div class="col" data-value="credit"><img src="./images/paypal.jpg" width="200px" height="60px" alt="Credit Card"></img></div>
                <div class="col" data-value="paypal"><img src="./images/visa.jpg" width="200px" height="60px"  alt="Credit Card"></img></div>                              
              </div>

              <div class="row p-2">
                <div class="col"><lable class="pay">Name on Card</lable></div>
                <div class="col"><input type="text" name="holdername" placeholder="John Smith"></input></div>
              </div>

              <div class="row p-2">
                
                  <div class="col"><label class="pay">Card Number</label></div>
                  <div class="col"><input type="text" name="ccno" id="ccno" placeholder="0000-0000-0000-0000" alt="Credit Card" minlength="19" maxlength="19"></input></div>
              </div>

              <div class="row p-2">
                  <div class="col"><label class="pay">CVV</label></div>
                  <div class="col"><input type="password" name="cvvno" id="cvvno" placeholder="&#9679;&#9679;&#9679;" minlength="3" maxlength="3"></input></div>
              </div>        

             
              <div class="row p-2">
                <div class="col"><label class="pay">Expiration Date</label></div>
                <div class="col"><input type="text" name="exp" id="exp" placeholder="MM/YY" minlength="5" maxlength="5"></input></div>               
              </div>

              <div class="bg-danger d-flex justify-content-between text-light">
                 <div><h5>Total Items</h5></div> <div><h5>{count}</h5></div>            
              </div>
              <div class="bg-danger d-flex justify-content-between text-light">
                  <div><h5>Items Price</h5></div> <div><h5>${price.toFixed(2)}</h5></div>
              </div>

              <div class="bg-danger d-flex justify-content-between text-light">
                 <div><h5>{computerBase.description}</h5></div> <div><h5>${computerBase.price}</h5></div>            
              </div>


              <div class="bg-danger d-flex justify-content-between text-light">
                  <div><h5>TOTAL PRICE</h5></div> <div><h5>${(price+700).toFixed(2)}</h5></div>
              </div>      
              
              <div class="row p-2">
              {count > 0 ? (
                 <button class="btn btn-warning" onClick={()=>order()}> Place Order</button>
              ) : (                 
                 
                 <button class="btn btn-success" onClick={() => navigate("/productlist")}>
                  Select Products to Order
                 </button>
              )
            }
              </div>
            </div>
          </div>
                       
        </div>                      
      </>
    );
}

export default ShoppingCart;