<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<@layout.page title="Регистрация">
    <@spring.bind "registerDto"/>
    <#list spring.status.errorMessages as error><p class="error">${error}</p></#list>
    <form method="post" action="/auth/register">
        <@layout.csrf/>
        <label>Логин <input name="login" value="${registerDto.login!}" maxlength="30" required></label>
        <label>Email <input type="email" name="email" value="${registerDto.email!}" maxlength="255" required></label>
        <label>Имя <input name="name" value="${registerDto.name!}" maxlength="100" required></label>
        <label>Пароль <input type="password" name="password" minlength="6" maxlength="50" required autocomplete="new-password"></label>
        <label>О себе <textarea name="information" maxlength="1000">${registerDto.information!}</textarea></label>
        <button type="submit">Зарегистрироваться</button>
    </form>
</@layout.page>
