async function verifyOTP() {
    let email = document.getElementById("email").value;
    let otp = document.getElementById("otp").value;

    if (!email || !otp) {
        document.getElementById("message").innerText = "Please enter your email and OTP.";
        return;
    }

    let response = await fetch(`/auth/verify-otp?email=${encodeURIComponent(email)}&otp=${encodeURIComponent(otp)}`, {
        method: "POST"
    });

    let message = await response.text();
    document.getElementById("message").innerText = message;

    if (message === "Email verified successfully!") {
        setTimeout(() => {
            window.location.href = "/login"; // Redirect to login
        }, 3000);
    }
}

async function resendOTP() {
    let email = document.getElementById("email").value;

    if (!email) {
        document.getElementById("message").innerText = "Please enter your email.";
        return;
    }

    let response = await fetch(`/auth/resend-otp?email=${encodeURIComponent(email)}`);
    let message = await response.text();
    document.getElementById("message").innerText = message;
}
