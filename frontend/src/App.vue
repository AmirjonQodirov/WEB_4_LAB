<template>
<div id="app">
    <div class="header">
        <div class="header-inner">
            <div>Кадыров Амир, Мансуров Бехруз, Р3210</div>
            <a class="header-logo" href="https://github.com/AmirjonQodirov">
                <img src="@/assets/GitHubIcon.svg" alt="git logo">
            </a>
            <div>Вариант 31721</div>
        </div>
    </div>

    <router-view/>

    <div class="alert"></div>
</div>
</template>

<script>
    export default {
        name: "App"
    }
</script>

<style>
    @import url('https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900&display=swap&subset=cyrillic');

    :focus {outline: none;}

    * {
        margin: 0;
        padding: 0;
    }

    html {
        font-size: 16px;
    }

    body {
        background-image: url("assets/1.jpg");
        font-family: 'Roboto', sans-serif;
        overflow-x: hidden;
    }

    table {
        border-spacing: 0;
        border-collapse: collapse;
        table-layout: fixed;
    }

    h1 {
        margin-bottom: 6px;
        font-size: 1.6rem;
        font-weight: 500;
    }

    a, router-link {
        color: #2c3e50;
        text-decoration: none;
    }

    h2 {
        margin-bottom: 15px;
        font-weight: 500;
        color: #2c3e50;
    }

    .header {
        color: black;
    }

    .header-inner {
        display: flex;
        position: relative;
        flex-direction: row;
        align-items: center;
        max-width: 1264px;
        margin: 0 auto;
        font-size: 21px;
        font-weight: 300;
        padding: 0 15px;
    }

    .header-inner > * {
        width: calc(100% / 3);
    }

    .header-inner > *:first-child {
        text-align: left;
    }

    .header-inner > *:last-child {
        text-align: right;
    }

    .header-logo {
        padding: 5px 0;
    }

    .header-logo img {
        display: block;
        height: 36px;
        margin: 0 auto;
    }

    .wrapper {
        padding-top: 20px;
    }

    .clearfix::after {
        content: "";
        display: table;
        clear: both;
    }

    .green {
        color: black;
    }

    .red {
        color: white;
    }
</style>
