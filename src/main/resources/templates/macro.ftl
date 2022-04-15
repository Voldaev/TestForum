<#macro sections rows>
    <table>
        <#list rows as row>
            <div class="row">
                <a href="/main/${row}">${row}</a>
            </div>
        </#list>
    </table>
</#macro>

<#macro themes rows>
    <table>
        <#list rows as row>
            <div class="row">
                <p> ${row}</p>
            </div>
        </#list>
    </table>
</#macro>