async function sendMessage() {
    let userInput = document.getElementById("userInput").value.trim();
    let chatContent = document.getElementById("chat-content");

    if (userInput === "") return;

    let userMessage = `<p><strong>You:</strong> ${userInput}</p>`;
    chatContent.innerHTML += userMessage;

    let response = await fetch("/chatbot/ask", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ message: userInput })
    });

    let botResponse = await response.text();
    let botMessage = `<p><strong>Chatbot:</strong> ${botResponse}</p>`;
    chatContent.innerHTML += botMessage;

    document.getElementById("userInput").value = "";
    chatContent.scrollTop = chatContent.scrollHeight;
}

function handleKeyPress(event) {
    if (event.key === "Enter") {
        sendMessage();
    }
}

function toggleChat() {
    let chatbox = document.getElementById("chatbox");
    chatbox.style.display = chatbox.style.display === "block" ? "none" : "block";
}
