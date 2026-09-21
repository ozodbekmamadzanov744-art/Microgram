<#import "../layout.ftlh" as layout>
<@layout.page title="Профиль пользователя">
    <img class="avatar" src="${profile.avatar}" alt="Аватар">
    <h2>${profile.name} (@${profile.login})</h2>
    <p class="text">${profile.information!}</p>
    <p>Публикаций: ${publicationCount} · Подписок: ${subscriptionCount} · Подписчиков: ${subscriberCount}</p>
    <#if currentUser?? && currentUser.id != profile.id>
        <#if subscribed>
            <form method="post" action="/users/${profile.id?c}/unsubscribe">
                <@layout.csrf/><button type="submit">Отписаться</button>
            </form>
        <#else>
            <form method="post" action="/users/${profile.id?c}/subscribe">
                <@layout.csrf/><button type="submit">Подписаться</button>
            </form>
        </#if>
    </#if>
    <@layout.publications items=publications/>
</@layout.page>
