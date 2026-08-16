import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function Cart() {

    const [cartItems, setCartItems] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const storedCart = JSON.parse(
            localStorage.getItem("cart") || "[]"
        );
        setCartItems(storedCart);
    }, []);

    const updateQuantity = (index, quantity) => {

        const updatedCart = [...cartItems];
        updatedCart[index].quantity = quantity;

        setCartItems(updatedCart);
        localStorage.setItem("cart", JSON.stringify(updatedCart));
    };

    const removeItem = (index) => {

        const updatedCart = cartItems.filter((_, i) => i !== index);

        setCartItems(updatedCart);
        localStorage.setItem("cart", JSON.stringify(updatedCart));
    };

    const getTotal = () => {
        return cartItems.reduce(
            (sum, item) => sum + item.price * item.quantity,
            0
        );
    };

    const placeOrder = async () => {

        const token = localStorage.getItem("token");
        
        try {

            for (const item of cartItems) {

                await api.post("api/orders", {
                    customerName: "Guest",
                    foodId: item.id,
                    quantity: item.quantity,
                    total: item.price * item.quantity,
                }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "X-API-KEY": "abc123"
                }
            });
            }

            localStorage.removeItem("cart");
            setCartItems([]);

            alert("Order placed successfully!");
            navigate("/orders");

        } catch (err) {
            alert("Failed to place order");
        }
    };

    return (
        <div>
            <h2>Cart</h2>

            {cartItems.length === 0 && <p>Your cart is empty</p>}

            <table border="1" cellPadding="8">
                <thead>
                    <tr>
                        <th>Food</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Subtotal</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {cartItems.map((item, index) => (
                        <tr key={index}>
                            <td>{item.name}</td>
                            <td>
                                <input
                                    type="number"
                                    min="1"
                                    value={item.quantity}
                                    onChange={(e) =>
                                        updateQuantity(index, Number(e.target.value))
                                    }
                                />
                            </td>
                            <td>{item.price}</td>
                            <td>{item.price * item.quantity}</td>
                            <td>
                                <button onClick={() => removeItem(index)}>
                                    Remove
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            <h3>Total: {getTotal()}</h3>

            {cartItems.length > 0 && (
                <button onClick={placeOrder}>Place Order</button>
            )}
        </div>
    );
}

export default Cart;