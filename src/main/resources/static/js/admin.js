const Name = sessionStorage.getItem("name");
const role = sessionStorage.getItem("role");
if (!Name) {
    window.location.href = "/login";
}
 if (!role || role.toLowerCase !== "admin") {
     alert("Unauthorized access!");
     window.history.back(); // Reload the same page
 }
document.getElementById("criteriaForm").addEventListener("submit", function(event) {
    event.preventDefault();

    let minTenth = document.getElementById("minTenth").value;
    let minTwelfth = document.getElementById("minTwelfth").value;
    let minCGPA = document.getElementById("minCGPA").value;
    let maxArrears = document.getElementById("maxArrears").value;
    let arrearHistory = document.getElementById("arrearHistory").value;

    let queryParams = `minTenth=${minTenth}&minTwelfth=${minTwelfth}&minCGPA=${minCGPA}&maxArrears=${maxArrears}&arrearHistory=${arrearHistory}`;
    window.location.href = `/admin/download-eligible-students?${queryParams}`;
});
