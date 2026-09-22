<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.page title="Регистрация">

    <section class="panel form-card">
        <div class="form-heading">
            <span class="form-brand">Microgram</span>
            <p class="muted">
                Создайте аккаунт и поделитесь первым моментом.
            </p>
        </div>

        <@spring.bind "registerDto"/>

        <#if spring.status.error>
            <div class="alert alert-error" role="alert">
                <#list spring.status.errorMessages as error>
                    <div>${error}</div>
                </#list>
            </div>
        </#if>

        <form method="post" action="/auth/register">
            <@layout.csrf/>

            <div class="form-group">
                <label for="login">Логин</label>

                <input id="login"
                       name="login"
                       value="${registerDto.login!}"
                       minlength="3"
                       maxlength="30"
                       autocomplete="username"
                       aria-describedby="loginHint loginError"
                       required>

                <small id="loginHint" class="field-hint">
                    От 3 до 30 латинских букв, цифр или символов _.
                </small>

                <@spring.bind "registerDto.login"/>
                <div id="loginError" class="field-error">
                    <@spring.showErrors "<br>" "error"/>
                </div>
            </div>

            <div class="form-group">
                <label for="email">Email</label>

                <input id="email"
                       type="email"
                       name="email"
                       value="${registerDto.email!}"
                       maxlength="255"
                       autocomplete="email"
                       aria-describedby="emailError"
                       required>

                <@spring.bind "registerDto.email"/>
                <div id="emailError" class="field-error">
                    <@spring.showErrors "<br>" "error"/>
                </div>
            </div>

            <div class="form-group">
                <label for="name">Имя</label>

                <input id="name"
                       name="name"
                       value="${registerDto.name!}"
                       maxlength="100"
                       autocomplete="name"
                       aria-describedby="nameError"
                       required>

                <@spring.bind "registerDto.name"/>
                <div id="nameError" class="field-error">
                    <@spring.showErrors "<br>" "error"/>
                </div>
            </div>

            <div class="form-group">
                <label for="password">Пароль</label>

                <input id="password"
                       type="password"
                       name="password"
                       minlength="6"
                       maxlength="50"
                       autocomplete="new-password"
                       aria-describedby="passwordHint passwordError"
                       required>

                <small id="passwordHint" class="field-hint">
                    От 6 до 50 символов: латинские буквы, цифры
                    и знаки без пробелов.
                </small>

                <@spring.bind "registerDto.password"/>
                <div id="passwordError" class="field-error">
                    <@spring.showErrors "<br>" "error"/>
                </div>
            </div>

            <div class="form-group">
                <label for="information">О себе</label>

                <textarea id="information"
                          name="information"
                          maxlength="1000"
                          rows="4"
                          placeholder="Расскажите о своих интересах"
                          aria-describedby="informationHint informationError">${registerDto.information!}</textarea>

                <small id="informationHint" class="field-hint">
                    Необязательно. До 1000 символов.
                </small>

                <@spring.bind "registerDto.information"/>
                <div id="informationError" class="field-error">
                    <@spring.showErrors "<br>" "error"/>
                </div>
            </div>

            <button class="button-block" type="submit">
                Зарегистрироваться
            </button>
        </form>

        <p class="form-bottom">
            Уже есть аккаунт?
            <a href="/auth/login">Войти</a>
        </p>
    </section>

</@layout.page>