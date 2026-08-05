import { useState } from "react";
import API from "../services/api";
import "../styles/ManagementPages.css";

function ReturnBook() {

    const [bookId, setBookId] = useState("");
    const [cardId, setCardId] = useState("");

    const returnBook = () => {

        if (!bookId || !cardId) {
            alert("Please enter both Book ID and Card ID");
            return;
        }

        API.post(`/returnBook?cardId=${cardId}&bookId=${bookId}`)
            .then((response) => {

                alert(response.data);

                setBookId("");
                setCardId("");

            })
            .catch((error) => {

                console.log(error);

                if (error.response) {
                    alert(
                        error.response.data.message ||
                        error.response.data
                    );
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

                    <h1>Return Book</h1>

                    <p>
                        Process a book return using the book and
                        library card identifiers.
                    </p>
                </div>

            </header>


            <section className="book-action-page">

                <article className="management-card book-action-card">

                    <div className="management-card-header">

                        <div className="management-card-icon">
                            ↩
                        </div>

                        <div>
                            <h2>Return a borrowed book</h2>

                            <p>
                                Enter the required information below
                                to complete the return.
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
                            onClick={returnBook}
                        >
                            Return Book
                            <span>↩</span>
                        </button>

                    </div>

                </article>

            </section>

        </main>

    );
}

export default ReturnBook;