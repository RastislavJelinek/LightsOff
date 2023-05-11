function newGame() {
    const userInput = document.getElementById("numSelector");
    const userValue = userInput.value;
    if(userValue.trim() === '')return;
    event.preventDefault();
    $.ajax({
        type: "POST",
        url: "/newGame",
        contentType: "application/json",
        data: JSON.stringify({ number: userValue}),
        success:  function() {
            window.location.href = "/game";
        },
        error: function() {
            showError();
        }
    });
}

function showError() {
    const errorMessage = document.getElementById("error-message");
    errorMessage.classList.add("show-error-message");

    setTimeout(() => {
        errorMessage.classList.remove("show-error-message");
    }, 3000);
}