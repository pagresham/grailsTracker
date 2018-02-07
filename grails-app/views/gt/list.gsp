<!doctype html>
<html>
<head>
    <meta name="layout" content="gt_layout"/>
    <title>GT Recipients</title>

</head>
<body>

%{--
For Items, I could have items on the list, and Purchased Items.
When you mark an item as purchased, it moves it to the purchased section
--}%

<div role="main" class="container">
    <section>
        <h3>Your List for ${recip.getFullName()}</h3>
        <p>Find below a list of Gifts for the selected Recipient</p>
    </section>
    <g:if test="${recip}">
    <section>
        <div>
            <h4>Gifts:</h4>
            <ul>
                <g:each var="gift" in="${recip.gifts}">
                    <li>
                        <p>Description: ${gift.desc}</p>
                        <p>Price: ${gift.price}</p>
                        %{-- Remove / Purchase / Add --}%
                        <g:link controller="gt" action="editGift" params="[giftId: gift.id]">Edit Gift</g:link>
                    </li>
                </g:each>
            </ul>
            <g:link controller="gt" action="addGift" params="[recipId: recip.id]">Add Gift</g:link>
        </div>
    </section>
    </g:if>
</div>

</body>
</html>
