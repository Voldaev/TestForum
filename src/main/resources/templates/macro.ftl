<#macro nicehat>
    <div class="row text-center">
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
            <div class="col">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h3 >${row.theme} </h3>
                        <h5 >автор: ${row.themeCreator}</h5>
                        <p class="card-text"></p>
                        <br>
                        ${row.text}
                        <br><br>
                        <div class="d-flex">
                            <small class="text-muted">Опубликовано ${row.published}</small>
                        </div>
                        <div class="row">
                            <div class="col-2">
                                <button type="button" class="btn btn-sm btn-outline-secondary" onclick="vote('/main/vote/theme/${row.id}')" >Проголосовать</button>

                                <p>рейтинг ${row.score}</p>
                            </div>
                            <div class="col text-center">
                                <div class="row">
                                    <a href="/main/discuss/${row.theme}">Комментарии</a>
                                </div>
                                <div class="row">
                                    <p>Комментариев сейчас ${row.comms}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br><br>
        </#list>
    </table>
</#macro>

<#macro comments rows>
    <table>
        <#list rows as row>
        <div class="row">
            <div class="col">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h5 >комментарий от пользователя: ${row.user}</h5>
                        <p class="card-text"></p>
                        <br>
                        ${row.text}
                        <br><br>
                        <div class="d-flex">
                            <small class="text-muted">Опубликовано ${row.published}</small>
                        </div>
                        <div class="row">
                            <div class="col-2">
                                <button type="button" class="btn btn-sm btn-outline-secondary" onclick="vote('/main/vote/comment/${row.id}')" >Проголосовать</button>
                                <p>рейтинг ${row.score}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br><br>
            </#list>
    </table>
</#macro>