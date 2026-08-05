import { useEffect, useState } from "react";
import API from "../services/api";

import StatCard from "../components/StatCard";
import BooksPieChart from "../components/BooksPieChart";
import StatsBarChart from "../components/StatsBarChart";
import RecentTransactions from "../components/RecentTransactions";

import "../styles/AdminDashboard.css";


function Dashboard() {

    const [books, setBooks] = useState(0);
    const [issuedBooks, setIssuedBooks] = useState(0);
    const [students, setStudents] = useState(0);
    const [authors, setAuthors] = useState(0);
    const [transactions, setTransactions] = useState(0);
    const [payments, setPayments] = useState(0);
    const [paidPayments, setPaidPayments] = useState(0);
    const [pendingPayments, setPendingPayments] = useState(0);
    const [revenue, setRevenue] = useState(0);


    useEffect(() => {

        API.get("/dashboard/admin")
            .then((res) => {

                setBooks(res.data.totalBooks);
                setIssuedBooks(res.data.issuedBooks);
                setStudents(res.data.totalStudents);
                setAuthors(res.data.totalAuthors);
                setTransactions(res.data.totalTransactions);
                setPayments(res.data.totalPayments);
                setPaidPayments(res.data.paidPayments);
                setPendingPayments(res.data.pendingPayments);
                setRevenue(res.data.totalRevenue);

            })
            .catch((error) => {

                console.error(
                    "Failed to load admin dashboard:",
                    error
                );

            });

    }, []);


    const username =
        localStorage.getItem("username") || "Admin";


    return (

        <div className="admin-dashboard-page">

            {/* =========================================
                PAGE HEADER
            ========================================= */}

            <header className="admin-dashboard-header">

                <div>

                    <span className="admin-dashboard-eyebrow">
                        ADMIN PORTAL
                    </span>

                    <h1>
                        Library Dashboard
                    </h1>

                    <p>
                        Welcome back,{" "}
                        <strong>{username}</strong>
                        <span className="admin-wave">
                            👋
                        </span>
                    </p>

                </div>


                <div className="admin-header-status">

                    <span className="admin-status-dot"></span>

                    System Online

                </div>

            </header>


            {/* =========================================
                STATISTICS
            ========================================= */}

            <section className="admin-stats-grid">

                <StatCard
                    title="Total Books"
                    value={books}
                    icon="📚"
                    subtitle="Complete library collection"
                    variant="purple"
                />

                <StatCard
                    title="Issued Books"
                    value={issuedBooks}
                    icon="📖"
                    subtitle="Currently borrowed books"
                    variant="indigo"
                />

                <StatCard
                    title="Students"
                    value={students}
                    icon="🎓"
                    subtitle="Registered student accounts"
                    variant="blue"
                />

                <StatCard
                    title="Authors"
                    value={authors}
                    icon="✍️"
                    subtitle="Authors in your catalogue"
                    variant="violet"
                />

                <StatCard
                    title="Transactions"
                    value={transactions}
                    icon="⇄"
                    subtitle="Total library transactions"
                    variant="purple"
                />

                <StatCard
                    title="Payments"
                    value={payments}
                    icon="💳"
                    subtitle="Total payment records"
                    variant="indigo"
                />

                <StatCard
                    title="Paid Payments"
                    value={paidPayments}
                    icon="✓"
                    subtitle="Successfully completed payments"
                    variant="green"
                />

                <StatCard
                    title="Pending Payments"
                    value={pendingPayments}
                    icon="!"
                    subtitle="Payments requiring attention"
                    variant="orange"
                />

                <StatCard
                    title="Total Revenue"
                    value={`₹${revenue}`}
                    icon="₹"
                    subtitle="Revenue collected from fines"
                    variant="violet"
                />

            </section>


            {/* =========================================
                ANALYTICS
            ========================================= */}

            <section className="admin-section-heading">

                <div>

                    <span className="admin-section-eyebrow">
                        ANALYTICS
                    </span>

                    <h2>
                        Library Overview
                    </h2>

                    <p>
                        Visual summary of books and library activity.
                    </p>

                </div>

            </section>


            <section className="admin-charts-grid">

                <BooksPieChart
                    totalBooks={books}
                    issuedBooks={issuedBooks}
                />

                <StatsBarChart
                    books={books}
                    students={students}
                    authors={authors}
                    transactions={transactions}
                />

            </section>


            {/* =========================================
                RECENT TRANSACTIONS
            ========================================= */}

            <RecentTransactions />

        </div>

    );
}


export default Dashboard;