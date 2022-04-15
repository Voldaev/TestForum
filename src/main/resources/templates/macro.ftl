<#macro table rows>
    <table>
        <#list rows as row>
            <div class="row">
                <a href="/main/${row}">${row}</a>
            </div>
        </#list>
    </table>
</#macro>