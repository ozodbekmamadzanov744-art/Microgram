<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#assign isOwner = currentUser??
    && currentUser.id == publication.user.id>

<@layout.page title="Публикация">

    <article class="post-details">
        <header class="post-header">
            <a class="post-author"
               href="/users/${publication.user.id?c}">

                <img class="avatar"
                     src="${publication.user.avatar}"
                     alt="Аватар ${publication.user.login}">

                <span class="author-info">
                    <strong>${publication.user.name}</strong>
                    <span class="muted">@${publication.user.login}</span>
                </span>
            </a>

            <#if isOwner>
                <form method="post"
                      action="/publications/${publication.id?c}/delete"
                      onsubmit="return confirm('Удалить публикацию вместе с лайками и комментариями?');">

                    <@layout.csrf/>

                    <button class="button-danger"
                            type="submit">
                        Удалить публикацию
                    </button>
                </form>
            </#if>
        </header>

        <div class="post-image-container">
            <img class="post-image"
                 src="${publication.image}"
                 alt="Изображение публикации ${publication.user.login}">
        </div>

        <div class="post-content">
            <div class="post-actions">
                <#if currentUser??>
                    <#if liked>
                        <button class="button-liked"
                                type="button"
                                disabled>
                            ♥ Уже понравилось
                        </button>
                    <#else>
                        <form method="post"
                              action="/publications/${publication.id?c}/likes">

                            <@layout.csrf/>

                            <button class="button-like"
                                    type="submit">
                                ♡ Нравится
                            </button>
                        </form>
                    </#if>
                <#else>
                    <a class="button button-secondary"
                       href="/auth/login">
                        Войти, чтобы поставить лайк
                    </a>
                </#if>

                <a class="comments-link" href="#comments">
                    Комментарии: ${commentCount?c}
                </a>
            </div>

            <p class="likes-count">
                Лайков: <strong>${likeCount?c}</strong>
            </p>

            <p class="text post-description">${publication.description}</p>

            <time class="post-date"
                  datetime="${publication.createdAt.toString()}">
                ${publication.createdAt.toString()?replace("T", " ")}
            </time>
        </div>
    </article>

    <section id="comments"
             class="comments-section"
             aria-labelledby="comments-title">

        <div class="section-heading">
            <h2 id="comments-title">Комментарии</h2>
            <span class="count-badge">${commentCount?c}</span>
        </div>

        <#if currentUser??>
            <form class="comment-form"
                  method="post"
                  action="/publications/${publication.id?c}/comments">

                <@layout.csrf/>

                <label for="commentText">
                    Ваш комментарий
                </label>

                <@spring.formTextarea
                    "commentDto.text"
                    'id="commentText" rows="3" maxlength="2000" required placeholder="Напишите комментарий..." aria-describedby="commentError commentHint"'
                />

                <div id="commentError"
                     class="field-error"
                     role="alert">
                    <@spring.showErrors "<br>" "error"/>
                </div>

                <div class="comment-form-footer">
                    <small id="commentHint" class="muted">
                        До 2000 символов
                    </small>

                    <button type="submit">
                        Отправить
                    </button>
                </div>
            </form>
        <#else>
            <div class="guest-notice">
                <p>
                    Чтобы оставить комментарий,
                    <a href="/auth/login">войдите</a>
                    или
                    <a href="/auth/register">зарегистрируйтесь</a>.
                </p>
            </div>
        </#if>

        <div class="comments-list">
            <#list comments as comment>
                <div class="comment-item">
                    <a class="comment-avatar-link"
                       href="/users/${comment.user.id?c}">
                        <img class="avatar"
                             src="${comment.user.avatar}"
                             alt="Аватар ${comment.user.login}"
                             loading="lazy">
                    </a>

                    <div class="comment-body">
                        <div class="comment-header">
                            <a class="comment-author"
                               href="/users/${comment.user.id?c}">
                                ${comment.user.name}
                                <span class="muted">
                                    @${comment.user.login}
                                </span>
                            </a>

                            <#if isOwner>
                                <form method="post"
                                      action="/publications/${publication.id?c}/comments/${comment.id?c}/delete"
                                      onsubmit="return confirm('Удалить этот комментарий?');">

                                    <@layout.csrf/>

                                    <button class="button-delete-comment"
                                            type="submit"
                                            aria-label="Удалить комментарий пользователя ${comment.user.login}">
                                        Удалить
                                    </button>
                                </form>
                            </#if>
                        </div>

                        <p class="text comment-text">${comment.text}</p>

                        <time class="comment-date"
                              datetime="${comment.createdAt.toString()}">
                            ${comment.createdAt.toString()?replace("T", " ")}
                        </time>
                    </div>
                </div>
            <#else>
                <div class="empty-state">
                    <p>Комментариев пока нет.</p>
                    <#if currentUser??>
                        <span class="muted">
                            Оставьте первый комментарий.
                        </span>
                    </#if>
                </div>
            </#list>
        </div>
    </section>

</@layout.page>