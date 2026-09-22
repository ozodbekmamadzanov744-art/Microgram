<#import "../layout.ftlh" as layout>

<@layout.page title="Поиск пользователей">

    <section class="panel search-panel">
        <form class="search-form"
              method="get"
              action="/users/search">

            <div class="search-field">
                <label for="searchParam">
                    Имя, логин или email
                </label>

                <input id="searchParam"
                       name="param"
                       value="${param}"
                       placeholder="Например, azamat"
                       required>
            </div>

            <button type="submit">Найти</button>
        </form>
    </section>

    <#if param?trim?has_content>
        <div class="section-heading">
            <h2>Результаты поиска</h2>
            <span class="count-badge">${users?size?c}</span>
        </div>

        <div class="user-list">
            <#list users as user>
                <article class="user-card">
                    <a class="user-card-main"
                       href="/users/${user.id?c}">
                        <img class="avatar"
                             src="${user.avatar}"
                             alt="Аватар ${user.login}"
                             loading="lazy">

                        <span class="author-info">
                            <strong>${user.name}</strong>
                            <span class="muted">@${user.login}</span>
                        </span>
                    </a>

                    <a class="button button-secondary button-small"
                       href="/users/${user.id?c}">
                        Открыть профиль
                    </a>
                </article>
            <#else>
                <div class="empty-state panel">
                    <h2>Никого не нашли</h2>
                    <p class="muted">
                        Попробуйте другое имя, логин или email.
                    </p>
                </div>
            </#list>
        </div>
    <#else>
        <div class="empty-state">
            <h2>Найдите знакомых</h2>
            <p class="muted">
                Введите имя, логин или email в строку поиска.
            </p>
        </div>
    </#if>

</@layout.page>