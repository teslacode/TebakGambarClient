//delay
var delay = 5;
//delay

//URL
var checkJawabanUrl = "";
var finishUrl = "";
var getGambarUrl = "";
var loadingImages = "";
//URL

//Web Socket
var websocket;
//Web Socket

//Current Player
var firstTurn = false;
var index = 0;
var nilai = 0;
var isReady = false;
var getGambarCount = 0;
var isMyTurn = false;
var timeSoal = 120;
var timeSoalCount = timeSoal;
var timeTurn = 10;
var timeTurnCount = timeTurn;
var time = 0;
var userName = "";
//Current Player

//Other Player
var otherNilai = 0;
var otherPlayerIsReady = false;
//Other Player

function setTurnCount() {
    timeTurnCount = timeTurn;
    if (isMyTurn) {
        getGambarCount = 1;
        $("#timeTurn").find(".progress-bar").html("Giliran Kamu");
        $("#timeTurn").find(".progress-bar").removeClass("progress-bar-warning");
        $("#timeTurn").find(".progress-bar").removeClass("progress-bar-danger");
        $("#timeTurn").find(".progress-bar").addClass("progress-bar-success");
    } else {
        getGambarCount = 0;
        $("#timeTurn").find(".progress-bar").html("Giliran Musuh");
        $("#timeTurn").find(".progress-bar").removeClass("progress-bar-success");
        $("#timeTurn").find(".progress-bar").removeClass("progress-bar-warning");
        $("#timeTurn").find(".progress-bar").addClass("progress-bar-danger");
    }
    $("#timeTurn").find(".progress-bar").attr("style", "width: 100%;");
}

function setSoalCount() {
    timeSoalCount = timeSoal;
    $("#timeSoal").find(".progress-bar").removeClass("progress-bar-danger");
    $("#timeSoal").find(".progress-bar").removeClass("progress-bar-warning");
    $("#timeSoal").find(".progress-bar").html(timeSoalCount);
    $("#timeSoal").find(".progress-bar").attr("style", "width: 100%;");
}

function getGambar(index, x, y) {
    var div = document.getElementById(index.toString() + x.toString() + y.toString());
    div.innerHTML = '<img style="width: 100%; height: 100%;" src="' + getGambarUrl + '?index=' + index + '&x=' + x + '&y=' + y + '"/>';
}

function getPotonganGambar(index, x, y) {
    $('input[name="jawaban"]').focus();
    if (isReady && otherPlayerIsReady && isMyTurn && getGambarCount > 0) {
        getGambar(index, x, y);
        getGambarCount = 0;
        websocket.send('{"module": "getGambar", "user": "' + userName + '", "reply": "false", "index": "' + index + '", "x": "' + x + '", "y": "' + y + '"}');
    } else {
        $("#popup").html("Tunggu Giliran").dialog({
            modal: true,
            draggable: true,
            resizable: false,
            buttons: {
                "Close": function () {
                    $(this).dialog('close');
                }
            },
            beforeClose: function () {
            }
        });
    }
}

function nextSoal() {
    $("#" + index).hide("slow");
    index++;
    $("#" + index).show("slow");
    setSoalCount();
    setTurnCount();
}

function addMessage(user, jawaban) {
    var message = document.getElementById("message");
    message.innerHTML = user + ": " + jawaban + "<br/>" + message.innerHTML;
}

function setJawabanBenar(pesan) {
    otherPlayerIsReady = false;
    isReady = false;
    firstTurn = !firstTurn;
    isMyTurn = firstTurn;
    setTurnCount();
    setSoalCount();
    for (i = 0; i < 4; i++) {
        for (j = 0; j < 3; j++) {
            getGambar(index, i, j);
        }
    }
    $("#popup").html(pesan).dialog({
        modal: true,
        draggable: true,
        resizable: false,
        buttons: {
            "Close": function () {
                $(this).dialog('close');
            }
        },
        beforeClose: function () {
            websocket.send('{"module": "readyConnection", "user": "' + userName + '", "reply": false, "ready": true}');
            nextSoal();
            isReady = true;
            if (index == 4) {
                i = 0;
                j = 0;
                timeSoal = 60;
                setSoalCount();
            }
        }
    });
}

function checkJawaban(jawaban) {
    addMessage(userName, jawaban);
    $.ajax({
        url: checkJawabanUrl,
        type: "POST",
        data: {index: index, jawaban: jawaban},
        error: function () {
            alert('ERROR');
        },
        success: function (data) {
            websocket.send('{"module": "postJawaban", "user": "' + userName + '", "index": "' + index + '", "jawaban": "' + jawaban + '", "hasil": "' + data + '"}');
            if (data == "BENAR") {
                nilai++;
                $("#nilai").html("Nilai: " + nilai);
                setJawabanBenar("Kamu Benar");
            }
        }
    });
}

function postJawaban(jawaban) {
    if (isReady && otherPlayerIsReady && isMyTurn) {
        checkJawaban(jawaban);
    } else {
        $("#popup").html("Tunggu Giliran").dialog({
            modal: true,
            draggable: true,
            resizable: false,
            buttons: {
                "Close": function () {
                    $(this).dialog('close');
                }
            },
            beforeClose: function () {
            }
        });
    }
}

function outGame(json) {
    if (index < 5) {
        $("#popup").html("Kamu Menang").dialog({
            modal: true,
            draggable: true,
            resizable: false,
            buttons: {
                "Close": function () {
                    $(this).dialog('close');
                }
            },
            beforeClose: function () {
                window.location.replace(finishUrl);
            }
        });
    }
}

function changeTurn(json) {
    time++;
}
function readyConnection(json) {
    otherPlayerIsReady = true;
    $("#otherPlayer").find(".userName").html(json.user);
    if (json.reply) {
        websocket.send('{"module": "readyConnection", "user": "' + userName + '", "reply": false, "ready": true}');
    }
    $("#popup").dialog('close');
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

function init(endPoint, user, checkJawabanPath, finishPath, getGambarPath, first, loadingImagesUrl) {
    userName = user;
    checkJawabanUrl = checkJawabanPath;
    finishUrl = finishPath;
    getGambarUrl = getGambarPath;
    firstTurn = first;
    isMyTurn = firstTurn;
    loadingImages = loadingImagesUrl;

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

    setSoalCount();
    setTurnCount();

    $("#popup").html('<img src="' + loadingImages + '">').dialog({
        modal: true,
        draggable: true,
        resizable: false,
        beforeClose: function () {
        }
    });
}

function onMessage(evt) {
    console.log(evt.data);
    var json = JSON.parse(evt.data);
    if (json.module == "getGambar") {
        getGambar(json.index, json.x, json.y);
    } else if (json.module == "postJawaban") {
        addMessage(json.user, json.jawaban);
        if (json.hasil == "BENAR") {
            var pesan = json.user + " Benar";
            index = json.index;
            otherNilai++;
            $("#otherPlayer").find(".nilai").html("Nilai: " + otherNilai);
            setJawabanBenar(pesan);
        }
    } else {
        window[json.module](json);
    }
}

function onOpen(evt) {
    console.log('WebSocket connection started');
    isReady = true;
    $("#" + index).show();
    equalheight('.potongan-gambar');
    time++;
    websocket.send('{"module": "readyConnection", "user": "' + userName + '", "reply": true, "ready": true}');
}
function onError(evt) {
    alert("ERROR");
}

function timerTurn() {
    if (timeTurnCount >= 0 && isReady && otherPlayerIsReady && time) {
        $("#timeTurn").find(".progress-bar").attr("style", "width: " + ((timeTurnCount-- / timeTurn) * 100) + "%;");
        var currentTime = (timeTurnCount / timeTurn) * 100;
        if (isMyTurn) {
            if (currentTime < 20) {
                $("#timeTurn").find(".progress-bar").removeClass("progress-bar-warning");
                $("#timeTurn").find(".progress-bar").addClass("progress-bar-danger");
            } else if (currentTime < 50) {
                $("#timeTurn").find(".progress-bar").removeClass("progress-bar-success");
                $("#timeTurn").find(".progress-bar").addClass("progress-bar-warning");
            }
        } else {
            if (currentTime < 30) {
                $("#timeTurn").find(".progress-bar").removeClass("progress-bar-danger");
                $("#timeTurn").find(".progress-bar").addClass("progress-bar-warning");
            }
        }
    } else if (timeTurnCount < 0 && isReady && otherPlayerIsReady && time) {
        websocket.send('{"module": "changeTurn", "user": "' + userName + '", "reply": false, "ready": true}');
        time = (time > 0 ? time - 1 : time);
        isMyTurn = !isMyTurn;
        setTurnCount();
    }
}

function timerSoal() {
    if (timeSoalCount >= 0 && isReady && otherPlayerIsReady) {
        $("#timeSoal").find(".progress-bar").html(timeSoalCount);
        $("#timeSoal").find(".progress-bar").attr("style", "width: " + ((timeSoalCount-- / timeSoal) * 100) + "%;");
        var currentTime = (timeSoalCount / timeSoal) * 100;
        if (currentTime < 20) {
            $("#timeSoal").find(".progress-bar").removeClass("progress-bar-warning");
            $("#timeSoal").find(".progress-bar").addClass("progress-bar-danger");
        } else if (currentTime < 50) {
            $("#timeSoal").find(".progress-bar").removeClass("progress-bar-success");
            $("#timeSoal").find(".progress-bar").addClass("progress-bar-warning");
        }
    } else if (timeSoalCount < 0 && isReady && otherPlayerIsReady) {
        firstTurn = !firstTurn;
        isMyTurn = firstTurn;
        nextSoal();
    }
}

function finishGame() {
    if (index == 5) {
        var message;
        if (nilai < otherNilai) {
            message = "Kamu Kalah";
        } else if (nilai > otherNilai) {
            message = "Kamu Menang";
        } else {
            message = "Seri";
        }
        $("#popup").html(message).dialog({
            modal: true,
            draggable: true,
            resizable: false,
            buttons: {
                "Close": function () {
                    $(this).dialog('close');
                }
            },
            beforeClose: function () {
                window.location.replace(finishUrl);
            }
        });
    }
}

function randomGetGambar() {
    j = (i == 4 ? j + 1 : j);
    i = i % 4;
    getGambar(index, i, j);
    i++;
}

function timer() {
    if (index < 4) {
        if (isReady && otherPlayerIsReady && delay >= 0) {
            if (delay == 0) {
                $("#popup").dialog('close');
            } else {
                $("#popup").html('<img src="' + loadingImages + '"> ' + delay).dialog({
                    modal: true,
                    draggable: true,
                    resizable: false,
                    beforeClose: function () {
                    }
                });
            }
            delay--;
        } else if (isReady && otherPlayerIsReady && delay < 0) {
            timerTurn();
            timerSoal();
        }
    } else if (index == 4) {
        if (isReady && otherPlayerIsReady) {
            if (timeSoalCount % 5 == 0 && i <= 4 && j < 3) {
                randomGetGambar();
            }
            delay--;
            isMyTurn = true;
            setTurnCount();
            getGambarCount = 0;
            timerSoal();

        }
    }
    else if (index == 5) {
        finishGame();
    }
}

function equalheight(container) {
    var height = $(container).width() * 0.71;
    $(container).height(height);
}

$(window).resize(function () {
    equalheight('.potongan-gambar');
});

//window.addEventListener("load", init, false);