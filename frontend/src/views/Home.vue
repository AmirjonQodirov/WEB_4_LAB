<template>
    <div class="wrapper">
        <form v-on:submit.prevent="checkForm">
            <input v-model="action" checked="" id="signin" name="action" type="radio" value="login">
            <label for="signin">Вход</label>
            <input v-model="action" id="signup" name="action" type="radio" value="register">
            <label for="signup">Регистрация</label>
            <div id="wrapper">
                <div id="arrow"></div>
                <input v-model="name" minlength="3" id="email"  placeholder="Имя пользователя" type="text">
                <input v-model="password" minlength="3" id="pass" placeholder="Пароль" type="password">
                <input v-model="repassword" minlength="3" id="repass" placeholder="Повторите пароль" type="password">
            </div>
            <button type="submit">
                <span>
                <br>
                Вход
                <br>
                Создать аккаунт
                </span>
            </button>
            <div class="login-error" v-if="errors.length">
                <p v-for="error in errors" v-bind:key="error" class="green">{{error}}</p>
            </div>
            <div class="login-error" v-else>
                <p class="orange">{{success}}</p>
            </div>
        </form>

    </div>
</template>

<script>
    export default {
        name: "Home",
        data() {
            return {
                response: null,
                action: 'login',
                errors: [],
                success: '',
                name: null,
                password: null,
                repassword: null
            }
        },
        methods: {
            checkForm: function () {
                this.errors = [];

                if (!this.name) {
                    this.errors.push('Введите имя пользователя');
                }
                if (!this.name.match(/^[A-Za-z0-9]*$/)) {
                    this.errors.push('Имя пользователя должен быть только из латинский символов или цифр');
                }
                if (!this.password) {
                    this.errors.push('Введите пароль');
                }
                if (!this.password.match(/^[A-Za-z0-9]*$/)) {
                    this.errors.push('Пароль должен быть только из латинский символов или цифр');
                }
                if (this.action === 'register') {
                    if (!this.repassword) {
                        this.errors.push('Введите пароль еще раз');
                    }
                    else {
                        if (this.password !== this.repassword) {
                            this.errors.push('Пароли не совпадают');
                        }
                    }
                }
                if (!this.errors.length) {
                    if (this.action === 'register') {
                        if (this.registerRequest()) {
                            this.loginRequest();
                        }
                    }
                    else if (this.action === 'login') {
                        this.loginRequest();
                    }
                }
            },
            loginRequest: function () {
                let base64Credential = 'Basic ' + btoa(this.name + ':' + this.password);
                this.$axios({
                    method: 'post',
                    url: this.$BaseURL + this.action,
                    headers: {'Content_type': 'application/json', 'Authorization':  base64Credential}
                }).then(response => {
                    this.response = response;
                    localStorage.setItem('currentUser', base64Credential);
                    this.$router.push('/main');
                    return true;
                }).catch(error => {
                    error.response.status === 401 ? this.errors.push('Неверный логин или пароль') : this.errors.push('Ошибка авторизации');
                    return false;
                });
            },
                registerRequest: function () {
                this.$axios({
                    method: 'post',
                    url: this.$BaseURL + this.action,
                    headers: {'Content_type': 'application/json'},
                    data: {
                        username: this.name,
                        password: this.password
                    }
                }).then(() => {
                    this.success = 'Успешно!';
                    this.action = "login";
                    this.loginRequest();
                    return true;
                }).catch(error => {
                    error.response.status === 409 ? this.errors.push('Имя пользователя занято') : this.errors.push('Ошибка регистрации');
                    return false;
                });
            },

        }
    }
</script>

<style scoped>
    #wrapper, label, #arrow, button span {
        transition: height, transform .5s, .5s ease, -webkit-transform .5s;
    }


    #wrapper {
        overflow: hidden;
    }

    #signin:checked ~ #wrapper {
        height: 126px;
    }

    #signin:checked ~ #wrapper #arrow {
        left: 10px;
    }

    #signin:checked ~ button span {
        transform: translate3d(0, -48px, 0);
    }

    #signup:checked ~ #wrapper {
        height: 184px;
    }

    #signup:checked ~ #wrapper #arrow {
        left: 108px;
    }

    #signup:checked ~ button span {
        transform: translate3d(0, -96px, 0);
    }

    #reset:checked ~ #wrapper {
        height: 94px;
    }

    #reset:checked ~ #wrapper #arrow {
        left: 404px;
    }

    #reset:checked ~ button span {
        transform: translate3d(0, 0, 0);
    }

    form {
        width: 450px;
        margin: 150px auto 0 auto;
    }

    input[type=radio] {
        display: none;
    }

    label {
        cursor: pointer;
        display: inline-block;
        font-size: 16px;
        font-weight: 800;
        opacity: .5;
        margin-bottom: 15px;
        text-transform: uppercase;
    }

    label:hover {
        transition: all .3s cubic-bezier(.6, 0, .4, 1);
        opacity: 1;
    }

    label[for="signin"] {
        margin-right: 20px;
    }

    input[type=radio]:checked + label {
        opacity: 1;
    }

    input[type=text],
    input[type=password] {
        background: #ffc634;
        border: none;
        border-radius: 8px;
        font-size: 16px;
        height: 48px;
        width: 99.5%;
        margin-bottom: 10px;
        opacity: 1;
        text-indent: 20px;
        transition: all .2s ease-in-out;
    }

    button {
        background-color: hsl(0, 0%, 0%);
        border: none;
        border-radius: 8px;
        color: #fff;
        cursor: pointer;
        font-size: 18px;
        height: 48px;
        width: 100%;
        margin-bottom: 10px;
        overflow: hidden;
        transition: all .3s cubic-bezier(.6, 0, .4, 1);
    }
    button:active{
        position: relative;
        top: 3px;
    }

    button span {
        display: block;
        line-height: 48px;
        position: relative;
        top: 0;
        transform: translate3d(0, 0, 0);
    }


    #arrow {
        height: 0;
        width: 0;
        border-bottom: 10px solid #000000;
        border-left: 10px solid transparent;
        border-right: 10px solid transparent;
        position: relative;
        left: 32px;
    }
    .orange{
        color: #ce6d16;
    }
</style>