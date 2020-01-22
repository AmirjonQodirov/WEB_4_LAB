<template>
    <div class="wrapper" id="wrapper">
        <div class="content">
            <div class="links">
                <h2>Укажите параметры X, Y и R</h2>

            </div>
            <div class="content-wrapper">

                <div class="selection">
                    <form v-on:submit.prevent="addPoint">
                        <div class="form-select x-select">
                            <label class="selection-label">Выберите X
                                <span class="warning" data-validate="Выберите X"></span>
                            </label>
                            <div class="select-buttons-for-x">
                                <button v-for="num in buttonsforX" v-bind:key="num" class="changeX" type="button">{{num}}</button>
                            </div>
                        </div>
                        <div>
                            <br/>
                        </div>
                        <div class="form-select y-select">
                            <label class="selection-label" for="Y-select">Выберите Y
                                <span class="warning" data-validate="Y - значение в диапазоне [-3; 3]"></span>
                            </label>
                            <input id="Y-select" type="text" placeholder="-3...3" name="y" autocomplete="off">
                        </div>
                        <div>
                            <br/>
                        </div>
                        <div class="form-select r-select">
                            <label class="selection-label">Выберите R
                                <span class="warning" data-validate="Выберите R"></span>
                            </label>
                            <div class="select-buttons">
                                <button v-for="num in buttons" v-bind:class="{'btn-hold': (num === 1)}" v-bind:key="num" class="changeR" type="button">{{num}}</button>
                            </div>
                        </div>
                        <div>
                            <br/>

                        </div>
                        <div class="form-buttons">
                            <input class="form-btn btn-submit" type="submit" value="Отправить">
                        </div>
                        <div><br/></div>
                        <div>
                            <a class="exit" href="/" v-on:click.prevent="logoutRequest">Выйти</a>
                        </div>
                            <br>
                        <div>
                            <div v-bind:class="{ active: pointtrue, 'non-active': !pointtrue }">
                                <img src ="../assets/11.jpg" width="200" height="200">
                            </div>

                            <div v-bind:class="{ active: pointfalse, 'non-active': !pointfalse }">
                                <img src ="../assets/12.jpg" width="200" height="200">
                            </div>

                            <div v-bind:class="{ active: ptimeout, 'non-active': !ptimeout }">
                                <img src ="../assets/13.jpg" width="200" height="200">
                            </div>
                        </div>
                    </form>
                    <br/>
                </div>
                <div>
                <div v-bind:class="{ active: videoPlay, 'non-active': !videoPlay }">
                    <video width="300" height="200"  >
                        <source src="../assets/video.mp4" type="video/mp4">
                    </video>
                </div>

                <div  v-bind:class="{ active: stoprek, 'non-active': !stoprek }">
                    <h3>Хотите пропустить рекламу?</h3>
                    <a class="exit" href="/" v-on:click.prevent="checkLK">Нажмите..</a>
                </div >
                <div v-bind:class="{ active: !stoprek, 'non-active': stoprek }">
                <div v-on:click="addPointFromCanvas" class="graph">
                    <canvas id="plot" width="300" height="300"></canvas>
                </div>
                </div>
                </div>
                <table class="results">
                    <tbody v-if="listOfPoints.length">
                    <tr>
                        <th>X</th>
                        <th>Y</th>
                        <th>R</th>
                        <th>Попадание</th>
                    </tr>
                    <tr v-for="cPoint in listOfPoints">
                        <td>{{cPoint.x}}</td>
                        <td>{{cPoint.y}}</td>
                        <td>{{cPoint.r}}</td>
                        <td v-if="cPoint.result" class="green">Входит</td>
                        <td v-else class="red">Не входит</td>
                    </tr>
                    </tbody>
                    <tbody v-else>
                    <tr>
                        <th>X</th>
                        <th>Y</th>
                        <th>R</th>
                        <th>Попадание</th>
                    </tr>
                    <tr>
                        <td colspan="4">Нет данных</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>
<script>
    import $ from 'jquery';

    export default {
        name: "Main",
        data() {
            return {

                LKAmir: null,
                LKBeha: null,
                response: null,
                listOfPoints: [],
                buttons: [0, 1, 2, 3, 4],
                buttonsforX: [-4, -3, -2, -1, 0, 1, 2, 3, 4],
                pointtrue: false,
                pointfalse: false,
                ptimeout: false,
                change: 0,
                videoPlay: false,
                stoprek: false,
            }
        },
        methods: {
            // IfLKChange: function(){
            //     if(this.change>5) {
            //         this.getLKAmir();
            //         this.getLKBeha();
            //
            //         if (this.LKAmir === 2 && this.LKBeha === 2) {
            //         } else {
            //             this.videoPlay = !this.videoPlay;
            //             this.stoprek = !this.stoprek;
            //             this.playV();
            //         }
            //     }
            // },
            playV: function(){
                let videoEl = document.getElementsByTagName('video')[0];
                videoEl.play();
                videoEl.volume = 50;

            },
            stopV: function(){
                let videoEl = document.getElementsByTagName('video')[0];
                videoEl.pause();
                videoEl.currentTime = 0;
            },
            checkLK:function() {
                this.getLKAmir();
                this.getLKBeha();
                setTimeout(() => this.timeCheckLk(),
                    5000);
            },
            timeCheckLk:function() {
                if(this.LKAmir === 2 && this.LKBeha === 2) {
                    this.stopV();
                    this.videoPlay = !this.videoPlay;
                    this.stoprek = !this.stoprek;
                }
                else if(this.LKAmir === "" && this.LKBeha === "") {
                    alert("Пожайлуста поставте Лк Кадырову и Мансурову 2 балла");
                }
                else if(this.LKAmir === "") {
                    alert("Пожайлуста поставте Лк Кадырову 2 балла");
                }
                else if(this.LKBeha === ""){
                    alert("Пожайлуста поставте Лк Мансурова 2 балла");
                }
                else {
                    alert("Пожайлуста поставте за Лк 2 балла сейчас за лк Кадырова стоит: <" + this.LKAmir + "> балла а за лк Мансурова: <" +this.LKBeha+">");
                }
            },
            getLKAmir: function(){
                let base64Credential = localStorage.getItem('currentUser');
                this.$axios({
                    method: 'get',
                    url: this.$BaseURL + "lkAmir",
                    headers: {'Content_type': 'application/json', 'Authorization': base64Credential}
                }).then(response => {
                     this.LKAmir = response.data;

                });
            },
            getLKBeha: function(){
                let base64Credential = localStorage.getItem('currentUser');
                this.$axios({
                    method: 'get',
                    url: this.$BaseURL + "lkBeha",
                    headers: {'Content_type': 'application/json', 'Authorization': base64Credential}
                }).then(response => {
                    this.LKBeha = response.data;
                });

            },
            logoutRequest: function () {
                let base64Credential = localStorage.getItem('currentUser');
                this.$axios({
                    method: 'post',
                    url: this.$BaseURL + 'login',
                    headers: {'Content_type': 'application/json', 'Authorization': base64Credential}
                }).then(response => {
                    this.response = response;
                    localStorage.removeItem('currentUser');
                    this.$router.push('/');
                    return true;
                }).catch(error => {
                    console.log(error.response.data);
                    return false;
                });
            },
            getPointsRequest: function () {
                let base64Credential = localStorage.getItem('currentUser');
                console.log(base64Credential);
                this.$axios({
                    method: 'get',
                    url: this.$BaseURL + 'points',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                        'Authorization': base64Credential
                    }
                }).then(response => {
                    console.log(response.data);
                    if(response.data.length){
                        this.listOfPoints = response.data;
                    }
                    return true;
                }).catch(error => {
                    console.log(error.response.data);
                    return false;
                });
            },
            addPointRequest: function (x, y, r) {
                let base64Credential = localStorage.getItem('currentUser');
                this.$axios({
                    method: 'post',
                    url: this.$BaseURL + 'points',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                        'Authorization': base64Credential
                    },
                    data: {x: x, y: y, r: r}
                }).then(response => this.response = response);
            },
            addPointFromCanvas: function (e) {
                this.change += 1;
                let r = document.querySelector('.r-select .btn-hold').innerText;
                const point = this.getCursorPosition(e);
                const plot_canvas = document.getElementById("plot");
                const context = plot_canvas.getContext("2d");
                const definedPoint = this.drawDefinedPoint(context, point.x, point.y, r);
                const x = definedPoint.x;
                const y = definedPoint.y;
                this.addPointRequest(x, y, r);
                if(this.checkArea(Number(x), Number(y), Number(r))) {
                    this.listOfPoints.push({x: x, y: y, r: r, result:true });
                    this.pointtrue = true;
                    this.pointfalse = false;
                    this.ptimeout = false
                }
                else{
                    this.listOfPoints.push({x: x, y: y, r: r, result:false });
                    this.pointtrue = false;
                    this.pointfalse = true;
                    this.ptimeout = false;
                }
                if(this.change === 20) {
                    this.pointtrue = false;
                    this.pointfalse = false;
                    this.ptimeout = true;
                    this.change = 5;
                }
                if(this.change===3){
                    this.stoprek = !this.stoprek;
                    this.videoPlay = !this.videoPlay;
                    this.playV();
                }
                this.IfLKChange();
            },
            addPoint: function () {
                this.change += 1;
                let xSelect = document.querySelector('.x-select .btn-hold');
                let x = xSelect ? xSelect.innerText : null;
                let y = document.getElementById('Y-select').value.trim();
                let rSelect = document.querySelector('.r-select .btn-hold');
                let r = rSelect ? rSelect.innerText : null;

                let validX = !!x;
                let validY = false;
                let validR = !!r;
                if (!validX) {$('.x-select .warning').addClass('icon-visible')}
                if (!validR) {$('.r-select .warning').addClass('icon-visible')}

                if (y.match(/^-?[0-9]*[.,]?[0-9]+$/) && y && y !== '-') {
                    y = y.replace(',', '.');
                    if (y >= -3 && y <= 3) {
                        validY = true;
                    }
                }
                if (!validY) {
                    $('.y-select .warning').addClass('icon-visible');
                }

                if (validX && validY && validR) {
                    console.log('all is correct (X Y R)');
                    this.listOfPoints.push({x: x, y: y, r: r, result: false});
                    this.addPointRequest(x, y, r);

                    //draw this point
                    const plot_canvas = document.querySelector("#plot");
                    const context = plot_canvas.getContext("2d");
                    context.beginPath();

                    if (this.checkArea(Number(x), Number(y), Number(r))) {
                        this.pointtrue = true;
                        this.pointfalse = false;
                        this.ptimeout = false;
                        this.listOfPoints[this.listOfPoints.length - 1].result = true;

                        x = Number(x * 28 + 150);
                        y = Number(150 - 28 * y);
                        context.arc(x, y, 2, 0, 2 * Math.PI);
                        context.fillStyle = '#28ff08';
                    } else {
                        this.pointtrue = false;
                        this.pointfalse = true;
                        this.ptimeout = false;
                        this.listOfPoints[this.listOfPoints.length - 1].result = false;

                        x = Number(x * 28 + 150);
                        y = Number(150 - 28 * y);
                        context.arc(x, y, 2, 0, 2 * Math.PI);
                        context.fillStyle = '#dd040d';
                    }
                    context.fill();
                }
                if(this.change === 20) {
                    this.pointtrue = false;
                    this.pointfalse = false;
                    this.ptimeout = true;
                    this.change = 0;
                }
            },
            drawDefinedPoint: function (context, xFromList, yFromList, r) {
                let point = {x:Number(xFromList), y:Number(yFromList)};
                context.beginPath();
                context.arc(point.x, point.y, 2, 0, 2 * Math.PI);
                const x = ((point.x - 150) / 28).toFixed(2);
                const y = ((-point.y + 150) / 28).toFixed(2);
                if (this.checkArea(Number(x), Number(y), Number(r))) {
                    context.fillStyle = '#28ff08';
                } else {
                    context.fillStyle = '#dd040d';
                }
                context.fill();
                return {x: x, y: y};
            },
            getCursorPosition: function (e) {
                let x;
                let y;
                const plot_canvas = document.getElementById("plot");
                if (e.pageX !== undefined && e.pageY !== undefined) {
                    x = e.pageX;
                    y = e.pageY;
                } else {
                    x = e.clientX + document.body.scrollLeft +
                        document.documentElement.scrollLeft;
                    y = e.clientY + document.body.scrollTop +
                        document.documentElement.scrollTop;
                }
                return {
                    x: x - plot_canvas.getBoundingClientRect().left,
                    y: y - plot_canvas.getBoundingClientRect().top
                }
            },
            checkArea: function (x, y, r) {
                return ((x >= 0) && (y <= 0) && (y >= (2 * x - r))
                    || (x <= 0) && (y >= 0) && ((x * x + y * y) <= r * r)
                    || (x >= 0) && (y >= 0) && (x <= r) && (y <= r / 2));
            },
            drawGraph: function () {
                const plot_canvas = document.querySelector("#plot");
                const context = plot_canvas.getContext("2d");
                let canvLength = plot_canvas.width;

                this.drawArea(context, canvLength);

                context.beginPath();
                //Ox
                context.moveTo(10, canvLength / 2);
                context.lineTo(canvLength - 10, canvLength / 2);
                context.lineTo(canvLength - 20, canvLength / 2 - 7);
                context.moveTo(canvLength - 10, canvLength / 2);
                context.lineTo(canvLength - 20, canvLength / 2 + 7);
                //Oy
                context.moveTo(canvLength / 2, canvLength - 10);
                context.lineTo(canvLength / 2, 10);
                context.lineTo(canvLength / 2 - 7, 20);
                context.moveTo(canvLength / 2, 10);
                context.lineTo(canvLength / 2 + 7, 20);
                //Dashes
                for (let i = 10 + 28; i < canvLength * 291 / 300 - 28; i += 28) {
                    context.moveTo(canvLength / 2, canvLength * (i) / 300);
                    context.lineTo(canvLength * 146 / 300, canvLength * (i) / 300);
                    context.lineTo(canvLength * 154 / 300, canvLength * (i) / 300);
                    context.moveTo(canvLength * (i) / 300, canvLength / 2);
                    context.lineTo(canvLength * (i) / 300, canvLength * 146 / 300);
                    context.lineTo(canvLength * (i) / 300, canvLength * 154 / 300);
                }
                context.closePath();
                context.stroke();

                //Names of dashes
                context.fillStyle = '#000000';
                context.font = '14px Arial';
                context.fillText("X", canvLength - 20, canvLength / 2 - 13);
                context.fillText("Y", canvLength / 2 - 20, 20);

                //Already drawn points
                let r = document.querySelector('.r-select .btn-hold').innerText;
                for (let point of this.listOfPoints) {
                    const x = Number(Number(point.x) * 28 + 150);
                    const y = Number(150 - 28 * Number(point.y));
                    this.drawDefinedPoint(context, x, y, r);
                }
            },
            drawArea: function (context, canvLength) {
                let r = document.querySelector('.r-select .btn-hold').innerText;
                context.beginPath();
                context.arc(canvLength/2, canvLength/2, canvLength*28*(r)/300, Math.PI, 3*Math.PI/2);
                context.lineTo(canvLength / 2, canvLength / 2);
                context.closePath();
                context.rect(canvLength/2 ,canvLength/2 - canvLength*28*(r/2)/300,canvLength*28*(r)/300, canvLength*28*(r/2)/300);
                context.fillStyle = '#ff7922';
                context.fill();
                context.beginPath();
                context.moveTo(canvLength/2, canvLength/2);
                context.lineTo(canvLength/2, canvLength/2 + canvLength*28*r/300);
                context.lineTo(canvLength/2+canvLength*28*(r/2)/300, canvLength/2);
                context.lineTo(canvLength/2, canvLength/2);
                context.closePath();
                context.fillStyle = '#ff7922';
                context.fill();
            },
            reDrawGraph: function () {
                const plot_canvas = document.getElementById("plot");
                const context = plot_canvas.getContext('2d');
                context.clearRect(0, 0, plot_canvas.width, plot_canvas.height);
                this.drawGraph();
            }
        },
        mounted() {
            const vm = this;
            $('.changeR').on('click', function () {
                $(this).siblings().removeClass('btn-hold');
                $(this).addClass('btn-hold');
                $(this).parent().parent().find('.warning').removeClass('icon-visible');
                vm.reDrawGraph();
            });
            $('.changeX').on('click', function () {
                $(this).siblings().removeClass('btn-hold');
                $(this).addClass('btn-hold');
                $(this).parent().parent().find('.warning').removeClass('icon-visible');
            });
            this.drawGraph();
            let response = this.getPointsRequest();
            if(response){
                this.listOfPoints = response;
            }
        },
    }

    $(document).ready(function () {
        $('#Y-select').on('focus', function () {
            $('.y-select .warning').removeClass('icon-visible');
        });
    });
</script>
<style scoped>
    .content {
        max-width: 1264px;
        margin: 0 auto;
        padding: 0 15px;
    }

    .content-wrapper {
        display: flex;
        flex-wrap: wrap;
        align-items: flex-start;
    }

    .content-wrapper > * {
        margin-bottom: 15px;
    }

    .links {
        display: flex;

        justify-content: space-between;
    }

    .alert {
        display: none;
        padding: 10px 18px;
        position: fixed;
        right: 10px;
        bottom: 0;
        background-color: #d13000;
        color: #fff;
        border-top-left-radius: 4px;
        border-top-right-radius: 4px;
        font-size: 0.9rem;
    }

    /* ====== Graph ====== */

    .graph {
        height: 300px;
        width: 300px;
        margin-right: 20px;
        border-radius: 2px;
    }
    h2 {
        color: black;
    }

    #plot {
        cursor:  pointer;
    }

    /* ====== Form ====== */

    .selection {
        height: 280px;
        width: 300px;
        margin-right: 20px;
        border-radius: 5px;
        padding: 10px;
    }

    .form-select {
        margin-bottom: 10px;
    }

    .selection-label {
        display: block;
        margin-bottom: 4px;
        position: relative;
        color: black;
    }

    .selection-label:hover .icon-visible::after {
        visibility: visible;
        opacity: 1;
    }

    .select-buttons {
        display: flex;
        justify-content: space-between;
    }

    .select-buttons > button {
        display: inline-block;
        width: 1.5rem;
        margin-right: 3px;
        padding: 3px 25px;
        background-color: #fff;
        border: 1px solid #d2141b;
        border-radius: 4px;
        text-align: center;
        outline: none;
        cursor: pointer;
    }

    .select-buttons > button:last-child {
        margin-right: 0;
    }

    .select-buttons > button:hover {
        background-color: #eee;

    }

    .select-buttons-for-x {
        display: flex;


        justify-content: space-between;
    }

    .select-buttons-for-x > button {
        display: inline-block;
        width: 1.5rem;
        margin-right: 3px;
        padding: 3px ;
        background-color: #fff;
        border: 1px solid #d2141b;
        border-radius: 4px;
        text-align: center;
        outline: none;
        cursor: pointer;
    }

    .select-buttons-for-x > button:last-child {
        margin-right: 0;
    }

    .select-buttons-for-x > button:hover {
        background-color: #eee;
    }

    .btn-hold {
        background-color: #ff9613 !important;
        /*background-color: #2c3e50;*/
        color: #fff;

    }

    #Y-select {
        display: block;
        width: 2rem;
        padding: 3px 5px 2px 5px;
        border: solid 1px #f18318;
        border-radius: 4px;
        outline: 0;
    }

    #Y-select:focus {
        border-color: #f12634;
    }

    .form-btn {
        width: 100%;
        padding: 10px 22px;
        border: 0;
        border-radius: 4px;
        /*color: #fff;*/
        font-size: 0.9rem;
        cursor: pointer;
        outline: 0;
    }
    .btn-submit:active {
        position: relative;
        top: 3px;
        box-shadow: #a55613;
        background: #a55613;
    }

    .btn-submit:hover {
        background-color: #a55613;
        margin-right: 5px;
    }

    .btn-submit {
        background-color: #ce6d16;
        margin-right: 5px;
    }

    .icon-visible {
        visibility: visible !important;
        opacity: 1;
    }

    .warning {
        content: attr(data-validate);
        display: inline-block;
        visibility: hidden;
        position: relative;
        background-size: 15px;
        width: 15px;
        height: 15px;
        background-image: url("../assets/warning.svg");
        vertical-align: top;
        margin: 2px 0 0 6px;
        transition: opacity 0.5s ease;
        transform-style: preserve-3d;
    }

    .warning::after {
        content: attr(data-validate);
        position: absolute;
        visibility: hidden;
        opacity: 0;
        width: max-content;
        left: -5px;
        top: 50%;
        padding: 4px 4px 3px 23px;
        font-size: 0.8rem;
        font-weight: 500;
        background-color: #d6d7ff;
        color: #ff7922;
        border: 1px solid #000000;
        border-radius: 3px;
        transform: translateY(-50%) translateZ(-1px);
        transition: opacity .2s;
    }

    /* ====== Table ====== */

    .results {
        flex-grow: 1;
    }

    .results tr:nth-child(odd) {
        background-color: #f18318;
    }
    .results tr:nth-child(even) {
        background-color: #ff9613;
    }

    .results th {
        background-color: #000000;
        color: white;
        font-weight: 500;
    }

    .results td, .results th{
        width: 20%;
        padding: 5px 4px;
        text-align: center;
    }

    a.exit{
        height: 10px;

    }
    a.exit {
        font-family: Arial, Helvetica, sans-serif;
        font-size: 11px;
        color: #000;
        padding: 10px 20px;
        display: inline-block;
        text-decoration: none;
        border-radius: 2px;
        box-shadow: 0px 0px #ce6d16;
        background: #ce6d16;
    }
    a.exit:hover {
        background: #a55613;
    }
    a.exit:active {
        position: relative;
        top: 3px;
        box-shadow: 0px 0px #a55613;
        background: #a55613;
    }
    .non-active {
        display: none;
    }

</style>