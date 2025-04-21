//const Name = sessionStorage.getItem("name");
//const role = sessionStorage.getItem("role");
//if (!Name) {
//    window.location.href = "/login";
//}
// if (!role || role.toLowerCase() !== "admin") {
//     alert("Unauthorized access!");
//     window.history.back(); // Reload the same page
// }
function logout() {
    sessionStorage.clear();
    localStorage.clear();
    window.location.href = '/logout';
}

// Placement Chart (Bar Chart)
const ctxPlacement = document.getElementById('placementChart').getContext('2d');
const placementChart = new Chart(ctxPlacement, {
    type: 'bar',
    data: {
        labels: ['2020', '2021', '2022', '2023', '2024'],
        datasets: [{
            label: 'Number of Students Placed',
            data: [150, 180, 220, 250, 280],
            backgroundColor: '#667eea',
            borderColor: '#556cd6',
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});

// Department-wise Placement (Pie Chart)
const ctxDepartment = document.getElementById('departmentChart').getContext('2d');
const departmentChart = new Chart(ctxDepartment, {
    type: 'pie',
    data: {
        labels: ['CSE', 'ECE', 'MECH', 'CIVIL', 'IT'],
        datasets: [{
            label: 'Department-wise Placements',
            data: [120, 80, 40, 20, 60],
            backgroundColor: ['#667eea', '#764ba2', '#ff4d4d', '#56cc9d', '#ffa600'],
        }]
    }
});
