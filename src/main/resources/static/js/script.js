function toggleBulb() {
    const bulb = document.getElementById("bulb");
    bulb.src = "images/bulbOnYellow.png";
    setTimeout(function() {
        bulb.classList.add("clicked");
        window.location.href = "/menu";
    }, 600);
}
