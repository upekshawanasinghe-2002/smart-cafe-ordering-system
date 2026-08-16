import React from "react";
import { Link } from "react-router-dom";

function Home() {

    return (
        <div>
            <h1>Welcome to Smart Café</h1>
            <p>Order your favorite food online, quick and easy!</p>

            <nav>
                <Link to="/menu">View Menu</Link> |{" "}
                <Link to="/cart">Cart</Link> |{" "}
                <Link to="/orders">My Orders</Link> |{" "}
                <Link to="/feedback">Feedback</Link>
            </nav>
        </div>
    );
}

export default Home;