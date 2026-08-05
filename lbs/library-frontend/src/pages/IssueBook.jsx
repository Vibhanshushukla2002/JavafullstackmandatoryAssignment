import { useState } from "react";
import API from "../services/api";
import "../styles/ManagementPages.css";

function IssueBook() {

    const [bookId, setBookId] = useState("");
    const [cardId, setCardId] = useState("");

    const issueBook = () => {

        if (!bookId || !cardId) {
            alert("Please enter both Book ID and Card ID");
            return;
        }

        API.post(`/issueBook?cardId=${cardId}&bookId=${bookId}`)
            .then((response) => {

                alert(response.data);

                setBookId("");
                setCardId("");

            })
            .catch((error) => {

                console.log(error);

                if (error.response) {
                    alert(JSON.stringify(error.response.data));
                } else {
                    alert(error.message);
                }

            });
    };

    return (

        <main className="management-page">

            <header className="management-header">

                <div>
                    <span className="management-eyebrow">
                        LIBRARY OPERATIONS
                    </span>

                    <h1>Issue Book</h1>

                    <p>
                        Assign an available library book to a
                        registered student's card.
                    </p>
                </div>

            </header>


            <section className="book-action-page">

                <article className="management-card book-action-card">

                    <div className="management-card-header">

                        <div className="management-card-icon">
                            ↗
                        </div>

                        <div>
                            <h2>Issue a library book</h2>

                            <p>
                                Enter the book and card identifiers
                                to create a new issue transaction.
                            </p>
                        </div>

                    </div>


                    <div className="book-action-form">

                        <div className="management-field">

                            <label>Book ID</label>

                            <input
                                type="number"
                                className="management-input"
                                placeholder="Enter Book ID"
                                value={bookId}
                                onChange={(e) =>
                                    setBookId(e.target.value)
                                }
                            />

                        </div>


                        <div className="management-field">

                            <label>Card ID</label>

                            <input
                                type="number"
                                className="management-input"
                                placeholder="Enter Card ID"
                                value={cardId}
                                onChange={(e) =>
                                    setCardId(e.target.value)
                                }
                            />

                        </div>


                        <button
                            className="
                                management-button
                                management-button-primary
                                book-action-submit
                            "
                            onClick={issueBook}
                        >
                            Issue Book
                            <span>↗</span>
                        </button>

                    </div>

                </article>

            </section>

        </main>

    );
}

export default IssueBook;