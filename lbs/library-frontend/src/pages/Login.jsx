import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import API from "../services/api";
import "../styles/Login.css";
import "../styles/Login.css";

function Login() {

    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [showPassword, setShowPassword] = useState(false);
    const [isLoading, setIsLoading] = useState(false);


    const login = async () => {

        if (!username.trim() || !password.trim()) {
            alert("Please enter username and password");
            return;
        }

        try {

            setIsLoading(true);

            const response = await API.post("/authenticate", {
                username,
                password
            });

            localStorage.setItem("token", response.data.token);
            localStorage.setItem("username", response.data.username);
            localStorage.setItem("role", response.data.role);

            const role = response.data.role;

            if (role === "ADMIN" || role === "ROLE_ADMIN") {

                navigate("/");

            } else if (
                role === "STUDENT" ||
                role === "ROLE_STUDENT"
            ) {

                navigate("/student-dashboard");

            } else {

                navigate("/");

            }

        } catch (error) {

            console.error("Login failed:", error);

            alert("Invalid Username or Password");

        } finally {

            setIsLoading(false);

        }

    };


    const handleKeyDown = (event) => {

        if (event.key === "Enter") {
            login();
        }

    };


    return (

        <main className="auth-login-page">

            {/* ================================
                LEFT BRAND PANEL
            ================================= */}

            <section className="auth-brand-panel">

                <div className="auth-glow auth-glow-one"></div>
                <div className="auth-glow auth-glow-two"></div>

                <div className="auth-pattern"></div>


                <div className="auth-brand-content">

                    <div className="auth-logo">

                        <div className="auth-logo-icon">
                            <span className="book-left"></span>
                            <span className="book-right"></span>
                        </div>

                        <span className="auth-logo-text">
                            Library Management System
                        </span>

                    </div>


                    <div className="auth-hero-content">

                        <span className="auth-eyebrow">
                            MODERN LIBRARY PLATFORM
                        </span>

                        <h1>
                            Your library,
                            <span> beautifully managed.</span>
                        </h1>

                        <p>
                            A smarter way to manage books, students,
                            transactions and library operations from
                            one secure platform.
                        </p>


                        <div className="auth-features">

                            <div className="auth-feature">

                                <span className="auth-check">
                                    ✓
                                </span>

                                <div>
                                    <strong>
                                        Smart book management
                                    </strong>

                                    <small>
                                        Organize and track your complete collection.
                                    </small>
                                </div>

                            </div>


                            <div className="auth-feature">

                                <span className="auth-check">
                                    ✓
                                </span>

                                <div>
                                    <strong>
                                        Real-time transactions
                                    </strong>

                                    <small>
                                        Track issued, returned and overdue books.
                                    </small>
                                </div>

                            </div>


                            <div className="auth-feature">

                                <span className="auth-check">
                                    ✓
                                </span>

                                <div>
                                    <strong>
                                        Secure role-based access
                                    </strong>

                                    <small>
                                        Dedicated experiences for admins and students.
                                    </small>
                                </div>

                            </div>

                        </div>

                    </div>


                    <div className="auth-brand-footer">

                        <div className="auth-status-dot"></div>

                        <span>
                            Secure Library Management System
                        </span>

                    </div>

                </div>

            </section>


            {/* ================================
                RIGHT LOGIN PANEL
            ================================= */}

            <section className="auth-form-panel">

                <div className="auth-form-wrapper">


                    <div className="auth-mobile-logo">

                        <div className="auth-logo-icon">
                            <span className="book-left"></span>
                            <span className="book-right"></span>
                        </div>

                        <span>
                            Library Management System
                        </span>

                    </div>


                    <div className="auth-form-header">

                        <span className="auth-form-label">
                            WELCOME BACK
                        </span>

                        <h2>
                            Sign in to your account
                        </h2>

                        <p>
                            Enter your credentials to continue to your
                            library dashboard.
                        </p>

                    </div>


                    <div className="auth-login-form">


                        {/* USERNAME */}

                        <div className="auth-field">

                            <label htmlFor="login-username">
                                Username
                            </label>

                            <div className="auth-input-container">

                                <span className="auth-user-icon"></span>

                                <input
                                    id="login-username"
                                    type="text"
                                    placeholder="Enter your username"
                                    value={username}
                                    onChange={(event) =>
                                        setUsername(event.target.value)
                                    }
                                    onKeyDown={handleKeyDown}
                                    autoComplete="username"
                                />

                            </div>

                        </div>


                        {/* PASSWORD */}

                        <div className="auth-field">

                            <div className="auth-label-row">

                                <label htmlFor="login-password">
                                    Password
                                </label>

                            </div>


                            <div className="auth-input-container">

                                <span className="auth-lock-icon"></span>

                                <input
                                    id="login-password"
                                    type={
                                        showPassword
                                            ? "text"
                                            : "password"
                                    }
                                    placeholder="Enter your password"
                                    value={password}
                                    onChange={(event) =>
                                        setPassword(event.target.value)
                                    }
                                    onKeyDown={handleKeyDown}
                                    autoComplete="current-password"
                                />


                                <button
                                    type="button"
                                    className="auth-password-toggle"
                                    onClick={() =>
                                        setShowPassword(!showPassword)
                                    }
                                    aria-label={
                                        showPassword
                                            ? "Hide password"
                                            : "Show password"
                                    }
                                >

                                    {showPassword ? "Hide" : "Show"}

                                </button>

                            </div>

                        </div>


                        {/* LOGIN BUTTON */}

                        <button
                            type="button"
                            className="auth-submit-button"
                            onClick={login}
                            disabled={isLoading}
                        >

                            {isLoading
                                ? "Signing in..."
                                : "Sign in"
                            }

                            {!isLoading && (
                                <span className="auth-button-arrow">
                                    →
                                </span>
                            )}

                        </button>

                    </div>


                    <div className="auth-divider">

                        <span></span>

                        <p>New to the platform?</p>

                        <span></span>

                    </div>


                    <div className="auth-signup">

                        <p>
                            Don't have an account?
                        </p>

                        <Link to="/signup">
                            Create your account
                            <span>→</span>
                        </Link>

                    </div>


                    <p className="auth-security-text">

                        <span className="auth-mini-lock"></span>

                        Your credentials are securely protected.

                    </p>

                </div>

            </section>

        </main>

    );

}

export default Login;