<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />

    <!--<asset:stylesheet src="application.css"/>-->
    <asset:stylesheet src="bootstrap_4/bootstrap.css"/>
    <asset:stylesheet src="font-awesome-4.7.0/css/font-awesome.min.css"/>
    <asset:stylesheet src="gtracker.main.css"/>
    <g:layoutHead/>
</head>

<body onload="${pageProperty(name:'body.onload')}">
<div id="page">
    <nav class="navbar navbar-expand-sm navbar-light bg-success">
        <div class="container-fluid">
            <g:link class="navbar-brand" controller="gt" action="welcome">
                <i class="fa fa-gift fa-lg" aria-hidden="true"></i>
                Giftracker
            </g:link>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">

                    <!-- Need to handle active tags here -->

                    <!-- if user is logged in, dont show next link -->
                    <g:if test="${!session.validatedUser}" >
                        <li class="nav-item active">
                            <g:link class="nav-link" action="welcome" controller="gt">Welcome</g:link>
                        </li>
                    </g:if>
                    <g:else >
                        <li class="nav-item">
                            <g:if test="${actionName == 'home'}">
                                <g:link class="nav-link active" action="home" controller="gt">Home</g:link>
                            </g:if>
                            <g:else>
                                <g:link class="nav-link " action="home" controller="gt">Home</g:link>
                            </g:else>
                        </li>

                        <li class="nav-item">
                            <g:if test="${actionName == 'list'}">
                                <g:link class="nav-link active" action="list" controller="gt">MyLists</g:link>
                            </g:if>
                            <g:else >
                                <g:link class="nav-link" action="list" controller="gt">MyLists</g:link>
                            </g:else>

                        </li>
                        <li class="nav-item">
                            <g:if test="${actionName == 'gifts'}">
                                <g:link class="nav-link active" action="gifts" controller="gt">MyGifts</g:link>
                            </g:if>
                            <g:else >
                                <g:link class="nav-link" action="gifts" controller="gt">MyGifts</g:link>
                            </g:else>

                        </li>
                        <!--<li class="nav-item dropdown">-->
                        <!--<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">-->
                        <!--Dropdown link-->
                        <!--</a>-->
                        <!--<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">-->
                        <!--<a class="dropdown-item" href="#">Action</a>-->
                        <!--<a class="dropdown-item" href="#">Another action</a>-->
                        <!--<a class="dropdown-item" href="#">Something else here</a>-->
                        <!--</div>-->
                        <!--</li>-->
                    </g:else>
                </ul>
            <g:if test="${session.validatedUser}" >
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <g:if test="${actionName == 'admin'}">
                            <g:link class="nav-link active" controller="gt" action="admin">${session.validatedUser.uname} / Profile</g:link>
                        </g:if>
                        <g:else >
                            <g:link class="nav-link" controller="gt" action="admin">${session.validatedUser.uname} / Profile</g:link>
                        </g:else>

                    </li>
                    <li class="nav-item">
                        <g:link class="nav-link" controller="gt" action="signout">SignOut</g:link>
                    </li>
                </ul>
            </g:if>
            <g:else >
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <g:link class="nav-link" >Sign In</g:link>
                    </li>
                </ul>
            </g:else>

            </div>
            <!-- nav right? -->
            <!-- Account Info -->
        </div>
    </nav>


    <div class="body">
        <g:layoutBody />
    </div>
</div> <!-- end #page -->
    <div>
        <footer class="container">
            <div class="text-success">
                <p class="text-success">Brought to you by: ${author}</p>
            </div>
        </footer>
    </div>


<!-- Popper is included in the bundle  -->
<asset:javascript src="jquery-3.3.1.min.js"/>
<asset:javascript src="bootstrap_js/bootstrap.bundle.js"/>
</body>
</html>