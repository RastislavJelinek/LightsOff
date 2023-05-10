
const loginLink = document.getElementById('login');
let user = JSON.parse(localStorage.getItem('user'));
if(user != null){
    loginLink.textContent = user;
}