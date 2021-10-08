function repeatAudio() {
    var player = document.getElementById("myPlayer");

    if (player.loop === false) {
        player.loop = true;
        document.getElementById("myRepeat").textContent = "Repeat ON";

    } else {
        player.loop = false;
        document.getElementById("myRepeat").textContent = "Repeat OFF";
    }
}

function playAudio(item, id) {
    var e = document.getElementById(id);
    var str = e.options[e.selectedIndex].text;
    var path = e.options[e.selectedIndex].value;
    console.log(path);
    if (path != "none") {
        var audio = new Audio(path);

        if (document.getElementById(item).checked)
            audio.play();

        setTimeout(function () {
            //Prevents long audios from playing until the end, showing only 5 seconds of audio like a little preview when choosing
            var fadeOut = setInterval(function () {

                if (audio.volume > 0.0000000000001) {
                    audio.volume = audio.volume -= 0.1;
                } else {
                    clearInterval(fadeOut);
                }

            }, 300); //delay in milliseconds for the fade out
        }, 5000); //delay in milliseconds for the preview

    } else {
        document.getElementById(item).checked = false;
        alert("You must select the audio to this line first.");
    }
}

$(document).ready(function () {
    counter = 5;
    $("#SongTable").ready(function () {

        //Function to open json file
        $.getJSON("/labi2018-p2-g3/list?type=", function (data) {
            //add Samples
            for (j = 1; j <= counter; j++) {
                for (i = 0; i < data.length; i++) {
                    $("#mySelect" + j).append("<option value = /labi2018-p2-g3/audio/" + data[i].name + ".wav>" + data[i].name + "</option>");
                }
            }
            //add Effects
            for (j = 1; j <= counter; j++) {
                $("#myEffect" + j).append("<option value ='fadein'>Fade In</option>");
                $("#myEffect" + j).append("<option value ='fadeout'>Fade Out</option>");
                $("#myEffect" + j).append("<option value ='echo'>Echo</option>");
            }

        });
    });

    $("#mySongList").ready(function () {
        //Function to open json file
        $.getJSON("/labi2018-p2-g3/list?type=songs", function (data) {
            $.getJSON("/labi2018-p2-g3/SongVotes?", function (votes) {
                $.get("/labi2018-p2-g3/loguser?", function (logeduser) {
                    console.log(logeduser)
                    for (i = 0; i < data.length; i++) {
                        like = false;
                        dislike = false;
                        contLike = 0;
                        contDislike = 0;
                        for (j = 0; j < votes.length; j++) {
                            if (data[i].id == votes[j].SongId) {
                                if (votes[j].Vote == "-1") {
                                    contDislike = contDislike + 1;
                                } else {
                                    contLike = contLike + 1;
                                }
                                if (votes[j].Vote == "-1" && votes[j].User == logeduser) {
                                    dislike = true;
                                    like = false;
                                } else {
                                    dislike = false;
                                    like = true;
                                }
                            }
                        }
                        $("#mySongList").append("<li><a href=\"\" data-value=\"../audio/" + data[i].name + ".wav\">" + data[i].name + "    </a> <button id=\"likebt" + data[i].id + "\"class=\"btn btn-success btn-sm\" type=\"button\" onclick=\"location.href='/labi2018-p2-g3/vote?id=" + data[i].id + "&user=" + logeduser + "&points=1\';\"><i class=\"fas fa-thumbs-up\"></i> " + contLike + " </button> <button id=\"dislikebt" + data[i].id + "\"class=\"btn btn-danger btn-sm\" type=\"button\" onclick=\"location.href='/labi2018-p2-g3/vote?id=" + data[i].id + "&user=" + logeduser + "&points=-1\';\"><i class=\"fas fa-thumbs-down\"> </i> " + contDislike + " </button></li>");

                        document.getElementById("likebt" + data[i].id).disabled = like;
                        document.getElementById("dislikebt" + data[i].id).disabled = dislike;
                    }
                });
            });
        });
    });


    $("#clear").click(function () {
        var proceed = confirm("Are you sure?\nThis will remove all samples and effects chosen above.");
        if (proceed == true) {
            var all_inputs = document.getElementsByTagName("input");
            for (var i in all_inputs)
                if (all_inputs[i].type == "checkbox") all_inputs[i].checked = false;

            for (i = 1; i <= counter; i++) {
                document.getElementById("mySelect" + (i)).selectedIndex = 0;
                document.getElementById("myEffect" + (i)).selectedIndex = 0;
            }
        }
    });

    $("#mySampleList").ready(function () {
        //Function to open json file
        $.getJSON("/labi2018-p2-g3//list?type=samples", function (data) {
            $.getJSON("/labi2018-p2-g3/SongVotes?", function (votes) {
                $.get("/labi2018-p2-g3/loguser?", function (logeduser) {
                    console.log(logeduser)

                    //add Samples
                    for (i = 0; i < data.length; i++) {
                        like = false;
                        dislike = false;
                        contLike = 0;
                        contDislike = 0;
                        for (j = 0; j < votes.length; j++) {
                            if (data[i].id == votes[j].SongId) {
                                if (votes[j].Vote == "-1") {
                                    contDislike = contDislike + 1;
                                } else {
                                    contLike = contLike + 1;
                                }
                                if (votes[j].Vote == "-1" && votes[j].User == logeduser) {
                                    dislike = true;
                                    like = false;
                                } else {
                                    dislike = false;
                                    like = true;
                                }
                            }
                        }

                        $("#mySampleList").append("<li><a href=\"\" data-value=\"../audio/" + data[i].name + ".wav\">" + data[i].name + "    </a> <button id=\"likebt" + data[i].id + "\"class=\"btn btn-success btn-sm\" type=\"button\" onclick=\"location.href='/labi2018-p2-g3/vote?id=" + data[i].id + "&user=" + logeduser + "&points=1\';\"><i class=\"fas fa-thumbs-up\"> </i> " + contLike + " </button> <button id=\"dislikebt" + data[i].id + "\"class=\"btn btn-danger btn-sm\" type=\"button\" onclick=\"location.href='/labi2018-p2-g3/vote?id=" + data[i].id + "&user=" + logeduser + "&points=-1\';\"><i class=\"fas fa-thumbs-down\"> </i> " + contDislike + " </button></li>");

                        document.getElementById("likebt" + data[i].id).disabled = like;
                        document.getElementById("dislikebt" + data[i].id).disabled = dislike;
                    }
                });
            });
        });
    });

    $('#inName').on('keypress', function (e) {
        if (e.which == 32)
            return false;
    });

    $("#recButton").click(function () {
        var bpm = 120;
        var samples = [];
        var effects = [];
        var music = [];
        var name = "";

        for (j = 1; j <= 20; j++) {
            var q = 0;
            var col = [];

            for (i = 1; i <= 5; i++) {
                //obtém os valores das colunas
                if (document.getElementById("myCheck" + i + "." + j).checked == true) {
                    col[q] = i - 1;
                    q++;
                }
            }
            music[j - 1] = col;
        }
        var c = 0;
        for (i = 0; i < counter; i++) {
            //obtém os samples e os effects
            var s = document.getElementById("mySelect" + (i + 1));
            var str = s.options[s.selectedIndex].text;
            if (s.options[s.selectedIndex].value != "none") {
                samples[c] = str;
                c++;
                //effects
                var temp = [];
                var e = document.getElementById("myEffect" + (i + 1));
                var str = e.options[e.selectedIndex].value;
                if (str != "none") {
                    temp[i] = str;
                } else {
                    temp[i] = [];
                }
                effects[i] = temp[i];
            }
        }

        var pauta = {"bpm": bpm, "samples": samples, "effects": effects, "music": music};
        var jpauta = JSON.stringify(pauta)
        names = document.getElementById("inName").value;

        if (names != "") {
            $.post("/labi2018-p2-g3/put", {sheet: jpauta, name: names})
                .done(function (data) {
                    alert('The song "' + names + '" has been created.');
                });
        } else {
            alert('You need to enter a name to your song.');
        }

        console.log("JPauta: " + jpauta);
        console.log("Nome: " + names);


    });

    $("#addanother").click(function () {
        console.log("ola");
        counter++;

        jQuery('#SongTable').append('<tr><th><select id="mySelect' + counter + '"><option value="none">Choose audio</option></select></th><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.1"  onclick="playAudio(\'myCheck' + counter + '.1\', \'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.2"  onclick="playAudio(\'myCheck' + counter + '.2\', \'mySelect' + counter + '\')"><label></label></td>		<td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.3"  onclick="playAudio(\'myCheck' + counter + '.3\', \'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.4"  onclick="playAudio(\'myCheck' + counter + '.4\', \'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.5"  onclick="playAudio(\'myCheck' + counter + '.5\', \'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.6"  onclick="playAudio(\'myCheck' + counter + '.6\', \'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.7"  onclick="playAudio(\'myCheck' + counter + '.7\', \'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.8"  onclick="playAudio(\'myCheck' + counter + '.8\', \'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.9"  onclick="playAudio(\'myCheck' + counter + '.9\', \'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.10" onclick="playAudio(\'myCheck' + counter + '.10\',\'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.11" onclick="playAudio(\'myCheck' + counter + '.11\',\'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.12" onclick="playAudio(\'myCheck' + counter + '.12\',\'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.13" onclick="playAudio(\'myCheck' + counter + '.13\',\'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.14" onclick="playAudio(\'myCheck' + counter + '.14\',\'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.15" onclick="playAudio(\'myCheck' + counter + '.15\',\'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.16" onclick="playAudio(\'myCheck' + counter + '.16\',\'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.17" onclick="playAudio(\'myCheck' + counter + '.17\',\'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.18" onclick="playAudio(\'myCheck' + counter + '.18\',\'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.19" onclick="playAudio(\'myCheck' + counter + '.19\',\'mySelect' + counter + '\')"><label></label></td><td class="checkbox_wrapper"><input type="checkbox" id="myCheck' + counter + '.20" onclick="playAudio(\'myCheck' + counter + '.20\',\'mySelect' + counter + '\')"><label></label></td><td><select id="myEffect' + counter + '"><option value="none">Choose effect</option></select></td></tr>');

        $.getJSON("/labi2018-p2-g3/list?type=", function (data) {
            //add Samples

            for (i = 0; i < data.length; i++) {
                $("#mySelect" + counter).append("<option value = /audio/" + data[i].name + ".wav>" + data[i].name + "</option>");
            }

            //add Effects

            $("#myEffect" + counter).append("<option value ='fadein'>Fade In</option>");
            $("#myEffect" + counter).append("<option value ='fadeout'>Fade Out</option>");
            $("#myEffect" + j).append("<option value ='echo'>Echo</option>");

        });
    });

});
	