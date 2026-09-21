<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<@layout.page title="Новая публикация">
    <@spring.bind "publicationFormDto"/>
    <#list spring.status.errorMessages as error><p class="error">${error}</p></#list>
    <form method="post" action="/publications?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
        <@layout.csrf/>
        <label>Картинка JPG или PNG, до 5 МБ <input type="file" name="image" accept="image/jpeg,image/png" required></label>
        <label>Описание <textarea name="description" maxlength="2000" required>${publicationFormDto.description!}</textarea></label>
        <button type="submit">Опубликовать</button>
    </form>
</@layout.page>
