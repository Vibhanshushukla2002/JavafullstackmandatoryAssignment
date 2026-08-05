import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import API from "../services/api";

import "./Signup.css";

function Signup() {

    const navigate = useNavigate();

    const [name, setName] = useState("");
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [age, setAge] = useState("");
    const [country, setCountry] = useState("");

    const register = () => {

        API.post("/register", {
            fullName: name,
            username,
            email,
            password,
            age: Number(age),
            country: country
        })
        .then(() => {
            alert("Registration Successful");
            navigate("/login");
        })
        .catch((error) => {
            console.error("Registration Error:", error.response?.data);
            alert("Registration Failed");
        });
    };

    return (
        <div className="signup-page">

            <div className="signup-card">

                <div className="signup-header">
                    <div className="signup-icon">📚</div>

                    <h2>Create Account</h2>

                    <p>
                        Join our Library Management System
                    </p>
                </div>

                <div className="signup-form">

                    <div className="input-group-custom">
                        <label>Full Name</label>
                        <input
                            type="text"
                            placeholder="Enter your full name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                        />
                    </div>

                    <div className="input-group-custom">
                        <label>Username</label>
                        <input
                            type="text"
                            placeholder="Choose a username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </div>

                    <div className="input-group-custom">
                        <label>Email Address</label>
                        <input
                            type="email"
                            placeholder="Enter your email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>

                    <div className="input-group-custom">
                        <label>Password</label>
                        <input
                            type="password"
                            placeholder="Create a strong password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>

                    <div className="signup-row">

                        <div className="input-group-custom">
                            <label>Age</label>
                            <input
                                type="number"
                                placeholder="Your age"
                                value={age}
                                min="5"
                                onChange={(e) => setAge(e.target.value)}
                            />
                        </div>

                        <div className="input-group-custom">
                            <label>Country</label>
                            <input
                                type="text"
                                placeholder="Your country"
                                value={country}
                                onChange={(e) => setCountry(e.target.value)}
                            />
                        </div>

                    </div>

                    <button
                        className="register-btn"
                        onClick={register}
                    >
                        Create Account
                    </button>

                    <div className="login-link">
                        Already have an account?{" "}
                        <Link to="/login">
                            Sign In
                        </Link>
                    </div>

                </div>

            </div>

        </div>
    );
}

export default Signup;