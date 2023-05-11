const header = document.getElementById("header");
const menu = document.getElementById("menu");
const bulb = document.getElementById("bulb");
const loginLink = document.getElementById('login');

function showMenu() {
    let user = JSON.parse(localStorage.getItem('user'));
    if(user != null){
        loginLink.textContent = 'Logout';
        loginLink.onclick = logOut;
        let ratingElem = document.getElementById('rating');

        fetch('/rating')
            .then(response => response.json())
            .then(data => {
                if (data >= 0) {
                    ratingElem.textContent = 'Thank you for your rating: ' + data;
                }else{
                    ratingElem.textContent = 'Please leave Us rating!';
                }
            })
            .catch(error => console.error(error));


    }
    setTimeout(function() {
        bulb.style.opacity = "1";
        menu.style.opacity = "1";
        header.style.opacity = "1";
    }, 1000);
}

function logOut() {
    let user = JSON.parse(localStorage.getItem('user'));
    if (user != null) {
        loginLink.textContent = 'Login';
        loginLink.onclick = login;
        localStorage.setItem('user',null);
        location.reload(); // force reload the page from the server
    }
}


function hideMenu(){
    header.style.opacity = "0";
    menu.style.opacity = "0";
    bulb.style.opacity = "0";
}
function login(){
    hideMenu();
    setTimeout(function() {
        window.location.href = "/login";
    }, 1000);
}

function newGame() {
    hideMenu();
    setTimeout(function() {
        window.location.href = "/gameSettings";
    }, 1000);
}
function continueGame(){
    hideMenu();
    setTimeout(function() {
        window.location.href = "/game";
    }, 1000);
}

function showCredits() {
    hideMenu();
    setTimeout(function() {
        window.location.href = "/credits";
    }, 1000);
}
function showScoreBoard(){
    hideMenu();
    setTimeout(function() {
        window.location.href = "/showScoreBoard";
    }, 1000);
}
function showComments(){
    hideMenu();
    setTimeout(function() {
        window.location.href = "/showComments";
    }, 1000);
}

const ratingDialog = document.createElement('div');
ratingDialog.classList.add('rating-dialog');





function showRatingDialog() {
    ratingDialog.innerHTML = `
        <h3>Please rate our website:</h3>
        <div class="stars">
            ${generateStarsHTML()}
        </div>
        <button onclick="submitRating()">Submit</button>
    `;
    document.body.appendChild(ratingDialog);
}

function generateStarsHTML() {
    let starsHTML = '';
    for (let i = 1; i <= 5; ++i) {
        starsHTML += `
            <input type="radio" id="star${i}" name="rating" value="${i}" />
            <label class="star" for="star${i}"></label>
        `;
    }
    return starsHTML;
}

function submitRating() {
    const ratingInputs = ratingDialog.querySelectorAll('input[name="rating"]');
    let ratingValue = null;
    for (const input of ratingInputs) {
        if (input.checked) {
            ratingValue = input.value;
            break;
        }
    }
    if (ratingValue !== null) {
        $.ajax({
            type: "POST",
            url: "/setrating",
            contentType: "application/json",
            data: JSON.stringify({ rating: ratingValue}),
            success:  function() {
                window.location.href = "/menu";
            },
            error: function() {
            }
        });
    } else {
        console.log('No rating selected.');
    }
}
