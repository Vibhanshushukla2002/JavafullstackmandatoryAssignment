import {
    useEffect,
    useState
} from "react";

import API from "../services/api";


function RecentTransactions() {

    const [transactions, setTransactions] =
        useState([]);


    useEffect(() => {

        API.get("/getTransactions")

            .then((res) => {

                console.log("Transactions API Response:", res.data);

                setTransactions(
                    res.data.slice(0, 5)
                );

            })

            .catch((error) => {

                console.error(
                    "Failed to load recent transactions:",
                    error
                );

            });

    }, []);


    return (

        <section className="admin-transactions-card">

            <div className="admin-transactions-header">

                <div className="admin-transactions-title">

                    <div className="admin-chart-icon">
                        ⇄
                    </div>

                    <div>

                        <h3>
                            Recent Transactions
                        </h3>

                        <p>
                            Latest library issue and return activity
                        </p>

                    </div>

                </div>


                <span className="admin-record-count">
                    {transactions.length} Recent
                </span>

            </div>


            {transactions.length > 0 ? (

                <div className="admin-table-wrapper">

                    <table className="admin-dashboard-table">

                        <thead>

                            <tr>
                                <th>Book</th>
                                <th>Student</th>
                                <th>Type</th>
                                <th>Status</th>
                                <th>Fine</th>
                                <th>Date</th>
                            </tr>

                        </thead>


                        <tbody>

                            {transactions.map(
                                (txn, index) => (

                                    <tr key={index}>

                                        <td>

                                            <div className="admin-book-cell">

                                                <span className="admin-book-icon">
                                                    B
                                                </span>

                                                <strong>
                                                    {txn.bookName}
                                                </strong>

                                            </div>

                                        </td>


                                        <td>
                                            {txn.studentName}
                                        </td>


                                        <td>

                                            <span
                                                className={
                                                    txn.type === "Issue"
                                                        ? "admin-badge admin-badge-issue"
                                                        : "admin-badge admin-badge-return"
                                                }
                                            >
                                                {txn.type}
                                            </span>

                                        </td>


                                        <td>

                                            <span
                                                className={
                                                    txn.status === "SUCCESSFUL"
                                                        ? "admin-badge admin-badge-success"
                                                        : "admin-badge admin-badge-danger"
                                                }
                                            >
                                                <span className="admin-badge-dot"></span>

                                                {txn.status}
                                            </span>

                                        </td>


                                        <td>

                                            <strong className="admin-fine-value">
                                                ₹{txn.fine ?? 0}
                                            </strong>

                                        </td>


                                        <td>

                                            {txn.date
                                                ? new Date(
                                                    txn.date
                                                ).toLocaleDateString()
                                                : "-"
                                            }

                                        </td>

                                    </tr>

                                )
                            )}

                        </tbody>

                    </table>

                </div>

            ) : (

                <div className="admin-empty-state">

                    <div className="admin-empty-icon">
                        ⇄
                    </div>

                    <div>

                        <strong>
                            No recent transactions
                        </strong>

                        <p>
                            Recent library activity will appear here.
                        </p>

                    </div>

                </div>

            )}

        </section>

    );
}


export default RecentTransactions;