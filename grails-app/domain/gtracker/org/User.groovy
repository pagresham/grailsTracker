package gtracker.org
import groovy.transform.ToString

@ToString(includeNames=true)
class User {

    String uname
    String passwdHash
    Byte [] salt
    static hasMany = [recips: Recip]

    static constraints = {
//        salt nullable: true
        uname size: 5..25, blank: false, unique: true
        passwdHash size: 5..255, blank: false, nullable: false
    }

}
