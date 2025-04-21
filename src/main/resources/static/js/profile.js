const Name = sessionStorage.getItem("name");
const role = sessionStorage.getItem("role");
if (!Name) {
    window.location.href = "/login";
}
 if (!role || role.toLowerCase() !== "student") {
     alert("Unauthorized access!");
     window.history.back(); // Reload the same page
 }
document.addEventListener("DOMContentLoaded", function () {
    // Fetch stored user details from session storage
    document.getElementById("name").value = sessionStorage.getItem("name") || "";
    document.getElementById("email").value = sessionStorage.getItem("email") || "";

    // Fetch existing profile details from backend
    fetchProfile();
});

async function fetchProfile() {
    let email = sessionStorage.getItem("email");

    let response = await fetch(`/profile/get?email=${email}`);
    let result = await response.json();

    if (result.status === "success") {
        document.getElementById("department").value = result.department || "";
        document.getElementById("rollno").value = result.rollno || "";
        document.getElementById("tenthPercent").value = result.tenthPercent || "";
        document.getElementById("twelvePercent").value = result.twelvePercent || "";
        document.getElementById("cgpa").value = result.cgpa || "";
        document.getElementById("arrears").value = result.arrears || "";
        document.getElementById("arrearHistory").value = result.arrearHistory || "No";
        document.getElementById("preferredRole").value = result.preferredRole || "";
        document.getElementById("skills").value = result.skills || "";
    }
}

async function saveProfile() {
    let profileData = {
        email: document.getElementById("email").value,
        department: document.getElementById("department").value,
        rollno: document.getElementById("rollno").value,
        tenthPercent: parseFloat(document.getElementById("tenthPercent").value),
        twelvePercent: parseFloat(document.getElementById("twelvePercent").value),
        cgpa: parseFloat(document.getElementById("cgpa").value),
        arrears: parseInt(document.getElementById("arrears").value),
        arrearHistory: document.getElementById("arrearHistory").value,
        preferredRole: document.getElementById("preferredRole").value,
        skills: document.getElementById("skills").value
    };

    let response = await fetch("/profile/save", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(profileData)
    });

    let result = await response.text();
    if(result === "Profile updated successfully!"){
    window.location.href="/st-home";
    }
    else{
     document.getElementById("message").innerText = result;
    }
}
