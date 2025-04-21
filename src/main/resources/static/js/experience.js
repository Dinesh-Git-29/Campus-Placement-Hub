const Name = sessionStorage.getItem("name");
const role = sessionStorage.getItem("role");
if (!Name) {
    window.location.href = "/login";
}
 if (!role || role.toLowerCase() !== "student") {
     alert("Unauthorized access!");
     window.history.back(); // Reload the same page
 }
document.getElementById("experienceForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    let formData = new FormData();
    formData.append("company", document.getElementById("company").value);
    formData.append("role", document.getElementById("role").value);
    formData.append("rounds", document.getElementById("rounds").value);
    formData.append("file", document.getElementById("file").files[0]);

    let response = await fetch("/experience/add", {
        method: "POST",
        body: formData
    });

    if (response.ok) {
        alert("Experience submitted successfully!");
        document.getElementById("experienceForm").reset();
    } else {
        alert("Error submitting experience!");
    }
});

// Search experiences
async function searchExperiences() {
    let company = document.getElementById("searchCompany").value;
    let role = document.getElementById("searchRole").value;

    let response = await fetch(`/experience/search?company=${company}&role=${role}`);
    let experiences = await response.json();

    let resultsDiv = document.getElementById("results");
    resultsDiv.innerHTML = "";

   experiences.forEach(exp => {
       let div = document.createElement("div");
       div.innerHTML = `<h3>${exp.company} - ${exp.role}</h3>
                        <p>${exp.rounds}</p>
                        ${exp.filePath ? `<a href="http://localhost:8080/experience/uploads${exp.filePath.split('/').pop()}" target="_blank">View File</a>` : ""}
                        <hr>`;
       resultsDiv.appendChild(div);
   });

}
