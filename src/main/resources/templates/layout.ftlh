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
        <header class="site-header">
            <nav class="site-nav" aria-label="Основная навигация">
                <a class="brand" href="/">Microgram</a>

                <div class="nav-links">
                    <a href="/users/search">Поиск</a>

                    <#if currentUser??>
                        <a href="/publications">Лента</a>
                        <a href="/users/${currentUser.id?c}">Мой профиль</a>
                        <a href="/publications/new">Новая публикация</a>
                    </#if>
                </div>

                <div class="nav-account">
                    <#if currentUser??>
                        <a class="nav-user"
                           href="/users/${currentUser.id?c}">
                            <img class="avatar avatar-small"
                                 src="${currentUser.avatar}"
                                 alt="">
                            <span>@${currentUser.login}</span>
                        </a>

                        <form action="/auth/logout" method="post">
                            <@csrf/>

                            <button class="button-secondary button-small"
                                    type="submit">
                                Выйти
                            </button>
                        </form>
                    <#else>
                        <a href="/auth/login">Войти</a>
                        <a class="button button-small"
                           href="/auth/register">
                            Регистрация
                        </a>
                    </#if>
                </div>
            </nav>
        </header>

        <main>
            <div class="page-heading">
                <h1>${title}</h1>
            </div>

            <#nested>
        </main>

        <footer class="site-footer">
            Microgram · Делитесь своими моментами
        </footer>
    </body>
    </html>
</#macro>

<#macro csrf>
    <#if _csrf??>
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}">
    </#if>
</#macro>

<#macro publications items>
    <div class="publication-list">
        <#list items as item>
            <article class="publication-card">
                <header class="post-header">
                    <a class="post-author"
                       href="/users/${item.user.id?c}">
                        <img class="avatar"
                             src="${item.user.avatar}"
                             alt="Аватар ${item.user.login}"
                             loading="lazy">

                        <span class="author-info">
                            <strong>${item.user.name}</strong>
                            <span class="muted">@${item.user.login}</span>
                        </span>
                    </a>

                    <time class="post-date"
                          datetime="${item.createdAt.toString()}">
                        ${item.createdAt.toString()?replace("T", " ")}
                    </time>
                </header>

                <a class="publication-image-link"
                   href="/publications/${item.id?c}">
                    <img class="publication"
                         src="${item.image}"
                         alt="Публикация ${item.user.login}"
                         loading="lazy">
                </a>

                <div class="post-content">
                    <p class="text post-description">${item.description}</p>

                    <a class="button button-secondary"
                       href="/publications/${item.id?c}">
                        Открыть публикацию
                    </a>
                </div>
            </article>
        <#else>
            <div class="empty-state panel">
                <h2>Публикаций пока нет</h2>
                <p class="muted">
                    Здесь будут отображаться опубликованные фотографии.
                </p>
            </div>
        </#list>
    </div>
</#macro>