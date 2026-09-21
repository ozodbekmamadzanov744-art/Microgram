<#import "../layout.ftlh" as layout>
<@layout.page title="Публикация">
    <@layout.publications items=[publication]/>
    <p>Лайков: ${likeCount} · Комментариев: ${commentCount}</p>
    <#if currentUser?? && currentUser.id == publication.user.id>
        <form method="post" action="/publications/${publication.id?c}/delete">
            <@layout.csrf/><button type="submit">Удалить публикацию</button>
        </form>
    </#if>
</@layout.page>
