<#import "../layout.ftlh" as layout>
<@layout.page title="Поиск пользователей">
    <form method="get" action="/users/search">
        <label>Имя, логин или email <input name="param" value="${param}" required></label>
        <button type="submit">Найти</button>
    </form>
    <#list users as user>
        <p><img class="avatar" src="${user.avatar}" alt="Аватар">
            <a href="/users/${user.id?c}">${user.name} (@${user.login})</a></p>
    <#else>
        <#if param?has_content><p>Пользователи не найдены.</p></#if>
    </#list>
</@layout.page>
