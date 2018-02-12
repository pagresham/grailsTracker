<!doctype html>
<html>
<head>
    <meta name="layout" content="gt_layout"/>
    <title>GiftTracker UserHome</title>

</head>
<body>


<div role="main" class="container">

    <g:if test="${user}">
    <section class="row">

        <div class="mt-4 col-12 col-md-6">
            <h4>Your Recipients:</h4>
            <table class="table table-striped">
                <tr>
                    <th>Name</th><th>Relation</th><th></th>
                </tr>
                <g:each var="recip" in="${user.recips}">
                    <tr>
                        <td>${recip.getFullName()}</td>
                        <td>${recip.relation}</td>
                        <td>
                            <g:link action="list" controller="gt" params="[recip_id: recip.id]" method="post">View List</g:link>
                        </td>
                    </tr>
                </g:each>
            </table>
            <g:if test="${!user.recips}">
                <div class="alert alert-info">
                    <span>Click below to add Recipients to your list</span>
                </div>
            </g:if>

            <div>
                <button class="btn btn-info btn-sm add-recip-btn">Add Recipient</button>
            </div>
        </div>

        <div class="mt-4 add-recip col-12 col-md-6">

            <div class="add-recip-form">
                <h4>New Recipient</h4>
                <g:form action="home" controller="gt" method="post" name="addRecipientForm" id="addRecipientForm">
                    <div class="form-group">
                        <label for="fname">First Name:</label>
                        <input class="form-control" type="text" name="fname" id="fname">
                    </div>
                    <div class="form-group">
                        <label for="lname">Last Name:</label>
                        <input class="form-control" type="text" name="lname" id="lname">
                    </div>
                    <div class="form-group-sm">
                        <label for="relation">Relationship:</label>
                        <select class="form-control" name="relation" id="relation">
                            <g:each var="relation" in="${relationList}">
                                <option value="${relation}">${relation}</option>
                            </g:each>
                        </select>
                    </div>
                    <div class="mt-4">
                        <g:submitButton class="btn btn-success btn-sm" name="addRecipient" id="addRecipient" value="Submit"/>
                    </div>

                </g:form>
            </div>
            <div>
                <g:if test="${flash.recipSuccess}">
                    <p>${flash.recipSuccess}</p>
                    Success!
                </g:if>
                <g:if test="${recipError && recipError.size() > 0}">
                    <div class="alert alert-danger">
                        <span>${recipError[0]}</span>
                    </div>
                </g:if>
            </div>
        </div>

    </section>
    </g:if>

</div>

</body>
</html>
