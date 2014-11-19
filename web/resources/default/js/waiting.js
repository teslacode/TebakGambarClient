//URL
var startUrl = "";
//URL

//Current User
var otherPlayer = document.getElementById('otherPlayer');
var websocket;
var isReady = false;
var userName = "";
//Current User

//Other User
var otherPlayerIsReady = false;
//Other User

$('button[name="ready"]').click(function () {
    $('button[name="ready"]').val((isReady ? "Siap" : "Belum Siap")).html((isReady ? "Siap" : "Belum Siap"));
    isReady = !isReady;
    websocket.send('{"module": "setReady", "user": "' + userName + '", "ready": ' + isReady + '}');
    checkReady();
});

function newPlayer(json) {
    setReady(json);
    if (json.reply) {
        websocket.send('{"module": "newPlayer", "user": "' + userName + '", "reply": false, "ready": ' + isReady + '}');
    }
}

function outRoom(json) {
    otherPlayer.innerHTML = "";
    otherPlayerIsReady = false;
}

function setReady(json) {
    otherPlayer.innerHTML = "<h2>" + (json.user ? json.user : "") + "</h2>" + "<h3>" + (json.ready ? "Siap" : "Belum Siap") + "</h3>";
    otherPlayerIsReady = json.ready;
    checkReady();
}

function checkReady() {
    if (isReady && otherPlayerIsReady) {
        $("#content").load(startUrl);
    }
}

function getRootUri() {
    if (window.location.protocol == 'http:') {
        wsUrl = 'ws://' + document.location.hostname + ':' + (document.location.hostname == "localhost" ? '8080' : '8000');
    } else {
        wsUrl = 'wss://' + document.location.hostname + ':' + (document.location.hostname == "localhost" ? '8080' : '8443');
    }

    return wsUrl;//"ws://" + (document.location.hostname == "" ? "localhost" : document.location.hostname) + ":8000";// +
    //(document.location.port == "" ? "8080" : document.location.port);
}

function init(endPoint, user, redirect) {
    startUrl = redirect;
    userName = user;

    console.log('WebSocket init ' + getRootUri() + endPoint);
    websocket = new WebSocket(getRootUri() + endPoint);
    websocket.onopen = function (evt) {
        onOpen(evt);
    };
    websocket.onmessage = function (evt) {
        onMessage(evt);
    };
    websocket.onerror = function (evt) {
        onError(evt);
    };
}

function onMessage(evt) {
    console.log(evt.data);
    var json = JSON.parse(evt.data);
    window[json.module](json);
}

function onOpen(evt) {
    console.log('WebSocket connection started');
    websocket.send('{"module": "newPlayer", "user": "' + userName + '", "reply": true, "ready": ' + isReady + '}');
}
function onError(evt) {
    alert("ERROR");
}

//window.addEventListener("load", init, false);