import { useEffect, useState } from "react";
import API from "../services/api";
import "../styles/ManagementPages.css";

function Authors() {

    const [authors, setAuthors] = useState([]);

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [age, setAge] = useState("");
    const [country, setCountry] = useState("");

    const [editing, setEditing] = useState(false);
    const [authorId, setAuthorId] = useState(null);

    const [search, setSearch] = useState("");

    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const [message, setMessage] = useState("");
    const [showToast, setShowToast] = useState(false);


    /* =========================================
       LOAD AUTHORS WHEN PAGE CHANGES
    ========================================= */

    useEffect(() => {
        loadAuthors();
    }, [page]);


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
       LOAD AUTHORS
    ========================================= */

    const loadAuthors = () => {

        API.get(`/getAuthorsByPage?page=${page}&size=5`)

            .then((response) => {

                setAuthors(response.data.content || []);

                setTotalPages(
                    response.data.totalPages || 0
                );

            })

            .catch((error) => {

                console.error(
                    "Failed to load authors:",
                    error
                );

                showErrorToast(error);
            });
    };


    /* =========================================
       SEARCH AUTHOR
    ========================================= */

    const searchAuthor = () => {

        if (search.trim() === "") {

            loadAuthors();

            return;
        }

        API.get(
            `/searchAuthor?name=${encodeURIComponent(search)}`
        )

            .then((response) => {

                setAuthors(response.data || []);

            })

            .catch((error) => {

                console.error(
                    "Failed to search author:",
                    error
                );

                showErrorToast(error);
            });
    };


    /* =========================================
       ADD AUTHOR
    ========================================= */

    const addAuthor = () => {

        if (
            name.trim() === "" ||
            email.trim() === "" ||
            age === "" ||
            country.trim() === ""
        ) {

            setMessage(
                "Please fill all author details."
            );

            setShowToast(true);

            setTimeout(() => {
                setShowToast(false);
            }, 2500);

            return;
        }


        const author = {
            name,
            email,
            age: Number(age),
            country
        };


        API.post("/createAuthor", author)

            .then(() => {

                alert("Author Added Successfully");

                resetForm();

                loadAuthors();

            })

            .catch((error) => {

                console.error(
                    "Failed to add author:",
                    error
                );

                showErrorToast(error);
            });
    };


    /* =========================================
       EDIT AUTHOR
    ========================================= */

    const editAuthor = (author) => {

        setAuthorId(author.id);

        setName(author.name ?? "");

        setEmail(author.email ?? "");

        setAge(author.age ?? "");

        setCountry(author.country ?? "");

        setEditing(true);


        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
    };


    /* =========================================
       UPDATE AUTHOR
    ========================================= */

    const updateAuthor = () => {

        if (
            name.trim() === "" ||
            email.trim() === "" ||
            age === "" ||
            country.trim() === ""
        ) {

            setMessage(
                "Please fill all author details."
            );

            setShowToast(true);

            setTimeout(() => {
                setShowToast(false);
            }, 2500);

            return;
        }


        const author = {

            id: authorId,

            name,

            email,

            age: Number(age),

            country
        };


        API.put("/updateAuthor", author)

            .then(() => {

                alert(
                    "Author Updated Successfully"
                );

                resetForm();

                loadAuthors();

            })

            .catch((error) => {

                console.error(
                    "Failed to update author:",
                    error
                );

                showErrorToast(error);
            });
    };


    /* =========================================
       DELETE AUTHOR
    ========================================= */

    const deleteAuthor = (id) => {

        const confirmDelete = window.confirm(
            "Are you sure you want to delete this author?"
        );

        if (!confirmDelete) {
            return;
        }


        API.delete(`/deleteAuthor?id=${id}`)

            .then(() => {

                alert(
                    "Author Deleted Successfully"
                );

                loadAuthors();

            })

            .catch((error) => {

                console.error(
                    "Failed to delete author:",
                    error
                );

                showErrorToast(error);
            });
    };


    /* =========================================
       RESET FORM
    ========================================= */

    const resetForm = () => {

        setName("");

        setEmail("");

        setAge("");

        setCountry("");

        setEditing(false);

        setAuthorId(null);
    };


    /* =========================================
       SEARCH USING ENTER KEY
    ========================================= */

    const handleSearchKeyDown = (event) => {

        if (event.key === "Enter") {
            searchAuthor();
        }
    };


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
                        AUTHOR DIRECTORY
                    </span>

                    <h1>
                        Authors Management
                    </h1>

                    <p>
                        Manage authors and maintain accurate
                        information about your library's contributors.
                    </p>

                </div>


                <span className="
                    management-badge
                    management-badge-purple
                ">
                    {authors.length} Authors Shown
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
                            Search Authors
                        </h2>

                        <p>
                            Find an author quickly by entering
                            their name.
                        </p>

                    </div>

                </div>


                <div className="management-search">

                    <input
                        type="text"
                        className="management-input"
                        placeholder="Search author by name..."
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
                        onClick={searchAuthor}
                    >
                        <span>⌕</span>

                        Search
                    </button>

                </div>

            </section>


            {/* =====================================
                ADD / UPDATE AUTHOR
            ===================================== */}

            <section className="management-card">

                <div className="management-card-header">

                    <div className="management-card-icon">
                        {editing ? "✎" : "+"}
                    </div>


                    <div>

                        <h2>
                            {editing
                                ? "Update Author"
                                : "Add New Author"
                            }
                        </h2>

                        <p>
                            {editing
                                ? "Modify the selected author's information."
                                : "Add a new author to your library directory."
                            }
                        </p>

                    </div>

                </div>


                <div className="management-form-grid">

                    {/* AUTHOR NAME */}

                    <div className="management-field">

                        <label>
                            Author Name
                        </label>

                        <input
                            type="text"
                            className="management-input"
                            placeholder="Enter author name"
                            value={name}
                            onChange={(event) =>
                                setName(event.target.value)
                            }
                        />

                    </div>


                    {/* EMAIL */}

                    <div className="management-field">

                        <label>
                            Email Address
                        </label>

                        <input
                            type="email"
                            className="management-input"
                            placeholder="Enter email address"
                            value={email}
                            onChange={(event) =>
                                setEmail(event.target.value)
                            }
                        />

                    </div>


                    {/* AGE */}

                    <div className="management-field">

                        <label>
                            Age
                        </label>

                        <input
                            type="number"
                            className="management-input"
                            placeholder="Enter age"
                            min="1"
                            value={age}
                            onChange={(event) =>
                                setAge(event.target.value)
                            }
                        />

                    </div>


                    {/* COUNTRY */}

                    <div className="management-field">

                        <label>
                            Country
                        </label>

                        <input
                            type="text"
                            className="management-input"
                            placeholder="Enter country"
                            value={country}
                            onChange={(event) =>
                                setCountry(event.target.value)
                            }
                        />

                    </div>

                </div>


                {/* FORM ACTIONS */}

                <div className="student-form-actions">

                    <button
                        type="button"
                        className="
                            management-button
                            management-button-primary
                        "
                        onClick={
                            editing
                                ? updateAuthor
                                : addAuthor
                        }
                    >
                        <span>
                            {editing ? "✓" : "+"}
                        </span>

                        {editing
                            ? "Update Author"
                            : "Add Author"
                        }
                    </button>


                    {editing && (

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

                    )}

                </div>

            </section>


            {/* =====================================
                AUTHORS TABLE
            ===================================== */}

            <section className="management-card">

                <div className="management-card-header">

                    <div className="management-card-icon">
                        ✍
                    </div>


                    <div>

                        <h2>
                            Library Authors
                        </h2>

                        <p>
                            View and manage all authors registered
                            in your library system.
                        </p>

                    </div>

                </div>


                {authors.length > 0 ? (

                    <>

                        <div className="management-table-wrapper">

                            <table className="management-table">

                                <thead>

                                    <tr>

                                        <th>ID</th>

                                        <th>Name</th>

                                        <th>Email</th>

                                        <th>Age</th>

                                        <th>Country</th>

                                        <th>Actions</th>

                                    </tr>

                                </thead>


                                <tbody>

                                    {authors.map((author) => (

                                        <tr key={author.id}>

                                            <td>
                                                #{author.id}
                                            </td>


                                            <td>

                                                <strong className="management-primary-text">
                                                    {author.name}
                                                </strong>

                                            </td>


                                            <td>
                                                {author.email}
                                            </td>


                                            <td>
                                                {author.age}
                                            </td>


                                            <td>
                                                {author.country}
                                            </td>


                                            <td>

                                                <div className="management-actions">

                                                    <button
                                                        type="button"
                                                        className="
                                                            management-action-button
                                                            management-edit-button
                                                        "
                                                        onClick={() =>
                                                            editAuthor(author)
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
                                                            deleteAuthor(author.id)
                                                        }
                                                    >
                                                        Delete
                                                    </button>

                                                </div>

                                            </td>

                                        </tr>

                                    ))}

                                </tbody>

                            </table>

                        </div>


                        {/* =============================
                            PAGINATION
                        ============================= */}

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
                                        currentPage =>
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
                                        currentPage =>
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
                            ✍
                        </div>


                        <div>

                            <strong>
                                No authors found
                            </strong>

                            <p>
                                No authors match your current search
                                or no authors are registered yet.
                            </p>

                        </div>

                    </div>

                )}

            </section>

        </main>

    );
}

export default Authors;