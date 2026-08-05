import {
    Chart as ChartJS,
    ArcElement,
    Tooltip,
    Legend
} from "chart.js";

import { Doughnut } from "react-chartjs-2";


ChartJS.register(
    ArcElement,
    Tooltip,
    Legend
);


function BooksChart({
    totalBooks,
    issuedBooks
}) {

    const availableBooks = Math.max(
        totalBooks - issuedBooks,
        0
    );


    const data = {

        labels: [
            "Available Books",
            "Issued Books"
        ],

        datasets: [
            {
                label: "Books",

                data: [
                    availableBooks,
                    issuedBooks
                ],

                backgroundColor: [
                    "#6557e8",
                    "#c4b5fd"
                ],

                borderColor: [
                    "#ffffff",
                    "#ffffff"
                ],

                borderWidth: 4,

                hoverOffset: 7
            }
        ]
    };


    const options = {

        responsive: true,

        maintainAspectRatio: false,

        cutout: "68%",

        plugins: {

            legend: {

                position: "bottom",

                labels: {

                    usePointStyle: true,

                    pointStyle: "circle",

                    padding: 22,

                    color: "#6f6b80",

                    font: {
                        size: 11,
                        weight: "600"
                    }

                }

            },

            tooltip: {

                backgroundColor: "#21184f",

                titleColor: "#ffffff",

                bodyColor: "#ffffff",

                padding: 12,

                cornerRadius: 9

            }

        }

    };


    return (

        <article className="admin-chart-card">

            <div className="admin-chart-header">

                <div className="admin-chart-title">

                    <div className="admin-chart-icon">
                        📚
                    </div>

                    <div>

                        <h3>
                            Books Status
                        </h3>

                        <p>
                            Available versus currently issued books
                        </p>

                    </div>

                </div>

            </div>


            <div className="admin-doughnut-wrapper">

                <Doughnut
                    data={data}
                    options={options}
                />

            </div>


            <div className="admin-chart-summary">

                <div>

                    <span>
                        Total Collection
                    </span>

                    <strong>
                        {totalBooks}
                    </strong>

                </div>


                <div>

                    <span>
                        Available
                    </span>

                    <strong>
                        {availableBooks}
                    </strong>

                </div>


                <div>

                    <span>
                        Issued
                    </span>

                    <strong>
                        {issuedBooks}
                    </strong>

                </div>

            </div>

        </article>

    );
}


export default BooksChart;