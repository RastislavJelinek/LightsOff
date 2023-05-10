const credits = document.getElementById("credits");
const menu = document.getElementById("menu");
function showMenu() {
    credits.style.opacity = "1";
    menu.style.opacity = "1";
}
function returnBack() {
    credits.style.opacity = "0";
    setTimeout(function() {
        window.location.href = "/menu";
    }, 1000);
}