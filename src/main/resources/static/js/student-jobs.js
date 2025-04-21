const Name = sessionStorage.getItem("name");
const role = sessionStorage.getItem("role");
if (!Name) {
    window.location.href = "/login";
}
 if (!role || role.toLowerCase() !== "student") {
     alert("Unauthorized access!");
     window.history.back(); // Reload the same page
 }
document.addEventListener("DOMContentLoaded", fetchJobs);

async function fetchJobs() {
    try {
        let response = await fetch("/jobs/list");

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        let jobs = await response.json();
        console.log("Jobs from API:", jobs);

         let email = sessionStorage.getItem("email");
         let stresponse = await fetch(`/profile/get?email=${email}`);
         let result = await stresponse.json();

        let studentProfile = {
            preferredRole: result.preferredRole || "",
            tenthPercent: result.tenthPercent || "",
            twelvePercent: result.twelvePercent || "",
            cgpa: result.cgpa || "",
            arrears: result.arrears || "",
            arrearHistory: result.arrearHistory || "No",
            skills: result.skills?result.skills.split(",").map(s => s.trim()) : []
        };

        console.log("Student Profile:", studentProfile);

        let preferredJobs = [];
        let otherJobs = [];

        jobs.forEach(job => {
            let isEligible =
                studentProfile.tenthPercent >= job.tenthPercent &&
                studentProfile.twelvePercent >= job.twelvePercent &&
                studentProfile.cgpa >= job.cgpa &&
                studentProfile.arrears <= job.arrears &&
                (job.arrearHistory === "No" ? studentProfile.arrearHistory === "Yes" : true) &&
                (job.skills ? job.skills.toString().split(",").some(skill => studentProfile.skills.includes(skill.trim())) : false);

            if (isEligible) {
                let jobRole = job.role.toLowerCase();
                let preferredRole = studentProfile.preferredRole.toLowerCase();

                if (jobRole.includes(preferredRole)) {
                    preferredJobs.push(job);
                } else {
                    otherJobs.push(job);
                }
            }
            else{
                otherJobs.push(job);
            }
        });

        displayJobs(preferredJobs, "preferredList");
        displayJobs(otherJobs, "otherList");

    } catch (error) {
        console.error("Error fetching jobs:", error);
    }
}

function displayJobs(jobs, elementId) {
    let jobListDiv = document.getElementById(elementId);
    jobListDiv.innerHTML = jobs.length > 0 ? "" : "<p>No jobs available</p>";

    jobs.forEach(job => {
        let jobItem = document.createElement("div");
        jobItem.innerHTML = `
            <h3>${job.companyName} - ${job.role}</h3>
            <p><b>Required Skills:</b> ${job.skills}</p>
            <a href="${job.jobLink}" target="_blank"><button>Apply</button></a>
            <hr>
        `;
        jobListDiv.appendChild(jobItem);
    });
}
