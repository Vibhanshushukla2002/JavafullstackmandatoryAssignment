import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
} from "chart.js";

import { Bar } from "react-chartjs-2";


ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
);


function StatsChart({
    books,
    students,
    authors,
    transactions
}) {

    const data = {

        labels: [
            "Books",
            "Students",
            "Authors",
            "Transactions"
        ],

        datasets: [
            {
                label: "Library Statistics",

                data: [
                    books,
                    students,
                    authors,
                    transactions
                ],

                backgroundColor: [
                    "#6557e8",
                    "#7c5ce7",
                    "#9b7cf4",
                    "#c4b5fd"
                ],

                borderRadius: 9,

                borderSkipped: false,

                maxBarThickness: 48
            }
        ]

    };


    const options = {

        responsive: true,

        maintainAspectRatio: false,

        plugins: {

            legend: {
                display: false
            },

            tooltip: {

                backgroundColor: "#21184f",

                titleColor: "#ffffff",

                bodyColor: "#ffffff",

                padding: 12,

                cornerRadius: 9

            }

        },

        scales: {

            x: {

                grid: {
                    display: false
                },

                ticks: {
                    color: "#777387"
                },

                border: {
                    display: false
                }

            },

            y: {

                beginAtZero: true,

                ticks: {

                    color: "#9995a8",

                    precision: 0

                },

                grid: {

                    color:
                        "rgba(99, 102, 241, 0.07)"

                },

                border: {
                    display: false
                }

            }

        }

    };


    return (

        <article className="admin-chart-card">

            <div className="admin-chart-header">

                <div className="admin-chart-title">

                    <div className="admin-chart-icon">
                        📊
                    </div>

                    <div>

                        <h3>
                            Library Statistics
                        </h3>

                        <p>
                            Overview of core library resources
                        </p>

                    </div>

                </div>

            </div>


            <div className="admin-bar-chart-wrapper">

                <Bar
                    data={data}
                    options={options}
                />

            </div>

        </article>

    );
}


export default StatsChart;