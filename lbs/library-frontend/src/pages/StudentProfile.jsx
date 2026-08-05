import { useEffect, useState } from "react";
import API from "../services/api";
import "../styles/StudentProfile.css";

function StudentProfile() {

    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [isEditing, setIsEditing] = useState(false);
    const [selectedFile, setSelectedFile] = useState(null);
    const [uploading, setUploading] = useState(false);

    const [editForm, setEditForm] = useState({
        name: "",
        age: "",
        country: ""
    });

    const [saving, setSaving] = useState(false);

    const loadProfile = async () => {

        try {

            setLoading(true);

            const response = await API.get("/student/profile");

            setProfile(response.data);
            setError("");

        } catch (error) {

            console.error(
                "Unable to load student profile:",
                error
            );

            setError("Unable to load student profile");

        } finally {

            setLoading(false);
        }
    };
const startEditing = () => {

    setEditForm({
        name: profile.name,
        age: profile.age,
        country: profile.country
    });

    setIsEditing(true);
};

const handleInputChange = (event) => {

    const { name, value } = event.target;

    setEditForm((previousForm) => ({
        ...previousForm,
        [name]: value
    }));
};
const cancelEditing = () => {

    setIsEditing(false);

    setEditForm({
        name: "",
        age: "",
        country: ""
    });
};
const saveProfile = async () => {

    try {

        setSaving(true);

        const response = await API.put(
            "/student/profile",
            {
                name: editForm.name,
                age: Number(editForm.age),
                country: editForm.country
            }
        );

        setProfile(response.data);

        setIsEditing(false);

        alert("Profile updated successfully");

    } catch (error) {

        console.error(
            "Unable to update profile:",
            error
        );

        alert(
            error.response?.data?.message ||
            error.response?.data ||
            "Unable to update profile"
        );

    } finally {

        setSaving(false);
    }
};
const uploadProfilePicture = async (event) => {

    const file = event.target.files[0];

    if (!file) {
        return;
    }

    try {

        setUploading(true);

        const formData = new FormData();

        formData.append("file", file);

        const response = await API.post(
            "/student/profile/picture",
            formData,
            {
                headers: {
                    "Content-Type": "multipart/form-data"
                }
            }
        );

        setProfile(response.data);

        alert("Profile picture uploaded successfully");

    } catch (error) {

        console.error(error);

        alert(
            error.response?.data ||
            "Unable to upload profile picture"
        );

    } finally {

        setUploading(false);

    }

};
    useEffect(() => {

        loadProfile();

    }, []);


    if (loading) {

        return (
            <div>
                Loading profile...
            </div>
        );
    }


    if (error) {

        return (
            <div>
                {error}
            </div>
        );
    }

return (

    <div className="student-profile-page">

        <h1>My Profile</h1>

        <p>
            Manage your personal information and view your
            library membership details.
        </p>


        <div className="student-full-profile-card">

            {/* ================================
                PROFILE AVATAR
            ================================= */}
           <div className="student-full-profile-avatar">

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



            {/* STUDENT NAME */}
            <h2>
                {profile?.name}
            </h2>


            {/* STUDENT ROLE */}
            <span>
                {profile?.role}
            </span>


            <hr />

            <div className="student-upload-section">

                <label
                    className="student-upload-btn"
                >

                    {uploading
                        ? "Uploading..."
                        : "Change Picture"}

                    <input
                        type="file"
                        accept="image/png,image/jpeg"
                        hidden
                        onChange={uploadProfilePicture}
                    />

                </label>

            </div>



            {/* ================================
                PROFILE INFORMATION GRID
            ================================= */}
            <div className="student-profile-info-grid">


                {/* STUDENT ID - LOCKED */}
                <div className="student-profile-field">

                    <span className="student-profile-field-label">
                        Student ID
                    </span>

                    <strong className="student-profile-field-value">
                        #{profile?.studentId}
                    </strong>

                    <span className="student-profile-lock">
                        🔒
                    </span>

                </div>


                {/* LIBRARY CARD ID - LOCKED */}
                <div className="student-profile-field">

                    <span className="student-profile-field-label">
                        Library Card ID
                    </span>

                    <strong className="student-profile-field-value">
                        #{profile?.cardId}
                    </strong>

                    <span className="student-profile-lock">
                        🔒
                    </span>

                </div>


                {/* USERNAME - LOCKED */}
                <div className="student-profile-field">

                    <span className="student-profile-field-label">
                        Username
                    </span>

                    <strong className="student-profile-field-value">
                        {profile?.username}
                    </strong>

                    <span className="student-profile-lock">
                        🔒
                    </span>

                </div>


                {/* EMAIL - LOCKED */}
                <div className="student-profile-field">

                    <span className="student-profile-field-label">
                        Email
                    </span>

                    <strong className="student-profile-field-value">
                        {profile?.email}
                    </strong>

                    <span className="student-profile-lock">
                        🔒
                    </span>

                </div>


                {/* FULL NAME - EDITABLE */}
                <div className="student-profile-field">

                    <span className="student-profile-field-label">
                        Full Name
                    </span>

                    {isEditing ? (

                        <input
                            type="text"
                            name="name"
                            value={editForm.name}
                            onChange={handleInputChange}
                            className="student-profile-input"
                        />

                    ) : (

                        <strong className="student-profile-field-value">
                            {profile?.name}
                        </strong>

                    )}

                </div>


                {/* AGE - EDITABLE */}
                <div className="student-profile-field">

                    <span className="student-profile-field-label">
                        Age
                    </span>

                    {isEditing ? (

                        <input
                            type="number"
                            name="age"
                            min="5"
                            value={editForm.age}
                            onChange={handleInputChange}
                            className="student-profile-input"
                        />

                    ) : (

                        <strong className="student-profile-field-value">
                            {profile?.age}
                        </strong>

                    )}

                </div>


                {/* COUNTRY - EDITABLE */}
                <div className="student-profile-field">

                    <span className="student-profile-field-label">
                        Country
                    </span>

                    {isEditing ? (

                        <input
                            type="text"
                            name="country"
                            value={editForm.country}
                            onChange={handleInputChange}
                            className="student-profile-input"
                        />

                    ) : (

                        <strong className="student-profile-field-value">
                            {profile?.country}
                        </strong>

                    )}

                </div>


                {/* CARD STATUS - LOCKED */}
                <div className="student-profile-field">

                    <span className="student-profile-field-label">
                        Card Status
                    </span>

                    <strong className="student-profile-card-status">

                        <span></span>

                        {profile?.cardStatus}

                    </strong>

                    <span className="student-profile-lock">
                        🔒
                    </span>

                </div>


                {/* MEMBER SINCE - LOCKED */}
                <div className="student-profile-field">

                    <span className="student-profile-field-label">
                        Member Since
                    </span>

                    <strong className="student-profile-field-value">
                        {profile?.memberSince}
                    </strong>

                    <span className="student-profile-lock">
                        🔒
                    </span>

                </div>


            </div>
            {/* PROFILE INFORMATION GRID ENDS HERE */}


            {/* ================================
                EDIT / SAVE / CANCEL BUTTONS
                GRID KE BAHAR HAIN
            ================================= */}
            <div className="student-profile-actions">

                {!isEditing ? (

                    <button
                        type="button"
                        className="student-edit-profile-btn"
                        onClick={startEditing}
                    >
                        Edit Profile
                    </button>

                ) : (

                    <div className="student-edit-action-buttons">

                        <button
                            type="button"
                            className="student-cancel-profile-btn"
                            onClick={cancelEditing}
                            disabled={saving}
                        >
                            Cancel
                        </button>

                        <button
                            type="button"
                            className="student-save-profile-btn"
                            onClick={saveProfile}
                            disabled={saving}
                        >
                            {saving
                                ? "Saving..."
                                : "Save Changes"}
                        </button>

                    </div>

                )}

            </div>
            {/* ACTION BUTTONS END HERE */}


        </div>
        {/* FULL PROFILE CARD ENDS HERE */}


    </div>
    /* PROFILE PAGE ENDS HERE */

);

}

export default StudentProfile;