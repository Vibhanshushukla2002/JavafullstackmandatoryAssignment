import { NavLink } from "react-router-dom";

function Sidebar() {

    const role = localStorage.getItem("role");

    const getNavLinkClass = ({ isActive }) => {
        return isActive
            ? "lms-sidebar-link active"
            : "lms-sidebar-link";
    };

    return (

        <aside className="lms-sidebar">

            {/* BRAND */}

            <div className="lms-sidebar-brand">

                <div className="lms-brand-icon">
                    📚
                </div>

                <div className="lms-brand-text">

                    <strong>
                        Library Management
                    </strong>

                    <span>
                        Smart Library Platform
                    </span>

                </div>

            </div>


            {/* MENU LABEL */}

            <div className="lms-sidebar-menu-label">
                Main Menu
            </div>


            {/* NAVIGATION */}

            <ul className="lms-sidebar-nav">

                <li className="lms-sidebar-item">

                    <NavLink
                        to={
                            role === "STUDENT"
                                ? "/student-dashboard"
                                : "/"
                        }
                        className={getNavLinkClass}
                        end={role !== "STUDENT"}
                    >

                        <span className="lms-nav-icon">
                            ⌂
                        </span>

                        <span>
                            Dashboard
                        </span>

                    </NavLink>

                </li>


                <li className="lms-sidebar-item">

                    <NavLink
                        to="/books"
                        className={getNavLinkClass}
                    >

                        <span className="lms-nav-icon">
                            ▤
                        </span>

                        <span>
                            Books
                        </span>

                    </NavLink>

                </li>


                {role === "ADMIN" && (

                    <>

                        <li className="lms-sidebar-item">

                            <NavLink
                                to="/students"
                                className={getNavLinkClass}
                            >

                                <span className="lms-nav-icon">
                                    ◉
                                </span>

                                <span>
                                    Students
                                </span>

                            </NavLink>

                        </li>


                        <li className="lms-sidebar-item">

                            <NavLink
                                to="/authors"
                                className={getNavLinkClass}
                            >

                                <span className="lms-nav-icon">
                                    ✎
                                </span>

                                <span>
                                    Authors
                                </span>

                            </NavLink>

                        </li>


                        <li className="lms-sidebar-item">

                            <NavLink
                                to="/transactions"
                                className={getNavLinkClass}
                            >

                                <span className="lms-nav-icon">
                                    ⇄
                                </span>

                                <span>
                                    Transactions
                                </span>

                            </NavLink>

                        </li>

                    </>

                )}


                <li className="lms-sidebar-item">

                    <NavLink
                        to="/issue-book"
                        className={getNavLinkClass}
                    >

                        <span className="lms-nav-icon">
                            ↗
                        </span>

                        <span>
                            Issue Book
                        </span>

                    </NavLink>

                </li>


                <li className="lms-sidebar-item">

                    <NavLink
                        to="/return-book"
                        className={getNavLinkClass}
                    >

                        <span className="lms-nav-icon">
                            ↩
                        </span>

                        <span>
                            Return Book
                        </span>

                    </NavLink>

                </li>

            </ul>


            {/* SIDEBAR FOOTER */}

            <div className="lms-sidebar-footer">

                <div className="lms-system-status">

                    <span className="lms-status-dot"></span>

                    <span>
                        Secure Library Management System
                    </span>

                </div>

            </div>

        </aside>
    );
}

export default Sidebar;