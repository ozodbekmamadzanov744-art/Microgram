<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.page title="Новая публикация">

    <section class="panel form-card publication-form-card">
        <div class="form-heading">
            <h2>Поделитесь моментом</h2>
            <p class="muted">
                Выберите фотографию и добавьте описание.
            </p>
        </div>

        <@spring.bind "publicationFormDto"/>

        <#if spring.status.error>
            <div class="alert alert-error" role="alert">
                <#list spring.status.errorMessages as error>
                    <div>${error}</div>
                </#list>
            </div>
        </#if>

        <form method="post"
              action="/publications?${_csrf.parameterName}=${_csrf.token}"
              enctype="multipart/form-data">

            <@layout.csrf/>

            <div class="form-group">
                <label for="image">Фотография</label>

                <div class="upload-field">
                    <input id="image"
                           type="file"
                           name="image"
                           accept="image/jpeg,image/png"
                           aria-describedby="imageHint imageError"
                           required>

                    <small id="imageHint" class="field-hint">
                        JPG или PNG, до 5 МБ.
                        Размер — не более 6000 × 6000 пикселей.
                    </small>
                </div>

                <@spring.bind "publicationFormDto.image"/>
                <div id="imageError" class="field-error">
                    <@spring.showErrors "<br>" "error"/>
                </div>
            </div>

            <div class="form-group">
                <label for="description">Описание</label>

                <textarea id="description"
                          name="description"
                          maxlength="2000"
                          rows="6"
                          placeholder="Расскажите, что на фотографии..."
                          aria-describedby="descriptionHint descriptionError"
                          required>${publicationFormDto.description!}</textarea>

                <small id="descriptionHint" class="field-hint">
                    До 2000 символов.
                </small>

                <@spring.bind "publicationFormDto.description"/>
                <div id="descriptionError" class="field-error">
                    <@spring.showErrors "<br>" "error"/>
                </div>
            </div>

            <div class="form-actions">
                <button type="submit">
                    Опубликовать
                </button>

                <a class="button button-secondary"
                   href="/users/${currentUser.id?c}">
                    Отмена
                </a>
            </div>
        </form>
    </section>

</@layout.page>