<#import "../layout.ftlh" as layout>
<@layout.page title="Лента подписок">
    <p>Здесь появляются публикации пользователей, на которых вы подписаны.</p>
    <@layout.publications items=publications/>
</@layout.page>
