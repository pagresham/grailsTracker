<!doctype html>
<html>
<head>
    <meta name="layout" content="gt_layout"/>
    <title>GT Welcome</title>

</head>
<body>



<div role="main" class="container">
    <section class="row">
        %{-- Select User Validation Widget --}%

        <div class="col-md-6 col-12 mt-4">
            <h4>Create a new Account</h4>
            <div>
                <g:form action="newAccount" controller="gt" method="post">
                    <div class="form-group">
                        <label for="uname">UserName:</label>
                        <input class="form-control" type="text" id="uname" name="uname" value="${(flash.uname) ?: ''}">
                    </div>
                    <div class="form-group">
                        <label for="passwd">Password:</label>
                        <input class="form-control" type="password" id="passwd" name="passwd">
                    </div>
                    <div class="form-group">
                        <label for="passwdrpt">Repeat Password:</label>
                        <input class="form-control" type="password" id="passwdrpt" name="passwdrpt">
                    </div>
                    <div>
                        <g:if test="${successMsg}">
                            <small class="text-success">${successMsg[0]}</small>
                        </g:if>

                    </div>
                    <div>
                        <g:submitButton class="btn btn-success btn-sm" name="submituser" value="Submit User"/>
                    </div>
                </g:form>
            </div>
            <g:if test="${flash.success}">
                <div class="alert alert-success welcome-success mt-4">
                    <p><strong>Success:</strong> ${flash.success}</p>
                </div>
            </g:if>
            <g:if test="${flash.errors}">
                <div class="alert alert-warning welcome-error mt-4">
                    <span><strong>Error:</strong> ${(flash.errors[0]) ?: ""}</span>
                </div>
            </g:if>
            %{-- The Below is worth looking at!!! --}%
            %{--<g:hasErrors bean="${newUser}">--}%
                %{--<ul>--}%
                    %{--<g:eachError var="err" bean="${newUser}">--}%
                        %{--<li><g:message error="${err}" /></li>--}%
                    %{--</g:eachError>--}%
                %{--</ul>--}%
            %{--</g:hasErrors>--}%
        </div>
        <div class="col-md-6 col-12 mt-4">
            <h4>Log In</h4>
            <g:form action="validate2" controller="gt" method="post" id="validate2-form">
                <div class="form-group">
                    <label for="username">UserName:</label>
                    <input class="form-control" type="text" id="username" name="username">
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input class="form-control" type="password" id="password" name="password">
                </div>
                <g:submitButton name="validate" class="btn btn-success btn-sm" value="LogIn"/>
            </g:form>
            <g:if test="${flash.validationError}">
                <div class="alert alert-danger mt-4">
                    <span>Error: ${flash.validationError}</span>
                </div>
            </g:if>

            <g:form action="validate" controller="gt" method="post" id="validate-form">
                <h4>Select or Change Users</h4>
                <div class="form-group">
                    <label for="user-select"></label>
                    <select class="form-control" name="user-select" id="user-select">
                        <option value="">Select a User</option>
                        <g:each var="user" in="${users}">
                            <option value="${user.id}">${user.uname}</option>
                        </g:each>
                    </select>
                </div>
                <div>
                    <g:submitButton class="btn btn-success btn-sm" name="submit-validate" value="Go!"/>
                </div>
            </g:form>
        </div>
    </section>
</div>

</body>
</html>
