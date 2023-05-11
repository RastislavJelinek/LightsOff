let won = false;




function switchLight(row, column) {
    if (won) return;
    $.ajax({
        type: "POST",
        url: "/switchLight",
        contentType: "application/json",
        data: JSON.stringify({ row: row, column: column }),
        success:  function() {
            game();
            //location.reload(); // force reload the page from the server
        },
        error: function() {
            console.log('error');
        }
    });
}


function game() {
    $.ajax({
        type: "GET",
        url: "/field",
        contentType: "application/json",
        success:  function(response) {
            console.log(response);
            gameMap(response.map);
            let score = response.score;
            let level = response.level;
            $('#score').text(`Score: ${score}`);
            $('#level').text(`Level: ${level}`);
            won = (response.state === "SOLVED");
            if(won){
                $('#score').text(`Score: ${level+1}`);
                document.getElementById("message").style.opacity = "1";
            }
        },
        error: function() {
            console.log('error');
        }
    });
}

function nextMap(){
    if (!won) return;
    $.ajax({
        type: "POST",
        url: "/nextLevel",
        contentType: "application/json",
        success:  function() {
            //location.reload(); // force reload the page from the server
            game();
            document.getElementById("message").style.opacity = "0";
        },
        error: function() {
            console.log('error');
        }
    });
}
function newGame(){
    $.ajax({
        type: "POST",
        url: "/newGame",
        contentType: "application/json",
        success:  function() {
            game();
            document.getElementById("message").style.opacity = "0";
        },
        error: function() {
            console.log('error');
        }
    });
}

function gameMap(map) {
    const gameTable = document.getElementById("gameTable");
    console.log(gameTable);
    if (gameTable !== null && gameTable.hasChildNodes()) {
        const rows = gameTable.childNodes;
        for (let i = 0; i < rows.length; i++) {
            const columns = rows[i].childNodes;
            for (let j = 0; j < columns.length; j++) {
                const tile = map[i][j];
                const img = columns[j].querySelector('img');
                if (getImageName(tile.state) !== img.getAttribute('src')) {
                    img.setAttribute('src', "../images/" + getImageName(tile.state) + ".png");
                }
            }
        }
    } else {
        for (let row = 0; row < map.length; row++) {
            const tr = document.createElement("tr");
            for (let column = 0; column < map[row].length; column++) {
                const tile = map[row][column];
                const td = document.createElement("td");
                const img = document.createElement("img");
                img.src = "../images/" + getImageName(tile.state) + ".png";
                img.id = "gameBulb";
                img.addEventListener("click", function() {
                    switchLight(row, column);
                });
                td.appendChild(img);
                tr.appendChild(td);
            }
            gameTable.appendChild(tr);
        }
    }
}


function getImageName(state) {
    switch(state) {
        case 'LIGHT_ON':
            return 'bulbOnYellow';
        case 'LIGHT_OFF':
            return 'bulbOff';
        default:
            return '';
    }
}