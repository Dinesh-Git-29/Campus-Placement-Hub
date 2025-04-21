async function signup() {
    let name = document.getElementById("name").value;
    let email = document.getElementById("email").value;
    let phone = document.getElementById("phone").value;
    let password = document.getElementById("password").value;

    let response = await fetch("/auth/signup", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email, phone, password })
    });

    let message = await response.text();
    document.getElementById("message").innerText = message;

    if (message.includes("Check your email for OTP")) {
        window.location.href = "/verify-otp";
    }
}
