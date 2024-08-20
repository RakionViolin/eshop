import { useEffect, useState } from "react";
import {BrowserRouter, Routes, Route} from "react-router-dom";

import './App.css';
import './style.css';

import Nav from "./components/Nav";
import Home from "./components/Home";
import ProductList from "./components/ProductList";
import Product from "./components/Product";
import Error from "./components/Error";
import Admin from "./components/Admin";
import ProductUpdate from "./components/ProductUpdate"
import ShoppingCart from "./components/ShoppingCart";
import Orders from "./components/Orders";
import OrderView from "./components/OrderView";
import OrderUpdate from "./components/OrderUpdate";

import ProductCreate from './components/ProductCreate';

asdsadsadsadsdasd
zxczxczxcxzczxc



const user = {
  name: 'React',
  imgURL: './coder.jpg',
  imgSize: 90
};

export default function App() {
  const [cart, setCart] = useState(JSON.parse(localStorage.getItem('cart')) || []);  
  const count = cart.reduce((total,product) => total + parseInt(product.quantity), 0);

  useEffect(() => {
    localStorage.setItem('cart', JSON.stringify(cart))}, [cart]
  );


  const handleClick = (product) => {
    if (cart.some((item) => item.id === product.id)) {
        setCart((cart)=> 
          cart.map((item) => item.id === product.id ?
            (item.quantity = parseInt(item.quantity) + parseInt(product.quantity), item) :item));
    }
    else 
      setCart([...cart, product]);
  }

  // const handleOrderClick = (handleOrderClick) => {
  //   if 
  // }


  return ( 
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Nav count={count}/>}>
          <Route index element={<Home user={user}/>}/>
          <Route path="productlist" element={<ProductList/>}/>
          <Route path="shoppingcart/" element={<ShoppingCart cart={cart} setCart={setCart} count={count}/>}/>
          <Route path="product/:id" element={<Product handleClick={handleClick}/>}/>
          <Route path="productCreate" element={<ProductCreate/>}/>
          <Route path="productUpdate/:id" element={<ProductUpdate/>}/>
          <Route path="orders" element={<Orders/>}/>
          <Route path="orderview/:id" element={<OrderView/>}/>
          <Route path="orderupdate/:id" element={<OrderUpdate/>}/>
          <Route path="admin" element={<Admin/>}/>
          <Route path="*" element={<Error/>}/>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
 
