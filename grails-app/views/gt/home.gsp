<!doctype html>
<html>
<head>
    <meta name="layout" content="gt_layout"/>
    <title>GiftTracker UserHome</title>

</head>
<body>



<div role="main" class="container">
    <section>
        <h3>Welcome ${session.validatedUser.uname}</h3>
        <p>Find below a list of your current Recipients.</p>
        <p>Click the link to edit their Gift List</p>

    </section>
    <g:if test="${user}">
        <section>
            <div>
                <h4>Recipients:</h4>
                <div>
                    <ul>
                    <g:each var="recip" in="${user.recips}">
                        <li>
                            Recip Name: ${recip.getFullName()}  Relation: ${recip.relation}
                            <g:link action="list" controller="gt" params="[recipId: recip.id]">View List</g:link>
                        </li>
                    </g:each>
                    </ul>
                </div>
                <p>Add New Recip</p>
            </div>

        </section>
    </g:if>

</div>

</body>
</html>
