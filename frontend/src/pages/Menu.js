import React, { useEffect, useState } from "react";
import api from "../services/api";

function Menu() {

    const [foods, setFoods] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        fetchFoods();
    }, []);

    const fetchFoods = async () => {

        const token = localStorage.getItem("token");

        try {
            const response = await api.get("api/menu", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            setFoods(response.data);
        } catch (err) {
            console.error("Failed to load menu:", err);
            setError("Failed to load menu");
        }
    };

    const addToCart = (food) => {

        const existingCart = JSON.parse(
            localStorage.getItem("cart") || "[]"
        );

        existingCart.push({ ...food, quantity: 1 });

        localStorage.setItem("cart", JSON.stringify(existingCart));

        alert(`${food.name} added to cart`);
    };

    return (
        <div>
            <h2>Menu</h2>

            {error && <p style={{ color: "red" }}>{error}</p>}

            <table border="1" cellPadding="8">
                <thead>
                    <tr>
                        <th>Food Name</th>
                        <th>Price</th>
                        <th>Category</th>
                        <th>Availability</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {foods.map((food) => (
                        <tr key={food.id}>
                            <td>{food.name}</td>
                            <td>{food.price}</td>
                            <td>{food.category}</td>
                            <td>{food.available ? "Available" : "Not Available"}</td>
                            <td>
                                <button onClick={() => addToCart(food)}>
                                    Add to Cart
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default Menu;