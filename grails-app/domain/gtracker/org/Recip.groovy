package gtracker.org
import groovy.transform.ToString

@ToString(includeNames=true)
class Recip {
    String fname
    String lname
    String relation
    static belongsTo = [user: User]
    static hasMany = [gifts: Gift]


    static constraints = {
        fname size: 1..25, blank: false
        lname size: 1..25, blank: true
        user nullable: true
        relation blank: false, inList: ["Family", "Friend", "Other"]
    }

    String getFullName() {
        return "${this.fname} ${this.lname}"
    }

    Double getGiftTotal() {
        def sum = this.gifts.price.sum()
        return sum
    }
}
