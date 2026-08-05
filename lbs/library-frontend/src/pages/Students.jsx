import { useEffect, useState } from "react";
import API from "../services/api";
import "../styles/ManagementPages.css";

function Students() {

    const [students, setStudents] = useState([]);

    const [name, setName] = useState("");
    const [emailId, setEmailId] = useState("");
    const [age, setAge] = useState("");
    const [country, setCountry] = useState("");

    const [editing, setEditing] = useState(false);
    const [studentId, setStudentId] = useState(null);

    const [search, setSearch] = useState("");

    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const [message, setMessage] = useState("");
    const [showToast, setShowToast] = useState(false);


    /* =========================================
       LOAD STUDENTS WHEN PAGE CHANGES
    ========================================= */

    useEffect(() => {
        loadStudents();
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
       LOAD STUDENTS
    ========================================= */

    const loadStudents = () => {

        API.get(`/getStudentsByPage?page=${page}&size=5`)

            .then((response) => {

                setStudents(response.data.content || []);

                setTotalPages(
                    response.data.totalPages || 0
                );

            })

            .catch((error) => {

                console.error(
                    "Failed to load students:",
                    error
                );

                showErrorToast(error);
            });
    };


    /* =========================================
       SEARCH STUDENT
    ========================================= */

    const searchStudent = () => {

        if (search.trim() === "") {

            loadStudents();

            return;
        }

        API.get(
            `/searchStudent?name=${encodeURIComponent(search)}`
        )

            .then((response) => {

                setStudents(response.data || []);

            })

            .catch((error) => {

                console.error(
                    "Failed to search student:",
                    error
                );

                showErrorToast(error);
            });
    };


    /* =========================================
       ADD STUDENT
    ========================================= */

    const addStudent = () => {

        if (
            name.trim() === "" ||
            emailId.trim() === "" ||
            age === "" ||
            country.trim() === ""
        ) {

            setMessage(
                "Please fill all student details."
            );

            setShowToast(true);

            setTimeout(() => {
                setShowToast(false);
            }, 2500);

            return;
        }


        const student = {
            name,
            emailId,
            age: Number(age),
            country
        };


        API.post("/createStudent", student)

            .then(() => {

                alert("Student Added Successfully");

                resetForm();

                loadStudents();

            })

            .catch((error) => {

                console.error(
                    "Failed to add student:",
                    error
                );

                showErrorToast(error);
            });
    };


    /* =========================================
       EDIT STUDENT
    ========================================= */

    const editStudent = (student) => {

        setStudentId(student.id);

        setName(student.name ?? "");

        setEmailId(student.emailId ?? "");

        setAge(student.age ?? "");

        setCountry(student.country ?? "");

        setEditing(true);


        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
    };


    /* =========================================
       UPDATE STUDENT
    ========================================= */

    const updateStudent = () => {

        if (
            name.trim() === "" ||
            emailId.trim() === "" ||
            age === "" ||
            country.trim() === ""
        ) {

            setMessage(
                "Please fill all student details."
            );

            setShowToast(true);

            setTimeout(() => {
                setShowToast(false);
            }, 2500);

            return;
        }


        const student = {

            id: studentId,

            name,

            emailId,

            age: Number(age),

            country
        };


        API.put("/updateStudent", student)

            .then(() => {

                alert(
                    "Student Updated Successfully"
                );

                resetForm();

                loadStudents();

            })

            .catch((error) => {

                console.error(
                    "Failed to update student:",
                    error
                );

                showErrorToast(error);
            });
    };


    /* =========================================
       DELETE STUDENT
    ========================================= */

    const deleteStudent = (id) => {

        const confirmDelete = window.confirm(
            "Are you sure you want to delete this student?"
        );

        if (!confirmDelete) {
            return;
        }


        API.delete(`/deleteStudent?id=${id}`)

            .then(() => {

                alert(
                    "Student Deleted Successfully"
                );

                loadStudents();

            })

            .catch((error) => {

                console.error(
                    "Failed to delete student:",
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

        setEmailId("");

        setAge("");

        setCountry("");

        setEditing(false);

        setStudentId(null);
    };


    /* =========================================
       SEARCH WITH ENTER KEY
    ========================================= */

    const handleSearchKeyDown = (event) => {

        if (event.key === "Enter") {
            searchStudent();
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
                        STUDENT DIRECTORY
                    </span>

                    <h1>
                        Students Management
                    </h1>

                    <p>
                        Manage registered students, library cards
                        and student account information.
                    </p>

                </div>


                <span className="
                    management-badge
                    management-badge-purple
                ">
                    {students.length} Students Shown
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
                            Search Students
                        </h2>

                        <p>
                            Find a registered student quickly
                            by entering their name.
                        </p>

                    </div>

                </div>


                <div className="management-search">

                    <input
                        type="text"
                        className="management-input"
                        placeholder="Search student by name..."
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
                        onClick={searchStudent}
                    >
                        <span>⌕</span>

                        Search
                    </button>

                </div>

            </section>


            {/* =====================================
                ADD / UPDATE STUDENT
            ===================================== */}

            <section className="management-card">

                <div className="management-card-header">

                    <div className="management-card-icon">
                        {editing ? "✎" : "+"}
                    </div>


                    <div>

                        <h2>
                            {editing
                                ? "Update Student"
                                : "Add New Student"
                            }
                        </h2>

                        <p>
                            {editing
                                ? "Modify the selected student's information."
                                : "Register a new student in the library system."
                            }
                        </p>

                    </div>

                </div>


                <div className="management-form-grid">

                    {/* NAME */}

                    <div className="management-field">

                        <label>
                            Full Name
                        </label>

                        <input
                            type="text"
                            className="management-input"
                            placeholder="Enter student name"
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
                            value={emailId}
                            onChange={(event) =>
                                setEmailId(event.target.value)
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
                                ? updateStudent
                                : addStudent
                        }
                    >
                        <span>
                            {editing ? "✓" : "+"}
                        </span>

                        {editing
                            ? "Update Student"
                            : "Add Student"
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
                STUDENTS TABLE
            ===================================== */}

            <section className="management-card">

                <div className="management-card-header">

                    <div className="management-card-icon">
                        🎓
                    </div>


                    <div>

                        <h2>
                            Registered Students
                        </h2>

                        <p>
                            View and manage all students registered
                            in your library system.
                        </p>

                    </div>

                </div>


                {students.length > 0 ? (

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

                                        <th>Card Status</th>

                                        <th>Actions</th>

                                    </tr>

                                </thead>


                                <tbody>

                                    {students.map((student) => (

                                        <tr key={student.id}>

                                            <td>
                                                #{student.id}
                                            </td>


                                            <td>

                                                <strong className="management-primary-text">
                                                    {student.name}
                                                </strong>

                                            </td>


                                            <td>
                                                {student.emailId}
                                            </td>


                                            <td>
                                                {student.age}
                                            </td>


                                            <td>
                                                {student.country}
                                            </td>


                                            <td>

                                                <span
                                                    className={
                                                        student.card?.cardStatus === "ACTIVATED" ||
                                                        student.card?.cardStatus === "ACTIVE"
                                                            ? "management-badge management-badge-success"
                                                            : "management-badge management-badge-danger"
                                                    }
                                                >
                                                    {student.card?.cardStatus || "No Card"}
                                                </span>

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
                                                            editStudent(student)
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
                                                            deleteStudent(student.id)
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
                            🎓
                        </div>


                        <div>

                            <strong>
                                No students found
                            </strong>

                            <p>
                                No students match your current search
                                or no students are registered yet.
                            </p>

                        </div>

                    </div>

                )}

            </section>

        </main>

    );
}

export default Students;