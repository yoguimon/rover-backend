Swal.fire({
  title: "Rover!",
  text: "🡱 Adelante 🡳 Atras 🡰 Girar izquierda 🡲 Girar derecha",
  imageUrl: "https://static.vecteezy.com/system/resources/previews/006/059/930/non_2x/pc-keyboard-arrow-keys-linear-icon-isolated-on-white-background-free-vector.jpg",
  imageWidth: 300,
  imageHeight: 200,
  imageAlt: "Rover",
  confirmButtonColor: "#82593B"
});
let rover = {};
getRover();
getObstacles();
async function getRover(){
    const request = await fetch('api/rover',{
        method: 'GET',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            }
    });
    rover = await request.json();
    updateRoverPosition();

}
function updateRoverPosition(){
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
            data.rover=rover;
            data.upOrdown=1;
            moveRover(data);
            break;
        case "ArrowDown":
            console.log("Presionaste ↓ (Abajo)");
            data.rover=rover;
            data.upOrdown=0;
            moveRover(data);
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
     getRover();
     console.log(response);
}
async function moveRover(data){
     const request = await fetch('api/rover/move', {
        method: 'PUT',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      });
     const response = await request.text();
     getRover();
     console.log(response);
}
async function getObstacles(){
    const request = await fetch('api/obstacle',{
        method: 'GET',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            }
    });
    if (!request.ok) {
        console.error("Error al obtener los obstáculos");
        return;
    }

    let obstacles = await request.json();
    updateObstaclePosition(obstacles);

}
function updateObstaclePosition(obstacles){
    const map = document.getElementById("map");
    map.innerHTML = ""; // Elimina los obstáculos anteriores

    obstacles.forEach(obstacle => {
        const obstacleImg = document.createElement("img");
        obstacleImg.src = "/img/roca.png";
        obstacleImg.width = 150; // Ajusta el tamaño
        obstacleImg.height = 150;
        obstacleImg.classList.add("position-absolute"); // Usa Bootstrap para posicionarlo

        obstacleImg.style.left = obstacle.x + "%";
        obstacleImg.style.top = obstacle.y + "%";

        map.appendChild(obstacleImg);
    });
}