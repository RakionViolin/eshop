import {Outlet, Link} from "react-router-dom";

const Nav = ({count}) => {
    return (
        <>
            <nav class="navbar navbar-expand-lg navbar-red">
                <div class="container-fluid">
                    <a class="navbar-brand" href="/"><h6>E<br/>SHOP</h6></a>
                    <div class="collapse navbar-collapse" id="eshopnavbar"> 
                    <ul class = "navbar-nav me-auto mb-2 nav-fill w-100">
                        <li class="nav-item d-flex">
                            <Link class="nav-link" to="/">Home</Link>
                        </li>
                        <li class="nav-item d-flex">
                            <Link class="nav-link" to="/productlist">Products</Link>
                        </li>
                        <li class="nav-item d-flex">
                            <Link class="nav-link" to="/orders">Orders</Link>
                        </li>
                        <li class="nav-item d-flex">
                            <Link class="nav-link" to="/admin">Admin</Link>
                        </li>
                        <li class="nav-item d-flex">
                             <Link class="nav-link" to="/shoppingcart">
                                <div class="cart"><span class="count">{count}</span><i class="bi bi-cart"></i></div>
                            </Link>
                        </li>                       
                    </ul>
                    </div>
                </div>                
            </nav>

            <Outlet/>
        </>
    )
};

export default Nav;