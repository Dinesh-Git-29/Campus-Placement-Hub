const Name = sessionStorage.getItem("name");
const role = sessionStorage.getItem("role");
if (!Name) {
    window.location.href = "/login";
}
 if (!role || role.toLowerCase()!== "admin") {
     alert("Unauthorized access!");
     window.history.back(); // Reload the same page
 }
document.getElementById("resourceForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    let fileInput = document.getElementById("file").files[0];
    if (!fileInput) {
        alert("Please select a file to upload!");
        return;
    }

    let formData = new FormData();
    formData.append("category", document.getElementById("category").value);
    formData.append("title", document.getElementById("title").value);
    formData.append("file", fileInput);

    try {
        let response = await fetch("/resources/upload", {
            method: "POST",
            body: formData
        });

        let result = await response.text();

        if (response.ok) {
            alert("Resource uploaded successfully!");
            loadResources();
            document.getElementById("resourceForm").reset();
        } else {
            alert("Error uploading resource: " + result);
        }
    } catch (error) {
        alert("Error connecting to server: " + error.message);
    }
});

// Load resources
async function loadResources() {
    try {
        let response = await fetch("/resources/list");
        let resources = await response.json();

        let resourceList = document.getElementById("resourceList");
        resourceList.innerHTML = "";

        resources.forEach(res => {
            let div = document.createElement("div");
            div.innerHTML = `<h3>${res.title} (${res.category})</h3>
                             <a href="/resources/download/${res.id}" target="_blank">Download File</a>
                             <button onclick="deleteResource(${res.id})">Delete</button>
                             <hr>`;
            resourceList.appendChild(div);
        });
    } catch (error) {
        alert("Error loading resources: " + error.message);
    }
}

// Delete resource
async function deleteResource(id) {
    if (!confirm("Are you sure you want to delete this resource?")) return;

    try {
        let response = await fetch(`/resources/delete/${id}`, { method: "DELETE" });

        let result = await response.text();

        if (response.ok) {
            alert("Resource deleted successfully!");
            loadResources();
        } else {
            alert("Error deleting resource: " + result);
        }
    } catch (error) {
        alert("Error connecting to server: " + error.message);
    }
}

window.onload = loadResources;
