<#import "../layout.ftlh" as layout>

<@layout.page title="Вход">

    <section class="panel form-card">
        <div class="form-heading">
            <span class="form-brand">Microgram</span>
            <p class="muted">
                Войдите, чтобы делиться фотографиями и общаться.
            </p>
        </div>

        <#if RequestParameters.error??>
            <div class="alert alert-error" role="alert">
                Неверный логин, email или пароль.
            </div>
        </#if>

        <#if RequestParameters.registered??>
            <div class="alert alert-success" role="status">
                Регистрация завершена. Теперь войдите в аккаунт.
            </div>
        </#if>

        <#if RequestParameters.logout??>
            <div class="alert alert-success" role="status">
                Вы вышли из аккаунта.
            </div>
        </#if>

        <form method="post" action="/auth/login">
            <@layout.csrf/>

            <div class="form-group">
                <label for="username">Логин или email</label>
                <input id="username"
                       name="username"
                       autocomplete="username"
                       placeholder="Ваш логин или email"
                       required>
            </div>

            <div class="form-group">
                <label for="password">Пароль</label>
                <input id="password"
                       type="password"
                       name="password"
                       autocomplete="current-password"
                       required>
            </div>

            <button class="button-block" type="submit">
                Войти
            </button>
        </form>

        <p class="form-bottom">
            Нет аккаунта?
            <a href="/auth/register">Зарегистрироваться</a>
        </p>
    </section>

</@layout.page>