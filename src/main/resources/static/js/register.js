async function register() {

    const userInput = document.getElementById("username");
    const userValue = userInput.value;
    const passwordInput = document.getElementById("password");
    const passwordValue = passwordInput.value;
    if(userValue.trim() === '' || passwordValue.trim() === ''){
        return;
    }
    event.preventDefault();
    console.log('tu');
    $.ajax({
        type: "POST",
        url: "/registeruser",
        contentType: "application/json",
        data: JSON.stringify({ username: userValue, password: passwordValue }),
        success:  function(response) {
            if(response === false){return;}
            console.log("tu");
            localStorage.setItem('user', JSON.stringify(userValue));
            window.location.href = "/menu";
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
