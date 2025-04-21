function toggleMenu() {
    const navMenu = document.getElementById("nav-menu");
    navMenu.classList.toggle("show");
}

// Show statistics one by one with delay
document.addEventListener("DOMContentLoaded", function () {
    const stats = document.querySelectorAll(".stat-box");
    stats.forEach((stat, index) => {
        setTimeout(() => {
            stat.classList.add("show");
        }, index * 1500); // Show each stat with a 1.5s delay
    });
});
