async function login() {
    let emailOrPhone = document.getElementById("emailOrPhone").value;
    let password = document.getElementById("password").value;

    let response = await fetch(`/auth/login?emailOrPhone=${emailOrPhone}&password=${password}`, {
        method: "POST"
    });

    let result = await response.json();

    if (result.status === "success") {
        // Store user details in session storage
        sessionStorage.setItem("name", result.name);
        sessionStorage.setItem("email", result.email);
        sessionStorage.setItem("role", result.role);

        // Redirect based on role
        if (result.role.toLowerCase() === "admin") {
            window.location.href = "/ad-home";
        } else {
            window.location.href = "/st-home";
        }
    }
    else if(result.status === "error" && result.message === "Email not verified!") {
        window.location.href = "/verify-otp";
    }
    else{
    document.getElementById("loginMessage").innerText = result.message;
    }
}
