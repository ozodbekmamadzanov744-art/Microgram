<#import "../layout.ftlh" as layout>
<@layout.page title="Ошибка ${status?c}">
    <p>${message}</p>
    <a href="/">На главную</a>
</@layout.page>
