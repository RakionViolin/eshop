import { useState, useEffect } from "react";
import axios from "axios";

const Orders = () => {
  const [orders, setOrders] = useState([]);
  const [sortOrder, setSortOrder] = useState({ column: "orderID", order: "asc" }); // Initialize sortOrder with column and order
  const [allOrders, setAllorders] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8081/eshop/order/getall").then((response) => {
      setAllorders(response.data);
      setOrders(response.data);
    });
    console.log (sortOrder);
  }, []);

  // Function to sort orders by column
  const sortOrders = (column) => {
    const newOrder = sortOrder.order === "asc" ? "desc" : "asc"; // Toggle order between asc and desc
    const sortedOrders = [...allOrders].sort((a, b) => {
      if (newOrder === "asc") {
        return a[column] > b[column] ? 1 : -1; // Sort ascending
      } else {
        return a[column] < b[column] ? 1 : -1; // Sort descending
      }
    });
    setSortOrder({ column, order: newOrder }); // Update sortOrder state
    setOrders(sortedOrders); // Update orders state with sorted orders
  };

  const oList = orders.map((order) => (
    <tr key={order.id}>
      <td class={sortOrder.column === "orderID" ? "table-info": ""}>{order.orderID}</td>
      <td >{order.description}</td>
      <td class={sortOrder.column === "price" ? "table-info": ""}>${order.price}</td>
      <td class={sortOrder.column === "date" ? "table-info": ""}>{new Date(order.date).toLocaleDateString()}</td>
      <td className="text-end">
        <div className="btn-group ">
          <a
            className="btn btn-success"
            href={"orderview/" + order.orderID}
            role="button"
          >
            <i className="bi bi-info-square"></i> View
          </a>
          <a
            className="btn btn-warning"
            href={"orderUpdate/" + order.orderID}
            role="button"
          >
            <i className="bi bi-info-square"></i> Update
          </a>
          <button
            type="button"
            onClick={() => cancelOrder(order.orderID)}
            className="btn btn-danger"
          >
            <i className="bi bi-x-square"></i> Cancel
          </button>
        </div>
      </td>
    </tr>
  ));

  const cancelOrder = async (id) => {
    console.log(id);
    axios
      .delete("http://localhost:8081/eshop/order/cancel/" + id)
      .then((response) => {
        console.log(response);
        const updatedOrders = orders.filter((order) => order.orderID !== id);
        setOrders(updatedOrders);
      });
  };

  return (
    <>
      <div className="container">
        <li className="list-group-teim d-flex justify-content-between align-items-center bg-success text-right">
          <h2>Order List</h2>
        </li>
        <table className="table table-hover">
          <thead className="table-info">
            <tr>
              <th
                scope="col"
                style={{ cursor: "pointer" }}
                onClick={() => sortOrders("orderID")}
                
              >
                ID{" "}
                <i
                  className={
                    sortOrder.column === "orderID" && sortOrder.order === "asc"
                      ? "bi bi-sort-up"
                      : "bi bi-sort-down"
                  }
                ></i>
              </th>
              <th scope="col">Description</th>
              <th
                scope="col"
                style={{ cursor: "pointer" }}
                onClick={() => sortOrders("price")} // Sort by price when clicked
                
              >
                Price{" "}
                <i
                  className={
                    sortOrder.column === "price" && sortOrder.order === "asc"
                      ? "bi bi-sort-up"
                      : "bi bi-sort-down"
                  }
                ></i>
              </th>
              <th
                scope="col"
                style={{ cursor: "pointer" }}
                onClick={() => sortOrders("date")}
               
              >
                Date{" "}
                <i
                  className={
                    sortOrder.column === "date" && sortOrder.order === "asc"
                      ? "bi bi-sort-up" : "bi bi-sort-down"
                  }
                ></i>
              </th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody>{oList}</tbody>
        </table>
      </div>
    </>
  );
};

export default Orders;
