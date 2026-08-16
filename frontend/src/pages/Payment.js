import React, { useState } from "react";
import api from "../services/api";

function Payment() {

    const [orderId, setOrderId] = useState("");
    const [amount, setAmount] = useState("");
    const [method, setMethod] = useState("CARD");
    const [result, setResult] = useState(null);

    const handlePayment = async (e) => {
        e.preventDefault();

        const token = localStorage.getItem("token"); 

        try {
            const response = await api.post("/api/payments", {
                orderId: Number(orderId),
                amount: Number(amount),
                method: method,
            }, {
            headers: {
                Authorization: `Bearer ${token}`,
                "X-API-KEY": "abc123"
            }
            });

            setResult(response.data);

        } catch (err) {
            setResult({ message: "Payment Failed", status: "ERROR" });
        }
    };

    return (
        <div>
            <h2>Payment</h2>

            <form onSubmit={handlePayment}>

                <input
                    type="number"
                    placeholder="Order ID"
                    value={orderId}
                    onChange={(e) => setOrderId(e.target.value)}
                    required
                />

                <input
                    type="number"
                    placeholder="Amount"
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                    required
                />

                <select
                    value={method}
                    onChange={(e) => setMethod(e.target.value)}
                >
                    <option value="CARD">Card</option>
                    <option value="CASH">Cash</option>
                </select>

                <button type="submit">Pay</button>

            </form>

            {result && (
                <p>
                    {result.message} — {result.status}
                </p>
            )}
        </div>
    );
}

export default Payment;