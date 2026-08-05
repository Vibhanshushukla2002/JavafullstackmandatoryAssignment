import { useEffect, useState } from "react";
import API from "../services/api";
import "../styles/ManagementPages.css";

function Books() {

    /* =========================================
       ROLE BASED ACCESS
    ========================================= */

    const role = localStorage.getItem("role");

    const isAdmin =
        role === "ADMIN" ||
        role === "ROLE_ADMIN";


    /* =========================================
       STATES
    ========================================= */

    const [books, setBooks] = useState([]);

    const [name, setName] = useState("");
    const [genre, setGenre] = useState("");
    const [authorId, setAuthorId] = useState("");

    const [editing, setEditing] = useState(false);
    const [bookId, setBookId] = useState(null);

    const [search, setSearch] = useState("");

    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);


    /* =========================================
       LOAD BOOKS WHEN PAGE CHANGES
    ========================================= */

    useEffect(() => {
        loadBooks();
    }, [page]);


    /* =========================================
       LOAD BOOKS
    ========================================= */

    const loadBooks = () => {

        API.get(`/getBooksByPage?page=${page}&size=5`)

            .then((response) => {

                console.log(
                    "BOOK API RESPONSE:",
                    response.data
                );

                setBooks(
                    response.data.content || []
                );

                setTotalPages(
                    response.data.totalPages || 0
                );
            })

            .catch((error) => {

                console.error(
                    "Failed to load books:",
                    error
                );

                showError(error);
            });
    };


    /* =========================================
       SEARCH BOOK
    ========================================= */

    const searchBook = () => {

        if (search.trim() === "") {

            loadBooks();

            return;
        }

        API.get(
            `/searchBook?name=${encodeURIComponent(search.trim())}`
        )

            .then((response) => {

                setBooks(response.data || []);
            })

            .catch((error) => {

                console.error(
                    "Failed to search book:",
                    error
                );

                showError(error);
            });
    };


    /* =========================================
       ADD BOOK - ADMIN ONLY
    ========================================= */

    const addBook = () => {

        if (!isAdmin) {

            alert(
                "Access denied. Only Admin can add books."
            );

            return;
        }

        if (
            name.trim() === "" ||
            genre === "" ||
            authorId === ""
        ) {

            alert(
                "Please fill all book details."
            );

            return;
        }

        const bookRequest = {

            name: name.trim(),

            genre: genre,

            authorId: Number(authorId)
        };

        console.log(
            "CREATE BOOK REQUEST:",
            bookRequest
        );

        API.post(
            "/createBook",
            bookRequest
        )

            .then((response) => {

                console.log(
                    "Book created:",
                    response.data
                );

                alert(
                    "Book Added Successfully"
                );

                resetForm();

                loadBooks();
            })

            .catch((error) => {

                console.error(
                    "Failed to add book:",
                    error.response?.data || error
                );

                showError(error);
            });
    };


    /* =========================================
       EDIT BOOK - ADMIN ONLY
    ========================================= */

    const editBook = (book) => {

        if (!isAdmin) {

            alert(
                "Access denied. Only Admin can edit books."
            );

            return;
        }

        console.log(
            "Selected book for editing:",
            book
        );

        setBookId(book.id);

        setName(book.name ?? "");

        setGenre(book.genre ?? "");

        setAuthorId(book.authorId ?? "");

        setEditing(true);

        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
    };


    /* =========================================
       UPDATE BOOK - ADMIN ONLY
    ========================================= */

    const updateBook = () => {

        if (!isAdmin) {

            alert(
                "Access denied. Only Admin can update books."
            );

            return;
        }

        if (
            !bookId ||
            name.trim() === "" ||
            genre === "" ||
            authorId === ""
        ) {

            alert(
                "Please fill all required details."
            );

            return;
        }

        const bookUpdateRequest = {

            id: Number(bookId),

            name: name.trim(),

            genre: genre,

            authorId: Number(authorId)
        };

        console.log(
            "UPDATE REQUEST BODY:",
            bookUpdateRequest
        );

        API.put(
            "/updateBook",
            bookUpdateRequest
        )

            .then((response) => {

                console.log(
                    "Updated book:",
                    response.data
                );

                alert(
                    "Book Updated Successfully"
                );

                resetForm();

                loadBooks();
            })

            .catch((error) => {

                console.error(
                    "UPDATE ERROR:",
                    error.response?.data || error
                );

                showError(error);
            });
    };


    /* =========================================
       DELETE BOOK - ADMIN ONLY
    ========================================= */

    const deleteBook = (id) => {

        if (!isAdmin) {

            alert(
                "Access denied. Only Admin can delete books."
            );

            return;
        }

        const confirmDelete = window.confirm(
            "Are you sure you want to delete this book?"
        );

        if (!confirmDelete) {
            return;
        }

        API.delete(
            `/deleteBook?id=${id}`
        )

            .then(() => {

                alert(
                    "Book Deleted Successfully"
                );

                loadBooks();
            })

            .catch((error) => {

                console.error(
                    "Failed to delete book:",
                    error
                );

                showError(error);
            });
    };


    /* =========================================
       RESET FORM
    ========================================= */

    const resetForm = () => {

        setName("");

        setGenre("");

        setAuthorId("");

        setEditing(false);

        setBookId(null);
    };


    /* =========================================
       ERROR HANDLER
    ========================================= */

    const showError = (error) => {

        const errorData =
            error.response?.data;

        let errorMessage =
            "Something went wrong";

        if (typeof errorData === "string") {

            errorMessage = errorData;

        } else if (errorData?.message) {

            errorMessage = errorData.message;

        } else if (error.message) {

            errorMessage = error.message;
        }

        alert(errorMessage);
    };


    /* =========================================
       HANDLE ENTER KEY FOR SEARCH
    ========================================= */

    const handleSearchKeyDown = (event) => {

        if (event.key === "Enter") {

            searchBook();
        }
    };


    return (

        <main className="management-page">


            {/* =====================================
                PAGE HEADER
            ===================================== */}

            <header className="management-header">

                <div>

                    <span className="management-eyebrow">

                        {isAdmin
                            ? "ADMIN • LIBRARY CATALOGUE"
                            : "STUDENT • LIBRARY CATALOGUE"
                        }

                    </span>


                    <h1>

                        {isAdmin
                            ? "Books Management"
                            : "Explore Library Books"
                        }

                    </h1>


                    <p>

                        {isAdmin
                            ? "Manage your complete library collection, search books and maintain book information."
                            : "Search and explore books available in your library catalogue."
                        }

                    </p>

                </div>


                <span
                    className="
                        management-badge
                        management-badge-purple
                    "
                >

                    {books.length} Books Shown

                </span>

            </header>


            {/* =====================================
                SEARCH SECTION
            ===================================== */}

            <section className="management-card">

                <div className="management-card-header">

                    <div className="management-card-icon">
                        ⌕
                    </div>


                    <div>

                        <h2>
                            Search Books
                        </h2>


                        <p>
                            Find a book quickly by entering its name.
                        </p>

                    </div>

                </div>


                <div className="management-search">

                    <input
                        type="text"
                        className="management-input"
                        placeholder="Search book by name..."
                        value={search}
                        onChange={(event) =>
                            setSearch(event.target.value)
                        }
                        onKeyDown={handleSearchKeyDown}
                    />


                    <button
                        type="button"
                        className="
                            management-button
                            management-button-primary
                        "
                        onClick={searchBook}
                    >

                        <span>
                            ⌕
                        </span>

                        Search

                    </button>

                </div>

            </section>


            {/* =====================================
                ADD / UPDATE BOOK - ADMIN ONLY
            ===================================== */}

            {isAdmin && (

                <section className="management-card">

                    <div className="management-card-header">

                        <div className="management-card-icon">

                            {editing ? "✎" : "+"}

                        </div>


                        <div>

                            <h2>

                                {editing
                                    ? "Update Book"
                                    : "Add New Book"
                                }

                            </h2>


                            <p>

                                {editing
                                    ? "Modify the selected book information."
                                    : "Add a new book to your library catalogue."
                                }

                            </p>

                        </div>

                    </div>


                    <div className="management-form-grid">


                        {/* BOOK NAME */}

                        <div className="management-field">

                            <label>
                                Book Name
                            </label>


                            <input
                                type="text"
                                className="management-input"
                                placeholder="Enter book name"
                                value={name}
                                onChange={(event) =>
                                    setName(event.target.value)
                                }
                            />

                        </div>


                        {/* GENRE */}

                        <div className="management-field">

                            <label>
                                Genre
                            </label>


                            <select
                                className="management-select"
                                value={genre}
                                onChange={(event) =>
                                    setGenre(event.target.value)
                                }
                            >

                                <option value="">
                                    Select Genre
                                </option>


                                <option value="PHYSICS">
                                    Physics
                                </option>


                                <option value="MATHEMATICS">
                                    Mathematics
                                </option>


                                <option value="CHEMISTRY">
                                    Chemistry
                                </option>

                            </select>

                        </div>


                        {/* AUTHOR ID */}

                        <div className="management-field">

                            <label>
                                Author ID
                            </label>


                            <input
                                type="number"
                                className="management-input"
                                placeholder="Enter author ID"
                                value={authorId}
                                min="1"
                                onChange={(event) =>
                                    setAuthorId(event.target.value)
                                }
                            />

                        </div>


                        {/* ACTION */}

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
                                onClick={
                                    editing
                                        ? updateBook
                                        : addBook
                                }
                            >

                                <span>

                                    {editing ? "✓" : "+"}

                                </span>


                                {editing
                                    ? "Update Book"
                                    : "Add Book"
                                }

                            </button>

                        </div>

                    </div>


                    {/* CANCEL EDITING */}

                    {editing && (

                        <div
                            style={{
                                marginTop: "12px"
                            }}
                        >

                            <button
                                type="button"
                                className="
                                    management-button
                                    management-button-secondary
                                "
                                onClick={resetForm}
                            >
                                Cancel Editing
                            </button>

                        </div>

                    )}

                </section>

            )}


            {/* =====================================
                BOOKS TABLE
            ===================================== */}

            <section className="management-card">

                <div className="management-card-header">

                    <div className="management-card-icon">
                        ▤
                    </div>


                    <div>

                        <h2>
                            Library Books
                        </h2>


                        <p>

                            {isAdmin
                                ? "View and manage all books currently available in your library system."
                                : "Browse books and check their current availability."
                            }

                        </p>

                    </div>

                </div>


                {books.length > 0 ? (

                    <>

                        <div className="management-table-wrapper">

                            <table className="management-table">

                                <thead>

                                    <tr>

                                        <th>
                                            ID
                                        </th>

                                        <th>
                                            Book Name
                                        </th>

                                        <th>
                                            Genre
                                        </th>

                                        <th>
                                            Author
                                        </th>

                                        <th>
                                            Status
                                        </th>


                                        {/* ADMIN ONLY */}

                                        {isAdmin && (

                                            <th>
                                                Actions
                                            </th>

                                        )}

                                    </tr>

                                </thead>


                                <tbody>

                                    {books.map((book) => (

                                        <tr key={book.id}>


                                            {/* ID */}

                                            <td>
                                                #{book.id}
                                            </td>


                                            {/* BOOK NAME */}

                                            <td>

                                                <strong
                                                    className="management-primary-text"
                                                >
                                                    {book.name}
                                                </strong>

                                            </td>


                                            {/* GENRE */}

                                            <td>

                                                <span
                                                    className="
                                                        management-badge
                                                        management-badge-purple
                                                    "
                                                >

                                                    {book.genre ||
                                                        "Not Specified"
                                                    }

                                                </span>

                                            </td>


                                            {/* AUTHOR */}

                                            <td>

                                                {book.authorName || "—"}

                                            </td>


                                            {/* STATUS */}

                                            <td>

                                                <span
                                                    className={
                                                        book.available
                                                            ? "management-badge management-badge-success"
                                                            : "management-badge management-badge-danger"
                                                    }
                                                >

                                                    {book.available
                                                        ? "Available"
                                                        : "Issued"
                                                    }

                                                </span>

                                            </td>


                                            {/* =====================================
                                                ACTIONS - ADMIN ONLY
                                            ===================================== */}

                                            {isAdmin && (

                                                <td>

                                                    <div className="management-actions">

                                                        <button
                                                            type="button"
                                                            className="
                                                                management-action-button
                                                                management-edit-button
                                                            "
                                                            onClick={() =>
                                                                editBook(book)
                                                            }
                                                        >
                                                            Edit
                                                        </button>


                                                        <button
                                                            type="button"
                                                            className="
                                                                management-action-button
                                                                management-delete-button
                                                            "
                                                            onClick={() =>
                                                                deleteBook(book.id)
                                                            }
                                                        >
                                                            Delete
                                                        </button>

                                                    </div>

                                                </td>

                                            )}

                                        </tr>

                                    ))}

                                </tbody>

                            </table>

                        </div>


                        {/* =====================================
                            PAGINATION
                        ===================================== */}

                        <div className="management-pagination">

                            <button
                                type="button"
                                className="
                                    management-button
                                    management-button-secondary
                                "
                                disabled={page === 0}
                                onClick={() =>
                                    setPage(
                                        (currentPage) =>
                                            currentPage - 1
                                    )
                                }
                            >
                                ← Previous
                            </button>


                            <span className="management-pagination-info">

                                Page{" "}

                                <strong>

                                    {totalPages === 0
                                        ? 0
                                        : page + 1
                                    }

                                </strong>

                                {" "}of{" "}

                                <strong>
                                    {totalPages}
                                </strong>

                            </span>


                            <button
                                type="button"
                                className="
                                    management-button
                                    management-button-secondary
                                "
                                disabled={
                                    totalPages === 0 ||
                                    page + 1 >= totalPages
                                }
                                onClick={() =>
                                    setPage(
                                        (currentPage) =>
                                            currentPage + 1
                                    )
                                }
                            >
                                Next →
                            </button>

                        </div>

                    </>

                ) : (

                    <div className="management-empty-state">

                        <div className="management-empty-icon">
                            📚
                        </div>


                        <div>

                            <strong>
                                No books found
                            </strong>


                            <p>
                                No books match your current search
                                or the library catalogue is empty.
                            </p>

                        </div>

                    </div>

                )}

            </section>

        </main>
    );
}

export default Books;