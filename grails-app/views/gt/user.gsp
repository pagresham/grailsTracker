<!doctype html>
<html>
<head>
    <meta name="layout" content="gt_layout"/>
    <title>GT Users/title></title>

</head>
<body>



<div role="main" class="container">
    <section>
        <h1>Welcome to Giftracker User Pages</h1>

        <p>
            This page allow to create a new user, or edit an existing user.
        </p>
    </section>
    <section>
        <g:if test="${users}">
        %{-- Display the list of users --}%
            <g:each var="user" in="${users}">
                <div class="user-div">
                    <h4>User: ${user.uname}</h4>

                    <g:each var="recip" in="${user.recips}">
                        <h5>Recipients  ${recip.getFullName()}</h5>

                        <ul>
                            <g:each var="gift" in="${recip.gifts}">
                                <li>Desc: ${gift.desc}, Price: ${gift.price}</li>
                            </g:each>
                        </ul>

                    </g:each>

                </div>
            </g:each>
        </g:if>

    </section>
</div>

</body>
</html>
