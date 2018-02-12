package gtracker.org

import groovy.json.*


/**
 * TODO
 * 1. Seperate into multiple controllers
 * 2. Finish Create new User
 * 3. Implement login User
 * 4. User adds Recip
 * 5. User adds Gifts to Recip
 * 6. User edits Recip and edits Gift and edits User
 */




class GtController {
    def jsonOutput = new JsonOutput()
    def slurper = new JsonSlurper()


    UserService userService
    SecurityService securityService
    String theAuthor = grailsApplication.config.getProperty('info.app.author')

    def appinfo() {
        theAuthor = grailsApplication.config.getProperty('info.app.author')
        def name = grailsApplication.config.getProperty('info.app.name')
        def version = grailsApplication.config.getProperty('info.app.version')

        render theAuthor + "\n"
        render name + "\n"
        render version + "\n"

    }

    def index() {
        redirect action: 'gt'
    }

    // edit user profile // delete lists //
    def admin() {

    }

    // loads default landing page //
    def welcome() {
        def users = User.list(sort: 'uname', order: 'asc')

        render view: 'welcome', model: [users: users]
    }

    /**
     * Handle Validation request from user select widget in welcome.gsp
     */
    def validate() {
        // catch params, logout, login, redirect //

        def userId = params['user-select']

        if (userId) {
            log.info(userId.toString())
            def validatedUser = User.get(userId)
            if(validatedUser) {
                session.validatedUser = validatedUser
            }
        }
        redirect action: 'home'
    }

    /**
     * Clears Session, and redirects to welcome page - on Sign Out
     */
    def signout() {
        session.invalidate()
        redirect action: 'welcome'
    }

    // After validation go here // Home page for user // recip view //
    def home() {
        if(!session.validatedUser) {
            redirect action: 'welcome'
            log.info('not found session.validatedUser')
            return
        }
        // Hardcoded list, could be somewhere else as a setting//
        def relationList = ["Family", "Friend", "Other"]
        // set up errors object //
        def recipError = []

        log.info("Running Home1")

        // fix this //

        def user = User.get((Integer)session.validatedUser.id)
        // if coming from add recip form //
        if(params.addRecipient) {
            log.info("Running Home2")
            def fname = params.fname.trim()
            def lname = params.lname.trim()
            def relation = params.relation.trim()
            if(!fname || !lname || !relation) {
                recipError << "All fields are required"
            }
            // fname, lname, relation, user
            def newRecip = new Recip(fname: fname, lname: lname, relation: relation, gifts: [])
            if(!newRecip.validate()) {
                recipError << "There was a problem with your Recipient"
            } else {
                log.info("newRecip Validated")
            }
            if(recipError.size() == 0) {

                def added = user.addToRecips(newRecip).save(flush: true)

                if(added) {
                    log.info("Created new Recipient")
                    flash.recipSuccess = "Created new Recipient"
                } else {
                    recipError << "Unable to save new Recipient"
                }
            }
        }

        if(!session.validatedUser) {
            log.info("session.validateUser not found")
            redirect action: 'welcome'
            return
        } else { //user is true
            render view: 'home', model: [user: user, relationList: relationList, recipError: recipError]
        }
    }

    /**
     * Handle validation of User PW and sets session for currently logged user
     */
    def validate2() {
        // check if params are available
        // look in DB to see if matching username
        // if no suggest create account
        // if yes get pw and salt for user
        // hash passed pw with salt and check.
        // if match Success else Fail

        log.info('running validate2')
        if(params.username && params.password) {
            log.info('running validate3')
            def uname = params.username
            def passwd = params.password
            def user = User.findByUname(uname)
//            def user = User.find { uname == uname }
            if(!user) {
                flash.validationError = "Unable to Validate User"
            } else {
                log.info('User is true')
                // gen pw based on users salt
                def hashToMatch = securityService.getSecurePassword(passwd, user.salt).generatedPassword
                // check if new pw hash matches original pw hash
                if( hashToMatch != user.passwdHash) {
//                    log.info(hashToMatch)
//                    log.info(user.passwdHash)
//                    log.info("Unable to log in!")
                    flash.validationError = "Unable to Validate Password"
                } else {
                    session.validatedUser = user
//                    log.info("Logging In!")
                    redirect controller: 'gt', action: 'home'
                    return
                }
            }
        } else {
            flash.validationError = "Please enter you Username and Password"
        }
        redirect controller: 'gt', action: 'welcome'
    }

    /**
     * Handle submission of new user from welcome.gsp
     * Recieve inputs, validate, save to DB, redirect to home page, or ask to log in
     */
    def newAccount() {
        log.info("In New Account1")
        def newUser
        def uname
        flash.errors = []
        if(params.uname.trim() && params.passwd.trim() && params.passwdrpt.trim()) {


            uname = params.uname.trim()
            flash.uname = uname
            def passwd = params.passwd.trim()
            def passwdrpt = params.passwdrpt.trim()
            if(passwd.size() < 5 || passwd.size() > 25) {
                flash.errors << "Passwords must me 5 - 25 characters"
            }

            def pattern = ~/^[a-zA-Z0-9_!@#$%*-]{5,25}$/
            def matches = uname =~ pattern
            if(!matches) {
                flash.errors << "UserName must be 5 - 25 characters, and consist of letters, numbers, and [-!@#\$%*-]"
            }
            if (passwd != passwdrpt) {
                flash.errors << "Passwords Do not Match"
            }
            if(!flash.errors.size()) {

                def passwdInfo = securityService.getSecurePassword(passwd)
                // Create New User
                newUser = new User(uname: uname,
                        passwdHash: passwdInfo.generatedPassword,
                        salt: passwdInfo.salt)

                if(!newUser.validate()) {
                    log.info("User did not validate")
                    if (newUser.errors.hasFieldErrors("uname")) {
                        flash.errors << "Usernames are at least 5 chars long, and must be unique!"
                    }
                    if(newUser.errors.hasFieldErrors("passwdHash")) {
                        flash.errors << "Error with password"
                    }
                    newUser.errors.allErrors.each {
                        println it
                    }
                } else {
                    if(newUser.save()) {
                        flash.success = "New User Created! Login to use the App!"
                    } else {
                        flash.errors << "There was a problem. Please create a new User"
                    }
                }
            }
        } else {
            flash.errors << "All fields are required to create a new User"
        }
        redirect action: 'welcome'
    }

    def testsecure () {
        def t1 = securityService.getSecurePassword("test2")
        def t2 = securityService.getSecurePassword("test2", t1.salt)
        if(t1.generatedPassword == t2.generatedPassword) {
            log.info("Passwords Match")
            log.info(t1.salt.getClass().toString())
        } else {
            log.info("Passwords DO NOT Match")
        }
        render "${t1.generatedPassword} <br>"
        render "${t1.salt} <br>"
        render "${t2.generatedPassword} <br>"
        render "${t2.salt} <br>"
    }


    // Content of individual list for specific recip
    // could contain an add item dialog
    /**
     * Display the list of Gifts for a specific Recip
     * @return
     */
    def list() {
        if(!session.validatedUser) {
            redirect action: 'welcome'
            return
        }
        def recipId = params.recip_id
        if(!recipId) {
            log.info("recipId Param not found")
            redirect action: 'home'
            return
        }
        // check if recip is in valUsers list //
        // Get the user
        def user = User.get(session.validatedUser.id)
        // get the users list if he has it //
        def hasRecip = user.recips.find {
            it.id.toString() == recipId }
        // Check if user has the list id
        if(hasRecip) {
            log.info("User has this list")
        } else {
            log.info("User does not have this list")
            redirect action: 'welcome'
            return
        }
        def recip = Recip.get(recipId)
        render view: 'list', model: [recip: recip]
    }


    def addGift() {
        if(!session.validatedUser) {
            redirect action: 'welcom'
            return
        }
        def giftErrors = []
        def recipId
        if(params.price && params.desc && params.recipId && params.name) {

            def desc = params.desc.trim()
            def price = params.price.trim().toDouble()
            def name = params.name.trim()
            recipId = params.recipId




            // need to create the new gift and validate
            // verify that user has this list?

            def newGift = new Gift(name: name, desc: desc, price: price)
            if(!newGift.validate()) {
                giftErrors << "Unable to validate new Gift"
            } else {
                log.info("New Gift Validated")
                println desc.toString()
                println price.toString()
                println recipId.toString()

                def user = User.get(session.validatedUser.id)

                def recip = Recip.get(recipId)


                def saved = recip.addToGifts(newGift).save(flush: true)
                if(saved) {
                    log.info("Saved is true")
                }
            }
        }
        log.info("here")
        redirect action: 'list', params: [recip_id: recipId]
    }



    // display and edit single
    def gift() {

        def toy = new Gift(desc: "my little pony")
        def result = toy.save()
        println result
        render result.desc.toString()

    }
    // display all items for current user
    def gifts() {
        log.info('in gifts()')
        if(!session.validatedUser) {
            log.info("Session not found at gt/items")
            redirect action: 'welcome'
            return
        }
        def query = Gift.where {
            recip.user.id == session.validatedUser.id
        }
        def allGifts = query.find()
        render view: 'gifts', model: [allGifts: allGifts]
    }

    def allusers() {
        def users = User.findAll()
        def recips = Recip.findAll()
        def gifts = Gift.findAll()
        log.info(users.size().toString())
//        log.info(users[0].toString())
//        log.info(recips[0].toString())
//        log.info(gifts[0].toString())
        render view: 'user', model: [users: users]
    }

    // Create account on welcome -> here // could double as edit user //
    def user() {
        def ok = false
        def u1p = securityService.getSecurePassword('banjo')
        def u2p = securityService.getSecurePassword('running')
        def u3p = securityService.getSecurePassword('starwars')

        def u1
        def u2
        def u3

        if(u1p && u2p && u3p) {
            u1 = new User(uname: "pagresham2", passwdHash: u1p.generatedPassword, salt: u1p.salt, recips: [])
            u2 = new User(uname: "rlgresham2", passwdHash: u2p.generatedPassword, salt: u2p.salt, recips: [])
            u3 = new User(uname: "sagresham2", passwdHash: u2p.generatedPassword, salt: u3p.salt, recips: [])
        }



        if(!u1.validate() || !u2.validate() || !u3.validate()) {
            u1.errors.each {print it}
            log.info("error in one of the us")
        } else {
            u1.save()
            u2.save()
            u3.save()
            log.info("passed all of the users")
            def r1 = new Recip(fname: "Sage", lname: "Gresham", relation: "Family", gifts: [], user: null)
            def r2 = new Recip(fname: "Laura", lname: "Cheney", relation: "Family", gifts: [], user: null)
            def r3 = new Recip(fname: "Robin", lname: "Gresham", relation: "Family", gifts: [], user: null)
            def r4 = new Recip(fname: "Bob", lname: "Cheney", relation: "Family", gifts: [], user: null)
            if(!r1.validate() || !r3.validate() || !r2.validate() || !r4.validate()) {
                log.info("error in one of the Recips")
                r2.errors.each {
                    println it.toString()
                }
            } else {
                log.info("Passed add of the Recips")
                u1.addToRecips(r2).save()
                u1.addToRecips(r4).save()
                u2.addToRecips(r3).save()
                u3.addToRecips(r1).save()

//                u1.recips[0].addToGifts(new Gift(desc: "Beer", price: 12.99)).save()
//                u2.recips[0].addToGifts(new Gift(desc: "Atari", price: 100.00)).save()
//                u3.recips[0].addToGifts(new Gift(desc: "Apple", price: 0.79)).save()

                def g1 = new Gift(desc: "Beer", price: 12.99)
                def g2 = new Gift(desc: "Atari", price: 100.00)
                def g3 = new Gift(desc: "Apple", price: 0.79)

                if(!g1.validate() || !g2.validate() || !g3.validate()) {
                    log.info("error in one of the gifts")
                    g3.errors.each {
                        println it
                    }
                } else {
                    log.info("padded the gifts")
                    r1.addToGifts(g1).save()
                    r2.addToGifts(g2).save()
                    r3.addToGifts(g3).save()
                    ok = true
                }
            }
        }
        if(ok) {
            log.info("OK")
            render view: 'user', model: [users: [u1, u2, u3]]
        } else {
            log.info("NOK")
            render "shit"
        }
    }

    def showUsers() {
        def users = User.list()
        users.each {
            println ""
            println it.id
            println it.uname
            println it.salt
            println it.passwdHash
            println ""
        }
    }

    def checkpw() {
        def pw = 'banjo' // like a submitted pw at auth
        def uname = 'pagresham2' // un of user to auth as
        def userToCheck = User.get(1) // gets the user // should hit the db an make suer user is there
        def userSalt = userToCheck.salt // get the users salt
        def pwMaybe = securityService.getSecurePassword(pw, userSalt) // generate a hash based on passed pw and users salt
        if(pwMaybe.generatedPassword == userToCheck.passwdHash) {
            println "Passwords Match!"
            render "Passwords Match!"
        }
        else {
            println "Passwords Do Not Match!!!"
            render "Passwords Do Not Match!"
        }

    }






}
