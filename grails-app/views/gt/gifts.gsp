<!doctype html>
<html>
<head>
    <meta name="layout" content="gt_layout"/>
    <title>GT Items</title>

</head>
<body>



<div role="main" class="container">
    <section>
        <h3>Gifts Page</h3>

        <p>
            This would contain a list of all gifts a user is tracking for their recips.
        </p>
        <p>It could be dynamic by recip, or some other param.</p>
    </section>
    <section>
        <g:if test="${allGifts}">
            <h4>Viewing All Gifts as enterd by ${session.validatedUser.uname}</h4>
            <div>
                <ul>
                <g:each var="gift" in="${allGifts}">
                    <li>
                        <p>Description: ${gift.desc}</p>
                        <p>Price: ${gift.price}</p>
                        <p>Recipient: ${gift.recip.getFullName()}</p>
                        <g:link controller="gt" action="editGift">Edit</g:link>
                    </li>
                </g:each>
                </ul>
            </div>

        </g:if>
    </section>
</div>

</body>
</html>
