const Name = sessionStorage.getItem("name");
if (!Name) {
    window.location.href = "/login";
}
document.addEventListener("DOMContentLoaded", function () {
    // Fetch stored user details from session storage
    document.getElementById("username").value = sessionStorage.getItem("name") || "";

    // Fetch existing profile details from backend
    loadComments();

    setInterval(loadComments, 5000);
});
document.getElementById("commentForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    let username = document.getElementById("username").value;
    let commentText = document.getElementById("commentText").value;
    let file = document.getElementById("commentFile").files[0];

    let formData = new FormData();
    formData.append("username", username);
    formData.append("commentText", commentText);
    if (file) {
        formData.append("file", file);
    }

    try {
        let response = await fetch("http://localhost:8080/comments", {
            method: "POST",
            body: formData
        });

        if (!response.ok) throw new Error("Failed to post comment");

        alert("Comment posted!");
        document.getElementById("commentForm").reset();
        loadComments();
    } catch (error) {
        console.error("Error posting comment:", error);
        alert("Failed to post comment. Please try again.");
    }
});

async function loadComments() {
    document.getElementById("username").value = sessionStorage.getItem("name") || "";
    try {
        let response = await fetch("http://localhost:8080/comments");
        if (!response.ok) throw new Error("Failed to fetch comments");

        let comments = await response.json();
        let commentsList = document.getElementById("commentsList");
        commentsList.innerHTML = "";

        // Reverse the comments array to show the latest at the top
        comments.reverse().forEach(comment => {
            let li = document.createElement("li");
            li.innerHTML = `<strong>${comment.username}</strong>: ${comment.commentText}`;
            if (comment.fileUrl) {
                li.innerHTML += ` <br> <a href="http://localhost:8080/files/${comment.fileUrl}" download>Download File</a>`;
            }
            li.innerHTML += `<br><button class="reply-btn" onclick="showReplyBox(${comment.id})">Reply</button>`;

            let repliesDiv = document.createElement("div");
            repliesDiv.id = `replies-${comment.id}`;
            li.appendChild(repliesDiv);

            commentsList.appendChild(li); // Adds at the top (because reversed)
            loadReplies(comment.id);
        });
    } catch (error) {
        console.error("Error loading comments:", error);
    }
}


async function loadReplies(commentId) {
    try {
        let response = await fetch(`http://localhost:8080/replies/${commentId}`);
        if (!response.ok) throw new Error(`Failed to fetch replies for comment ${commentId}`);

        let replies = await response.json();
        let repliesDiv = document.getElementById(`replies-${commentId}`);
        repliesDiv.innerHTML = "";

        replies.forEach(reply => {
            let replyEl = document.createElement("p");
            replyEl.innerHTML = `<strong>${reply.username}</strong>: ${reply.replyText}`;
            repliesDiv.appendChild(replyEl);
        });
    } catch (error) {
        console.error("Error loading replies:", error);
    }
}

function showReplyBox(commentId) {
    let replyText = prompt("Enter your reply:");
    if (replyText) {
        postReply(commentId, replyText);
    }
}

async function postReply(commentId, replyText) {
    try {
        let response = await fetch("http://localhost:8080/replies", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                comment: { id: commentId },
                username: sessionStorage.getItem("name") || "",
                replyText
            })
        });

        if (!response.ok) throw new Error("Failed to post reply");

        loadReplies(commentId);
    } catch (error) {
        console.error("Error posting reply:", error);
        alert("Failed to post reply. Please try again.");
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const commentList = document.querySelector(".comments-container ul");
    const commentInput = document.getElementById("comment-input"); // Adjust ID as needed
    const submitButton = document.getElementById("submit-button"); // Adjust ID as needed

    function addNewComment(commentText) {
        const newComment = document.createElement("li");
        newComment.textContent = commentText;

        // Add new comment at the top of the list
        commentList.prepend(newComment);
    }

    submitButton.addEventListener("click", function () {
        const commentText = commentInput.value.trim();
        if (commentText !== "") {
            addNewComment(commentText);
            commentInput.value = ""; // Clear input
        }
    });
});





