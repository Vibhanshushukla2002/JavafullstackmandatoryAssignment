import { useEffect, useState } from "react";
import api from "../services/api";

export default function PaymentHistory() {

    const [payments, setPayments] = useState([]);

    useEffect(() => {
        loadPayments();
    }, []);

    const loadPayments = async () => {
        try {
            const response = await api.get("/payment/my");
            setPayments(response.data);
        } catch (e) {
            console.log(e);
        }
    };

    return (
        <div className="container mt-4">

            <h2>My Payment History</h2>

            <table className="table table-bordered table-hover">

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Book ID</th>
                        <th>Amount</th>
                        <th>Status</th>
                        <th>Date</th>
                        <th>Transaction ID</th>
                    </tr>
                </thead>

                <tbody>

                    {payments.map((payment) => (

                        <tr key={payment.id}>

                            <td>{payment.id}</td>

                            <td>{payment.bookId}</td>

                            <td>₹ {payment.amount}</td>

                            <td>
                                {payment.status === "PAID"
                                    ? <span className="badge bg-success">PAID</span>
                                    : <span className="badge bg-warning text-dark">PENDING</span>}
                            </td>

                            <td>{payment.paymentDate}</td>

                            <td>{payment.razorpayPaymentId || "-"}</td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </div>
    );
}