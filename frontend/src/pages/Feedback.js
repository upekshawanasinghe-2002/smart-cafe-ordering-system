import React, { useState } from "react";
import api from "../services/api";

function Feedback() {

    const [foodId, setFoodId] = useState("");
    const [rating, setRating] = useState(5);
    const [review, setReview] = useState("");
    const [customer, setCustomer] = useState("");
    const [message, setMessage] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

       const token = localStorage.getItem("token");
 

        try {
            await api.post("/api/feedback", {
                foodId: Number(foodId),
                rating: Number(rating),
                review: review,
                customer: customer,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "X-API-KEY": "abc123"
                }
            });

            setMessage("Feedback submitted successfully!");

            setFoodId("");
            setRating(5);
            setReview("");
            setCustomer("");

        } catch (err) {
            setMessage("Failed to submit feedback");
        }
    };

    return (
        <div>
            <h2>Feedback</h2>

            <form onSubmit={handleSubmit}>

                <input
                    type="number"
                    placeholder="Food ID"
                    value={foodId}
                    onChange={(e) => setFoodId(e.target.value)}
                    required
                />

                <select
                    value={rating}
                    onChange={(e) => setRating(e.target.value)}
                >
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>

                <textarea
                    placeholder="Review"
                    value={review}
                    onChange={(e) => setReview(e.target.value)}
                    required
                />

                <input
                    type="text"
                    placeholder="Your Name"
                    value={customer}
                    onChange={(e) => setCustomer(e.target.value)}
                    required
                />

                <button type="submit">Submit</button>

            </form>

            {message && <p>{message}</p>}
        </div>
    );
}

export default Feedback;