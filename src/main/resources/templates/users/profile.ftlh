<#import "../layout.ftlh" as layout>

<#assign ownProfile = currentUser?? && currentUser.id == profile.id>

<@layout.page title="Профиль пользователя">

    <section class="panel profile-panel">
        <div class="profile-top">
            <img class="profile-avatar"
                 src="${profile.avatar}"
                 alt="Аватар ${profile.login}">

            <div class="profile-info">
                <div class="profile-title-row">
                    <div>
                        <h2 class="profile-name">${profile.name}</h2>
                        <p class="profile-login">@${profile.login}</p>
                    </div>

                    <div class="profile-actions">
                        <#if ownProfile>
                            <a class="button"
                               href="/publications/new">
                                Новая публикация
                            </a>
                        <#elseif currentUser??>
                            <#if subscribed>
                                <form method="post"
                                      action="/users/${profile.id?c}/unsubscribe">
                                    <@layout.csrf/>

                                    <button class="button-secondary"
                                            type="submit">
                                        Отписаться
                                    </button>
                                </form>
                            <#else>
                                <form method="post"
                                      action="/users/${profile.id?c}/subscribe">
                                    <@layout.csrf/>

                                    <button type="submit">
                                        Подписаться
                                    </button>
                                </form>
                            </#if>
                        <#else>
                            <a class="button button-secondary"
                               href="/auth/login">
                                Войти, чтобы подписаться
                            </a>
                        </#if>
                    </div>
                </div>

                <dl class="profile-stats">
                    <div>
                        <dt>Публикации</dt>
                        <dd>${publicationCount?c}</dd>
                    </div>

                    <div>
                        <dt>Подписки</dt>
                        <dd>${subscriptionCount?c}</dd>
                    </div>

                    <div>
                        <dt>Подписчики</dt>
                        <dd>${subscriberCount?c}</dd>
                    </div>
                </dl>

                <#if profile.information?? && profile.information?has_content>
                    <p class="text profile-bio">${profile.information}</p>
                <#else>
                    <p class="muted profile-bio">
                        Пользователь пока не рассказал о себе.
                    </p>
                </#if>
            </div>
        </div>
    </section>

    <div class="section-heading">
        <h2>Публикации</h2>
        <span class="count-badge">${publicationCount?c}</span>
    </div>

    <#if publications?has_content>
        <@layout.publications items=publications/>
    <#else>
        <section class="empty-state panel">
            <h2>Пока нет публикаций</h2>

            <#if ownProfile>
                <p class="muted">
                    Добавьте фотографию и расскажите о своём дне.
                </p>

                <a class="button" href="/publications/new">
                    Создать публикацию
                </a>
            <#else>
                <p class="muted">
                    Когда пользователь добавит публикации,
                    они появятся здесь.
                </p>
            </#if>
        </section>
    </#if>

</@layout.page>