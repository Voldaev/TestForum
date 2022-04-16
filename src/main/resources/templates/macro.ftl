<#macro nicehat>
    <div class="row">
        <div class="col text-center ">
            <h1> КРАСИВАЯ ШАПКА С НАЗВАНИЕМ </h1>
        </div>
    </div>
</#macro>

<#macro sections rows>
    <table>
        <#list rows as row>
            <div class="row">
                <a href="/main/${row}/1">${row}</a>
            </div>
        </#list>
    </table>
</#macro>

<#macro themes rows>
    <table>
        <#list rows as row>
            <div class="row">
                <p> ${row.section}</p>
            </div>
            <div class="row">
                <p> ${row.themeCreator}</p>
            </div>
            <div class="row">
                <p> ${row.theme}</p>
            </div>
            <div class="row">
                <p> ${row.text}</p>
            </div>
            <div class="row">
                <p> ${row.published}</p>
            </div>
            <div class="row">
                <p> ${row.score}</p>
            </div>
        </#list>
    </table>
</#macro>