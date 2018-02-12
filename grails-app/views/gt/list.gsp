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
        <h3>Recipient: ${recip.getFullName()}</h3>
        <p>Find below a list of Gifts for the selected Recipient</p>
    </section>
    <g:if test="${recip}">
    <section>
        <div>
            <h4>Gifts:</h4>

            <table class="table table-striped">
                <tr>
                    <th></th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th></th>
                </tr>
                <g:if test="${recip.gifts}">
                    <g:each var="gift" in="${recip.gifts}" status="i">
                        <tr>
                            <td>${i + 1}</td>
                            <td>${gift.name}</td>
                            <td>${gift.desc}</td>
                            <td>${gift.price}</td>
                            <td>Edit</td>
                        </tr>
                    </g:each>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>${recip.getGiftTotal()}</td>
                        <td></td>
                    </tr>
                </g:if>
            </table>
            <g:if test="${!recip.gifts}">
                <div class="alert alert-info">
                    <span>Click below to add Gifts to this Recipient</span>
                </div>

            </g:if>
        </div>

        <div id="addGift">
            <h4>Add Gift for ${recip.getFullName()}</h4>
            <g:form action="addGift" controller="gt" method="post">
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input class="form-control" name="name" id="name" type="text">
                </div>
                <div class="form-group">
                    <label for="desc">Description:</label>
                    <input class="form-control" name="desc" id="desc" type="text">
                </div>
                <div class="form-group">
                    <label for="gift-price">Price:</label>
                    <input class="form-control" name="price" type="number" step="0.01" id="gift-price" min="0" max="2000000">
                </div>
                <div>
                    <input type="hidden" name="recipId" value="${recip.id}">
                </div>
                <div>
                    <input class="btn btn-success btn-sm" type="submit" value="Add Gift">
                </div>
            </g:form>
        </div>
    </section>
    </g:if>
</div>

</body>
</html>
