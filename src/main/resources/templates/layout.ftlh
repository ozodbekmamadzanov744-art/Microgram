<#macro page title>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title} — Microgram</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<nav>
    <a href="/">Microgram</a>
    <a href="/users/search">Поиск</a>
    <#if currentUser??>
        <a href="/publications">Лента</a>
        <a href="/users/${currentUser.id?c}">Мой профиль</a>
        <a href="/publications/new">Новая публикация</a>
        <form action="/auth/logout" method="post">
            <@csrf/>
            <button type="submit">Выйти</button>
        </form>
    <#else>
        <a href="/auth/login">Войти</a>
        <a href="/auth/register">Регистрация</a>
    </#if>
</nav>
<main>
    <h1>${title}</h1>
    <#nested>
</main>
</body>
</html>
</#macro>

<#macro csrf>
    <#if _csrf??>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </#if>
</#macro>

<#macro publications items>
    <#list items as item>
        <article>
            <p><img class="avatar" src="${item.user.avatar}" alt="Аватар">
                <a href="/users/${item.user.id?c}">${item.user.name} (@${item.user.login})</a></p>
            <a href="/publications/${item.id?c}"><img class="publication" src="${item.image}" alt="Публикация"></a>
            <p class="text">${item.description}</p>
            <small>${item.createdAt.toString()?replace("T", " ")}</small>
        </article>
    <#else>
        <p>Публикаций пока нет.</p>
    </#list>
</#macro>
