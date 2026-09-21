<#import "../layout.ftlh" as layout>
<@layout.page title="Вход">
    <#if RequestParameters.error??><p class="error">Неверный логин, email или пароль.</p></#if>
    <#if RequestParameters.registered??><p>Регистрация завершена. Войдите в аккаунт.</p></#if>
    <#if RequestParameters.logout??><p>Вы вышли из аккаунта.</p></#if>
    <form method="post" action="/auth/login">
        <@layout.csrf/>
        <label>Логин или email <input name="username" required autocomplete="username"></label>
        <label>Пароль <input type="password" name="password" required autocomplete="current-password"></label>
        <button type="submit">Войти</button>
    </form>
</@layout.page>
