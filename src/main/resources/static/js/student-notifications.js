document.addEventListener("DOMContentLoaded", fetchNotifications);

async function fetchNotifications() {
    let email = sessionStorage.getItem("email");
    let response = await fetch(`/notifications/${email}`);
    let notifications = await response.json();

    let notificationList = document.getElementById("notifications");
    notificationList.innerHTML = notifications.length > 0 ? "" : "<p>No new notifications</p>";

    notifications.forEach(notification => {
        let notificationItem = document.createElement("div");
        notificationItem.innerHTML = `<p>${notification.message}</p><hr>`;
        notificationList.appendChild(notificationItem);
    });
}
