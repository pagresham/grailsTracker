package gtracker.org
import groovy.transform.ToString

@ToString(includeNames=true)
class User {

    String uname
    String passwdHash
    static hasMany = [recips: Recip]

    static constraints = {
        uname size: 5..25, blank: false, unique: false
        passwdHash size: 5..255, blank: false, nullable: false
    }

}
