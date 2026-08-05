import { useState } from "react";
import api from "../services/api";

export default function Payment() {

    const [amount, setAmount] = useState("");

    const payNow = async () => {

        try {



            const response = await api.post("/payment/start", {
                bookId: 1,
                amount: Number(amount)
            });



            const payment = response.data;
            console.log("PAYMENT =", payment);
            console.log("TYPE =", typeof payment);


            const options = {
                key: payment.key,

                amount: payment.amount,

                currency: payment.currency,

                name: "Library Management System",

                description: "Book Payment",

                order_id: payment.orderId,

                handler: async function (response) {

                    console.log(response);

                    alert("Payment Successful");

                 await api.put(
                     `/payment/pay/${payment.paymentId}?paymentId=${response.razorpay_payment_id}`
                 );
             console.log(verifyResponse.data);

                    alert("Payment Saved Successfully");
                },
            modal: {
                    ondismiss: function () {
                        alert("Payment Cancelled");
                    }
                },

                prefill: {
                    name: localStorage.getItem("username")
                },



                theme: {
                    color: "#1976d2"
                }
            };

            const razor = new window.Razorpay(options);

            razor.open();

        } catch (e) {

            console.log(e);

                console.log("Status:", e.response?.status);
                    console.log("Data:", e.response?.data);

                    alert(JSON.stringify(e.response?.data));
            //alert("Payment Failed");

        }

    };

    return (

        <div>

            <h2>Payment</h2>

            <input
                type="number"
                placeholder="Enter Amount"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
            />

            <br /><br />

            <button onClick={payNow}>
                Pay Now
            </button>

        </div>

    );

}