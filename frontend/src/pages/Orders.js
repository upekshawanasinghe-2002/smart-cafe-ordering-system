import React, { useEffect, useState } from "react";
import api from "../services/api";

function Orders() {

    const [orders, setOrders] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        fetchOrders();
    }, []);

    const fetchOrders = async () => {
        try {
            const response = await api.get("/orders");
            setOrders(response.data);
        } catch (err) {
            setError("Failed to load orders");
        }
    };

    const cancelOrder = async (id) => {
        try {
            await api.delete(`/orders/${id}`);
            fetchOrders();
        } catch (err) {
            alert("Failed to cancel order");
        }
    };

    return (
        <div>
            <h2>My Orders</h2>

            {error && <p style={{ color: "red" }}>{error}</p>}

            <table border="1" cellPadding="8">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Food ID</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {orders.map((order) => (
                        <tr key={order.id}>
                            <td>{order.id}</td>
                            <td>{order.foodId}</td>
                            <td>{order.quantity}</td>
                            <td>{order.total}</td>
                            <td>{order.status}</td>
                            <td>
                                <button onClick={() => cancelOrder(order.id)}>
                                    Cancel
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default Orders;