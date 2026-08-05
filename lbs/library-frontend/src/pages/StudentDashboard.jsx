import { useEffect, useState } from "react";
import API from "../services/api";
import "../styles/StudentDashboard.css";
import { useNavigate } from "react-router-dom";

function StudentDashboard() {

    const navigate = useNavigate();

    const [dashboard, setDashboard] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [profile, setProfile] = useState(null);


    const loadDashboard = async () => {
        try {
            setLoading(true);

            const response = await API.get("/dashboard/student");

            setDashboard(response.data);
            setError("");

        } catch (err) {
            console.error(err);
            setError("Unable to load student dashboard");
        } finally {
            setLoading(false);
        }
    };

  useEffect(() => {

      loadDashboard();
      loadProfile();

  }, []);

const loadProfile = async () => {

    try {

        const response = await API.get("/student/profile");

        console.log("Student Profile:", response.data);

        setProfile(response.data);

    } catch (error) {

        console.error(
            "Unable to load student profile:",
            error
        );
    }
};



//     const handlePayFine = (item) => {
//         console.log("Selected fine:", item);
//         alert(`Paying fine of ₹${item.fineAmount}`);
//     };

    if (loading) {
        return (
            <div className="container mt-5">
                <h4>Loading dashboard...</h4>
            </div>
        );
    }

    if (error) {
        return (
            <div className="container mt-5">
                <div className="alert alert-danger">
                    {error}
                </div>
            </div>
        );
    }
   const handlePayFine = async (item) => {

       try {

           console.log("Starting payment for transaction:", item.transactionId);

           // Step 1: Backend se Razorpay order create karo
           const response = await API.post("/payment/start", {
               transactionId: item.transactionId
           });

           console.log("Payment start response:", response.data);

           const paymentData = response.data;

           // Step 2: Check Razorpay script loaded hai ya nahi
           if (!window.Razorpay) {
               alert("Razorpay SDK failed to load");
               return;
           }

           // Step 3: Razorpay Checkout options
           const options = {

               key: paymentData.key,

               amount: paymentData.amount,

               currency: paymentData.currency,

               order_id: paymentData.orderId,

               name: "Library Management System",

               description: `Fine Payment for ${item.bookName}`,

              handler: async function (razorpayResponse) {

                  try {

                      console.log(
                          "Razorpay payment successful:",
                          razorpayResponse
                      );

                      const verifyResponse = await API.post("/payment/verify", {

                          paymentId: paymentData.paymentId,

                          razorpayOrderId: razorpayResponse.razorpay_order_id,

                          razorpayPaymentId: razorpayResponse.razorpay_payment_id,

                          razorpaySignature: razorpayResponse.razorpay_signature

                      });

                      console.log(
                          "Payment verification response:",
                          verifyResponse.data
                      );

                      alert("Fine paid successfully");

                      // Refresh student dashboard
                      await loadDashboard();

                  } catch (error) {

                      console.error(
                          "Payment verification failed:",
                          error
                      );

                      alert(
                          error.response?.data ||
                          "Payment verification failed"
                      );
                  }
              },

               theme: {
                   color: "#0d6efd"
               }
           };

           // Step 4: Open Razorpay popup
           const razorpay = new window.Razorpay(options);

           razorpay.open();

       } catch (error) {

           console.error("Payment start failed:", error);

           alert(
               error.response?.data ||
               "Unable to start payment"
           );
       }
   };

const handleDownloadReceipt = async (item) => {
    try {

        console.log("Downloading receipt for payment:", item.paymentId);

        const response = await API.get(
            `/payment/${item.paymentId}/receipt`,
            {
                responseType: "blob"
            }
        );

        const blob = new Blob(
            [response.data],
            { type: "application/pdf" }
        );

        const url = window.URL.createObjectURL(blob);

        const link = document.createElement("a");
        link.href = url;
        link.download = `LibraryReceipt-${item.paymentId}.pdf`;

        document.body.appendChild(link);
        link.click();

        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);

    } catch (error) {

        console.error("Receipt download failed:", error);

        alert(
            error.response?.data ||
            "Unable to download receipt"
        );
    }
};

    return (
        <div className="student-dashboard-page">

            {/* =========================
                PAGE HEADER
            ========================= */}
            <header className="student-dashboard-header">

                <div>
                    <span className="student-dashboard-eyebrow">
                        STUDENT PORTAL
                    </span>

                    <h1>
                        Student Dashboard
                    </h1>

                    <p>
                        Welcome back,{" "}
                        <strong>{dashboard?.username}</strong>
                        <span className="welcome-wave">👋</span>
                    </p>
                </div>

               {profile && (

                   <div className="student-profile-card">

                      <div className="student-profile-avatar">

                          {profile?.profilePicture ? (

                              <img
                                  src={`http://localhost:8080/uploads/profile-pictures/${profile.profilePicture}`}
                                  alt="Profile"
                                  className="student-profile-image"
                              />

                          ) : (

                              profile?.name
                                  ?.charAt(0)
                                  ?.toUpperCase() || "U"

                          )}

                      </div>

                       <div className="student-profile-main">

                           <div className="student-profile-name-row">

                               <div>
                                   <strong className="student-profile-name">
                                       {profile.name}
                                   </strong>

                                   <span className="student-profile-role">
                                       {profile.role}
                                   </span>
                               </div>

                               <span className="student-profile-status">
                                   <span></span>
                                   {profile.cardStatus}
                               </span>

                           </div>

                           <div className="student-profile-details">

                               <div>
                                   <span>Student ID</span>
                                   <strong>#{profile.studentId}</strong>
                               </div>

                               <div>
                                   <span>Library Card</span>
                                   <strong>#{profile.cardId}</strong>
                               </div>

                           </div>

                           <button
                               type="button"
                               className="student-view-profile-btn"
                               onClick={() => navigate("/student-profile")}
                           >
                               View Profile
                               <span>→</span>
                           </button>

                       </div>

                   </div>

               )}

            </header>


            {/* =========================
                STATISTICS CARDS
            ========================= */}
            <section className="student-stats-grid">

                <article className="student-stat-card">

                    <div className="stat-card-top">

                        <div className="stat-icon stat-icon-purple">
                            <span className="stat-book-icon">▣</span>
                        </div>

                        <span className="stat-trend">
                            Current
                        </span>

                    </div>

                    <div className="stat-card-content">

                        <span className="stat-label">
                            Active Books
                        </span>

                        <strong className="stat-value">
                            {dashboard?.activeBooks ?? 0}
                        </strong>

                        <small>
                            Currently issued books
                        </small>

                    </div>

                </article>


                <article className="student-stat-card">

                    <div className="stat-card-top">

                        <div className="stat-icon stat-icon-indigo">
                            <span>↗</span>
                        </div>

                        <span className="stat-trend">
                            All time
                        </span>

                    </div>

                    <div className="stat-card-content">

                        <span className="stat-label">
                            Total Books Issued
                        </span>

                        <strong className="stat-value">
                            {dashboard?.totalBooksIssued ?? 0}
                        </strong>

                        <small>
                            Your complete borrowing activity
                        </small>

                    </div>

                </article>


                <article className="student-stat-card">

                    <div className="stat-card-top">

                        <div className="stat-icon stat-icon-orange">
                            <span>₹</span>
                        </div>

                        <span className="stat-trend">
                            Payment
                        </span>

                    </div>

                    <div className="stat-card-content">

                        <span className="stat-label">
                            Pending Fine
                        </span>

                        <strong className="stat-value">
                            ₹{dashboard?.pendingFine ?? 0}
                        </strong>

                        <small>
                            Outstanding fine amount
                        </small>

                    </div>

                </article>


                <article className="student-stat-card">

                    <div className="stat-card-top">

                        <div className="stat-icon stat-icon-red">
                            <span>!</span>
                        </div>

                        <span className="stat-trend">
                            Attention
                        </span>

                    </div>

                    <div className="stat-card-content">

                        <span className="stat-label">
                            Overdue Books
                        </span>

                        <strong className="stat-value">
                            {dashboard?.overdueBooks ?? 0}
                        </strong>

                        <small>
                            Books past their due date
                        </small>

                    </div>

                </article>

            </section>


            {/* =========================
                NEAREST DUE BOOK
            ========================= */}
            <section className="student-content-card due-book-card">

                <div className="student-card-header">

                    <div className="student-card-title">

                        <div className="section-icon section-icon-purple">
                            <span>⌑</span>
                        </div>

                        <div>
                            <h2>Nearest Due Book</h2>

                            <p>
                                Your upcoming book return deadline
                            </p>
                        </div>

                    </div>

                </div>


                {dashboard?.nearestDueBook ? (

                    <div className="due-book-grid">

                        <div className="due-detail">

                            <span className="due-detail-label">
                                Book
                            </span>

                            <strong>
                                {dashboard.nearestDueBook.bookName}
                            </strong>

                        </div>


                        <div className="due-detail">

                            <span className="due-detail-label">
                                Due Date
                            </span>

                            <strong>
                                {dashboard.nearestDueBook.dueDate}
                            </strong>

                        </div>


                        <div className="due-detail">

                            <span className="due-detail-label">
                                Days Left
                            </span>

                            <strong>
                                {dashboard.nearestDueBook.daysLeft}
                            </strong>

                        </div>


                        <div className="due-detail">

                            <span className="due-detail-label">
                                Current Fine
                            </span>

                            <strong>
                                ₹{dashboard.nearestDueBook.fine}
                            </strong>

                        </div>

                    </div>

                ) : (

                    <div className="student-empty-state">

                        <div className="empty-state-icon">
                            ✓
                        </div>

                        <div>
                            <strong>
                                No upcoming deadlines
                            </strong>

                            <p>
                                You don't have any active book currently issued.
                            </p>
                        </div>

                    </div>

                )}

            </section>


            {/* =========================
                ACTIVE BOOKS
            ========================= */}
            <section className="student-content-card">

                <div className="student-card-header">

                    <div className="student-card-title">

                        <div className="section-icon section-icon-indigo">
                            <span>▤</span>
                        </div>

                        <div>
                            <h2>Active Books</h2>

                            <p>
                                Books currently issued to your account
                            </p>
                        </div>

                    </div>


                    <span className="section-count-badge">
                        {dashboard?.activeBooksList?.length ?? 0} Active
                    </span>

                </div>


                {dashboard?.activeBooksList?.length > 0 ? (

                    <div className="student-table-wrapper">

                        <table className="student-dashboard-table">

                            <thead>
                                <tr>
                                    <th>Book</th>
                                    <th>Issue Date</th>
                                    <th>Due Date</th>
                                    <th>Days Left</th>
                                    <th>Fine</th>
                                    <th>Status</th>
                                </tr>
                            </thead>

                            <tbody>

                                {dashboard.activeBooksList.map((book) => (

                                    <tr key={book.bookId}>

                                        <td>
                                            <div className="table-book-name">

                                                <span className="table-book-icon">
                                                    B
                                                </span>

                                                <strong>
                                                    {book.bookName}
                                                </strong>

                                            </div>
                                        </td>

                                        <td>{book.issueDate}</td>

                                        <td>{book.dueDate}</td>

                                        <td>
                                            <span className="days-left-text">
                                                {book.daysLeft}
                                            </span>
                                        </td>

                                        <td>
                                            <strong>
                                                ₹{book.fine}
                                            </strong>
                                        </td>

                                        <td>

                                            <span
                                                className={`student-status-badge ${
                                                    book.status === "OVERDUE"
                                                        ? "status-overdue"
                                                        : "status-active"
                                                }`}
                                            >
                                                <span></span>
                                                {book.status}
                                            </span>

                                        </td>

                                    </tr>

                                ))}

                            </tbody>

                        </table>

                    </div>

                ) : (

                    <div className="student-empty-state">

                        <div className="empty-state-icon">
                            ✓
                        </div>

                        <div>
                            <strong>No active books</strong>

                            <p>
                                You currently don't have any issued books.
                            </p>
                        </div>

                    </div>

                )}

            </section>


            {/* =========================
                BORROWING HISTORY
            ========================= */}
            <section className="student-content-card history-card">

                <div className="student-card-header">

                    <div className="student-card-title">

                        <div className="section-icon section-icon-purple">
                            <span>↻</span>
                        </div>

                        <div>
                            <h2>Borrowing History</h2>

                            <p>
                                Complete history of your library transactions
                            </p>
                        </div>

                    </div>


                    <span className="section-count-badge">
                        {dashboard?.history?.length ?? 0} Records
                    </span>

                </div>


                {dashboard?.history?.length > 0 ? (

                    <div className="student-table-wrapper">

                        <table className="student-dashboard-table">

                            <thead>
                                <tr>
                                    <th>Book</th>
                                    <th>Issue Date</th>
                                    <th>Return Date</th>
                                    <th>Fine</th>
                                    <th>Payment</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                            </thead>

                            <tbody>

                                {dashboard.history.map((item, index) => (

                                    <tr key={`${item.bookId}-${index}`}>

                                        <td>
                                            <div className="table-book-name">

                                                <span className="table-book-icon">
                                                    B
                                                </span>

                                                <strong>
                                                    {item.bookName}
                                                </strong>

                                            </div>
                                        </td>

                                        <td>
                                            {item.issueDate}
                                        </td>

                                        <td>
                                            {item.returnDate || "-"}
                                        </td>

                                        <td>
                                            <strong>
                                                ₹{item.fineAmount}
                                            </strong>
                                        </td>

                                        <td>

                                            <span
                                                className={`student-payment-badge ${
                                                    item.paymentStatus === "PAID"
                                                        ? "payment-paid"
                                                        : item.paymentStatus === "PENDING"
                                                        ? "payment-pending"
                                                        : "payment-none"
                                                }`}
                                            >
                                                {item.paymentStatus}
                                            </span>

                                        </td>

                                        <td>

                                            <span className="transaction-status">
                                                {item.status}
                                            </span>

                                        </td>

                                        <td>

                                            {item.paymentStatus === "PENDING" &&
                                            item.fineAmount > 0 ? (

                                                <button
                                                    className="student-action-btn pay-fine-btn"
                                                    onClick={() =>
                                                        handlePayFine(item)
                                                    }
                                                >
                                                    Pay ₹{item.fineAmount}
                                                </button>

                                            ) : item.paymentStatus === "PAID" &&
                                              item.paymentId ? (

                                                <button
                                                    className="student-action-btn receipt-btn"
                                                    onClick={() =>
                                                        handleDownloadReceipt(item)
                                                    }
                                                >
                                                    Download Receipt
                                                </button>

                                            ) : (

                                                <span className="no-action">
                                                    —
                                                </span>

                                            )}

                                        </td>

                                    </tr>

                                ))}

                            </tbody>

                        </table>

                    </div>

                ) : (

                    <div className="student-empty-state">

                        <div className="empty-state-icon">
                            ↻
                        </div>

                        <div>
                            <strong>
                                No borrowing history
                            </strong>

                            <p>
                                Your completed library transactions will appear here.
                            </p>
                        </div>

                    </div>

                )}

            </section>

        </div>
    );


}

export default StudentDashboard;