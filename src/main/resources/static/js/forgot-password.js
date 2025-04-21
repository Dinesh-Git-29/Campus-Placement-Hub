async function sendOTP() {
    let email = document.getElementById("email").value;
    if (!email) return alert("Enter your registered email");

    let response = await fetch("/auth/send-otp", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email })
    });

    let result = await response.json();
    if (result.success) {
        alert("OTP sent to your email.");
        document.getElementById("otpSection").style.display = "block";
    } else {
        alert(result.message);
    }
}

async function resetPassword() {
    let email = document.getElementById("email").value;
    let otp = document.getElementById("otp").value;
    let newPassword = document.getElementById("newPassword").value;

    let response = await fetch("/auth/reset-password", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, otp, newPassword })
    });

    let result = await response.json();
    if (result.success) {
        alert("Password reset successful! Login with your new password.");
        window.location.href = "/login";
    } else {
        alert(result.message);
    }
}
