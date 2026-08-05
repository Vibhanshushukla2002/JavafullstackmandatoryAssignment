import Navbar from "../components/Navbar";
import Sidebar from "../components/Sidebar";
import "../styles/DashboardLayout.css";

function Layout({ children }) {

    return (
        <div className="lms-dashboard-layout">

            <Sidebar />

            <main className="lms-main-area">

                <Navbar />

                <div className="lms-page-content">
                    {children}
                </div>

            </main>

        </div>
    );
}

export default Layout;