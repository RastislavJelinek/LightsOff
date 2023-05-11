function addComment(){
    const userInput = document.getElementById("name");
    const userValue = userInput.value;
    const userComment = document.getElementById("comment");
    const commentValue = userComment.value;
    if(userValue.trim() === '' || commentValue.trim() === '')return;
    event.preventDefault();
    $.ajax({
        type: "POST",
        url: "/addComment",
        contentType: "application/json",
        data: JSON.stringify({ username: userValue, comment: commentValue }),
        success:  function() {
            window.location.href = "/showComments";
        },
        error: function() {
        }
    });
}