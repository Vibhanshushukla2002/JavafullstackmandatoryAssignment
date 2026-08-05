import { useNavigate } from "react-router-dom";

function Navbar() {

    const navigate = useNavigate();

    const username =
        localStorage.getItem("username") || "User";

    const role =
        localStorage.getItem("role") || "Member";


    const logout = () => {

        localStorage.clear();

        navigate("/login");

    };


    const getInitial = () => {

        return username
            .charAt(0)
            .toUpperCase();

    };


    return (

        <nav className="lms-navbar">

            <div className="lms-navbar-inner">

                {/* LEFT SIDE */}

                <div className="lms-navbar-brand">

                    <div className="lms-navbar-logo">
                        📚
                    </div>

                    <h2 className="lms-navbar-title">
                        Library Management System
                    </h2>

                </div>


                {/* RIGHT SIDE */}

                <div className="lms-navbar-user">

                    <div className="lms-user-info">

                        <div className="lms-user-avatar">
                            {getInitial()}
                        </div>

                        <div className="lms-user-details">

                            <strong>
                                {username}
                            </strong>

                            <span>
                                {role.toLowerCase()}
                            </span>

                        </div>

                    </div>


                    <button
                        type="button"
                        className="lms-logout-button"
                        onClick={logout}
                    >
                        <span>Logout</span>
                        <span>→</span>
                    </button>

                </div>

            </div>

        </nav>

    );
}

export default Navbar;