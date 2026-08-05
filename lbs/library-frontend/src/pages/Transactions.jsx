import { useEffect, useState } from "react";
import API from "../services/api";
import "../styles/ManagementPages.css";

function Transactions() {

    const [transactions, setTransactions] = useState([]);

    const [search, setSearch] = useState("");

    const [filter, setFilter] = useState("ALL");

    const [message, setMessage] = useState("");

    const [showToast, setShowToast] = useState(false);


    /* =========================================
       LOAD TRANSACTIONS
    ========================================= */

    useEffect(() => {
        loadTransactions();
    }, []);


    /* =========================================
       SHOW ERROR TOAST
    ========================================= */

    const showErrorToast = (error) => {

        let errorMessage = "Something went wrong";

        if (typeof error.response?.data === "string") {

            errorMessage = error.response.data;

        } else if (error.response?.data?.message) {

            errorMessage = error.response.data.message;

        } else if (error.message) {

            errorMessage = error.message;
        }

        setMessage(errorMessage);

        setShowToast(true);

        setTimeout(() => {
            setShowToast(false);
        }, 2500);
    };


    /* =========================================
       LOAD ALL TRANSACTIONS
    ========================================= */

    const loadTransactions = () => {

        API.get("/getTransactions")

            .then((response) => {

                setTransactions(
                    response.data || []
                );

            })

            .catch((error) => {

                console.error(
                    "Failed to load transactions:",
                    error
                );

                showErrorToast(error);
            });
    };


    /* =========================================
       SEARCH TRANSACTION
    ========================================= */

    const searchTransaction = () => {

        if (search.trim() === "") {

            loadTransactions();

            return;
        }

        API.get(
            `/searchTransaction?keyword=${encodeURIComponent(search)}`
        )

            .then((response) => {

                setTransactions(
                    response.data || []
                );

            })

            .catch((error) => {

                console.error(
                    "Failed to search transactions:",
                    error
                );

                showErrorToast(error);
            });
    };


    /* =========================================
       SEARCH USING ENTER KEY
    ========================================= */

    const handleSearchKeyDown = (event) => {

        if (event.key === "Enter") {
            searchTransaction();
        }
    };


    /* =========================================
       FILTERED TRANSACTIONS
    ========================================= */

    const filteredTransactions = transactions.filter(
        (transaction) => {

            if (filter === "ALL") {
                return true;
            }

            return transaction.type === filter;
        }
    );


    /* =========================================
       TRANSACTION COUNTS
    ========================================= */

    const issueCount = transactions.filter(
        (transaction) =>
            transaction.type === "Issue"
    ).length;


    const returnCount = transactions.filter(
        (transaction) =>
            transaction.type === "Return"
    ).length;


    const totalFine = transactions.reduce(
        (total, transaction) => {

            return total + Number(
                transaction.fine || 0
            );

        },
        0
    );


    return (

        <main className="management-page">


            {/* =====================================
                TOAST MESSAGE
            ===================================== */}

            {showToast && (

                <div className="management-toast">

                    <span>
                        {message}
                    </span>

                    <button
                        type="button"
                        onClick={() =>
                            setShowToast(false)
                        }
                    >
                        ×
                    </button>

                </div>

            )}


            {/* =====================================
                PAGE HEADER
            ===================================== */}

            <header className="management-header">

                <div>

                    <span className="management-eyebrow">
                        LIBRARY ACTIVITY
                    </span>

                    <h1>
                        Transaction History
                    </h1>

                    <p>
                        Track book issues, returns, transaction
                        status and library fines.
                    </p>

                </div>


                <span
                    className="
                        management-badge
                        management-badge-purple
                    "
                >
                    {filteredTransactions.length} Transactions Shown
                </span>

            </header>


            {/* =====================================
                SUMMARY CARDS
            ===================================== */}

            <section className="transaction-summary-grid">


                <article className="transaction-summary-card">

                    <div
                        className="
                            transaction-summary-icon
                            transaction-icon-purple
                        "
                    >
                        ⇄
                    </div>


                    <div>

                        <span>
                            Total Transactions
                        </span>

                        <strong>
                            {transactions.length}
                        </strong>

                        <small>
                            Complete library activity
                        </small>

                    </div>

                </article>


                <article className="transaction-summary-card">

                    <div
                        className="
                            transaction-summary-icon
                            transaction-icon-blue
                        "
                    >
                        ↗
                    </div>


                    <div>

                        <span>
                            Books Issued
                        </span>

                        <strong>
                            {issueCount}
                        </strong>

                        <small>
                            Issue transactions recorded
                        </small>

                    </div>

                </article>


                <article className="transaction-summary-card">

                    <div
                        className="
                            transaction-summary-icon
                            transaction-icon-green
                        "
                    >
                        ↩
                    </div>


                    <div>

                        <span>
                            Books Returned
                        </span>

                        <strong>
                            {returnCount}
                        </strong>

                        <small>
                            Return transactions recorded
                        </small>

                    </div>

                </article>


                <article className="transaction-summary-card">

                    <div
                        className="
                            transaction-summary-icon
                            transaction-icon-orange
                        "
                    >
                        ₹
                    </div>


                    <div>

                        <span>
                            Total Fine
                        </span>

                        <strong>
                            ₹{totalFine}
                        </strong>

                        <small>
                            Fine amount in current records
                        </small>

                    </div>

                </article>

            </section>


            {/* =====================================
                SEARCH AND FILTER
            ===================================== */}

            <section className="management-card">

                <div className="management-card-header">

                    <div className="management-card-icon">
                        ⌕
                    </div>


                    <div>

                        <h2>
                            Search & Filter
                        </h2>

                        <p>
                            Search transactions by book or student
                            and filter by transaction type.
                        </p>

                    </div>

                </div>


                <div className="transaction-search-grid">


                    <div className="management-field">

                        <label>
                            Search Transactions
                        </label>

                        <input
                            type="text"
                            className="management-input"
                            placeholder="Search by book or student..."
                            value={search}
                            onChange={(event) =>
                                setSearch(event.target.value)
                            }
                            onKeyDown={handleSearchKeyDown}
                        />

                    </div>


                    <div className="management-field">

                        <label>
                            Transaction Type
                        </label>

                        <select
                            className="management-select"
                            value={filter}
                            onChange={(event) =>
                                setFilter(event.target.value)
                            }
                        >
                            <option value="ALL">
                                All Transactions
                            </option>

                            <option value="Issue">
                                Issue
                            </option>

                            <option value="Return">
                                Return
                            </option>

                        </select>

                    </div>


                    <div className="management-field">

                        <label>
                            Action
                        </label>

                        <button
                            type="button"
                            className="
                                management-button
                                management-button-primary
                            "
                            onClick={searchTransaction}
                        >
                            <span>⌕</span>

                            Search
                        </button>

                    </div>

                </div>

            </section>


            {/* =====================================
                TRANSACTION TABLE
            ===================================== */}

            <section className="management-card">

                <div className="management-card-header">

                    <div className="management-card-icon">
                        ⇄
                    </div>


                    <div>

                        <h2>
                            Library Transactions
                        </h2>

                        <p>
                            Detailed history of all book issue
                            and return operations.
                        </p>

                    </div>

                </div>


                {filteredTransactions.length > 0 ? (

                    <div className="management-table-wrapper">

                        <table className="management-table transaction-table">

                            <thead>

                                <tr>

                                    <th>
                                        Transaction ID
                                    </th>

                                    <th>
                                        Book
                                    </th>

                                    <th>
                                        Student
                                    </th>

                                    <th>
                                        Type
                                    </th>

                                    <th>
                                        Status
                                    </th>

                                    <th>
                                        Fine
                                    </th>

                                    <th>
                                        Date
                                    </th>

                                </tr>

                            </thead>


                            <tbody>

                                {filteredTransactions.map(
                                    (transaction, index) => (

                                        <tr
                                            key={
                                                transaction.transactionId ||
                                                index
                                            }
                                        >

                                            {/* TRANSACTION ID */}

                                            <td>

                                                <span className="transaction-id">

                                                    {transaction.transactionId
                                                        ? `${String(
                                                            transaction.transactionId
                                                        ).substring(0, 8)}...`
                                                        : "—"
                                                    }

                                                </span>

                                            </td>


                                            {/* BOOK */}

                                            <td>

                                                <div className="transaction-entity-cell">

                                                    <span className="transaction-book-icon">
                                                        B
                                                    </span>

                                                    <strong className="management-primary-text">
                                                        {transaction.bookName || "—"}
                                                    </strong>

                                                </div>

                                            </td>


                                            {/* STUDENT */}

                                            <td>

                                                <div className="transaction-entity-cell">

                                                    <span className="transaction-student-avatar">

                                                        {transaction.studentName
                                                            ? transaction.studentName
                                                                .charAt(0)
                                                                .toUpperCase()
                                                            : "S"
                                                        }

                                                    </span>

                                                    <span>
                                                        {transaction.studentName || "—"}
                                                    </span>

                                                </div>

                                            </td>


                                            {/* TYPE */}

                                            <td>

                                                <span
                                                    className={
                                                        transaction.type === "Issue"
                                                            ? "management-badge management-badge-blue"
                                                            : "management-badge management-badge-success"
                                                    }
                                                >

                                                    {transaction.type === "Issue"
                                                        ? "↗ "
                                                        : "↩ "
                                                    }

                                                    {transaction.type || "Unknown"}

                                                </span>

                                            </td>


                                            {/* STATUS */}

                                            <td>

                                                <span
                                                    className={
                                                        transaction.status === "SUCCESSFUL" ||
                                                        transaction.status === "SUCCESS"
                                                            ? "management-badge management-badge-success"
                                                            : "management-badge management-badge-purple"
                                                    }
                                                >

                                                    <span className="transaction-status-dot"></span>

                                                    {transaction.status || "Unknown"}

                                                </span>

                                            </td>


                                            {/* FINE */}

                                            <td>

                                                <strong
                                                    className={
                                                        Number(transaction.fine || 0) > 0
                                                            ? "transaction-fine transaction-fine-pending"
                                                            : "transaction-fine"
                                                    }
                                                >
                                                    ₹{transaction.fine ?? 0}
                                                </strong>

                                            </td>


                                            {/* DATE */}

                                            <td>

                                                <span className="transaction-date">

                                                    {transaction.date
                                                        ? new Date(
                                                            transaction.date
                                                        ).toLocaleString()
                                                        : "—"
                                                    }

                                                </span>

                                            </td>

                                        </tr>

                                    )
                                )}

                            </tbody>

                        </table>

                    </div>

                ) : (

                    <div className="management-empty-state">

                        <div className="management-empty-icon">
                            ⇄
                        </div>


                        <div>

                            <strong>
                                No transactions found
                            </strong>

                            <p>
                                No transactions match your current
                                search or selected filter.
                            </p>

                        </div>

                    </div>

                )}

            </section>

        </main>

    );
}

export default Transactions;