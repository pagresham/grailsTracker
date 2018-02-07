package gtracker.org

import grails.core.GrailsApplication
import groovy.json.*


class GtController {
    def jsonOutput = new JsonOutput()
    def slurper = new JsonSlurper()


    UserService userService = new UserService()
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
            log.info("session.validateUser Param not found")
            redirect action: 'welcome'
            return
        }
        // get list of Recips for current user //
        def user = User.get((Integer)session.validatedUser.id)
        render view: 'home', model: [user: user]
    }

    def newuser() {
        def allusers = User.findAll()
        render allusers.size()
        allusers.each {
            println ""
            println "New User"
            println it.uname
            println it.passwdHash
            println ""
            println "Recips for ${it.uname}"
            it.recips.each {
                println it.getFullName()
            }
        }

        def recips = Recip.findAll()
        render recips.size()

        def firstUser = allusers.get(0)
        render firstUser.uname
    }


    // Content of individual list for specific recip
    // could contain an add item dialog
    /**
     * Display the list of Gifts for a specific Recip
     * @return
     */
    def list() {
        def recipId = params.recipId
        if(!recipId) {
            log.info("recipId Param not found")
            redirect action: 'home'
            return
        }
        def recip = Recip.get(recipId)
        render view: 'list', model: [recip: recip]
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
        def u1 = new User(uname: "pagresham2", passwdHash: "banjo", recips: [])
        def u2 = new User(uname: "rlgresham2", passwdHash: "running", recips: [])
        def u3 = new User(uname: "sagresham2", passwdHash: "starwars", recips: [])

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
            if(!r1.validate() || !r3.validate() || !r2.validate()) {
                log.info("error in one of the Recips")
                r2.errors.each {
                    println it.toString()
                }
            } else {
                log.info("Passed add of the Recips")
                u1.addToRecips(r2).save()
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






}
