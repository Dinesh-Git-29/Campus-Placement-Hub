const Name = sessionStorage.getItem("name");
const role = sessionStorage.getItem("role");
if (!Name) {
    window.location.href = "/login";
}
 if (!role || role.toLowerCase() !== "admin") {
     alert("Unauthorized access!");
     window.history.back(); // Reload the same page
 }

document.addEventListener("DOMContentLoaded", fetchJobs);

async function fetchJobs() {
    let response = await fetch("/jobs/list");
    let jobs = await response.json();

    let jobListDiv = document.getElementById("jobList");
    jobListDiv.innerHTML = "";

    jobs.forEach(job => {
        let jobItem = document.createElement("div");
        jobItem.innerHTML = `
            <h3>${job.companyName} - ${job.role}</h3>
            <p><b>Required Skills:</b> ${job.skills}</p>
            <p><b>Application Link:</b> <a href="${job.jobLink}" target="_blank">${job.jobLink}</a></p>
            <button onclick="editJob(${job.id})">Edit</button>
            <button onclick="deleteJob(${job.id})">Delete</button>
            <hr>
        `;
        jobListDiv.appendChild(jobItem);
    });
}

async function saveJob() {
    let jobData = {
        id: document.getElementById("jobId").value || null,
        companyName: document.getElementById("companyName").value,
        role: document.getElementById("role").value,
        tenthPercent: parseFloat(document.getElementById("tenthPercent").value),
        twelvePercent: parseFloat(document.getElementById("twelvePercent").value),
        cgpa: parseFloat(document.getElementById("cgpa").value),
        arrears: parseInt(document.getElementById("arrears").value),
        arrearHistory: document.getElementById("arrearHistory").value,
        skills: document.getElementById("skills").value,
        jobLink: document.getElementById("jobLink").value
    };

    let method = jobData.id ? "PUT" : "POST";
    let response = await fetch("/jobs/save", {
        method: method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(jobData)
    });

    let result = await response.text();
    document.getElementById("message").innerText = result;
    fetchJobs();
}

async function editJob(jobId) {
    let response = await fetch(`/jobs/${jobId}`);
    let job = await response.json();

    document.getElementById("jobId").value = job.id;
    document.getElementById("companyName").value = job.companyName;
    document.getElementById("role").value = job.role;
    document.getElementById("tenthPercent").value = job.tenthPercent;
    document.getElementById("twelvePercent").value = job.twelvePercent;
    document.getElementById("cgpa").value = job.cgpa;
    document.getElementById("arrears").value = job.arrears;
    document.getElementById("arrearHistory").value = job.arrearHistory;
    document.getElementById("skills").value = job.skills;
    document.getElementById("jobLink").value = job.jobLink;
}

async function deleteJob(jobId) {
    await fetch(`/jobs/delete/${jobId}`, { method: "DELETE" });
    fetchJobs();
}
