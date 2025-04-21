const Name = sessionStorage.getItem("name");
const role = sessionStorage.getItem("role");
if (!Name) {
    window.location.href = "/login";
}
 if (!role || role.toLowerCase() !== "student") {
     alert("Unauthorized access!");
     window.history.back(); // Reload the same page
 }
async function loadResources() {
    let category = document.getElementById("category").value;
    let url = "/resources/lists";

    if (category) {
        url += `?category=${encodeURIComponent(category)}`;
    }

    try {
        let response = await fetch(url);
        let resources = await response.json();

        let resourceList = document.getElementById("resourceList");
        resourceList.innerHTML = "";

        if (resources.length === 0) {
            resourceList.innerHTML = "<p>No resources found for this category.</p>";
            return;
        }

        resources.forEach(res => {
            let div = document.createElement("div");
            div.innerHTML = `<h3>${res.title} (${res.category})</h3>
                             <a href="/resources/download/${res.id}" target="_blank">Download File</a>
                             <hr>`;
            resourceList.appendChild(div);
        });
    } catch (error) {
        alert("Error loading resources: " + error.message);
    }
}

window.onload = loadResources;
