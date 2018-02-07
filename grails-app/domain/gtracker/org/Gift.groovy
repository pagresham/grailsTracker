package gtracker.org
import groovy.transform.ToString

@ToString(includeNames=true)
class Gift {
    String desc
    double price
    static belongsTo = [recip: Recip]

    static constraints = {
        recip nullable: true
        desc blank: false, size: 1..128, nullable: false
//        price min: 0d, nullable: false

    }

}
