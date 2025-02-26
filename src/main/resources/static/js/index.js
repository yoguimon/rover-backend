getRover();
let rover = {};
async function getRover(){
    const request = await fetch('api/rover',{
        method: 'GET',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            }
    });
    rover = await request.json();
    updateRoverPosition(rover);

}
function updateRoverPosition(rover){
    const roverImg = document.getElementById("rover");

    roverImg.style.left = rover.x + "%";
    roverImg.style.top = rover.y + "%";

    // Cambiar el src según la dirección
    switch (rover.direction) {
        case "NORTH":
            roverImg.src = "/img/rovern.png";
            break;
        case "EAST":
            roverImg.src = "/img/rovere.png";
            break;
        case "SOUTH":
            roverImg.src = "/img/rovers.png";
            break;
        case "WEST":
            roverImg.src = "/img/roverw.png";
            break;
    }
}

document.addEventListener("keydown", function(event) {
    let data = {};
    switch (event.key) {
        case "ArrowUp":
            console.log("Presionaste ↑ (Arriba)");
            rover.x+=5;
            console.log("posx=",rover.x);
            console.log("posy=",rover.y);
            break;
        case "ArrowDown":
            console.log("Presionaste ↓ (Abajo)");
            rover.y+=5;
            console.log("posx=",rover.x);
            console.log("posy=",rover.y);
            break;
        case "ArrowLeft":
            console.log("Presionaste ← (Izquierda)");
            data.headPosition="LEFT";
            data.actualDirection=rover.direction;
            changeDirectionDB(data);
            break;
        case "ArrowRight":
            console.log("Presionaste → (Derecha)");
            data.headPosition="RIGHT";
            data.actualDirection=rover.direction;
            changeDirectionDB(data);
            break;
    }
    getRover(rover);
});

async function changeDirectionDB(data){
     const request = await fetch('api/rover/direction', {
        method: 'PUT',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      });
      const response = await request.text();
     console.log(response);
}