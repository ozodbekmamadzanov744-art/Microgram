<#import "../layout.ftlh" as layout>

<@layout.page title="Лента подписок">

    <div class="page-intro">
        <p class="muted">
            Новые публикации пользователей, на которых вы подписаны.
        </p>

        <a class="button button-secondary"
           href="/users/search">
            Найти пользователей
        </a>
    </div>

    <#if publications?has_content>
        <@layout.publications items=publications/>
    <#else>
        <section class="empty-state panel">
            <div class="empty-symbol" aria-hidden="true">◎</div>

            <h2>В ленте пока тихо</h2>

            <p class="muted">
                Найдите интересных пользователей и подпишитесь на них.
                Их публикации появятся здесь — сначала самые новые.
            </p>

            <a class="button" href="/users/search">
                Перейти к поиску
            </a>
        </section>
    </#if>

</@layout.page>