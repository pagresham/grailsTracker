<!doctype html>
<html>
<head>
    <meta name="layout" content="gt_layout"/>
    <title>GT Welcome</title>

</head>
<body>



<div role="main" class="container">
    <section>
        <h1>Welcome to Giftracker Welcome Page</h1>
        <p>
            This will be the landing page for GiftTracker 2.0!
        </p>
        <p>user can validate, link to create user, and get summary info</p>
    </section>
    <section>
        %{-- Select User Validation Widget --}%
        <div>
            <g:form action="validate" controller="gt" method="post" id="validate-form">
                <h4>Select or Change Users</h4>
                <div>
                    <label for="user-select"></label>
                    <select name="user-select" id="user-select">
                        <option value="">Select a User</option>
                        <g:each var="user" in="${users}">
                            <option value="${user.id}">${user.uname}</option>
                        </g:each>
                    </select>    
                </div>
                <div>
                    <g:submitButton name="submit-validate"/>
                </div>
            </g:form>
        </div>
        <div>
            <h4>Create a new Account</h4>
            <div>
                <g:form action="newAccount" controller="gt" method="post" id="newAccount-form">
                    <div>
                        <label for="uname">UserName:</label>
                        <input type="text" id="uname" name="uname">
                    </div>
                    <div>
                        <label for="passwd">Password:</label>
                        <input type="password" id="passwd" name="passwd">
                    </div>
                    <div>
                        <label for="passwdrpt">Repeat Password:</label>
                        <input type="password" id="passwdrpt" name="passwdrpt">
                    </div>
                    <g:submitButton class="btn btn-success btn-sm" name="submituser" value="Submit User"/>
                </g:form>
            </div>
        </div>
    </section>
</div>

</body>
</html>
